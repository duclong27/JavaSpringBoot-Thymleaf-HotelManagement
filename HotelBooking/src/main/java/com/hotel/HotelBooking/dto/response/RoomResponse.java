package com.hotel.HotelBooking.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RoomResponse {
    @JsonProperty("hotel_id")
    private int hotel_id;


    @JsonProperty("room_type")
    private String roomType;


}
