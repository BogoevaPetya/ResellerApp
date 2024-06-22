package com.resellerapp.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false )
    private String email;

    @OneToMany(mappedBy = "createdBy")
    private Set<Offer> offers;

    @OneToMany(mappedBy = "boughtBy")
    private Set<Offer> boughOffers;

    public User(){
        this.offers = new HashSet<>();
        this.boughOffers = new HashSet<>();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    public Set<Offer> getBoughOffers() {
        return boughOffers;
    }

    public void setBoughOffers(Set<Offer> boughOffers) {
        this.boughOffers = boughOffers;
    }

}
