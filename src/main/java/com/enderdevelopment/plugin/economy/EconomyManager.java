package com.enderdevelopment.plugin.economy;

import com.enderdevelopment.plugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {

    private final Main plugin;
    private final Map<UUID, Double> balances = new HashMap<>();
    private File dataFile;
    private YamlConfiguration data;

    public EconomyManager(Main plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        dataFile = new File(plugin.getDataFolder(), "balances.yml");
        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create balances.yml: " + e.getMessage());
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
        for (String key : data.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                balances.put(uuid, data.getDouble(key));
            } catch (IllegalArgumentException ignored) {
                // skip invalid keys
            }
        }
    }

    public double getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, plugin.getConfig().getDouble("start-money", 100.0));
    }

    public void setBalance(UUID uuid, double amount) {
        balances.put(uuid, amount);
    }

    public void addBalance(UUID uuid, double amount) {
        setBalance(uuid, getBalance(uuid) + amount);
    }

    public void subtractBalance(UUID uuid, double amount) {
        setBalance(uuid, Math.max(0.0, getBalance(uuid) - amount));
    }

    public boolean hasAtLeast(UUID uuid, double amount) {
        return getBalance(uuid) >= amount;
    }

    public void saveAll() {
        for (Map.Entry<UUID, Double> entry : balances.entrySet()) {
            data.set(entry.getKey().toString(), entry.getValue());
        }
        try {
            data.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save balances.yml: " + e.getMessage());
        }
    }
}
