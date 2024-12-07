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
    @Column(name = "name")
    private String name;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "is_active")
    private String isActive;

    @Override
    public String toString() {
        return "Category{name='" + name + "'}";
    }

}
