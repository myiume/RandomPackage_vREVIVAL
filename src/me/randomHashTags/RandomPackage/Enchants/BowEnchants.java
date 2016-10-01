package me.randomHashTags.RandomPackage.Enchants;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;

import me.randomHashTags.RandomPackage.RandomPackage;

public class BowEnchants implements Listener {
	private int chance = 0;
	private Player shooter = null;
	private LivingEntity entity = null;
	public static ArrayList<ItemStack> bow = new ArrayList<ItemStack>();
	public static ArrayList<Player> test = new ArrayList<Player>();
	private ArrayList<EntityType> livingEntities = new ArrayList<EntityType>();
	private Random random = new Random();
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		livingEntities.add(EntityType.BAT); livingEntities.add(EntityType.BLAZE);
		livingEntities.add(EntityType.CAVE_SPIDER); livingEntities.add(EntityType.CHICKEN); livingEntities.add(EntityType.COW); livingEntities.add(EntityType.CREEPER);
		livingEntities.add(EntityType.ENDER_DRAGON); livingEntities.add(EntityType.ENDERMAN); livingEntities.add(EntityType.ENDERMITE); livingEntities.add(EntityType.GHAST);
		livingEntities.add(EntityType.GIANT); livingEntities.add(EntityType.GUARDIAN); livingEntities.add(EntityType.HORSE); livingEntities.add(EntityType.IRON_GOLEM);
		livingEntities.add(EntityType.MAGMA_CUBE); livingEntities.add(EntityType.MUSHROOM_COW); livingEntities.add(EntityType.OCELOT); livingEntities.add(EntityType.PIG);
		livingEntities.add(EntityType.PIG_ZOMBIE); livingEntities.add(EntityType.PLAYER); livingEntities.add(EntityType.RABBIT); livingEntities.add(EntityType.SHEEP);
		livingEntities.add(EntityType.SHULKER); livingEntities.add(EntityType.SILVERFISH); livingEntities.add(EntityType.SKELETON); livingEntities.add(EntityType.SLIME);
		livingEntities.add(EntityType.SNOWMAN); livingEntities.add(EntityType.SPIDER); livingEntities.add(EntityType.SQUID); livingEntities.add(EntityType.VILLAGER);
		livingEntities.add(EntityType.WITCH); livingEntities.add(EntityType.WITHER); livingEntities.add(EntityType.WOLF); livingEntities.add(EntityType.ZOMBIE);
	}
	@EventHandler
	private void entityShootBowEvent(EntityShootBowEvent event) {
		if(event.getEntity() instanceof Player && !(event.isCancelled())) {
			Player player = (Player) event.getEntity();
			if(event.getBow().hasItemMeta() && event.getBow().getItemMeta().hasLore()) {
				bow.add(event.getBow());
				test.add(player);
				if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Elite.Hellfire") == true) {
					chance = 10;
					// HELLFIRE
					for(int i = 1; i <= 5; i++) {
						chance = chance + 10;
						if(event.getBow().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Elite.Hellfire.Hellfire" + i + "-itemLore"))) && random.nextInt(100) <= chance) {
							event.getProjectile().setFireTicks(300);
							return;
						}
					}
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
					public void run() {
						bow.remove(event.getBow());
						test.remove(player);
					}
				}, 100);
				chance = 0;
				return;
			} else {
				chance = 0;
				bow.remove(player);
				test.remove(player);
				return;
			}
		} else { bow.remove(event.getEntity()); test.remove(event.getEntity()); return; }
	}
	@EventHandler
	private void projectileHitEvents(ProjectileHitEvent event) {
		if(event.getEntity().getShooter() instanceof Player && !((Player) event.getEntity().getShooter()).getGameMode().equals(GameMode.CREATIVE)) {
			for(int i = 0; i < test.size(); i++) {
				if(test.get(i).equals(((Player) event.getEntity().getShooter()))) {
					int eye = i;
					Player player = (Player) event.getEntity().getShooter();
					// SIMPLE
					if(RandomPackage.getEnabledEnchantsConfig().getBoolean("Simple.Lightning") == true) { Lightning(event); }
					//
					Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
						public void run() {
							if(event.getEntity().isOnGround() == true) {
								
							} else {
								shooter = (Player) event.getEntity().getShooter();
								if(0 < event.getEntity().getNearbyEntities(0.15, 0.15, 0.15).size()) {
									entity = (LivingEntity) event.getEntity().getNearbyEntities(0.15, 0.15, 0.15).get(0);
								} else { return; }
								
								
								
							}
							bow.remove(eye);
							test.remove(player);
							shooter = null;
						}
					}, 1);
					return;
				}
			}
			return;
		} else { return; }
	}
	@EventHandler
	private void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if(!(event.isCancelled()) && event.getDamager().getType().name().endsWith("ARROW")) {
			Projectile proj = (Projectile) event.getDamager();
			if(proj.getShooter() instanceof Player && test.contains((Player) proj.getShooter())) {
				((Player) proj.getShooter()).sendMessage("worked");
				return;
			} else { return; }
		} else { return; }
	}
	/*
	 * SIMPLE ENCHANTS
	 */
	// LIGHTNING
	private void Lightning(ProjectileHitEvent event) {
		chance = 12;
		for(int i = 0; i < test.size(); i++) {
			chance = chance + 2;
			for(int o = 1; o <= 3; o++) {
				if(test.get(i).equals(event.getEntity().getShooter()) && bow.get(i).getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Simple.Lightning.Lightning" + o + "-itemLore")))
						&& random.nextInt(100) <= chance) {
					event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
					return;
				}
			}
		}
		return;
	}
	/*
	 * ELITE ENCHANTS
	 */
	
	/*
	 * ULTIMATE ENCHANTS
	 */
}
