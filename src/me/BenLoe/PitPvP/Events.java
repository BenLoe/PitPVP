package me.BenLoe.PitPvP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.Prison.Main.Currency.MoneyAPI;
import org.Prison.Main.Ranks.RankType;
import org.Prison.Main.Traits.SpeedTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;


public class Events implements Listener {
	
	public static HashMap<String,String> lastdamager = new HashMap<String,String>();
	public static List<String> teleporting = new ArrayList<String>();
	
	public static Main plugin;
	public Events(Main instance){
		plugin = instance;
	}
	
	@EventHandler
	public void playermove(PlayerMoveEvent event){
		Player p = event.getPlayer();
		if (p.getWorld().getName().equals("PVP")){
			if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ())
			if (teleporting.contains(p.getName())){
				teleporting.remove(p.getName());
				p.sendMessage(ChatColor.RED + "You moved so the teleportation got cancelled.");
				}
		}
	}
	
	@EventHandler (ignoreCancelled = true)
	public void playerDamage(EntityDamageByEntityEvent event){
		if (event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if (p.getWorld().getName().equals("PVP")){
			if (event.getDamager() instanceof Player){
				if (teleporting.contains(p.getName())){
					teleporting.remove(p.getName());
					p.sendMessage(ChatColor.RED + "You were damaged so the teleportation got cancelled.");
					}
				Player damager = (Player) event.getDamager();
				if (!(damager.getLocation().getBlockY() > 80) && p.getWorld().getName() == "PVP"){
				CombatLog.setInCombat(p);
				CombatLog.setInCombat(damager);
				double damage = event.getDamage(DamageModifier.BASE) + event.getDamage(DamageModifier.ARMOR) + event.getDamage(DamageModifier.BLOCKING);
				lastdamager.put(p.getName(), damager.getName());
				if (p.getHealth() - damage  < 1){
					event.setCancelled(true);
					if (damager.getInventory().getItemInHand() != null){
					die(p, damager, true);
					}else{
						die (p, damager, false);
					}
				}
				}else{
					event.setCancelled(true);
				}
			}
		}
			if (event.getDamager() instanceof Projectile){
				if (!(event.getDamager() instanceof Snowball)){
				Projectile a = (Arrow) event.getDamager();
				Player damager = (Player) a.getShooter();
				if (teleporting.contains(p.getName())){
					teleporting.remove(p.getName());
					p.sendMessage(ChatColor.RED + "You were damaged so the teleportation got cancelled.");
					}
				if (!(damager.getLocation().getBlockY() > 80)){
				CombatLog.setInCombat(p);
				CombatLog.setInCombat(damager);
				double damage = event.getDamage(DamageModifier.BASE) + event.getDamage(DamageModifier.ARMOR) + event.getDamage(DamageModifier.BLOCKING);
				lastdamager.put(p.getName(), damager.getName());
				if (p.getHealth() - damage  < 1){
					event.setCancelled(true);
					die(p, damager, true);
				}
				}else{
					event.setCancelled(true);
				}
				}
			}
		}
	}
	
	@EventHandler (ignoreCancelled = true)
	public void playerDamage(EntityDamageEvent event){
		if (event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if (p.getWorld().getName().equals("PVP")){
			if (event.getCause().equals(DamageCause.FALL)){
				event.setCancelled(true);
			}
			if (event.getCause().equals(DamageCause.FIRE_TICK)){
				if (teleporting.contains(p.getName())){
				teleporting.remove(p.getName());
				p.sendMessage(ChatColor.RED + "You were damaged so the teleportation got cancelled.");
				}
				if (p.getHealth() - event.getDamage() < 1){
					die(p, Bukkit.getPlayer(lastdamager.get(p.getName())), false);
				}
			}
		}
	}
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if (p.getWorld().getName().equals("PVP")){
			if (p.getInventory().getItemInHand() != null){
				if (p.getInventory().getItemInHand().getType().equals(Material.MUSHROOM_SOUP)){
					if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
						Location loc = p.getLocation().add(0, 1.2, 0).toVector().add(p.getLocation().add(0, 2, 0).getDirection().multiply(0.8)).toLocation(p.getWorld());
						ParticleEffect.ITEM_CRACK.display(new ParticleEffect.ItemData(Material.MUSHROOM_SOUP, (byte)0) {}, 0.2f, 0.2f, 0.2f, 0.001f, 60, loc.add(0, 0.3, 0), 2);
						event.setCancelled(true);
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 2));
						p.playSound(p.getLocation(), Sound.DIG_GRASS, 1f, 1f);
						p.getInventory().setItem(p.getInventory().getHeldItemSlot(), null);
						p.updateInventory();
					}
				}
			}
		}
		if (event.getAction().equals(Action.PHYSICAL)){
			if (p.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.GOLD_BLOCK)){
				p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1f, 1f);
				p.teleport(p.getLocation().add(0, 0.5, 0));
				p.setVelocity(p.getLocation().getDirection().multiply(7).setY(0.8));
			}
		}
	}
	
	public static void die(Player p, Player damager, boolean displayName){
		Location loc = p.getLocation();
		p.damage(0.0);
		p.teleport(new Location(Bukkit.getWorld("PVP"), -117, 94, -845));
		ParticleEffect.BLOCK_CRACK.display(new ParticleEffect.BlockData(Material.REDSTONE_BLOCK, (byte)0), 0.5f, 1.2f, 0.5f, 0.001f, 250, loc.add(0, 0.15, 0), 16);
		
		damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1f, 1f);
		
		Firework(loc, 0);
		Firework(loc, 1);
		Firework(loc, 2);
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 2));
		Title death = new Title("You Died");
		death.setFadeInTime(1);
		death.setFadeOutTime(1);
		death.setStayTime(2);
		death.setTitleColor(ChatColor.RED);
		death.send(p);
		p.playSound(p.getLocation(), Sound.ENDERMAN_DEATH, 1f, 1f);
		p.setHealth(20.0);
		Die.itemsFly(loc);
		Die.die(p, damager, loc, displayName);
	}
	
	@EventHandler
	public void changeWorld(PlayerChangedWorldEvent event){
		Player p = event.getPlayer();
		if (p.getWorld().getName().equals("PVP")){
			InfoBoard.setBoard(p);
			if (RankType.getRank(p).equals(RankType.ELITE)){
				p.setAllowFlight(false);
			}
			p.setWalkSpeed(0.20f);
		}else{
			SpeedTrait.setCorrectSpeed(p);
			org.Prison.Main.InfoBoard.InfoBoard.setBoardFor(p);
		}
	}
	
	
	@EventHandler
	public void leaveEvent(PlayerQuitEvent event){
		Player p = event.getPlayer();
		if (CombatLog.inCombat(p)){
			CombatLog.leftInCombat(p);
		}
	}
	
	@EventHandler
	public void enchant(EnchantItemEvent event){
		Player p = event.getEnchanter();
		if (event.getExpLevelCost() == 30){
			if (RankType.getRank(p).equals(RankType.NONE) || RankType.getRank(p).equals(RankType.VIP)){
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You must be a §a§lELITE §cor above to enchant level 30.");
			}
		}else
		if (event.getExpLevelCost() >= 25){
			if (RankType.getRank(p).equals(RankType.NONE)){
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You must be a §3§lVIP §cor above to enchant level 25 and above.");
			}
		}
	}
	
	
	public static void Firework(Location loc, int power){
		Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
		FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(false).withColor(Color.AQUA, Color.WHITE).with(Type.BALL_LARGE).build();
		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.clearEffects();
		fwm.setPower(power);
		fwm.addEffect(effect);
		fw.setFireworkMeta(fwm);
		}
	
	@EventHandler
	public void command(PlayerCommandPreprocessEvent event){
		final Player p = event.getPlayer();
		if (event.getMessage().equalsIgnoreCase("/spawn")){
			if (p.getWorld().getName().equals("PVP")){
			event.setCancelled(true);
			p.sendMessage(ChatColor.GREEN + "Stand still and don't get damaged for" + ChatColor.AQUA + " 3 " + ChatColor.GREEN + "seconds.");
			teleporting.add(p.getName());
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable(){
				public void run(){
					if (teleporting.contains(p.getName())){
						p.teleport(org.Prison.Main.Main.getLocation("PvPSpawn"));
						teleporting.remove(p.getName());
					}
				}
			}, 3 * 20l);
			}
		}
	}
	
	@EventHandler
	public void npcClick(NPCRightClickEvent event){
		int id = event.getNPC().getId();
		if (Files.config().getInt("ArrowID") == id){
			ArrowTrail.openMenu(event.getClicker());
		}
		if (Files.config().getInt("WeaponID") == id){
			Weaponsmith.openMenu(event.getClicker());
		}
	}
	
	@EventHandler
	public void inventoryClose(InventoryCloseEvent event){
		if (ArrowTrail.inMenu.contains(event.getPlayer().getName())){
			ArrowTrail.inMenu.remove(event.getPlayer().getName());
		}
		if (Weaponsmith.inMenu.contains(event.getPlayer().getName())){
			Weaponsmith.inMenu.remove(event.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void inventoryChange(InventoryClickEvent event){
		if (ArrowTrail.inMenu.contains(event.getWhoClicked().getName())){
			if (ArrowTrail.wasAItem(event.getRawSlot())){
				event.setCancelled(true);
				ArrowTrail.getItemFromSlot(event.getRawSlot()).wasClicked((Player) event.getWhoClicked());
			}
		}
		if (Weaponsmith.inMenu.contains(event.getWhoClicked().getName())){
			if (Weaponsmith.wasAItem(event.getRawSlot())){
				event.setCancelled(true);
				Weaponsmith.getItemFromSlot(event.getRawSlot()).wasClicked((Player) event.getWhoClicked());
			}
		}
	}
	
	@EventHandler
	public void projectileLaunch(ProjectileLaunchEvent event){
		if (event.getEntity().getShooter() instanceof Player){
			Player p = (Player) event.getEntity().getShooter();
			if (p.getWorld().getName().equals("PVP")){
				if (event.getEntity() instanceof Arrow){
					if (!ArrowTrail.getTrail(p).equals(ArrowTrail.NONE)){
						ArrowTrail.arrows.put((Arrow) event.getEntity(), ArrowTrail.getTrail(p));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void projectileLand(ProjectileHitEvent event){
		if (ArrowTrail.arrows.containsKey(event.getEntity())){
			ArrowTrail.arrows.remove(event.getEntity());
		}
	}
}
