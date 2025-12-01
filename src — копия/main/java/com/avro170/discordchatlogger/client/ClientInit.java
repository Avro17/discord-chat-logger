package com.avro170.discordchatlogger.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.avro170.discordchatlogger.Config;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            // /blacklist add/remove
            dispatcher.register(literal("blacklist")
                    .then(literal("add")
                            .then(argument("nick", StringArgumentType.word())
                                    .executes(context -> {
                                        String nick = StringArgumentType.getString(context, "nick");
                                        Config config = Config.load();
                                        if (!config.getBlackList().contains(nick)) {
                                            config.getBlackList().add(nick);
                                            config.save();
                                            context.getSource().sendFeedback(Text.literal("User " + nick + " added to blacklist!"));
                                        } else {
                                            context.getSource().sendFeedback(Text.literal("User " + nick + " is already in blacklist!"));
                                        }
                                        return 1;
                                    })))
                    .then(literal("remove")
                            .then(argument("nick", StringArgumentType.word())
                                    .executes(context -> {
                                        String nick = StringArgumentType.getString(context, "nick");
                                        Config config = Config.load();
                                        if (config.getBlackList().contains(nick)) {
                                            config.getBlackList().remove(nick);
                                            config.save();
                                            context.getSource().sendFeedback(Text.literal("User " + nick + " removed from blacklist!"));
                                        } else {
                                            context.getSource().sendFeedback(Text.literal("User " + nick + " not found in blacklist!"));
                                        }
                                        return 1;
                                    }))));

            // /dclreload — перечитать конфиг с диска
            dispatcher.register(literal("dclreload")
                    .executes(context -> {
                        Config cfg = Config.load();
                        context.getSource().sendFeedback(Text.literal("Discord Chat Logger config reloaded. useWebhookSeparation=" + cfg.useWebhookSeparation));
                        return 1;
                    }));
        });
    }
}
