package com.resellerapp.controller;

import com.resellerapp.model.dtos.AddOfferDTO;
import com.resellerapp.service.LoggedUser;
import com.resellerapp.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class OfferController {
    private final OfferService offerService;
    private final LoggedUser loggedUser;

    public OfferController(OfferService offerService, LoggedUser loggedUser) {
        this.offerService = offerService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/offers/add")
    public String create(){
        if (!loggedUser.isLogged()){
            return "redirect:/login";
        }
        return "offer-add";
    }

    @ModelAttribute
    public AddOfferDTO addOfferDTO(){
        return new AddOfferDTO();
    }

    @PostMapping("/offers/add")
    public String addOffer(@Valid AddOfferDTO addOfferDTO){
        if (!loggedUser.isLogged()){
            return "redirect:/login";
        }

        if (offerService.create(addOfferDTO)){
            return "redirect:/home";
        }
        return "redirect:/offers/add";
    }

    @GetMapping("/offers/buy/{id}")
    public String buyOffer(@PathVariable("id") UUID id){
        if (!loggedUser.isLogged()){
            return "redirect:/login";
        }

        offerService.buy(id);
        return "redirect:/home";
    }

    @DeleteMapping("/offers/remove/{id}")
    public String removeOffer(@PathVariable UUID id){
        if (!loggedUser.isLogged()){
            return "redirect:/login";
        }
        offerService.remove(id);

        return "redirect:/home";
    }
}
