package com.avro170.discordchatlogger.client;

import net.fabricmc.api.ClientModInitializer;
import com.avro170.discordchatlogger.Config;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Config.load();
    }
}
