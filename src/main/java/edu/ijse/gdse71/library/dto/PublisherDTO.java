package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PublisherDTO {
    private String publisherID;
    private String name;
    private String address;
    private Date regDate;

}
