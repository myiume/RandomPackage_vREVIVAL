package me.randomHashTags.RandomPackage.Events.gkit;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;


public class gkitEvents implements Listener {
	private static ItemStack item = new ItemStack(Material.ACACIA_STAIRS, 1);
	private static ItemMeta itemMeta = item.getItemMeta();
	private static ArrayList<String> lore = new ArrayList<String>();
	public static Inventory gkits = Bukkit.createInventory(null, RandomPackage.getGkitsConfig().getInt("chest-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString("chest-title")));
	private static Random random = new Random();
	public static ArrayList<String> players = new ArrayList<String>();
	@EventHandler
	private void gkitSelect(InventoryClickEvent event) {
		if(event.isCancelled() || event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR) || !(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(RandomPackage.getGkitsConfig().getString("chest-title")))) { return;
		} else {
			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);
			if(event.getRawSlot() >= event.getWhoClicked().getOpenInventory().getTopInventory().getSize()) { return; }
			
			if(event.getClick() == ClickType.LEFT) {
				if(event.getWhoClicked().hasPermission("RandomPackage.gkits." + event.getRawSlot())) {
					
					if(!(RandomPackage.getGkitDataConfig().get(player.getUniqueId().toString()) == null)) {
						if(RandomPackage.getGkitDataConfig().get(player.getUniqueId().toString() + "." + event.getRawSlot()) == null) {
							RandomPackage.gkitData.set(player.getUniqueId().toString() + "." + event.getRawSlot(), RandomPackage.getGkitsConfig().getInt("" + event.getRawSlot() + ".cooldown"));
							RandomPackage.saveGkitDataConfig();
							//
							players.add(player.getName() + "=" + event.getRawSlot());
							gkitGiveItems(event);
							return;
							//
						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("gkits.wait-for-cooldown").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
							player.closeInventory();
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
							return;
						}
					} else {
						RandomPackage.gkitData.createSection(player.getUniqueId().toString());
						RandomPackage.gkitData.set(player.getUniqueId().toString() + "." + event.getRawSlot(), RandomPackage.getGkitsConfig().getInt("" + event.getRawSlot() + ".cooldown"));
						RandomPackage.saveGkitDataConfig();
						//
						players.add(player.getName() + "=" + event.getRawSlot());
						gkitGiveItems(event);
						return;
						//
					}
				} else { player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1); return; }
			} else if(event.getClick() == ClickType.RIGHT) {
				gkitView(player, event);
				return;
			} else { return; }
		}
	}
	public static void playerGkit(Player player) {
		gkits = Bukkit.createInventory(player, RandomPackage.getGkitsConfig().getInt("chest-size"), RandomPackage.getGkitsConfig().getString("chest-title"));
		for(int i = 0; i < gkits.getSize(); i++) {
			if(!(RandomPackage.getGkitsConfig().get(i + "") == null)) {
				if(!(RandomPackage.getGkitsConfig().get(i + ".gui.item") == null)) {
					if(Material.getMaterial(RandomPackage.getGkitsConfig().getString(i + ".gui.item").toUpperCase()) == null) {
						player.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Invalid item name at " + ChatColor.RED + i + ".gui.item - '" + RandomPackage.getGkitsConfig().getString(i + ".gui.item") + "'");
						return;
					} else {
						int data = 0;
						if(!(RandomPackage.getGkitsConfig().get(i + ".gui.item-data") == null)) { data = RandomPackage.getGkitsConfig().getInt(i + ".gui.item-data"); } else { data = 0; }
						item = new ItemStack(Material.getMaterial(RandomPackage.getGkitsConfig().getString(i + ".gui.item").toUpperCase()), 1, (byte) data);
						if(!(RandomPackage.getGkitsConfig().get(i + ".gui.name") == null)) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".gui.name"))); }
						if(!(RandomPackage.getGkitsConfig().get(i + ".gui.lore") == null)) {
							for(int o = 0; o < RandomPackage.getGkitsConfig().getStringList(i + ".gui.lore").size(); o++) {
								lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getStringList(i + ".gui.lore").get(o)));
							}
							if(player.hasPermission("RandomPackage.gkits." + i)) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("gkits.gkit-permission-lore"))); } else { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("gkits.gkit-no-permission-lore"))); }
						}
						if(!(RandomPackage.getGkitsConfig().get(i + ".gui.enchanted") == null) && RandomPackage.getGkitsConfig().getBoolean(i + ".gui.enchanted") == true) { itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); }
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
					}
				}
			} else { item = new ItemStack(Material.AIR); }
			
			lore.clear();
			itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
			itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
			gkits.setItem(i, item);
		}
		player.openInventory(gkits);
		return;
	}
	public static void gkitView(Player player, InventoryClickEvent event) {
		int data = 0, amount = 1;
		gkits = Bukkit.createInventory(player, 9, event.getCurrentItem().getItemMeta().getDisplayName());
		for(int i = 0; i < RandomPackage.getGkitsConfig().getInt("chest-size"); i++) {
			for(int o = 1; o <= 10; o++) {
				if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".item") == null) && event.getRawSlot() == i) {
					if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".item-data") == null)) { data = RandomPackage.getGkitsConfig().getInt(i + ".items." + o + ".item-data"); } else { data = 0; }
					if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".amount") == null)) { amount = RandomPackage.getGkitsConfig().getInt(i + ".items." + o + ".amount"); } else { amount = 1; }
					if(!(Material.getMaterial(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".item").toUpperCase()) == null)) {
						item = new ItemStack(Material.getMaterial(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".item").toUpperCase()), amount, (byte) data);
					} else {
						item = new ItemStack(Material.BARRIER);
						itemMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Invalid material: '" + RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".item").toUpperCase() + "'");
					}
					if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".name") == null) && !(item.getType().equals(Material.BARRIER))) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".name").replace("%playername%", event.getWhoClicked().getName()))); } else { itemMeta.setDisplayName(null); }
					for(int p = 0; p < RandomPackage.getGkitsConfig().getStringList(i + ".items." + o + ".lore").size(); p++) {
						String selectedLore = ChatColor.stripColor(RandomPackage.getGkitsConfig().getStringList(i + ".items." + o + ".lore").get(p).replace("%", "")), enchant = null;
						for(int q = 1; q <= 10; q++) {
							if(!(RandomPackage.getPlugin().getConfig().getString("Soul." + selectedLore + "." + selectedLore + q + "-itemLore") == null)) {
								enchant = ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Soul." + selectedLore + "." + selectedLore + q + "-itemLore"));
							} else if(!(RandomPackage.getPlugin().getConfig().getString("Legendary." + selectedLore + "." + selectedLore + q + "-itemLore") == null)) {
								enchant = ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary." + selectedLore + "." + selectedLore + q + "-itemLore"));
							} else if(!(RandomPackage.getPlugin().getConfig().getString("Ultimate." + selectedLore + "." + selectedLore + q + "-itemLore") == null)) {
								enchant = ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate." + selectedLore + "." + selectedLore + q + "-itemLore"));
							} else if(!(RandomPackage.getPlugin().getConfig().getString("Elite." + selectedLore + "." + selectedLore + q + "-itemLore") == null)) {
								enchant = ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite." + selectedLore + "." + selectedLore + q + "-itemLore"));
							} else if(!(RandomPackage.getPlugin().getConfig().getString("Unique." + selectedLore + "." + selectedLore + q + "-itemLore") == null)) {
								enchant = ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique." + selectedLore + "." + selectedLore + q + "-itemLore"));
							} else if(!(RandomPackage.getPlugin().getConfig().getString("Simple." + selectedLore + "." + selectedLore + q + "-itemLore") == null)) {
								enchant = ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple." + selectedLore + "." + selectedLore + q + "-itemLore"));
							}
						}
						if(!(enchant == null)) { lore.add(enchant); enchant = null; } else { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getStringList(i + ".items." + o + ".lore").get(p).replace("%", ""))); }
					}
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					int enchantLevel = 0;
					Enchantment enchant = Enchantment.ARROW_DAMAGE;
					for(int p = 1; p <= 5; p++) {
						if(!(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p) == null)) {
							if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("protection") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("prot")) { enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("blastprotection") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("blastprot")) { enchant = Enchantment.PROTECTION_EXPLOSIONS;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("featherfalling") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("ff")) { enchant = Enchantment.PROTECTION_FALL;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("projectileprotection") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("projectileprot")) { enchant = Enchantment.PROTECTION_PROJECTILE;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("sharpness")) { enchant = Enchantment.DAMAGE_ALL;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("smite")) { enchant = Enchantment.DAMAGE_UNDEAD;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("baneofarth")) { enchant = Enchantment.DAMAGE_ARTHROPODS;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("unbreaking")) { enchant = Enchantment.DURABILITY;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("knockback")) { enchant = Enchantment.KNOCKBACK;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("power")) { enchant = Enchantment.ARROW_DAMAGE;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("punch")) { enchant = Enchantment.ARROW_KNOCKBACK;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("flame")) { enchant = Enchantment.ARROW_FIRE;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("infinity")) { enchant = Enchantment.ARROW_INFINITE;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("depthstrider")) { enchant = Enchantment.DEPTH_STRIDER;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("efficiency")) { enchant = Enchantment.DIG_SPEED;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("frostwalker")) { enchant = Enchantment.FROST_WALKER;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("fortune")) { enchant = Enchantment.LOOT_BONUS_BLOCKS;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("looting")) { enchant = Enchantment.LOOT_BONUS_MOBS;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("luck")) { enchant = Enchantment.LUCK;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("lure")) { enchant = Enchantment.LURE;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("mending")) { enchant = Enchantment.MENDING;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("aquaaffinity") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("aa")) { enchant = Enchantment.OXYGEN;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("silktouch")) { enchant = Enchantment.SILK_TOUCH;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("thorns")) { enchant = Enchantment.THORNS;
							} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("respiration")) { enchant = Enchantment.WATER_WORKER; } else { enchant = null; }
							if(!(enchant == null)) {
								for(int q = 1; q <= 100; q++) {
									if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).endsWith("_random")) {
										int randomLevel = random.nextInt(RandomPackage.getPlaceholderConfig().getInt("gkits.max-enchant-level"));
										for(int z = 1; z <= 10; z++) { if(randomLevel < RandomPackage.getPlaceholderConfig().getInt("gkits.min-enchant-level")) { randomLevel = randomLevel + 1; } }
										enchantLevel = randomLevel;
									} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).endsWith("_" + q)) { enchantLevel = q; }
								}
								item.addUnsafeEnchantment(enchant, enchantLevel);
							}
						}
					}
					gkits.setItem(o - 1, item);
					lore.clear();
				} else if(event.getRawSlot() == i && o - 1 < 9) {
					item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
					itemMeta.setDisplayName(" "); itemMeta.setLore(null);
					item.setItemMeta(itemMeta);
					gkits.setItem(o - 1, item);
				}
			}
		}
		player.openInventory(gkits);
		return;
	}
	@EventHandler
	private void inventoryCloseEvent(InventoryCloseEvent event) {
		for(int i = 0; i < RandomPackage.getGkitsConfig().getInt("chest-size"); i++) {
			if(!(RandomPackage.getGkitsConfig().get(i + ".gui.name") == null) && event.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".gui.name")))) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
					public void run() {
						playerGkit((Player) event.getPlayer());
						return;
					}
				}, 2);
				return;
			}
		}
		return;
	}
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomPackage.getPlugin(), new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for(int i = 0; i < players.size(); i++) {
					for(int o = 0; o <= 50; o++) {
						if(players.get(i).endsWith("=" + o)) {
							String playerName = players.get(i).replace("=" + o, "");
							if(Bukkit.getPlayer(playerName) == null) {
								gkitTimingsOffline(Bukkit.getOfflinePlayer(playerName), Integer.parseInt(players.get(i).replace(playerName + "=", "")));
							} else {
								gkitTimings(Bukkit.getPlayer(playerName), Integer.parseInt(players.get(i).replace(playerName + "=", "")));
							}
						}
					}
				}
				return;
			}
		}, 20, 20);
		return;
	}
	private void gkitTimings(Player player, int slot) {
		if(!(RandomPackage.getGkitDataConfig().get(player.getUniqueId().toString() + "." + slot) == null)) {
			if(RandomPackage.getGkitDataConfig().getInt(player.getUniqueId().toString() + "." + slot) - 1 == 0) {
				RandomPackage.getGkitDataConfig().set(player.getUniqueId().toString() + "." + slot, null);
				RandomPackage.saveGkitDataConfig();
			} else {
				RandomPackage.getGkitDataConfig().set(player.getUniqueId().toString() + "." + slot, RandomPackage.getGkitDataConfig().getInt(player.getUniqueId().toString() + "." + slot) - 1);
				RandomPackage.saveGkitDataConfig();
			}
		}
	}
	private void gkitTimingsOffline(OfflinePlayer player, int slot) {
		if(!(RandomPackage.getGkitDataConfig().get(player.getUniqueId().toString() + "." + slot) == null)) {
			if(RandomPackage.getGkitDataConfig().getInt(player.getUniqueId().toString() + "." + slot) - 1 == 0) {
				RandomPackage.getGkitDataConfig().set(player.getUniqueId().toString() + "." + slot, null);
				RandomPackage.saveGkitDataConfig();
			} else {
				RandomPackage.getGkitDataConfig().set(player.getUniqueId().toString() + "." + slot, RandomPackage.getGkitDataConfig().getInt(player.getUniqueId().toString() + "." + slot) - 1);
				RandomPackage.saveGkitDataConfig();
			}
		}
	}
	private void gkitGiveItems(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 2);
		int amount = 0, data = 0, randomEnchantSize = 0;
		for(int i = 0; i <= 10; i++) {
			if(!(RandomPackage.getGkitsConfig().get(event.getRawSlot() + "") == null) && !(RandomPackage.getGkitsConfig().get(event.getRawSlot() + ".items." + i) == null)) {
				if(Material.getMaterial(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".item").toUpperCase()) == null) {
					item = new ItemStack(Material.AIR);
					player.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Invalid material for gkit " + event.getRawSlot() + ". '" + ChatColor.RED + RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".item") + ChatColor.YELLOW + "'");
				} else {
					if(!(RandomPackage.getGkitsConfig().get(event.getRawSlot() + ".items." + i + ".amount") == null)) { amount = RandomPackage.getGkitsConfig().getInt(event.getRawSlot() + ".items." + i + ".amount"); } else { amount = 1; }
					if(!(RandomPackage.getGkitsConfig().get(event.getRawSlot() + ".items." + i + ".item-data") == null)) { data = RandomPackage.getGkitsConfig().getInt(event.getRawSlot() + ".items." + i + ".item-data"); } else { data = 0; }
					if(!(RandomPackage.getGkitsConfig().get(event.getRawSlot() + ".items." + i + ".durability") == null)) { item.setDurability((short) RandomPackage.getGkitsConfig().getInt(event.getRawSlot() + ".items." + i + ".durability")); }
					item = new ItemStack(Material.getMaterial(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".item").toUpperCase()), amount, (byte) data);
					if(!(RandomPackage.getGkitsConfig().get(event.getRawSlot() + ".items." + i + ".name") == null)) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".name"))); } else { itemMeta.setDisplayName(null); }
					if(!(RandomPackage.getGkitsConfig().get(event.getRawSlot() + ".items." + i + ".lore") == null)) {
						for(int o = 0; o < RandomPackage.getGkitsConfig().getStringList(event.getRawSlot() + ".items." + i + ".lore").size(); o++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getStringList(event.getRawSlot() + ".items." + i + ".lore").get(o)));
						}
						for(int o = 0; o < lore.size(); o++) {
							if(lore.get(o).startsWith("%") && lore.get(o).endsWith("%")) {
								//
								for(int p = 0; p < 400; p++) {
									if(Data.getLegendaryItemLores.size() > p && ChatColor.stripColor(lore.get(o)).replace("%", "").equalsIgnoreCase(ChatColor.stripColor(Data.getLegendaryItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""))) {
										String legendaryEnchantName = ChatColor.stripColor(Data.getLegendaryItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "");
										for(int q = 1; q <= 10; q++) {
											if(!(RandomPackage.getPlugin().getConfig().get("Legendary." + legendaryEnchantName + "." + legendaryEnchantName + q + "-itemLore") == null)) { randomEnchantSize = q; }
											if(q == 10) {
												int randomEnchant = random.nextInt(randomEnchantSize);
												if(!(randomEnchant == 0) && randomEnchant < randomEnchantSize) {
													lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary." + legendaryEnchantName + "." + legendaryEnchantName + randomEnchant + "-itemLore")));
												} else {
													lore.set(o, "REMOVE THIS");
												}
											}
										}
									}
									if(Data.getUltimateItemLores.size() > p && ChatColor.stripColor(lore.get(o)).replace("%", "").equalsIgnoreCase(ChatColor.stripColor(Data.getUltimateItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""))) {
										String ultimateEnchantName = ChatColor.stripColor(Data.getUltimateItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "");
										for(int q = 1; q <= 10; q++) {
											if(!(RandomPackage.getPlugin().getConfig().get("Ultimate." + ultimateEnchantName + "." + ultimateEnchantName + q + "-itemLore") == null)) { randomEnchantSize = q; }
											if(q == 10) {
												int randomEnchant = random.nextInt(randomEnchantSize);
												if(!(randomEnchant == 0) && randomEnchant < randomEnchantSize) {
													lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate." + ultimateEnchantName + "." + ultimateEnchantName + randomEnchant + "-itemLore")));
												} else {
													lore.set(o, "REMOVE THIS");
												}
											}
										}
									}
									if(Data.getEliteItemLores.size() > p && ChatColor.stripColor(lore.get(o)).replace("%", "").equalsIgnoreCase(ChatColor.stripColor(Data.getEliteItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""))) {
										String eliteEnchantName = ChatColor.stripColor(Data.getEliteItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "");
										for(int q = 1; q <= 10; q++) {
											if(!(RandomPackage.getPlugin().getConfig().get("Elite." + eliteEnchantName + "." + eliteEnchantName + q + "-itemLore") == null)) { randomEnchantSize = q; }
											if(q == 10) {
												int randomEnchant = random.nextInt(randomEnchantSize);
												if(!(randomEnchant == 0) && randomEnchant < randomEnchantSize) {
													lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite." + eliteEnchantName + "." + eliteEnchantName + randomEnchant + "-itemLore")));
												} else {
													lore.set(o, "REMOVE THIS");
												}
											}
										}
									}
									if(Data.getUniqueItemLores.size() > p && ChatColor.stripColor(lore.get(o)).replace("%", "").equalsIgnoreCase(ChatColor.stripColor(Data.getUniqueItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""))) {
										String uniqueEnchantName = ChatColor.stripColor(Data.getUniqueItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "");
										for(int q = 1; q <= 10; q++) {
											if(!(RandomPackage.getPlugin().getConfig().get("Unique." + uniqueEnchantName + "." + uniqueEnchantName + q + "-itemLore") == null)) { randomEnchantSize = q; }
											if(q == 10) {
												int randomEnchant = random.nextInt(randomEnchantSize);
												if(!(randomEnchant == 0) && randomEnchant < randomEnchantSize) {
													lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique." + uniqueEnchantName + "." + uniqueEnchantName + randomEnchant + "-itemLore")));
												} else {
													lore.set(o, "REMOVE THIS");
												}
											}
										}
									}
									if(Data.getSimpleItemLores.size() > p && ChatColor.stripColor(lore.get(o)).replace("%", "").equalsIgnoreCase(ChatColor.stripColor(Data.getSimpleItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""))) {
										String simpleEnchantName = ChatColor.stripColor(Data.getSimpleItemLores.get(p)).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "");
										for(int q = 1; q <= 10; q++) {
											if(!(RandomPackage.getPlugin().getConfig().get("Simple." + simpleEnchantName + "." + simpleEnchantName + q + "-itemLore") == null)) { randomEnchantSize = q; }
											if(q == 10) {
												int randomEnchant = random.nextInt(randomEnchantSize);
												if(!(randomEnchant == 0) && randomEnchant < randomEnchantSize) {
													lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple." + simpleEnchantName + "." + simpleEnchantName + randomEnchant + "-itemLore")));
												} else {
													lore.set(o, "REMOVE THIS");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			//
			if(!(item.getType().equals(Material.AIR))) {
				for(int o = 0; o < lore.size(); o++) {
					if(!(lore.get(o) == null) && lore.get(o).equalsIgnoreCase("REMOVE THIS")) { lore.remove(o); o = o - 1; }
				}
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				Enchantment enchant = Enchantment.ARROW_DAMAGE;
				int enchantLevel = 0;
				for(int p = 1; p <= 10; p++) {
					if(!(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p) == null)) {
						if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("protection") || RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("prot")) { enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("blastprotection") || RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("blastprot")) { enchant = Enchantment.PROTECTION_EXPLOSIONS;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("featherfalling") || RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("ff")) { enchant = Enchantment.PROTECTION_FALL;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("projectileprotection") || RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("projectileprot")) { enchant = Enchantment.PROTECTION_PROJECTILE;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("sharpness")) { enchant = Enchantment.DAMAGE_ALL;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("smite")) { enchant = Enchantment.DAMAGE_UNDEAD;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("baneofarth")) { enchant = Enchantment.DAMAGE_ARTHROPODS;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("unbreaking")) { enchant = Enchantment.DURABILITY;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("knockback")) { enchant = Enchantment.KNOCKBACK;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("power")) { enchant = Enchantment.ARROW_DAMAGE;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("punch")) { enchant = Enchantment.ARROW_KNOCKBACK;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("flame")) { enchant = Enchantment.ARROW_FIRE;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("infinity")) { enchant = Enchantment.ARROW_INFINITE;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("depthstrider")) { enchant = Enchantment.DEPTH_STRIDER;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("efficiency")) { enchant = Enchantment.DIG_SPEED;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("frostwalker")) { enchant = Enchantment.FROST_WALKER;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("fortune")) { enchant = Enchantment.LOOT_BONUS_BLOCKS;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("looting")) { enchant = Enchantment.LOOT_BONUS_MOBS;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("luck")) { enchant = Enchantment.LUCK;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("lure")) { enchant = Enchantment.LURE;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("mending")) { enchant = Enchantment.MENDING;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("aquaaffinity") || RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("aa")) { enchant = Enchantment.OXYGEN;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("silktouch")) { enchant = Enchantment.SILK_TOUCH;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("thorns")) { enchant = Enchantment.THORNS;
						} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).toLowerCase().startsWith("respiration")) { enchant = Enchantment.WATER_WORKER; }
						for(int q = 1; q <= 100; q++) {
							if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).endsWith("_random")) {
								int randomLevel = random.nextInt(RandomPackage.getPlaceholderConfig().getInt("gkits.max-enchant-level"));
								for(int z = 1; z <= 10; z++) { if(randomLevel < RandomPackage.getPlaceholderConfig().getInt("gkits.min-enchant-level")) { randomLevel = randomLevel + 1; } }
								enchantLevel = randomLevel;
							} else if(RandomPackage.getGkitsConfig().getString(event.getRawSlot() + ".items." + i + ".enchant" + p).endsWith("_" + q)) { enchantLevel = q; }
						}
						item.addUnsafeEnchantment(enchant, enchantLevel);
					}
				}
				//
				if(player.getInventory().firstEmpty() < 0) {
					player.getWorld().dropItem(player.getLocation(), item);
				} else {
					player.getInventory().addItem(item);
					player.updateInventory();
				}
			}
			//
			lore.clear();
			itemMeta.setDisplayName(null);
			item = new ItemStack(Material.AIR);
		}
		return;
	}
	//
	@EventHandler
	private void gkitViewItemClick(InventoryClickEvent event) {
		if(event.isCancelled() || event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) { return;
		} else {
			for(int i = 0; i < RandomPackage.getGkitsConfig().getInt("chest-size"); i++) {
				if(!(RandomPackage.getGkitsConfig().get(i + ".gui.name") == null) && event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".gui.name")))) {
					event.setCancelled(true);
				}
			}
			return;
		}
	}
}
