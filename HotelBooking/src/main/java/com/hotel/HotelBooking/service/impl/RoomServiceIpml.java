package com.hotel.HotelBooking.service.impl;


import com.hotel.HotelBooking.entities.Room;
import com.hotel.HotelBooking.repositories.RoomRepository;
import com.hotel.HotelBooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;


@Service
public class RoomServiceIpml implements RoomService
{
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        System.out.println("Số lượng phòng trong service: " + rooms.size());
        return rooms;
    }


    @Override
    public Boolean deleteRoom(Integer id) {
       Room room = roomRepository.findById(id).orElseThrow(null);
       if (!ObjectUtils.isEmpty(room)){
           roomRepository.delete(room);
           return true;
       }
       return false;
    }


    @Override
    public Room getRoomById(Integer id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) {
            // Kiểm tra trong log nếu room không tìm thấy
             System.out.println("Room not found with id: " + id);
        }
        // Kiểm tra thêm nếu image là null
        if (room.getImage() == null) {
            // Log hoặc xử lý khi không có ảnh
            System.out.println("Room with id " + id + " has no image.");
        }
        return room;
    }


    @Override
    public Room updateRoom(Room room, MultipartFile image) {
        Room dbRoom = getRoomById(room.getId());
        // Nếu có ảnh mới, sử dụng tên của ảnh mới, ngược lại giữ nguyên tên ảnh cũ
        String imageName = image.isEmpty() ? dbRoom.getImage() : image.getOriginalFilename();
        // Cập nhật các thuộc tính của phòng
        dbRoom.setName(room.getName());
        dbRoom.setDescription(room.getDescription());
        dbRoom.setCategory(room.getCategory());
        dbRoom.setStock(room.getStock());
        dbRoom.setPrice(room.getPrice());
        dbRoom.setCapacity(room.getCapacity());
        dbRoom.setImage(imageName); // Cập nhật tên ảnh
        dbRoom.setIsActive(room.getIsActive());
        dbRoom.setDiscount(room.getDiscount());
        Double disocunt = room.getPrice() * (room.getDiscount() / 100.0);
        Double discountPrice = room.getPrice() - disocunt;
        dbRoom.setDiscountPrice(discountPrice);
        // Lưu đối tượng Room đã cập nhật vào cơ sở dữ liệu
        Room updatedRoom = roomRepository.save(dbRoom);
        // Kiểm tra nếu lưu thành công
        if (!ObjectUtils.isEmpty(updatedRoom)) {
            // Nếu có ảnh mới, tiến hành lưu ảnh vào đường dẫn chỉ định
            if (!image.isEmpty()) {
                try {
                    // Đường dẫn thư mục lưu ảnh
                    File saveDir = new ClassPathResource("static/img/HinhAnh/Ecommerce Spring Boot/category_img").getFile();
                    // Tạo đường dẫn đầy đủ để lưu ảnh
                    Path path = Paths.get(saveDir.getAbsolutePath() + File.separator + image.getOriginalFilename());
                    // Copy ảnh vào đường dẫn mới, ghi đè nếu ảnh đã tồn tại
                    Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return dbRoom; // Trả về đối tượng Room đã được cập nhật
        }
        return null;
    }


    @Override
    public List<Room> getAllActiveRooms(String category) {

        if (ObjectUtils.isEmpty(category)) {
          return   roomRepository.findAll();
        } else {
            return roomRepository.findByCategory(category);
        }
    }


    @Override
    public List<Room> searchRoom(String ch) {
        return roomRepository.findByNameContainingIgnoreCase(ch);
    }



}
