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
import com.sammy.fufo.core.setup.content.programming.InstructionTypeRegistry;
import com.sammy.fufo.core.systems.programming.InstructionType;
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
    FlexBox toolbox;
    FlexBox collapser;
    Wrapper actions;

    boolean collapsed = true;
    float scale = 1.5f;
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
//        Transition collapsingTransition = new AxisOrderedPositionEasingTransition(7.0, true, 8.0);
//        Transition expandingTransition = new AxisOrderedPositionEasingTransition(7.0, false, 8.0);
        Transition collapsingTransition = new PositionEasingTransition(7.0);
        Transition expandingTransition = new PositionEasingTransition(7.0);

        screen = new FlexBox()
                .withWidth(new PixelConstraint(width / scale))
                .withHeight(new PixelConstraint(height / scale))
                .withOpacity(0.7f)
                .withColor(Color.GRAY)
                .withAxis(FlexBox.Axis.HORIZONTAL)
                .padded(new Vector2(5, 5));

        // side panel/top left window
        DimensionConstraint collapsedConstraint = new PixelConstraint(110);
        DimensionConstraint expandedConstraint = new PercentageConstraint(1.0);

        sidePanel = new FlexBox()
                .withWidth(new PixelConstraint(80))
                .withHeight(collapsedConstraint)
                .withAlignmentAlongAxis(FlexBox.Alignment.CENTER)
                .withAlignmentAgainstAxis(FlexBox.Alignment.START)
                .withAxis(FlexBox.Axis.VERTICAL)
                .withTransition(positionTransition)
                .withTransition(sizeTransition)
                .withOpacity(0.0f);

        sidePanel.withChild(new FlexBox()
                .padded(new Vector2(2, 2))
                .withHeight(new PixelConstraint(12))
                .withWidth(new PercentageConstraint(1))
                .withColor(Color.BLACK)
                .withOpacity(0.8f)
                .withChild(new TextComponent("Actions", 8f))
                .withTransition(positionTransition)
                .withTransition(sizeTransition)
        );


        toolbox = new FlexBox()
                .withWidth(new PercentageConstraint(1))
                .withHeight(new PercentageConstraint(1))
                .withFlex(1)
                .withAxis(FlexBox.Axis.VERTICAL)
                .padded(new Vector2(3, 3))
                .withColor(Color.BLACK)
                .withOpacity(0.5f)
                .withTransition(positionTransition)
                .withTransition(sizeTransition)
                .withSpacing(3);

        actions = new Wrapper()
                .withWidth(new PercentageConstraint(1))
                .withHeight(new PercentageConstraint(1))
                .withColor(Color.RED)
                .withOpacity(0.0f)
                .withAxis(FlexBox.Axis.HORIZONTAL)
                .withTransition(positionTransition)
                .withTransition(sizeTransition)
                .withSpacing(3);

        // add all the instruction type icons to toolbox
        for (InstructionType instructionType : InstructionTypeRegistry.ORDERED_INSTRUCTION_TYPES) {
            actions.withChild(new InstructionTypeIcon(instructionType).withTransition(expandingTransition));
        }

        toolbox.withChild(actions);

        sidePanel.withChild(toolbox);

        String collapsedText = "▼";
        String expandedText = "▲";

        TextComponent collapsedTextComponent = new TextComponent(collapsedText, 13f);
        collapser = new FlexBox()
                .withWidth(new PercentageConstraint(1.0))
                .withHeight(new PixelConstraint(13))
                .withColor(Color.BLACK)
                .withOpacity(0.6f)
                .withAlignmentAgainstAxis(FlexBox.Alignment.CENTER)
                .withAlignmentAlongAxis(FlexBox.Alignment.CENTER)
                .withTransition(positionTransition)
                .withTransition(sizeTransition)
                .withChild(collapsedTextComponent)
                .withClickHandler((position, button) -> {
                    if(collapsed) {
                        actions.withAxis(FlexBox.Axis.VERTICAL);
                        sidePanel.withHeight(expandedConstraint);
                        sidePanel.reform();

                        for (UIComponent<?> child : actions.getChildren()) {
                            child.withTransition(expandingTransition);
                            ((InstructionTypeIcon) child).showName(true);
                        }
                    } else {
                        actions.withAxis(FlexBox.Axis.HORIZONTAL);
                        sidePanel.withHeight(collapsedConstraint);
                        sidePanel.reform();

                        for (UIComponent<?> child : actions.getChildren()) {
                            child.withTransition(collapsingTransition);
                            ((InstructionTypeIcon) child).showName(false);
                        }
                    }

                    collapsed = !collapsed;
                    collapsedTextComponent.setText(collapsed ? collapsedText : expandedText);
                });

        sidePanel.withChild(collapser);

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
