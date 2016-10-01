package me.randomHashTags.RandomPackage.Events.filter;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.commands.randomPackageCommands;

public class filterEvents implements Listener {
	private static ItemStack item = new ItemStack(Material.ACACIA_STAIRS, 1);
	private static ItemMeta itemMeta = item.getItemMeta();
	private static ArrayList<String> lore = new ArrayList<String>();
	private static String invType = null;
	private static int data = 0;
	public static Inventory inv = Bukkit.createInventory(null, 9);
	public static Inventory itemFilterSelector = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-Selection-title")));
	private static Inventory equipment = Bukkit.createInventory(null, RandomPackage.getFilterConfig().getInt("filter-equipment-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-equipment-title")));
	private static Inventory potionSup = Bukkit.createInventory(null, RandomPackage.getFilterConfig().getInt("filter-potionSupplies-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-potionSupplies-title")));
	private static Inventory raiding = Bukkit.createInventory(null, RandomPackage.getFilterConfig().getInt("filter-raiding-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-raiding-title")));
	private static Inventory food = Bukkit.createInventory(null, RandomPackage.getFilterConfig().getInt("filter-food-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-food-title")));
	private static Inventory specialty = Bukkit.createInventory(null, RandomPackage.getFilterConfig().getInt("filter-specialty-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-specialty-title")));
	private static Inventory ores = Bukkit.createInventory(null, RandomPackage.getFilterConfig().getInt("filter-ores-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-ores-title")));
	@EventHandler
	private void playerItemPickupEvent(PlayerPickupItemEvent event) {
		if(randomPackageCommands.activeFilter.contains(event.getPlayer())) {
			for(int i = 1; i <= 6; i++) {
				if(i == 1) { invType = "equipment";
				} else if(i == 2) { invType = "potionSupplies";
				} else if(i == 3) { invType = "raiding";
				} else if(i == 4) { invType = "food";
				} else if(i == 5) { invType = "specialty";
				} else if(i == 6) { invType = "ores"; } 
				for(int o = 0; o < 27; o++) {
					if(!(RandomPackage.getFilterDataConfig().get(event.getPlayer().getUniqueId().toString() + "." + invType + "." + o) == null)
							&& event.getItem().getItemStack().getType().name().equalsIgnoreCase(RandomPackage.getFilterDataConfig().getString(event.getPlayer().getUniqueId().toString() + "." + invType + "." + o))) {
						return;
					}
				}
			}
			event.setCancelled(true);
			return;
		} else { return; }
	}
	@EventHandler
	private void inventoryClickEvent(InventoryClickEvent event) {
		if(event.isCancelled() || event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)
				|| !(event.getWhoClicked().getOpenInventory().getTitle().equals(equipment.getTitle())) && !(event.getWhoClicked().getOpenInventory().getTitle().equals(potionSup.getTitle()))
				&& !(event.getWhoClicked().getOpenInventory().getTitle().equals(raiding.getTitle())) && !(event.getWhoClicked().getOpenInventory().getTitle().equals(food.getTitle()))
				&& !(event.getWhoClicked().getOpenInventory().getTitle().equals(specialty.getTitle())) && !(event.getWhoClicked().getOpenInventory().getTitle().equals(ores.getTitle()))) {
			return;
		} else {
			event.setCancelled(true);
			return;
		}
	}
	@EventHandler
	private void inventoryCloseEvent(InventoryCloseEvent event) {
		if(event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-equipment-title")))
				|| event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-potionSupplies-title")))
				|| event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-raiding-title")))
				|| event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-food-title")))
				|| event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-specialty-title")))
				|| event.getInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-ores-title")))) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
				public void run() {
					event.getPlayer().openInventory(itemFilterSelector);
				}
			}, 2);
		} else { return; }
		return;
	}
	@EventHandler
	private void filterSelectionChange(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) { return; }
		if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(itemFilterSelector.getTitle())) {
			event.setCancelled(true);
			if(event.getRawSlot() == 1) { invType = "equipment"; changeSelection(player);
			} else if(event.getRawSlot() == 2) { invType = "potionSupplies"; changeSelection(player);
			} else if(event.getRawSlot() == 3) { invType = "raiding"; changeSelection(player);
			} else if(event.getRawSlot() == 5) { invType = "food"; changeSelection(player);
			} else if(event.getRawSlot() == 6) { invType = "specialty"; changeSelection(player);
			} else if(event.getRawSlot() == 7) { invType = "ores"; changeSelection(player); }
		} else if(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(equipment.getTitle()) || event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(potionSup.getTitle())
				|| event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(raiding.getTitle()) || event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(food.getTitle())
				|| event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(specialty.getTitle()) || event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(ores.getTitle())) {
			event.setCancelled(true);
			if(event.getRawSlot() < player.getOpenInventory().getTopInventory().getSize()) {
				if(RandomPackage.getFilterDataConfig().get(player.getUniqueId().toString()) == null) { RandomPackage.getFilterDataConfig().createSection(player.getUniqueId().toString()); }
				if(RandomPackage.getFilterDataConfig().get(player.getUniqueId().toString() + "." + invType + "." + event.getRawSlot()) == null) {
					RandomPackage.getFilterDataConfig().set(player.getUniqueId().toString() + "." + invType + "." + event.getRawSlot(), event.getCurrentItem().getType().name().toUpperCase());
					RandomPackage.saveFilterDataConfig();
					item = event.getCurrentItem();
					itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filtered-items-name-color")) + ChatColor.stripColor(item.getItemMeta().getDisplayName()));
					for(int i = 0; i < RandomPackage.getFilterConfig().getStringList("filtered-lore").size(); i++) {
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getStringList("filtered-lore").get(i)));
					}
					itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
					itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					player.getOpenInventory().setItem(event.getRawSlot(), item);
					lore.clear();
				} else {
					RandomPackage.getFilterDataConfig().set(player.getUniqueId().toString() + "." + invType + "." + event.getRawSlot(), null);
					RandomPackage.saveFilterDataConfig();
					item = event.getCurrentItem();
					itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("unfiltered-items-name-color")) + ChatColor.stripColor(item.getItemMeta().getDisplayName()));
					for(int i = 0; i < RandomPackage.getFilterConfig().getStringList("unfiltered-lore").size(); i++) {
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getStringList("unfiltered-lore").get(i)));
					}
					itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					player.getOpenInventory().setItem(event.getRawSlot(), item);
					lore.clear();
				}
				return;
			} else { return; }
		}
		return;
	}
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		for(int i = 1; i <= 7; i++) {
			if(i == 1) {
				item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
				itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Equipment");
			} else if(i == 2) {
				item = new ItemStack(Material.POTION, 1);
				itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Potion Supplies");
			} else if(i == 3) {
				item = new ItemStack(Material.REDSTONE, 1);
				itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Raiding");
			} else if(i == 5) {
				item = new ItemStack(Material.BREAD, 1);
				itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Food");
			} else if(i == 6) {
				item = new ItemStack(Material.MOB_SPAWNER, 1);
				itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Specialty");
			} else if(i == 7) {
				item = new ItemStack(Material.DIAMOND_ORE, 1);
				itemMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Ores");
			} else { item = new ItemStack(Material.AIR); }
			//
			if(!(item.getType().equals(Material.AIR))) {
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
			}
			itemFilterSelector.setItem(i, item);
			lore.clear();
		}
		return;
	}
	public static void changeSelection(Player player) {
		inv = Bukkit.createInventory(player, RandomPackage.getFilterConfig().getInt("filter-" + invType + "-size"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filter-" + invType + "-title")));
		player.openInventory(inv);
		for(int i = 0; i < inv.getSize(); i++) {
			if(!(RandomPackage.getFilterConfig().get(invType + "." + i) == null)) {
				if(Material.getMaterial(RandomPackage.getFilterConfig().getString(invType + "." + i + ".item").toUpperCase()) == null) {
					item = new ItemStack(Material.BARRIER, 1);
					itemMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Material: '" + RandomPackage.getFilterConfig().getString(invType + "." + i + ".item").toUpperCase() + "'");
				} else {
					item = new ItemStack(Material.getMaterial(RandomPackage.getFilterConfig().getString(invType + "." + i + ".item").toUpperCase()), 1, (byte) data);
					if(!(RandomPackage.getFilterConfig().get(invType + "." + i) == null) && !(RandomPackage.getFilterDataConfig().get(player.getUniqueId().toString()) == null) && !(RandomPackage.getFilterDataConfig().get(player.getUniqueId().toString() + "." + invType + "." + i) == null)) {
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("filtered-items-name-color")) + item.getType().name()
								.replace("DIAMOND", "Diamond").replace("IRON", "Iron").replace("GOLD", "Gold").replace("REDSTONE", "Redstone").replace("BLOCK", "Block").replace("ENDER_PEARL", "Ender Pearl").replace("BONE", "Bone").replace("NETHER", "Nether").replace("STALK", "Stalk")
								.replace("HELMET", "Helmet").replace("CHESTPLATE", "Chestplate").replace("LEGGINGS", "Leggings").replace("BOOTS", "Boots").replace("SWORD", "Sword").replace("AXE", "Axe").replace("BOW", "Bow").replace("HOE", "Hoe").replace("ARROW", "Arrow").replace("_ITEM", "")
								.replace("MAGMA_CREAM", "Magma Cream").replace("SKULL_ITEM", "Player Skulls").replace("SPIDER_EYE", "Spider Eye").replace("GHAST_TEAR", "Ghast Tear").replace("GLOWSTONE", "Glowstone").replace("POTION", "Potion").replace("SPECKLED", "Speckled")
								.replace("SUGAR", "Sugar").replace("SLIME", "Slime").replace("BALL", "Ball").replace("WATER", "Water").replace("LILY", "Lily").replace("GoldEN", "Golden").replace("APPLE", "Apple").replace("POISONOUS", "Poisonous").replace("POTATO", "Potato")
								.replace("QUARTZ", "Quartz").replace("MUSHROOM", "Mushroom").replace("RED", "Red").replace("BROWN", "Brown").replace("CARROT", "Carrot").replace("INK_SACK", "Ink Sack").replace("_OFF", "").replace("_ON", "").replace("TORCH", "Torch").replace("LAVA", "Lava")
								.replace("BUCKET", "Bucket").replace("WATER", "Water").replace("WEB", "Cobweb").replace("SPONGE", "Sponge").replace("ICE", "Ice").replace("PACKED", "Packed").replace("DISPENSER", "Dispenser").replace("DROPPER", "Dropper").replace("MONSTER", "Monster")
								.replace("EGG", "Egg").replace("PISTON", "Piston").replace("_BASE", "").replace("PISTON_STICKY", "Sticky Piston").replace("HOPPER", "Hopper").replace("FLINT_AND_STEEL", "Flint and Steel").replace("PUMPKIN", "Pumpkin").replace("MELON", "Melon").replace("RAW_", "").replace("BEEF", "Beef")
								.replace("CANE", "Cane").replace("CACTUS", "Cactus").replace("PORK", "Pork").replace("GRILLED", "Cooked").replace("COOKED", "Cooked").replace("SEEDS", "Seeds").replace("MOB_SPAWNER", "Mob Spawner").replace("NAME_TAG", "Name Tag").replace("CHEST", "Chest").replace("TRAPPED", "Trapped")
								.replace("ENDER", "Ender").replace("PAPER", "Paper").replace("EMPTY_MAP", "Map").replace("EXP", "Exp").replace("BOTTLE", "Bottle").replace("BOOK", "Book").replace("EMERALD", "Emerald").replace("COAL", "Coal").replace("LAPIS", "Lapis").replace("ORE", "Ore").replace("INGOT", "Ingot")
								.replace("_", " "));
						for(int l = 0; l < RandomPackage.getFilterConfig().getStringList("filtered-lore").size(); l++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getStringList("filtered-lore").get(l)));
						}
						itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
						itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					} else if(!(RandomPackage.getFilterConfig().get(invType + "." + i) == null)) {
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getString("unfiltered-items-name-color")) + item.getType().name()
								.replace("DIAMOND", "Diamond").replace("IRON", "Iron").replace("GOLD", "Gold").replace("REDSTONE", "Redstone").replace("BLOCK", "Block").replace("ENDER_PEARL", "Ender Pearl").replace("BONE", "Bone").replace("NETHER", "Nether").replace("STALK", "Stalk")
								.replace("HELMET", "Helmet").replace("CHESTPLATE", "Chestplate").replace("LEGGINGS", "Leggings").replace("BOOTS", "Boots").replace("SWORD", "Sword").replace("AXE", "Axe").replace("BOW", "Bow").replace("HOE", "Hoe").replace("ARROW", "Arrow").replace("_ITEM", "")
								.replace("MAGMA_CREAM", "Magma Cream").replace("SKULL_ITEM", "Player Skulls").replace("SPIDER_EYE", "Spider Eye").replace("GHAST_TEAR", "Ghast Tear").replace("GLOWSTONE", "Glowstone").replace("POTION", "Potion").replace("SPECKLED", "Speckled")
								.replace("SUGAR", "Sugar").replace("SLIME", "Slime").replace("BALL", "Ball").replace("WATER", "Water").replace("LILY", "Lily").replace("GoldEN", "Golden").replace("APPLE", "Apple").replace("POISONOUS", "Poisonous").replace("POTATO", "Potato")
								.replace("QUARTZ", "Quartz").replace("MUSHROOM", "Mushroom").replace("RED", "Red").replace("BROWN", "Brown").replace("CARROT", "Carrot").replace("INK_SACK", "Ink Sack").replace("_OFF", "").replace("_ON", "").replace("TORCH", "Torch").replace("LAVA", "Lava")
								.replace("BUCKET", "Bucket").replace("WATER", "Water").replace("WEB", "Cobweb").replace("SPONGE", "Sponge").replace("ICE", "Ice").replace("PACKED", "Packed").replace("DISPENSER", "Dispenser").replace("DROPPER", "Dropper").replace("MONSTER", "Monster")
								.replace("EGG", "Egg").replace("PISTON", "Piston").replace("_BASE", "").replace("PISTON_STICKY", "Sticky Piston").replace("HOPPER", "Hopper").replace("FLINT_AND_STEEL", "Flint and Steel").replace("PUMPKIN", "Pumpkin").replace("MELON", "Melon").replace("RAW_", "").replace("BEEF", "Beef")
								.replace("CANE", "Cane").replace("CACTUS", "Cactus").replace("PORK", "Pork").replace("GRILLED", "Cooked").replace("COOKED", "Cooked").replace("SEEDS", "Seeds").replace("MOB_SPAWNER", "Mob Spawner").replace("NAME_TAG", "Name Tag").replace("CHEST", "Chest").replace("TRAPPED", "Trapped")
								.replace("ENDER", "Ender").replace("PAPER", "Paper").replace("EMPTY_MAP", "Map").replace("EXP", "Exp").replace("BOTTLE", "Bottle").replace("BOOK", "Book").replace("EMERALD", "Emerald").replace("COAL", "Coal").replace("LAPIS", "Lapis").replace("ORE", "Ore").replace("INGOT", "Ingot")
								.replace("_", " "));
						for(int l = 0; l < RandomPackage.getFilterConfig().getStringList("unfiltered-lore").size(); l++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getFilterConfig().getStringList("unfiltered-lore").get(l)));
						}
						itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
						itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
					}
					if(!(item.getType().equals(Material.AIR))) {
						itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
						itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
						itemMeta.setLore(lore);
						item.setItemMeta(itemMeta);
					}
					player.getOpenInventory().setItem(i, item);
					lore.clear();
				}
			}
			//
		}
		return;
	}
}