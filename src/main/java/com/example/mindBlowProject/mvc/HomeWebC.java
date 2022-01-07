package com.example.mindBlowProject.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebC {                    /*  Mapping host:port/ --to--> templates/landing_page */
    @GetMapping("/")
        public String home(){
        return "landing_page";
    }

    @GetMapping("/register")
    public String register()
    {
        return "userRegistration";
    }

}

