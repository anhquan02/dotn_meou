package com.datn.meou.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    private Long id;
    private String token;
    private String fullname;
    private String username;
    private String nameRole;
    private Long roleId;

}

