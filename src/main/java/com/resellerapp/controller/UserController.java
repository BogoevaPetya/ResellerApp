package com.resellerapp.controller;

import com.resellerapp.model.dtos.UserLoginDTO;
import com.resellerapp.model.dtos.UserRegisterDTO;
import com.resellerapp.service.LoggedUser;
import com.resellerapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final LoggedUser loggedUser;

    public UserController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute("userLoginDTO")
    public  UserLoginDTO initUserLoginDTO(){
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public String login(){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDTO userLoginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
            return "redirect:/login";
        }

        boolean isLogged = userService.login(userLoginDTO);
        if (isLogged){
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("passwordOrEmailMismatch", true);
            return "redirect:/login";
        }

    }

    @GetMapping("/register")
    public String register(){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }
        return "register";
    }

    @ModelAttribute("userRegisterDTO")
    public  UserRegisterDTO initUserRegisterDTO(){
        return new UserRegisterDTO();
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if (loggedUser.isLogged()){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors() || !userService.register(userRegisterDTO)){
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){
        if (!loggedUser.isLogged()){
            return "redirect:/home";
        }

        userService.logout();
        return "redirect:/";
    }
}
