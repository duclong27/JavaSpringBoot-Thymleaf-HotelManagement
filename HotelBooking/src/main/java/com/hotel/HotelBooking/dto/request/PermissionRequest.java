package com.hotel.HotelBooking.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

}
