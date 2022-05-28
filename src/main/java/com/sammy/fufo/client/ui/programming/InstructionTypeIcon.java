package com.sammy.fufo.client.ui.programming;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.client.ui.RectTransform;
import com.sammy.fufo.client.ui.Vector2;
import com.sammy.fufo.client.ui.component.FlexBox;
import com.sammy.fufo.client.ui.component.QuickBox;
import com.sammy.fufo.client.ui.component.TextComponent;
import com.sammy.fufo.client.ui.constraint.DimensionConstraint;
import com.sammy.fufo.client.ui.transition.OpacityEasingTransition;
import com.sammy.fufo.core.systems.programming.InstructionType;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class InstructionTypeIcon extends QuickBox {
    public static final ResourceLocation INSTRUCTION_ICON_1 = FufoMod.fufoPath("textures/ui/programming/button.png");

    private InstructionType type;
    public TextComponent name;

    public InstructionTypeIcon(InstructionType type) {
        super(16, 16, INSTRUCTION_ICON_1, Color.WHITE, 1.0f);
        this.type = type;
        this.withChild(new QuickBox(16, 16, type.atlas, Color.WHITE, 1.0f).withUV(new RectTransform(0, this.type.index / 16.0, 1, 1 / 16.0)));
        name = new TextComponent(type.name, 6.0f).withTransition(new OpacityEasingTransition(7.0));

        FlexBox flexBox = new FlexBox().withAxis(Axis.HORIZONTAL).withOpacity(0.0f);
        flexBox.setPosition(new Vector2(16 + 2, 0));
        flexBox.setAbsolutePositioned(true);
        flexBox.setAllocatedSize(new Vector2(60, 16));
        flexBox.useAllocatedSpace(true);
        flexBox.padded(new Vector2(0, 2));
        flexBox.withChild(name);
        this.withChild(flexBox);

        showName(false);
    }

    public void showName(boolean show) {
        if(show) {
            name.withOpacity(1.0f);
        } else {
            name.withOpacity(0.0f);
        }
    }

}
