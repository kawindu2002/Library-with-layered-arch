package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BookDTO {
    private String bookID;
    private String title;
    private String isbn;
    private Date regDate;
    private String publisherID;
    private double price;
    private String state;
    private String bookshelfID;

}

