package com.hotel.HotelBooking.repositories;


import com.hotel.HotelBooking.entities.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddress,Integer> {



}
