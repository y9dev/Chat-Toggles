package net.y9.chattogles.client.screen.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ToggleButton extends Button {

	private float animateProgress = 0f;
	private final int startOffsetX;
	private final int startOffsetY;
	private static final long DURATION = 300; // ms

	public ToggleButton(int x, int y, int width, int height, Component component, OnPress onPress,
			CreateNarration createNarration) {
		super(x, y, width, height, component, onPress, createNarration);
		this.startOffsetX = x;
		this.startOffsetY = y;
	}

	@Override
	protected void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, float deltaTicks) {
		var pose = guiGraphics.pose();
		
		if (animateProgress < 1f)
			animateProgress += deltaTicks * 0.1f;
		if (animateProgress > 1f)
			animateProgress = 1f;

		float eased = 1f - (float) Math.pow(1f - animateProgress, 3);

		float x = this.startOffsetX * (1f - eased);
		float y = this.startOffsetY * (1f - eased);
		float alpha = 1f - (float) Math.pow(1f - Math.max(0f, (animateProgress - 0.1f) / 0.9f), 3);
		float scale = 1f - (float) Math.pow(1f - Math.max(0f, (animateProgress - 0.5f) / 0.5f), 3);

		pose.pushMatrix();

		this.setPosition((int) x, (int) y);
		this.setAlpha(alpha);
		pose.scale(scale, scale);

		this.renderDefaultSprite(guiGraphics);
		this.renderDefaultLabel(guiGraphics.textRendererForWidget(this, GuiGraphics.HoveredTextEffects.NONE));

		pose.popMatrix();
	}

}