package com.prv.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/list_users")
    public String user(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("listOfUser", users);
        return "list_users";
    }

    @PostMapping("/reg")
    public String registration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passCode = encoder.encode(user.getPassword());
        user.setPassword(passCode);
        System.out.println(user.getPassword());
        System.out.println(passCode);

        userRepository.save(user);
        System.out.println(user);
        return "done";
    }

}
