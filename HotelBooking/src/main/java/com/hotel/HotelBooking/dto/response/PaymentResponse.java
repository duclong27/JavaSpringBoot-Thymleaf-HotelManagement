package com.hotel.HotelBooking.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    @JsonProperty("reservation_id")
    private int reservation_id;


    @JsonProperty("hotel_id")
    private Date paymentDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

}
