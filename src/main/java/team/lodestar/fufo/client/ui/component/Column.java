package team.lodestar.fufo.client.ui.component;

/**
 * Simple extension of {@link FlexBox} for a column.
 */
public class Column extends FlexBox {

    public Column() {
        super();
        this.withAxis(Axis.VERTICAL);
    }

}
