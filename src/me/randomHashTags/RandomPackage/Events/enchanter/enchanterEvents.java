package me.randomHashTags.RandomPackage.Events.enchanter;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;

public class enchanterEvents implements Listener {
	public static Inventory enchanterGui = Bukkit.createInventory(null, RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Enchanter.EnchanterGui.size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.EnchanterGui.chest-title")));
	public static Inventory enchanter = Bukkit.createInventory(null, RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Enchanter.Enchanter.size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter.chest-title")));
	public static Inventory soulTrackers = Bukkit.createInventory(null, RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Enchanter.SoulTracker.size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker.chest-title")));
	public static Inventory other = Bukkit.createInventory(null, RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Enchanter.Other.size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other.chest-title")));
	public static Inventory custom = Bukkit.createInventory(null, RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Enchanter.Custom.size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Custom.chest-title")));
	private ItemStack item = new ItemStack(Material.ACACIA_STAIRS);
	private ItemMeta itemMeta = item.getItemMeta();
	private ArrayList<String> lore = new ArrayList<String>();
	private Inventory gui = Bukkit.createInventory(null, 9), defaultGui = Bukkit.createInventory(null, 9);
	private String type = null;
	private Player player = null;
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		//
		if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.default-gui").equalsIgnoreCase("EnchanterGui")) { defaultGui = enchanterGui;
		} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.default-gui").equalsIgnoreCase("Enchanter")) { defaultGui = enchanter;
		} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.default-gui").equalsIgnoreCase("SoulTracker")) { defaultGui = soulTrackers;
		} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.default-gui").equalsIgnoreCase("Other")) { defaultGui = other;
		} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.default-gui").equalsIgnoreCase("Custom")) { defaultGui = custom;
		} else { defaultGui = enchanterGui; }
		
