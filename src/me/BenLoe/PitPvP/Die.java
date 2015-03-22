package me.BenLoe.PitPvP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.Prison.Main.Currency.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Die {

	public static void die(Player p, Player damager, Location loc, boolean displayName){
		CombatLog.setOutOfCombat(p);
		int killstreak = StatAPI.getKillstreak(p);
		StatAPI.addKillstreak(damager);
		int newmoney = 0;
		if (killstreak == 0){
			newmoney = 200;
		}else{
		 newmoney = Math.round(6 * (killstreak));
		}
		MoneyAPI.addMoney(damager, newmoney);
		damager.sendMessage(ChatColor.GRAY + "Killed player: " + ChatColor.GREEN + "+ " + newmoney + "$");
		damager.setLevel(damager.getLevel() + 5);
		p.setLevel(0);
		StatAPI.resetKillstreak(p);
		if (Events.lastdamager.containsKey(p.getName())){
			Events.lastdamager.remove(p.getName());
		}
			Random r = new Random();
			int number = 0;
				for(int i = 1; i <= 60; i++){
				int util = r.nextInt(20) + 1;
				if (util == 1){
					List<String> armors = new ArrayList<String>();
					if (p.getInventory().getBoots() != null){
						armors.add("Boots");
					}
					if (p.getInventory().getLeggings() != null){
						armors.add("Leggings");
					}
					if (p.getInventory().getChestplate() != null){
						armors.add("Chestplate");
					}
					if (p.getInventory().getHelmet() != null){
						armors.add("Helmet");
					}
					if (armors.size() > 0){
						int rand = r.nextInt(armors.size());
						String type = armors.get(rand);
						switch(type){
						case "Boots":
							p.getWorld().dropItemNaturally(loc, p.getInventory().getBoots());
							p.getInventory().setBoots(null);
							number++;
							break;
						case "Chestplate":
							p.getWorld().dropItemNaturally(loc, p.getInventory().getChestplate());
							p.getInventory().setChestplate(null);
							number++;
							break;
						case "Leggings":
							p.getWorld().dropItemNaturally(loc, p.getInventory().getLeggings());
							p.getInventory().setLeggings(null);
							number++;
							break;
						case "Helmet":
							p.getWorld().dropItemNaturally(loc, p.getInventory().getHelmet());
							p.getInventory().setHelmet(null);
							number++;
							break;
						}
					}
				}else{
				int slot = r.nextInt(35) + 1;
				if (p.getInventory().getItem(slot) != null && slot != 7 && slot != 8){
					p.getWorld().dropItemNaturally(loc, p.getInventory().getItem(slot));
					p.getInventory().setItem(slot, null);
					number++;
				}
				}
				if (number == 5){
					break;
				}
				}
				p.updateInventory();
				
				for (Player p1 : Bukkit.getOnlinePlayers()){
					if (p1.getWorld().getName().equals("PVP")){
						if (!p1.getName().equals(damager.getName())){
							if (displayName){
								String name = damager.getInventory().getItemInHand().getType().name().replace("_", " ").toLowerCase();
							p1.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + damager.getName() + ChatColor.GRAY + " using " + ChatColor.YELLOW + name);
							}else{
							p1.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " was killed by " + ChatColor.YELLOW + damager.getName());	
							}
						}
					}
				}
	}
	
	@SuppressWarnings("deprecation")
	public static void itemsFly(Location loc){
		ItemStack blood = new ItemStack(351, 1, (byte) 1);
		final Item item1 = loc.getWorld().dropItemNaturally(loc.add(0, 1, 0), blood);
		item1.setVelocity(new Vector(0.1, 0.1, 0));
		item1.setPickupDelay(1000);
		final Item item2 = loc.getWorld().dropItemNaturally(loc.add(0, 1, 0), blood);
		item2.setVelocity(new Vector(0, 0.1, 0.1));
		item2.setPickupDelay(1000);
		final Item item3 = loc.getWorld().dropItemNaturally(loc.add(0, 1, 0), blood);
		item3.setVelocity(new Vector(-0.1, 0.1, 0));
		item3.setPickupDelay(1000);
		final Item item4 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item4.setVelocity(new Vector(0, 0.1, -0.1));
		item4.setPickupDelay(1000);
		final Item item5 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item5.setVelocity(new Vector(0.05, -0.1, 0.1));
		item5.setPickupDelay(1000);
		final Item item6 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item6.setVelocity(new Vector(-0.1, -0.1, 0.05));
		item6.setPickupDelay(1000);
		final Item item7 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item7.setVelocity(new Vector(-0.1, 0.1, -0.1));
		item7.setPickupDelay(1000);
		final Item item8 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item8.setVelocity(new Vector(0.15, 0.1, -0.15));
		item8.setPickupDelay(1000);
		final Item item9 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item9.setVelocity(new Vector(-0.1, 0.2, -0.1));
		item9.setPickupDelay(1000);
		final Item item10 = loc.getWorld().dropItemNaturally(loc.add(0, 0, 0), blood);
		item10.setVelocity(new Vector(0.1, 0.4, 0.1));
		item10.setPickupDelay(1000);
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
			public void run(){
				item1.teleport(item1.getLocation().subtract(0, 200, 0));
				item2.teleport(item1.getLocation().subtract(0, 200, 0));
				item3.teleport(item1.getLocation().subtract(0, 200, 0));
				item4.teleport(item1.getLocation().subtract(0, 200, 0));
				item5.teleport(item1.getLocation().subtract(0, 200, 0));
				item6.teleport(item1.getLocation().subtract(0, 200, 0));
				item7.teleport(item1.getLocation().subtract(0, 200, 0));
				item8.teleport(item1.getLocation().subtract(0, 200, 0));
				item9.teleport(item1.getLocation().subtract(0, 200, 0));
				item10.teleport(item1.getLocation().subtract(0, 200, 0));
			}
		}, 35l);
	}
}
