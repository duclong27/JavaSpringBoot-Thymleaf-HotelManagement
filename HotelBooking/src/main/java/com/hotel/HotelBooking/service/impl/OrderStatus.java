package com.hotel.HotelBooking.service.impl;





public enum OrderStatus {

    IN_PROGRESS(1, "In Progress"), ORDER_RECEIVED(2, "Order Received"), PAID(3, " PAID"),
    PENDING(4, "PENDING"),COMPLETED_BOOKED(5,"COMPLETED_BOOKED"), CHECK_IN(6, "CHECK IN"),CHECK_OUT(7,"CHECK OUT"),CANCELLED(8,"Cancelled");

    private Integer id;

    private String name;

    private OrderStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
