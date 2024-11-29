package com.hotel.HotelBooking.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @JsonProperty("reservation_id")
    private int reservation_id;


    @JsonProperty("hotel_id")
    private Date paymentDate;


    @JsonProperty("hotel_id")
    private BigDecimal amount;


    @JsonProperty("payment_method")
    private String paymentMethod;

}
