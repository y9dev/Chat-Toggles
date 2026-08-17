package net.y9.chattogles.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.y9.chattogles.client.utils.Toggle;

import java.util.List;

@Config(name = "chattoggles")
public class TogglesConfig implements ConfigData {
    public boolean isEnabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean isPreviewEnabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean shouldChat = false;

    public List<Toggle> toggles = List.of(new Toggle("Default"));
}
