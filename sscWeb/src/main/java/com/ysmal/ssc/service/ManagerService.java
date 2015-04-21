
package com.ysmal.ssc.service;

import com.ysmal.ssc.server.ThreadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    
    @Autowired
    private ThreadManager mainManager;

    public String startNewScanner (){
        String STATUS = "Success";
        return STATUS;
    }
}
