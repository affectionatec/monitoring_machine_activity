package uk.ac.cardiff.mma.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cardiff.mma.application.role.entity.Role_dto;
import uk.ac.cardiff.mma.application.role.repositories.Role_repository;
import uk.ac.cardiff.mma.application.service.Role_service;

@Controller
public class Role_controller {

    @Autowired
    private Role_service role_service;

    @Autowired
    private Role_repository user_repository;

    // display list of users
    @GetMapping("/superadmin/user_list")
    public String viewHomePage(Model model) {
        model.addAttribute("list_users", role_service.getAllUsers());

        return "/admin/user_list";
    }


    @GetMapping("/superadmin/show_new_user")
    public String show_new_user(Model model) {
        // create model attribute to bind form data
        Role_dto user = new Role_dto();
        model.addAttribute("user", user);
        return "/admin/add_user";
    }

    @PostMapping("/superadmin/save_user")
    public String save_user(@ModelAttribute("user") Role_dto user) {
        String rawPassword = user.getPassword();
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        role_service.saveUser(user);
        return "redirect:/superadmin/user_list";


    }

    @GetMapping("/superadmin/update_user/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        // get user from the service
        Role_dto user = role_service.getUserById(id);

        // set users as a model attribute to pre-populate the form
        model.addAttribute("user", user);
        return "/admin/update_user";
    }

    @GetMapping("/superadmin/delete_user/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        // call delete user method
        this.role_service.deleteUserById(id);
        return "/admin/user_list";
    }


    @GetMapping("/modify_password/{username}")
    public String modify_password(@PathVariable(value = "username") String username, Model model) {
        Role_dto user = role_service.getUserByUsername(username);
        model.addAttribute("password_modify", user);
        return "password_modify";
    }

    @PostMapping("/save_password")
    public String save_password(@ModelAttribute("password_modify") Role_dto user) {
        String rawPassword = user.getPassword();
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        role_service.saveUser(user);
        return "/password_modify";


    }
}


