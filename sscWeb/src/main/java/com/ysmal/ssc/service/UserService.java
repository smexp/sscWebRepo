package com.ysmal.ssc.service;

import com.ysmal.ssc.dao.VacancyDAO;
import com.ysmal.ssc.model.UserInfo;
import com.ysmal.ssc.server.ThreadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

//    @Autowired
//    private VacancyDAO vacancyDAOImpl;
//    @Autowired
//    private ThreadManager mainManager;

//   public String setFilter (String userName, String filter){
//       UserInfo ui = mainManager.getUserInfo(userName);
//       ui.setFilter(filter);
//       mainManager.addOrUpdateUserLogOn(userName,ui);
//       return "main";
//   };
//    public void cleanFilter(String userName){
//        UserInfo ui = mainManager.getUserInfo(userName);
//        ui.setFilter("");
//        mainManager.addOrUpdateUserLogOn(userName,ui);
//    }
}
