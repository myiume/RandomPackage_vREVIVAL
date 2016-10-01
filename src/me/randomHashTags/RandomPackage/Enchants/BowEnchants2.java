package me.randomHashTags.RandomPackage.Enchants;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.randomHashTags.RandomPackage.RandomPackage;

public class BowEnchants2 implements Listener {
	private int chance = 0;
	private Random random = new Random();
	@EventHandler
	private void playerIsDamagedByArrow(EntityDamageByEntityEvent event) {
		if(event.getDamager().getType().name().endsWith("ARROW") && event.getEntity() instanceof Player && !(event.isCancelled())) {
			Player player = (Player) event.getEntity();
			player.sendMessage("test");
			if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Heavy") == true) { Heavy(event, player); }
			return;
		} else { return; }
	}
	
	
	// HEAVY
	private void Heavy(EntityDamageByEntityEvent event, Player player) {
		double damage = 0.00;
		for(int i = 1; i <= 5; i++) {
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore() && player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Heavy.Heavy" + i + "-itemLore")))) { damage = damage + 0.02; }
			if(!(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore() && player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Heavy.Heavy" + i + "-itemLore")))) { damage = damage + 0.02; }
			if(!(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore() && player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Heavy.Heavy" + i + "-itemLore")))) { damage = damage + 0.02; }
			if(!(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore() && player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Heavy.Heavy" + i + "-itemLore")))) { damage = damage + 0.02; }
		}
		if(!(damage == 0.0)) {
			event.setDamage(event.getDamage() - (event.getDamage()* damage));
			player.getWorld().playSound(player.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 2);
		}
		return;
	}
	
}
