package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.enums.ConditionName;

import java.math.BigDecimal;
import java.util.UUID;

public class MyOfferDTO {
    private UUID id;
    private ConditionName condition;
    private BigDecimal price;
    private String description;

    public MyOfferDTO() {
    }

    public MyOfferDTO(Offer offer) {
        condition = offer.getCondition().getName();
        price = offer.getPrice();
        description = offer.getDescription();
    }

    public ConditionName getCondition() {
        return condition;
    }

    public void setCondition(ConditionName condition) {
        this.condition = condition;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
