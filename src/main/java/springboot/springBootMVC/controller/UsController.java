package springboot.springBootMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import springboot.springBootMVC.dao.UserRepository;
import springboot.springBootMVC.model.User;
import springboot.springBootMVC.service.RoleService;

import java.security.Principal;

@Controller
public class UsController {

    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public UsController(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String log() {
        return "login";
    }

    @GetMapping("/admin")
    public String index(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "index";
    }

    @GetMapping("/userPage")
    public String forUser(ModelMap modelMap, Principal principal) {
        User us = userRepository.findByUsername(principal.getName());
        modelMap.addAttribute("user", us);
        return "userPage";
    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUserC(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
