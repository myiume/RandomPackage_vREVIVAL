package me.randomHashTags.RandomPackage.Enchants;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.randomHashTags.RandomPackage.RandomPackage;

public class PlayerItemConsumeEnchants implements Listener {
	private Random random = new Random();
	@EventHandler
	private void playerItemConsumeEvent(PlayerItemConsumeEvent event) {
		for(int i = 1; i <= 7; i++) {
			if(!(event.getPlayer().getInventory().getHelmet() == null) && event.getPlayer().getInventory().getHelmet().hasItemMeta() && event.getPlayer().getInventory().getHelmet().getItemMeta().hasLore()
					&& event.getPlayer().getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Nutrition.Nutrition" + i + "-itemLore")))
					|| !(event.getPlayer().getInventory().getChestplate() == null) && event.getPlayer().getInventory().getChestplate().hasItemMeta() && event.getPlayer().getInventory().getChestplate().getItemMeta().hasLore()
					&& event.getPlayer().getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Nutrition.Nutrition" + i + "-itemLore")))
					|| !(event.getPlayer().getInventory().getLeggings() == null) && event.getPlayer().getInventory().getLeggings().hasItemMeta() && event.getPlayer().getInventory().getLeggings().getItemMeta().hasLore()
					&& event.getPlayer().getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Nutrition.Nutrition" + i + "-itemLore")))
					|| !(event.getPlayer().getInventory().getBoots() == null) && event.getPlayer().getInventory().getBoots().hasItemMeta() && event.getPlayer().getInventory().getBoots().getItemMeta().hasLore()
					&& event.getPlayer().getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Nutrition.Nutrition" + i + "-itemLore")))) {
				event.setCancelled(true);
				event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 1);
				event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, random.nextInt(i), 0));
				return;
			}
		}
		return;
	}
}
