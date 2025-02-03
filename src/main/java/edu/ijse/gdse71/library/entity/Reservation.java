package edu.ijse.gdse71.library.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Reservation {
    private String reservationID;
    private String memberID;
    private String bookID;
    private String userID;
    private Date reservationDate;

}
