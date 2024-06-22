package com.resellerapp.controller;

import com.resellerapp.model.dtos.OfferHomeDTO;
import com.resellerapp.service.LoggedUser;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final OfferService offerService;
    private final LoggedUser loggedUser;

    public HomeController(OfferService offerService, LoggedUser loggedUser) {
        this.offerService = offerService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/")
    public String  index(){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model){
        if (!loggedUser.isLogged()){
            return "redirect:/login";
        }

        OfferHomeDTO offerHomeDTO = offerService.getOffersForHomePage();
        model.addAttribute("offerHomeDTO", offerHomeDTO);

        return "home";
    }
}
