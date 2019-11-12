package com.jit.Other.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReg {
    private String name;
    private String password;
    private String email_id;
    private String email_code;
}
