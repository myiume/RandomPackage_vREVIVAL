package me.randomHashTags.RandomPackage.Events.tinkerer;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.ryujix.withdraw.WithdrawAdvanced;
import dev.ryujix.withdraw.Commands.XpWithdraw;
import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;
import me.randomHashTags.RandomPackage.Events.SuccessAndDestroySystem;

public class tinkererEvents implements Listener {
	public static Inventory tinkererGui = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.chest-title")));
	private static ItemStack item = new ItemStack(Material.ACACIA_STAIRS);
	private static ItemMeta itemMeta = item.getItemMeta();
	private static ArrayList<String> lore = new ArrayList<String>();
	private Random random = new Random();
	
	public static void tinkerer(Player player) {
		tinkererGui = Bukkit.createInventory(player, RandomPackage.getTinkererEnchanterAlchemistConfig().getInt("Tinkerer.slots"), ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.chest-title")));
		//
		for(int i = 0; i < tinkererGui.getSize(); i++) {
			if(i == 0 || i == 8) {
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
				itemMeta.setDisplayName(" ");
			} else if(i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49) {
				item = new ItemStack(Material.THIN_GLASS, 1);
				itemMeta.setDisplayName(" ");
			} else { item = new ItemStack(Material.AIR); }
			
			if(!(item.getType().equals(Material.AIR))) {
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				lore.clear();
			}
			tinkererGui.setItem(i, item);
		}
		player.openInventory(tinkererGui);
		//
		return;
	}
	@EventHandler
	private void tinkererItem(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR) || event.isCancelled() || !(event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase(tinkererGui.getTitle()))) {
			return;
		} else {
			Player player = (Player) event.getWhoClicked();
			if(event.getRawSlot() > tinkererGui.getSize()) {
				event.setCancelled(true);
				//
				String bookRarity = null;
				if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase())) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
					if(Data.getLegendaryBookNames.contains(event.getCurrentItem().getItemMeta().getDisplayName())) { bookRarity = "legendary";
					} else if(Data.getUltimateBookNames.contains(event.getCurrentItem().getItemMeta().getDisplayName())) { bookRarity = "ultimate";
					} else if(Data.getEliteBookNames.contains(event.getCurrentItem().getItemMeta().getDisplayName())) { bookRarity = "elite";
					} else if(Data.getUniqueBookNames.contains(event.getCurrentItem().getItemMeta().getDisplayName())) { bookRarity = "unique";
					} else if(Data.getSimpleBookNames.contains(event.getCurrentItem().getItemMeta().getDisplayName())) { bookRarity = "simple";
					} else { return; }
					item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.fireballs.item").toUpperCase()), 1);
					itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.fireballs." + bookRarity)));
					for(int i = 0; i < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.fireballs.lore").size(); i++) {
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.fireballs.lore").get(i)));
					}
				} else if(SuccessAndDestroySystem.enchantableItems.contains(event.getCurrentItem().getType())) {
					int xp = 0;
					if(event.getCurrentItem().hasItemMeta()) {
						for(int i = 0; i < 5; i++) {
							if(event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && event.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == i) { xp = xp + RandomPackage.getTinkererXPConfig().getInt("prot" + i); }
							if(event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.PROTECTION_EXPLOSIONS) && event.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS) == i) { xp = xp + RandomPackage.getTinkererXPConfig().getInt("blastprot" + i); }
							if(event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.PROTECTION_FALL) && event.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_FALL) == i) { xp = xp + RandomPackage.getTinkererXPConfig().getInt("featherfalling" + i); }
							if(event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.PROTECTION_FIRE) && event.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_FIRE) == i) { xp = xp + RandomPackage.getTinkererXPConfig().getInt("fireprot" + i); }
							if(event.getCurrentItem().getItemMeta().hasEnchant(Enchantment.PROTECTION_PROJECTILE) && event.getCurrentItem().getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE) == i) { xp = xp + RandomPackage.getTinkererXPConfig().getInt("projprot" + i); }
						}
						if(event.getCurrentItem().getItemMeta().hasLore()) {
							for(int i = 0; i < 300; i++) {
								if(i < Data.getSoulItemLores.size() && event.getCurrentItem().getItemMeta().getLore().contains(Data.getSoulItemLores.get(i))) {
									xp = xp + RandomPackage.getTinkererXPConfig().getInt(ChatColor.stripColor(Data.getSoulItemLores.get(i).replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", "")));
								} else  if(i < Data.getLegendaryItemLores.size() && event.getCurrentItem().getItemMeta().getLore().contains(Data.getLegendaryItemLores.get(i))) {
									xp = xp + RandomPackage.getTinkererXPConfig().getInt(ChatColor.stripColor(Data.getLegendaryItemLores.get(i).replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", "")));
								} else if(i < Data.getUltimateItemLores.size() && event.getCurrentItem().getItemMeta().getLore().contains(Data.getUltimateItemLores.get(i))) {
									xp = xp + RandomPackage.getTinkererXPConfig().getInt(ChatColor.stripColor(Data.getUltimateItemLores.get(i).replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", "")));
								} else if(i < Data.getEliteItemLores.size() && event.getCurrentItem().getItemMeta().getLore().contains(Data.getEliteItemLores.get(i))) {
									xp = xp + RandomPackage.getTinkererXPConfig().getInt(ChatColor.stripColor(Data.getEliteItemLores.get(i).replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", "")));
								} else if(i < Data.getUniqueItemLores.size() && event.getCurrentItem().getItemMeta().getLore().contains(Data.getUniqueBookNames.get(i))) {
									xp = xp + RandomPackage.getTinkererXPConfig().getInt(ChatColor.stripColor(Data.getUniqueItemLores.get(i).replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", "")));
								} else if(i < Data.getSimpleItemLores.size() && event.getCurrentItem().getItemMeta().getLore().contains(Data.getSimpleItemLores.get(i))) {
									xp = xp + RandomPackage.getTinkererXPConfig().getInt(ChatColor.stripColor(Data.getSimpleItemLores.get(i).replace(" X", "10").replace(" IX", "9").replace(" VIII", "8").replace(" VII", "7").replace(" VI", "6").replace(" V", "5").replace(" IV", "4").replace(" III", "3").replace(" II", "2").replace(" I", "1").replace(" ", "")));
								}
							}
							item = new ItemStack(Material.EXP_BOTTLE, 1);
							if(!(Bukkit.getPluginManager().getPlugin("WithdrawAdvanced") == null) && Bukkit.getPluginManager().getPlugin("WithdrawAdvanced").isEnabled()) {
								itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', WithdrawAdvanced.plugin.getConfig().getString("experience-bottle.name")));
								for(int i = 0; i < WithdrawAdvanced.plugin.getConfig().getStringList("experience-bottle.lore").size(); i++) {
									lore.add(ChatColor.translateAlternateColorCodes('&', WithdrawAdvanced.plugin.getConfig().getStringList("experience-bottle.lore").get(i).replace("%amount%", "" + xp).replace("%player%", "Tinkerer")));
								}
							} else if(!(Bukkit.getPluginManager().getPlugin("Beast-XpWithDraw") == null)) {
								itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Bukkit.getPluginManager().getPlugin("Beast-XpWithDraw").getConfig().getString("BottleName")));
								lore.add(ChatColor.translateAlternateColorCodes('&', Bukkit.getPluginManager().getPlugin("Beast-XpWithDraw").getConfig().getString("Value").replace("%value%", "" + xp)));
								lore.add(ChatColor.translateAlternateColorCodes('&', Bukkit.getPluginManager().getPlugin("Beast-XpWithDraw").getConfig().getString("BottleEnchanter").replace("%player%", "Tinkerer")));
							} else {
								itemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Exp Bottle");
								lore.add(ChatColor.DARK_PURPLE + "Download " + ChatColor.YELLOW + "WithdrawAdvanced" + ChatColor.DARK_PURPLE + " or " + ChatColor.YELLOW + "Beast-XpWithdraw");
								lore.add(ChatColor.DARK_PURPLE + "to use this feature.");
							}
							itemMeta.setLore(lore);
							item.setItemMeta(itemMeta);
						}
					}
				}
				//
				itemMeta.setLore(lore);
				item.setItemMeta(itemMeta);
				lore.clear();
				if(player.getOpenInventory().getTopInventory().firstEmpty() <= 3) { player.getOpenInventory().getTopInventory().setItem(player.getOpenInventory().getTopInventory().firstEmpty() + 4, item);
				} else { player.getOpenInventory().getTopInventory().setItem(player.getOpenInventory().getTopInventory().firstEmpty() + 5, item); }
				player.getOpenInventory().getTopInventory().addItem(event.getCurrentItem());
				event.setCurrentItem(new ItemStack(Material.AIR));
				player.updateInventory();
				return;
				//
			} else if(event.getRawSlot() == 0) {
				event.setCancelled(true);
				player.getOpenInventory().setItem(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13));
				player.closeInventory();
			} else {
				event.setCancelled(true);
				if(event.getCurrentItem().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase())) || SuccessAndDestroySystem.enchantableItems.contains(event.getCurrentItem().getType())) {
					player.getInventory().addItem(event.getCurrentItem());
					event.setCurrentItem(new ItemStack(Material.AIR));
					//
					if(event.getRawSlot() <= 3) { player.getOpenInventory().getTopInventory().setItem(event.getRawSlot() + 4, new ItemStack(Material.AIR));
					} else { player.getOpenInventory().getTopInventory().setItem(event.getRawSlot() + 5, new ItemStack(Material.AIR)); }
					//
				}
			}
			player.updateInventory();
			return;
		}
	}
	@EventHandler
	private void tinkererClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		if(event.getInventory().getTitle().equalsIgnoreCase(tinkererGui.getTitle())) {
			if(!(event.getInventory().getItem(0) == null) && event.getInventory().getItem(0).equals(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Tinkerer.trade-accept").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
				for(int i = 4; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
					if(i < player.getOpenInventory().getTopInventory().getSize() && i >= 5 && i <= 7 || i < player.getOpenInventory().getTopInventory().getSize() && i >= 14 && i <= 17
							|| i < player.getOpenInventory().getTopInventory().getSize() && i >= 23 && i <= 26 || i < player.getOpenInventory().getTopInventory().getSize() && i >= 31 && i <= 35
							|| i < player.getOpenInventory().getTopInventory().getSize() && i >= 40 && i <= 44 || i < player.getOpenInventory().getTopInventory().getSize() && i >= 49 && i <= 53) {
						if(!(player.getOpenInventory().getItem(i) == null) && !(player.getOpenInventory().getItem(i).getType().equals(Material.AIR))
								&& !(player.getOpenInventory().getItem(i).getType().equals(Material.THIN_GLASS))) {
							if(!(player.getInventory().firstEmpty() < 0)) {
								player.getInventory().addItem(player.getOpenInventory().getItem(i));
								player.updateInventory();
							} else {
								player.getWorld().dropItem(player.getLocation(), player.getOpenInventory().getItem(i));
							}
						}
					}
				}
				return;
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Tinkerer.trade-cancelled").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
				for(int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
					if(!(player.getOpenInventory().getTopInventory().getItem(i) == null) && !(player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.AIR))) {
						if(player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("given-item").toUpperCase()))
								|| player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_HELMET) || player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_CHESTPLATE)
								|| player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_LEGGINGS) || player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_BOOTS)
								|| player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_SWORD) || player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_PICKAXE)
								|| player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_AXE) || player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.DIAMOND_SPADE)
								|| player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.BOW)) {
							if(!(player.getInventory().firstEmpty() < 0)) {
								player.getInventory().addItem(player.getOpenInventory().getItem(i));
								player.updateInventory();
							} else {
								player.getWorld().dropItem(player.getLocation(), player.getOpenInventory().getItem(i));
							}
						}
					}
				}
				return;
			}
		} else { return; }
	}
	@EventHandler
	private void dustRevealEvent(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		if(player.getInventory().getItemInMainHand() == null || !(player.getInventory().getItemInMainHand().getType().equals(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.fireballs.item").toUpperCase())))
				|| !(player.getInventory().getItemInMainHand().hasItemMeta()) || !(player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) || !(player.getInventory().getItemInMainHand().getItemMeta().hasLore())) {
			return;
		} else {
			event.setCancelled(true);
			String type = null;
			int randomNumber = random.nextInt(100);
			for(int i = 1; i <= 5; i++) {
				if(i == 1) { type = "legendary";
				} else if(i == 2) { type = "ultimate";
				} else if(i == 3) { type = "elite";
				} else if(i == 4) { type = "unique";
				} else if(i == 5) { type = "simple"; }
				
				if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.fireballs." + type)))) {
					if(randomNumber <= 5) {
						randomNumber = random.nextInt(31);
						if(randomNumber < 10) { randomNumber = 10; }
						item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.primal.item").toUpperCase()), 1);
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.primal." + type)));
						for(int o = 0; o < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.primal.lore").size(); o++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.primal.lore").get(o).replace("%percent%", "" + randomNumber).replace("%color%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust." + type + "-color"))).replace("%book%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust." + type + "-book")))));
						}
					} else if(randomNumber <= 10) {
						randomNumber = random.nextInt(10);
						if(randomNumber == 0) { randomNumber = 1; }
						item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust.item").toUpperCase()), 1);
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust." + type)));
						for(int o = 0; o < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.dust.lore").size(); o++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.dust.lore").get(o).replace("%percent%", "" + randomNumber).replace("%color%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.dust." + type + "-color"))).replace("%book%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.primal." + type + "-book")))));
						}
						
					} else {
						item = new ItemStack(Material.getMaterial(RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.mystery.item").toUpperCase()), 1);
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.mystery.name")));
						for(int o = 0; o < RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.mystery.lore").size(); o++) {
							lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getStringList("Tinkerer.mystery.lore").get(o)));
						}
						
					}
					if(!(itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getTinkererEnchanterAlchemistConfig().getString("Tinkerer.mystery.name"))))) {
						for(int o = 1; o <= 10; o++) { player.getWorld().playEffect(player.getEyeLocation(), Effect.SPELL, 1); }
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
					} else {
						for(int o = 1; o <= 10; o++) { player.getWorld().playEffect(player.getEyeLocation(), Effect.LAVA_POP, 1); }
						player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
					}
					//
					if(player.getInventory().getItemInMainHand().getAmount() == 1) { player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
					} else { player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1); }
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					if(player.getInventory().firstEmpty() < 0) { player.getWorld().dropItem(player.getLocation(), item);
					} else { player.getInventory().addItem(item); player.updateInventory(); }
					lore.clear();
					return;
				}
			}
			return;
		}
	}
}