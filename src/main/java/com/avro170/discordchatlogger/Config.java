package com.avro170.discordchatlogger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Config {

    public boolean useWebhookSeparation = false;
    public String defaultWebhook = "https://discord.com/api/webhooks/...";

    private Map<String, String> webhooks = new HashMap<>();
    private List<String> blacklist = new ArrayList<>();

    public boolean logChatMessages = true;
    public boolean logDeathMessages = true;
    public boolean logJoinMessages = true;
    public boolean logLeaveMessages = true;

    public static Config load() {
        File configFile = new File("config/discord-chat-logger.json");
        if (!configFile.exists()) {
            Config config = new Config();
            config.defaultWebhook = "https://discord.com/api/webhooks/...";
            config.save();
            return config;
        }

        try (FileReader reader = new FileReader(configFile)) {
            Type type = new TypeToken<Config>() {}.getType();
            Config cfg = new Gson().fromJson(reader, type);
            if (cfg == null) {
                cfg = new Config();
                cfg.save();
            }
            return cfg;
        } catch (IOException e) {
            e.printStackTrace();
            Config cfg = new Config();
            cfg.save();
            return cfg;
        }
    }

    public void save() {
        try {
            File dir = new File("config");
            if (!dir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                dir.mkdirs();
            }

            try (FileWriter writer = new FileWriter("config/discord-chat-logger.json")) {
                new Gson().toJson(this, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWebhookForServer(String serverAddress) {
        if (serverAddress == null || serverAddress.isEmpty()) {
            return defaultWebhook;
        }

        // убираем порт
        String cleanAddress = serverAddress.split(":")[0];

        // убираем возможные [], / и пробелы, приводим к lower-case
        cleanAddress = cleanAddress
                .replace("[", "")
                .replace("]", "")
                .replace("/", "")
                .trim()
                .toLowerCase();

        if (useWebhookSeparation) {
            if (webhooks.containsKey(cleanAddress)) {
                return webhooks.get(cleanAddress);
            }
        }

        return defaultWebhook;
    }

    public List<String> getBlackList() {
        return blacklist;
    }

    public Map<String, String> getWebhooks() {
        return webhooks;
    }
}
