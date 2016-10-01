package me.randomHashTags.RandomPackage.Events;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;

public class SuccessAndDestroySystem implements Listener {
	private Random random = new Random();
	private static ItemStack item = new ItemStack(Material.ACACIA_STAIRS);
	private static ItemMeta itemMeta = item.getItemMeta();
	private static ArrayList<String> lore = new ArrayList<String>(), otherLore = new ArrayList<String>();
	public static ArrayList<Material> enchantableItems = new ArrayList<Material>();
	@EventHandler
	private void enchantableItemsAdd(PluginEnableEvent event) {
		enchantableItems.add(Material.DIAMOND_HELMET); enchantableItems.add(Material.DIAMOND_CHESTPLATE); enchantableItems.add(Material.DIAMOND_LEGGINGS); enchantableItems.add(Material.DIAMOND_BOOTS);
		enchantableItems.add(Material.IRON_HELMET); enchantableItems.add(Material.IRON_CHESTPLATE); enchantableItems.add(Material.IRON_LEGGINGS); enchantableItems.add(Material.IRON_BOOTS);
		enchantableItems.add(Material.GOLD_HELMET); enchantableItems.add(Material.GOLD_CHESTPLATE); enchantableItems.add(Material.GOLD_LEGGINGS); enchantableItems.add(Material.GOLD_BOOTS);
		enchantableItems.add(Material.LEATHER_HELMET); enchantableItems.add(Material.LEATHER_CHESTPLATE); enchantableItems.add(Material.LEATHER_LEGGINGS); enchantableItems.add(Material.LEATHER_BOOTS);
		//
		enchantableItems.add(Material.DIAMOND_SWORD); enchantableItems.add(Material.IRON_SWORD); enchantableItems.add(Material.GOLD_SWORD); enchantableItems.add(Material.STONE_SWORD); enchantableItems.add(Material.WOOD_SWORD);
		enchantableItems.add(Material.DIAMOND_AXE); enchantableItems.add(Material.IRON_AXE); enchantableItems.add(Material.GOLD_AXE); enchantableItems.add(Material.STONE_AXE); enchantableItems.add(Material.WOOD_AXE);
		enchantableItems.add(Material.DIAMOND_PICKAXE); enchantableItems.add(Material.IRON_PICKAXE); enchantableItems.add(Material.GOLD_PICKAXE); enchantableItems.add(Material.STONE_PICKAXE); enchantableItems.add(Material.WOOD_PICKAXE);
		enchantableItems.add(Material.DIAMOND_SPADE); enchantableItems.add(Material.IRON_SPADE); enchantableItems.add(Material.GOLD_SPADE); enchantableItems.add(Material.STONE_SPADE); enchantableItems.add(Material.WOOD_SPADE);
		enchantableItems.add(Material.DIAMOND_HOE); enchantableItems.add(Material.IRON_HOE); enchantableItems.add(Material.GOLD_HOE); enchantableItems.add(Material.STONE_HOE); enchantableItems.add(Material.WOOD_HOE);
		enchantableItems.add(Material.BOW);
		//
	}
	@EventHandler
	private void bookApply(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR) || event.getCursor() == null || event.getCursor().getType().equals(Material.AIR)
				|| !(event.getCursor().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase())))
				|| !(event.getCursor().hasItemMeta()) || !(event.getCursor().getItemMeta().hasLore()) || !(event.getCursor().getItemMeta().hasDisplayName())
				|| event.getCursor().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase())) && event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()))
				|| event.getCurrentItem().getType().name().endsWith("HELMET") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Armor")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Helmet"))))
				|| event.getCurrentItem().getType().name().endsWith("CHESTPLATE") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Armor")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Chestplate"))))
				|| event.getCurrentItem().getType().name().endsWith("LEGGINGS") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Armor")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Leggings"))))
				|| event.getCurrentItem().getType().name().endsWith("BOOTS") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Armor")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Boots"))))
				|| event.getCurrentItem().getType().name().endsWith("SWORD") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Weapon")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Sword"))))
				|| event.getCurrentItem().getType().name().endsWith("PICKAXE") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Pickaxe")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Tool"))))
				|| event.getCurrentItem().getType().name().endsWith("AXE") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Weapon")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Axe")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Tool"))))
				|| event.getCurrentItem().getType().name().endsWith("SPADE") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Shovel")))) && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Tool"))))
				|| event.getCurrentItem().getType().name().endsWith("BOW") && !(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Bow"))))
				|| !(enchantableItems.contains(event.getCurrentItem().getType())) || event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getItemMeta().getLore().size() == 9 && !(event.getCurrentItem().getItemMeta().getLore().get(8).contains(Data.whitescroll))) {
			return;
		} else {
			String itemName = event.getCursor().getItemMeta().getDisplayName().replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "");
			String type = null;
			//
			if(Data.getSoulBookNames.contains(event.getCursor().getItemMeta().getDisplayName())) { type = "Soul";
			} else if(Data.getLegendaryBookNames.contains(event.getCursor().getItemMeta().getDisplayName())) { type = "Legendary";
			} else if(Data.getUltimateBookNames.contains(event.getCursor().getItemMeta().getDisplayName())) { type = "Ultimate";
			} else if(Data.getEliteBookNames.contains(event.getCursor().getItemMeta().getDisplayName())) { type = "Elite";
			} else if(Data.getUniqueBookNames.contains(event.getCursor().getItemMeta().getDisplayName())) { type = "Unique";
			} else if(Data.getSimpleBookNames.contains(event.getCursor().getItemMeta().getDisplayName())) { type = "Simple";
			} else { return; }
			//
			if(!(RandomPackage.getEnabledEnchantsConfig().get(type + "." + ChatColor.stripColor(itemName)) == null)) {
				item = event.getCurrentItem();
				int success = 0, destroy = 0;
				//
				for(int i = 0; i <= 100; i++) {
					if(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString("success-lore").replace("%success%", "" + i)))) {
						success = i;
					}
					if(event.getCursor().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString("destroy-lore").replace("%destroy%", "" + i)))) {
						destroy = i;
					}
				}
				// Success
				if(random.nextInt(100) <= success) {
					String itemLore = ChatColor.stripColor(event.getCursor().getItemMeta().getDisplayName().replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", ""));
					itemName = ChatColor.stripColor(itemName);
					if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
						for(int i = 1; i <= 10; i++) {
							if(!(RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemName + i + "-itemLore") == null) && item.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemName + i + "-itemLore")))) {
								lore.clear();
								lore.addAll(event.getCurrentItem().getItemMeta().getLore());
								if(i >= Integer.parseInt(itemLore.toLowerCase().replace("a", "").replace("b", "").replace("c", "").replace("d", "").replace("e", "").replace("f", "").replace("g", "").replace("h", "").replace("i", "").replace("j", "").replace("k", "").replace("l", "").replace("m", "").replace("n", "").replace("o", "").replace("p", "").replace("q", "").replace("r", "").replace("s", "").replace("t", "").replace("u", "").replace("v", "").replace("w", "").replace("x", "").replace("y", "").replace("z", ""))) {
									// Leveled up by 1
									if(!(RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemName + Math.addExact(i, 1) + "-itemLore") == null)) {
										for(int o = 0; o < lore.size(); o++) {
											if(event.getCurrentItem().getItemMeta().getLore().get(o).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemName + i + "-itemLore")))) {
												lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemName + Math.addExact(i, 1) + "-itemLore")));
											}
										}
									} else {
										return;
									}
								} else {
									// Leveled up to the book's level
									for(int o = 0; o < lore.size(); o++) {
										if(event.getCurrentItem().getItemMeta().getLore().get(o).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemName + i + "-itemLore")))) {
											lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemLore + "-itemLore")));
										}
									}
								}
								for(int o = 1; o <= 10; o++) { ((Player) event.getWhoClicked()).getWorld().playEffect(event.getWhoClicked().getEyeLocation(), Effect.SPELL, 1); }
								event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
								finish(event);
								return;
							}
						}
					}
					if(item.hasItemMeta() && item.getItemMeta().hasLore()) { lore.addAll(item.getItemMeta().getLore()); }
					for(int o = 1; o <= 10; o++) { ((Player) event.getWhoClicked()).getWorld().playEffect(event.getWhoClicked().getEyeLocation(), Effect.SPELL, 1); }
					event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
					//
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + itemName + "." + itemLore + "-itemLore")));
					if(lore.contains(Data.whitescroll)) { lore.remove(Data.whitescroll); lore.add(Data.whitescroll); }
					String soulTrackerType = null;
					if(!(lore.isEmpty())) {
						for(int i = 1; i <= 5; i++) {
							if(i == 1) { soulTrackerType = "legendary";
							} else if(i == 2) { soulTrackerType = "ultimate";
							} else if(i == 3) { soulTrackerType = "elite";
							} else if(i == 4) { soulTrackerType = "unique";
							} else if(i == 5) { soulTrackerType = "simple"; }
							for(int o = 0; o < lore.size(); o++) {
								if(lore.get(o).startsWith(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + soulTrackerType + ".tracked-lore").replace("%souls%", "")))) {
									soulTrackerType = lore.get(o);
									lore.remove(o);
									lore.add(soulTrackerType);
								}
							}
						}
					}
					finish(event);
					return;
					//
				// Failed
				} else if(random.nextInt(100) <= destroy) {
					destroy(event);
					finish(event);
					return;
				} else {
					for(int i = 1; i <= 10; i++) { event.getWhoClicked().getWorld().playEffect(event.getWhoClicked().getEyeLocation(), Effect.LAVA_POP, 1); }
					event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
					if(item.hasItemMeta() && item.getItemMeta().hasLore()) { lore.addAll(item.getItemMeta().getLore()); }
					finish(event);
					return;
				}
				//
			} else { return; }
		}
	}
	@SuppressWarnings("deprecation")
	public static void finish(InventoryClickEvent event) {
		for(int i = 1; i <= 15; i++) { if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains(Data.transmog.replace("%loreCount%", "" + i))) { itemMeta.setDisplayName(item.getItemMeta().getDisplayName().replace(Data.transmog.replace("%loreCount%", "" + i), "" + Math.addExact(i, 1))); } }
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		event.setCurrentItem(item);
		if(event.getCursor().getAmount() > 1) { event.getCursor().setAmount(event.getCursor().getAmount() - 1);
		} else { event.setCursor(new ItemStack(Material.AIR)); }
		event.setCancelled(true);
		((Player) event.getWhoClicked()).updateInventory();
		lore.clear();
		return;
	}
	public void destroy(InventoryClickEvent event) {
		for(int i = 1; i <= 10; i++) { event.getWhoClicked().getWorld().playEffect(event.getWhoClicked().getEyeLocation(), Effect.LAVA_POP, 1); }
		event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		if(lore.contains(Data.whitescroll)) {  lore.remove(Data.whitescroll);
		} else { item = new ItemStack(Material.AIR); }
		finish(event);
		return;
	}
}