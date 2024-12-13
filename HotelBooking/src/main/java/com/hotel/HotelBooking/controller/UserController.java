package com.hotel.HotelBooking.controller;



import com.hotel.HotelBooking.entities.*;
import com.hotel.HotelBooking.repositories.RoomOrderRepository;
import com.hotel.HotelBooking.repositories.RoomRepository;
import com.hotel.HotelBooking.service.*;
import com.hotel.HotelBooking.service.impl.OrderStatus;
import com.hotel.HotelBooking.service.impl.RoomServiceIpml;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Transactional
@Controller
@RequestMapping("/users")
@FieldDefaults(level=AccessLevel.PRIVATE)
@RequiredArgsConstructor // Thêm annotation này để tự động tạo constructor cho field UserService
public class UserController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomOrderRepository roomOrderRepository;
    @Autowired
    private RoomOrderService roomOrderService;
    @Autowired
    private BookingCartService bookingCartService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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


    @GetMapping("/addCart")
    public String addToCart(@RequestParam int rid,
                            @RequestParam int uid,
                            @RequestParam String checkInDate,
                            @RequestParam String checkOutDate,
                            @RequestParam(required = false) String services,
                            HttpSession session,
                            Model model) {
        System.out.println(services);
        BookingCart saveBookingCart = bookingCartService.saveBookingCart(rid, uid, checkInDate, checkOutDate, services);
        session.setAttribute("checkIn", checkInDate);
        session.setAttribute("checkOut", checkOutDate);
        if (saveBookingCart != null) {
            session.setAttribute("succMsg", "Room added to cart successfully");
        } else {
            session.setAttribute("errorMsg", "Failed to add room to cart");
        }

        model.addAttribute("room", saveBookingCart.getRoom());
        return "room_details";
    }


    @GetMapping("/viewCart")
    public String viewCart(Model m, Principal p, HttpSession session,
                           @RequestParam(required = false) String checkInDate,
                           @RequestParam(required = false) String checkOutDate) {
        User user = getLoggedInUserDetails(p);
        if (checkInDate == null) {
            checkInDate = (String) session.getAttribute("checkIn");
        }
        if (checkOutDate == null) {
            checkOutDate = (String) session.getAttribute("checkOut");
        }
        // Kiểm tra giá trị ngày check-in và check-out
        if (checkInDate == null || checkOutDate == null) {
            m.addAttribute("error", "Please select check-in and check-out dates.");
            return "/user1/cart";
        }
        try {
            // Gọi phương thức service để lấy danh sách giỏ hàng
            List<BookingCart> bookingCarts = bookingCartService.getBookingCartsByUser(user.getId(), checkInDate, checkOutDate);
            if (bookingCarts == null || bookingCarts.isEmpty()) {
                throw new IllegalArgumentException("No carts found for user " + user.getId());
            }
            // Thêm giỏ hàng vào model
            m.addAttribute("bookingCarts", bookingCarts);
            int cartSize = bookingCarts.size();
            System.out.println("Carts size: " +cartSize);
            // Tính tổng giá trị đơn hàng
            Double totalOrderPrice = 0.0;
            for (BookingCart bookingCart : bookingCarts) {
                totalOrderPrice += bookingCart.getTotalPrice(); ; // Lấy tổng giá trị của giỏ hàng (đã tính sẵn)
            }
            m.addAttribute("totalOrderPrice", totalOrderPrice);

        } catch (IllegalArgumentException e) {
            m.addAttribute("error", e.getMessage());
            return "/user1/cart";
        }
//         Thêm ngày check-in và check-out vào model
        m.addAttribute("checkInDate", checkInDate);
        m.addAttribute("checkOutDate", checkOutDate);
        // Lấy dịch vụ và subtotal từ session
        Map<String, String> services = (Map<String, String>) session.getAttribute("services");
        m.addAttribute("services", services);
        Double subtotal = (Double) session.getAttribute("subtotal");
        m.addAttribute("subtotal", subtotal);

        return "/user1/cart";
    }


    private User getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        User user = userService.getUserByEmail(email);
        return user;
    }


    @GetMapping("/orders")
    public String orderPage(Principal p, Model m, String checkInDate,
                             String checkOutDate,HttpSession session) {
        if (checkInDate == null) {
            checkInDate = (String) session.getAttribute("checkIn");
        }
        if (checkOutDate == null) {
            checkOutDate = (String) session.getAttribute("checkOut");
        }
        User user = getLoggedInUserDetails(p);
        List<BookingCart> carts = bookingCartService.getBookingCartsByUser(user.getId(),  checkInDate,  checkOutDate);
        m.addAttribute("carts", carts);
        m.addAttribute("checkInDate", checkInDate);
        m.addAttribute("checkOutDate", checkOutDate);
        if (checkInDate == null) {
            checkInDate = (String) session.getAttribute("checkIn");
        }
        if (checkOutDate == null) {
            checkOutDate = (String) session.getAttribute("checkOut");
        }
        if (carts.size() > 0) {
            Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice()  + 100;
            m.addAttribute("orderPrice", orderPrice);
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/user1/order";
    }


    @PostMapping("/save-roomOrder")
    public String saveOrder(@ModelAttribute OrderRequest orderRequest,
                            Principal p,
                            HttpSession session, @RequestParam(required = false) String checkInDate,
                            @RequestParam(required = false) String checkOutDate) throws Exception {
        // System.out.println(request);
        User user = getLoggedInUserDetails(p);
        roomOrderService.saveRoomOrder(user.getId(),orderRequest);
        session.setAttribute("checkIn", checkInDate);
        session.setAttribute("checkOut", checkOutDate);

        return "redirect:/users/success";
    }


    @GetMapping("/success")
    public String loadSuccess() {
        return "/user1/success";
    }


    @GetMapping("/user-orders")
    public String myOrder(Model m, Principal p,
                          @RequestParam(required = false) String checkInDate,
                          @RequestParam(required = false) String checkOutDate,
                          HttpSession session) {
        User loginUser = getLoggedInUserDetails(p);
        List<RoomOrder> roomOrders = roomOrderService.getOrdersByUser(loginUser.getId());
        List<RoomOrder> roomOrders1 = roomOrderRepository.findAll();
        m.addAttribute("roomOrders1", roomOrders1);
        m.addAttribute("roomOrders", roomOrders);
        if (checkInDate == null) {
            checkInDate = (String) session.getAttribute("checkIn");
        }
        if (checkOutDate == null) {
            checkOutDate = (String) session.getAttribute("checkOut");
        }
        return "/user1/my_order";
    }

    @GetMapping("/update-status")
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
        return "redirect:/users/user-orders";
    }


    @GetMapping("/viewProfile")
    public String viewProfile(){
        return "/user1/profile";
    }


    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute User user, @RequestParam MultipartFile img, HttpSession session) {
        User updateUserProfile = userService.updateUserProfile(user, img);
        if (ObjectUtils.isEmpty(updateUserProfile)) {
            session.setAttribute("errorMsg", "Profile not updated");
        } else {
            session.setAttribute("succMsg", "Profile Updated");
        }
        return "redirect:/users/viewProfile";
    }


    @PostMapping("/change-password")
    public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
                                 HttpSession session) {
        User loggedInUserDetails = getLoggedInUserDetails(p);
        boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());
        if (matches) {
            String encodePassword = passwordEncoder.encode(newPassword);
            loggedInUserDetails.setPassword(encodePassword);
            User updateUser = userService.updateUser(loggedInUserDetails);
            if (ObjectUtils.isEmpty(updateUser)) {
                session.setAttribute("errorMsg", "Password not updated !! Error in server");
            } else {
                session.setAttribute("succMsg", "Password Updated sucessfully");
            }
        } else {
            session.setAttribute("errorMsg", "Current Password incorrect");
        }

        return "redirect:/user1/profile";
    }
}


