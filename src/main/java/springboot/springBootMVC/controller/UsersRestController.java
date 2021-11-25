package springboot.springBootMVC.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
//import springboot.springBootMVC.dto.UserDTO;
//import springboot.springBootMVC.mapper.UserMapper;
import springboot.springBootMVC.dao.UserRepository;
import springboot.springBootMVC.dto.UserDTO;
import springboot.springBootMVC.model.Role;
import springboot.springBootMVC.model.User;
import springboot.springBootMVC.service.RoleService;
import springboot.springBootMVC.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/")
public class UsersRestController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public UsersRestController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    //Get all users
    @GetMapping("admin/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //Create a new user
//    @SneakyThrows //lombok  бросание проверяемых исключений без их объявления в throws метода, без неё javassist.NotFoundException
    @PostMapping("admin")
    public ResponseEntity<User> newUser(@RequestBody UserDTO userDTO) throws NotFoundException{
        User user = new User(userDTO);
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoleNames()) {
            roles.add(roleService.getByName(roleName));
        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET all roles
    @GetMapping("admin/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }


    //Getting a user by Id
    @GetMapping("admin/{id}")
    public ResponseEntity<User> getUserId(@PathVariable(name = "id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //User update
//    @SneakyThrows //lombok  бросание проверяемых исключений без их объявления в throws метода, без неё javassist.NotFoundException
    @PutMapping("admin")
    public ResponseEntity<User> editUser(@RequestBody UserDTO userDTO) throws NotFoundException{
        User user = new User(userDTO);
        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoleNames()) {
            roles.add(roleService.getByName(roleName));
        }
        user.setRoles(roles);
        userService.edit(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete user by ID
    @DeleteMapping("admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}