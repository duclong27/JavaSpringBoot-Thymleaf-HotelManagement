package com.hotel.HotelBooking.service.impl;

import com.hotel.HotelBooking.dto.RoomOrderReport;
import com.hotel.HotelBooking.repositories.RoomOrderRepository;
import com.hotel.HotelBooking.service.RoomOrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RoomOrderReportImpl implements RoomOrderReportService {


     @Autowired
    private RoomOrderRepository roomOrderRepository;




    @Override
    public List<RoomOrderReport> getRoomOrderReport() {
        return roomOrderRepository.countRoomOrders();
    }



//    @Override
//    public List<RoomOrderReport> getOrdersStatistics() {
//        return roomOrderRepository.findOrdersGroupedByDate();
//    }
//

//    @Override
//    public List<RoomOrderReport> getOrdersStatistics() {
//        List<RoomOrderReport> reports = roomOrderRepository.findOrdersGroupedByDate();
//        if (reports == null || reports.isEmpty()) {
//            System.out.println("22222");
//        } else {
//            reports.forEach(report -> {
//                System.out.println("Ngày: " + report.getOrderDate() + ", Số lượt đặt: " + report.getNumberOfOrders());
//            });
//        }
//        return reports;
//    }



//    public List<RoomOrderReport> getRoomOrderStatistics() {
//        return roomOrderRepository.countRoomOrders();
//    }

}
