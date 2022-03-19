package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.entity.User;
import run.itlife.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap){
        return "login";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap){
        return "register";
    }

}
