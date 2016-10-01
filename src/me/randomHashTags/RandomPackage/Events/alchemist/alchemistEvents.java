package me.randomHashTags.RandomPackage.Events.alchemist;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;

public class alchemistEvents implements Listener {
	public static Inventory alchemistGui = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.chest-title")));
	private static ItemStack item = new ItemStack(Material.ACACIA_STAIRS);
	private static ItemMeta itemMeta = item.getItemMeta();
	private static ArrayList<String> lore = new ArrayList<String>();
	private String dust = null, enchantName = null, rarity = null, upgrade = null, previous = null;
	private Random random = new Random();
	private int success = 0, destroy = 0, data = 0;
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		//
		
		//
		return;
	}
	public static void playerOpenAlchemist(Player player) {
		alchemistGui = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.chest-title")));
		for(int i = 0; i < 27; i++) {
			if(!(i == 3) && !(i == 5) && !(i == 13) && !(i == 22)) {
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
				itemMeta.setDisplayName(" ");
			} else if(i == 13) {
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 0);
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.preview.name")));
				for(int o = 0; o < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.preview.lore").size(); o++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.preview.lore").get(o)));
				}
			} else if(i == 22) {
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.exchange.name")));
				for(int o = 0; o < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.exchange.lore").size(); o++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.exchange.lore").get(o)));
				}
			} else { item = new ItemStack(Material.AIR); }
			
			if(!(item.getType().equals(Material.AIR))) {
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				lore.clear();
			}
			alchemistGui.setItem(i, item);
		}
		player.openInventory(alchemistGui);
	}
	private ArrayList<String> price = new ArrayList<String>();
	@EventHandler
	private void inventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem() == null || !(player.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.chest-title"))))) { return;
		} else {
			if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust.legendary")))
					|| event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.primal.legendary")))
					|| !(event.getCurrentItem().hasItemMeta()) || !(event.getCurrentItem().getItemMeta().hasDisplayName()) || !(event.getCurrentItem().getItemMeta().hasLore())) { event.setCancelled(true); return; }
			if(event.getRawSlot() == 3 && !(player.getOpenInventory().getItem(3).getType() == Material.AIR) && player.getOpenInventory().getItem(3).hasItemMeta()
					|| event.getRawSlot() == 5 && !(player.getOpenInventory().getItem(5).getType() == Material.AIR)) {
				for(int i = 0; i < price.size(); i++) {
					if(price.get(i).startsWith(player.getName())) {
						price.remove(i);
					}
				}
				player.getInventory().addItem(player.getOpenInventory().getItem(event.getRawSlot()));
				event.setCurrentItem(new ItemStack(Material.AIR));
				event.setCancelled(true);
				
				lore.clear();
				
				if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Alchemist.preview.item-data") == null)) { data = RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Alchemist.preview.item-data"); } else { data = 0; }
				item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.preview.item").toUpperCase()), 1, (byte) data);
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.preview.name")));
				for(int i = 0; i < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.preview.lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.preview.lore").get(i)));
				}
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				player.getOpenInventory().setItem(13, item);
				
				if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Alchemist.exchange.item-data") == null)) { data = RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Alchemist.exchange.item-data"); } else { data = 0; }
				item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.exchange.item").toUpperCase()), 1, (byte) data);
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.exchange.name")));
				for(int i = 0; i < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.exchange.lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.exchange.lore").get(i)));
				}
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				player.getOpenInventory().setItem(22, item);
				player.updateInventory();
				return;
			} else if(event.getRawSlot() == 13) { event.setCancelled(true); return;
			} else if(event.getRawSlot() == 22) {
				if(player.getOpenInventory().getItem(3).getType() == Material.AIR || player.getOpenInventory().getItem(5).getType() == Material.AIR) { event.setCancelled(true); return;
				} else {
					int cost = 0;
					for(int i = 0; i < price.size(); i++) {
						if(price.get(i).startsWith(player.getName())) {
							cost = Integer.parseInt(price.get(i).replace(player.getName() + "_", ""));
							price.remove(i);
							event.setCancelled(true);
							//
							if(!(player.getTotalExperience() >= cost) && !(player.getGameMode() == GameMode.CREATIVE)) {
								player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Alchemist.need-more-xp").replace("%prefix%", RandomPackage.getMessagesConfig().getString("prefix"))));
								if(player.getInventory().firstEmpty() < 0) {
									player.getWorld().dropItem(player.getLocation(), player.getOpenInventory().getItem(3));
									player.getWorld().dropItem(player.getLocation(), player.getOpenInventory().getItem(5));
								} else {
									player.getInventory().addItem(player.getOpenInventory().getItem(3));
									player.getInventory().addItem(player.getOpenInventory().getItem(5));
								}
							} else {
								int experience = player.getTotalExperience() - cost;
								if(!(player.getGameMode() == GameMode.CREATIVE)) { player.setTotalExperience(0); player.setExp(0); player.setLevel(0); player.giveExp(experience); }
								
								player.getInventory().addItem(player.getOpenInventory().getItem(13));
								player.playSound(player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
								for(int o = 1; o <= 50; o++) {
									player.getWorld().playEffect(new Location(player.getWorld(), player.getLocation().getX() + random.nextDouble(), player.getLocation().getY() + random.nextDouble(), player.getLocation().getZ() + random.nextDouble()), Effect.HAPPY_VILLAGER, 1);
								}
								player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1, 2);
							}
							player.updateInventory();
							player.closeInventory();
							return;
						}
					}
					return;
				}
			} else if(event.getRawSlot() > 27 && event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) { event.setCancelled(true); return; } else {
				if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase())) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
					if(!(player.getOpenInventory().getItem(3).getType() == Material.AIR) && player.getOpenInventory().getItem(5).getType() == Material.AIR
							&& player.getOpenInventory().getItem(3).getItemMeta().getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()) && event.getCurrentItem().getAmount() == 1) {
						player.getOpenInventory().setItem(5, event.getCurrentItem());
						
						for(int i = 0; i <= 100; i++) {
							if(player.getOpenInventory().getItem(3).getItemMeta().getLore().get(0).equals(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("success").replace("%success%", "" + i)))) { success = i; }
							if(player.getOpenInventory().getItem(3).getItemMeta().getLore().get(1).equals(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("destroy").replace("%destroy%", "" + i)))) { destroy = i; }
						}
						for(int i = 0; i <= 100; i++) {
							if(player.getOpenInventory().getItem(5).getItemMeta().getLore().get(0).contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("success").replace("%success%", "" + i)))) { success = (success / 4) + (i / 4); }
							if(player.getOpenInventory().getItem(5).getItemMeta().getLore().get(1).contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("destroy").replace("%destroy%", "" + i)))) {
								if(i > destroy) { destroy = (i + destroy) / 4 + i; } else { destroy = (i + destroy) / 4 + destroy; }
							}
						}
						if(destroy > 100) { destroy = 100; }
						for(int i = 0; i <= 100; i++) {
							if(i < Data.getLegendaryBookNames.size() && Math.addExact(i, 1) < Data.getLegendaryBookNames.size() && Data.getLegendaryBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())
									&& Data.getLegendaryBookNames.get(Math.addExact(i, 1)).startsWith(event.getCurrentItem().getItemMeta().getDisplayName().replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", ""))) {
								enchantName = Data.getLegendaryBookNames.get(Math.addExact(i, 1)); rarity = "Legendary";
							} else if(i < Data.getUltimateBookNames.size() && Math.addExact(i, 1) < Data.getUltimateBookNames.size() && Data.getUltimateBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())
									&& Data.getUltimateBookNames.get(Math.addExact(i, 1)).startsWith(event.getCurrentItem().getItemMeta().getDisplayName().replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", ""))) {
								enchantName = Data.getUltimateBookNames.get(Math.addExact(i, 1)); rarity = "Ultimate";
							} else if(i < Data.getEliteBookNames.size() && Math.addExact(i, 1) < Data.getEliteBookNames.size() && Data.getEliteBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())
									&& Data.getEliteBookNames.get(Math.addExact(i, 1)).startsWith(event.getCurrentItem().getItemMeta().getDisplayName().replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", ""))) {
								enchantName = Data.getEliteBookNames.get(Math.addExact(i, 1)); rarity = "Elite";
							} else if(i < Data.getUniqueBookNames.size() && Math.addExact(i, 1) < Data.getUniqueBookNames.size() && Data.getUniqueBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())
									&& Data.getUniqueBookNames.get(Math.addExact(i, 1)).startsWith(event.getCurrentItem().getItemMeta().getDisplayName().replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", ""))) {
								enchantName = Data.getUniqueBookNames.get(Math.addExact(i, 1)); rarity = "Unique";
							} else if(i < Data.getSimpleBookNames.size() && Math.addExact(i, 1) < Data.getSimpleBookNames.size() && Data.getSimpleBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName())
									&& Data.getSimpleBookNames.get(Math.addExact(i, 1)).startsWith(event.getCurrentItem().getItemMeta().getDisplayName().replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", ""))) {
								enchantName = Data.getSimpleBookNames.get(Math.addExact(i, 1)); rarity = "Simple";
							} else if(i == 100 && enchantName == null) { event.setCancelled(true); return; }
						}
						item = new ItemStack(Material.BOOK, 1);
						itemMeta.setDisplayName(enchantName);
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("success").replace("%success%", "" + success)));
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("destroy").replace("%destroy%", "" + destroy)));
						
						for(int i = 0; i < RandomPackage.getPlugin().getConfig().getStringList(rarity + "." + ChatColor.stripColor(enchantName).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "") + ".BookLore").size(); i++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getStringList(rarity + "." + ChatColor.stripColor(enchantName).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "") + ".BookLore").get(i)));
						}
						enchantName = ChatColor.stripColor(enchantName.replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""));
						if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Armor")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Armor")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Axe")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Axe")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Bow")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Bow")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Helmet")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Helmet")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Chestplate")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Chestplate")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Leggings")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Leggings")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Boots")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Boots")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Pickaxe")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Pickaxe")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Shovel")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Shovel")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Sword")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Sword")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Tool")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Tool")));
						} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Weapon")) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Weapon")));
						} else { return; }
						
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.last-book-lore")));
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
						player.getOpenInventory().setItem(13, item);
						
						lore.clear();
						int xpCost = 0;
						for(int i = 1; i <= 6; i++) {
							String type = null;
							ArrayList<String> typE = new ArrayList<String>();
							if(i == 1) { type = "soul"; typE = Data.getSoulBookNames;
							} else if(i == 2) { type = "legendary"; typE = Data.getLegendaryBookNames;
							} else if(i == 3) { type = "ultimate"; typE = Data.getUltimateBookNames;
							} else if(i == 4) { type = "elite"; typE = Data.getEliteBookNames;
							} else if(i == 5) { type = "unique"; typE = Data.getUniqueBookNames;
							} else if(i == 6) { type = "simple"; typE = Data.getSimpleBookNames; }
							if(typE.contains(event.getCurrentItem().getItemMeta().getDisplayName())) {
								xpCost = xpCost + RandomPackage.getAlchemistUpgradeCostsConfig().getInt("book-upgrades." + type + "-cost") + RandomPackage.getAlchemistUpgradeCostsConfig().getInt("book-upgrades." + type + "." + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1")));
							}
						}
						if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Alchemist.accept.item-data") == null)) { data = RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Alchemist.accept.item-data"); } else { data = 0; }
						item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.accept.item").toUpperCase()), 1, (byte) data);
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.accept.name")));
						for(int i = 0; i < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.accept.lore").size(); i++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.accept.lore").get(i).replace("%cost%", "" + xpCost)));
							price.add(player.getName() + "_" + xpCost);
						}
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
						player.getOpenInventory().setItem(22, item);
						
						event.setCurrentItem(new ItemStack(Material.AIR));
					} else if(player.getOpenInventory().getItem(3).getType() == Material.AIR && player.getOpenInventory().getItem(5).getType() == Material.AIR) {
						for(int i = 0; i < 300; i++) {
							if(i < Data.getLegendaryBookNames.size() && Math.addExact(i, 1) < Data.getLegendaryBookNames.size() && Data.getLegendaryBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()) && !(Data.getLegendaryBookNames.get(Math.addExact(i, 1)).startsWith(Data.getLegendaryBookNames.get(i).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "")))
									|| i < Data.getUltimateBookNames.size() && Math.addExact(i, 1) < Data.getUltimateBookNames.size() && Data.getUltimateBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()) && !(Data.getUltimateBookNames.get(Math.addExact(i, 1)).startsWith(Data.getUltimateBookNames.get(i).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "")))
									|| i < Data.getEliteBookNames.size() && Math.addExact(i, 1) < Data.getEliteBookNames.size() && Data.getEliteBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()) && !(Data.getEliteBookNames.get(Math.addExact(i, 1)).startsWith(Data.getEliteBookNames.get(i).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "")))
									|| i < Data.getUniqueBookNames.size() && Math.addExact(i, 1) < Data.getUniqueBookNames.size() && Data.getUniqueBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()) && !(Data.getUniqueBookNames.get(Math.addExact(i, 1)).startsWith(Data.getUniqueBookNames.get(i).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "")))
									|| i < Data.getSimpleBookNames.size() && Math.addExact(i, 1) < Data.getSimpleBookNames.size() && Data.getSimpleBookNames.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()) && !(Data.getSimpleBookNames.get(Math.addExact(i, 1)).startsWith(Data.getSimpleBookNames.get(i).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "")))) {
								event.setCancelled(true);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Alchemist.max-enchants").replace("%prefix%", RandomPackage.getMessagesConfig().getString("prefix"))));
								return;
							}
						}
						player.getOpenInventory().setItem(3, event.getCurrentItem());
						event.setCurrentItem(new ItemStack(Material.AIR));
					} else { event.setCancelled(true); }
					player.updateInventory();
					return;
				} else if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust.item").toUpperCase()))
						&& event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
					dust = "dust";
				} else if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.primal.item").toUpperCase()))
						&& event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
					dust = "primal";
				} else { event.setCancelled(true); return; }
				if(!(dust == null)) {
					if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getAmount() == 1) {
						if(player.getOpenInventory().getItem(3).getType() == Material.AIR) {
							player.getOpenInventory().setItem(3, event.getCurrentItem()); event.setCurrentItem(new ItemStack(Material.AIR)); return;
						} else if(player.getOpenInventory().getItem(5).getType() == Material.AIR && player.getOpenInventory().getItem(3).getItemMeta().getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName())) {
							player.getOpenInventory().setItem(5, event.getCurrentItem()); event.setCurrentItem(new ItemStack(Material.AIR));
							
							item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + ".item").toUpperCase()));
							if(player.getOpenInventory().getItem(3).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + ".ultimate")))
									&& player.getOpenInventory().getItem(5).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + ".ultimate")))) {
								upgrade = "legendary"; previous = "ultimate";
							} else if(player.getOpenInventory().getItem(3).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + ".elite")))
									&& player.getOpenInventory().getItem(5).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + "elite")))) {
								upgrade = "ultimate"; previous = "elite";
							} else if(player.getOpenInventory().getItem(3).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + "unique")))
									&& player.getOpenInventory().getItem(5).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + "unique")))) {
								upgrade = "elite"; previous = "unique";
							} else if(player.getOpenInventory().getItem(3).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + ".simple")))
									&& player.getOpenInventory().getItem(5).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + ".simple")))) {
								upgrade = "unique"; previous = "simple";
							} else { event.setCancelled(true); return; }
							
							success = 0;
							for(int i = 0; i <= 30; i++) {
								if(player.getOpenInventory().getItem(3).getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer." + dust + ".lore").get(0).replace("%percent%", "" + i)))) {
									success = success + i;
								}
								if(player.getOpenInventory().getItem(5).getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer." + dust + ".lore").get(0).replace("%percent%", "" + i)))) {
									success = success + i;
								}
							}
							success = success / 2;
							
							itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer." + dust + "." + upgrade)));
							for(int i = 0; i < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer." + dust + ".lore").size(); i++) {
								lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer." + dust + ".lore").get(i).replace("%percent%", "" + success)));
							}
							itemMeta.setLore(lore);
							item.setItemMeta(itemMeta);
							
							player.getOpenInventory().setItem(13, item);
							
							lore.clear();
							
							if(!(RandomPackage.getTinkererEnchanterAlchemistConfig().get("Alchemist.accept.item-data") == null)) { data = RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Alchemist.accept.item-data"); } else { data = 0; }
							item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.accept.item").toUpperCase()), 1, (byte) data);
							itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.accept.name")));
							
							int cost = RandomPackage.getAlchemistUpgradeCostsConfig().getInt("dust-upgrade." + upgrade + "-" + dust.replace("dust", "regular"));
							
							for(int i = 0; i < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.accept.lore").size(); i++) {
								lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Alchemist.accept.lore").get(i).replace("%cost%", "" + cost)));
							}
							price.add(player.getName() + "_" + cost);
							itemMeta.setLore(lore);
							item.setItemMeta(itemMeta);
							player.getOpenInventory().setItem(22, item);
							return;
						} else {
							event.setCancelled(true);
							return;
						}
					} else { event.setCancelled(true); return; }
				} else {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	@EventHandler
	private void inventoryCloseEvent(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(player.isOnline() && player.getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.chest-title")))) {
			if(player.getOpenInventory().getItem(13).getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()))
					|| player.getOpenInventory().getItem(13).getType().equals(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust.item").toUpperCase())) || player.getOpenInventory().getItem(13).getType().equals(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.primal.item").toUpperCase()))) {
				return;
			} else {
				if(!(player.getOpenInventory().getItem(3).getType() == Material.AIR)) { player.getInventory().addItem(player.getOpenInventory().getItem(3)); }
				if(!(player.getOpenInventory().getItem(5).getType() == Material.AIR)) { player.getInventory().addItem(player.getOpenInventory().getItem(5)); }
				return;
			}
		} else { return; }
	}
	@EventHandler
	private void playerQuitEvent(PlayerQuitEvent event) {
		if(event.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Alchemist.chest-title")))) {
			if(event.getPlayer().getInventory().firstEmpty() < 0) {
				if(!(event.getPlayer().getOpenInventory().getTopInventory().getItem(3) == null)) { event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), event.getPlayer().getOpenInventory().getTopInventory().getItem(3)); }
				if(!(event.getPlayer().getOpenInventory().getTopInventory().getItem(5) == null)) { event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), event.getPlayer().getOpenInventory().getTopInventory().getItem(5)); }
			} else {
				if(!(event.getPlayer().getOpenInventory().getTopInventory().getItem(3) == null)) { event.getPlayer().getInventory().addItem(event.getPlayer().getOpenInventory().getTopInventory().getItem(3)); }
				if(!(event.getPlayer().getOpenInventory().getTopInventory().getItem(5) == null)) { event.getPlayer().getInventory().addItem(event.getPlayer().getOpenInventory().getTopInventory().getItem(5)); }
				event.getPlayer().updateInventory();
			}
		} else { return; }
		return;
	}

}
