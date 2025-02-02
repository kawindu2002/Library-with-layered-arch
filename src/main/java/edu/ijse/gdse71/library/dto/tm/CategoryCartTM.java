package edu.ijse.gdse71.library.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CategoryCartTM {
    private String categoryID;
    private Button removeBtn;

}
