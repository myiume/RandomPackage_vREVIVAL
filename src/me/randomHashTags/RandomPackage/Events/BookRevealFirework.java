package me.randomHashTags.RandomPackage.Events;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;

public class BookRevealFirework implements Listener {
	private Random random = new Random();
	private ItemStack item = new ItemStack(Material.ACACIA_STAIRS);
	private ItemMeta itemMeta = item.getItemMeta();
	private ArrayList<String> lore = new ArrayList<String>(), enchantRarity = new ArrayList<String>();
	@EventHandler
	private void bookRevealEvent(PlayerInteractEvent event) {
		if(event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)
				|| !(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase())))
				|| !(event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) || !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
				|| !(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore())) {
			return;
		} else {
			String type = null;
			for(int i = 1; i <= 6; i++) {
				if(i == 1) { type = "Soul"; enchantRarity = Data.getSoulBookNames;
				} else if(i == 2) { type = "Legendary"; enchantRarity = Data.getLegendaryBookNames;
				} else if(i == 3) { type = "Ultimate"; enchantRarity = Data.getUltimateBookNames;
				} else if(i == 4) { type = "Elite"; enchantRarity = Data.getEliteBookNames;
				} else if(i == 5) { type = "Unique"; enchantRarity = Data.getUniqueBookNames;
				} else if(i == 6) { type = "Simple"; enchantRarity = Data.getSimpleBookNames; }
				//
				if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString(type + ".name")))) {
					Firework fw = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
					FireworkMeta fwm = fw.getFireworkMeta();
					if(type.equalsIgnoreCase("Soul")) { fwm.setPower(2); } else { fwm.setPower(1); }
					if(type.equalsIgnoreCase("Legendary")) { fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).flicker(true).trail(true).withColor(Color.ORANGE).withFade(Color.ORANGE).build());
					} else if(type.equalsIgnoreCase("Ultimate")) { fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).flicker(true).trail(true).withColor(Color.YELLOW).withFade(Color.YELLOW).build());
					} else if(type.equalsIgnoreCase("Elite")) { fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).flicker(true).trail(true).withColor(Color.AQUA).withFade(Color.AQUA).build());
					} else if(type.equalsIgnoreCase("Unique")) { fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).flicker(true).trail(true).withColor(Color.GREEN).withFade(Color.GREEN).build());
					} else if(type.equalsIgnoreCase("Simple")) { fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).flicker(true).trail(true).withColor(Color.GRAY).withFade(Color.GRAY).build());
					} else { fwm.addEffect(FireworkEffect.builder().with(Type.BALL_LARGE).flicker(true).trail(true).withColor(Color.RED).withFade(Color.RED).build()); }
					fw.setFireworkMeta(fwm);
					
					event.setCancelled(true);
					String randomEnchant = enchantRarity.get(random.nextInt(enchantRarity.size()));
					String enchantName = ChatColor.stripColor(randomEnchant.replace(" X", "").replace(" IX", "").replace(" VIII", "").replace(" VII", "").replace(" VI", "").replace(" V", "").replace(" IV", "").replace(" III", "").replace(" II", "").replace(" I", "").replace(" ", ""));
					
					event.getPlayer().sendMessage(randomEnchant);
					item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("given-item").toUpperCase()), 1, (byte) RandomPackage.getBookOptionsConfig().getInt("given-item-data"));
					itemMeta.setDisplayName(randomEnchant);
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString("success-lore").replace("%success%", "" + random.nextInt(101))));
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString("destroy-lore").replace("%destroy%", "" + random.nextInt(101))));
					for(int o = 0; o < RandomPackage.getPlugin().getConfig().getStringList(type + "." + ChatColor.stripColor(enchantName) + ".BookLore").size(); o++) {
						lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getStringList(type + "." + ChatColor.stripColor(enchantName) + ".BookLore").get(o)));
					}
					if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Armor")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Armor")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Helmet")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Helmet")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Chestplate")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Chestplate")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Leggings")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Leggings")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Boots")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Boots")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Weapon")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Weapon")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Sword")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Sword")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Axe")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Axe")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Tool")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Tool")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Shovel")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Shovel")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Pickaxe")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Pickaxe")));
					} else if(RandomPackage.getEnchantmentTypeConfig().getString(enchantName).equalsIgnoreCase("Bow")) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlaceholderConfig().getString("EnchantTypes.Bow")));
					} else { lore.add(ChatColor.GRAY + "Unknown Enchant"); }
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString("last-lore")));
					itemMeta.setLore(lore);
					item.setItemMeta(itemMeta);
					if(event.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
						event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
						if(event.getPlayer().getInventory().firstEmpty() < 0) {
							event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), item);
						} else { event.getPlayer().getInventory().addItem(item); }
					} else { event.getPlayer().getInventory().setItemInMainHand(item); }
					event.getPlayer().updateInventory();
					lore.clear();
					return;
				}
			}
			return;
		}
	}
}