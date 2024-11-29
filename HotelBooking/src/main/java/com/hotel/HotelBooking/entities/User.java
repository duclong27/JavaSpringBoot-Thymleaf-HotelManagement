package com.hotel.HotelBooking.entities;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="users2")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="fullname")
    private String fullName;


    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="email")
    private String email;


    @Column(name="address")
    private String address;


    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;





    @Column(name="created_at")
    private LocalDateTime createdAt;





    @Column(name="facebook_account_id")
    private int facebookAccountId;



    @Column(name="role")
    private String role;


    @Column(name="password")
    private String password;


    @Column(name="profile_image")
    private String profileImage;



    @Column(name="isEnable")
    private Boolean isEnable;



    @Column(name="account_non_locked")
    private Boolean accountNonLocked;



    @Column(name="failed_attempt")
    private Integer failedAttempt;



    @Column(name="lock_time")
    private Date lockTime;



    @Column(name="reset_token")
    private String resetToken;





}
