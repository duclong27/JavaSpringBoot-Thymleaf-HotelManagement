package com.hotel.HotelBooking.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="rooms3")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

<<<<<<< HEAD
=======

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    @NotBlank(message = "Name is required")
    @Column(name="name",length = 50 )
    private String name;

<<<<<<< HEAD
=======

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    @Column(name = "status")
    private String isActive;

    @Column(name = "description")
    private String description;

    @Column(name="price")
    private Double price;

    @Column(name = "discount")
    private int discount;

    @Column(name = "discountPrice")
    private Double discountPrice;

<<<<<<< HEAD
    @Column(name="capacity")
    private int capacity;

    @Column(name ="image")
    private String image;

    @Column(name = "category")
    private String category;

    @Column(name="stock")
    private int stock;

    @Column(name = "subtotal")
    private Double subTotal;
=======

    @Column(name="capacity")
    private int capacity;


    @Column(name ="image")
    private String image;


    @Column(name = "category")
    private String category;


     @Column(name="stock")
     private int stock;

     @Column(name = "subtotal")
     private Double subTotal;
>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7

    @Override
    public String toString() {
        return "Category{name='" + name + "'}";
    }

}
