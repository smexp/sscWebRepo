/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ysmal.ssc.service;

import com.ysmal.ssc.dao.VacancyDAO;
import com.ysmal.ssc.model.Vacancy;
import com.ysmal.ssc.server.ScannerThread;
import com.ysmal.ssc.server.ThreadManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VacancyService {
    
    @Autowired
    private VacancyDAO vacancyDAOImpl;
    @Autowired
    private ThreadManager mainManager;
    
    @Transactional
    public List<Vacancy> getVacancyRecord(String query, int page, int limit, String sortingField, String typeSorting) {
        int offset;
        if (page == 1) {
            offset=0;
        } else {
            offset = page*limit - limit;
        }
        return vacancyDAOImpl.getVacancyRecord(query, offset, limit, sortingField, typeSorting);
    }
    
    @Transactional
    public Integer getMaxPage (String filter){
        return vacancyDAOImpl.getMaxPage(filter);
    }
    
    public String addThread(String name, String userName){
        
        return mainManager.addScannerThread(name, userName);
        
    }
    
    public String getCurrentThreads(String name){
        return mainManager.getJsonInfoRunningThreads(name);
    }
}
