

package com.ysmal.ssc.server.Impl;

import com.ysmal.ssc.server.Abstract.AbstractStorage;
import com.ysmal.ssc.model.Vacancy;
import java.util.Iterator;
import java.util.List;


public class StorageSOUT implements AbstractStorage{

    @Override
    public void addOrUpdateVacanciesList(List<Vacancy> vacancies) {
        for (Iterator<Vacancy> it = vacancies.iterator(); it.hasNext();) {
            Vacancy r = it.next();
            System.out.println( "Vacancy id="+r.getId()+
                                " name="+r.getName()+
                                " salary="+r.getSalary()+
                                " company="+r.getCompanyName()+
                                " date="+r.getStringDate()
                              );
        }
    }
}
