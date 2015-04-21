package com.ysmal.ssc.server.Abstract;

import com.ysmal.ssc.model.Vacancy;
import java.util.Date;
import java.util.List;

public abstract class AbstractScannerThread extends Thread {

    protected AbstractSource source;
    protected AbstractStorage storage;
    protected List<Vacancy> vacanciesList;
    protected long timeLag;
    protected boolean stopThread;
    protected int workCycleCount;
    protected Date triggeredTime;

    @Override
    public void run() {
        do {
            this.vacanciesList = source.getVacanciesList();
            if (this.vacanciesList != null && this.vacanciesList.size() > 0) {
                storage.addOrUpdateVacanciesList(vacanciesList);
            }
            this.vacanciesList = null;
            this.workCycleCount = this.workCycleCount + 1;

            synchronized (this) {
                try {
                    this.triggeredTime = new Date();
                    Thread.currentThread().wait(this.timeLag);
                } 
                catch (InterruptedException ex) 
                {
                }
            }

        } while (!this.isStopThread() || this.workCycleCount>500);
        System.out.println("STOP Abstract thread");
    }

// getters and setters
    public AbstractSource getSource() {
        return source;
    }

    public AbstractStorage getStorage() {
        return storage;
    }

    public List<Vacancy> getVacanciesList() {
        return vacanciesList;
    }

    public long getTimeLag() {
        return timeLag;
    }


    public boolean isStopThread() {
        return stopThread;
    }

    public void setSource(AbstractSource source) {
        this.source = source;
    }

    public void setStorage(AbstractStorage storage) {
        this.storage = storage;
    }

    public void setVacanciesList(List<Vacancy> vacanciesList) {
        this.vacanciesList = vacanciesList;
    }

    public void setTimeLag(long timeLag) {
        this.timeLag = timeLag;
    }

    public void setStopThread(boolean stopThread) {
        this.stopThread = stopThread;
    }

    public int getWorkCycleCount() {
        return workCycleCount;
    }

    public void setWorkCycleCount(int workCycleCount) {
        this.workCycleCount = workCycleCount;
    }

    
}
