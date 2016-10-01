package me.randomHashTags.RandomPackage.commands;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Data.Data;
import me.randomHashTags.RandomPackage.Events.DisabledEnchants;
import me.randomHashTags.RandomPackage.Events.alchemist.alchemistEvents;
import me.randomHashTags.RandomPackage.Events.enchanter.enchanterEvents;
import me.randomHashTags.RandomPackage.Events.filter.filterEvents;
import me.randomHashTags.RandomPackage.Events.gkit.gkitEvents;
import me.randomHashTags.RandomPackage.Events.tinkerer.tinkererEvents;

public class randomPackageCommands implements CommandExecutor {
	private static ArrayList<String> messages = new ArrayList<String>(), lore = new ArrayList<String>();
	private static ItemStack item = new ItemStack(Material.ACACIA_STAIRS, 1);
	private static ItemMeta itemMeta = item.getItemMeta();
	private Random random = new Random();
	public static ArrayList<Player> activeFilter = new ArrayList<Player>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// Random Package Command
		if(cmd.getName().equalsIgnoreCase("randompackage")) {
			if(args.length == 0) {
				messages.add(ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "========================================");
				messages.add(ChatColor.GOLD + "        RandomPackage - " + ChatColor.RED + "Made by " + ChatColor.LIGHT_PURPLE + "Random" + ChatColor.GOLD + ChatColor.MAGIC + "H" + ChatColor.GREEN + "ashTags");
				messages.add(" ");
				messages.add(ChatColor.BLUE + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "========================================");
			} else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				sender.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Plugin has been reloaded");
				Bukkit.getPluginManager().disablePlugin(RandomPackage.getPlugin());
				Bukkit.getPluginManager().enablePlugin(RandomPackage.getPlugin());
				return true;
			} else { return true; }
		// Disabled enchants
		} else if(cmd.getName().equalsIgnoreCase("de")) {
			sender.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Current enchants disabled:");
			sender.sendMessage(DisabledEnchants.list.toString().replace("[", "").replace("]", ""));
			return true;
		// filter
		} else if(cmd.getName().toLowerCase().startsWith("filter")) {
			if(sender instanceof Player) {
				if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("Filter.permission"))) {
					if(args.length == 0) {
						for(int i = 0; i < RandomPackage.getMessagesConfig().getStringList("Filter.argument-0").size(); i++) {
							messages.add(RandomPackage.getMessagesConfig().getStringList("Filter.argument-0").get(i));
						}
					} else if(args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
						if(activeFilter.contains(sender)) {
							activeFilter.remove(sender);
							for(int i = 0; i < RandomPackage.getMessagesConfig().getStringList("Filter.toggle-off").size(); i++) {
								messages.add(RandomPackage.getMessagesConfig().getStringList("Filter.toggle-off").get(i));
							}
						} else {
							activeFilter.add((Player) sender);
							for(int i = 0; i < RandomPackage.getMessagesConfig().getStringList("Filter.toggle-on").size(); i++) {
								messages.add(RandomPackage.getMessagesConfig().getStringList("Filter.toggle-on").get(i));
							}
						}
					} else if(args.length == 1 && args[0].equalsIgnoreCase("edit")) {
						((Player) sender).openInventory(filterEvents.itemFilterSelector);
					}
				} else {
					messages.add(RandomPackage.getCommandsAndPermissionsConfig().getString("Filter.insufficient-permission"));
				}
			} else {
				sender.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Only players can use this command");
				return true;
			}
		// splitsouls
		} else if(cmd.getName().toLowerCase().startsWith("splitsouls")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("SplitSouls.permission"))) {
					if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)
							|| !(player.getInventory().getItemInMainHand().hasItemMeta()) || !(player.getInventory().getItemInMainHand().getItemMeta().hasLore())) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("SplitSouls.needs-soul-tracker").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
						player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
						return true;
					} else {
						String type = null; int multiply = 0;
						if(args.length == 0) {
							for(int i = 1; i <= RandomPackage.getPlaceholderConfig().getInt("max-souls-harvested"); i++) {
								if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.legendary.tracked-lore").replace("%souls%", "" + i)))) { type = "legendary"; multiply = 5;
								} else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.ultimate.tracked-lore").replace("%souls%", "" + i)))) { type = "ultimate"; multiply = 4;
								} else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.elite.tracked-lore").replace("%souls%", "" + i)))) { type = "elite"; multiply = 3;
								} else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.unique.tracked-lore").replace("%souls%", "" + i)))) { type = "unique"; multiply = 2;
								} else if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.simple.tracked-lore").replace("%souls%", "" + i)))) { type = "simple"; multiply = 1; }
								if(!(type == null) && !(multiply == 0)) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("SplitSouls.split-" + type).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix"))).replace("%souls%", "" + i)));
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("SplitSouls.add-soul-gems").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix"))).replace("%amount%", "" + (i * multiply))));
									item = player.getInventory().getItemInMainHand();
									lore.addAll(item.getItemMeta().getLore());
									lore.remove(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + i)));
									lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("SoulTrackers." + type + ".tracked-lore").replace("%souls%", "" + 0)));
									if(lore.contains(Data.whitescroll)) { lore.remove(Data.whitescroll); lore.add(Data.whitescroll); }
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									player.getInventory().setItemInMainHand(item); player.updateInventory(); lore.clear();
									//
									item = new ItemStack(Material.getMaterial(RandomPackage.getSoulConfig().getString("SoulGem.item").toUpperCase()), 1, (byte) RandomPackage.getSoulConfig().getInt("SoulTrackers.item-data"));
									itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "" + (i * multiply))));
									for(int o = 0; o < RandomPackage.getSoulConfig().getStringList("SoulGem.lore").size(); o++) { lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getStringList("SoulGem.lore").get(o))); }
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									if(player.getInventory().firstEmpty() < 0) { player.getWorld().dropItem(player.getLocation(), item); } else { player.getInventory().addItem(item); }
									player.updateInventory(); lore.clear(); type = null; multiply = 0;
									return true;
								} else if(i == 5000 && type == null && multiply == 0) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("SplitSouls.needs-souls").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
									player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
									return true;
								}
							}
						}
						return true;
					}
					
				} else {
					messages.add(RandomPackage.getMessagesConfig().getString("SplitSouls.insufficient-permission"));
				}
			} else {
				sender.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Only players can execute this command!");
				return true;
			}
		// kitop
		} else if(cmd.getName().toLowerCase().startsWith("kitop")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(sender.hasPermission("RandomPackage.kitop")) {
					kitop(player);
					return true;
				} else {
					messages.add(RandomPackage.getMessagesConfig().getString("Kitop.insufficient-permission"));
					player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 2);
				}
			} else {
				sender.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "Only players can execute this command!");
				return true;
			}
		// givedp
		} else if(cmd.getName().toLowerCase().startsWith("givedp")) {
			String configItem = null, type = null, target = null;
			int argument = 0, data = 0;
			if(args.length == 0) {
				sender.sendMessage(ChatColor.BLUE + "RandomPackage> " + ChatColor.YELLOW + "/givedp <player> <item>");
				sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Available Givedp items: 1-30" + ChatColor.RESET + ChatColor.AQUA + " (/givedp <item>)");
				return true;
			} else if(args.length == 1) {
				if(sender instanceof Player) { target = sender.getName(); }
				argument = 0;
			} else if(args.length == 2 || args.length == 3) {
				argument = 1;
				if(!(Bukkit.getPlayer(args[0]) == null) && Bukkit.getPlayer(args[0]).isOnline()) {
					target = Bukkit.getPlayer(args[0]).getName();
				} else {
					sender.sendMessage(RandomPackage.prefix + ChatColor.RED + "Player '" + ChatColor.YELLOW + args[0] + ChatColor.RED + "' doesn't exist or isn't online!");
					return true;
				}
			} else { return true; }
			
			if(args[argument].equalsIgnoreCase("1") || args[argument].equalsIgnoreCase("simplesoultracker")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase()), 1, (byte) RandomPackage.getGivedpItemsConfig().getInt("SoulTrackers.item-data")); configItem = "SoulTracker.simple";
			} else if(args[argument].equalsIgnoreCase("2") || args[argument].equalsIgnoreCase("uniquesoultracker")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase()), 1, (byte) RandomPackage.getGivedpItemsConfig().getInt("SoulTrackers.item-data")); configItem = "SoulTracker.unique";
			} else if(args[argument].equalsIgnoreCase("3") || args[argument].equalsIgnoreCase("elitesoultracker")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase()), 1, (byte) RandomPackage.getGivedpItemsConfig().getInt("SoulTrackers.item-data")); configItem = "SoulTracker.elite";
			} else if(args[argument].equalsIgnoreCase("4") || args[argument].equalsIgnoreCase("ultimatesoultracker")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase()), 1, (byte) RandomPackage.getGivedpItemsConfig().getInt("SoulTrackers.item-data")); configItem = "SoulTracker.ultimate";
			} else if(args[argument].equalsIgnoreCase("5") || args[argument].equalsIgnoreCase("legendarysoultracker")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("SoulTrackers.item").toUpperCase()), 1, (byte) RandomPackage.getGivedpItemsConfig().getInt("SoulTrackers.item-data")); configItem = "SoulTracker.legendary";
			} else if(args[argument].equalsIgnoreCase("6") || args[argument].equalsIgnoreCase("whitescroll")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("WhiteScrolls.item").toUpperCase()), 1); configItem = "WhiteScrolls";
			} else if(args[argument].equalsIgnoreCase("7") || args[argument].equalsIgnoreCase("blackscroll")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("BlackScrolls.item").toUpperCase()), 1); configItem = "BlackScrolls";
			} else if(args[argument].equalsIgnoreCase("8") || args[argument].equalsIgnoreCase("transmog") || args[argument].equalsIgnoreCase("transmogscroll")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("TransmogScrolls.item").toUpperCase()), 1); configItem = "TransmogScrolls";
			} else if(args[argument].equalsIgnoreCase("9") || args[argument].equalsIgnoreCase("itemnametag") || args[argument].equalsIgnoreCase("nametag")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("ItemNameTags.item").toUpperCase()), 1); configItem = "ItemNameTags";
			} else if(args[argument].equalsIgnoreCase("10") || args[argument].equalsIgnoreCase("mms") || args[argument].equalsIgnoreCase("mysterymobspawner")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getGivedpItemsConfig().getString("MysteryMobSpawners.item").toUpperCase()), 1); configItem = "MysteryMobSpawners";
			} else if(args[argument].equalsIgnoreCase("11") || args[argument].equalsIgnoreCase("sb") || args[argument].equalsIgnoreCase("soulbook")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()), 1); configItem = "Book.Soul";
			} else if(args[argument].equalsIgnoreCase("12") || args[argument].equalsIgnoreCase("legendaryb") || args[argument].equalsIgnoreCase("legendarybook")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()), 1); configItem = "Book.Legendary";
			} else if(args[argument].equalsIgnoreCase("13") || args[argument].equalsIgnoreCase("ultimateb") || args[argument].equalsIgnoreCase("ultimatebook")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()), 1); configItem = "Book.Ultimate";
			} else if(args[argument].equalsIgnoreCase("14") || args[argument].equalsIgnoreCase("eliteb") || args[argument].equalsIgnoreCase("elitebook")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()), 1); configItem = "Book.Elite";
			} else if(args[argument].equalsIgnoreCase("15") || args[argument].equalsIgnoreCase("uniqueb") || args[argument].equalsIgnoreCase("uniquebook")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()), 1); configItem = "Book.Unique";
			} else if(args[argument].equalsIgnoreCase("16") || args[argument].equalsIgnoreCase("simpleb") || args[argument].equalsIgnoreCase("simplebook")) {
				item = new ItemStack(Material.getMaterial(RandomPackage.getBookOptionsConfig().getString("reveal-item").toUpperCase()), 1); configItem = "Book.Simple";
			} else if(args[argument].equalsIgnoreCase("17") || args[argument].equalsIgnoreCase("sg") || args[argument].equalsIgnoreCase("soulgem")) {
				if(!(RandomPackage.getSoulConfig().get("SoulGem.item-data") == null)) { data = RandomPackage.getSoulConfig().getInt("SoulGem.item-data"); } else { data = 0; }
				item = new ItemStack(Material.getMaterial(RandomPackage.getSoulConfig().getString("SoulGem.item").toUpperCase()), 1, (byte) data); configItem = "SoulGem";
			} else if(args[argument].toLowerCase().startsWith("gkit")) {
				for(int i = 1; i <= RandomPackage.getGkitsConfig().getInt("chest-size"); i++) {
					if(args[argument].equalsIgnoreCase("gkit" + i)) { configItem = "gkit" + Math.subtractExact(i, 1); }
				}
			} else if(args[argument].toLowerCase().equalsIgnoreCase("kitop")) {
				if(sender instanceof Player) {
					kitop((Player) sender);
				} else if(args.length == 2) {
					if(!(Bukkit.getPlayer(args[0]) == null) && Bukkit.getPlayer(args[0]).isOnline()) {
						kitop(Bukkit.getPlayer(args[0]));
					}
				}
				return true;
			} else { return true; }
			
			int randomNumber = random.nextInt(100);
			if(!(RandomPackage.getGivedpItemsConfig().getBoolean("BlackScrolls.randomized-percent") == true)) { randomNumber = RandomPackage.getGivedpItemsConfig().getInt("BlackScrolls.default-percent");
			} else if(randomNumber < RandomPackage.getGivedpItemsConfig().getInt("BlackScrolls.min-percent")) { randomNumber = RandomPackage.getGivedpItemsConfig().getInt("BlackScrolls.min-percent") + randomNumber; }
			if(randomNumber > 100) { randomNumber = 100; }
			
			lore.clear();
			
			if(configItem.startsWith("Book")) {
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString(configItem.replace("Book.", "") + ".name")));
				for(int i = 0; i < RandomPackage.getBookOptionsConfig().getStringList(configItem.replace("Book.", "") + ".lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getStringList(configItem.replace("Book.", "") + ".lore").get(i)));
				}
				//sender.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getBookOptionsConfig().getString("Messages.Obtain" + type)));
			} else if(configItem.equals("SoulGem")) {
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("SoulGem.name").replace("%souls%", "" + random.nextInt(1001))));
				for(int i = 0; i < RandomPackage.getSoulConfig().getStringList("SoulGem.lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getStringList("SoulGem.lore").get(i)));
				}
				//sender.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getSoulConfig().getString("Messages.SoulGem.Obtain")));
			} else if(configItem.equals("WhiteScrolls") || configItem.equals("TransmogScrolls") || configItem.equals("BlackScrolls") || configItem.equals("ItemNameTags") || configItem.equals("MysteryMobSpawners") || configItem.startsWith("SoulTracker")) {
				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString(configItem.replace("SoulTracker", "SoulTrackers") + ".name")));
				for(int i = 0; i < RandomPackage.getGivedpItemsConfig().getStringList(configItem.replace("SoulTracker", "SoulTrackers") + ".lore").size(); i++) {
					lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getStringList(configItem.replace("SoulTracker", "SoulTrackers") + ".lore").get(i).replace("%percent%", "" + randomNumber)));
				}
				//sender.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("Messages." + configItem + "Obtain")));
			} else if(configItem.startsWith("gkit")) {
				
				if(args.length == 0) {
					for(int i = 0; i <= RandomPackage.getGkitsConfig().getInt("chest-size"); i++) {
						if(!(RandomPackage.getGkitsConfig().getString("" + Math.subtractExact(i, 1)) == null) && configItem.equalsIgnoreCase("gkit" + Math.subtractExact(i, 1))) {
							
							if(sender instanceof Player) {
								//sender.sendMessage(ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString("givedpObtain").replace("%gkit%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".Gui.Name")))));
								((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
							} else {
								if(args.length == 2 && !(Bukkit.getPlayer(args[0]) == null) && Bukkit.getPlayer(args[0]).isOnline()) {
									//sender.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString("givedpConsole").replace("%player%", Bukkit.getPlayer(args[0]).getName()).replace("%gkit%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".Gui.Name")))));
								} else {
									sender.sendMessage(ChatColor.RED + "Player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " might be offline, or doesn't exist.");
									sender.sendMessage(ChatColor.RED + "/givedp <player> <item> <amount>");
									return true;
								}
							}
							
							for(int o = 1; o <= 10; o++) {
								if(!(RandomPackage.getGkitsConfig().getString(i + ".items." + o) == null)) {
									if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".item-data") == null)) { data = RandomPackage.getGkitsConfig().getInt(i + ".items." + o + ".item-data"); }
									item = new ItemStack(Material.getMaterial(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".item").toUpperCase()), 1, (byte) data);
									if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".name") == null)) { itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".name"))); }
									if(!(RandomPackage.getGkitsConfig().get(i + ".items." + o + ".lore") == null)) {
										for(int p = 0; p < RandomPackage.getGkitsConfig().getStringList(i + ".items." + o + ".lore").size(); p++) {
											lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGkitsConfig().getStringList(i + ".items." + o + ".lore").get(p)));
										}
									}
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									//
									Enchantment enchant = Enchantment.ARROW_DAMAGE;
									int enchantLevel = 0;
									for(int p = 1; p <= 10; p++) {
										if(!(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p) == null)) {
											if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("protection") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("prot")) { enchant = Enchantment.PROTECTION_ENVIRONMENTAL;
											} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("blastprotection") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("blastprot")) { enchant = Enchantment.PROTECTION_EXPLOSIONS;
											} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("featherfalling") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("ff")) { enchant = Enchantment.PROTECTION_FALL;
											} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("blastprotection") || RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("blastprot")) { enchant = Enchantment.PROTECTION_PROJECTILE;
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
											} else if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).toLowerCase().startsWith("respiration")) { enchant = Enchantment.WATER_WORKER; }
											for(int q = 1; q <= 100; q++) {
												if(RandomPackage.getGkitsConfig().getString(i + ".items." + o + ".enchant" + p).endsWith("_" + q)) { enchantLevel = q; }
											}
											item.addUnsafeEnchantment(enchant, enchantLevel);
										}
									}
									//
									if(sender instanceof Player) {
										Player player = (Player) sender;
										if(player.getInventory().firstEmpty() < 0) {
											player.getWorld().dropItemNaturally(player.getLocation(), item);
										} else { player.getInventory().addItem(item); }
										player.updateInventory();
									} else if(args.length == 2 || args.length == 3) {
										if(Bukkit.getPlayer(args[0]) == null || !(Bukkit.getPlayer(args[0]).isOnline())) {
											sender.sendMessage(ChatColor.RED + "Player " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " doesn't exist or is offline.");
											return true;
										} else {
											if(Bukkit.getPlayer(args[0]).getInventory().firstEmpty() < 0) {
												Bukkit.getPlayer(args[0]).getWorld().dropItem(Bukkit.getPlayer(args[0]).getLocation(), item);
											} else {
												Bukkit.getPlayer(args[0]).getInventory().addItem(item);
												Bukkit.getPlayer(args[0]).updateInventory();
											}
										}
									}
									lore.clear();
									//
								}
							}
							
						}
					}
				}
			}
			itemMeta.setLore(lore);
			item.setItemMeta(itemMeta);
			if(!(sender instanceof Player)) {
				if(!(target == null)) {
					if(args.length == 3 && !(Integer.parseInt(args[2]) == 0)) {
						item.setAmount(Integer.parseInt(args[2]));
					}
					Bukkit.getPlayer(target).getInventory().addItem(item);
					//Bukkit.getPlayer(target).sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Obtain." + configItem)));
					Bukkit.getPlayer(target).updateInventory();
					return true;
				} else { sender.sendMessage(ChatColor.RED + "Player " + ChatColor.YELLOW + args[0] + ChatColor.RED + " is offline or doesn't exist"); return true; }
			} else {
				Player player = (Player) sender;
				if(args.length == 1) {
					player.getInventory().addItem(item);
					player.updateInventory();
					return true;
				} else if(args.length == 2) {
					if(!(target == null)) {
						Bukkit.getPlayer(target).getInventory().addItem(item);
						Bukkit.getPlayer(target).updateInventory();
						return true;
					} else { sender.sendMessage(ChatColor.RED + "Player " + ChatColor.YELLOW + args[0] + ChatColor.RED + " is offline or doesn't exist"); return true; }
				} else if(args.length == 3) {
					sender.sendMessage(ChatColor.YELLOW + "This has not been implemented yet.");
					return true;
				} else { return true; }
			}
		// gkit
		} else if(cmd.getName().equalsIgnoreCase("gkit")) {
			
			if(sender instanceof Player) {
				
				if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("gkits.permission")) && args.length == 0) {
					messages.add(RandomPackage.getMessagesConfig().getString("gkits.open"));
					((Player) sender).openInventory(gkitEvents.gkits);
					gkitEvents.playerGkit(((Player) sender));
				} else if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("gkits.reset-permission")) && args.length == 1 && args[0].equalsIgnoreCase("reset")) {
					messages.add(RandomPackage.getMessagesConfig().getString("gkits.reset-self-gkits"));
					RandomPackage.getGkitDataConfig().set(((Player) sender).getUniqueId().toString(), null);
					for(int i = 0; i < gkitEvents.players.size(); i++) {
						if(gkitEvents.players.get(i).startsWith(((Player) sender).getName())) { gkitEvents.players.remove(i); }
					}
					RandomPackage.saveGkitDataConfig();
				} else if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("gkits.reset-other-permission")) && args.length == 2 && args[0].equalsIgnoreCase("reset")) {
					if(Bukkit.getPlayer(args[1]) == null || !(Bukkit.getPlayer(args[1]).isOnline())) {
						messages.add(RandomPackage.getMessagesConfig().getString("gkits.reset-other-offline"));
					} else {
						messages.add(RandomPackage.getMessagesConfig().getString("gkits.reset-other-gkits").replace("%player%", Bukkit.getPlayer(args[1]).getName()));
						RandomPackage.getGkitDataConfig().set(Bukkit.getPlayer(args[1]).getUniqueId().toString(), null);
						for(int i = 0; i < gkitEvents.players.size(); i++) {
							if(gkitEvents.players.get(i).startsWith(Bukkit.getPlayer(args[1]).getName())); { gkitEvents.players.remove(i); }
						}
						RandomPackage.saveGkitDataConfig();
					}
				} else {
					messages.add(RandomPackage.getMessagesConfig().getString("gkits.insufficent-permission"));
				}
				
			} else {
				messages.add(RandomPackage.prefix + ChatColor.YELLOW + "Only players can execute this command.");
			}
			
		// Tinkerer
		} else if(cmd.getName().equalsIgnoreCase("tinkerer")) {
			
			if(sender instanceof Player) {
				
				if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("Tinkerer.permission"))) {
					messages.add(RandomPackage.getMessagesConfig().getString("Tinkerer.open"));
					tinkererEvents.tinkerer(((Player) sender));
				} else {
					messages.add(RandomPackage.getMessagesConfig().getString("Tinkerer.insufficient-permission"));
				}
				
			} else {
				messages.add(RandomPackage.prefix + ChatColor.YELLOW + "Only players can execute this command.");
			}
			
		// Enchanter
		} else if(cmd.getName().equalsIgnoreCase("enchanter")) {
			
			if(sender instanceof Player) {
				if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("Enchanter.permission"))) {
					messages.add(RandomPackage.getMessagesConfig().getString("Enchanter.open"));
					((Player) sender).openInventory(enchanterEvents.enchanterGui);
				} else {
					messages.add(RandomPackage.getMessagesConfig().getString("Enchanter.insufficient-permission"));
				}
			} else {
				messages.add(RandomPackage.prefix + ChatColor.YELLOW + "Only players can execute this command.");
			}
		// Alchemist
		} else if(cmd.getName().equalsIgnoreCase("alchemist")) {
			
			if(sender instanceof Player) {
				if(sender.hasPermission(RandomPackage.getCommandsAndPermissionsConfig().getString("Alchemist.permission"))) {
					messages.add(RandomPackage.getMessagesConfig().getString("Alchemist.open"));
					alchemistEvents.playerOpenAlchemist((Player) sender);
				} else {
					messages.add(RandomPackage.getMessagesConfig().getString("Alchemist.insufficient-permission"));
				}
			} else {
				messages.add(RandomPackage.prefix + ChatColor.YELLOW + "Only players can execute this command.");
			}
			
		} else {
			return true;
		}
		if(!(messages.isEmpty())) {
			for(int i = 0; i < messages.size(); i++) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.get(i).replace("%prefix%", RandomPackage.getMessagesConfig().getString("prefix"))));
			}
			messages.clear();
		}
		return true;
	}
	
	
	public static void kitop(Player player) {
		for(int i = 1; i <= 10; i++) {
			itemMeta.setDisplayName(null);
			if(i == 1) { item = new ItemStack(Material.DIAMOND_HELMET);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Drunk.Drunk4-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Hardened.Hardened3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Cactus.Cactus2-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Aquatic.Aquatic1-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.EnderShift.EnderShift3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Glowing.Glowing1-itemLore")));
			} else if(i == 2) { item = new ItemStack(Material.DIAMOND_CHESTPLATE);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Overload.Overload3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Armored.Armored4-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Guardians.Guardians10-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Valor.Valor5-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Curse.Curse5-itemLore")));
			} else if(i == 3) { item = new ItemStack(Material.DIAMOND_LEGGINGS);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.ObsidianShield.ObsidianShield1-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Wither.Wither5-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Cactus.Cactus2-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.PlagueCarrier.PlagueCarrier1-itemLore")));
			} else if(i == 4) { item = new ItemStack(Material.DIAMOND_BOOTS);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Gears.Gears3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.AntiGravity.AntiGravity3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.RocketEscape.RocketEscape3-itemLore")));
			} else if(i == 5) { item = new ItemStack(Material.DIAMOND_SWORD);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Inquisitive.Inquisitive4-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Lifesteal.Lifesteal5-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Rage.Rage6-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Blind.Blind3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Demonforged.Demonforged4-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Poison.Poison3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.DeepWounds.DeepWounds3-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Featherweight.Featherweight3-itemLore")));
			} else if(i == 6) { item = new ItemStack(Material.DIAMOND_AXE);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Rage.Rage6-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Confusion.Confusion3-itemLore")));
			} else if(i == 7) { item = new ItemStack(Material.DIAMOND_PICKAXE);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Haste.Haste3-itemLore")));
			} else if(i == 8) { item = new ItemStack(Material.BOW);
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Explosive.Explosive5-itemLore")));
			lore.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Lightning.Lightning3-itemLore")));
			} else if(i == 9) { item = new ItemStack(Material.GOLDEN_APPLE, 16, (byte) 1);
			} else if(i == 10) { item = new ItemStack(Material.ARROW, 1); }
			if(i <= 4) { itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
			} else if(i == 5 || i == 6) {
				itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, false);
				itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
			} else if(i == 7) {
				itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);
				itemMeta.removeEnchant(Enchantment.FIRE_ASPECT);
				itemMeta.addEnchant(Enchantment.DIG_SPEED, 5, false);
				itemMeta.addEnchant(Enchantment.SILK_TOUCH, 2, false);
			} else if(i == 8) {
				itemMeta.removeEnchant(Enchantment.DIG_SPEED);
				itemMeta.removeEnchant(Enchantment.SILK_TOUCH);
				itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, false);
				itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
				itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 3, false);
				itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, false);
			}
			if(i <= 8) { itemMeta.addEnchant(Enchantment.DURABILITY, 3, false); } else { itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE); itemMeta.removeEnchant(Enchantment.ARROW_FIRE);
						itemMeta.removeEnchant(Enchantment.ARROW_INFINITE); itemMeta.removeEnchant(Enchantment.ARROW_KNOCKBACK);itemMeta.removeEnchant(Enchantment.DURABILITY); }
			if(i > 4) { itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL); }
			itemMeta.setLore(lore);
			item.setItemMeta(itemMeta);
			if(player.getInventory().firstEmpty() < 0) { player.getWorld().dropItem(player.getLocation(), item); } else { player.getInventory().addItem(item); player.updateInventory(); }
			lore.clear();
		}
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Obtain.Kitop").replace("%prefix%", ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("prefix")))));
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
		return;
	}
}
