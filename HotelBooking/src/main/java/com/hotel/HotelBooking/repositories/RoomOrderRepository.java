package com.hotel.HotelBooking.repositories;

import com.hotel.HotelBooking.dto.RoomOrderReport;
import com.hotel.HotelBooking.entities.RoomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoomOrderRepository extends JpaRepository< RoomOrder,Integer> {


    List<RoomOrder> findByUserId(Integer userId);
    RoomOrder findByOrderId(String orderId);
    @Query("SELECT ro FROM RoomOrder ro JOIN FETCH ro.room WHERE ro.user.id = :userId")
    List<RoomOrder> findByUserIdWithRoom(@Param("userId") Integer userId);

    @Query("SELECT new com.hotel.HotelBooking.dto.RoomOrderReport(ro.room.id, COUNT(ro)) " +
            "FROM RoomOrder ro " +
            "GROUP BY ro.room.id " +
            "ORDER BY COUNT(ro) DESC")
    List<RoomOrderReport> countRoomOrders();

    @Query("SELECT new com.hotel.HotelBooking.dto.RoomOrderReport(ro.orderDate, SUM(ro.totalPrice)) " +
            "FROM RoomOrder ro " +
            "GROUP BY ro.orderDate " +
            "ORDER BY SUM(ro.totalPrice) DESC")
    List<RoomOrderReport> findDailyRevenue();


}
