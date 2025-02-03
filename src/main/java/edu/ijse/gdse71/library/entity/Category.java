package edu.ijse.gdse71.library.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Category {
    private String categoryID;
    private String description;
    private Date regDate;

}

