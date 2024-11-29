package com.hotel.HotelBooking.service.impl;


import com.hotel.HotelBooking.dto.RoomOrderReport;
import com.hotel.HotelBooking.entities.*;
import com.hotel.HotelBooking.repositories.BookingCartRepository;
import com.hotel.HotelBooking.repositories.RoomOrderRepository;
import com.hotel.HotelBooking.repositories.RoomRepository;
import com.hotel.HotelBooking.repositories.UserRepository;
import com.hotel.HotelBooking.service.RoomOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class RoomOrderServiceImpl implements RoomOrderService {



    @Autowired
    private RoomOrderRepository roomOrderRepository;



    @Autowired
    private BookingCartRepository bookingCartRepository;




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;


//    @Override
//    public void saveRoomOrder(Integer userId, OrderRequest orderRequest) throws Exception {
//        List<BookingCart> bookingCarts = bookingCartRepository.findByUserId(userId);
//
//        for(BookingCart bookingCart : bookingCarts ){
//
//
//             RoomOrder roomOrder= new RoomOrder();
//             roomOrder.setOrderId(UUID.randomUUID().toString());
//             roomOrder.setOrderDate(LocalDate.now());
//             roomOrder.setCheckInDate(bookingCart.getCheckInDate());
//             roomOrder.setCheckOutDate(bookingCart.getCheckOutDate());
//             roomOrder.setService(bookingCart.getServices());
//             roomOrder.setPaymentType(orderRequest.getPaymentType());
//             roomOrder.setStatus(OrderStatus.IN_PROGRESS.getName());
//             roomOrder.setTotalPrice(bookingCart.getTotalPrice());
//
//
//
//
//
//
//
//             OrderAddress orderAddress = new OrderAddress();
//             OrderAddress address = new OrderAddress();
//             address.setFirstName(orderRequest.getFirstName());
//             address.setLastName(orderRequest.getLastName());
//             address.setEmail(orderRequest.getEmail());
//             address.setMobileNo(orderRequest.getMobileNo());
//             address.setAddress(orderRequest.getAddress());
//             address.setCity(orderRequest.getCity());
//             address.setState(orderRequest.getState());
//             address.setPincode(orderRequest.getPincode());
//
//            roomOrder.setOrderAddress(address);
//            User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
//            roomOrder.setUser(user);
//
//
//            RoomOrder saveOrder = roomOrderRepository.save(roomOrder);
//
//        }
//
//
//        }


    @Override
    public void saveRoomOrder(Integer userId, OrderRequest orderRequest) throws Exception {
        // Lấy danh sách giỏ hàng của người dùng
        List<BookingCart> bookingCarts = bookingCartRepository.findByUserId(userId);

        // Lặp qua từng giỏ hàng
        for (BookingCart bookingCart : bookingCarts) {
            // Tìm thông tin Room dựa trên roomId từ BookingCart
            Room room = roomRepository.findById(bookingCart.getRoom().getId())
                    .orElseThrow(() -> new Exception("Room not found with ID: " + bookingCart.getRoom().getId()));

            // Tạo RoomOrder
            RoomOrder roomOrder = new RoomOrder();
            roomOrder.setOrderId(UUID.randomUUID().toString());
            roomOrder.setOrderDate(LocalDate.now());
            roomOrder.setCheckInDate(bookingCart.getCheckInDate());
            roomOrder.setCheckOutDate(bookingCart.getCheckOutDate());
            roomOrder.setService(bookingCart.getServices());
            roomOrder.setPaymentType(orderRequest.getPaymentType());
            roomOrder.setStatus(OrderStatus.IN_PROGRESS.getName());
            roomOrder.setTotalPrice(bookingCart.getTotalPrice());
            roomOrder.setRoom(room); // Gắn Room vào RoomOrder

            // Tạo và gắn OrderAddress
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setFirstName(orderRequest.getFirstName());
            orderAddress.setLastName(orderRequest.getLastName());
            orderAddress.setEmail(orderRequest.getEmail());
            orderAddress.setMobileNo(orderRequest.getMobileNo());
            orderAddress.setAddress(orderRequest.getAddress());
            orderAddress.setCity(orderRequest.getCity());
            orderAddress.setState(orderRequest.getState());
            orderAddress.setPincode(orderRequest.getPincode());
            roomOrder.setOrderAddress(orderAddress);

            // Gắn thông tin người dùng
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new Exception("User not found with ID: " + userId));
            roomOrder.setUser(user);

            // Lưu RoomOrder vào CSDL
            roomOrderRepository.save(roomOrder);
        }
    }





    @Override
    public List<RoomOrder> getAllRoomOrders() {
        return roomOrderRepository.findAll();
    }

//    @Override
//    public List<RoomOrder> getOrdersByUser(Integer userId) {
//
//        List<RoomOrder>  roomOrders = roomOrderRepository.findByUserId(userId);
//        return  roomOrders ;
//    }

    @Override
    public RoomOrder getOrdersByOrderId(String orderId) {
        return roomOrderRepository.findByOrderId(orderId);
    }
    @Override
    public List<RoomOrder> getOrdersByUser(Integer userId) {
        return roomOrderRepository.findByUserIdWithRoom(userId);
    }

    @Override
    public RoomOrder updateRoomOrderStatus(Integer id, String status) {
        Optional<RoomOrder> findById = roomOrderRepository.findById(id);
        if (findById.isPresent()) {
            RoomOrder roomOrder = findById.get();
            roomOrder.setStatus(status);
            RoomOrder updateOrder = roomOrderRepository.save(roomOrder);
            return updateOrder;
        }
        return null;
    }


    @Override
    public List<RoomOrder> getAllOrders() {
        return roomOrderRepository.findAll();
    }

    @Override
    public Page<RoomOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return roomOrderRepository.findAll(pageable);
    }






}
