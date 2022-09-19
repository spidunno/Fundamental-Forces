package team.lodestar.fufo.client.ui.transition;

import team.lodestar.fufo.client.ui.component.UIComponent;

import java.util.HashMap;

public class OpacityPulsingTransition extends Transition{
    // period in seconds
    public double period = 1.0;

    public OpacityPulsingTransition(double period) {
        this.period = period;
    }

    private HashMap<UIComponent<?>, Double> elapsed = new HashMap<UIComponent<?>, Double>();

    @Override
    public void apply(UIComponent<?> element, double deltaTime) {
        if(!elapsed.containsKey(element)) {
            elapsed.put(element, 0.0);
        }
        elapsed.put(element, elapsed.get(element) + deltaTime);

        // pulse opacity paying respect to period & element.getOpacity()
        double opacity = (Math.sin(elapsed.get(element) / period * Math.PI) / 2 + 0.5) * element.getOpacity();
        element.animatedOpacity = (float) opacity;
    }

    @Override
    public void jerk(UIComponent<?> element) {

    }


    @Override
    public Type getType() {
        return Type.OPACITY;
    }
}
