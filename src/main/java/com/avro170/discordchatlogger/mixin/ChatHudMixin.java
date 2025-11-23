package com.avro170.discordchatlogger.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.avro170.discordchatlogger.Config;
import com.avro170.discordchatlogger.DiscordWebhook;
import com.avro170.discordchatlogger.MessageDetector;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    @Inject(method = "addMessage", at = @At("HEAD"))
    private void onAddMessage(Text text, CallbackInfo ci) {
        if (text == null) return;
        String message = text.getString();
        if (message == null || message.isEmpty()) return;

        Config config = Config.load();
        MinecraftClient mc = MinecraftClient.getInstance();
        String serverIp = mc.getCurrentServerEntry() != null ? mc.getCurrentServerEntry().address : "singleplayer";
        String webhookUrl = config.getWebhookForServer(serverIp);

        if (webhookUrl == null || webhookUrl.isEmpty()) return;

        // Извлекаем ник игрока до ":" если это чат-сообщение (например, "Avro170: Привет")
        String sender = "";
        int colon = message.indexOf(":");
        if (colon != -1) {
            sender = message.substring(0, colon).trim();
        }
        if (!sender.isEmpty() && config.getBlackList().contains(sender)) return;

        boolean isDeath = MessageDetector.isDeath(message);
        boolean isJoin = MessageDetector.isJoin(message);
        boolean isLeave = MessageDetector.isLeave(message);

        if (isDeath && !config.logDeathMessages) return;
        if (isJoin && !config.logJoinMessages) return;
        if (isLeave && !config.logLeaveMessages) return;
        if (!isDeath && !isJoin && !isLeave && !config.logChatMessages) return;

        new Thread(() -> {
            try {
                DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                webhook.setContent(message);
                webhook.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
