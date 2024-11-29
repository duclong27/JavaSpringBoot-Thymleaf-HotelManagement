package com.hotel.HotelBooking.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HotelResponse {

    @JsonProperty("hotel_name")
    private String hotelName;


    @JsonProperty("address")
    private String address;


    @JsonProperty("city")
    private String city;


    private List<RoomResponse> rooms;

}
