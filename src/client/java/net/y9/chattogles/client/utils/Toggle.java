package net.y9.chattogles.client.utils;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class Toggle {

    public boolean enabled;
    public String name;
    @ConfigEntry.Gui.Tooltip()
    public String chatPreset;


     public Toggle() {
        this.enabled = true;
        this.name = "";
        this.chatPreset = "";
    }

    public Toggle(String name) {
        this.enabled = true;
        this.name = name;
        this.chatPreset = "";
    }


}
