package edu.ijse.gdse71.library.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class FineTM {
    private String fineID;
    private String userID;
    private String memberID;
    private String loanID;
    private double price;
    private Date fineDate;
    private String reason;

}
