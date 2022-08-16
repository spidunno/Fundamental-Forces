package team.lodestar.fufo.client.ui.transition;

import team.lodestar.fufo.client.ui.component.UIComponent;

public class OpacityEasingTransition extends Transition{
    public double speed = 2.0;

    public OpacityEasingTransition(double speed) {
        this.speed = speed;
    }

    @Override
    public void apply(UIComponent<?> element, double deltaTime) {
        element.animatedOpacity = (float) (element.animatedOpacity + (element.getOpacity() - element.animatedOpacity) * (speed * Math.min(deltaTime, 1.0)));
    }

    @Override
    public void jerk(UIComponent<?> element) {
        element.animatedOpacity = element.getOpacity();
    }

    @Override
    public Type getType() {
        return Type.OPACITY;
    }
}
