package com.hotel.HotelBooking.service;


import com.hotel.HotelBooking.dto.RoomOrderReport;
import com.hotel.HotelBooking.entities.OrderRequest;
import com.hotel.HotelBooking.entities.RoomOrder;
import org.springframework.data.domain.Page;

import java.util.List;


public interface RoomOrderService {

   public void saveRoomOrder(Integer userId, OrderRequest orderRequest) throws Exception ;
   public List<RoomOrder> getAllRoomOrders();
   public RoomOrder getOrdersByOrderId(String orderId);
   public List<RoomOrder> getOrdersByUser(Integer userId);
   public RoomOrder updateRoomOrderStatus(Integer id, String status);
   public List<RoomOrder> getAllOrders();
   public Page<RoomOrder> getAllOrdersPagination(Integer pageNo, Integer pageSize);



}
