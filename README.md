# EnderRP Minecraft Plugin

EnderRP is a Minecraft roleplay server plugin that adds an economy system with currency, a playtime income system, and a graphical shop interface. It provides commands for managing balances, transferring money between players, setting roleplay names, and purchasing items from the in-game Sun Shop. The plugin stores all data in YAML files and integrates directly with the Paper API for a seamless server experience.

## Install

1. Download the `plugin.jar` file from the releases section.
2. Stop your Minecraft server.
3. Place the `plugin.jar` file into your `plugins` folder.
4. Restart your server to generate the default configuration files.

## Use

1. Start your server and verify EnderRP is enabled in the console.
2. New players automatically receive the starting balance (default: 100 coins).
3. Use `/money` to check your balance.
4. Use `/pay <player> <amount>` to transfer coins to another player.
5. Use `/rp <name>` to set a roleplay display name, or `/rp clear` to reset it.
6. Use `/shop` to open the Sun Shop GUI and click item icons to purchase.
7. Admins can use `/setmoney <player> <amount>` (requires `enderrp.admin` permission).
8. Players earn coins automatically every configured interval while online, up to a daily cap.

The income system rewards players for time spent online with configurable rewards and daily limits. All player balances and roleplay names are saved to `balances.yml` and `rpnames.yml` in the plugin data folder, ensuring persistence across restarts.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/42832b18-cf22-4ba5-a879-6df10f5f2244?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=42832b18-cf22-4ba5-a879-6df10f5f2244
