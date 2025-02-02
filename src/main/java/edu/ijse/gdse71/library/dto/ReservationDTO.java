package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ReservationDTO {
    private String reservationID;
    private String memberID;
    private String bookID;
    private String userID;
    private Date reservationDate;

}
