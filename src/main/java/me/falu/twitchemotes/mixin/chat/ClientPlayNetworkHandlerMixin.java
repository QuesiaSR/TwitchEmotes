package me.falu.twitchemotes.mixin.chat;

import me.falu.twitchemotes.TwitchEmotes;
import me.falu.twitchemotes.TwitchEmotesOptions;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "sendChatMessage", at = @At("TAIL"))
    private void sendTwitchMessage(String content, CallbackInfo ci) {
        if (TwitchEmotes.CHAT_CONNECTED && TwitchEmotesOptions.CHAT_BACK.getValue()) {
            TwitchEmotes.sendChatMessage(content);
        }
    }
}
