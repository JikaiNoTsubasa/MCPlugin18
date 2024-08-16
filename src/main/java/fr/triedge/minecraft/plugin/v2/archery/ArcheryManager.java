package fr.triedge.minecraft.plugin.v2.archery;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.gson.JsonIOException;

import fr.triedge.minecraft.plugin.v2.MCPlugin20;
import fr.triedge.minecraft.plugin.v2.custom.Custom;
import fr.triedge.minecraft.plugin.v2.exceptions.MCLoadingException;
import fr.triedge.minecraft.plugin.v2.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class ArcheryManager implements Listener{
	
	private MCPlugin20 plugin;
	private ArcheryDataList archeryDataList = new ArcheryDataList();
	
	public ArcheryManager(MCPlugin20 plugin) {
		setPlugin(plugin);
	}

	@EventHandler
	public void onEntityShootBowEvent(EntityShootBowEvent event) {
		//getPlugin().getLogger().log(Level.INFO, "Start shooting bow");
		if (event.getEntity() instanceof Player) {
			Player player = (Player)event.getEntity();
			//getPlugin().getLogger().log(Level.INFO, "Shooting bow for player: "+player.getName());
			
			if (event.getBow() != null && event.getBow().getItemMeta()!=null && event.getBow().getItemMeta().getDisplayName() != null) {
				String name = event.getBow().getItemMeta().getDisplayName();
				//getPlugin().getLogger().log(Level.INFO, "Bow name is: "+name);
				event.getProjectile().setMetadata("player", new FixedMetadataValue(plugin, player.getName()));
				event.getProjectile().setMetadata("bow", new FixedMetadataValue(plugin, name));
				
				if (name.equals(Custom.IMP_FIRE_BOW)) {
					event.getProjectile().setFireTicks(200);
				}
				
				if (name.equals(Custom.IMP_ICE_BOW)) {
					event.getProjectile().setFreezeTicks(200);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow)event.getDamager();
			if (arrow.hasMetadata("player")) {
				String playerName = arrow.getMetadata("player").get(0).asString();
				
				//Player player = Utils.getPlayer(playerName);
				
				if (arrow.hasMetadata("bow")) {
					String bowName = arrow.getMetadata("bow").get(0).asString();
					
					float dmg = getBowDamage(playerName, bowName);
					event.setDamage(dmg);
					increaseBowXp(playerName, bowName);
				}
			}
		}
	}
	
	private float getBowDamage(String player, String bow) {
		float dmg = 1;
		ArcheryData data = getArcheryDataList().getDataForPlayer(player);
		if (bow.equals(Custom.IMP_COPPER_BOW)) {
			int bowLevel = data.getCopperLevel();
			dmg = Utils.getCopperBowDamage(bowLevel);
		}else if (bow.equals(Custom.IMP_FIRE_BOW)) {
			dmg = Utils.getFireBowDamage(data.getFireBowLevel());
		}
		else if (bow.equals(Custom.IMP_ICE_BOW)) {
			dmg = Utils.getIceBowDamage(data.getIceBowLevel());
		}
		return dmg;
	}
	
	private void increaseBowXp(String player, String bow) {
		ArcheryData data = getArcheryDataList().getDataForPlayer(player);
		if (bow.equals(Custom.IMP_COPPER_BOW)) {
			int xp = data.getCopperXp()+1;
			float nextXp = Utils.getCopperBowRequiredXp(data.getCopperLevel());
			if (xp >= nextXp) {
				data.setCopperXp(0);
				data.setCopperLevel(data.getCopperLevel()+1);
				Player p = Utils.getPlayer(player);
				p.sendMessage(ChatColor.GREEN+bow+" level: "+data.getCopperLevel());
			}else {
				data.setCopperXp(xp);
			}
		}else if (bow.equals(Custom.IMP_FIRE_BOW)) {
			int xp = data.getFireBowXp()+1;
			float nextXp = Utils.getFireBowRequiredXp(data.getFireBowLevel());
			if (xp >= nextXp) {
				data.setFireBowXp(0);
				data.setFireBowLevel(data.getFireBowLevel()+1);
				Player p = Utils.getPlayer(player);
				p.sendMessage(ChatColor.GREEN+bow+" level: "+data.getFireBowLevel());
			}else {
				data.setFireBowXp(xp);
			}
		}else if (bow.equals(Custom.IMP_ICE_BOW)) {
			int xp = data.getIceBowXp()+1;
			float nextXp = Utils.getIceBowRequiredXp(data.getIceBowLevel());
			if (xp >= nextXp) {
				data.setIceBowXp(0);
				data.setIceBowLevel(data.getIceBowLevel()+1);
				Player p = Utils.getPlayer(player);
				p.sendMessage(ChatColor.GREEN+bow+" level: "+data.getIceBowLevel());
			}else {
				data.setIceBowXp(xp);
			}
		}
	}
	
	public void loadArchery(String path) throws JsonIOException, IOException {
		getPlugin().getLogger().log(Level.INFO,"Loading archery from file "+path+"...");
		File file = new File(path);
		if (file.exists()) {
			try {
				ArcheryDataList list = Utils.loadJson(ArcheryDataList.class, file);
				if (list == null) {
					getPlugin().getLogger().log(Level.SEVERE, "ArcheryData loaded list is null!");
					throw new MCLoadingException("ArcheryData loaded list is null");
				}
				getPlugin().getLogger().log(Level.INFO,"Archery loaded");
				setArcheryDataList(list);
			} catch (Exception e) {
				getPlugin().getLogger().log(Level.SEVERE, "Cannot load config file: "+file.getAbsolutePath(), e);
			}
		}else {
			getPlugin().getLogger().log(Level.WARNING, "Config file doesn't exists, created empty in "+file.getAbsolutePath());
			file.getParentFile().mkdirs();
			save(path);
		}
	}
	
	public void save(String path) throws JsonIOException, IOException {
		getPlugin().getLogger().log(Level.INFO,"Storing archerydata into "+path+"...");
		Utils.storeJson(getArcheryDataList(), new File(path));
		getPlugin().getLogger().log(Level.INFO,"ArcheryData stored");
	}

	public MCPlugin20 getPlugin() {
		return plugin;
	}

	public void setPlugin(MCPlugin20 plugin) {
		this.plugin = plugin;
	}

	public ArcheryDataList getArcheryDataList() {
		return archeryDataList;
	}

	public void setArcheryDataList(ArcheryDataList archeryDataList) {
		this.archeryDataList = archeryDataList;
	}
	
	
}
