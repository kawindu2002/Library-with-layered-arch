package edu.ijse.gdse71.library.dto.tm;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AuthorTM {
    private String authorID;
    private String name;
    private String biography;
    private Date regDate;

}
