# Discord Chat Logger

[🇷🇺 Русский](#описание) | [🇬🇧 English](#description)

---

## Описание

Fabric мод для Minecraft, который отправляет сообщения чата на Discord webhook.

### ✨ Возможности
- 💬 Логирование сообщений игроков
- 💀 Логирование смертей
- ✅ Логирование присоединений
- ❌ Логирование покиданий сервера
- ⚙️ Полная настройка через JSON конфиг

### 📋 Требования
- Minecraft: **1.21.8**
- Loader: **Fabric 0.17.3+**
- Java: **21+**

### 🚀 Установка

1. Скачай **Fabric Loader** для 1.21.8
2. Положи JAR мода в папку `.minecraft/mods`
3. Запусти игру
4. Создай Discord webhook:
    - Перейди в **Settings** → **Integrations** → **Webhooks**
    - **New Webhook** → Скопируй URL
5. Отредактируй конфиг: `.minecraft/config/discord-chat-logger.json`

### ⚙️ Конфигурация

Файл: `.minecraft/config/discord-chat-logger.json`

{
"webhookUrl": "https://discord.com/api/webhooks/YOUR_WEBHOOK_URL",
"logChatMessages": true,
"logDeathMessages": true,
"logJoinMessages": true,
"logLeaveMessages": true
}


### 📝 Параметры

| Параметр | Описание | По умолчанию |
|----------|---------|--------------|
| `webhookUrl` | URL Discord webhook | `""` |
| `logChatMessages` | Логировать сообщения чата | `true` |
| `logDeathMessages` | Логировать смерти | `true` |
| `logJoinMessages` | Логировать присоединения | `true` |
| `logLeaveMessages` | Логировать покидания | `true` |

### 🔗 Ссылки

- **GitHub:** https://github.com/Avro17/discord-chat-logger
- **Modrinth:** https://modrinth.com/mod/discord-chat-logger

### 📄 Лицензия

MIT

---

## Description

Fabric mod for Minecraft that sends chat messages to Discord webhook.

### ✨ Features
- 💬 Player chat message logging
- 💀 Death message logging
- ✅ Player join logging
- ❌ Player leave logging
- ⚙️ Full configuration via JSON

### 📋 Requirements
- Minecraft: **1.21.8**
- Loader: **Fabric 0.17.3+**
- Java: **21+**

### 🚀 Installation

1. Download **Fabric Loader** for 1.21.8
2. Place mod JAR in `.minecraft/mods` folder
3. Launch game
4. Create Discord webhook:
    - Go to **Settings** → **Integrations** → **Webhooks**
    - **New Webhook** → Copy URL
5. Edit config: `.minecraft/config/discord-chat-logger.json`

### ⚙️ Configuration

File: `.minecraft/config/discord-chat-logger.json`

{
"webhookUrl": "https://discord.com/api/webhooks/YOUR_WEBHOOK_URL",
"logChatMessages": true,
"logDeathMessages": true,
"logJoinMessages": true,
"logLeaveMessages": true
}


### 📝 Parameters

| Parameter | Description | Default |
|-----------|-------------|---------|
| `webhookUrl` | Discord webhook URL | `""` |
| `logChatMessages` | Log chat messages | `true` |
| `logDeathMessages` | Log deaths | `true` |
| `logJoinMessages` | Log joins | `true` |
| `logLeaveMessages` | Log leaves | `true` |

### 🔗 Links

- **GitHub:** https://github.com/Avro17/discord-chat-logger
- **Modrinth:** https://modrinth.com/mod/discord-chat-logger

### 📄 License

MIT

---

**Made by Avro170** 🎮
