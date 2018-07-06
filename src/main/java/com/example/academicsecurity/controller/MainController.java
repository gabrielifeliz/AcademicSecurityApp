package com.example.academicsecurity.controller;

import com.example.academicsecurity.model.AppRole;
import com.example.academicsecurity.model.AppUser;
import com.example.academicsecurity.repository.AppRoleRepository;
import com.example.academicsecurity.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
public class MainController {

    @Autowired
    AppRoleRepository roles;

    @Autowired
    AppUserRepository users;

    @RequestMapping("/")
    public String index() {
    	return "navbar";
    }
    
    @RequestMapping("/studentorteacher")
    public String studentOrTeacher() {
    	return "linkpage";
    }
    
    @PostConstruct
    public void loadData(){

        AppRole student = new AppRole("STUDENT");
        roles.save(student);

        AppRole teacher = new AppRole("TEACHER");
        roles.save(teacher);

        AppUser studentLogin = new AppUser("student", "pwstudent");
        studentLogin.addRole(student);
        users.save(studentLogin);

        AppUser teacherLogin = new AppUser("teacher", "pwteacher");
        teacherLogin.addRole(teacher);
        users.save(teacherLogin);
    }
}
