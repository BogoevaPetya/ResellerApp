package com.resellerapp.model.dtos;

import com.resellerapp.model.entity.Offer;
import com.resellerapp.model.enums.ConditionName;

import java.math.BigDecimal;
import java.util.UUID;

public class OtherOffersDTO {
    private UUID id;
    private String sellerUsername;
    private String description;
    private ConditionName condition;
    private BigDecimal price;

    public OtherOffersDTO(Offer offer){
        this.id = offer.getId();
        this.sellerUsername = offer.getCreatedBy().getUsername();
        this.description = offer.getDescription();
        this.condition = offer.getCondition().getName();
        this.price = offer.getPrice();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
