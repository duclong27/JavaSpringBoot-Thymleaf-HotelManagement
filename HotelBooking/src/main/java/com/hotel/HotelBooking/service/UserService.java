package com.hotel.HotelBooking.service;


import com.hotel.HotelBooking.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User getUserByEmail(String email);

    public List<User> getUsers(String role);

    public Boolean updateAccountStatus(Integer id, Boolean status);

    public void increaseFailedAttempt(User user);

    public void userAccountLock(User user);

    public boolean unlockAccountTimeExpired(User user);

    public Boolean deleteUser(int id);

    public User updateUser(User user);

    public User updateUserProfile(User user, MultipartFile img);




}
