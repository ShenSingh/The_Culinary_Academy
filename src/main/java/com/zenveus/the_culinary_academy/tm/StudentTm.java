package com.zenveus.the_culinary_academy.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTm {
    private String studentID;
    private String studentName;
    private String studentNIC;
    private String studentAge;
    private String studentEmail;
    private String studentPhone;
    private String studentAddress;
}
