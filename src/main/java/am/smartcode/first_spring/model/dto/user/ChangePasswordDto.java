package am.smartcode.first_spring.model.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordDto {

    private Integer id;
    private String oldPassword;
    private String newPassword;
    private String newPassword2;

}
