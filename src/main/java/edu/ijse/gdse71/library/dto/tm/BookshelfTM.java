package edu.ijse.gdse71.library.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BookshelfTM {
    private String bookshelfID;
    private String categoryID;
    private int capacity;
    private String location;

}
