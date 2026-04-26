package mongo.demo.app.model;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Setter @Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Document(collection = "todos")
public class ToDoDTO {

    @Id
    private String id;

    @NotBlank(message = "Todo title cannot be empty")
    @Size(max = 100, message = "Todo title must be between 3 and 100 characters",min = 3)
    private String todo;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 255, message = "Description must be between 3 and 255 characters",min = 3)
    private String description;

    @NotNull(message = "Completed status is required")
    private Boolean completed;

    private Date createdAt;

    private Date updatedAt;

}
