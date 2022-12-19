package fr.triedge.minecraft.plugin.v2.magic;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.MetadataValue;

import com.google.gson.JsonIOException;

import fr.triedge.minecraft.plugin.v2.MCPlugin19;
import fr.triedge.minecraft.plugin.v2.custom.Custom;
import fr.triedge.minecraft.plugin.v2.exceptions.MCLoadingException;
import fr.triedge.minecraft.plugin.v2.utils.Utils;


public class MagicManager implements Listener{
	
	private MCPlugin19 plugin;
	private SpellDataList spellDataList = new SpellDataList();
	
	public MagicManager(MCPlugin19 plugin) {
		setPlugin(plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event == null)
			return;

		// Executes for main hand
		if (event.getHand() != null && event.getHand().equals(EquipmentSlot.HAND) && event.getPlayer().getInventory().getItemInMainHand() != null) {
			if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null && event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName() != null)
				onPlayerUseCustomItem(event, event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName());
		}

	}
	
	private void onPlayerUseCustomItem(PlayerInteractEvent event, String name) {
		if (name.equals(Custom.WAND_SNOW) && event.getAction() == Action.LEFT_CLICK_AIR) {
			Spell spell = new SpellSnowBall(event.getPlayer(), getSpellDataList(), getPlugin());
			spell.launch();
		// FireBall
		}else if (name.equals(Custom.WAND_FIRE) && event.getAction() == Action.LEFT_CLICK_AIR) {
			Spell spell = new SpellFireBall(event.getPlayer(), getSpellDataList(), getPlugin());
			spell.launch();
		}

	}
	
	private void setDamageForSpell(Player player, EntityDamageByEntityEvent event, Spell spell) {
		if (player != null) {
			if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {
				event.setDamage(spell.getDamage());
				//int xp = spell.addXP();
				//player.sendMessage("XP "+spell.getDisplayName()+": "+xp);
				getPlugin().getLogger().info(player.getName()+" deals "+spell.getDamage()+" damages to "+event.getEntity().getName());
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (event == null)
			return;

		// skills damage
		if (event.getDamager() instanceof Snowball) {
			Snowball snow = (Snowball)event.getDamager();
			List<MetadataValue> list_val = snow.getMetadata("player");
			if (list_val != null && !list_val.isEmpty()) {
				String playerName = list_val.get(0).asString();
				Player player = Utils.getPlayer(playerName);
				if (getSpellDataList() == null) {
					getPlugin().getLogger().info("/!\\ cfgMagic config is NULL");
					return;
				}
				getPlugin().getLogger().info("Snow skill touched, sent by player: "+player.getName());
				setDamageForSpell(player, event, new SpellSnowBall(player, getSpellDataList(), getPlugin()));
			}
		}else if (event.getDamager() instanceof SmallFireball) {
			SmallFireball fire = (SmallFireball)event.getDamager();
			List<MetadataValue> list_val = fire.getMetadata("player");
			if (list_val != null && !list_val.isEmpty()) {
				String playerName = list_val.get(0).asString();
				Player player = Utils.getPlayer(playerName);
				if (getSpellDataList() == null) {
					getPlugin().getLogger().info("/!\\ cfgMagic config is NULL");
					return;
				}
				getPlugin().getLogger().info("Fire skill touched, sent by player: "+player.getName());
				setDamageForSpell(player, event, new SpellFireBall(player, getSpellDataList(), getPlugin()));
			}
		}else if (event.getDamager() instanceof Arrow) {
			Arrow ar = (Arrow)event.getDamager();
			List<MetadataValue> list_val = ar.getMetadata("player");
			if (list_val != null && !list_val.isEmpty()) {
				
			}
		}
	}
	
	public void loadMagic(String path) throws MCLoadingException, JsonIOException, IOException {
		getPlugin().getLogger().log(Level.INFO,"Loading magic from file "+path+"...");
		File file = new File(path);
		if (file.exists()) {
			try {
				SpellDataList list = Utils.loadJson(SpellDataList.class, file);
				if (list == null) {
					getPlugin().getLogger().log(Level.SEVERE, "SpellData loaded list is null!");
					throw new MCLoadingException("SpellData loaded list is null");
				}
				getPlugin().getLogger().log(Level.INFO,"Speels loaded");
				setSpellDataList(list);
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
		getPlugin().getLogger().log(Level.INFO,"Storing spelldata into "+path+"...");
		Utils.storeJson(getSpellDataList(), new File(path));
		getPlugin().getLogger().log(Level.INFO,"SpellData stored");
	}
	

	public MCPlugin19 getPlugin() {
		return plugin;
	}

	public void setPlugin(MCPlugin19 plugin) {
		this.plugin = plugin;
	}

	public SpellDataList getSpellDataList() {
		return spellDataList;
	}

	public void setSpellDataList(SpellDataList spellDataList) {
		this.spellDataList = spellDataList;
	}
}
