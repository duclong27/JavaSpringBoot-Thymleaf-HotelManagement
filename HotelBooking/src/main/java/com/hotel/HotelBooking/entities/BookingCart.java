package com.hotel.HotelBooking.entities;


import jakarta.persistence.*;
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
@Table(name="booking_cart2")
public class BookingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @Column(name = "stay_duration")
    private Integer stayDuration;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_order_price")
    private Double totalOrderPrice;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "services")
    private String services;

}
