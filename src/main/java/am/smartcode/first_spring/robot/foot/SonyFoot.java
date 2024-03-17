package am.smartcode.first_spring.robot.foot;

import org.springframework.stereotype.Component;

@Component
public class SonyFoot implements Foot{

    @Override
    public void walk() {
        System.out.println("walking with Sony foot");
    }
}
