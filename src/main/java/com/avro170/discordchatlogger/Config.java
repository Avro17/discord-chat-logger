package com.avro170.discordchatlogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

    public String webhookUrl = "";
    public boolean logChatMessages = true;
    public boolean logDeathMessages = true;
    public boolean logJoinMessages = true;
    public boolean logLeaveMessages = true;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(
            FabricLoader.getInstance().getConfigDir().toFile(),
            "discord-chat-logger.json"
    );

    public static Config load() {
        if (!CONFIG_FILE.exists()) {
            Config config = new Config();
            config.save();
            return config;
        }
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Config config = GSON.fromJson(reader, Config.class);
            if (config == null) {
                config = new Config();
                config.save();
            }
            return config;
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            Config config = new Config();
            config.save();
            return config;
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }
}
