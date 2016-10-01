package me.randomHashTags.RandomPackage.Data;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import me.randomHashTags.RandomPackage.RandomPackage;

public class api implements Listener {
	/*
	 *  THIS IS JUST AN API TEST CLASS!
	 */
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		RandomPackage.createEnchant("Spooky", ChatColor.GREEN + "Spooky", 2, EnchantType.WEAPON, Rarity.LEGENDARY, true);
	}
	
}
