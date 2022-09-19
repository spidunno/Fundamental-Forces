package team.lodestar.fufo.client.ui.transition;

import team.lodestar.fufo.client.ui.component.UIComponent;

public class SizeEasingTransition extends Transition{
    public double speed = 2.0;

    public SizeEasingTransition(double speed) {
        this.speed = speed;
    }

    @Override
    public void apply(UIComponent<?> element, double deltaTime) {
        element.animatedSize = element.animatedSize.plus(element.getSize().minus(element.animatedSize).times(speed * Math.min(deltaTime, 1.0)));
    }

    @Override
    public void jerk(UIComponent<?> element) {
        element.animatedSize = element.getSize();
    }


    @Override
    public Type getType() {
        return Type.SIZE;
    }
}
