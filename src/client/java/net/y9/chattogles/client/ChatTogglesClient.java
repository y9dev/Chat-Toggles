package net.y9.chattogles.client;

import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.y9.chattogles.client.config.ToggleState;
import net.y9.chattogles.client.config.TogglesConfig;
import net.y9.chattogles.client.utils.Toggle;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;

public class ChatTogglesClient implements ClientModInitializer {

    public static final String MOD_ID = "chattoggles";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "chattogles.keymap_category"));

    private static final ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();

    private final KeyMapping controlKey = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "chattogles.key.toggles_control",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_X,
                    CATEGORY
            )
    );
    private final boolean[] previousKeys = new boolean[9];

    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(this::handleControlKey);

        AutoConfigClient.getGuiRegistry(TogglesConfig.class).registerTypeProvider((i18n, field, config, defaults, guiProvider) -> {
            return Collections.singletonList(ENTRY_BUILDER.startSubCategory(
                    Component.literal(((Toggle)config).name), guiProvider.getAndTransform(i18n, field, config, defaults, guiProvider)
            ).setExpanded(false).build());
        }, Toggle.class);

        AutoConfig.register(TogglesConfig.class, GsonConfigSerializer::new);

        Toggle toggle = ToggleState.getToggles().getFirst();

        if (toggle != null) {
            ToggleState.setCurrentToggle(toggle);
        }

        LOGGER.info("Chat Toggles reporting for duty. (it is initialized)");
    }

    public static TogglesConfig getConfig() {
        return AutoConfig.getConfigHolder(TogglesConfig.class).getConfig();
    }

    private void handleControlKey(Minecraft client) {
        if (client.screen != null || client.player == null) {
            return;
        }

        long window = client.getWindow().handle();

        if (!controlKey.isDown()) {
            Arrays.fill(previousKeys, false);
            return;
        }

        for (int i = 0; i < ToggleState.getToggles().size(); i++) {
            int key = GLFW.GLFW_KEY_1 + i;
            boolean pressed = GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;

            if (pressed && !previousKeys[i]) {
                Toggle toggle = ToggleState.getToggles().get(i);
                boolean shouldOpenChat = ToggleState.shouldChat();

                if (toggle == null) {
                    return;
                }

                ToggleState.setCurrentToggle(toggle);

                if (shouldOpenChat) {
                    client.setScreen(new ChatScreen("", false));
                    return;
                }

                if (toggle.chatPreset.isEmpty()) {
                    client.player.displayClientMessage(Component.translatable("chattoggles.toggle.selected_default"), true);
                    return;
                }

                client.player.displayClientMessage(Component.literal(String.format(Component.translatable("chattoggles.toggle.selected").getString(), toggle.name)), true);
            }

            previousKeys[i] = pressed;
        }
    }

}