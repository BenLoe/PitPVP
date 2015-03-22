package me.BenLoe.PitPvP;

import org.Prison.Main.Main;
import org.Prison.Main.Currency.MoneyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class InfoBoard {

	@SuppressWarnings("deprecation")
	public static void setBoard(Player p){
		Scoreboard sc = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sc.registerNewObjective(p.getName(), "dummy");
		o.setDisplayName("§r  §7§m--§4§l[ The Pit ]§7§m-- ");
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score1 = o.getScore(Bukkit.getServer().getOfflinePlayer("§r"));
		score1.setScore(16);
		Score score2 = o.getScore(Bukkit.getServer().getOfflinePlayer("§a§lMoney:"));
		score2.setScore(15);
		Score score3 = o.getScore(Bukkit.getServer().getOfflinePlayer("" + MoneyAPI.getMoney(p)));
		score3.setScore(14);
		Score score4 = o.getScore(Bukkit.getServer().getOfflinePlayer("§r§1"));
		score4.setScore(13);
		Score score5 = o.getScore(Bukkit.getServer().getOfflinePlayer("§b§lKillstreak:"));
		score5.setScore(12);
		Score score6 = o.getScore(Bukkit.getServer().getOfflinePlayer("§r" + StatAPI.getKillstreak(p)));
		score6.setScore(11);
		Score score9 = o.getScore(Bukkit.getServer().getOfflinePlayer("§r§2"));
		score9.setScore(10);
		p.setScoreboard(sc);
	}
	
	public static void update(final Objective o, final Player p, final Score money, final Score Killstreak){
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
			@SuppressWarnings("deprecation")
			public void run(){
				if (p.isOnline()){			
					if (p.getWorld().getName().equals("PVP")){
						Score money1 = o.getScore(Bukkit.getServer().getOfflinePlayer("" + MoneyAPI.getMoney(p)));
						Score crystal1 = o.getScore(Bukkit.getServer().getOfflinePlayer("§r" + StatAPI.getKillstreak(p)));
					if (Integer.parseInt(money.getEntry()) != MoneyAPI.getMoney(p)){
				    money1.setScore(14);
				    p.getScoreboard().resetScores(money.getEntry());
					}
					    p.getScoreboard().resetScores(Killstreak.getEntry());
					    crystal1.setScore(11);
					}
				}
			}
		}, 20l * 8);
	}
		
	public static void update2(final Objective o, final Player p, final Score money, final Score crystal){
		update(o, p, money, crystal);
	}
	
	@SuppressWarnings("deprecation")
	public static void update(Player p){
		if (p.getWorld().getName().equals("PVP")){
			Objective o = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
			Score old = o.getScore(Bukkit.getServer().getOfflinePlayer("§r" + (StatAPI.getKillstreak(p) - 1)));
			Score newscore = o.getScore(Bukkit.getServer().getOfflinePlayer("§r" + (StatAPI.getKillstreak(p))));
			p.getScoreboard().resetScores(old.getEntry());
			newscore.setScore(11);
		}
	}
}
