package com.hotel.HotelBooking.entities;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="categories1")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
<<<<<<< HEAD
    @Column(name = "name")
    private String name;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "is_active")
=======

    private String name;

    private String imageName;

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    private String isActive;

    @Override
    public String toString() {
        return "Category{name='" + name + "'}";
    }

}
