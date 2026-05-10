package net.y9.chattogles.client.screen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.y9.chattogles.client.utils.Toggle;

public class TogglesScreen extends Screen {

	private List<Toggle> toggles = new ArrayList<Toggle>();
	private int CIRCLE_RADIUS = 60;
	private int BUTTON_SIZE = 20;
	
	private String[] RANDOM_WORDS = {"Apple", "Table", "River", "Cloud", "Stone", "Minecraft", "Pig", "Creeper", "CraftTable"};

	public TogglesScreen() {
		super(Component.literal("Chat Toggles"));
	}
	
	// [DEBUG] Only for debug, dont load config buttons!
	public TogglesScreen(int count) {
		super(Component.literal("Chat Toggles"));
		for (int i = 0; i < count; i++) {
			Random r = new Random();
			
			int randomHex = r.nextInt(0, 16777215);
			char randomLetter = (char)('A' + r.nextInt(26));
			String randomWord = RANDOM_WORDS[r.nextInt(RANDOM_WORDS.length)] + ": " + randomLetter;
			
			toggles.add(new Toggle(randomLetter, randomHex, randomWord));
		}
	}

	@Override
	protected void init() {
		toggles.clear();

		List<Point> points = getTogglesPoints(toggles.size(), CIRCLE_RADIUS, this.width / 2, this.height / 2);

		for (int i = 0; i < toggles.size(); i++) {
			Toggle toggle = toggles.get(i);

			this.addRenderableWidget(
					Button.builder(Component.literal(Objects.toString(toggle.getName())).withColor(toggle.getColor()),
							button -> {
								ChatScreen chatScreen = new ChatScreen(toggle.getChatPreset(), false);
								Minecraft.getInstance().setScreen(chatScreen);
							}).bounds(points.get(i).x - BUTTON_SIZE / 2, points.get(i).y - BUTTON_SIZE / 2, BUTTON_SIZE,
									BUTTON_SIZE)
							.build());
		}
	}
	
	@Override
	public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	private List<Point> getTogglesPoints(int count, int radius, int cx, int cy) {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			double angle = (Math.PI * 2F * i) / count;
			points.add(new Point((int) (cx + radius * Math.cos(angle)), (int) (cy + radius * Math.sin(angle))));
		}
		return points;
	}
}
