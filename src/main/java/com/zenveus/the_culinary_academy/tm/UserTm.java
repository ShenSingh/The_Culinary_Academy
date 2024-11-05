package com.zenveus.the_culinary_academy.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTm {
    String userId;
    String fullName;
    String email;
    String phoneNumber;
    String address;
}
