package com.example.mindBlowProject.mvc;

import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeWebC {


   private final UserRepository userRepository;

    public HomeWebC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*  Mapping host:port/ --to--> templates/landing_page */
    @GetMapping("/")
        public String home(){
        return "landing_page";
    }

    @GetMapping("/nodata/{id}")
    private String nodata(@PathVariable("id") String id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user",user);

        return "nodataPage";
    }


}

