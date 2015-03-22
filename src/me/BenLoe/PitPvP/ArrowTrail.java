package me.BenLoe.PitPvP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.Prison.Main.Items.ItemAPI;
import org.Prison.Main.Ranks.RankType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum ArrowTrail {

	GREEN(10),
	
	HEART(11),
	
	ORE(16),
	
	WATER(12),
	
	FLAME(13),
	
	NOTE(14),
	
	EXPLOSION(15),
	
	NONE(31);
	
	private int slot;
	
	public static List<String> inMenu = new ArrayList<String>();
	public static HashMap<Arrow,ArrowTrail> arrows = new HashMap<Arrow,ArrowTrail>();
	
	private ArrowTrail(int i){
		this.slot = i;
	}

	public void setTrail(Player p){
		Files.getDataFile().set("Players." + p.getName() + ".ArrowTrail", this.slot);
		Files.saveDataFile();
	}
	
	public static ArrowTrail getTrail(Player p){
		if (Files.getDataFile().contains("Players." + p.getName() + ".ArrowTrail")){
			for (ArrowTrail t : values()){
				if (t.slot == Files.getDataFile().getInt("Players." + p.getName() + ".ArrowTrail")){
					return t;
				}
			}
		}
		return NONE;
	}
	
	public void display(Location loc){
		switch(this){
		case FLAME:{
		ParticleEffect.FLAME.display(0.0001f, 0.0001f, 0.0001f, 0.0001f, 1, loc, 120);	
		}
		break;
		case GREEN:{
			ParticleEffect.VILLAGER_HAPPY.display(0.01f, 0.01f, 0.01f, 0.0001f, 3, loc, 120);		
		}
		break;
		case HEART:{
			ParticleEffect.HEART.display(0.0001f, 0.0001f, 0.0001f, 0.0001f, 1, loc, 120);	
		}
		break;
		case NOTE:{
			int note = new Random().nextInt(6) + 1;
			switch(note){
			case 1:
				ParticleEffect.NOTE.display(0.0001f, 0.0001f, 0.0001f, 1f, 2, loc, 120);
			break;
			case 2:
				ParticleEffect.NOTE.display(0.0001f, 0.0001f, 0.0001f, 2f, 2, loc, 120);
			break;
			case 3:
				ParticleEffect.NOTE.display(0.0001f, 0.0001f, 0.0001f, 3f, 2, loc, 120);
			break;
			case 4:
				ParticleEffect.NOTE.display(0.0001f, 0.0001f, 0.0001f, 4f, 2, loc, 120);
			break;
			case 5:
				ParticleEffect.NOTE.display(0.0001f, 0.0001f, 0.0001f, 5f, 2, loc, 120);
			break;
			case 6:
				ParticleEffect.NOTE.display(0.0001f, 0.0001f, 0.0001f, 6f, 2, loc, 120);
			}
		}
		break;
		case ORE:{
			int ore = new Random().nextInt(4) + 1;
			switch(ore){
			case 1:{
				ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.DIAMOND_BLOCK, (byte)0), 0.0001f, 0.0001f, 0.0001f, 0.0001f, 25, loc, 120);		
			}
			break;
			case 2:{
				ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.GOLD_BLOCK, (byte)0), 0.0001f, 0.0001f, 0.0001f, 0.0001f, 25, loc, 120);	
			}
			break;
			case 3:{
				ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.EMERALD_BLOCK, (byte)0), 0.0001f, 0.0001f, 0.0001f, 0.0001f, 25, loc, 120);	
			}
			break;
			case 4:{
				ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.IRON_BLOCK, (byte)0), 0.0001f, 0.0001f, 0.0001f, 0.0001f, 25, loc, 50);	
			}
			}
		}
		break;
		case WATER:{
			ParticleEffect.DRIP_WATER.display(0.01f, 0.01f, 0.01f, 0.0001f, 5, loc, 120);	
		}
		break;
		case EXPLOSION:{
			ParticleEffect.EXPLOSION_LARGE.display(0.0001f, 0.0001f, 0.0001f, 0.001f, 1, loc, 120);
		}
		}
	}
	
	public void wasClicked(Player p){
		switch(this){
		case EXPLOSION:{
			if (RankType.getRank(p).equals(RankType.NONE) || RankType.getRank(p).equals(RankType.VIP)){
				p.sendMessage("§cOnly §a§lELITE §cand above can use this arrow trail.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lExplosions" );
			}
		}
		break;
		case FLAME:{
			if (RankType.getRank(p).equals(RankType.NONE)){
				p.sendMessage("§cOnly §a§lVIP §cand above can arrow trails.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lFlames" );
			}
		}
		break;
		case GREEN:{
			if (RankType.getRank(p).equals(RankType.NONE)){
				p.sendMessage("§cOnly §a§lVIP §cand above can arrow trails.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lGreen Sparkles" );
			}
		}
		break;
		case HEART:{
			if (RankType.getRank(p).equals(RankType.NONE)){
				p.sendMessage("§cOnly §a§lVIP §cand above can arrow trails.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lHearts" );
			}
		}
		break;
		case NONE:{
			if (RankType.getRank(p).equals(RankType.NONE)){
				p.sendMessage("§cOnly §a§lVIP §cand above can arrow trails.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail reset." );
			}
		}
		break;
		case NOTE:{
			if (RankType.getRank(p).equals(RankType.NONE)){
				p.sendMessage("§cOnly §a§lVIP §cand above can arrow trails.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lNotes" );
			}
		}
		break;
		case ORE:{
			if (RankType.getRank(p).equals(RankType.NONE) || RankType.getRank(p).equals(RankType.VIP)){
				p.sendMessage("§cOnly §a§lELITE §cand above can use this arrow trail.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lOres" );
			}
		}
		break;
		case WATER:{
			if (RankType.getRank(p).equals(RankType.NONE)){
				p.sendMessage("§cOnly §a§lVIP §cand above can arrow trails.");
			}else{
				this.setTrail(p);
				p.sendMessage("§aArrow trail set to: §b§lWater Drops" );
			}
		}
		}
	}
	
	public ItemStack getItemFor(Player p){
		List<String> lore = new ArrayList<String>();
		switch(this){
		case EXPLOSION:
			lore.add(ChatColor.GRAY + "Set your arrow trail to small explosions.");
			lore.add("");
			lore.add("§a§lELITE §7exclusive");
			return ItemAPI.getItem(Material.TNT, ChatColor.GREEN + "Explosion arrow trail", lore);
		case FLAME:
			lore.add(ChatColor.GRAY + "Set your arrow trail to flames.");
			lore.add("");
			lore.add("§3§lVIP §7exclusive");
			return ItemAPI.getItem(Material.TORCH, ChatColor.GREEN + "Flame arrow trail", lore);
		case GREEN:
			lore.add(ChatColor.GRAY + "Set your arrow trail to green sparkles.");
			lore.add("");
			lore.add("§3§lVIP §7exclusive");
			return ItemAPI.getItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Green Sparkles arrow trail", lore);
		case HEART:
			lore.add(ChatColor.GRAY + "Set your arrow trail to hearts.");
			lore.add("");
			lore.add("§3§lVIP §7exclusive");
			return ItemAPI.getItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Hearts arrow trail", lore);
		case NONE:
			lore.add(ChatColor.GRAY + "Reset your trail.");
			return ItemAPI.getItem(Material.GLASS, ChatColor.GREEN + "No arrow trail", lore);
		case NOTE:
			lore.add(ChatColor.GRAY + "Set your arrow trail to notes.");
			lore.add("");
			lore.add("§3§lVIP §7exclusive");
			return ItemAPI.getItem(Material.NOTE_BLOCK, ChatColor.GREEN + "Notes arrow trail", lore);
		case ORE:
			lore.add(ChatColor.GRAY + "Set your arrow trail to random ores.");
			lore.add("");
			lore.add("§a§lELITE §7exclusive");
			return ItemAPI.getItem(Material.DIAMOND_BLOCK, ChatColor.GREEN + "Ores arrow trail", lore);
		case WATER:
			lore.add(ChatColor.GRAY + "Set your arrow trail to water drops.");
			lore.add("");
			lore.add("§3§lVIP §7exclusive");
			return ItemAPI.getItem(Material.WATER_BUCKET, ChatColor.GREEN + "Water drops arrow trail", lore);
		}
		return new ItemStack(Material.AIR);
	}
	
	public int getSlot(){
		return this.slot;
	}
	
	public static ArrowTrail getItemFromSlot(int slot){
		for (ArrowTrail m : values()){
				if (slot == m.slot){
					return m;
				}
		}
		return NONE;
	}
	public static boolean wasAItem(int slot){
		for (ArrowTrail m : values()){
				if (slot == m.slot){
					return true;
				}
		}
		return false;
	}
	
	public static Inventory createMenu(Inventory inv, Player p){
		for (ArrowTrail m : values()){
				inv.setItem(m.getSlot(), m.getItemFor(p));
		}
		return inv;
	}
	
	public static void openMenu(Player p){
		inMenu.add(p.getName());
		Inventory inv = Bukkit.createInventory(null, 4 * 9, ChatColor.BLUE + "Arrow Trails");
		p.openInventory(createMenu(inv, p));
	}
}
