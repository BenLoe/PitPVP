package me.BenLoe.PitPvP;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public final Events event = new Events(this);
	public final Files files = new Files(this);
	
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		Files.reloadData();
		saveDefaultConfig();
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
			public void run(){
				CombatLog.check();
			}
		}, 20l, 20l);
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getPlugin(Main.class), new Runnable(){
			public void run(){
				if (!ArrowTrail.arrows.isEmpty()){
				for (Arrow e : ArrowTrail.arrows.keySet()){
						ArrowTrail.arrows.get(e).display(e.getLocation());
				}
				}
			}
		}, 20l, 2l);
		Bukkit.getPluginManager().registerEvents(new Events(this), this);
	}
}
