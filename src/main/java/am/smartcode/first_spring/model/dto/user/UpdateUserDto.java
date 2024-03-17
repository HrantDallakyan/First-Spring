package am.smartcode.first_spring.model.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class UpdateUserDto {

    private String name;
    private String lastname;
    private LocalDate age;

}
