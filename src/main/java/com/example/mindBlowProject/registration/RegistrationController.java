package com.example.mindBlowProject.registration;

import com.example.mindBlowProject.Repositories.UserRepository;
import com.example.mindBlowProject.entities.AppUserRole;
import com.example.mindBlowProject.entities.User;
import com.example.mindBlowProject.entities.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final EmailValidator emailValidator;

    public RegistrationController(UserRepository userRepository, UserService userService, EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.emailValidator = emailValidator;
    }

    @GetMapping("/v1/registration")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register-user";
    }

    @PostMapping("/v1/registration")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register-user";
        }

        boolean isValidEmail = emailValidator.test(user.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }

        user.setAppUserRole(AppUserRole.USER);
        userService.signUpUser(user);
        userRepository.save(user);
        model.addAttribute("user", user);
        return "redirect:/users";
    }
}
