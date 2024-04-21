package me.carrot0322.triplesense.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.event.impl.ChatEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.triplesense.util.etc.Util.EVENT_BUS;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessageHook(String content, CallbackInfo ci) {
        ChatEvent event = new ChatEvent(content);
        EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(@NotNull String message, CallbackInfo ci) {
        if (message.startsWith(TripleSense.commandManager.getPrefix())) {
            try {
                TripleSense.commandManager.getDispatcher().execute(
                        message.substring(TripleSense.commandManager.getPrefix().length()),
                        TripleSense.commandManager.getSource()
                );
            } catch (CommandSyntaxException ignored) {}

            ci.cancel();
        }
    }
}