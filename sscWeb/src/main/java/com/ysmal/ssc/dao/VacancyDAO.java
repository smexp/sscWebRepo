/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ysmal.ssc.dao;

import com.ysmal.ssc.model.Vacancy;
import java.util.List;

public interface VacancyDAO {
    public List<Vacancy> getVacancyRecord(String query, int offset, int limit, String sortingField, String typeSorting);
    public Integer getMaxPage(String filter);
    
}

