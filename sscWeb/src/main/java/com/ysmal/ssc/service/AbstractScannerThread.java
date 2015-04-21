
package com.ysmal.ssc.service;

import com.ysmal.ssc.model.ChangedAtribute;
import com.ysmal.ssc.model.Vacancy;
import java.util.List;

public abstract class AbstractScannerThread extends Thread {
    
    protected String url;
    protected List<Vacancy> vacancyList;
    protected long timeLag;
    protected boolean stopThread;
    protected List<ChangedAtribute> historyList;

    abstract List<Vacancy> getVacancies(String url);

    abstract void saveOrUpdateVacanciesFromList(List<Vacancy> vacancyList);

    public void setStopThread(boolean stopThread) {
        this.stopThread = stopThread;
    }

    public boolean isStopThread() {
        return stopThread;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }
}
