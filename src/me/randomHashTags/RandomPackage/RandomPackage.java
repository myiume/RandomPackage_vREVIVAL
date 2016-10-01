package me.randomHashTags.RandomPackage;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.randomHashTags.RandomPackage.Data.Data;
import me.randomHashTags.RandomPackage.Data.EnchantType;
import me.randomHashTags.RandomPackage.Data.Rarity;
import me.randomHashTags.RandomPackage.Enchants.BlockBreakEventEnchants;
import me.randomHashTags.RandomPackage.Enchants.BowEnchants;
import me.randomHashTags.RandomPackage.Enchants.BowEnchants2;
import me.randomHashTags.RandomPackage.Enchants.EnchantsThatAddPotionEffects;
import me.randomHashTags.RandomPackage.Enchants.EntityDamageByEntityEnchants;
import me.randomHashTags.RandomPackage.Enchants.PlayerItemConsumeEnchants;
import me.randomHashTags.RandomPackage.Enchants.PlayerMoveEventEnchants;
import me.randomHashTags.RandomPackage.Events.BookRevealFirework;
import me.randomHashTags.RandomPackage.Events.DisabledEnchants;
import me.randomHashTags.RandomPackage.Events.SuccessAndDestroySystem;
import me.randomHashTags.RandomPackage.Events.alchemist.alchemistEvents;
import me.randomHashTags.RandomPackage.Events.dropPackages.dropPackageEvents;
import me.randomHashTags.RandomPackage.Events.enchanter.enchanterEvents;
import me.randomHashTags.RandomPackage.Events.filter.filterEvents;
import me.randomHashTags.RandomPackage.Events.givedpItems.givedpEvents;
import me.randomHashTags.RandomPackage.Events.gkit.gkitEvents;
import me.randomHashTags.RandomPackage.Events.global.globalEvents;
import me.randomHashTags.RandomPackage.Events.soul.soulEvents;
import me.randomHashTags.RandomPackage.Events.tinkerer.tinkererEvents;
import me.randomHashTags.RandomPackage.commands.randomPackageCommands;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class RandomPackage extends JavaPlugin implements Listener {
	private PluginManager pm = getServer().getPluginManager();
	private static Plugin plugin;
	public static Plugin getPlugin() { return plugin; }
	public static String prefix = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[" + ChatColor.GREEN + ChatColor.BOLD + "RandomPackage" + ChatColor.DARK_GRAY + ChatColor.BOLD + "]" + ChatColor.RESET + " ";
	
	public static File TinkererEnchanterAlchemistF, tinkererXPF, EnabledEnchantsF, SoulF, globalEventsF, BookOptionsF, givedpItemsF, gkitsF, messagesF, commandsPermissionsF, enchantTypeF, placeholdersF, gkitDataF, filterF, filterDataF, alchemistUpgradeF, dropPackageF;
	public static FileConfiguration TinkererEnchanterAlchemist, tinkererXP, EnabledEnchants, Soul, globalEvents, BookOptions, givedpItems, gkits, messages, commandsPermissions, enchantType, placeholders, gkitData, filter, filterData, alchemistUpgrade, dropPackage;
	
	private static File legendaryDPF, ultimateDPF, eliteDPF, uniqueDPF, simpleDPF;
	private static FileConfiguration legendaryDP, ultimateDP, eliteDP, uniqueDP, simpleDP;
	
	public static Economy econ = null;
	private boolean setupEconomy() {
	    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
	    if(rsp == null) { return false; } econ = rsp.getProvider(); return econ != null;
	}
	public static void createEnchant(String enchantName, String itemLore, int maxLevels, EnchantType enchantType, Rarity enchantRarity, boolean canBeObtainableInCustomBooks) {
		
	}
	public void onEnable() {
		plugin = this;
		configs();
		commands();
		events();
		setupEconomy();
		if(!(setupEconomy())) {
			if(!(getServer().getPluginManager().getPlugin("Vault") == null)) {
				Bukkit.broadcastMessage(RandomPackage.prefix + ChatColor.YELLOW + "Download an economy plugin to enable all RandomPackage features!");
			}
            return;
		}
	}
	
	private void configs() {
		this.saveDefaultConfig();
		TinkererEnchanterAlchemistF = new File(getDataFolder(), "TinkererEnchanterAlchemist.yml");
		EnabledEnchantsF = new File(getDataFolder(), "enabled-enchants.yml");
		SoulF = new File(getDataFolder(), "soul.yml");
		globalEventsF = new File(getDataFolder(), "global-events.yml");
		BookOptionsF = new File(getDataFolder(), "book-options.yml");
		givedpItemsF = new File(getDataFolder(), "givedp-items.yml");
		gkitsF = new File(getDataFolder(), "gkits.yml");
		messagesF = new File(getDataFolder(), "messages.yml");
		commandsPermissionsF = new File(getDataFolder(), "commands-permissions.yml");
		enchantTypeF = new File(getDataFolder(), "enchant-types.yml");
		placeholdersF = new File(getDataFolder(), "_placeholders.yml");
		gkitDataF = new File(getDataFolder(), "data-gkits.yml");
		filterF = new File(getDataFolder(), "filter.yml");
		filterDataF = new File(getDataFolder(), "data-filter.yml");
		alchemistUpgradeF = new File(getDataFolder(), "alchemist-upgrade-costs.yml");
		tinkererXPF = new File(getDataFolder(), "tinkerer-xp.yml");
		//
		dropPackageF = new File(getDataFolder(), "drop-packages.yml");
		legendaryDPF = new File(getDataFolder(), "drop-packages-legendary.yml");
		ultimateDPF = new File(getDataFolder(), "drop-packages-ultimate.yml");
		eliteDPF = new File(getDataFolder(), "drop-packages-elite.yml");
		uniqueDPF = new File(getDataFolder(), "drop-packages-unique.yml");
		simpleDPF = new File(getDataFolder(), "drop-packages-simple.yml");
		//
		if(!(TinkererEnchanterAlchemistF.exists())) { TinkererEnchanterAlchemistF.getParentFile().mkdirs(); saveResource("TinkererEnchanterAlchemist.yml", false); }
		if(!(EnabledEnchantsF.exists())) { EnabledEnchantsF.getParentFile().mkdirs(); saveResource("enabled-enchants.yml", false); }
		if(!(SoulF.exists())) { SoulF.getParentFile().mkdirs(); saveResource("soul.yml", false); }
		if(!(globalEventsF.exists())) { globalEventsF.getParentFile().mkdirs(); saveResource("global-events.yml", false); }
		if(!(BookOptionsF.exists())) { BookOptionsF.getParentFile().mkdirs(); saveResource("book-options.yml", false); }
		if(!(givedpItemsF.exists())) { givedpItemsF.getParentFile().mkdirs(); saveResource("givedp-items.yml", false); }
		if(!(gkitsF.exists())) { gkitsF.getParentFile().mkdirs(); saveResource("gkits.yml", false); }
		if(!(messagesF.exists())) { messagesF.getParentFile().mkdirs(); saveResource("messages.yml", false); }
		if(!(commandsPermissionsF.exists())) { commandsPermissionsF.getParentFile().mkdirs(); saveResource("commands-permissions.yml", false); }
		if(!(enchantTypeF.exists())) { enchantTypeF.getParentFile().mkdirs(); saveResource("enchant-types.yml", false); }
		if(!(placeholdersF.exists())) { placeholdersF.getParentFile().mkdirs(); saveResource("_placeholders.yml", false); }
		if(!(gkitDataF.exists())) { gkitDataF.getParentFile().mkdirs(); saveResource("data-gkits.yml", false); }
		if(!(filterF.exists())) { filterF.getParentFile().mkdirs(); saveResource("filter.yml", false); }
		if(!(filterDataF.exists())) { filterDataF.getParentFile().mkdirs(); saveResource("data-filter.yml", false); }
		if(!(alchemistUpgradeF.exists())) { alchemistUpgradeF.getParentFile().mkdirs(); saveResource("alchemist-upgrade-costs.yml", false); }
		if(!(tinkererXPF.exists())) { tinkererXPF.getParentFile().mkdirs(); saveResource("tinkerer-xp.yml", false); }
		//
		if(!(dropPackageF.exists())) { dropPackageF.getParentFile().mkdirs(); saveResource("drop-packages.yml", false); }
		if(!(legendaryDPF.exists())) { legendaryDPF.getParentFile().mkdirs(); saveResource("drop-packages-legendary.yml", false); }
		if(!(ultimateDPF.exists())) { ultimateDPF.getParentFile().mkdirs(); saveResource("drop-packages-ultimate.yml", false); }
		if(!(eliteDPF.exists())) { eliteDPF.getParentFile().mkdirs(); saveResource("drop-packages-elite.yml", false); }
		if(!(uniqueDPF.exists())) { uniqueDPF.getParentFile().mkdirs(); saveResource("drop-packages-unique.yml", false); }
		if(!(simpleDPF.exists())) { simpleDPF.getParentFile().mkdirs(); saveResource("drop-packages-simple.yml", false); }
		//
		TinkererEnchanterAlchemist = new YamlConfiguration();
		EnabledEnchants = new YamlConfiguration();
		Soul = new YamlConfiguration();
		globalEvents = new YamlConfiguration();
		BookOptions = new YamlConfiguration();
		givedpItems = new YamlConfiguration();
		gkits = new YamlConfiguration();
		messages = new YamlConfiguration();
		commandsPermissions = new YamlConfiguration();
		enchantType = new YamlConfiguration();
		placeholders = new YamlConfiguration();
		gkitData = new YamlConfiguration();
		filter = new YamlConfiguration();
		filterData = new YamlConfiguration();
		alchemistUpgrade = new YamlConfiguration();
		tinkererXP = new YamlConfiguration();
		//
		dropPackage = new YamlConfiguration();
		legendaryDP = new YamlConfiguration();
		ultimateDP = new YamlConfiguration();
		eliteDP = new YamlConfiguration();
		uniqueDP = new YamlConfiguration();
		simpleDP = new YamlConfiguration();
		//
		try {
			TinkererEnchanterAlchemist.load(TinkererEnchanterAlchemistF);
			EnabledEnchants.load(EnabledEnchantsF);
			Soul.load(SoulF);
			globalEvents.load(globalEventsF);
			BookOptions.load(BookOptionsF);
			givedpItems.load(givedpItemsF);
			gkits.load(gkitsF);
			messages.load(messagesF);
			commandsPermissions.load(commandsPermissionsF);
			enchantType.load(enchantTypeF);
			placeholders.load(placeholdersF);
			gkitData.load(gkitDataF);
			filter.load(filterF);
			filterData.load(filterDataF);
			alchemistUpgrade.load(alchemistUpgradeF);
			tinkererXP.load(tinkererXPF);
			//
			dropPackage.load(dropPackageF);
			legendaryDP.load(legendaryDPF);
			ultimateDP.load(ultimateDPF);
			eliteDP.load(eliteDPF);
			uniqueDP.load(uniqueDPF);
			simpleDP.load(simpleDPF);
			//
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		//
	}
	private void commands() {
		getCommand("randompackage").setExecutor(new randomPackageCommands());
		getCommand("givedp").setExecutor(new randomPackageCommands());
		getCommand("gkit").setExecutor(new randomPackageCommands());
		getCommand("enchanter").setExecutor(new randomPackageCommands());
		getCommand("alchemist").setExecutor(new randomPackageCommands());
		getCommand("tinkerer").setExecutor(new randomPackageCommands());
		getCommand("kitop").setExecutor(new randomPackageCommands());
		getCommand("splitsouls").setExecutor(new randomPackageCommands());
		getCommand("filter").setExecutor(new randomPackageCommands());
		getCommand("de").setExecutor(new randomPackageCommands());
	}
	public static FileConfiguration getTinkererEnchanterAlchemistConfig() { return TinkererEnchanterAlchemist; }
	public static FileConfiguration getTinkererXPConfig() { return tinkererXP; }
	public static FileConfiguration getEnabledEnchantsConfig() { return EnabledEnchants; }
	public static FileConfiguration getSoulConfig() { return Soul; }
	public static FileConfiguration getGlobalEventsConfig() { return globalEvents; }
	public static FileConfiguration getBookOptionsConfig() { return BookOptions; }
	public static FileConfiguration getGivedpItemsConfig() { return givedpItems; }
	public static FileConfiguration getGkitsConfig() { return gkits; }
	public static FileConfiguration getMessagesConfig() { return messages; }
	public static FileConfiguration getCommandsAndPermissionsConfig() { return commandsPermissions; }
	public static FileConfiguration getEnchantmentTypeConfig() { return enchantType; }
	public static FileConfiguration getPlaceholderConfig() { return placeholders; }
	public static FileConfiguration getGkitDataConfig() { return gkitData; }
	public static FileConfiguration getFilterConfig() { return filter; }
	public static FileConfiguration getFilterDataConfig() { return filterData; }
	public static FileConfiguration getAlchemistUpgradeCostsConfig() { return alchemistUpgrade; }
	//
	public static FileConfiguration getDropPackageConfig() { return dropPackage; }
	public static FileConfiguration getLegendaryDropPackageConfig() { return legendaryDP; }
	public static FileConfiguration getUltimateDropPackageConfig() { return ultimateDP; }
	public static FileConfiguration getEliteDropPackageConfig() { return eliteDP; }
	public static FileConfiguration getUniqueDropPackageConfig() { return uniqueDP; }
	public static FileConfiguration getSimpleDropPackageConfig() { return simpleDP; }
	//
	/*
	 * 
	 */
	public static void saveGkitDataConfig() { try { gkitData.save(gkitDataF); } catch (IOException e) { e.printStackTrace(); } }
	public static void saveFilterDataConfig() { try { filterData.save(filterDataF); } catch (IOException e) { e.printStackTrace(); } }
	/*
	 * 
	 */
	private void events() {
		pm.registerEvents(new tinkererEvents(), this);
		pm.registerEvents(new enchanterEvents(), this);
		pm.registerEvents(new alchemistEvents(), this);
		pm.registerEvents(new givedpEvents(), this);
		pm.registerEvents(new gkitEvents(), this);
		pm.registerEvents(new soulEvents(), this);
		pm.registerEvents(new filterEvents(), this);
		pm.registerEvents(new BookRevealFirework(), this);
		pm.registerEvents(new Data(), this);
		pm.registerEvents(new SuccessAndDestroySystem(), this);
		pm.registerEvents(new DisabledEnchants(), this);
		pm.registerEvents(new dropPackageEvents(), this);
		pm.registerEvents(new globalEvents(), this);
		//
		pm.registerEvents(new EnchantsThatAddPotionEffects(), this);
		pm.registerEvents(new EntityDamageByEntityEnchants(), this);
		pm.registerEvents(new BowEnchants(), this);
		pm.registerEvents(new BowEnchants2(), this);
		pm.registerEvents(new BlockBreakEventEnchants(), this);
		pm.registerEvents(new PlayerMoveEventEnchants(), this);
		pm.registerEvents(new PlayerItemConsumeEnchants(), this);
	}
	/*
	 * 
	 */
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		Bukkit.getScheduler().cancelTasks(this);
		return;
	}
}
