package am.smartcode.first_spring.robot;


import am.smartcode.first_spring.robot.foot.Foot;
import am.smartcode.first_spring.robot.hand.Hand;
import am.smartcode.first_spring.robot.head.Head;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Robot {

    private Foot foot;
    private Hand hand;
    private Head head;

    public void walk() {
        foot.walk();
    }

    public void hit() {
        hand.hit();
    }

    public void think() {
        head.think();
    }

    @Autowired
    public void setFoot(@Qualifier("sonyFoot") Foot foot) {
        this.foot = foot;
    }

    @Autowired
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Autowired
    public void setHead(Head head) {
        this.head = head;
    }
}
