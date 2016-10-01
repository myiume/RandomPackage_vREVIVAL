package me.randomHashTags.RandomPackage.Enchants;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.Events.soul.soulEvents;

public class SoulEnchants implements Listener {
	private ArrayList<Player> teleblock = new ArrayList<Player>();
	private int number1 = 0, number2 = 0;
	private Random random = new Random();
	@EventHandler
	private void playerIsDamaged(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player && soulEvents.soulMode.contains(event.getEntity())) {
			Player victim = (Player) event.getEntity();
			return;
		} else { return; }
	}
	@EventHandler
	private void playerIsDamagedByArrow(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player && soulEvents.soulMode.contains(event.getEntity()) && event.getDamager().getType().name().endsWith("ARROW")) {
			
		} else { return; }
	}
	private void teleblock(Player player) {
		teleblock.add(player);
		number2 = random.nextInt(11); if(number2 == 0) { number2 = 3; }
		if(player.getInventory().contains(Material.ENDER_PEARL)) {
			number1 = random.nextInt(6);
			if(number1 == 0) { number1 = 1; }
			player.getInventory().remove(new ItemStack(Material.ENDER_PEARL, number1));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchants.Soul.Teleblock").replace("%enderpearlamount%", "" + number1).replace("%seconds%", "" + number2)));
			player.updateInventory();
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin(), new Runnable() {
			public void run() {
				teleblock.remove(player);
			}
		}, 20 * number2);
		return;
	}
	@EventHandler
	private void teleblockInteract(PlayerInteractEvent event) {
		if(teleblock.contains(event.getPlayer()) && !(event.getPlayer().getInventory().getItemInMainHand() == null)
				&& !(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) && event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_PEARL)
				&& !(event.getAction() == Action.LEFT_CLICK_AIR) && !(event.getAction() == Action.LEFT_CLICK_BLOCK)) {
			event.setCancelled(true);
			event.getPlayer().updateInventory();
			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', RandomPackage.getMessagesConfig().getString("Enchants.Soul.Teleblock-interact")));
			return;
		} else  { return; }
	}
}
