package me.BenLoe.PitPvP;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;

public class CombatLog {

	public static HashMap<String,Integer> timeleft = new HashMap<String,Integer>();
	
	public static boolean inCombat(Player p){
		if (timeleft.containsKey(p.getName())){
			return true;
		}
		return false;
	}
	
	public static void setInCombat(Player p){
		if (!inCombat(p)){
			p.sendMessage("§c§o✗ Combat logged §7(You may not logout)");
		}
		timeleft.put(p.getName(), 10);
	}
	
	public static void setOutOfCombat(Player p){
		if (inCombat(p)){
		timeleft.remove(p.getName());
		p.sendMessage("§a§o✓ Not Combat logged §7(You may logout)");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void leftInCombat(Player p){
		Location loc = p.getLocation();
		final Hologram h = HolographicDisplaysAPI.createHologram(Main.getPlugin(Main.class), loc.add(0, 2, 0), ChatColor.RED + "" + p.getName() + " combat logged.");
		for (int i = 0; i <35 ; i++){
			ItemStack item = p.getInventory().getItem(i);
			if (item != null && i != 7 && i!= 8){
		loc.getWorld().dropItemNaturally(loc, item);
			}
		}
		for (ItemStack item : p.getInventory().getArmorContents()){
			if (item != null){
				loc.getWorld().dropItemNaturally(loc, item);
			}
		}
		p.getInventory().setChestplate(null);
		p.getInventory().setBoots(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setHelmet(null);
		ParticleEffect.EXPLOSION_LARGE.display(0.2f, 0.2f, 0.2f, 0.2f, 2, loc.add(0, 1.2, 0), 16);
		StatAPI.resetKillstreak(p);
		CombatLog.setOutOfCombat(p);
		p.getInventory().clear();
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
			public void run(){
				h.delete();
			}
		}, 60l);
	}
	
	public static void check(){
		for (Player p : Bukkit.getOnlinePlayers()){
			if (inCombat(p)){
				if (timeleft.get(p.getName()) == 0){
					setOutOfCombat(p);
				}else{
				timeleft.put(p.getName(), timeleft.get(p.getName()) - 1);
				}
			}
		}
	}
}
