package com.hotel.HotelBooking.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.HotelBooking.dto.RoomOrderReport;
import com.hotel.HotelBooking.entities.Room;
import com.hotel.HotelBooking.entities.RoomOrder;
import com.hotel.HotelBooking.entities.User;
import com.hotel.HotelBooking.repositories.RoomOrderRepository;
import com.hotel.HotelBooking.service.*;
import com.hotel.HotelBooking.service.impl.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.hotel.HotelBooking.entities.Category;
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
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BookingCartService bookingCartService;
    @Autowired
    private RoomOrderService roomOrderService;
    @Autowired
    private RoomOrderReportService roomOrderReportService;
    @Autowired
    private RoomOrderRepository roomOrderRepository;


    @GetMapping()
    public String index() {
        return "admin/index";
    }

    @GetMapping("/add_admin")
    public String add_admin() {
        return "admin/add_admin";
    }


    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userService.getUserByEmail(email);
            m.addAttribute("user", user);
        }
        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categories", allActiveCategory);
    }


    @GetMapping("/category")
    public String category(Model m) {
        m.addAttribute("categories", categoryService.getAllCategory());
        return "admin/category";
    }


    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                               HttpSession session) throws IOException {
        // Lấy tên hình ảnh từ tệp tải lên hoặc gán là "default.jpg" nếu không có tệp
        String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
        category.setImageName(imageName);
        // Kiểm tra xem danh mục đã tồn tại chưa
        Boolean existCategory = categoryService.existCategory(category.getName());
        if (existCategory == null) {
            session.setAttribute("errorMsg", "Internal error occurred while checking category existence");
        } else if (existCategory) {
            session.setAttribute("errorMsg", "Category Name already exists");
        } else {
            // Lưu danh mục vào cơ sở dữ liệu
            Category saveCategory = categoryService.saveCategory(category);
            if (ObjectUtils.isEmpty(saveCategory)) {
                session.setAttribute("errorMsg", "Not saved! Internal server error");
            } else {
                // Lưu tệp hình ảnh vào thư mục đúng
                File saveFile = new ClassPathResource("static/img/HinhAnh/Ecommerce Spring Boot/category_img").getFile();
                // Tạo đường dẫn tệp để lưu hình ảnh
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + imageName);
                // Sao chép tệp hình ảnh vào đường dẫn đã chỉ định
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                session.setAttribute("succMsg", " Category Saved successfully");
            }
        }
        return "redirect:/admin/category";
    }


    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                                 HttpSession session) throws IOException {
        Category oldCategory = categoryService.getCategoryById(category.getId());
        String imageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();
        if (!ObjectUtils.isEmpty(category)) {
            oldCategory.setName(category.getName());
            oldCategory.setIsActive(category.getIsActive());
            oldCategory.setImageName(imageName);
        }
        Category updateCategory = categoryService.saveCategory(oldCategory);
        if (!ObjectUtils.isEmpty(updateCategory)) {
            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/img/HinhAnh/Ecommerce Spring Boot/category_img").getFile();
                // Kiểm tra và tạo thư mục nếu chưa tồn tại
                if (!saveFile.exists()) {
                    saveFile.mkdirs();
                }
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }
            session.setAttribute("succMsg", "Category updated successfully");
        } else {
            session.setAttribute("errorMsg", "Something went wrong on the server");
        }
        return "redirect:/admin/editCategory/" + category.getId();
    }


    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id, HttpSession session) {
        Boolean deleteCategory = categoryService.deleteCategory(id);
        if (deleteCategory) {
            session.setAttribute("succMsg", "category deleted successfully");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }
        return "redirect:/admin/category";
    }


    @GetMapping("/loadAddRoom")
    public String loadAddRoom(Model model) {
        List<Category> categories = categoryService.getAllCategory(); // Giả sử bạn có một service để lấy danh sách category
        model.addAttribute("categories", categories); // Thêm danh sách vào mô hình với tên là "categories"
        return "/admin/add_room"; // Trả về view "add_room.html"
    }


    @PostMapping("/saveRoom")
    public String saveRoom(@ModelAttribute Room room, @RequestParam("file") MultipartFile image,
                           HttpSession session) throws IOException {
        // Kiểm tra xem tệp hình ảnh có rỗng không và đặt tên mặc định nếu cần
        String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();
        // Thiết lập hình ảnh cho sản phẩm
        room.setImage(imageName);
        // Lưu sản phẩm vào dịch vụ
        Room saveRoom = roomService.saveRoom(room);
        if (!ObjectUtils.isEmpty(saveRoom)) {
            // Lấy thư mục nơi lưu hình ảnh
            File saveDir = new ClassPathResource("static/img/HinhAnh/Ecommerce Spring Boot/category_img").getFile();
            // Kiểm tra xem thư mục có tồn tại không, nếu không thì tạo nó
            if (!saveDir.exists()) {
                saveDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }
            // Xây dựng đường dẫn lưu tệp hình ảnh
            Path path = Paths.get(saveDir.getAbsolutePath() + File.separator + imageName);
            // Sao chép tệp hình ảnh vào thư mục
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            // Thêm thông báo thành công vào phiên
            session.setAttribute("succMsg", "Room Saved Successfully");
        } else {
            // Thêm thông báo lỗi vào phiên
            session.setAttribute("errorMsg", "Something went wrong on the server");
        }
        // Chuyển hướng về trang thêm phòng
        return "redirect:/admin/loadAddRoom";
    }


    @GetMapping("/editCategory/{id}")
    public String loadEditCategory(@PathVariable int id, Model m) {
        m.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/edit_category";
    }


    @GetMapping("/room")
    public String loadViewRoom(Model m, @RequestParam(defaultValue = "") String ch,
                               @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        List<Room> rooms = null;
        if (ch != null && ch.length() > 0) {
            rooms = roomService.searchRoom(ch);
        } else {
            rooms = roomService.getAllRooms();
        }
        m.addAttribute("rooms", rooms);
        return "admin/room";
    }


    @GetMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable Integer id, HttpSession session) {
        Boolean deleteRoom = roomService.deleteRoom(id);
        if (deleteRoom) {
            session.setAttribute("succMsg", "Room deleted success");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }
        return "redirect:/admin/room";
    }


    @GetMapping("/editRoom/{id}")
    public String editRoom(@PathVariable int id, Model m) {
        m.addAttribute("room", roomService.getRoomById(id));
        return "admin/edit_room";
    }

    @GetMapping("/editRoom")
    public String editRoom1() {
        return "admin/edit_room";
    }


    @PostMapping("/updateRoom")
    public String updateRoom(@ModelAttribute Room room, @RequestParam("file") MultipartFile image,
                             HttpSession session, Model m) {
        Room updateRoom = roomService.updateRoom(room, image);
        if (!ObjectUtils.isEmpty(updateRoom)) {
            session.setAttribute("succMsg", "Room update success");
        } else {
            session.setAttribute("errorMsg", "Something wrong on server");
        }
        return "redirect:/admin/editRoom/" + room.getId();
    }


    @GetMapping("/orders")
    public String getAllOrders(Model m, @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		List<RoomOrder> allOrders = roomOrderService.getAllOrders();
		m.addAttribute("orders", allOrders);
        Page<RoomOrder> page = roomOrderService.getAllOrdersPagination(pageNo, pageSize);
        m.addAttribute("orders", page.getContent());
        m.addAttribute("srch", false);
        m.addAttribute("pageNo", page.getNumber());
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPages", page.getTotalPages());
        m.addAttribute("isFirst", page.isFirst());
        m.addAttribute("isLast", page.isLast());
        return "/admin/orders";
    }


    @PostMapping("/update-order-status")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {
        OrderStatus[] values = OrderStatus.values();
        String status = null;
        for (OrderStatus orderSt : values) {
            if (orderSt.getId().equals(st)) {
                status = orderSt.getName();
            }
        }
        RoomOrder updateOrder = roomOrderService.updateRoomOrderStatus(id, status);
        if (!ObjectUtils.isEmpty(updateOrder)) {
            session.setAttribute("succMsg", "Status Updated");
        } else {
            session.setAttribute("errorMsg", "status not updated");
        }
        return "redirect:/admin/orders";
    }


    @GetMapping("/profile")
    public String profile() {
        return "admin/profile";
    }


    @GetMapping("/users")
    public String loadAllUser(Model m, String ch) {
        List<User> users = userService.getUsers("ROLE_USER");
        List<User> users1 = userService.getUsers("ROLE_ADMIN");
        // In ra số lượng phòng để kiểm tra
        System.out.println("Số lượng phòng: " + (users != null ? users.size() : 0));
        m.addAttribute("users", users);
        m.addAttribute("users1", users1);
        return "admin/user";
    }


    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id, HttpSession session) {
        Boolean deleteUser = userService.deleteUser(id);
        if (deleteUser) {
            session.setAttribute("succMsg", "User deleted success");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }
        return "redirect:/admin/users";
    }


    @GetMapping("/updateUserStatus")
    public String updateUserStatus(@RequestParam Boolean status, @RequestParam Integer id, HttpSession session) {
        Boolean f = userService.updateAccountStatus(id, status);
        if (f) {
            session.setAttribute("succMsg", "Account Status Updated");
        } else {
            session.setAttribute("errorMsg", "Something wrong on server");
        }
        return "redirect:/admin/users";
    }


    @GetMapping("/search-order")
    public String searchProduct(@RequestParam String orderId, Model m, HttpSession session) {
        if (orderId != null && orderId.length() > 0) {
            RoomOrder order = roomOrderService.getOrdersByOrderId(orderId.trim());
            if (ObjectUtils.isEmpty(order)) {
                session.setAttribute("errorMsg", "Incorrect orderId");
                m.addAttribute("orderDtls", null);
            } else {
                m.addAttribute("orderDtls", order);
            }
            m.addAttribute("srch", true);
        } else {
            List<RoomOrder> allOrders = roomOrderService.getAllOrders();
            m.addAttribute("orders", allOrders);
            m.addAttribute("srch", false);
        }
        return "/admin/orders";

    }


    @GetMapping("/report")
    public String viewReport(Model model) throws JsonProcessingException {
        List<RoomOrderReport> reports = roomOrderReportService.getRoomOrderReport();
        long totalOrders = reports.stream()
                .mapToLong(RoomOrderReport::getNumberOfOrders)
                .sum();
        if (totalOrders == 0) {
            model.addAttribute("error", "Không có đơn đặt phòng nào.");
        } else {
            for (RoomOrderReport report : reports) {
                double percentage = (double) report.getNumberOfOrders() / totalOrders * 100;
                percentage = Math.round(percentage);
                report.setPercentage(percentage);  // Đặt giá trị phần trăm vào DTO
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String reportsJson = objectMapper.writeValueAsString(reports);  // Chuyển đổi thành JSON
        model.addAttribute("reportsJson", reportsJson);
        model.addAttribute("reports", reports);
        List<RoomOrderReport> dailyRevenue = roomOrderRepository.findDailyRevenue();
        model.addAttribute("dailyRevenue", dailyRevenue);

        return "/admin/report";
    }

}


