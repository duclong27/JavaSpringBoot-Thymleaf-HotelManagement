package com.hotel.HotelBooking.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class    OrderAddress  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

<<<<<<< HEAD
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
=======
    private String firstName;

    private String lastName;

    private String email;

    private String mobileNo;

    private String address;

    private String city;

    private String state;

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    private String pincode;


}
