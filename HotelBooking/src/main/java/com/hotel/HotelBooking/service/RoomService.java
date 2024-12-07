package com.hotel.HotelBooking.service;


import com.hotel.HotelBooking.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Service
public interface RoomService {

    public Room saveRoom(Room room);

    public List<Room> getAllRooms();

    public Boolean deleteRoom(Integer id);

    public Room getRoomById(Integer id);

    public Room updateRoom(Room room, MultipartFile file);

    public List<Room> getAllActiveRooms(String category);


    public List<Room> searchRoom(String ch);



}
