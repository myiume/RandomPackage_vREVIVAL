package me.randomHashTags.RandomPackage.Enchants;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.randomHashTags.RandomPackage.RandomPackage;

public class EntityDamageByEntityEnchants implements Listener {
	private Player player = null;
	private LivingEntity entity = null;
	private Random random = new Random();
	private double lucky = 0.0;
	private int chance = 0, luckyFinal = 0;
	private Projectile arrow = null;
	@EventHandler
	private void playerDamageEntity(EntityDamageByEntityEvent event) {
		if(event.isCancelled() || !(event.getDamager() instanceof Player) || !(event.getEntity() instanceof LivingEntity)) {
			return;
		} else {
			entity = (LivingEntity) event.getEntity();
			player = (Player) event.getDamager();
			Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
				public void run() {
					if(event.isCancelled() || player.getInventory().getItemInMainHand() == null || !(player.getInventory().getItemInMainHand().hasItemMeta())
							|| !(player.getInventory().getItemInMainHand().getItemMeta().hasLore())) { return;
					} else {
						chance = 0; lucky = 0.0; luckyFinal = 0;
						// SOUL ENCHANTS
						// LEGENDARY ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Barbarian") == true) { Barbarian(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Lifesteal") == true) { Lifesteal(event); }
						// ULTIMATE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Blessed") == true) { Blessed(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Blind") == true) { Blind(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Enrage") == true) { Enrage(event); }
						// ELITE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.DeepWounds") == true) { DeepWounds(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.DoubleStrike") == true) { DoubleStrike(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Execute") == true) { Execute(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Frozen") == true) { Frozen(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.IceAspect") == true) { IceAspect(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Poison") == true) { Poison(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Trap") == true) { Trap(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Trickster") == true) { Trickster(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Vampire") == true) { Vampire(event); }
						// UNIQUE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Berserk") == true) { Berserk(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Featherweight") == true) { Featherweight(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Paralyze") == true) { Paralyze(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Ravenous") == true) { Ravenous(event); }
						// SIMPLE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Confusion") == true) { Confusion(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Epicness") == true) { Epicness(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Insomnia") == true) { Insomnia(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Obliterate") == true) { Obliterate(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.ThunderingBlow") == true) { ThunderingBlow(event); }
						return;
					}
				}
			}, 2);
			return;
		}
	}
	@EventHandler
	private void playerIsDamaged(EntityDamageByEntityEvent event) {
		if(event.isCancelled() || !(event.getEntity() instanceof Player) || !(event.getDamager() instanceof LivingEntity)) {
			return;
		} else {
			entity = (LivingEntity) event.getDamager();
			player = (Player) event.getEntity();
			Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
				public void run() {
					if(event.isCancelled()) { return;
					} else {
						chance = 0; lucky = 0.0; luckyFinal = 0;
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Lucky") == true) {
							for(int i = 1; i <= 10; i++) {
								if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
										&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Lucky.Lucky" + i + "-itemLore")))
										|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
										&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Lucky.Lucky" + i + "-itemLore")))
										|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
										&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Lucky.Lucky" + i + "-itemLore")))
										|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
										&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Lucky.Lucky" + i + "-itemLore")))) {
									if(random.nextInt(4) == 0) { // 25% chance of lucky procking
										lucky = lucky + 0.5;
										luckyFinal = (int) lucky;
									}
								}
							}
						}
						// SOUL ENCHANTS
						// LEGENDARY ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Deathbringer") == true) { Deathbringer(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Enlighted") == true) { Enlighted(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Legendary.Inversion") == true) { Inversion(event); }
						// ULTIMATE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Angelic") == true) { Angelic(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Armored") == true) { Armored(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Creeper") == true) { CreeperArmor(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Demonforged") == true) { Demonforged(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Farcast") == true) { Farcast(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Guardians") == true) { Guardians(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Hardened") == true) { Hardened(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Silence") == true) { Silence(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Tank") == true) { Tank(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Ultimate.Wither") == true) { Wither(event); }
						// ELITE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Cactus") == true) { Cactus(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Dodge") == true) { Dodge(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Poisoned") == true) { Poisoned(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.RocketEscape") == true) { RocketEscape(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Shockwave") == true) { Shockwave(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.SmokeBomb") == true) { SmokeBomb(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Spirits") == true) { Spirits(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Stormcaller") == true) { Stormcaller(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.UndeadRuse") == true) { UndeadRuse(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Valor") == true) { Valor(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Voodoo") == true) { Voodoo(event); }
						// UNIQUE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Curse") == true) { Curse(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.EnderShift") == true) { EnderShift(event); }
						//if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Lifebloom") == true) { Lifebloom(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Molten") == true) { Molten(event); }
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Unique.Ragdoll") == true) { Ragdoll(event); }
						// SIMPLE ENCHANTS
						if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Obliterate") == true) { Obliterate(event); }
						return;
					}
				}
			}, 2);
			return;
		}
	}
	@EventHandler
	private void playerIsDamagedByArrow(EntityDamageByEntityEvent event) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
			public void run() {
				if(event.isCancelled() || !(event.getDamager().getType().name().endsWith("ARROW"))) { arrow = null; return; } else {
					arrow = (Projectile) event.getDamager();
					
					
					
				}
			}
		}, 2);
		return;
	}
	
	
	
	//-------------------------------------------------------------------------//	
	/*
	 * 									Soul
	 */
	//-------------------------------------------------------------------------//
	/*
	 * 
	 */
	/*
	 * Legendary
	 */
	/*
	 * 
	 */
	// BARBARIAN
	private void Barbarian(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 0;
			for(int i = 1; i <= 4; i++) {
				chance = chance + 8;
				double damage = 0.0;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Barbarian.Barbarian" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					if(i == 1) { damage = 1.15; } else if(i == 2) { damage = 1.20; } else if(i == 3) { damage = 1.25; } else { damage = 1.30; }
					event.setDamage(event.getDamage() * damage);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("BarbarianCriticalMessage")));
					player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1, 2);
					return;
				}
			}
			return;
		} else { return; }
	}
	// DEATHRBRINGER
	private void Deathbringer(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 4;
		for(int i = 1; i <= 3; i++) {
			chance = chance + 2;
			if(random.nextInt(100) <= chance) {
				if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
						&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Deathbringer.Deathbringer" + i + "-itemLore")))
						|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
						&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Deathbringer.Deathbringer" + i + "-itemLore")))
						|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
						&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Deathbringer.Deathbringer" + i + "-itemLore")))
						|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
						&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Deathbringer.Deathbringer" + i + "-itemLore")))) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * i + 40, i - 1));
					return;
				}
			}
		}
		return;
	}
	// ENLIGHTED
	private void Enlighted(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			chance = luckyFinal + 7;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 8;
				if(random.nextInt(100) <= chance) {
					if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
							&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted" + i + "-itemLore")))
							|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
							&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted" + i + "-itemLore")))
							|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
							&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted" + i + "-itemLore")))
							|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
							&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Enlighted.Enlighted" + i + "-itemLore")))) {
						if(!(player.getHealth() + 2 > player.getMaxHealth())) {
							player.setHealth(player.getHealth() + 2);
							return;
						} else { return; }
					}
				}
			}
			return;
		} else { return; }
	}
	// INVERSION
	private void Inversion(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player && entity instanceof Player && !(player.getLastDamageCause() == null) && player.getLastDamageCause().equals(DamageCause.ENTITY_ATTACK)) {
			chance = luckyFinal + 6;
			for(int i = 1; i <= 4; i++) {
				chance = chance + 2;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Inversion.Inversion" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					if(player.getHealth() == 20) { return;
					} else if(player.getHealth() + player.getLastDamage() + (20 - player.getHealth()) > 20) { player.setHealth(20);
					} else { player.setHealth(player.getHealth() + player.getLastDamage() + (20 - player.getHealth())); }
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchants.Inversion").replace("%amount%", "" + (player.getHealth() + player.getLastDamage() + (20 - player.getHealth())))));
					return;
				}
			}
		}
		return;
	}
	// LIFESTEAL
	private void Lifesteal(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
			chance = luckyFinal + 6;
			for(int i = 1; i <= 5; i++) {
				chance = chance + 2;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Lifesteal.Lifesteal" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					if(entity.getHealth() - 1 < 0.0 || player.getHealth() + 1 > player.getMaxHealth()) { return;
					} else {
						entity.setHealth(entity.getHealth() - 1);
						player.setHealth(player.getHealth() + 1);
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 5);
						return;
					}
				}
			}
			return;
		} else { return; }
	}
	/*
	 * 
	 */
	/*
	 * Ultimate
	 */
	/*
	 * 
	 */
	// ANGELIC
	private void Angelic(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 6;
		for(int i = 1; i <= 5; i++) {
			chance = chance + 2;
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Angelic.Angelic" + i + "-itemLore")))
					|| !(player.getInventory().getChestplate() == null)
					&& player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Angelic.Angelic" + i + "-itemLore")))
					|| !(player.getInventory().getLeggings() == null)
					&& player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Angelic.Angelic" + i + "-itemLore")))
					|| !(player.getInventory().getBoots() == null)
					&& player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Angelic.Angelic" + i + "-itemLore")))) {
				if(random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, i * 20 + 20, i - 1));
					return;
				}
			}
		}
		return;
	}
	// ARMORED
	private void Armored(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			Player damager = (Player) entity;
			if(!(damager.getInventory().getItemInMainHand() == null) && damager.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
				chance = 0;
				double dividedDamage = 0.0;
				for(int i = 1; i <= 4; i++) {
					chance = chance + 6;
					if(random.nextInt(100) <= chance) {
						if(!(player.getInventory().getHelmet() == null)
								&& player.getInventory().getHelmet().hasItemMeta()
								&& player.getInventory().getHelmet().getItemMeta().hasLore()
								&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Armored.Armored" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						if(!(player.getInventory().getChestplate() == null)
								&& player.getInventory().getChestplate().hasItemMeta()
								&& player.getInventory().getChestplate().getItemMeta().hasLore()
								&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Armored.Armored" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						if(!(player.getInventory().getLeggings() == null)
								&& player.getInventory().getLeggings().hasItemMeta()
								&& player.getInventory().getLeggings().getItemMeta().hasLore()
								&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Armored.Armored" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						if(!(player.getInventory().getBoots() == null)
								&& player.getInventory().getBoots().hasItemMeta()
								&& player.getInventory().getBoots().getItemMeta().hasLore()
								&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Armored.Armored" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						event.setDamage(event.getDamage() / dividedDamage);
						return;
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
	// BLESSED
	private void Blessed(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 6;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 3;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Blessed.Blessed" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					if(random.nextInt(2) == 0) { if(player.hasPotionEffect(PotionEffectType.SLOW)) { player.removePotionEffect(PotionEffectType.SLOW); } }
					if(random.nextInt(2) == 0) { if(player.hasPotionEffect(PotionEffectType.BLINDNESS)) { player.removePotionEffect(PotionEffectType.BLINDNESS); } }
					if(random.nextInt(2) == 0) { if(player.hasPotionEffect(PotionEffectType.CONFUSION)) { player.removePotionEffect(PotionEffectType.CONFUSION); } }
					if(random.nextInt(2) == 0) { if(player.hasPotionEffect(PotionEffectType.WITHER)) { player.removePotionEffect(PotionEffectType.WITHER); } }
					if(random.nextInt(2) == 0) { if(player.hasPotionEffect(PotionEffectType.POISON)) { player.removePotionEffect(PotionEffectType.POISON); } }
					if(random.nextInt(2) == 0) { if(player.hasPotionEffect(PotionEffectType.WEAKNESS)) { player.removePotionEffect(PotionEffectType.WEAKNESS); } }
					return;
				}
			}
			return;
		} else { return; }
	}
	// BLIND
	private void Blind(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") && entity instanceof Player) {
			Player victim = (Player) entity;
			chance = luckyFinal + 7;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Blind.Blind" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * i + 40, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// CREEPER ARMOR
	private void CreeperArmor(EntityDamageByEntityEvent event) {
		if(event.getDamager().getType().equals(EntityType.PRIMED_TNT) || event.getDamager().getType().equals(EntityType.CREEPER)) {
			for(int i = 1; i <= 3; i++) {
				if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
						&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.CreeperArmor.CreeperArmor" + i + "-itemLore")))) {
					event.setDamage(event.getDamage() / i + 2);
					event.setCancelled(true);
					return;
				}
			}
			return;
		} else { return; }
	}
	// DEMONFORGED
	private void Demonforged(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			Player damager = (Player) entity;
			if(!(damager.getInventory().getItemInMainHand() == null) && damager.getInventory().getItemInMainHand().hasItemMeta() && damager.getInventory().getItemInMainHand().getItemMeta().hasLore()
					&& damager.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
				chance = luckyFinal + 9;
				for(int i = 1; i <= 4; i++) {
					chance = chance + 1;
					if(damager.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Demonforged.Demonforged" + i + "-itemLore")))
							&& random.nextInt(100) <= chance) {
						if(!(player.getInventory().getHelmet() == null) && random.nextInt(2) == 0) {
							player.getInventory().getHelmet().setDurability((short) (player.getInventory().getHelmet().getDurability() - Math.subtractExact(chance, 9)));
						}
						if(!(player.getInventory().getChestplate() == null) && random.nextInt(2) == 0) {
							player.getInventory().getChestplate().setDurability((short) (player.getInventory().getChestplate().getDurability() - Math.subtractExact(chance, 9)));
						}
						if(!(player.getInventory().getLeggings() == null) && random.nextInt(2) == 0) {
							player.getInventory().getLeggings().setDurability((short) (player.getInventory().getLeggings().getDurability() - Math.subtractExact(chance, 9)));
						}
						if(!(player.getInventory().getBoots() == null) && random.nextInt(2) == 0) {
							player.getInventory().getBoots().setDurability((short) (player.getInventory().getBoots().getDurability() - Math.subtractExact(chance, 9)));
						}
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
	// ENRAGE
	private void Enrage(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 10;
		for(int i = 1; i <= 3; i++) {
			if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Enrage.Enrage" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				event.setDamage(event.getDamage() * (3 - player.getHealth() / 10));
				player.playSound(player.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1, 2);
				return;
			}
		}
		return;
	}
	// FARCAST
	private void Farcast(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 5;
		double knockback = 0.0;
		for(int i = 1; i <= 5; i++) {
			chance = chance + 5;
			knockback = knockback + 0.25;
			if(!(player.getInventory().getItemInMainHand() == null) && !(player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) && player.getInventory().getItemInMainHand().getType().equals(Material.BOW) && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasLore()
					&& player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Farcast.Farcast" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				entity.setVelocity(new Vector(-entity.getLocation().getDirection().getX() + knockback, 0.0, -entity.getLocation().getDirection().getZ() + knockback));
				return;
			}
		}
		return;
	}
	// GUARDIANS
	private void Guardians(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 4;
		for(int i = 1; i <= 10; i++) {
			chance = chance + 1;
			if(!(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Guardians.Guardians" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				IronGolem ig = (IronGolem) player.getWorld().spawnEntity(player.getLocation(), EntityType.IRON_GOLEM);
				ig.setCollidable(false);
				ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 1, false));
				ig.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 1, false));
				ig.setCustomName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("GuardiansName").replace("%player%", player.getName())));
				ig.setTarget(entity);
				ig.setCanPickupItems(false);
				Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
					public void run() {
						ig.remove();
						return;
					}
				}, 120);
				return;
			}
		}
		return;
	}
	// HARDENED
	private void Hardened(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 24;
		for(int i = 1; i <= 3; i++) {
			chance = chance + 8;
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Hardened.Hardened" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				player.getInventory().getHelmet().setDurability((short) (player.getInventory().getHelmet().getDurability() - 1));
			}
			if(!(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Hardened.Hardened" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				player.getInventory().getChestplate().setDurability((short) (player.getInventory().getChestplate().getDurability() - 1));
			}
			if(!(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Hardened.Hardened" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				player.getInventory().getLeggings().setDurability((short) (player.getInventory().getLeggings().getDurability() - 1));
			}
			if(!(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Hardened.Hardened" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				player.getInventory().getBoots().setDurability((short) (player.getInventory().getBoots().getDurability() - 1));
			}
		}
		return;
	}
	// SILENCE
	private void Silence(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			Player damager = (Player) entity;
			if(!(damager.getInventory().getItemInMainHand() == null) && damager.getInventory().getItemInMainHand().hasItemMeta() && damager.getInventory().getItemInMainHand().getItemMeta().hasLore()
					&& damager.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
				chance = luckyFinal + 3;
				for(int i = 1; i <= 4; i++) {
					chance = chance + 1;
					if(damager.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Silence.Silence" + i + "-itemLore")))
							&& random.nextInt(100) <= chance) {
						player.removePotionEffect(PotionEffectType.ABSORPTION);
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.WATER_BREATHING);
						return;
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
	/*
	 * ELITE ENCHANTS
	 */
	// DODGE
	private void Dodge(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 4;
		for(int i = 1; i <= 5; i++) {
			chance = chance + 4;
			if(player.isSneaking()) { chance = chance + 2; }
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Dodge.Dodge" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Dodge.Dodge" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Dodge.Dodge" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Dodge.Dodge" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Enchants.Dodge")));
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 3);
				return;
			}
		}
	}
	// STORMCALLER
	private void Stormcaller(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			chance = luckyFinal + 13;
			for(int i = 1; i <= 4; i++) {
				chance = chance + 2;
				if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
						&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Stormcaller.Stormcaller" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
						&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Stormcaller.Stormcaller" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
						&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Stormcaller.Stormcaller" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
						&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Stormcaller.Stormcaller" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					entity.getWorld().strikeLightning(new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()));
					return;
				}
			}
			return;
		} else { return; }
	}
	// TANK
	private void Tank(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			Player damager = (Player) entity;
			if(!(damager.getInventory().getItemInMainHand() == null) && damager.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
				chance = 0;
				double dividedDamage = 0.0;
				for(int i = 1; i <= 4; i++) {
					chance = chance + 6;
					if(random.nextInt(100) <= chance) {
						if(!(player.getInventory().getHelmet() == null)
								&& player.getInventory().getHelmet().hasItemMeta()
								&& player.getInventory().getHelmet().getItemMeta().hasLore()
								&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Tank.Tank" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						if(!(player.getInventory().getChestplate() == null)
								&& player.getInventory().getChestplate().hasItemMeta()
								&& player.getInventory().getChestplate().getItemMeta().hasLore()
								&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Tank.Tank" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						if(!(player.getInventory().getLeggings() == null)
								&& player.getInventory().getLeggings().hasItemMeta()
								&& player.getInventory().getLeggings().getItemMeta().hasLore()
								&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Tank.Tank" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						if(!(player.getInventory().getBoots() == null)
								&& player.getInventory().getBoots().hasItemMeta()
								&& player.getInventory().getBoots().getItemMeta().hasLore()
								&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Tank.Tank" + i + "-itemLore")))) {
							dividedDamage = dividedDamage + 1.85 * i;
						}
						event.setDamage(event.getDamage() / dividedDamage);
						return;
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
	// TRICKSTER
	private void Trickster(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 2;
		for(int i = 1; i <= 7; i++) {
			chance = chance + 2;
			if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Trickster.Trickster" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				Location location = entity.getLocation();
				if(entity.getLocation().getYaw() >= 0) { location.setYaw(entity.getLocation().getYaw() - 180);
				} else { location.setYaw(entity.getLocation().getYaw() + 180); }
				entity.teleport(location);
				return;
			}
		}
	}
	// VOODOO
	private void Voodoo(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			chance = luckyFinal + 6;
			for(int i = 1; i <= 6; i++) {
				chance = chance + 2;
				if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
						&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Voodoo.Voodoo" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
						&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Voodoo.Voodoo" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
						&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Voodoo.Voodoo" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
						&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Voodoo.Voodoo" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40 * i + 40, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// WITHER
	private void Wither(EntityDamageByEntityEvent event) {
		if(!(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()) {
			chance = luckyFinal + 4;
			for(int i = 1; i <= 5; i++) {
				chance = chance + 2;
				if(player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Ultimate.Wither.Wither" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40 * i + 40, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	
	/*
	 * 
	 */
	/*
	 * Elite
	 */
	/*
	 * 
	 */
	// CACTUS
	private void Cactus(EntityDamageByEntityEvent event) {
		for(int i = 1; i <= 2; i++) {
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Cactus.Cactus" + i + "-itemLore")))
					|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Cactus.Cactus" + i + "-itemLore")))
					|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Cactus.Cactus" + i + "-itemLore")))
					|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Cactus.Cactus" + i + "-itemLore")))) {
				if(random.nextInt(100) <= 25) {
					entity.damage(i);
					return;
				}
			}
		}
		return;
	}
	// DEEP WOUNDS
	private void DeepWounds(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
			Player victim = (Player) event.getEntity();
			chance = luckyFinal + 7;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.DeepWounds.DeepWounds" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 140, i - 1));
					return;
				}
			}
			return;
		}
	}
	// DOUBLE STRIKE
	private void DoubleStrike(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
			chance = luckyFinal + 10;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 2;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.DoubleStrike.DoubleStrike" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					event.setDamage(event.getDamage() * 2);
					return;
				}
			}
			return;
		} else { return; }
	}
	// EXECUTE
	private void Execute(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
			chance = luckyFinal + 7;
			for(int i = 1; i <= 7; i++) {
				chance = chance + 1;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Execute.Execute" + i + "-itemLore")))
						&& random.nextInt(100) <= chance && entity.getHealth() < entity.getMaxHealth() / 2) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * i, 1));
					return;
				}
			}
			return;
		}
	}
	// FROZEN
	private void Frozen(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
			chance = luckyFinal + 7;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Frozen.Frozen" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40 * i + 40, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// ICE ASPECT
	private void IceAspect(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 0;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 3;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.IceAspect.IceAspect" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40 * i, i + 2));
					return;
				}
			}
			return;
		} else { return; }
	}
	// POISON
	private void Poison(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD")) {
			chance = luckyFinal + 7;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Poison.Poison" + i + "-itemLore")))) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40 * i + 40, i - 1));
					return;
				}
			}
		} else { return; }
	}
	// POISONED
	private void Poisoned(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 4;
		for(int i = 1; i <= 4; i++) {
			chance = chance + 2;
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Poisoned.Poisoned" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Poisoned.Poisoned" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Poisoned.Poisoned" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Poisoned.Poisoned" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40 * i + 40, i - 1));
				return;
			}
		}
		return;
	}
	// ROCKET ESCAPE
	private void RocketEscape(EntityDamageByEntityEvent event) {
		if(!(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()) {
			chance = luckyFinal + 7;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.RocketEscape.RocketEscape" + i + "-itemLore")))
						&& player.getHealth() <= i * 2 + 4
						&& random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * i + 20, i - 1 + 7));
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * i + 40, i));
					return;
				}
			}
			return;
		} else { return; }
	}
	// SHOCKWAVE
	private void Shockwave(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 7;
		for(int i = 1; i <= 4; i++) {
			chance = chance + 3;
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Shockwave.Shockwave" + i + "-itemLore")))
					|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Shockwave.Shockwave" + i + "-itemLore")))
					|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Shockwave.Shockwave" + i + "-itemLore")))
					|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Shockwave.Shockwave" + i + "-itemLore")))) {
				if(random.nextInt(100) <= chance) {
					entity.setVelocity(new Vector(-entity.getLocation().getDirection().getX() * 3, entity.getVelocity().getY(), -entity.getLocation().getDirection().getZ() * 3));
					return;
				}
			}
		}
		return;
	}
	// SMOKE BOMB
	private void SmokeBomb(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 8;
		for(int i = 1; i <= 8; i++) {
			chance = chance + 1;
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.SmokeBomb.SmokeBomb" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * i + 100, i - 1));
				return;
			}
		}
		return;
	}
	// SPIRITS
	private void Spirits(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 4;
		for(int i = 1; i <= 10; i++) {
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Spirits.Spirits" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				for(int o = 0; o < i / 2; o++) {
					Blaze blaze = (Blaze) player.getWorld().spawnEntity(player.getLocation(), EntityType.BLAZE);
					blaze.setCustomName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("SpiritsName").replace("%player%", player.getName())));
					blaze.setTarget(entity);
					if(random.nextInt(10) <= 5) {
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 1));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999, 1));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, 0));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 1));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 1));
					} else {
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 0));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 99999, 0));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 0));
					}
					if(random.nextInt(100) <= 10) {
						blaze.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1));
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
						public void run() {
							blaze.remove();
						}
					}, 240);
				}
			}
		}
		return;
	}
	// TRAP
	private void Trap(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 2;
		for(int i = 1; i <= 3; i++) {
			chance = chance + 2;
			if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Trap.Trap" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40 * i + 40, i + 1));
				return;
			}
		}
		return;
	}
	// UNDEAD RUSE
	private void UndeadRuse(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 3;
		for(int i = 1; i <= 10; i++) {
			chance = chance + 1;
			if(!(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.UndeadRuse.UndeadRuse" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				for(int o = 1; o <= i; o++) {
					Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
					zombie.setCustomName(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("UndeadRuseName").replace("%player%", player.getName())));
					zombie.setTarget(entity);
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 0));
					zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
					zombie.setCollidable(false);
					zombie.setCanPickupItems(false);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
						public void run() {
							zombie.remove();
						}
					}, 240);
				}
			}
		}
		return;
	}
	// VALOR
	private void Valor(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 10;
		for(int i = 1; i <= 5; i++) {
			chance = chance + 2;
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Valor.Valor" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Valor.Valor" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Valor.Valor" + i + "-itemLore"))) && random.nextInt(100) <= chance
					|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Valor.Valor" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * i + 120, i - 1));
				return;
			}
		}
		return;
	}
	// VAMPIRE
	private void Vampire(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 14;
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			for(int i = 1; i <= 3; i++) {
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Vampire.Vampire" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					if(player.getHealth() + event.getDamage() / 2 > player.getMaxHealth()) { player.setHealth(player.getMaxHealth()); return;
					} else { player.setHealth(player.getHealth() + event.getDamage() / 2); return; }
				}
			}
			return;
		} else { return; }
	}
	
	/*
	 * 
	 */
	/*
	 * Unique
	 */
	/*
	 * 
	 */
	// BERSERK
	private void Berserk(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 4;
			for(int i = 1; i <= 5; i++) {
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Berserk.Berserk" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * i + 60, i - 1));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * i + 60, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// CURSE
	private void Curse(EntityDamageByEntityEvent event) {
		if(entity instanceof Player && !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()) {
			chance = luckyFinal + 8;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 2;
				if(player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Curse.Curse" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * i + 80, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// ENDER SHIFT
	private void EnderShift(EntityDamageByEntityEvent event) {
		if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()) {
			chance = luckyFinal + 15;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.EnderShift.EnderShift" + i + "-itemLore")))
						&& player.getHealth() <= i + 7 && random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * i + 80, i - 1));
					player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * i + 80, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// FEATHERWEIGHT
	private void Featherweight(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 10;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 10;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Featherweight.Featherweight" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * i + 60, i - 1));
					return;
				}
			}
			return;
		}
	}
	// LIFEBLOOM
	/*private void Lifebloom(EntityDamageByEntityEvent event) {
		if(player.getHealth() <= 6) {
			chance = 0;
			for(int i = 1; i <= 5; i++) {
				chance = chance + 20;
				if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore() && random.nextInt(100) <= chance
						|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore() && random.nextInt(100) <= chance
						|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore() && random.nextInt(100) <= chance
						|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore() && random.nextInt(100) <= chance) {
					for(Entity entity : player.getNearbyEntities(10, 10, 10)) {
						if(entity instanceof Player) {
							Player nearbyplayer = (Player) entity;
							if(Bukkit.getPluginManager().getPlugin("MassiveCore") == null && !(Bukkit.getPluginManager().getPlugin("Factions") == null)) {
								RelationParticipator nearbyPlayer = FPlayers.getInstance().getByPlayer((Player) entity);
								if(!(FPlayers.getInstance().getByPlayer(player).getRelationTo(nearbyPlayer) == null) && !(FPlayers.getInstance().getByPlayer(player).getRelationTo(nearbyPlayer) == Relation.ENEMY)) {
									nearbyplayer.setMaxHealth(nearbyplayer.getMaxHealth());
								}
							} else if(!(Bukkit.getPluginManager().getPlugin("Factions") == null) && !(Bukkit.getPluginManager().getPlugin("MassiveCore") == null)) {
								MPlayer nearbyPlayer = MPlayer.get((Player) entity);
								if(!(MPlayer.get(player).getRelationTo(nearbyPlayer) == null) && !(MPlayer.get(player).getRelationTo(nearbyPlayer) == Rel.ENEMY) && !(MPlayer.get(player).getRelationTo(nearbyPlayer) == Rel.NEUTRAL)) {
									nearbyplayer.setMaxHealth(nearbyplayer.getMaxHealth());
								}
							} else {
								DisabledEnchants.list.add(ChatColor.GREEN + "Lifebloom");
							}
							return;
						}
					}
				}
			}
		} else { return; }
	}*/
	// MOLTEN
	private void Molten(EntityDamageByEntityEvent event) {
		chance = luckyFinal + 7;
		for(int i = 1; i <= 4; i++) {
			if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
					&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Molten.Molten" + i + "-itemLore")))
					&& random.nextInt(100) <= chance
					|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
					&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Molten.Molten" + i + "-itemLore")))
					&& random.nextInt(100) <= chance
					|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
					&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Molten.Molten" + i + "-itemLore")))
					&& random.nextInt(100) <= chance
					|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
					&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Molten.Molten" + i + "-itemLore")))
					&& random.nextInt(100) <= chance) {
				entity.setFireTicks(40 * i + 80);
				return;
			}
		}
		return;
	}
	// PARALYZE
	private void Paralyze(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 2;
			for(int i = 1; i <= 4; i++) {
				chance = chance + 2;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Paralyze.Paralyze" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					entity.getWorld().strikeLightning(entity.getLocation());
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * i + 60, i - 1));
					entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * i + 60, i - 1));
					return;
				}
			}
			return;
		} else { return; }
	}
	// RAGDOLL
	private void Ragdoll(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			chance = luckyFinal + 25;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 25;
				if(!(player.getInventory().getHelmet() == null) && player.getInventory().getHelmet().hasItemMeta() && player.getInventory().getHelmet().getItemMeta().hasLore()
						&& player.getInventory().getHelmet().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Ragdoll.Ragdoll" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getChestplate() == null) && player.getInventory().getChestplate().hasItemMeta() && player.getInventory().getChestplate().getItemMeta().hasLore()
						&& player.getInventory().getChestplate().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Ragdoll.Ragdoll" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getLeggings() == null) && player.getInventory().getLeggings().hasItemMeta() && player.getInventory().getLeggings().getItemMeta().hasLore()
						&& player.getInventory().getLeggings().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Ragdoll.Ragdoll" + i + "-itemLore"))) && random.nextInt(100) <= chance
						|| !(player.getInventory().getBoots() == null) && player.getInventory().getBoots().hasItemMeta() && player.getInventory().getBoots().getItemMeta().hasLore()
						&& player.getInventory().getBoots().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Ragdoll.Ragdoll" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					player.setVelocity(player.getLocation().getDirection().multiply(-random.nextDouble() - 0.5));
					return;
				}
			}
			return;
		} else { return; }
	}
	// RAVENOUS
	private void Ravenous(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 12;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 2;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Ravenous.Ravenous" + i + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1, 4));
					return;
				}
			}
			return;
		} else { return; }
	}
	
	/*
	 * 
	 */
	/*
	 * Simple
	 */
	/*
	 * 
	 */
	
	// CONFUSION
	private void Confusion(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
				chance = luckyFinal + 6;
				for(int i = 1; i <= 3; i++) {
					chance = chance + 2;
					if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Confusion.Confusion" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
						entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * i + 120 + 20, i + 2));
						return;
					}
				}
				return;
			} else { return; }
		} else { return; }
	}
	// EPICNESS
	private void Epicness(EntityDamageByEntityEvent event) {
		for(int i = 1; i <= 3; i++) {
			if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Epicness.Epicness" + i + "-itemLore")))) {
				if(event.getEntity().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("true")) { event.getEntity().getWorld().setGameRuleValue("commandBlockOutput", "false"); }
				if(event.getEntity().getWorld().getGameRuleValue("sendCommandFeedback").equalsIgnoreCase("true")) { event.getEntity().getWorld().setGameRuleValue("sendCommandFeedback", "false"); }
				if(event.getEntity().getWorld().getGameRuleValue("logAdminCommands").equalsIgnoreCase("true")) { event.getEntity().getWorld().setGameRuleValue("logAdminCommands", "false"); }
				if(i == 1) { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " ~ ~ ~ particle largesmoke " + event.getEntity().getLocation().getX() + " " + event.getEntity().getLocation().getY() + " " + event.getEntity().getLocation().getZ() + " 0.5 1 0.5 1 25 1");
				} else if(i == 2) { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " ~ ~ ~ particle magicCrit " + event.getEntity().getLocation().getX() + " " + event.getEntity().getLocation().getY() + " " + event.getEntity().getLocation().getZ() + " 0.5 1 0.5 1 25 1");
				} else if(i == 3) { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " ~ ~ ~ particle cloud " + event.getEntity().getLocation().getX() + " " + event.getEntity().getLocation().getY() + " " + event.getEntity().getLocation().getZ() + " 0.5 1 0.5 1 25 1"); }
				return;
			}
		}
	}
	// INSOMNIA
	private void Insomnia(EntityDamageByEntityEvent event) {
		if(entity instanceof Player) {
			chance = luckyFinal + 4;
			for(int i = 1; i <= 7; i++) {
				chance = chance + 2;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Insomnia.Insomnia" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40 * i, 1));
					player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40 * i, i - 1));
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40 * i + 20, i - 1));
					if(random.nextInt(100) <= 25 + i) { event.setDamage(event.getDamage() * 2); }
					return;
				}
			}
		} else { return; }
	}
	// OBLITERATE
	private void Obliterate(EntityDamageByEntityEvent event) {
		if(entity instanceof Player && !(((Player) entity).getInventory().getItemInMainHand() == null) && !(((Player) entity).getInventory().getItemInMainHand().getType().equals(Material.AIR)) && ((Player) entity).getInventory().getItemInMainHand().hasItemMeta() && ((Player) entity).getInventory().getItemInMainHand().getItemMeta().hasLore()) {
			chance = luckyFinal + 50;
			for(int i = 1; i <= 5; i++) {
				chance = chance + 5;
				if(((Player) entity).getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Obliterate.Obliterate" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					double multiplier = i + 0.25;
					event.getEntity().setVelocity(new Vector(-event.getEntity().getLocation().getDirection().getX() * multiplier, event.getEntity().getVelocity().getY(), -event.getEntity().getLocation().getDirection().getZ() * multiplier));
					return;
				}
			}
		}
	}
	// THUNDERING BLOW
	private void ThunderingBlow(EntityDamageByEntityEvent event) {
		if(player.getInventory().getItemInMainHand().getType().name().endsWith("SWORD") || player.getInventory().getItemInMainHand().getType().name().endsWith("_AXE")) {
			chance = luckyFinal + 5;
			for(int i = 1; i <= 3; i++) {
				chance = chance + 1;
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.ThunderingBlow.ThunderingBlow" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
					for(int o = 1; o <= 5; o++) {
						entity.getWorld().strikeLightning(entity.getLocation());
					}
					return;
				}
			}
			return;
		} else { return; }
	}
	@EventHandler
	private void playerItemDamageEvent(PlayerItemDamageEvent event) {
		chance = luckyFinal + 18;
		for(int i = 1; i <= 10; i++) {
			chance = chance + 2;
			if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore() && event.getItem().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Unique.Reforged.Reforged" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
				event.setCancelled(true);
			}
		}
		return;
	}
	@EventHandler
	private void Inquisitive(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			if(event.getEntity().getKiller().getInventory().getItemInMainHand() == null || event.getEntity().getKiller().getInventory().getItemInMainHand().getType().equals(Material.AIR) || !(event.getEntity().getKiller().getInventory().getItemInMainHand().hasItemMeta())
					|| !(event.getEntity().getKiller().getInventory().getItemInMainHand().getItemMeta().hasLore())) {
				return;
			} else {
				for(int i = 1; i <= 4; i++) {
					if(event.getEntity().getKiller().getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Inquisitive.Inquisitive" + i + "-itemLore")))) {
						if(i == 1) { event.setDroppedExp(event.getDroppedExp() / 2); } else {
							event.setDroppedExp(event.getDroppedExp() * i);
							return;
						}
					}
				}
			}
		} else { return; }
	}
}