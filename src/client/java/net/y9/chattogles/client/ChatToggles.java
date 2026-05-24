package net.y9.chattogles.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;

public class ChatToggles implements ClientModInitializer {
	
	private static final String MOD_ID = "chattoggles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void onInitializeClient() {
		ChatTogglesCommand.registerCommands();
		
		LOGGER.info("Chat Toggles reporting for duty. (if simply, it is initialized)");
	}
	
	
}