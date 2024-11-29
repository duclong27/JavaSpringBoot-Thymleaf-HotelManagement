package com.hotel.HotelBooking.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="RoomOrderReport")

public class RoomOrderReport {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int roomId;

    private long numberOfOrders;

    private LocalDate date;

    private double totalRevenue;

    private double percentage;





    public RoomOrderReport(double percentage) {
        this.percentage = percentage;
    }

    public RoomOrderReport(int roomId, long numberOfOrders) {
        this.roomId = roomId;
        this.numberOfOrders = numberOfOrders;

    }

    public RoomOrderReport(LocalDate date, double totalRevenue) {
        this.date = date;
        this.totalRevenue = totalRevenue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }


    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
