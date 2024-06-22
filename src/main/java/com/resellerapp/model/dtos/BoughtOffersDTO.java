package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.enums.ConditionName;

import java.math.BigDecimal;
import java.util.UUID;

public class BoughtOffersDTO {
    private UUID id;
    private String description;
    private BigDecimal price;

    public BoughtOffersDTO(Offer offer) {
        this.id = offer.getId();
        this.description = offer.getDescription();
        this.price = offer.getPrice();
    }

    public BoughtOffersDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
