package edu.ijse.gdse71.library.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CategoryTM {
    private String categoryID;
    private String description;
    private Date regDate;

}
