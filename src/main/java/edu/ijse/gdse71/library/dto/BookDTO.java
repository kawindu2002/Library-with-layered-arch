package edu.ijse.gdse71.library.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

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
    private ArrayList<CategoryDetailsDTO> categoryDetailsDTOS = new ArrayList<>();
    private ArrayList<AuthorDetailsDTO> authorDetailsDTOS = new ArrayList<>();


}
