package com.hotel.HotelBooking.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

    @JsonProperty("hotel_name")
    private String hotelName;


    @JsonProperty("address")
    private String address;


    @JsonProperty("city")
    private String city;


}
