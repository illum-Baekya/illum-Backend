package com.gdg.illum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdatedUserDto {
    private String username;
    private String password;
    private String email;
}
