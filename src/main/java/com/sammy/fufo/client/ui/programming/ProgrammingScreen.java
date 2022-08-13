package com.sammy.fufo.client.ui.programming;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.fufo.client.ui.Vector2;
import com.sammy.fufo.client.ui.component.*;
import com.sammy.fufo.client.ui.component.TextComponent;
import com.sammy.fufo.client.ui.constraint.DimensionConstraint;
import com.sammy.fufo.client.ui.constraint.PercentageConstraint;
import com.sammy.fufo.client.ui.constraint.PixelConstraint;
import com.sammy.fufo.client.ui.transition.AxisOrderedPositionEasingTransition;
import com.sammy.fufo.client.ui.transition.PositionEasingTransition;
import com.sammy.fufo.client.ui.transition.SizeEasingTransition;
import com.sammy.fufo.client.ui.transition.Transition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.Map;

public class ProgrammingScreen extends Screen {

    FlexBox screen;
    FlexBox sidePanel;

    boolean collapsed = true;
    float scale = 1.0f;
    public long previousTime = -1;

    public ProgrammingScreen(Component pTitle) {
        // TODO: lang stuff
        super(new TranslatableComponent("Programming Screen"));
    }

    @Override
    protected void init() {
        super.init();

        // position transition
        PositionEasingTransition positionTransition = new PositionEasingTransition(5.0);
        SizeEasingTransition sizeTransition = new SizeEasingTransition(5.0);
        Transition collapsingTransition = new PositionEasingTransition(7.0);
        Transition expandingTransition = new PositionEasingTransition(7.0);

        screen = new FlexBox()
                .withWidth(new PixelConstraint(width / scale))
                .withHeight(new PixelConstraint(height / scale))
                .withOpacity(0.8f)
                .withColor(Color.WHITE)
                .withAxis(FlexBox.Axis.HORIZONTAL);

        // side panel/top left window
        DimensionConstraint collapsedConstraint = new PixelConstraint(110);
        DimensionConstraint expandedConstraint = new PercentageConstraint(1.0);

        sidePanel = new FlexBox()
                .withWidth(new PixelConstraint(50))
                .withHeight(new PixelConstraint(1000))
                .withAlignmentAlongAxis(FlexBox.Alignment.CENTER)
                .withAlignmentAgainstAxis(FlexBox.Alignment.START)
                .withAxis(FlexBox.Axis.VERTICAL)
                .withTransition(positionTransition)
                .withTransition(sizeTransition)
                .withColor(Color.BLACK)
                .withOpacity(0.5f);

        screen.withChild(sidePanel);
        screen.jerk();

    }



    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return screen.handleClick(new Vector2(pMouseX, pMouseY).div(scale), pButton);
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        this.minecraft = pMinecraft;
        this.itemRenderer = pMinecraft.getItemRenderer();
        this.font = pMinecraft.font;
        this.width = pWidth;
        this.height = pHeight;
        screen.withWidth(new PixelConstraint(width / scale)).withHeight(new PixelConstraint(height / scale));
        screen.reform();
        screen.jerk();
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        long currentTime = System.currentTimeMillis();
        if(previousTime == -1)
            previousTime = currentTime;
        long deltaTime = currentTime - previousTime;
        double delta = deltaTime / 1000.0;

        pPoseStack.scale(scale, scale, 1);

        screen.render(pPoseStack, delta);

        previousTime = currentTime;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
//        Minecraft.getInstance().player.chat("dragged " + pDragX + " " + pDragY + " with pos " + pMouseX + " " + pMouseY);
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }
}
