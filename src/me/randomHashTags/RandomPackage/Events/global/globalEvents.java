package me.randomHashTags.RandomPackage.Events.global;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.randomHashTags.RandomPackage.RandomPackage;

public class globalEvents implements Listener {
	@EventHandler
	private void playerMoveEvent(PlayerMoveEvent event) {
		if(event.getPlayer().hasPotionEffect(PotionEffectType.FIRE_RESISTANCE) && !(event.getPlayer().getFireTicks() == -20)
				&& RandomPackage.getGlobalEventsConfig().getBoolean("FireTicks") == true) {
			event.getPlayer().setFireTicks(-20);
			return;
		} else { return; }
	}
	@EventHandler
	private void rageParticles(EntityDamageByEntityEvent event) {
		if(event.isCancelled() || !(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof Player)
				|| !(RandomPackage.getGlobalEventsConfig().getBoolean("rage-particles") == true)) {
			return;
		} else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
				public void run() {
					if(event.isCancelled()) { return;
					} else {
						Player damager = (Player) event.getDamager();
						LivingEntity entity = (LivingEntity) event.getEntity();
						for(int i = 1; i <= 6; i++) {
							if(!(damager.getInventory().getItemInMainHand() == null) && damager.getInventory().getItemInMainHand().hasItemMeta() && damager.getInventory().getItemInMainHand().getItemMeta().hasLore()
									&& damager.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&', RandomPackage.getPlugin().getConfig().getString("Legendary.Rage.Rage" + i + "-itemLore")))) {
								entity.getWorld().playEffect(new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
								entity.getWorld().playEffect(new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY() + 1, entity.getLocation().getZ()), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
								return;
							}
						}
						return;
					}
				}
			}, 2);
			return;
		}
	}
	private ArrayList<String> appleSickness = new ArrayList<String>();
	@EventHandler
	private void playerConsumeEvent(PlayerItemConsumeEvent event) {
		if(RandomPackage.getGlobalEventsConfig().getBoolean("GoldenAppleSickness") == false) { return;
		} else {
			for(int i = 1; i <= 3; i++) {
				if(event.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("GOLDEN_APPLE")) {
					if(!(appleSickness.contains(event.getPlayer().getName() + i))
							|| appleSickness.contains(event.getPlayer().getName() + 3)) {
						appleSickness.add(event.getPlayer().getName() + i);
						if(appleSickness.contains(event.getPlayer().getName() + 3)) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGlobalEventsConfig().getString("GoldenAppleSicknessMessage")));
							event.getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
							event.getPlayer().removePotionEffect(PotionEffectType.POISON);
							event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 1));
							event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 1));
							return;
						} else {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getGlobalEventsConfig().getString("GoldenAppleWarningMessage")));
							return;
						}
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
						public void run() {
							for(int i = 1; i <= 3; i++) {
								appleSickness.remove(event.getPlayer().getName() + i);
							}
							return;
						}
					}, 20 * RandomPackage.getGlobalEventsConfig().getInt("GoldenAppleSicknessInSeconds"));
				} else {
					return;
				}
			}
			return;
		}
	}
}
