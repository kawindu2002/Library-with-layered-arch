package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class FineDTO {
    private String fineID;
    private String userID;
    private String memberID;
    private String loanID;
    private double price;
    private Date fineDate;
    private String reason;

}
