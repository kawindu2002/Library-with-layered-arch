package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDTO {
    private String userID;
    private String name;
    private String password;
    private String role;
    private Date regDate;
    private String state;

}

