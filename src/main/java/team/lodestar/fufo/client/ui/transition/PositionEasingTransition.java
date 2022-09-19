package team.lodestar.fufo.client.ui.transition;

import team.lodestar.fufo.client.ui.component.UIComponent;

public class PositionEasingTransition extends Transition{

    public double speed = 2.0;

    public PositionEasingTransition(double speed) {
        this.speed = speed;
    }

    @Override
    public void apply(UIComponent<?> element, double deltaTime) {
        // ease between animated position and target position taking into account deltatime for consistency
        element.animatedPosition = element.animatedPosition.plus(element.getPosition().minus(element.animatedPosition).times(speed * Math.min(deltaTime, 1.0)));
    }

    @Override
    public void jerk(UIComponent<?> element) {
        element.animatedPosition = element.getPosition();
    }


    @Override
    public Type getType() {
        return Type.POSITION;
    }
}
