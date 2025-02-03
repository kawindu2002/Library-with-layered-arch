package edu.ijse.gdse71.library.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Bookshelf {
    private String bookshelfID;
    private String categoryID;
    private int capacity;
    private String location;

}
