package com.hotel.HotelBooking.repositories;


import com.hotel.HotelBooking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    public User findByEmail(String email);

    public List<User> findByRole(String role);


    public User findByResetToken(String token);



}
