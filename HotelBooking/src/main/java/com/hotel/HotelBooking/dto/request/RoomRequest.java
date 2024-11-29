package com.hotel.HotelBooking.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

      @JsonProperty("hotel_id")
      private int hotel_id;


      @JsonProperty("room_type")
      private String roomType;


      @JsonProperty("price")
      private String price;


      @JsonProperty("capacity")
      private int capacity;


}
