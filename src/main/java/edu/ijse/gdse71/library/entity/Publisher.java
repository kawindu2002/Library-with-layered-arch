package edu.ijse.gdse71.library.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Publisher {
    private String publisherID;
    private String name;
    private String address;
    private Date regDate;

}
