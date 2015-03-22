package me.BenLoe.PitPvP;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.Prison.Main.Currency.MoneyAPI;
import org.Prison.Main.Items.ItemAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum Weaponsmith {

	SWORD(2),
	
	BOW(4),
	
	ARROWS(6);
	
	private int slot;
	
	public static List<String> inMenu = new ArrayList<String>();
	
	private Weaponsmith(int i){
		this.slot = i;
	}
	
	public int getSlot(){
		return this.slot;
	}
	
	public static Weaponsmith getItemFromSlot(int slot){
		for (Weaponsmith m : values()){
				if (slot == m.slot){
					return m;
				}
		}
		return BOW;
	}
	public static boolean wasAItem(int slot){
		for (Weaponsmith m : values()){
				if (slot == m.slot){
					return true;
				}
		}
		return false;
	}
	
	public static Inventory createMenu(Inventory inv, Player p){
		for (Weaponsmith m : values()){
				inv.setItem(m.getSlot(), m.getItemFor(p));
		}
		return inv;
	}
	
	public static void openMenu(Player p){
		inMenu.add(p.getName());
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Weaponsmith");
		p.openInventory(createMenu(inv, p));
	}
	
	public void wasClicked(Player p){
		switch(this){
		case ARROWS:
			if (MoneyAPI.getMoney(p) >= 6000){
				MoneyAPI.removeMoney(p, 6000);
				ItemStack item = new ItemStack(Material.ARROW, 32);
				p.getInventory().addItem(item);
				p.updateInventory();
			}
			break;
		case BOW:
			if (MoneyAPI.getMoney(p) >= 21000){
				MoneyAPI.removeMoney(p, 21000);
				ItemStack item = new ItemStack(Material.BOW);
				p.getInventory().addItem(item);
				p.updateInventory();
				p.closeInventory();
			}
			break;
		case SWORD:
			if (MoneyAPI.getMoney(p) >= 30000){
				MoneyAPI.removeMoney(p, 30000);
				ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
				p.getInventory().addItem(item);
				p.updateInventory();
				p.closeInventory();
			}
			break;
		}
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Weaponsmith");
		p.openInventory(createMenu(inv, p));
		inMenu.add(p.getName());
	}
	
	public ItemStack getItemFor(Player p){
		List<String> lore = new ArrayList<String>();
		switch(this){
		case ARROWS:
			lore.add("  ");
			lore.add(ChatColor.GRAY + "Click to purchase 32 arrows.");
			if (MoneyAPI.getMoney(p) >= 6000){
				lore.add(ChatColor.GRAY + "Cost:" + ChatColor.GREEN + " 6000$");
			}else{
				lore.add(ChatColor.GRAY + "Cost:" + ChatColor.RED +  " 6000$");
			}
			return ItemAPI.getItem(Material.ARROW, ChatColor.BLUE + "Arrows", lore);
		case BOW:
			lore.add("  ");
			lore.add(ChatColor.GRAY + "Click to purchase a bow.");
			if (MoneyAPI.getMoney(p) >= 21000){
				lore.add(ChatColor.GRAY + "Cost:" + ChatColor.GREEN + " 21000$");
			}else{
				lore.add(ChatColor.GRAY + "Cost:" + ChatColor.RED + " 21000$");
			}
			return ItemAPI.getItem(Material.BOW, ChatColor.BLUE + "Bow", lore);
		case SWORD:
			lore.add("  ");
			lore.add(ChatColor.GRAY + "Click to purchase a diamond sword.");
			if (MoneyAPI.getMoney(p) >= 30000){
				lore.add(ChatColor.GRAY + "Cost:" + ChatColor.GREEN + " 30000$");
			}else{
				lore.add(ChatColor.GRAY + "Cost:" + ChatColor.RED + " 30000$");
			}
			return ItemAPI.getItem(Material.DIAMOND_SWORD, ChatColor.BLUE + "Diamond Sword", lore);
		}
		return new ItemStack(Material.AIR);
		}
}
