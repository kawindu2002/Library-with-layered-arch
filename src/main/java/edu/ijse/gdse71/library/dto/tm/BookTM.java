package edu.ijse.gdse71.library.dto.tm;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BookTM {
    private String bookID;
    private String title;
    private String isbn;
    private Date regDate;
    private String publisherID;
    private double price;
    private String state;
    private String bookshelfID;

}
