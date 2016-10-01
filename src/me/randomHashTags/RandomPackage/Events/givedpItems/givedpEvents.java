package me.randomHashTags.RandomPackage.Events.givedpItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;
import me.randomHashTags.RandomPackage.Events.SuccessAndDestroySystem;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class givedpEvents implements Listener {
	public static ArrayList<Player> itemNameTag = new ArrayList<Player>();
	private ItemStack item  = new ItemStack(Material.ACACIA_STAIRS, 1);
	private ItemMeta itemMeta = item.getItemMeta();
	private ArrayList<String> lore = new ArrayList<String>();
	private Random random = new Random();
	@EventHandler
	private void playerInteractEvent(PlayerInteractEvent event) {
		if(!(event.getItem() == null) && !(event.getItem().getType().equals(Material.AIR)) && event.getItem().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("WhiteScrolls.item").toUpperCase()))
				&& event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName() && event.getItem().getItemMeta().hasLore()
				&& event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("WhiteScrolls.name")))) {
			event.setCancelled(true);
			event.getPlayer().updateInventory();
			return;
		} else if(!(event.getItem() == null) && !(event.getItem().getType().equals(Material.AIR))) {
			if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName() && event.getItem().getItemMeta().hasLore()) {
				if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("ItemNameTags.name"))) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("ItemNameTags.item").toUpperCase()))) {
					itemNameTag.add(event.getPlayer());
					if(event.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) { event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1); } else { event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR)); }
					event.getPlayer().updateInventory();
					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("ItemNameTags.enter-rename").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
					return;
				} else { return; }
			} else { return; }
		} else { return; }
	}
	@EventHandler
	private void whitescrollBlackscrollTransmogscroll(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR) || !(event.getCursor().hasItemMeta())
				|| !(event.getCursor().getItemMeta().hasDisplayName()) || !(event.getCursor().getItemMeta().hasLore())) {
			return;
		} else {
			if(event.getCursor().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("WhiteScrolls.item").toUpperCase())) && event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("WhiteScrolls.name")))) {
				if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getItemMeta().getLore().contains(Data.whitescroll)
						|| !(SuccessAndDestroySystem.enchantableItems.contains(event.getCurrentItem().getType()))) { return;
				} else {
					item = event.getCurrentItem();
					if(item.hasItemMeta() && item.getItemMeta().hasLore()) { lore.addAll(item.getItemMeta().getLore()); }
					lore.add(Data.whitescroll);
					String soulTrackerType = null;
					for(int o = 0; o < lore.size(); o++) {
						for(int i = 1; i <= 5; i++) {
							if(i == 1) { soulTrackerType = "legendary";
							} else if(i == 2) { soulTrackerType = "ultimate";
							} else if(i == 3) { soulTrackerType = "elite";
							} else if(i == 4) { soulTrackerType = "unique";
							} else if(i == 5) { soulTrackerType = "simple"; }
							//
							if(lore.get(o).startsWith(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + soulTrackerType + ".tracked-lore").replace("%souls%", "")))) {
								soulTrackerType = lore.get(o);
								lore.remove(o);
								lore.add(soulTrackerType);
							}
						}
					}
					finish(event);
					return;
				}
			} else if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && SuccessAndDestroySystem.enchantableItems.contains(event.getCurrentItem().getType())
					&& event.getCursor().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("BlackScrolls.item").toUpperCase())) && event.getCursor().getItemMeta().hasDisplayName() && event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("BlackScrolls.name")))) {
				return;
				/*lore.clear();
				String enchant = null, type = null;
				ItemStack currentItem = event.getCurrentItem();
				itemMeta = item.getItemMeta();
				List<String> currentItemLore = event.getCurrentItem().getItemMeta().getLore();
				int randomSlot = random.nextInt(currentItemLore.size());
				for(int i = 50; i <= 100; i++) {
					if(event.getCursor().getItemMeta().getLore().get(2).contains(org.bukkit.ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList("BlackScrolls.lore").get(2)).replace("%percent%", "" + i))) {
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("success").replace("%success%", "" + i)));
					}
				}
				lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("destroy").replace("%destroy%", "100")));
				for(int i = 0; i <= 300; i++) {
					if(i < Data.getSoulItemLores.size() && Data.getSoulItemLores.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getLore().get(randomSlot))) {
						enchant = Data.getSoulItemLores.get(i); type = "Soul";
					} else if(i < Data.getLegendaryItemLores.size() && Data.getLegendaryItemLores.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getLore().get(randomSlot))) {
						enchant = Data.getLegendaryItemLores.get(i); type = "Legendary";
					} else if(i < Data.getUltimateItemLores.size() && Data.getUltimateItemLores.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getLore().get(randomSlot))) {
						enchant = Data.getUltimateItemLores.get(i); type = "Ultimate";
					} else if(i < Data.getEliteItemLores.size() && Data.getEliteItemLores.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getLore().get(randomSlot))) {
						enchant = Data.getEliteItemLores.get(i); type = "Elite";
					} else if(i < Data.getUniqueItemLores.size() && Data.getUniqueItemLores.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getLore().get(randomSlot))) {
						enchant = Data.getUniqueItemLores.get(i); type = "Unique";
					} else if(i < Data.getSimpleItemLores.size() && Data.getSimpleItemLores.get(i).equalsIgnoreCase(event.getCurrentItem().getItemMeta().getLore().get(randomSlot))) {
						enchant = Data.getSimpleItemLores.get(i); type = "Simple";
					} else if(i == 300 && enchant == null) {
						event.getWhoClicked().sendMessage("          " + event.getCurrentItem().getItemMeta().getLore().get(randomSlot));
						event.getWhoClicked().sendMessage(RandomPackage.prefix + ChatColor.GRAY + "This enchant is currently disabled");
						return;
					}
				}
				for(int i = 0; i < RandomPackage.getPlugin().getConfig().getStringList(type + "." + ChatColor.stripColor(enchant).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "") + ".BookLore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getStringList(type + "." + ChatColor.stripColor(enchant).replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", "") + ".BookLore").get(i)));
				}
				return;*/
			} else if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && SuccessAndDestroySystem.enchantableItems.contains(event.getCurrentItem().getType())
					&& event.getCursor().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("TransmogScrolls.item").toUpperCase())) && event.getCursor().getItemMeta().hasDisplayName() && event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("TransmogScrolls.name")))) {
				int lore = 0;
				for(int i = 0; i < event.getCurrentItem().getItemMeta().getLore().size(); i++) {
					if(Data.getLegendaryItemLores.contains(event.getCurrentItem().getItemMeta().getLore().get(i)) || Data.getUltimateItemLores.contains(event.getCurrentItem().getItemMeta().getLore().get(i)) || Data.getEliteItemLores.contains(event.getCurrentItem().getItemMeta().getLore().get(i))
							|| Data.getUniqueItemLores.contains(event.getCurrentItem().getItemMeta().getLore().get(i)) || Data.getSimpleItemLores.contains(event.getCurrentItem().getItemMeta().getLore().get(i))) { lore = lore + 1;
					}
				}
				String dname = null;
				if(event.getCurrentItem().getItemMeta().hasDisplayName()) { dname = event.getCurrentItem().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("TransmogScrolls.apply-count").replace("%loreCount%", "" + lore).replace("   ", "").replace("  ", "")), ""); } else { dname = event.getCurrentItem().getType().name().replace("DIAMOND", "Diamond").replace("GOLD", "Gold").replace("IRON", "Iron").replace("PICKAXE", "Pickaxe").replace("AXE", "Axe").replace("SPADE", "Shovel").replace("BOW", "Bow").replace("SWORD", "Sword").replace("HELMET", "Helmet").replace("CHESTPLATE", "Chestplate").replace("LEGGINGS", "Leggings").replace("BOOTS", "Boots").replace("_", " "); }
				item = event.getCurrentItem();
				dname = dname + " ";
				itemMeta.setDisplayName(dname + ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("TransmogScrolls.apply-count").replace("%loreCount%", "" + lore)));
				this.lore.addAll(event.getCurrentItem().getItemMeta().getLore());
				event.getWhoClicked().sendMessage("" + lore);
				finish(event);
				return;
			} else { return; }
		}
	}
	private void finish(InventoryClickEvent event) {
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		event.setCurrentItem(item);
		event.setCursor(new ItemStack(Material.AIR));
		event.setCancelled(true);
		((Player) event.getWhoClicked()).updateInventory();
		for(int i = 1; i <= 15; i++) { event.getWhoClicked().getWorld().playEffect(event.getWhoClicked().getEyeLocation(), Effect.SPELL, 1); }
		event.getWhoClicked().getWorld().playSound(event.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		lore.clear();
		return;
	}
	@EventHandler
	private void itemRenameEvent(PlayerChatEvent event) {
		if(itemNameTag.contains(event.getPlayer())) {
			event.setCancelled(true);
			itemNameTag.remove(event.getPlayer());
			if(event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("ItemNameTags.cannot-rename-air").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
				itemNameTagCancel(event);
				return;
			} else {
				for(int i = 0; i < RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.blacklisted-items").size(); i++) {
					if(!(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.blacklisted-items").get(i)) == null) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.blacklisted-items").get(i).toUpperCase()))) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("ItemNameTags.cannot-rename-item").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
						itemNameTagCancel(event);
						return;
					}
				}
				//
				for(int i = 0; i < RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.blacklisted-names").size(); i++) {
					if(ChatColor.translateAlternateColorCodes('&', event.getMessage()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.blacklisted-names").get(i)))) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("ItemNameTags.cannot-rename-name").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
						itemNameTagCancel(event);
						return;
					}
				}
				//
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 2);
				item = event.getPlayer().getInventory().getItemInMainHand();
				itemMeta = item.getItemMeta();
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
				item.setItemMeta(itemMeta);
				event.getPlayer().getInventory().setItemInMainHand(item);
				event.getPlayer().updateInventory();
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("ItemNameTags.rename-item").replace("%rename%", ChatColor.translateAlternateColorCodes('&', event.getMessage())).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
				return;
			}
		} else { return; }
	}
	private void itemNameTagCancel(PlayerChatEvent event) {
		event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
		int data = 0;
		if(!(RandomPackage.getGivedpItemsConfig().get("ItemNameTags.item-data") == null)) { data = RandomPackage.getGivedpItemsConfig().getInt("ItemNameTags.item-data"); } else { data = 0; }
		item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("ItemNameTags.item").toUpperCase()), 1, (byte) data);
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("ItemNameTags.name")));
		for(int i = 0; i < RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.lore").size(); i++) {
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList("ItemNameTags.lore").get(i)));
		}
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		if(event.getPlayer().getInventory().firstEmpty() < 0) { event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item); } else { event.getPlayer().getInventory().addItem(item); }
		event.getPlayer().updateInventory();
		lore.clear();
		return;
	}
}
