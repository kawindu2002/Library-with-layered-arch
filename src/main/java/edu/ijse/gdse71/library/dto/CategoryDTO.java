package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CategoryDTO {
    private String categoryID;
    private String description;
    private Date regDate;

}
