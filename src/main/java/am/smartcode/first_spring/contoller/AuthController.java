package am.smartcode.first_spring.contoller;

import am.smartcode.first_spring.exception.UserAlreadyExistsException;
import am.smartcode.first_spring.exception.ValidationException;
import am.smartcode.first_spring.exception.VerificationException;
import am.smartcode.first_spring.model.dto.user.CreateUserDto;
import am.smartcode.first_spring.model.entity.UserEntity;
import am.smartcode.first_spring.service.user.AuthService;
import am.smartcode.first_spring.util.constants.Parameter;
import am.smartcode.first_spring.util.constants.Path;
import am.smartcode.first_spring.util.encoder.AESManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView start(
            @CookieValue(value = Parameter.REMEMBER_COOKIE, required = false) Cookie rememberCookie,
            HttpSession session,
            HttpServletResponse resp) {

        try {
            if (rememberCookie != null) {
                String encriptedString = rememberCookie.getValue();
                String emailPass = AESManager.decrypt(encriptedString);
                String[] split = emailPass.split(":");
                String email = split[0];
                String password = split[1];
                Cookie newCookie = new Cookie(Parameter.REMEMBER_COOKIE, AESManager.encrypt(email + ":" + password));
                newCookie.setMaxAge(360000);
                resp.addCookie(newCookie);

                return login(email, password, "on", resp, session);
            } else {
                return new ModelAndView(Path.WELCOME);
            }
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Path.WELCOME);
            modelAndView.addObject(Parameter.MESSAGE, e.getMessage());
            return modelAndView;
        }

    }


    @PostMapping("/login")
    public ModelAndView login(@RequestParam(Parameter.EMAIL) String email,
                              @RequestParam(Parameter.PASSWORD) String password,
                              @RequestParam(value = Parameter.REMEMBER_ME, required = false) String rememberMe,
                              HttpServletResponse resp,
                              HttpSession session
    ) {
        try {
            authService.login(email, password);
            if (rememberMe != null && rememberMe.equalsIgnoreCase(Parameter.ON)) {
                Cookie cookie = new Cookie(Parameter.REMEMBER_COOKIE, AESManager.encrypt(email + ":" + password));
                cookie.setMaxAge(360000);
                resp.addCookie(cookie);
            }
            UserEntity byEmail = authService.getByEmail(email);
            session.setAttribute(Parameter.ID, byEmail.getId());
            session.setAttribute(Parameter.NAME, byEmail.getName());
            return new ModelAndView(Path.HOME);
        } catch (ValidationException e) {
            ModelAndView modelAndView = new ModelAndView(Path.WELCOME);
            modelAndView.addObject(Parameter.MESSAGE, e.getMessage());
            return modelAndView;
        } catch (VerificationException e) {
            ModelAndView modelAndView = new ModelAndView(Path.VERIFY);
            modelAndView.addObject(Parameter.MESSAGE, e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam(Parameter.NAME) String name,
                                 @RequestParam(Parameter.LAST_NAME) String lastname,
                                 @RequestParam(Parameter.AGE) Integer age,
                                 @RequestParam(Parameter.EMAIL) String email,
                                 @RequestParam(Parameter.PASSWORD) String password) {

        CreateUserDto user = CreateUserDto.builder()
                .name(name)
                .lastname(lastname)
                .email(email)
                .age(LocalDate.now())
                .password(password)
                .build();

        try {
            authService.register(user);
            return new ModelAndView(Path.VERIFY);
        } catch (UserAlreadyExistsException e) {
            ModelAndView modelAndView = new ModelAndView(Path.WELCOME);
            modelAndView.addObject(Parameter.MESSAGE, e.getMessage());
            return modelAndView;
        }

    }

    @PostMapping("/verify")
    public ModelAndView verify(@RequestParam String email,
                               @RequestParam String code) {

        try {
            authService.verify(email, code);
            return new ModelAndView(Path.WELCOME);
        } catch (VerificationException e) {
            ModelAndView modelAndView = new ModelAndView(Path.VERIFY);
            modelAndView.addObject(Parameter.MESSAGE, e.getMessage());
            return modelAndView;
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session,
                               HttpServletResponse resp,
                               @CookieValue(name = Parameter.REMEMBER_COOKIE, required = false) Cookie cookie) {
        session.invalidate();
        if (cookie != null) {
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }
        return new ModelAndView(Path.WELCOME);
    }


}
