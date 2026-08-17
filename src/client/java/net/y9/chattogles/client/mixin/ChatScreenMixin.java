package net.y9.chattogles.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.y9.chattogles.client.config.ToggleState;
import net.y9.chattogles.client.utils.Toggle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {

    @Shadow
    protected EditBox input;

    @Shadow
    CommandSuggestions commandSuggestions;

    @Unique
    private StringWidget preview;

    protected ChatScreenMixin(Component component) {
        super(component);
    }

    @ModifyVariable(method = "handleChatInput", at = @At("HEAD"), argsOnly = true)
    public String modifyChatInput(String string) {
        Toggle toggle = ToggleState.getCurrentToggle();
        boolean shouldModify = ToggleState.isEnabled();

        if (toggle == null || !toggle.enabled || string.startsWith("/") || string.isEmpty()) shouldModify = false;

        if (shouldModify) {
            return ToggleState.getCurrentToggle().chatPreset.trim() + " " + string;
        }

        return string;
    }

    @ModifyArg(method = "handleChatInput", at =
    @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/components/ChatComponent;addRecentChat(Ljava/lang/String;)V")
    )
    public String modifyRecentChat(String string) {
        if (ToggleState.getCurrentToggle() == null) {
            return string;
        }

        String[] msg = string.split(ToggleState.getCurrentToggle().chatPreset);

        if (msg.length < 2) {
            return string;
        }

        return msg[1].trim();
    }

    @Unique
    private Component formatPreview(MutableComponent component) {
        return component.setStyle(Style.EMPTY.withItalic(true).withColor(0xD3D3D3));
    }

    @Inject(method = "onEdited", at = @At("RETURN"))
    public void editPreviewText(String string, CallbackInfo ci) {
        Toggle toggle = ToggleState.getCurrentToggle();

        if (toggle == null) {
            preview.setMessage(formatPreview(Component.literal(input.getValue())));
            return;
        }

        if (string.startsWith("/")) {
            preview.setMessage(formatPreview(Component.literal(input.getValue())));
        } else {
            preview.setMessage(formatPreview(Component.literal(toggle.chatPreset.trim() + " " + input.getValue())));
        }

    }

    @Inject(method = "init", at = @At("TAIL"))
    public void initPreview(CallbackInfo ci) {
        Font font = Minecraft.getInstance().fontFilterFishy;
        Toggle toggle = ToggleState.getCurrentToggle();

        preview = new StringWidget(Component.empty(), Minecraft.getInstance().fontFilterFishy);

        preview.setX(input.getX() + 10 + font.width(input.getValue()));
        preview.setY(input.getY());

        if (toggle == null) {
            preview.setMessage(formatPreview(Component.literal(input.getValue())));
            return;
        }

        if (input.getValue().startsWith("/")) {
            preview.setMessage(formatPreview(Component.literal(input.getValue())));
        } else {
            preview.setMessage(formatPreview(Component.literal(toggle.chatPreset.trim() + " " + input.getValue())));
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void renderPreview(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        Toggle toggle = ToggleState.getCurrentToggle();
        boolean shouldRender = ToggleState.isPreviewEnabled();

        if (input.getValue().isEmpty() || toggle == null || !toggle.enabled) shouldRender = false;


        if (!commandSuggestions.isVisible() && shouldRender) {
            preview.render(guiGraphics, i, j, f);
        }

        preview.setX(input.getX() + 10 + font.width(input.getValue()));
        preview.setY(input.getY());
    }
}
