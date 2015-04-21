
package com.ysmal.ssc.server.Abstract;

import com.ysmal.ssc.model.Vacancy;
import java.util.List;

public interface AbstractStorage {
    void addOrUpdateVacanciesList(List<Vacancy> vacancies);
}
