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

    private String name;

    private String imageName;

    private String isActive;

    @Override
    public String toString() {
        return "Category{name='" + name + "'}";
    }

}
