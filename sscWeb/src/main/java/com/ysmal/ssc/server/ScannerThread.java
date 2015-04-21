

package com.ysmal.ssc.server;

import com.ysmal.ssc.server.Abstract.AbstractScannerThread;
import java.util.Date;


public class ScannerThread extends AbstractScannerThread{

    protected String name;
    protected Integer countWorking;
    protected Date startTime;
    protected String userName;
    protected String searchWord;
    

public String toJson(){
    return "{\"name\":\""+this.name+
            "\",\"state\":\""+this.getState().toString()+
            "\",\"startTime\":\""+this.startTime.toString()+
            "\",\"count\":\"" + this.workCycleCount +
            "\",\"triggeredTime\":\"" + this.triggeredTime +
            "\"}";
}
}
