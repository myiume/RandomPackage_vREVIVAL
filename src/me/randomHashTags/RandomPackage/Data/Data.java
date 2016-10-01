package me.randomHashTags.RandomPackage.Data;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Events.DisabledEnchants;

public class Data implements Listener {
	public static String whitescroll = ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("WhiteScrolls.protected-lore"));
	public static String transmog = ChatColor.translateAlternateColorCodes('&', RandomPackage.getGivedpItemsConfig().getString("TransmogScrolls.apply-count"));
	
	public static int maxSoulsHarvested = RandomPackage.getPlaceholderConfig().getInt("max-souls-harvested");
	
	private ArrayList<String> bookNames = new ArrayList<String>();
	public static ArrayList<String> getSoulBookNames = new ArrayList<String>(), getLegendaryBookNames = new ArrayList<String>(), getUltimateBookNames = new ArrayList<String>();
	public static ArrayList<String> getEliteBookNames = new ArrayList<String>(), getUniqueBookNames = new ArrayList<String>(), getSimpleBookNames = new ArrayList<String>();
	private ArrayList<String> itemLores = new ArrayList<String>();
	public static ArrayList<String> getSoulItemLores = new ArrayList<String>(), getLegendaryItemLores = new ArrayList<String>(), getUltimateItemLores = new ArrayList<String>();
	public static ArrayList<String> getEliteItemLores = new ArrayList<String>(), getUniqueItemLores = new ArrayList<String>(), getSimpleItemLores = new ArrayList<String>();
	private String type = null, enchant = null;
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		//
		for(int i = 1; i <= 6; i++) {
			if(i == 1) { type = "Soul";
			} else if(i == 2) { type = "Legendary";
			} else if(i == 3) { type = "Ultimate";
			} else if(i == 4) { type = "Elite";
			} else if(i == 5) { type = "Unique";
			} else if(i == 6) { type = "Simple"; }
			//
			for(int o = 0; o <= 100; o++) {
				if(!(type == null)) {
					// Soul Enchants
					if(type.equalsIgnoreCase("Soul")) {
						bookNames = getSoulBookNames;
						itemLores = getSoulItemLores;
						if(o == 0) { enchant = "DivineImmolation";
						} else if(o == 1) { enchant = "Immortal";
						} else if(o == 2) { enchant = "NaturesWrath"; 
						} else { enchant = null; }
					// Legendary Enchants
					} else if(type.equalsIgnoreCase("Legendary")) {
						bookNames = getLegendaryBookNames;
						itemLores = getLegendaryItemLores;
						if(o == 0) { enchant = "Barbarian";
						} else if(o == 1) { enchant = "BloodLust";
						} else if(o == 2) { enchant = "Clarity";
						} else if(o == 3) { enchant = "Deathbringer";
						} else if(o == 4) { enchant = "Devour";
						} else if(o == 5) { enchant = "Disarmor";
						} else if(o == 6) { enchant = "Drunk";
						} else if(o == 7) { enchant = "Enlighted";
						} else if(o == 8) { enchant = "Gears";
						} else if(o == 9) { enchant = "Ghost";
						} else if(o == 10) { enchant = "Greatsword";
						} else if(o == 11) { enchant = "Inquisitive";
						} else if(o == 12) { enchant = "KillAura";
						} else if(o == 13) { enchant = "Lifesteal";
						} else if(o == 14) { enchant = "Overload";
						} else if(o == 15) { enchant = "Protection";
						} else if(o == 16) { enchant = "Rage";
						} else if(o == 17) { enchant = "Sniper";
						} else if(o == 18) { enchant = "Inversion";
						} else { enchant = null; }
					// Ultimate Enchants
					} else if(type.equalsIgnoreCase("Ultimate")) {
						bookNames = getUltimateBookNames;
						itemLores = getUltimateItemLores;
						if(o == 0) { enchant = "Angelic";
						} else if(o == 1) { enchant = "Armored";
						} else if(o == 2) { enchant = "ArrowLifesteal";
						} else if(o == 3) { enchant = "Bleed";
						} else if(o == 4) { enchant = "Blessed";
						} else if(o == 5) { enchant = "Blind";
						} else if(o == 6) { enchant = "Block";
						} else if(o == 7) { enchant = "Cleave";
						} else if(o == 8) { enchant = "CreeperArmor";
						} else if(o == 9) { enchant = "Demonforged";
						} else if(o == 10) { enchant = "Detonate";
						} else if(o == 11) { enchant = "Guardians";
						} else if(o == 12) { enchant = "Hardened";
						} else if(o == 13) { enchant = "Leadership";
						} else if(o == 14) { enchant = "Lucky";
						} else if(o == 15) { enchant = "ObsidianShield";
						} else if(o == 16) { enchant = "Piercing";
						} else if(o == 17) { enchant = "Shackle";
						} else if(o == 18) { enchant = "Silence";
						} else if(o == 19) { enchant = "Skilling";
						} else if(o == 20) { enchant = "Tank";
						} else if(o == 21) { enchant = "Training";
						} else if(o == 22) { enchant = "Unfocus";
						} else if(o == 23) { enchant = "Venom";
						} else if(o == 24) { enchant = "Wither";
						} else if(o == 25) { enchant = "Enrage";
						} else if(o == 26) { enchant = "EnderWalker";
						} else if(o == 27) { enchant = "Heavy";
						} else if(o == 28) { enchant = "Farcast";
						} else { enchant = null; }
					// Elite Enchants
					} else if(type.equalsIgnoreCase("Elite")) {
						bookNames = getEliteBookNames;
						itemLores = getEliteItemLores;
						if(o == 0) { enchant = "AntiGravity";
						} else if(o == 1) { enchant = "Cactus";
						} else if(o == 2) { enchant = "DeepWounds";
						} else if(o == 3) { enchant = "DoubleStrike";
						} else if(o == 4) { enchant = "EnderWalker";
						} else if(o == 5) { enchant = "Execute";
						} else if(o == 6) { enchant = "Frozen";
						} else if(o == 7) { enchant = "Hellfire";
						} else if(o == 8) { enchant = "IceAspect";
						} else if(o == 9) { enchant = "IceFreeze";
						} else if(o == 10) { enchant = "Implants";
						} else if(o == 11) { enchant = "Infernal";
						} else if(o == 12) { enchant = "Nimble";
						} else if(o == 13) { enchant = "Poison";
						} else if(o == 14) { enchant = "Poisoned";
						} else if(o == 15) { enchant = "Pummel";
						} else if(o == 16) { enchant = "RocketEscape";
						} else if(o == 17) { enchant = "Shockwave";
						} else if(o == 18) { enchant = "SkillSwipe";
						} else if(o == 19) { enchant = "SmokeBomb";
						} else if(o == 20) { enchant = "Spirits";
						} else if(o == 21) { enchant = "Springs";
						} else if(o == 22) { enchant = "Stormcaller";
						} else if(o == 23) { enchant = "Telepathy";
						} else if(o == 24) { enchant = "Teleportation";
						} else if(o == 25) { enchant = "Trap";
						} else if(o == 26) { enchant = "Trickster";
						} else if(o == 27) { enchant = "UndeadRuse";
						} else if(o == 28) { enchant = "Valor";
						} else if(o == 29) { enchant = "Vampire";
						} else if(o == 30) { enchant = "Virus";
						} else if(o == 31) { enchant = "Voodoo";
						} else if(o == 32) { enchant = "Dodge";
						} else { enchant = null; }
					// Unique
					} else if(type.equalsIgnoreCase("Unique")) {
						bookNames = getUniqueBookNames;
						itemLores = getUniqueItemLores;
						if(o == 0) { enchant = "Aquatic";
						} else if(o == 1) { enchant = "AutoSmelt";
						} else if(o == 2) { enchant = "Berserk";
						} else if(o == 3) { enchant = "Commander";
						} else if(o == 4) { enchant = "Cowification";
						} else if(o == 5) { enchant = "Curse";
						} else if(o == 6) { enchant = "EnderShift";
						} else if(o == 7) { enchant = "Experience";
						} else if(o == 8) { enchant = "Explosive";
						} else if(o == 9) { enchant = "Featherweight";
						} else if(o == 10) { enchant = "Molten";
						} else if(o == 11) { enchant = "ObsidianDestroyer";
						} else if(o == 12) { enchant = "Paralyze";
						} else if(o == 13) { enchant = "PlagueCarrier";
						} else if(o == 14) { enchant = "Ragdoll";
						} else if(o == 15) { enchant = "Ravenous";
						} else if(o == 16) { enchant = "Reforged";
						} else if(o == 17) { enchant = "SelfDestruct";
						} else if(o == 18) { enchant = "SpiritLink";
						} else if(o == 19) { enchant = "Lifebloom";
						} else { enchant = null; }
					// Simple Enchants
					} else if(type.equalsIgnoreCase("Simple")) {
						bookNames = getSimpleBookNames;
						itemLores = getSimpleItemLores;
						if(o == 0) { enchant = "Confusion";
						} else if(o == 1) { enchant = "Decapitation";
						} else if(o == 2) { enchant = "Epicness";
						} else if(o == 3) { enchant = "Glowing";
						} else if(o == 4) { enchant = "Haste";
						} else if(o == 5) { enchant = "Headless";
						} else if(o == 6) { enchant = "Healing";
						} else if(o == 7) { enchant = "Insomnia";
						} else if(o == 8) { enchant = "Lightning";
						} else if(o == 9) { enchant = "Nutrition";
						} else if(o == 10) { enchant = "Obliterate";
						} else if(o == 11) { enchant = "Oxygenate";
						} else if(o == 12) { enchant = "ThunderingBlow";
						} else { enchant = null; }
					} else { type = null; enchant = null; }
					
					if(!(type == null) && !(enchant == null) && !(bookNames == null) && !(itemLores == null)) {
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean(type + "." + enchant) == true) {
							for(int p = 1; p <= 10; p++) {
								if(!(RandomPackage.getPlugin().getConfig().getString(type + "." + enchant + "." + enchant + p + "-bookName") == null) && !(bookNames.contains(RandomPackage.getPlugin().getConfig().getString(type + "." + enchant + "." + enchant + p + "-bookName")))) {
									bookNames.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + enchant + "." + enchant + p + "-bookName")));
								}
								if(!(RandomPackage.getPlugin().getConfig().getString(type + "." + enchant + "." + enchant + p + "-itemLore") == null) && !(bookNames.contains(RandomPackage.getPlugin().getConfig().getString(type + "." + enchant + "." + enchant + p + "-itemLore")))) {
									itemLores.add(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString(type + "." + enchant + "." + enchant + p + "-itemLore")));
								}
							}
						} else {
							if(type.equalsIgnoreCase("Soul")) { enchant = ChatColor.RED + enchant;
							} else if(type.equalsIgnoreCase("Legendary")) { enchant = ChatColor.GOLD + enchant;
							} else if(type.equalsIgnoreCase("Ultimate")) { enchant = ChatColor.YELLOW + enchant;
							} else if(type.equalsIgnoreCase("Elite")) { enchant = ChatColor.AQUA + enchant;
							} else if(type.equalsIgnoreCase("Unique")) { enchant = ChatColor.GREEN + enchant;
							} else if(type.equalsIgnoreCase("Simple")) { enchant = ChatColor.GRAY + enchant;
							} else { enchant = ChatColor.RED + "IGNORE_THIS"; }
							if(!(DisabledEnchants.list.contains(enchant))) { DisabledEnchants.list.add(enchant); }
						}
					}
				}
			}
			//
		}
		//
		return;
	}

}
