/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ysmal.ssc.server;

import com.ysmal.ssc.model.UserInfo;
import com.ysmal.ssc.server.Abstract.AbstractSource;
import com.ysmal.ssc.server.Abstract.AbstractStorage;
import com.ysmal.ssc.server.Impl.ScanHH;
import com.ysmal.ssc.server.Impl.SourceHHapi;
import com.ysmal.ssc.server.Impl.StorageDB;
import com.ysmal.ssc.server.Impl.StorageSOUT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class ThreadManager {

    private Map<String, List<ScannerThread>> globalRepo;
    private List<ScannerThread> repository;

    private Map<String, UserInfo> usersLogOn;

    private AbstractSource source;
    private AbstractStorage storage;

    public ThreadManager() {
        this.globalRepo = new HashMap();
        this.usersLogOn = new HashMap();

        this.repository = new ArrayList<ScannerThread>();
        this.source = new SourceHHapi("https://api.hh.ru/vacancies?area=2&text=Java&period=1&per_page=100");
        this.storage = new StorageDB();
//        this.storage = new StorageSOUT();
    }


    public String addScannerThread(String name, String userName) {
        if (name.isEmpty()||name.matches(""))
        {
            name = "JavaThread";
            this.source = new SourceHHapi("https://api.hh.ru/vacancies?area=2&text=Java&period=1&per_page=100");
        }
        else
        {
            this.source = new SourceHHapi("https://api.hh.ru/vacancies?area=2&text="+name+"&period=1&per_page=100");
            name = name + "Thread";
        }
        ScannerThread th1 = new ScanHH(source, storage, 14400000L, name, userName);
        this.repository.add(th1);
        this.globalRepo.put(userName, this.repository);
        th1.startTime = new Date();
        th1.start();
//        ObjectMapper mapper = new ObjectMapper();
        String ret = th1.toString();
//        try {
//
//            String test = "{\"age\":29,\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"],\"name\":\"mkyong\"}";
//            Object json = mapper.readValue(test, ScannerThread.class);
//            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
//            
//        } catch (IOException ex) {
//            //error
//        }

        return ret;
    }

    public void deleteScannerThread() {
    }

    public String getJsonInfoRunningThreads(String userName) {
//        System.out.println("NAME="+userName);

        String ret = "{\"message\":\"None\"}";
        if (this.globalRepo != null) {
            List<ScannerThread> listThreads = this.globalRepo.get(userName);
            if (listThreads != null) {
//                ret = "{\"message\":\"Success\",\"scanners\":[{\"name\":\""+listThreads.get(0).name+"\",\"state\":\""+listThreads.get(0).getState().toString()+"\"}]}";
                ret = "{\"message\":\"Success\",\"scanners\":[";

                for (ScannerThread i : listThreads) {

                    ret = ret + i.toJson() + ",";
                }
//                        listThreads.get(0).toJson()
                ret = ret.substring(0, ret.length() - 1);
                ret = ret + "]}";
                return ret;
            } else {
                //return listThreads.get(0).getName();  //return ArrayList current Thread
//                ret = "List is null";
                return ret;
            }
        } else {
            return ret;
        }
    }

    public UserInfo getUserInfo(String userName){
        return this.usersLogOn.get(userName);
    }

    public void addOrUpdateUserLogOn(String userName, UserInfo userInfo){
//        System.out.println("UserInfo="+userInfo.getFilter().toString());
        this.usersLogOn.put(userName, userInfo);
    }
}
