package team.lodestar.fufo.client.ui.transition;

import team.lodestar.fufo.client.ui.Vector2;
import team.lodestar.fufo.client.ui.component.UIComponent;

public class AxisOrderedPositionEasingTransition extends Transition{

    public double speed = 2.0;
    public double margin = 2.0;
    public boolean xFirst = true;

    public AxisOrderedPositionEasingTransition(double speed, boolean xFirst, double margin) {
        this.speed = speed;
        this.xFirst = xFirst;
        this.margin = margin;
    }

    @Override
    public void apply(UIComponent<?> element, double deltaTime) {
        // ease between animated position and target position taking into account deltatime for consistency

        if (xFirst) {
            element.animatedPosition = new Vector2(element.animatedPosition.x + (element.getPosition().x - element.animatedPosition.x) * speed * deltaTime, element.animatedPosition.y);

            if (Math.abs(element.animatedPosition.x - element.getPosition().x) < margin) {
                // do y
                element.animatedPosition = new Vector2(element.animatedPosition.x, element.animatedPosition.y + (element.getPosition().y - element.animatedPosition.y) * speed * deltaTime);
            }
        } else {
            element.animatedPosition = new Vector2(element.animatedPosition.x, element.animatedPosition.y + (element.getPosition().y - element.animatedPosition.y) * speed * deltaTime);

            if (Math.abs(element.animatedPosition.y - element.getPosition().y) < margin) {
                // do x
                element.animatedPosition = new Vector2(element.animatedPosition.x + (element.getPosition().x - element.animatedPosition.x) * speed * deltaTime, element.animatedPosition.y);
            }
        }
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
