package com.datn.meou.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "dotn_account")
public class Account extends BaseEntity {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String address;
    private Integer roleId;

}
