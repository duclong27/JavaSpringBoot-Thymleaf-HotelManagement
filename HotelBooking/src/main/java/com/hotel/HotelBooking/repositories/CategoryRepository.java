package com.hotel.HotelBooking.repositories;


import com.hotel.HotelBooking.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public boolean existsByName(String name);


    public List<Category> findByIsActiveTrue();

}
