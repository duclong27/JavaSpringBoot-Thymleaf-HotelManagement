package com.hotel.HotelBooking.service.impl;


import com.hotel.HotelBooking.entities.Room;
import com.hotel.HotelBooking.entities.User;
import com.hotel.HotelBooking.repositories.UserRepository;
import com.hotel.HotelBooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {







    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setIsEnable(true);
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);
        user.setCreatedAt(user.getCreatedAt());
        user.setFullName(user.getFullName());
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public List<User> getUsers(String role) {
        return userRepository.findByRole(role);
    }






    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        Optional<User> findByUser = userRepository.findById(id);

        if(!findByUser.isEmpty()){
            User user = findByUser.get();
            user.setIsEnable(status);
            userRepository.save(user);
            return true;
        }


        return false;
    }

    @Override
    public void increaseFailedAttempt(User user) {

    }

    @Override
    public void userAccountLock(User user) {

    }

    @Override
    public boolean unlockAccountTimeExpired(User user) {
        return false;
    }

    @Override
    public Boolean deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(null);

        if (!ObjectUtils.isEmpty(user)){
            userRepository.delete(user);
            return true;
        }
        return false;
    }



    @Override
    public void resetAttempt(int userId) {

    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {

    }

    @Override
    public User getUserByToken(String token) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User updateUserProfile(User user, MultipartFile img) {
        // Tìm kiếm người dùng trong database
        User dbUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Nếu có ảnh được tải lên
        if (!img.isEmpty()) {
            // Sử dụng tên file gốc
            String originalFilename = img.getOriginalFilename();
            dbUser.setProfileImage(originalFilename);

            try {
                // Thư mục lưu ảnh
                File saveFile = new ClassPathResource("static/img/HinhAnh/Ecommerce Spring Boot/profile_img").getFile();
                if (!saveFile.exists()) {
                    saveFile.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                }

                // Đường dẫn lưu file
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + originalFilename);

                // Ghi file lên hệ thống
                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save profile image", e);
            }
        }

        // Cập nhật các thông tin khác của người dùng
        dbUser.setFullName(user.getFullName());
        dbUser.setPhoneNumber(user.getPhoneNumber());
        dbUser.setAddress(user.getAddress());

        // Lưu thông tin người dùng vào database
        return userRepository.save(dbUser);
    }


    @Override
    public User saveAdmin(User user) {
        return null;
    }

    @Override
    public Boolean existsEmail(String email) {
        return null;
    }
}
