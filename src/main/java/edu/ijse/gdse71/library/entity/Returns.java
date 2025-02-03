package edu.ijse.gdse71.library.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Returns {
    private String returnID;
    private String userID;
    private String memberID;
    private String loanID;
    private String bookID;
    private Date returnDate;

}
