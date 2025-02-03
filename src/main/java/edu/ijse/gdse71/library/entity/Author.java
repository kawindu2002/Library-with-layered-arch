package edu.ijse.gdse71.library.entity;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Author {
    private String authorID;
    private String name;
    private String biography;
    private Date regDate;

}

