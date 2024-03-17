package am.smartcode.first_spring.model.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserDto {

    private Integer id;
    private String name;
    private String lastname;
    private LocalDate age;
    private String email;

}
