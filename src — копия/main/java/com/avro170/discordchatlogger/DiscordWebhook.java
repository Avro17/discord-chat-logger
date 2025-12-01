package com.avro170.discordchatlogger;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhook {
    private final String url;
    private String content;
    private String username = "Minecraft Chat";

    public DiscordWebhook(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void execute() throws Exception {
        if (content == null || url == null || url.isEmpty()) {
            return;
        }

        URL urlObj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Discord Chat Logger");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        String jsonPayload = buildJsonPayload();
        byte[] postDataBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);
        connection.setFixedLengthStreamingMode(postDataBytes.length);

        try (OutputStream os = connection.getOutputStream()) {
            os.write(postDataBytes);
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            System.err.println("Discord webhook error: " + responseCode);
        }
        connection.disconnect();
    }

    private String buildJsonPayload() {
        return "{\"username\":\"" + escapeJson(username) + "\",\"content\":\"" + escapeForDiscord(content) + "\"}";
    }

    private String escapeForDiscord(String text) {
        if (text == null) return "";
        return escapeJson(text); // теперь никаких выделений!!
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