		for(int z = 1; z <= 5; z++) {
			if(z == 1) { gui = enchanterGui; type = "EnchanterGui";
			} else if(z == 2) { gui = enchanter; type = "Enchanter";
			} else if(z == 3) { gui = soulTrackers; type = "SoulTracker";
			} else if(z == 4) { gui = other; type = "Other";
			} else if(z == 5) { gui = custom; type = "Custom"; }
			//
			for(int i = 0; i < gui.getSize(); i++) {
				int data = 0;
				if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + i) == null)) {
					if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + i + ".item-data") == null)) { data = RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Enchanter." + type + "." + i + ".item-data"); } else { data = 0; }
					if(!(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".item").toUpperCase()) == null)) {
						item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".item").toUpperCase()), 1, (byte) data);
					} else {
						item = new ItemStack(Material.BARRIER, 1);
						itemMeta.setDisplayName(ChatColor.RED + "Invalid item: '" + ChatColor.WHITE + RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".item") + ChatColor.RED + "'");
					}
					if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + i + ".name") == null) && !(item.getType().equals(Material.BARRIER))) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".name"))); }
					if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + i + ".lore") == null)) {
						for(int o = 0; o < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Enchanter." + type + "." + i + ".lore").size(); o++) {
							if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".cost") == null)) {
								lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Enchanter." + type + "." + i + ".lore").get(o).replace("%cost%", RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".cost"))));
							} else {
								lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Enchanter." + type + "." + i + ".lore").get(o)));
							}
						}
					}
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					lore.clear();
				} else { item = new ItemStack(Material.AIR); }
				gui.setItem(i, item);
			}
			//
		}
		//
		
	}
	private String bookType = null;
	@EventHandler
	private void EnchanterEvents(InventoryClickEvent event) {
		player = (Player) event.getWhoClicked();
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR) || event.getWhoClicked().getOpenInventory().getTitle() == null) {
			return;
		} else {
			if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.EnchanterGui.chest-title")))) {
				check(event);
				type = "EnchanterGui"; openGui(event);
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker.chest-title")))) {
				check(event);
				if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter.SoulTracker." + event.getRawSlot()) == null)) {
					type = "SoulTracker"; openGui(event);
					for(int i = 0; i < soulTrackers.getSize(); i++) {
						if(event.getRawSlot() == i && !(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter.SoulTracker." + i + ".purchase") == null)) {
							purchase(event, i, (Player) event.getWhoClicked());
						}
					}
					return;
				}
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter.chest-title")))) {
				check(event);
				if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter.Enchanter." + event.getRawSlot()) == null)) {
					type = "Enchanter"; openGui(event);
					for(int i = 0; i < enchanter.getSize(); i++) {
						if(event.getRawSlot() == i && !(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter.Enchanter." + i + ".purchase") == null)) {
							purchase(event, i, (Player) event.getWhoClicked());
						}
					}
				}
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other.chest-title")))) {
				check(event);
				if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter.Other." + event.getRawSlot()) == null)) {
					type = "Other"; openGui(event);
					for(int i = 0; i < other.getSize(); i++) {
						if(event.getRawSlot() == i && !(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter.Other." + i + ".purchase") == null)) {
							purchase(event, i, (Player) event.getWhoClicked());
						}
					}
				}
				return;
			} else { return; }
			return;
		}
	}
	private void check(InventoryClickEvent event) {
		if(event.getRawSlot() >= event.getWhoClicked().getOpenInventory().getTopInventory().getSize()) {
			event.setCancelled(true); player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
		} else { event.setCancelled(true); }
		return;
	}
	private void openGui(InventoryClickEvent event) {
		if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + event.getRawSlot() + ".open-gui") == null)) {
			if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + event.getRawSlot() + ".open-gui").equalsIgnoreCase("EnchanterGui")) { event.getWhoClicked().openInventory(enchanterGui);
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + event.getRawSlot() + ".open-gui").equalsIgnoreCase("SoulTracker")) { event.getWhoClicked().openInventory(soulTrackers);
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + event.getRawSlot() + ".open-gui").equalsIgnoreCase("Enchanter")) { event.getWhoClicked().openInventory(enchanter);
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + event.getRawSlot() + ".open-gui").equalsIgnoreCase("Other")) { event.getWhoClicked().openInventory(other);
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + event.getRawSlot() + ".open-gui").equalsIgnoreCase("custom")) { event.getWhoClicked().openInventory(custom);
			} else { event.getWhoClicked().sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Invalid 'open-gui' in TinkererEnchanterAlchemist.yml. " + ChatColor.RED + "'" + RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + event.getRawSlot() + ".open-gui") + "'"); return; }
			event.setCancelled(true);
			player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
			return;
		} else { return; }
	}
	private void purchase(InventoryClickEvent event, int i, Player player) {
		if(!(event.getWhoClicked().getGameMode() == GameMode.CREATIVE) && !(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + i + ".purchase") == null) && !(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Enchanter." + type + "." + i + ".cost") == null)) {
			if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".cost").toLowerCase().startsWith("$")) {
				int itemCost = Integer.parseInt(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".cost").replace("$", "").replace(" ", ""));
				if(!(RandomPackage.econ.withdrawPlayer(player, itemCost).transactionSuccess())) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchanter.need-more-cash").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
					player.closeInventory();
					return;
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchanter.purchase-item-with-cash").replace("%amount%", "" + itemCost)));
				}
			} else {
				int itemCost = Integer.parseInt(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter." + type + "." + i + ".cost").replace("XP", "").replace(" ", ""));
				if(player.getTotalExperience() - itemCost < 0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchanter.need-more-xp").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
					player.closeInventory();
					return;
				} else {
					int playerXP = player.getTotalExperience() - itemCost;
					player.setTotalExperience(0); player.setLevel(0); player.setExp(0);
					player.giveExp(playerXP);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchanter.purchase-item-with-xp").replace("%amount%", "" + itemCost)));
				}
			}
		} else if(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
			player.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "You didn't lose any money / xp because you are in Creative Mode.");
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
		}
		int data = 0;
		if(type.equalsIgnoreCase("Enchanter") && RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().endsWith("enchantmentbook")) {
			if(!(RandomPackage.getBookOptionsConfig().get("reveal-item-data") == null)) { data = RandomPackage.getBookOptionsConfig().getInt("reveal-item-data"); } else { data = 0; }
			item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()));
			if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().startsWith("soul")) { bookType = "Soul";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().startsWith("legendary")) { bookType = "Legendary";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().startsWith("ultimate")) { bookType = "Ultimate";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().startsWith("elite")) { bookType = "Elite";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().startsWith("unique")) { bookType = "Unique";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Enchanter." + i + ".purchase").toLowerCase().startsWith("simple")) { bookType = "Simple"; }
			//
			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString(bookType + ".name")));
			for(int o = 0; o < RandomPackage.getBookOptionsConfig().getStringList(bookType + ".lore").size(); o++) {
				lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getStringList(bookType + ".lore").get(o)));
			}
		} else if(type.equalsIgnoreCase("SoulTracker") && RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker." + i + ".purchase").toLowerCase().endsWith("soultracker")) {
			if(!(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item-data") == null)) { data = RandomPackage.getGivedpItemsConfig().getInt("SoulTrackers.item-data"); } else { data = 0; }
			item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase()), 1, (byte) data);
			if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker." + i + ".purchase").toLowerCase().startsWith("legendary")) { bookType = "legendary";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker." + i + ".purchase").toLowerCase().startsWith("ultimate")) { bookType = "ultimate";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker." + i + ".purchase").toLowerCase().startsWith("elite")) { bookType = "elite";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker." + i + ".purchase").toLowerCase().startsWith("unique")) { bookType = "unique";
			} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.SoulTracker." + i + ".purchase").toLowerCase().startsWith("simple")) { bookType = "simple"; }
			//
			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + bookType + ".name")));
			for(int o = 0; o < RandomPackage.getGivedpItemsConfig().getStringList("SoulTrackers." + bookType + ".lore").size(); o++) {
				lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList("SoulTrackers." + bookType + ".lore").get(o)));
			}
		} else {
			if(type.equalsIgnoreCase("Other")) {
				String givedpItem = null, stType = null;
				if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other." + i + ".purchase").equalsIgnoreCase("MysteryMobSpawner") && type.equalsIgnoreCase("Other")) { givedpItem = "MysteryMobSpawners";
				} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other." + i + ".purchase").equalsIgnoreCase("WhiteScroll") && type.equalsIgnoreCase("Other")) { givedpItem = "WhiteScrolls";
				} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other." + i + ".purchase").equalsIgnoreCase("BlackScroll") && type.equalsIgnoreCase("Other")) { givedpItem = "BlackScrolls";
				} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other." + i + ".purchase").equalsIgnoreCase("TransmogScroll") && type.equalsIgnoreCase("Other")) { givedpItem = "TransmogScrolls";
				} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other." + i + ".purchase").equalsIgnoreCase("ItemNameTag") && type.equalsIgnoreCase("Other")) { givedpItem = "ItemNameTags";
				} else if(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Enchanter.Other." + i + ".purchase").toLowerCase().endsWith("soultracker") && type.equalsIgnoreCase("Other")) { givedpItem = "SoulTrackers"; stType = RandomPackage.getGivedpItemsConfig().getString("Enchanter.Other." + i + ".purchase").toLowerCase().replace("soultracker", "");
				} else {
					item = new ItemStack(Material.BARRIER); givedpItem = null;
				}
				if(!(givedpItem == null)) {
					if(!(RandomPackage.getGivedpItemsConfig().getString(givedpItem + ".item-data") == null)) { data = RandomPackage.getGivedpItemsConfig().getInt(givedpItem + ".item-data"); } else { data = 0; }
					item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString(givedpItem + ".item").toUpperCase()), 1, (byte) data);
					if(givedpItem.equalsIgnoreCase("SoulTrackers") && !(stType == null)) {
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString(givedpItem + "." + stType + ".name")));
					} else { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString(givedpItem + ".name"))); }
					for(int p = 0; p < RandomPackage.getGivedpItemsConfig().getStringList(givedpItem + ".lore").size(); p++) {
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList(givedpItem + ".lore").get(p)));
					}
				}
			} else if(type.equalsIgnoreCase("Custom")) {
				// Coming soon
			} else { return; }
		}
		//
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		lore.clear();
		if(event.getWhoClicked().getInventory().firstEmpty() < 0) { event.getWhoClicked().getWorld().dropItem(event.getWhoClicked().getLocation(), item);
		} else { event.getWhoClicked().getInventory().addItem(item); }
		((Player) event.getWhoClicked()).updateInventory();
		return;
	}
}
