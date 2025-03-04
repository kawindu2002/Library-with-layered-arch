package edu.ijse.gdse71.library.dto.tm;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class MemberTM {
    private String memberID;
    private String name;
    private String address;
    private String email;
    private String contact;
    private Date regDate;
    private String state;

}
