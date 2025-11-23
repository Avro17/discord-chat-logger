package com.avro170.discordchatlogger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Config {
    private Map<String, String> webhooks = new HashMap<>();
    private List<String> blacklist = new ArrayList<>();
    public boolean logChatMessages = true, logDeathMessages = true, logJoinMessages = true, logLeaveMessages = true;

    public static Config load() {
        File configFile = new File("config/discord-chat-logger.json");
        if (!configFile.exists()) {
            Config config = new Config();
            config.webhooks.put("mc.example.com", "https://discord.com/api/webhooks/...");
            config.save();
            return config;
        }
        try (FileReader reader = new FileReader(configFile)) {
            Type type = new TypeToken<Config>(){}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            Config config = new Config();
            config.save();
            return config;
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter("config/discord-chat-logger.json")) {
            new Gson().toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWebhookForServer(String serverAddress) {
        String cleanAddress = serverAddress.split(":")[0];
        return webhooks.getOrDefault(cleanAddress, "");
    }
    public List<String> getBlackList() { return blacklist; }
}
