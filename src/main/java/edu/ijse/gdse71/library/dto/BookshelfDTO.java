package edu.ijse.gdse71.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BookshelfDTO {
    private String bookshelfID;
    private String categoryID;
    private int capacity;
    private String location;

}
