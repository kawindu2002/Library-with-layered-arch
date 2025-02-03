package edu.ijse.gdse71.library.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Loan {
    private String loanID;
    private String userID;
    private String memberID;
    private String bookID;
    private Date loanDate;
    private Date dueDate;

}
