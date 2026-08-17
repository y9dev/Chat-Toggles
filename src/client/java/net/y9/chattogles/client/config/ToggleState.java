package net.y9.chattogles.client.config;

import net.y9.chattogles.client.ChatTogglesClient;
import net.y9.chattogles.client.utils.Toggle;

import java.util.List;

public class ToggleState {
    private static Toggle currentToggle;
    private static final Toggle nullToggle = new Toggle("Default");

    public static Toggle getCurrentToggle() {
        return currentToggle;
    }

    public static Toggle getNullToggle() {
        return nullToggle;
    }

    public static void setCurrentToggle(Toggle toggle) {
        currentToggle = toggle;
    }

    public static boolean isEnabled() {
        return ChatTogglesClient.getConfig().isEnabled;
    }

    public static boolean isPreviewEnabled() {
        return ChatTogglesClient.getConfig().isPreviewEnabled;
    }

    public static boolean shouldChat() {
        return ChatTogglesClient.getConfig().shouldChat;
    }

    public static List<Toggle> getToggles() {
        return ChatTogglesClient.getConfig().toggles;
    }


}
