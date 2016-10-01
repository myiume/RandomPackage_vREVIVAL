package me.randomHashTags.RandomPackage.Enchants;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.randomHashTags.RandomPackage.RandomPackage;

public class PlayerMoveEventEnchants implements Listener {
	private int lucky = 0, chance = 0, blindnessTime = 0;
	private Random random = new Random();
	private ArrayList<Player> damageCancel = new ArrayList<Player>();
	@EventHandler
	private void playerMoveEvent(PlayerMoveEvent event) {
		// ARMOR ENCHANTS
		// HELMET ENCHANTS
		if(!(event.getPlayer().getInventory().getHelmet() == null) && event.getPlayer().getInventory().getHelmet().hasItemMeta() && event.getPlayer().getInventory().getHelmet().getItemMeta().hasLore()) {
			if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Implants") == true) { Implants(event); }
			if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Clarity") == true) { Clarity(event); }
		}
		// CHESTPLATE ENCHANTS
		// LEGGINGS ENCHANTS
		// BOOT ENCHANTS
		if(!(event.getPlayer().getInventory().getBoots() == null) && event.getPlayer().getInventory().getBoots().hasItemMeta() && event.getPlayer().getInventory().getBoots().getItemMeta().hasLore()) {
			//if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.EnderWalker") == true) { EnderWalker(event); }
		}
	}
	/*
	 * Legendary Enchants
	 */
	// CLARITY
	private void Clarity(PlayerMoveEvent event) {
		if(event.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS)) {
			chance = 5;
			for(PotionEffect pe : event.getPlayer().getActivePotionEffects()) {
				chance = chance + 1;
				if(pe.getType().equals(PotionEffectType.BLINDNESS) && random.nextInt(100) <= chance) {
					for(int i = 1; i <= 3; i++) {
						if(event.getPlayer().getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Clarity.Clarity" + i + "-itemLore"))) && pe.getAmplifier() <= i - 1) {
							event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
							event.getPlayer().damage(0);
							if(event.getPlayer().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("true")) { event.getPlayer().getWorld().setGameRuleValue("commandBlockOutput", "false"); }
							if(event.getPlayer().getWorld().getGameRuleValue("logAdminCommands").equalsIgnoreCase("true")) { event.getPlayer().getWorld().setGameRuleValue("logAdminCommands", "false"); }
							if(event.getPlayer().getWorld().getGameRuleValue("sendCommandFeedback").equalsIgnoreCase("true")) { event.getPlayer().getWorld().setGameRuleValue("sendCommandFeedback", "false"); }
							double y = event.getPlayer().getEyeLocation().getY() + 0.5;
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "particle cloud " + event.getPlayer().getEyeLocation().getX() + " " + y + " " + event.getPlayer().getEyeLocation().getZ() + " 0.3 0.2 0.3 0 10");
							return;
						}
					}
				}
			}
			return;
		} else { return; }
	}
	/*
	 * Ultimate Enchants
	 */
	// INFINITE BLINDNESS GLITCH
	// ENDER WALKER
	private void EnderWalker(PlayerMoveEvent event) {
		if(event.getPlayer().hasPotionEffect(PotionEffectType.POISON) || event.getPlayer().hasPotionEffect(PotionEffectType.WITHER)) {
			chance = lucky + 2;
			for(int i = 1; i <= 5; i++) {
				chance = chance + 2;
				if(event.getPlayer().getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.EnderWalker.EnderWalker" + i + "-itemLore"))) && random.nextInt(100) <= chance
						&& !(damageCancel.contains(event.getPlayer()))) {
					damageCancel.add(event.getPlayer());
					for(PotionEffect pe : event.getPlayer().getActivePotionEffects()) {
						if(pe.getType().equals(PotionEffectType.POISON) || pe.getType().equals(PotionEffectType.WITHER)) {
							blindnessTime = pe.getDuration();
						}
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
						public void run() {
							damageCancel.remove(event.getPlayer());
						}
					}, blindnessTime);
					return;
				}
			}
			return;
		} else { return; }
	}
	/*
	 * Elite Enchants
	 */
	// IMPLANTS
		private void Implants(PlayerMoveEvent event) {
			chance = 10;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 2;
				if(event.getPlayer().getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Implants.Implants" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					if(event.getPlayer().getFoodLevel() + 1 <= 20) { event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 1); }
					return;
				}
			}
			return;
		}
	@EventHandler
	private void entityDamageEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player && damageCancel.contains(event.getEntity())) {
			Player player = (Player) event.getEntity();
			if(!(event.getCause() == null) && event.getEntity().getLastDamageCause().getCause().equals(DamageCause.POISON) && player.hasPotionEffect(PotionEffectType.POISON) || !(event.getCause() == null) && event.getEntity().getLastDamageCause().getCause().equals(DamageCause.WITHER) && player.hasPotionEffect(PotionEffectType.WITHER)) {
				event.setCancelled(true);
				for(int i = 1; i <= 5; i++) {
					if(!(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore() && player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.EnderWalker.EnderWalker" + i + "-itemLore")))
							&& damageCancel.contains(player)) {
						if(!(player.hasPotionEffect(PotionEffectType.BLINDNESS))) {
							player.setSprinting(false);
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, blindnessTime, i - 1));
						}
						return;
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
}
