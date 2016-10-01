package me.randomHashTags.RandomPackage.Events.dropPackages;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;

public class dropPackageEvents implements Listener {
	private ArrayList<Player> randomizedPanes = new ArrayList<Player>();
	private String type = null;
	private ItemStack item = new ItemStack(Material.ACACIA_STAIRS);
	private ItemMeta itemMeta = item.getItemMeta();
	private ArrayList<String> lore = new ArrayList<String>();
	private Inventory inventory = null;
	private int data = 0;
	private Random random = new Random();
	@EventHandler
	private void playerOpenDropPackageEvent(PlayerInteractEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR) || !(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) || !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) || !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore())) { return;
		} else if(!(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("legendary.name")))) && !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("ultimate.name"))))
				&& !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("elite.name")))) && !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("unique.name"))))
				&& !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("simple.name")))) && !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("god.name"))))) {
			return;
		} else {
			event.setCancelled(true);
			event.getPlayer().updateInventory();
			event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHEST_OPEN, 1, 1);
			for(int i = 1; i <= 6; i++) {
				if(i == 1) { type = "god";
				} else if(i == 2) { type = "legendary";
				} else if(i == 3) { type = "ultimate";
				} else if(i == 4) { type = "elite";
				} else if(i == 5) { type = "unique";
				} else if(i == 6) { type = "simple"; }
				if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial(RandomPackage.getDropPackageConfig().getString(type + ".item").toUpperCase())) && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString(type + ".name")))) {
					inventory = Bukkit.createInventory(event.getPlayer(), RandomPackage.getDropPackageConfig().getInt(type + ".chest-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString(type + ".chest-title")));
					for(int o = 0; o < inventory.getSize(); o++) {
						if(!(RandomPackage.getDropPackageConfig().get("panes.select-item-data") == null)) { data = RandomPackage.getDropPackageConfig().getInt("panes.select-item-data"); } else { data = 0; }
						item = new ItemStack(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.select-item").toUpperCase()), o + 1, (byte) data);
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("panes.select-item-name")));
						for(int p = 0; p < RandomPackage.getDropPackageConfig().getStringList("panes.select-item-lore").size(); p++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getStringList("panes.select-item-lore").get(p)));
						}
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
						inventory.setItem(o, item);
						lore.clear();
					}
				}
			}
			event.getPlayer().openInventory(inventory);
			return;
		}
	}
	@EventHandler
	private void inventoryCheckEvent(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) {
			return;
		} else {
			String type = null;
			if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("god.chest-title")))) { type = "god";
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("legendary.chest-title")))) { type = "legendary";
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("ultimate.chest-title")))) { type = "ultimate";
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("elite.chest-title")))) { type = "elite";
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("unique.chest-title")))) { type = "unique";
			} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("simple.chest-title")))) { type = "simple"; } else { return; }
			playerChoosePaneEvent(event, type);
			return;
		}
	}
	@SuppressWarnings("deprecation")
	private void playerChoosePaneEvent(InventoryClickEvent event, String type) {
		event.setCancelled(true);
		if(event.getRawSlot() > event.getWhoClicked().getOpenInventory().getTopInventory().getSize()) { return; }
		//
		if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.select-item").toUpperCase()))) {
			if(!(RandomPackage.getDropPackageConfig().get("panes.selected-item-data") == null)) { data = RandomPackage.getDropPackageConfig().getInt("panes.selected-item-data"); } else { data = 0; }
			item = new ItemStack(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.selected-item").toUpperCase()), 1, (byte) data);
			((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 3);
		} else if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.selected-item").toUpperCase()))) {
			if(!(RandomPackage.getDropPackageConfig().get("panes.select-item-data") == null)) { data = RandomPackage.getDropPackageConfig().getInt("panes.select-item-data"); } else { data = 0; }
			item = new ItemStack(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.select-item").toUpperCase()), event.getRawSlot() + 1, (byte) data);
			((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
		} else if(!(randomizedPanes.contains(event.getWhoClicked())) && event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
			String paneType = null;
			if(event.getCurrentItem().getData().getData() == 1) { paneType = "legendary"; } else if(event.getCurrentItem().getData().getData() == 3) { paneType = "elite"; } else if(event.getCurrentItem().getData().getData() == 4) { paneType = "ultimate"; } else if(event.getCurrentItem().getData().getData() == 5) { paneType = "unique"; } else if(event.getCurrentItem().getData().getData() == 8) { paneType = "simple"; }
			revealPaneSelected(event, type, paneType);
			return;
		} else { return; }
		itemMeta.setDisplayName(event.getCurrentItem().getItemMeta().getDisplayName());
		lore.addAll(event.getCurrentItem().getItemMeta().getLore());
		finish(event);
		int has4panes = 0;
		for(int o = 0; o < event.getWhoClicked().getOpenInventory().getTopInventory().getSize(); o++) {
			if(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(o).getType().equals(Material.THIN_GLASS)) {
				has4panes = has4panes + 1;
				if(has4panes == 4) {
					for(int i = 0; i < event.getWhoClicked().getOpenInventory().getTopInventory().getSize(); i++) {
						if(!(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.THIN_GLASS))) {
							event.getWhoClicked().getOpenInventory().getTopInventory().setItem(i, new ItemStack(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.select-item").toUpperCase()), i + 1));
						}
					}
					randomizedPanes.add((Player) event.getWhoClicked());
					Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
						public void run() {
							if(randomizedPanes.contains(event.getWhoClicked())) {
								randomizedPanes.remove((Player) event.getWhoClicked());
								for(int i = 0; i < event.getWhoClicked().getOpenInventory().getTopInventory().getSize(); i++) {
									if(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(i).hasItemMeta()) {
										int randomRarity = random.nextInt(8);
										for(int p = 1; p <= 10; p++) { if(!(randomRarity == 1) && !(randomRarity == 3) && !(randomRarity == 4) && !(randomRarity == 5) && !(randomRarity == 8)) { randomRarity = random.nextInt(9); } }
										ChatColor type = null;
										if(randomRarity == 1) { type = ChatColor.GOLD; } else if(randomRarity == 3) { type = ChatColor.AQUA; } else if(randomRarity == 4) { type = ChatColor.YELLOW; } else if(randomRarity == 5) { type = ChatColor.GREEN; } else if(randomRarity == 8) { type = ChatColor.GRAY; } else { type = ChatColor.RED; }
										item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) randomRarity);
										itemMeta.setDisplayName(type + "" + ChatColor.BOLD + ChatColor.stripColor(event.getInventory().getItem(i).getItemMeta().getDisplayName()));
										lore.addAll(event.getInventory().getItem(i).getItemMeta().getLore());
										itemMeta.setLore(lore);
										item.setItemMeta(itemMeta);
										event.getWhoClicked().getOpenInventory().setItem(i, item);
									} else {
										event.getWhoClicked().getOpenInventory().setItem(i, new ItemStack(Material.AIR));
									}
									lore.clear();
								}
							}
						}
					}, 20 * 5);
				}
			}
		}
		return;
	}
	private void revealPaneSelected(InventoryClickEvent event, String dpType, String paneType) {
		String itemType = null;
		FileConfiguration config = null;
		int maxItems = 0, randomItem = 0;
		if(dpType.equalsIgnoreCase("legendary")) { config = RandomPackage.getLegendaryDropPackageConfig();
		} else if(dpType.equalsIgnoreCase("ultimate")) { config = RandomPackage.getUltimateDropPackageConfig();
		} else if(dpType.equalsIgnoreCase("elite")) { config = RandomPackage.getEliteDropPackageConfig();
		} else if(dpType.equalsIgnoreCase("unique")) { config = RandomPackage.getUniqueDropPackageConfig();
		} else if(dpType.equalsIgnoreCase("simple")) { config = RandomPackage.getSimpleDropPackageConfig();
		} else { return; }
		for(int i = 1; i <= 100; i++) { if(!(config.get(paneType + ".item" + i) == null)) { maxItems = i; } }
		randomItem = random.nextInt(maxItems);
		if(randomItem == 0) { randomItem = 1; }
		if(!(config.get(paneType + ".item" + randomItem + ".item") == null)) {
			int data = 0, amount = 1;
			if(!(Material.getMaterial(config.getString(paneType + ".item" + randomItem + ".item").toUpperCase()) == null)) {
				if(!(config.get(paneType + ".item" + randomItem + ".item-data") == null)) { data = config.getInt(paneType + ".item" + randomItem + ".item-data"); } else { data = 0; }
				if(!(config.get(paneType + ".item" + randomItem + ".amount") == null)) { amount = config.getInt(paneType + ".item" + randomItem + ".amount"); } else { amount = 1; }
				item = new ItemStack(Material.getMaterial(config.getString(paneType + ".item" + randomItem + ".item").toUpperCase()), amount, (byte) data);
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("whitescroll")) { itemType = "WhiteScrolls";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("blackscroll")) { itemType = "BlackScrolls";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("transmogscroll")) { itemType = "TransmogScrolls";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("itemnametag")) { itemType = "ItemNameTags";
			//} else if(config.getString(paneType + ".item" + randomItem + ".item").toLowerCase().endsWith("soultracker")) { itemType = "SoulTrackers";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("mysterymobspawner")) { itemType = "MysteryMobSpawners";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("soulbook")) { itemType = "SoulBook";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("legendarybook")) { itemType = "LegendaryBook";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("ultimatebook")) { itemType = "UltimateBook";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("elitebook")) { itemType = "EliteBook";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("uniquebook")) { itemType = "UniqueBook";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("simplebook")) { itemType = "SimpleBook";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("legendarydp")) { itemType = "legendaryDP";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("ultimatedp")) { itemType = "ultimateDP";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("elitedp")) { itemType = "eliteDP";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("uniquedp")) { itemType = "uniqueDP";
			} else if(config.getString(paneType + ".item" + randomItem + ".item").equalsIgnoreCase("simpledp")) { itemType = "simpleDP";
			} else {
				event.getWhoClicked().sendMessage(RandomPackage.prefix + ChatColor.RED + "Invalid material at '" + paneType + ".item" + randomItem + ".item'");
				return;
			}
			if(itemType.equalsIgnoreCase("whitescrolls") || itemType.equalsIgnoreCase("blackscrolls") || itemType.equalsIgnoreCase("soultrackers") || itemType.equalsIgnoreCase("transmogscrolls") || itemType.equalsIgnoreCase("itemnametags")) {
				if(!(RandomPackage.getGivedpItemsConfig().get(itemType + ".item-data") == null)) { data = RandomPackage.getGivedpItemsConfig().getInt(itemType + ".item-data"); } else { data = 0; }
				if(!(config.get(paneType + ".item" + randomItem + ".amount") == null)) { amount = config.getInt(paneType + ".item" + randomItem + ".amount"); } else { amount = 1; }
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString(itemType + ".item").toUpperCase()), amount, (byte) data);
				if(!(RandomPackage.getGivedpItemsConfig().get(itemType + ".name") == null)) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString(itemType + ".name"))); } else { itemMeta.setDisplayName(null); }
				for(int i = 0; i < RandomPackage.getGivedpItemsConfig().getStringList(itemType + ".lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList(itemType + ".lore").get(i).replace("%percent%", "" + random.nextInt(101))));
				}
			} else if(itemType.endsWith("Book")) {
				itemType = itemType.replace("Book", "");
				if(!(RandomPackage.getBookOptionsConfig().get("given-item-data") == null)) { data = RandomPackage.getBookOptionsConfig().getInt("given-item-data"); } else { data = 0; }
				if(!(config.get(paneType + ".item" + randomItem + ".amount") == null)) { amount = config.getInt(paneType + ".item" + randomItem + ".amount"); } else { amount = 1; }
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("given-item").toUpperCase()), amount, (byte) data);
				if(!(RandomPackage.getBookOptionsConfig().get(itemType + ".name") == null)) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString(itemType + ".name"))); } else { itemMeta.setDisplayName(null); }
				for(int i = 0; i < RandomPackage.getBookOptionsConfig().getStringList(itemType + ".lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getStringList(itemType + ".lore").get(i)));
				}
			} else if(itemType.endsWith("DP")) {
				itemType = itemType.replace("DP", "");
				if(!(RandomPackage.getDropPackageConfig().get(itemType + ".item-data") == null)) { data = RandomPackage.getDropPackageConfig().getInt(itemType + ".item-data"); } else { data = 0; }
				if(!(config.get(paneType + ".item" + randomItem + ".amount") == null)) { amount = config.getInt(paneType + ".item" + randomItem + ".amount"); } else { amount = 1; }
				item = new ItemStack(Material.getMaterial(RandomPackage.getDropPackageConfig().getString(itemType + ".item").toUpperCase()), amount, (byte) data);
				if(!(RandomPackage.getDropPackageConfig().get(itemType + ".name") == null)) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString(itemType + ".name"))); } else { itemMeta.setDisplayName(null); }
				for(int i = 0; i < RandomPackage.getDropPackageConfig().getStringList(itemType + ".lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getStringList(itemType + ".lore").get(i)));
				}
			}
			itemMeta.setLore(lore);
			item.setItemMeta(itemMeta);
			event.setCurrentItem(item);
			lore.clear();
		}
	}
	@EventHandler
	private void inventoryCloseEvent(InventoryCloseEvent event) {
		if(randomizedPanes.contains(event.getPlayer())) {
			randomizedPanes.remove(event.getPlayer());
		} else {
			String type = null;
			for(int i = 1; i <= 6; i++) {
				if(i == 1) { type = "god";
				} else if(i == 2) { type = "legendary";
				} else if(i == 3) { type = "ultimate";
				} else if(i == 4) { type = "elite";
				} else if(i == 5) { type = "unique";
				} else if(i == 6) { type = "simple"; }
				if(event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString(type + ".chest-title")))) {
					for(int o = 0; o < event.getInventory().getSize(); o++) {
						if(!(event.getInventory().getItem(o) == null) && !(event.getInventory().getItem(o).getType().equals(Material.AIR))) {
							if(event.getInventory().getItem(o).getType().equals(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.select-item").toUpperCase())) && event.getInventory().getItem(o).hasItemMeta() && event.getInventory().getItem(o).getItemMeta().hasDisplayName() && !(event.getInventory().getItem(o).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getDropPackageConfig().getString("panes.select-item-name"))))
									|| !(event.getInventory().getItem(o).getType().equals(Material.getMaterial(RandomPackage.getDropPackageConfig().getString("panes.select-item").toUpperCase())))) {
								event.getPlayer().getInventory().addItem(event.getInventory().getItem(o));
								((Player) event.getPlayer()).updateInventory();
							}
						}
					}
				}
			}
		}
		return;
	}
	@EventHandler
	private void randomizePanes(PluginEnableEvent event) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomPackage.getPlugin(), new Runnable() {
			public void run() {
				for(Player player : randomizedPanes) {
					player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 3);
					for(int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
						int randomRarity = random.nextInt(8);
						for(int p = 1; p <= 10; p++) { if(!(randomRarity == 1) && !(randomRarity == 3) && !(randomRarity == 4) && !(randomRarity == 5) && !(randomRarity == 8)) { randomRarity = random.nextInt(9); } }
						if(!(player.getOpenInventory().getTopInventory().getItem(i).hasItemMeta())) {
							player.getOpenInventory().getTopInventory().setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) randomRarity));
						} else {
							item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) randomRarity);
							ChatColor type = null;
							if(randomRarity == 1) { type = ChatColor.GOLD; } else if(randomRarity == 3) { type = ChatColor.AQUA; } else if(randomRarity == 4) { type = ChatColor.YELLOW; } else if(randomRarity == 5) { type = ChatColor.GREEN; } else if(randomRarity == 8) { type = ChatColor.GRAY; } else { type = ChatColor.RED; }
							itemMeta.setDisplayName(type + "" + ChatColor.BOLD + ChatColor.stripColor(player.getOpenInventory().getTopInventory().getItem(i).getItemMeta().getDisplayName()));
							lore.addAll(player.getOpenInventory().getTopInventory().getItem(i).getItemMeta().getLore());
							itemMeta.setLore(lore);
							item.setItemMeta(itemMeta);
							player.getOpenInventory().getTopInventory().setItem(i, item);
						}
						lore.clear();
					}
				}
			}
		}, 0, 3);
		return;
	}
	private void finish(InventoryClickEvent event) {
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		event.setCurrentItem(item);
		((Player) event.getWhoClicked()).updateInventory();
		lore.clear();
		return;
	}
	@EventHandler
	private void pluginDisableEvent(PluginDisableEvent event) {
		if(!(randomizedPanes.isEmpty())) {
			for(Player player : randomizedPanes) {
				player.closeInventory();
				player.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "You have been kicked out of the gui due the plugin restarting.");
			}
		} else { return; }
	}
}
