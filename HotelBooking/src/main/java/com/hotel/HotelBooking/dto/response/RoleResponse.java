package com.hotel.HotelBooking.dto.response;

import com.hotel.HotelBooking.entities.User;
import lombok.*;

import java.util.Set;



@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {

    private String roleName;



    private Set<PermissionResponse> permissions;
}
