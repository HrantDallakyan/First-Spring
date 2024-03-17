package am.smartcode.first_spring.robot.foot;

import org.springframework.stereotype.Component;

@Component
public class ToshibaFoot implements Foot {

    @Override
    public void walk() {
        System.out.println("walking with Toshiba foot");
    }

}
