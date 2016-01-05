package com.ysmal.ssc.server.Impl;

import com.ysmal.ssc.server.Abstract.AbstractStorage;
import com.ysmal.ssc.server.HibernateUtil;
import com.ysmal.ssc.model.ChangedAtribute;
import com.ysmal.ssc.model.Vacancy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StorageDB implements AbstractStorage {

    protected List<ChangedAtribute> historyList;

    @Override
    public void addOrUpdateVacanciesList(List<Vacancy> vacancies) {

        historyList = new ArrayList();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();

        Calendar cal = Calendar.getInstance();
        s.beginTransaction();
        for (Vacancy r : vacancies) {
            Vacancy rInBase = (Vacancy) s.get(Vacancy.class, r.getId());
            if (rInBase != null) {

                s.update(getMappedVacancy(rInBase, r, cal));
                if (!historyList.isEmpty())
                {
//                  SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                    Session h = sessionFactory.openSession();

                    for (ChangedAtribute att : historyList) {
                        System.out.println("Save history " + att);
                        h.beginTransaction();
                        h.save(att);
                        h.getTransaction().commit();
                    }
                    historyList.clear();
                    h.close();
                }
            } else {
                r.setCreatedate(cal);
                r.setUpdatedate(cal);
                s.save(r);
            }
        }
        s.getTransaction().commit();
    }

    private SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    private Vacancy getMappedVacancy(Vacancy rInBase, Vacancy r, Calendar cal) {
        boolean isChangedAtributes = false;
        // make compare attributes
        ChangedAtribute recordHistory = new ChangedAtribute();
        if (rInBase.getAddress() != null && !rInBase.getAddress().equals(r.getAddress())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("address");
            recordHistory.setOldValue(rInBase.getAddress());
            recordHistory.setNewValue(r.getAddress());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;

            rInBase.setAddress(r.getAddress());

        }
        if (rInBase.getCompanyName() != null && !rInBase.getCompanyName().equals(r.getCompanyName())) {
            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("companyname");
            recordHistory.setOldValue(rInBase.getCompanyName());
            recordHistory.setNewValue(r.getCompanyName());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;

            rInBase.setCompanyName(r.getCompanyName());

        }
        if (rInBase.getLocation() != null && !rInBase.getLocation().equals(r.getLocation())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("location");
            recordHistory.setOldValue(rInBase.getLocation());
            recordHistory.setNewValue(r.getLocation());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setLocation(r.getLocation());

        }
        if (rInBase.getName() != null && !rInBase.getName().equals(r.getName())) {
            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("name");
            recordHistory.setOldValue(rInBase.getName());
            recordHistory.setNewValue(r.getName());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setName(r.getName());
        }
        if (rInBase.getRef() != null && !rInBase.getRef().equals(r.getRef())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("ref");
            recordHistory.setOldValue(rInBase.getRef());
            recordHistory.setNewValue(r.getRef());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setRef(r.getRef());

        }
        if (rInBase.getSalary() != null && !rInBase.getSalary().equals(r.getSalary())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("salary");
            recordHistory.setOldValue(rInBase.getSalary());
            recordHistory.setNewValue(r.getSalary());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setSalary(r.getSalary());

        }
        if (rInBase.getSearchWord() != null && !rInBase.getSearchWord().equals(r.getSearchWord())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("searchword");
            recordHistory.setOldValue(rInBase.getSearchWord());
            recordHistory.setNewValue(r.getSearchWord());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setSearchWord(r.getSearchWord());

        }
        if (rInBase.getStringDate() != null && !rInBase.getStringDate().equals(r.getStringDate())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("stringdate");
            recordHistory.setOldValue(rInBase.getStringDate());
            recordHistory.setNewValue(r.getStringDate());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setStringDate(r.getStringDate());

        }
        if (rInBase.getVacRef() != null && !rInBase.getVacRef().equals(r.getVacRef())) {

            recordHistory.setIdRecord(rInBase.getId());
            recordHistory.setAtributeName("vacref");
            recordHistory.setOldValue(rInBase.getVacRef());
            recordHistory.setNewValue(r.getVacRef());
            recordHistory.setCreatedate(cal);

            historyList.add(recordHistory);
            isChangedAtributes = true;
            rInBase.setVacRef(r.getVacRef());

        }

        if (isChangedAtributes) {
            rInBase.setUpdatedate(cal);
        }
        recordHistory = null;
        return rInBase;
    }
}
