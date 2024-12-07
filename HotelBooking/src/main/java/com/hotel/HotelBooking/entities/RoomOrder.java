package com.hotel.HotelBooking.entities;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="room_order")
public class RoomOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

<<<<<<< HEAD
    @Column(name = "orderId")
    private String orderId;

    @Column(name = "order_date")
=======
    private String orderId;

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

<<<<<<< HEAD
=======

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

<<<<<<< HEAD
    @Column(name = "service")
    private String service;
=======
        private String service;
>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7

    @ManyToOne
    private User user;

<<<<<<< HEAD
    @Column(name = "status")
    private String status;

    @Column(name = "payment_type")
=======
    private String status;

>>>>>>> aa46548bc9d431e305071582f51aedf8bc376dc7
    private String paymentType;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderAddress orderAddress;



}
