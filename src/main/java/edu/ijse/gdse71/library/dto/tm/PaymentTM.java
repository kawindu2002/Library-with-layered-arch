package edu.ijse.gdse71.library.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaymentTM {
    private String paymentID;
    private String memberID;
    private String purpose;
    private double price;
    private Date paymentDate;
    private String userID;

}
