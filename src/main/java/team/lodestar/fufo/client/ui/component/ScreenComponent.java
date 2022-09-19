package team.lodestar.fufo.client.ui.component;

import team.lodestar.fufo.client.ui.Vector2;

import java.util.function.Function;

public class ScreenComponent extends FlexBox {

    private Function<Vector2, Vector2> localToScreenSpace = (v) -> v;

    public ScreenComponent(Function<Vector2, Vector2> localToScreenSpace) {
        this.localToScreenSpace = localToScreenSpace;
    }

    @Override
    public Vector2 getScreenPosition(Vector2 position) {
        return localToScreenSpace.apply(position);
    }
}
