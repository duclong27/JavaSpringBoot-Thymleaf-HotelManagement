package com.hotel.HotelBooking.repositories;


import com.hotel.HotelBooking.entities.BookingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCartRepository extends JpaRepository<BookingCart,Integer> {


    public BookingCart findByRoomIdAndUserId(Integer roomId, Integer userId);

    public List<BookingCart> findByUserId(int id);

    public Integer countByUserId(Integer userId);
}
