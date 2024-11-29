package com.hotel.HotelBooking.Mapper;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
////     Chuyển từ DTO (UserCreationRequest) sang entity (User)
////    @Mapping(target = "role", ignore = true)  // Gán Role riêng
//
//    User toUser(UserCreationRequest request);
//
////     Chuyển từ entity (User) sang DTO (UserResponse)
//    @Mapping(target = "roleName", source = "role.roleName") // Ánh xạ roleName
//    UserResponse toUserResponse(User user);
}