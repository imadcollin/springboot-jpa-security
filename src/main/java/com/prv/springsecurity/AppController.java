package com.prv.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerView(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "index";}
            else {
        model.addAttribute("user", user);
        return "register";}
    }

    @GetMapping("/list_users")
    public String user(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("listOfUser", users);
        return "list_users";
    }

    @PostMapping("/reg")
    public String registration(@Valid User user,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            return "register";}
        else {
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

}
