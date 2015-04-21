package com.ysmal.ssc.server.Impl;

import com.ysmal.ssc.server.Abstract.AbstractScannerThread;
import com.ysmal.ssc.server.Abstract.AbstractSource;
import com.ysmal.ssc.server.Abstract.AbstractStorage;
import com.ysmal.ssc.server.ScannerThread;

public class ScanHH extends ScannerThread {

    public ScanHH(AbstractSource source, AbstractStorage storage, Long timeLag, String name, String userName) {
        this.source = source;
        this.storage = storage;
        this.stopThread = false;
        this.timeLag = timeLag;
        this.name = name;
        this.userName = userName;
        this.workCycleCount = 0;
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + 
                this.name + "\",\"userName\":\"" + 
                this.userName + "\",\"state\":\""+
                this.getState().toString()+"\"}";
        
    }

}
