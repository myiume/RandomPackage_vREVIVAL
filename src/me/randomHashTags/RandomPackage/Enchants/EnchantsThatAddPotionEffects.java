package me.randomHashTags.RandomPackage.Enchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.randomHashTags.RandomPackage.RandomPackage;

public class EnchantsThatAddPotionEffects implements Listener {
	@EventHandler
	private void inventoryClickEvent(InventoryClickEvent event) {
		if(event.isCancelled() || !(event.getSlotType() == SlotType.ARMOR)) {
			return;
		} else if(event.getRawSlot() == 5 && !(event.getCurrentItem() == null) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getType().name().endsWith("HELMET")
				|| event.getRawSlot() == 6 && !(event.getCurrentItem() == null) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getType().name().endsWith("CHESTPLATE")
				|| event.getRawSlot() == 7 && !(event.getCurrentItem() == null) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getType().name().endsWith("LEGGINGS")
				|| event.getRawSlot() == 8 && !(event.getCurrentItem() == null) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getType().name().endsWith("BOOTS")) {
			for(int i = 1; i <= 4; i++) {
				// Legendary Enchants //
				/*
				 * Drunk
				 */
				if(i <= 4 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Drunk.Drunk" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Drunk") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) { event.getWhoClicked().removePotionEffect(PotionEffectType.INCREASE_DAMAGE); }
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SLOW)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW); }
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW_DIGGING); }
				}
				/*
				 * Gears
				 */
				if(i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Gears.Gears" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Gears") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SPEED)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SPEED); }
				}
				/*
				 * Overload
				 */
				if(i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Overload.Overload" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Overload") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.HEALTH_BOOST)) { event.getWhoClicked().removePotionEffect(PotionEffectType.HEALTH_BOOST); }
				}
				// Ultimate Enchants //
				/*
				 * Obsidian Shield
				 */
				if(i == 1 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.ObsidianShield.ObsidianShield1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.ObsidianShield") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) { event.getWhoClicked().removePotionEffect(PotionEffectType.FIRE_RESISTANCE); }
				}
				// Elite Enchants //
				/*
				 * Anti Gravity & Springs
				 */
				if(i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.AntiGravity.AntiGravity" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.AntiGravity") == true
						|| i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Springs.Springs" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Springs") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.JUMP)) { event.getWhoClicked().removePotionEffect(PotionEffectType.JUMP); }
				}
				// Unique Enchants //
				/*
				 * Aquatic
				 */
				if(i == 1 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Aquatic.Aquatic1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Aquatic") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.WATER_BREATHING)) { event.getWhoClicked().removePotionEffect(PotionEffectType.WATER_BREATHING); }
				}
				// Simple Enchants //
				/*
				 * Glowing
				 */
				if(i == 1 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Glowing.Glowing1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Glowing") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.NIGHT_VISION)) { event.getWhoClicked().removePotionEffect(PotionEffectType.NIGHT_VISION); }
				}
			}
			return;
		} else if(event.getRawSlot() == 5 && !(event.getCursor() == null) && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasLore() && event.getCursor().getType().name().endsWith("HELMET") && !(event.getClick() == ClickType.DOUBLE_CLICK)
				|| event.getRawSlot() == 6 && !(event.getCursor() == null) && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasLore() && event.getCursor().getType().name().endsWith("CHESTPLATE") && !(event.getClick() == ClickType.DOUBLE_CLICK)
				|| event.getRawSlot() == 7 && !(event.getCursor() == null) && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasLore() && event.getCursor().getType().name().endsWith("LEGGINGS") && !(event.getClick() == ClickType.DOUBLE_CLICK)
				|| event.getRawSlot() == 8 && !(event.getCursor() == null) && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasLore() && event.getCursor().getType().name().endsWith("BOOTS") && !(event.getClick() == ClickType.DOUBLE_CLICK)) {
			for(int i = 1; i <= 4; i++) {
				// Legendary Enchants //
				/*
				 * Drunk
				 */
				if(i <= 4 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Drunk.Drunk" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Drunk") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) { event.getWhoClicked().removePotionEffect(PotionEffectType.INCREASE_DAMAGE); }
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SLOW)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW); }
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW_DIGGING); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, i - 1));
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, i - 1));
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, i - 1));
				}
				/*
				 * Gears
				 */
				if(i <= 3 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Gears.Gears" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Gears") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SPEED)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SPEED); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, i));
				}
				/*
				 * Overload
				 */
				if(i <= 3 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Overload.Overload" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Overload") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.HEALTH_BOOST)) { event.getWhoClicked().removePotionEffect(PotionEffectType.HEALTH_BOOST); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999, i - 1));
				}
				// Ultimate Enchants //
				/*
				 * Obsidian Shield
				 */
				if(i == 1 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.ObsidianShield.ObsidianShield1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.ObsidianShield") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) { event.getWhoClicked().removePotionEffect(PotionEffectType.FIRE_RESISTANCE); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
				}
				// Elite Enchants //
				/*
				 * Anti Gravity & Springs
				 */
				if(i <= 3 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.AntiGravity.AntiGravity" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.AntiGravity") == true
						|| i <= 3 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Springs.Springs" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Springs") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.JUMP)) { event.getWhoClicked().removePotionEffect(PotionEffectType.JUMP); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, i - 1));
				}
				// Unique Enchants //
				/*
				 * Aquatic
				 */
				if(i == 1 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Aquatic.Aquatic1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Aquatic") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.WATER_BREATHING)) { event.getWhoClicked().removePotionEffect(PotionEffectType.WATER_BREATHING); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 0));
				}
				// Simple Enchants //
				/*
				 * Glowing
				 */
				if(i == 1 && event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Glowing.Glowing1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Glowing") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.NIGHT_VISION)) { event.getWhoClicked().removePotionEffect(PotionEffectType.NIGHT_VISION); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
				}
			}
			return;
		} else {
			return;
		}
	}
	@EventHandler
	private void playerInteractEvent(PlayerInteractEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand() == null
				|| !(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) || !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore())
				|| !(event.getAction() == Action.RIGHT_CLICK_AIR) && !(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		} else {
			ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
			Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
				public void run() {
					if(event.getPlayer().getInventory().getItemInMainHand().equals(itemInMainHand)) {
						return;
					} else {
						for(int i = 1; i <= 4; i++) {
							// Legendary Enchants //
							/*
							 * Drunk
							 */
							if(i <= 4 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Drunk.Drunk" + i + "-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Drunk") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) { event.getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE); }
								if(event.getPlayer().hasPotionEffect(PotionEffectType.SLOW)) { event.getPlayer().removePotionEffect(PotionEffectType.SLOW); }
								if(event.getPlayer().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) { event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, i - 1));
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, i - 1));
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, i - 1));
							}
							/*
							 * Gears
							 */
							if(i <= 3 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Gears.Gears" + i + "-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Gears") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) { event.getPlayer().removePotionEffect(PotionEffectType.SPEED); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, i));
							}
							/*
							 * Overload
							 */
							if(i <= 3 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Overload.Overload" + i + "-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Overload") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.HEALTH_BOOST)) { event.getPlayer().removePotionEffect(PotionEffectType.HEALTH_BOOST); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999, i - 1));
							}
							// Ultimate Enchants //
							/*
							 * Obsidian Shield
							 */
							if(i == 1 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.ObsidianShield.ObsidianShield1-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.ObsidianShield") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) { event.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
							}
							// Elite Enchants //
							/*
							 * Anti Gravity & Springs
							 */
							if(i <= 3 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.AntiGravity.AntiGravity" + i + "-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.AntiGravity") == true
									|| i <= 3 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Springs.Springs" + i + "-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Springs") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.JUMP)) { event.getPlayer().removePotionEffect(PotionEffectType.JUMP); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, i - 1));
							}
							// Unique Enchants //
							/*
							 * Aquatic
							 */
							if(i == 1 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Aquatic.Aquatic1-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Aquatic") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.WATER_BREATHING)) { event.getPlayer().removePotionEffect(PotionEffectType.WATER_BREATHING); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 0));
							}
							// Simple Enchants //
							/*
							 * Glowing
							 */
							if(i == 1 && itemInMainHand.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Glowing.Glowing1-itemLore")))
									&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Glowing") == true) {
								if(event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) { event.getPlayer().removePotionEffect(PotionEffectType.NIGHT_VISION); }
								event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
							}
						}
						return;
					}
				}
			}, 1);
			return;
		}
	}
	@EventHandler
	private void shiftInventoryClickEvent(InventoryClickEvent event) {
		if(event.isCancelled() || !(event.isShiftClick()) || event.getSlotType() == SlotType.ARMOR
				|| event.getClick() == ClickType.CONTROL_DROP
				|| !(event.getWhoClicked().getOpenInventory().getType() == InventoryType.CRAFTING)
				|| event.getCurrentItem() == null || !(event.getCurrentItem().hasItemMeta()) || !(event.getCurrentItem().getItemMeta().hasLore())
				|| event.getCurrentItem().getType().name().endsWith("HELMET") && !(event.getWhoClicked().getInventory().getHelmet() == null)
				|| event.getCurrentItem().getType().name().endsWith("CHESTPLATE") && !(event.getWhoClicked().getInventory().getChestplate() == null)
				|| event.getCurrentItem().getType().name().endsWith("LEGGINGS") && !(event.getWhoClicked().getInventory().getLeggings() == null)
				|| event.getCurrentItem().getType().name().endsWith("BOOTS") && !(event.getWhoClicked().getInventory().getBoots() == null)) {
			return;
		} else {
			for(int i = 1; i <= 4; i++) {
				// Legendary Enchants //
				/*
				 * Drunk
				 */
				if(i <= 4 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Drunk.Drunk" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Drunk") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) { event.getWhoClicked().removePotionEffect(PotionEffectType.INCREASE_DAMAGE); }
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SLOW)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW); } 
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SLOW_DIGGING)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SLOW_DIGGING); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, i - 1));
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, i - 1));
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 99999, i - 1));
				}
				/*
				 * Gears
				 */
				if(i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Gears.Gears" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Gears") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.SPEED)) { event.getWhoClicked().removePotionEffect(PotionEffectType.SPEED); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, i));
				}
				/*
				 * Overload
				 */
				if(i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Overload.Overload" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Overload") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.HEALTH_BOOST)) { event.getWhoClicked().removePotionEffect(PotionEffectType.HEALTH_BOOST); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999, i - 1));
				}
				// Ultimate Enchants //
				/*
				 * Obsidian Shield
				 */
				if(i == 1 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.ObsidianShield.ObsidianShield1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.ObsidianShield") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) { event.getWhoClicked().removePotionEffect(PotionEffectType.FIRE_RESISTANCE); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
				}
				// Elite Enchants //
				/*
				 * Anti Gravity & Springs
				 */
				if(i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.AntiGravity.AntiGravity" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.AntiGravity") == true
						|| i <= 3 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Springs.Springs" + i + "-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Springs") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.JUMP)) { event.getWhoClicked().removePotionEffect(PotionEffectType.JUMP); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, i - 1));
				}
				// Unique Enchants //
				/*
				 * Aquatic
				 */
				if(i == 1 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Aquatic.Aquatic1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Aquatic") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.WATER_BREATHING)) { event.getWhoClicked().removePotionEffect(PotionEffectType.WATER_BREATHING); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 99999, 0));
				}
				// Simple Enchants //
				/*
				 * Glowing
				 */
				if(i == 1 && event.getCurrentItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Glowing.Glowing1-itemLore")))
						&& RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Glowing") == true) {
					if(event.getWhoClicked().hasPotionEffect(PotionEffectType.NIGHT_VISION)) { event.getWhoClicked().removePotionEffect(PotionEffectType.NIGHT_VISION); }
					event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 0));
				}
			}
			return;
		}
	}
}
