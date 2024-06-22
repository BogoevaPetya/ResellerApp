package com.resellerapp.service;

import com.resellerapp.model.dtos.*;
import com.resellerapp.model.entity.Condition;
import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.entity.User;
import com.resellerapp.repository.ConditionRepository;
import com.resellerapp.repository.OfferRepository;
import com.resellerapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final LoggedUser loggedUser;
    private final ConditionRepository conditionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, LoggedUser loggedUser, ConditionRepository conditionRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.loggedUser = loggedUser;
        this.conditionRepository = conditionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public OfferHomeDTO getOffersForHomePage(){
        List<Offer> offers = offerRepository.findAll();

        List<MyOfferDTO> myOffers = new ArrayList<>();
        List<BoughtOffersDTO> boughOffers = new ArrayList<>();
        List<OtherOffersDTO> otherOffers = new ArrayList<>();

        for (int i = 0; i < offers.size(); i++) {
            Offer offer = offers.get(i);

            String loggedUsername = loggedUser.getUsername();

            if (offer.getBoughtBy() == null && offer.getCreatedBy().getUsername().equals(loggedUsername)){
                MyOfferDTO myOfferDTO = modelMapper.map(offer, MyOfferDTO.class);
                myOfferDTO.setCondition(offer.getCondition().getName());
                myOffers.add(myOfferDTO);
            } else if (offer.getBoughtBy() != null && offer.getBoughtBy().getUsername().equals(loggedUsername)){
                BoughtOffersDTO boughtOffersDTO = modelMapper.map(offer, BoughtOffersDTO.class);
                boughOffers.add(new BoughtOffersDTO(offer));
            } else if (offer.getBoughtBy() == null) {
                otherOffers.add(new OtherOffersDTO(offer));
            }
        }

        return new OfferHomeDTO(myOffers, boughOffers, otherOffers);
    }

    public boolean create(AddOfferDTO addOfferDTO){
        Condition condition = conditionRepository.findByName(addOfferDTO.getCondition());
        Optional<User> user = userRepository.findByUsername(loggedUser.getUsername());
        if (condition != null && user.isPresent()){
            Offer offer = new Offer();

            offer.setDescription(addOfferDTO.getDescription());
            offer.setCreatedBy(user.get());
            offer.setCondition(condition);
            offer.setPrice(addOfferDTO.getPrice());

            offerRepository.save(offer);
            return true;
        }
        return false;
    }

    public void buy(UUID id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);

        if (optionalOffer.isPresent()){
            User user = userRepository.findByUsername(loggedUser.getUsername()).get();
            Offer offer = optionalOffer.get();

            offer.setBoughtBy(user);
            offerRepository.save(offer);
        }
    }

    public void remove(UUID offerId) {
        Optional<Offer> optionalOffer = this.offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()){
            return;
        }

        Offer offer = optionalOffer.get();

        offerRepository.delete(offer);
    }
}
