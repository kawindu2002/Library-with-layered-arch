package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoanDTO {
    private String loanID;
    private String userID;
    private String memberID;
    private String bookID;
    private Date loanDate;
    private Date dueDate;

}
