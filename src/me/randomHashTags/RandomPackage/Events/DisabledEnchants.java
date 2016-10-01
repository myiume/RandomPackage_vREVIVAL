package me.randomHashTags.RandomPackage.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import me.randomHashTags.RandomPackage.RandomPackage;

public class DisabledEnchants implements Listener {
	public static ArrayList<String> list = new ArrayList<String>();
	
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
			public void run() {
				if(!(list.isEmpty())) {
					Bukkit.broadcastMessage(RandomPackage.prefix + ChatColor.YELLOW + "Current enchants disabled: ");
					Bukkit.broadcastMessage(list.toString().replace("[", "").replace("]", ""));
					return;
				} else { return; }
			}
		}, 300);
		return;
	}
	@EventHandler private void pluginDisableEvent(PluginDisableEvent event) { list.clear(); return; }
}
