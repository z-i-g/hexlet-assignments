package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class BooDto {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate createdAt;
}
