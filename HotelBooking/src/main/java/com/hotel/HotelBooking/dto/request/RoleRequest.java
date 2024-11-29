package com.hotel.HotelBooking.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    @JsonProperty("role_name")
    private String roleName;



    private Set<String> permissions = new HashSet<>();  ;
}
