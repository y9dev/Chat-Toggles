package net.y9.chattogles.client.utils;

public class Toggle {
	private char name;
	private int color;
	private String chatPreset;

	public Toggle(char name, int color, String chatPreset) {
		this.name = name;
		this.color = color;
		this.chatPreset = chatPreset;
	}

	public Toggle(int color) {
		this('N', color, "");
	}

	public Toggle(String chatPreset) {
		this('N', 0xFFFFFF, chatPreset);
	}

	public Toggle() {
		this('N', 0xFFFFFF, "");
	}
	
	public int getColor() {
		return this.color;
	}
	public String getChatPreset() {
		return this.chatPreset;
	}
	public char getName() {
		return this.name;
	}

}
