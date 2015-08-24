package com.ysmal.ssc.controller;

import com.ysmal.ssc.dao.VacancyDAO;
import com.ysmal.ssc.dao.VacancyDAOImpl;
import com.ysmal.ssc.model.Vacancy;

import com.ysmal.ssc.service.VacancyService;
import com.ysmal.ssc.util.exception.FindException;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class MainController {

    @Autowired
    private VacancyService vacancyService;

    @RequestMapping(method = RequestMethod.GET, value="jsn")
	public @ResponseBody List<Vacancy> getInJSON(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "limit") int limit,
            @RequestParam(value = "sorting") String sorting,
            @RequestParam(value = "typeSorting") String typeSorting
            ) {
        return vacancyService.getVacancyRecord("true", page, limit, sorting, typeSorting);
    }

    @RequestMapping(method = RequestMethod.GET, value = "main")
    public String mainForm(Model model) {
        model.addAttribute("vacancyList", vacancyService.getVacancyRecord("true", 1, 10, "updatedate", "desc"));
        model.addAttribute("maxPage", vacancyService.getMaxPage().intValue());
        return "main";
    }
    
    @RequestMapping(method = RequestMethod.GET, value="addThread")
	public @ResponseBody String addThread(
            @RequestParam(value = "name") String name,
                @RequestParam(value = "userName") String userName
            ) {
        return vacancyService.addThread(name, userName);
    }
    
    @RequestMapping(method = RequestMethod.GET, value="getThreads")
	public @ResponseBody String getThreads(
            @RequestParam(value = "name") String name
                
            ) {
        return vacancyService.getCurrentThreads(name);
    }
    
}
