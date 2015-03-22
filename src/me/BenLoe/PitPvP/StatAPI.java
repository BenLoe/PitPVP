package me.BenLoe.PitPvP;

import java.util.HashMap;

import org.PrisonMain.Achievement.AchievementAPI;
import org.PrisonMain.Achievement.Menu.AchievementMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class StatAPI {

	
	public static HashMap<String,Integer> killstreak = new HashMap<String,Integer>();
	
	public static int getKillstreak(Player p){
		if (killstreak.containsKey(p.getName())){
			return killstreak.get(p.getName());
		}else{
			return 0;
		}
	}
	
	public static void addKillstreak(Player p){
		if (killstreak.containsKey(p.getName())){
			killstreak.put(p.getName(), getKillstreak(p) + 1);
			if (killstreak.get(p.getName()) == 15){
				AchievementAPI.completeAchievement(p, AchievementMenu.ULTIMATE_KILLSTREAK);
			}
		}else{
			killstreak.put(p.getName(), 1);
		}
		InfoBoard.update(p);
	}
	
	@SuppressWarnings("deprecation")
	public static void resetKillstreak(Player p){
		if (p.getWorld().getName().equals("PVP")){
			Objective o = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
			Score old = o.getScore(Bukkit.getServer().getOfflinePlayer("§r" + (StatAPI.getKillstreak(p))));
			Score newscore = o.getScore(Bukkit.getServer().getOfflinePlayer("§r0"));
			p.getScoreboard().resetScores(old.getEntry());
			newscore.setScore(11);
		}
		killstreak.remove(p.getName());
	}
}
