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

            // /blacklist add/remove/list — теперь это список слов/масок
            dispatcher.register(literal("blacklist")
                    .then(literal("add")
                            // greedyString позволяет писать русские слова и фразы с пробелами
                            .then(argument("word", StringArgumentType.greedyString())
                                    .executes(context -> {
                                        String word = StringArgumentType.getString(context, "word");
                                        Config config = Config.load();
                                        if (!config.getBlackList().contains(word)) {
                                            config.getBlackList().add(word);
                                            config.save();
                                            context.getSource().sendFeedback(Text.literal("Word \"" + word + "\" added to blacklist!"));
                                        } else {
                                            context.getSource().sendFeedback(Text.literal("Word \"" + word + "\" is already in blacklist!"));
                                        }
                                        return 1;
                                    })))
                    .then(literal("remove")
                            .then(argument("word", StringArgumentType.greedyString())
                                    .executes(context -> {
                                        String word = StringArgumentType.getString(context, "word");
                                        Config config = Config.load();
                                        if (config.getBlackList().contains(word)) {
                                            config.getBlackList().remove(word);
                                            config.save();
                                            context.getSource().sendFeedback(Text.literal("Word \"" + word + "\" removed from blacklist!"));
                                        } else {
                                            context.getSource().sendFeedback(Text.literal("Word \"" + word + "\" not found in blacklist!"));
                                        }
                                        return 1;
                                    })))
                    .then(literal("list")
                            .executes(context -> {
                                Config config = Config.load();
                                if (config.getBlackList().isEmpty()) {
                                    context.getSource().sendFeedback(Text.literal("Blacklist is empty."));
                                } else {
                                    String joined = String.join(", ", config.getBlackList());
                                    context.getSource().sendFeedback(Text.literal("Blacklist: " + joined));
                                }
                                return 1;
                            }))
            );

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
