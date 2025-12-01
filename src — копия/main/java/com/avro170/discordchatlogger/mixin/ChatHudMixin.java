package com.avro170.discordchatlogger.mixin;

import com.avro170.discordchatlogger.Config;
import com.avro170.discordchatlogger.DiscordWebhook;
import com.avro170.discordchatlogger.MessageDetector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {

    // ОБРАБАТЫВАЕМ только новую перегрузку 1.21.10 – она ловит и ванильный чат, и join/leave
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
            at = @At("HEAD"))
    private void onAddMessageSigned(Text text, MessageSignatureData signature, MessageIndicator indicator, CallbackInfo ci) {
        handleMessage(text);
    }

    // Старую версию addMessage(Text) вообще не трогаем, чтобы не ловить дубли на серверах,
    // где оба метода вызываются цепочкой.

    private void handleMessage(Text text) {
        System.out.println("[DCL] ChatHudMixin fired, text = " + text);
        if (text == null) return;

        String message = text.getString();
        System.out.println("[DCL] message.getString() = " + message);
        if (message == null || message.isEmpty()) return;

        Config config = Config.load();
        MinecraftClient mc = MinecraftClient.getInstance();
        String serverIp = mc.getCurrentServerEntry() != null ? mc.getCurrentServerEntry().address : "singleplayer";
        String webhookUrl = config.getWebhookForServer(serverIp);
        System.out.println("[DCL] webhookUrl = " + webhookUrl);
        if (webhookUrl == null || webhookUrl.isEmpty()) return;

        String sender = extractNickUniversal(message);
        System.out.println("[DCL] sender = " + sender);

        if (sender != null && !sender.isEmpty()) {
            for (Object o : config.getBlackList()) {
                String blacklistNick = String.valueOf(o);
                if (sender.equalsIgnoreCase(blacklistNick)) {
                    System.out.println("[DCL] blocked by blacklist: " + sender);
                    return;
                }
            }
        }

        boolean isDeath = MessageDetector.isDeath(message);
        boolean isJoin = MessageDetector.isJoin(message);
        boolean isLeave = MessageDetector.isLeave(message);
        System.out.println("[DCL] flags: death=" + isDeath + ", join=" + isJoin + ", leave=" + isLeave);

        if (isDeath && !config.logDeathMessages) return;
        if (isJoin && !config.logJoinMessages) return;
        if (isLeave && !config.logLeaveMessages) return;
        if (!isDeath && !isJoin && !isLeave && !config.logChatMessages) return;

        new Thread(() -> {
            try {
                System.out.println("[DCL] sending to webhook: " + message);
                DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                webhook.setContent(message);
                webhook.execute();
                System.out.println("[DCL] sent OK");
            } catch (Exception e) {
                System.out.println("[DCL] ERROR while sending webhook");
                e.printStackTrace();
            }
        }).start();
    }

    // "<Nick> msg" и "Nick: msg"
    private String extractNickUniversal(String message) {
        int lt = message.indexOf('<');
        int gt = message.indexOf('>');
        if (lt != -1 && gt != -1 && gt > lt + 1) {
            String possible = message.substring(lt + 1, gt).trim();
            if (!possible.isEmpty()) {
                return possible;
            }
        }

        int colon = message.indexOf(':');
        if (colon > 0) {
            String possible = message.substring(0, colon).trim();
            if (!possible.isEmpty() && !possible.equalsIgnoreCase("system")) {
                return possible;
            }
        }

        return null;
    }
}
