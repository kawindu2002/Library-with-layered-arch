package edu.ijse.gdse71.library.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PublisherTM {
    private String publisherID;
    private String name;
    private String address;
    private Date regDate;

}
