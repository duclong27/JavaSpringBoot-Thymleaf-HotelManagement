package com.hotel.HotelBooking.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailRequest {


    @JsonProperty("reservation_id")
    private int reservation_id;


    @JsonProperty("room_id")
    private Long room_id;

    @JsonProperty("check_in_time")
    private LocalDateTime checkInTime;


    @JsonProperty("check_out_time")
    private LocalDateTime checkOutTime;

    @JsonProperty("room_price")
    private BigDecimal roomPrice;
}
