package com.enderdevelopment.plugin;

import com.enderdevelopment.plugin.commands.MoneyCommand;
import com.enderdevelopment.plugin.commands.PayCommand;
import com.enderdevelopment.plugin.commands.RpCommand;
import com.enderdevelopment.plugin.commands.SetMoneyCommand;
import com.enderdevelopment.plugin.commands.ShopCommand;
import com.enderdevelopment.plugin.economy.EconomyManager;
import com.enderdevelopment.plugin.economy.IncomeManager;
import com.enderdevelopment.plugin.listener.IncomeListener;
import com.enderdevelopment.plugin.listener.JoinListener;
import com.enderdevelopment.plugin.rp.RpManager;
import com.enderdevelopment.plugin.shop.ShopManager;
import com.enderdevelopment.plugin.shop.ShopListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private EconomyManager economyManager;
    private IncomeManager incomeManager;
    private RpManager rpManager;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.economyManager = new EconomyManager(this);
        this.incomeManager = new IncomeManager(this, economyManager);
        this.incomeManager.start();
        this.rpManager = new RpManager(this);
        this.shopManager = new ShopManager(this, economyManager);

        getCommand("money").setExecutor(new MoneyCommand(economyManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager));
        getCommand("rp").setExecutor(new RpCommand(rpManager));
        getCommand("shop").setExecutor(new ShopCommand(shopManager));
        getCommand("setmoney").setExecutor(new SetMoneyCommand(economyManager));

        getServer().getPluginManager().registerEvents(new JoinListener(rpManager), this);
        getServer().getPluginManager().registerEvents(new ShopListener(shopManager), this);
        getServer().getPluginManager().registerEvents(new IncomeListener(incomeManager), this);

        getLogger().info("EnderRP enabled.");
    }

    @Override
    public void onDisable() {
        if (incomeManager != null) {
            incomeManager.stop();
        }
        if (economyManager != null) {
            economyManager.saveAll();
        }
        if (rpManager != null) {
            rpManager.saveAll();
        }
        getLogger().info("EnderRP disabled.");
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public RpManager getRpManager() {
        return rpManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }
}
