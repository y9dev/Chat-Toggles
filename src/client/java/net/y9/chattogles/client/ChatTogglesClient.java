package net.y9.chattogles.client;

import net.fabricmc.api.ClientModInitializer;
import net.y9.chattogles.ChatToggles;

public class ChatTogglesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ChatToggles.LOGGER.info("Chat Toggles reporting for duty. (if simply, it is initialized)");
	}
}