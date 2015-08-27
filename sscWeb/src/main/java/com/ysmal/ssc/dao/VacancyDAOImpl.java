
package com.ysmal.ssc.dao;


import com.ysmal.ssc.model.Vacancy;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Transactional
public class VacancyDAOImpl implements VacancyDAO {
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override

    public List<Vacancy> getVacancyRecord(String query, int offset, int limit, String sortingField, String typeSorting) {
        List<Vacancy> vacancyList = new ArrayList<>();
        String queryHQL;
        if (query.equalsIgnoreCase("true")) {
            queryHQL = "from Vacancy as vacancy order by vacancy." + sortingField + " " + typeSorting;
        }
        else {
            queryHQL = "from Vacancy as vacancy where " + query + " order by vacancy." + sortingField + " " + typeSorting;
        };
 //       System.out.println("QUERY= "+queryHQL);
        vacancyList = sessionFactory.getCurrentSession().createQuery(queryHQL).setFirstResult(offset).setMaxResults(limit).list();
//        System.out.println("vacancy List result="+vacancyList);
        if (vacancyList.size() > 0) {
            return vacancyList;
        } else {
            Vacancy nullVacancy = new Vacancy();
            vacancyList.add(0,nullVacancy);
            return vacancyList;
        }
    }
    
    @Override
    public Integer getMaxPage(){
        Integer count = (Integer)sessionFactory.getCurrentSession().createQuery("from Vacancy").list().size();
        count = (count/15)+1;
        return count;
    }
    
}
