package com.hotel.HotelBooking.service;


import com.hotel.HotelBooking.entities.BookingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


public interface BookingCartService {

    public List<BookingCart> loadAllBookingCart();

    public BookingCart saveBookingCart(int roomId, int userId,  String checkInDate, String checkOutDate, String services);

    public Integer getCountBookingCart(Integer userId);

    public List<BookingCart> getBookingCartsByUser(Integer userId, String checkInDate, String checkOutDate);

}
