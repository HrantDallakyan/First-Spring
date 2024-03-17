package am.smartcode.first_spring.util;

import javax.servlet.http.Cookie;
import java.util.Arrays;

public class CookieUtil {

    public static Cookie getCookieByName(Cookie[] cookies,String name) {
        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}
