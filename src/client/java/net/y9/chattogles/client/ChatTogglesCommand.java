package net.y9.chattogles.client;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.y9.chattogles.client.screen.TogglesScreen;

public class ChatTogglesCommand {
	protected static void registerCommands() {
		// [DEBUG]
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext) -> {
			dispatcher.register(ClientCommandManager.literal("chattoggles").then(ClientCommandManager.literal("debug")
					.then(ClientCommandManager.literal("openScreen").executes((ctx) -> {
						ctx.getSource().getClient().execute(() -> {
							Minecraft.getInstance().setScreen(new TogglesScreen(6));
						});
						return 1;
					}).then(ClientCommandManager.argument("count", IntegerArgumentType.integer()).executes(ctx -> {
						ctx.getSource().getClient().execute(() -> {
							int count = IntegerArgumentType.getInteger(ctx, "count");
							Minecraft.getInstance().setScreen(new TogglesScreen(count));
						});
						return 1;
					})))));
		});
	}

}
