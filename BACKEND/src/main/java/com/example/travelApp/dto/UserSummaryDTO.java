package com.example.travelApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSummaryDTO {
    private Integer id;
    private String userId;
    private String name;
    private String email;
}
