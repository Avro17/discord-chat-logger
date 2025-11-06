package com.avro170.discordchatlogger.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.avro170.discordchatlogger.MessageDetector;
import com.avro170.discordchatlogger.DiscordWebhook;
import com.avro170.discordchatlogger.Config;
import java.util.HashSet;
import java.util.Set;

@Mixin(ChatHud.class)
public class ClientPlayNetworkHandlerMixin {
    private static final Set<String> SENT_MESSAGES = new HashSet<>();

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At("HEAD"))
    private void onAddMessage(Text text, CallbackInfo ci) {
        if (text == null) return;

        String message = text.getString();
        if (message == null || message.isEmpty()) return;

        // Только один раз в секунду - отправляй одно сообщение
        String msgHash = message.hashCode() + "_" + (System.currentTimeMillis() / 1000);
        if (!SENT_MESSAGES.add(msgHash)) {
            return;
        }

        // Очистка старых хешей
        if (SENT_MESSAGES.size() > 500) {
            SENT_MESSAGES.clear();
        }

        Config config = Config.load();

        boolean isDeath = MessageDetector.isDeath(message);
        boolean isJoin = MessageDetector.isJoin(message);
        boolean isLeave = MessageDetector.isLeave(message);

        if (isDeath && !config.isLogDeathMessages()) return;
        if (isJoin && !config.isLogJoinMessages()) return;
        if (isLeave && !config.isLogLeaveMessages()) return;
        if (!isDeath && !isJoin && !isLeave && !config.isLogChatMessages()) return;

        if (!config.getWebhookUrl().isEmpty()) {
            new Thread(() -> {
                try {
                    DiscordWebhook webhook = new DiscordWebhook(config.getWebhookUrl());
                    webhook.setContent(message);
                    webhook.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
