package com.avro170.discordchatlogger;

import net.minecraft.client.MinecraftClient;
import java.util.HashSet;
import java.util.Set;

public class PlayerTracker {

    // Получить список всех онлайн игроков из таба (player list)
    public static Set<String> getOnlinePlayers() {
        Set<String> players = new HashSet<>();
        MinecraftClient client = MinecraftClient.getInstance();

        try {
            if (client != null && client.getNetworkHandler() != null) {
                // Получаем список игроков из player list
                var playerList = client.getNetworkHandler().getPlayerList();
                if (playerList != null) {
                    for (var entry : playerList) {
                        if (entry != null && entry.getProfile() != null) {
                            String playerName = entry.getProfile().getName();
                            if (playerName != null && !playerName.isEmpty()) {
                                players.add(playerName);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    // Проверить является ли слово именем игрока
    public static boolean isPlayerName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        try {
            Set<String> onlinePlayers = getOnlinePlayers();
            return onlinePlayers.contains(name);
        } catch (Exception e) {
            return false;
        }
    }
}
