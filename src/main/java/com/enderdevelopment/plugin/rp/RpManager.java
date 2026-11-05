package com.enderdevelopment.plugin.rp;

import com.enderdevelopment.plugin.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RpManager {

    private final Main plugin;
    private final Map<UUID, String> rpNames = new HashMap<>();
    private File dataFile;
    private YamlConfiguration data;

    public RpManager(Main plugin) {
        this.plugin = plugin;
        load();
    }

    private void load() {
        dataFile = new File(plugin.getDataFolder(), "rpnames.yml");
        if (!dataFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create rpnames.yml: " + e.getMessage());
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
        for (String key : data.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                rpNames.put(uuid, data.getString(key));
            } catch (IllegalArgumentException ignored) {
                // skip invalid keys
            }
        }
    }

    public String displayName(UUID uuid, String vanillaName) {
        String rp = rpNames.get(uuid);
        if (rp == null || rp.isEmpty()) {
            return vanillaName;
        }
        return rp;
    }

    public void setRpName(UUID uuid, String name) {
        if (name == null || name.isEmpty() || name.equalsIgnoreCase("clear")) {
            rpNames.remove(uuid);
        } else {
            rpNames.put(uuid, name);
        }
    }

    public boolean hasRpName(UUID uuid) {
        return rpNames.containsKey(uuid);
    }

    public void saveAll() {
        for (Map.Entry<UUID, String> entry : rpNames.entrySet()) {
            data.set(entry.getKey().toString(), entry.getValue());
        }
        try {
            data.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save rpnames.yml: " + e.getMessage());
        }
    }
}
