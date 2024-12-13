package com.hotel.HotelBooking.service.impl;

import com.hotel.HotelBooking.entities.BookingCart;
import com.hotel.HotelBooking.entities.Room;
import com.hotel.HotelBooking.entities.User;
import com.hotel.HotelBooking.repositories.BookingCartRepository;
import com.hotel.HotelBooking.repositories.RoomRepository;
import com.hotel.HotelBooking.repositories.UserRepository;
import com.hotel.HotelBooking.service.BookingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookingCartServiceImpl implements BookingCartService {



    @Autowired
    private BookingCartRepository bookingCartRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<BookingCart> loadAllBookingCart() {
        return bookingCartRepository.findAll();
    }

    @Override
    public BookingCart saveBookingCart(int roomId, int userId, String checkInDate, String checkOutDate, String services) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        BookingCart bookingCartStatus = bookingCartRepository.findByRoomIdAndUserId(roomId, userId);
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);
        BookingCart bookingCart = null;
        if (ObjectUtils.isEmpty(bookingCartStatus)) {
            // Nếu chưa có giỏ hàng, tạo mới
            bookingCart = new BookingCart();
            bookingCart.setRoom(room);
            bookingCart.setUser(user);
            bookingCart.setCheckInDate(checkIn);
            bookingCart.setCheckOutDate(checkOut);
            bookingCart.setServices(services);
            long duration = ChronoUnit.DAYS.between(checkIn, checkOut);
            bookingCart.setStayDuration((int) duration);
            bookingCart.setTotalPrice(room.getSubTotal());
        } else {
            // Nếu đã có giỏ hàng, cập nhật thông tin
            bookingCart = bookingCartStatus;
            bookingCart.setCheckInDate(checkIn);
            bookingCart.setCheckOutDate(checkOut);
            bookingCart.setServices(services);
            bookingCart.setStayDuration(bookingCart.getStayDuration());
            bookingCart.setTotalPrice(bookingCart.getTotalPrice());
            bookingCart.setTotalOrderPrice(bookingCart.getTotalOrderPrice());
        }
        return bookingCartRepository.save(bookingCart);
    }


    @Override
    public Integer getCountBookingCart(Integer userId) {
        Integer countByUserId = bookingCartRepository.countByUserId(userId);
        return countByUserId;
    }


    @Override
    public List<BookingCart> getBookingCartsByUser(Integer userId, String checkInDate, String checkOutDate) {
        List<BookingCart> bookingCarts = bookingCartRepository.findByUserId(userId);
        System.out.println("BookingCart: " + bookingCarts.size());
        Double totalOrderPrice = 0.0;
        List<BookingCart> updateBookingCarts = new ArrayList<>();
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in date and check-out date must not be null");
        }
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);
        long stayDuration = java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut) + 1 ;
        for (BookingCart c : bookingCarts) {
            Double totalPrice = c.getRoom().getDiscountPrice() * stayDuration;
            c.setTotalPrice(totalPrice);
            System.out.println("totalPrice : " + totalPrice);
            totalOrderPrice = totalOrderPrice + totalPrice;
            c.setTotalOrderPrice(totalOrderPrice);
            System.out.println("totalOrderPrice : " + totalOrderPrice);
            updateBookingCarts.add(c);
            bookingCartRepository.save(c);
            System.out.println("BookingCart: " + bookingCarts.size());
        }
        return updateBookingCarts;
    }
}
