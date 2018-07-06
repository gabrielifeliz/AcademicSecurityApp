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
        AppRole ordinaryUser = new AppRole("USER");
        roles.save(ordinaryUser);

        AppRole admin = new AppRole("ADMIN");
        roles.save(admin);

        AppUser newUser = new AppUser("user", "password");
        newUser.addRole(ordinaryUser);
        users.save(newUser);
    }
}
