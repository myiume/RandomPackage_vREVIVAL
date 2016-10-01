package me.randomHashTags.RandomPackage.Events.soul;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;
import me.randomHashTags.RandomPackage.Events.SuccessAndDestroySystem;

public class soulEvents implements Listener {
	public static ArrayList<Player> soulMode = new ArrayList<Player>();
	private ArrayList<Player> spam = new ArrayList<Player>();
	private static HashMap<Player, Integer> soulGemSoulAmount = new HashMap<Player, Integer>();
	private static ItemStack soulModeItem = new ItemStack(Material.EMERALD, 1, (byte) 0), item = new ItemStack(Material.ACACIA_STAIRS, 1);
	private static ItemMeta itemMeta = item.getItemMeta();
	private static ArrayList<String> lore = new ArrayList<String>();
	private String type = null, typ3;
	@EventHandler
	private void soulTrackerApply(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR) || event.getCursor() == null || event.getCursor().getType().equals(Material.AIR) || !(event.getCursor().getType().equals(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase())))
				|| !(event.getCursor().hasItemMeta()) || !(event.getCursor().getItemMeta().hasDisplayName()) || !(event.getCursor().getItemMeta().hasLore())
				|| event.getCurrentItem().getType().name().endsWith("SWORD") || event.getCurrentItem().getType().name().endsWith("AXE") || event.getCurrentItem().getType().name().endsWith("HELMET") || event.getCurrentItem().getType().name().endsWith("CHESTPLATE") || event.getCurrentItem().getType().name().endsWith("LEGGINGS")
				|| event.getCurrentItem().getType().name().endsWith("BOOTS") || event.getCurrentItem().getType().name().endsWith("HOE") || event.getCurrentItem().getType().name().endsWith("SPADE")
				|| !(SuccessAndDestroySystem.enchantableItems.contains(event.getCurrentItem().getType()))) {
			return;
		} else {
			for(int i = 1; i <= 5; i++) {
				if(i == 1) { type = "legendary";
				} else if(i == 2) { type = "ultimate";
				} else if(i == 3) { type = "elite";
				} else if(i == 4) { type = "unique";
				} else if(i == 5) { type = "simple"; }
				if(event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".name")))) {
					item  = event.getCurrentItem();
					if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
						lore.addAll(item.getItemMeta().getLore());
						for(int j = 1; j <= 5; j++) {
							if(j == 1) { typ3 = "legendary";
							} else if(j == 2) { typ3 = "ultimate";
							} else if(j == 3) { typ3 = "elite";
							} else if(j == 4) { typ3 = "unique";
							} else if(j == 5) { typ3 = "simple"; }
							for(int o = 0; o < lore.size(); o++) {
								if(lore.get(o).startsWith(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + typ3 + ".tracked-lore").replace("%souls%", "")))) {
									if(lore.contains(Data.whitescroll)) { lore.remove(Data.whitescroll); lore.add(Data.whitescroll); }
									lore.set(o, ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + 0)));
									event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("SoulTrackers.apply-" + type)).replace("%item%", event.getCurrentItem().getType().name().replace("_", " ")));
									finish(event);
									return;
								}
							}
						}
					}
					if(lore.contains(Data.whitescroll)) { lore.remove(Data.whitescroll); lore.add(Data.whitescroll); }
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + 0)));
					event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("SoulTrackers.apply-" + type)).replace("%item%", event.getCurrentItem().getType().name().replace("_", " ")));
					finish(event);
					return;
				}
			}
			return;
		}
	}
	@EventHandler
	private void soulAdd(PlayerDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			Player killer = (Player) event.getEntity().getKiller();
			if(!(killer.getInventory().getItemInMainHand() == null) && !(killer.getInventory().getItemInMainHand().getType().equals(Material.AIR)) && killer.getInventory().getItemInMainHand().hasItemMeta() && killer.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				for(int i = 1; i <= 5; i++) {
					if(i == 1) { type = "legendary";
					} else if(i == 2) { type = "ultimate";
					} else if(i == 3) { type = "elite";
					} else if(i == 4) { type = "unique";
					} else if(i == 5) { type = "simple"; }
					for(int o = 0; o <= Data.maxSoulsHarvested; o++) {
						if(killer.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + o)))) {
							item = killer.getInventory().getItemInMainHand();
							if(!(item.getItemMeta().getDisplayName() == null)) { itemMeta.setDisplayName(item.getItemMeta().getDisplayName()); }
							lore.addAll(item.getItemMeta().getLore());
							lore.remove(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + o)));
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + Math.addExact(o, 1))));
							if(lore.contains(Data.whitescroll)) { lore.remove(Data.whitescroll); lore.add(Data.whitescroll); }
							itemMeta.setLore(lore);
							item.setItemMeta(itemMeta);
							lore.clear();
							killer.getInventory().setItemInMainHand(item);
							killer.updateInventory();
							return;
						}
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
	@EventHandler
	private void soulMode(PlayerInteractEvent event) {
		if(!(event.getPlayer().getInventory().getItemInMainHand() == null) && !(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) && !(event.getAction() == Action.LEFT_CLICK_AIR) && !(event.getAction() == Action.LEFT_CLICK_BLOCK) && !(event.getAction() == Action.PHYSICAL)
				&& event.getPlayer().getInventory().getItemInMainHand().hasItemMeta() && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName() && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
			int test = -1;
			for(int i = 0; i <= Data.maxSoulsHarvested; i++) {
				if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "" + i)))) {
					test = i;
				}
				if(test == -1 && i == Data.maxSoulsHarvested) { return; }
			}
			
			if(Material.getMaterial(RandomPackage.getSoulConfig().getString("SoulGem.item").toUpperCase()) == null) {
				event.getPlayer().sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Invalid material for Soul Gem. '" + ChatColor.RED + RandomPackage.getSoulConfig().getString("SoulGem.item") + ChatColor.YELLOW + "'");
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
				return;
			} else {
				if(!(soulMode.contains(event.getPlayer()))) {
					if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "0")))) {
						if(!(spam.contains(event.getPlayer()))) {
							spam.add(event.getPlayer());
							event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
							for(int i = 0; i < RandomPackage.getMessagesConfig().getStringList("SoulMode.need-souls").size(); i++) {
								event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getStringList("SoulMode.need-souls").get(i).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
							}
							Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
								public void run() {
									spam.remove(event.getPlayer());
								}
							}, 20);
							return;
						} else { return; }
					} else {
						soulMode.add(event.getPlayer());
						for(int i = 0; i < RandomPackage.getMessagesConfig().getStringList("SoulMode.activate").size(); i++) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getStringList("SoulMode.activate").get(i).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
						}
					}
				} else {
					soulMode.remove(event.getPlayer());
					for(int i = 0; i < RandomPackage.getMessagesConfig().getStringList("SoulMode.deactivate").size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getStringList("SoulMode.deactivate").get(i).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
					}
				}
				return;
			}
			
		} else { return; }
	}
	@EventHandler
	private void soulModeTimings(PluginEnableEvent event) {
		if(!(Material.getMaterial(RandomPackage.getSoulConfig().getString("SoulGem.item").toUpperCase()) == null)) {
			int data = 0;
			if(!(RandomPackage.getSoulConfig().get("SoulGem.item-data") == null)) { data = RandomPackage.getSoulConfig().getInt("SoulGem.item-data"); } else { data = 0; }
			soulModeItem = new ItemStack(Material.getMaterial(RandomPackage.getSoulConfig().getString("SoulGem.item").toUpperCase()), 1, (byte) data);
		}
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomPackage.getPlugin(), new Runnable() {
			public void run() {
				for(int i = 0; i < soulMode.size(); i++) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + Bukkit.getPlayer(soulMode.get(i).getName()).getName() + " ~ ~ ~ particle enchantmenttable " + Bukkit.getPlayer(soulMode.get(i).getName()).getLocation().getX() + " " + Bukkit.getPlayer(soulMode.get(i).getName()).getLocation().getY() + " " + Bukkit.getPlayer(soulMode.get(i).getName()).getLocation().getZ() + " .5 .5 .5 1 100 1");
				}
				return;
			}
		}, 0, 5);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomPackage.getPlugin(), new Runnable() {
			public void run() {
				for(int i = 0; i < soulMode.size(); i++) {
					
					for(int o = 0; o < Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getSize(); o++) {
						if(!(Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o) == null) && !(Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).getType().equals(Material.AIR))
								&& Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).hasItemMeta() && Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).getItemMeta().hasDisplayName()
								&& Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).getType().equals(soulModeItem.getType())
								&& Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).getData().equals(soulModeItem.getData())) {
							if(Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "0")))) {
								for(int p = 0; p < RandomPackage.getMessagesConfig().getStringList("SoulMode.out-of-souls").size(); p++) {
									Bukkit.getPlayer(soulMode.get(i).getName()).sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getStringList("SoulMode.out-of-souls").get(p).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
								}
								soulMode.remove(i);
								return;
							} else {
								for(int p = 1; p <= Data.maxSoulsHarvested; p++) {
									if(Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "" + p)))) {
										item = Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().getItem(o);
										itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "" + Math.subtractExact(p, 1))));
										if(item.getItemMeta().hasLore()) { lore.addAll(item.getItemMeta().getLore()); }
										itemMeta.setLore(lore);
										item.setItemMeta(itemMeta);
										lore.clear();
										soulGemSoulAmount.remove(Bukkit.getPlayer(soulMode.get(i).getName()));
										soulGemSoulAmount.put(Bukkit.getPlayer(soulMode.get(i).getName()), p);
										Bukkit.getPlayer(soulMode.get(i).getName()).getInventory().setItem(o, item);
										Bukkit.getPlayer(soulMode.get(i).getName()).updateInventory();
										if(Integer.toString(Math.subtractExact(p, 1)).endsWith("00") || Integer.toString(Math.subtractExact(p, 1)).endsWith("10") || Integer.toString(Math.subtractExact(p, 1)).endsWith("20")
												|| Integer.toString(Math.subtractExact(p, 1)).endsWith("40") || Integer.toString(Math.subtractExact(p, 1)).endsWith("60") || Integer.toString(Math.subtractExact(p, 1)).endsWith("80")) {
											for(int q = 0; q < RandomPackage.getMessagesConfig().getStringList("SoulMode.soul-drain-update").size(); q++) {
												Bukkit.getPlayer(soulMode.get(i).getName()).sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getStringList("SoulMode.soul-drain-update").get(q).replace("%souls%", "" + Math.subtractExact(p, 1)).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
											}
										}
										return;
									}
								}
								return;
							}
						}
					}
					
				}
				return;
			}
		}, 0, 20);
		return;
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
}
