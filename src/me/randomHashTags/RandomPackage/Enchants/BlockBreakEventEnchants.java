package me.randomHashTags.RandomPackage.Enchants;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.randomHashTags.RandomPackage.RandomPackage;

public class BlockBreakEventEnchants implements Listener {
	private int chance = 0;
	private Random random = new Random();
	private boolean Break = false;
	private ArrayList<Location> detonateLocation = new ArrayList<Location>();
	private Location dL;
	@EventHandler
	private void blockBreakEvent(BlockBreakEvent event) {
		if(event.isCancelled() || event.getPlayer().getInventory().getItemInMainHand() == null || !(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta())
				|| !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) || event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		} else {
			for(int a = 1; a <= 7; a++) {
				if(a == 6) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ())).getLocation();
				} else if(a == 7) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX() + 1, event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ())).getLocation();
				} else if(a == 3) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX() - 1, event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ())).getLocation();
				} else if(a == 4) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ() + 1)).getLocation();
				} else if(a == 5) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ() - 1)).getLocation();
				} else if(a == 2) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() + 1, event.getBlock().getLocation().getBlockZ())).getLocation();
				} else if(a == 1) { dL = Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(new Location(event.getPlayer().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() - 1, event.getBlock().getLocation().getBlockZ())).getLocation(); }
				detonateLocation.add(dL);
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
				public void run() {
					if(!(event.isCancelled())) {
						// SIMPLE
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Oxygenate") == true) { Oxygenate(event); }
						// UNIQUE
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.AutoSmelt") == true) { AutoSmelt(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Experience") == true) { Experience(event); }
						// ELITE
						// ULTIMATE
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Detonate") == true) { Detonate(event); }
						// LEGENDARY
						
						return;
					} else { detonateLocation.clear(); return; }
				}
			}, 2);
			return;
		}
	}
	// SIMPLE
	private void Oxygenate(BlockBreakEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("AXE") || event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("SPADE")) {
			if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Enchantments.Simple.Oxygenate.Oxygenate1.ItemLore"))) && random.nextInt(100) <= 85) {
				if(event.getPlayer().getRemainingAir() + 40 <= 300) {
					event.getPlayer().setRemainingAir(event.getPlayer().getRemainingAir() + 40);
					return;
				} else { return; }
			} else { return; }
		}
	}
	// UNIQUE
	// AUTOSMELT
	private void AutoSmelt(BlockBreakEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("PICKAXE")) {
			chance = 20;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 20;
				if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Enchantments.Unique.AutoSmelt.AutoSmelt" + i + ".ItemLore"))) && random.nextInt(100) <= chance) {
					if(event.getBlock().getType().equals(Material.GOLD_ORE)) {
						Break = true;
						event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, i));
					} else if(event.getBlock().getType().equals(Material.IRON_ORE)) {
						Break = true;
						event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, i));
					} else { Break = false; return; }
					if(Break == true) {
						Break = false;
						event.setCancelled(true);
						event.getBlock().setType(Material.AIR);
						return;
					}
				}
			}
			return;
		} else { return; }
	}
	// EXPERIENCE
	private void Experience(BlockBreakEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("PICKAXE")) {
			chance = 0;
			for(int i = 1; i <= 5; i++) {
				int randomNumber = random.nextInt(i + 1);
				if(randomNumber == 0) { randomNumber = 1; }
				chance = chance + 15;
				if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Enchantments.Unique.Experience.Experience" + i + ".ItemLore"))) && randomNumber <= chance) {
					event.setExpToDrop(event.getExpToDrop() * randomNumber);
					return;
				}
			}
			return;
		} else { return; }
	}
	
	/*
	 * Elite
	 */
	
	/*
	 * Ultimate
	 */
	// DETONATE
	private void Detonate(BlockBreakEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("AXE") || event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("SPADE")) {
			if(event.getPlayer().getWorld().getGameRuleValue("logAdminCommands").equalsIgnoreCase("true")) { event.getPlayer().getWorld().setGameRuleValue("logAdminCommands", "false"); }
			if(event.getPlayer().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("true")) { event.getPlayer().getWorld().setGameRuleValue("commandBlockOutput", "false"); }
			if(event.getPlayer().getWorld().getGameRuleValue("sendCommandFeedback").equalsIgnoreCase("true")) { event.getPlayer().getWorld().setGameRuleValue("sendCommandFeedback", "false"); }
			chance = 35;
			for(int i = 1; i <= 9; i++) {
				chance = chance + 5;
				if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Enchantments.Ultimate.Detonate.Detonate" + i + ".ItemLore"))) && random.nextInt(100) <= chance) {
					event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.EXPLOSION_HUGE, 1);
					event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
					for(int o = 0; o < detonateLocation.size(); o++) {
						for(int p = 0; p < RandomPackage.getPlaceholderConfig().getStringList("Enchants.Detonate-blacklisted-blocks").size(); p++) {
							if(detonateLocation.get(o).getBlock().getType().name().equalsIgnoreCase(RandomPackage.getPlaceholderConfig().getStringList("Enchants.Detonate-blacklisted-blocks").get(p))) {
								return;
							} else {
								Bukkit.getPlayer(event.getPlayer().getName()).getWorld().getBlockAt(detonateLocation.get(o)).breakNaturally();
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + event.getPlayer().getName() + " ~ ~ ~ particle flame " + detonateLocation.get(o).getBlockX() + " " + detonateLocation.get(o).getBlockY() + " " + detonateLocation.get(o).getBlockZ() + " 0.25 0.5 0.25 0 75");
							}
						}
					}
					detonateLocation.clear();
					return;
				}
			}
			return;
		} else { return; }
	}
	// LEGENDARY
}
