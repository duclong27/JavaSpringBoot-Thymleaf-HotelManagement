package com.hotel.HotelBooking.repositories;


import com.hotel.HotelBooking.entities.Room;
import com.hotel.HotelBooking.entities.RoomOrder;
import com.hotel.HotelBooking.dto.RoomOrderReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {

    public boolean existsByName(String name);


    public List<Room> findByIsActiveTrue();

    List<Room> findByCategory(String category);

    Optional<Room> findById(Integer roomId);

//    @Query("SELECT r FROM Room r WHERE r.category = :category")
//    List<Room> findByCategory(@Param("category") String category);


//    List<Room> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch,String ch2);

    List<Room> findByNameContainingIgnoreCase(String name);


    // Truy vấn đếm số phòng được đặt theo room_id
    @Query("SELECT ro.room.id, COUNT(ro) FROM RoomOrder ro GROUP BY ro.room.id ORDER BY COUNT(ro) DESC")
    List<Object[]> countRoomOrders();




}
