package com.hotel.HotelBooking.controller;


import com.hotel.HotelBooking.entities.Room;
import com.hotel.HotelBooking.service.BookingCartService;
import com.hotel.HotelBooking.service.RoomService;

import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.hotel.HotelBooking.entities.Category;

import com.hotel.HotelBooking.entities.User;
import com.hotel.HotelBooking.service.CategoryService;
import com.hotel.HotelBooking.service.UserService;
import jakarta.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class HomeController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingCartService bookingCartService;
    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user= userService.getUserByEmail(email);
            m.addAttribute("user", user);
            Integer countBookingCart = bookingCartService.getCountBookingCart(user.getId());
            m.addAttribute("countCart", countBookingCart);
        }
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categories", allActiveCategory);
    }


    @GetMapping("/index")
    public String index(Model m) {
        List<Category> allActiveCategory = categoryService.getAllCategory().stream()
                .sorted((c1, c2) -> Integer.compare(c2.getId(), c1.getId())) // Dùng Integer.compare
                .limit(6)
                .toList();
        List<Room> allActiveRooms = roomService.getAllRooms().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getId(), p1.getId())) // Dùng Integer.compare
                .limit(8)
                .toList();
        m.addAttribute("category", allActiveCategory);
        System.out.println("number of categry"+ allActiveCategory);
        m.addAttribute("rooms",  allActiveRooms);
        System.out.println("number of room" +  allActiveRooms);
        return "index";
    }


    @GetMapping("/base")
    public String base() {
        return "base";
    }


    @GetMapping("/signin")
    public String login() {
        return "login";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/forget_password")
    public String forget_password() {
        return "forget_password";
    }


    @GetMapping("/message")
    public String message() {
        return "message";
    }


    @GetMapping("/reset_password")
    public String reset_password() {
        return "reset_password";
    }


    @GetMapping("/category")
    public String category() {
        return "category";
    }


    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, @RequestParam("img") MultipartFile file, HttpSession session)
            throws IOException {
        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        user.setProfileImage(imageName);
        User saveUser = userService.saveUser(user);
        if (!ObjectUtils.isEmpty(saveUser)) {
            if (!file.isEmpty()) {
                // Đường dẫn đến thư mục chứa profile_img
                String profileImgPath = "static/img/HinhAnh/Ecommerce Spring Boot/category_img";
                File profileImgDir = new File(profileImgPath);
                // Kiểm tra xem thư mục có tồn tại không, nếu không thì tạo nó
                if (!profileImgDir.exists()) {
                    profileImgDir.mkdirs(); // Tạo tất cả các thư mục cha nếu cần
                }
                // Tạo đường dẫn tới tệp hình ảnh
                Path path = Paths.get(profileImgDir.getAbsolutePath(), file.getOriginalFilename());
                // Sao chép tệp hình ảnh vào thư mục
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("succMsg", "Register successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on server");
        }
        return "redirect:/register";
    }

    @GetMapping("/rooms")
    public String rooms(Model m, @RequestParam(value = "category", defaultValue = "") String category) {
        System.out.println(" category = " + category);
        List<Category> categories = categoryService.getAllCategory();
        Page<Room> page = null;
        List<Room> rooms = roomService.getAllActiveRooms(category);
        int roomsSize = rooms.size();
        // Log the size of rooms
        System.out.println("Number of rooms: " + roomsSize);
        m.addAttribute("rooms", rooms);
        m.addAttribute("roomsSize", roomsSize);
        m.addAttribute("categories", categories);
        m.addAttribute("paramValue", category);
        return "room";
    }


    @GetMapping("/room_details/{id}")
    public String roomDetails(
            @PathVariable("id") int id,
            @RequestParam(value = "checkInDate", required = false) String checkInDate,
            @RequestParam(value = "checkOutDate", required = false) String checkOutDate,
            @RequestParam Map<String, String> services, // Nhận các dịch vụ đã chọn
            Model model,HttpSession session) {
        // Lấy thông tin phòng theo id
        Room room = roomService.getRoomById(id);
        model.addAttribute("room", room);
        // Nếu có ngày check-in và check-out, tính số ngày lưu trú và subtotal
        if (checkInDate != null && checkOutDate != null) {
            try {
                LocalDate checkIn = LocalDate.parse(checkInDate);
                LocalDate checkOut = LocalDate.parse(checkOutDate);
            } catch (Exception e) {
                // Xử lý lỗi nếu ngày không hợp lệ
                model.addAttribute("stayDuration", "Invalid dates");
            }
        }
        return "room_details";
    }

     @GetMapping("/search")
          public String searchRoom(@RequestParam String ch, Model m) {
              List<Room> searchRooms = roomService.searchRoom(ch);
          int roomsSize = searchRooms.size();
           // Log kích thước danh sách
         System.out.println("Number of rooms: " + roomsSize);
          // Thêm vào model
         m.addAttribute("roomsSize", roomsSize);
         m.addAttribute("rooms", searchRooms);
           return "room"; // Tên template
}

}
