package fr.triedge.minecraft.plugin.v2.warp;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.google.gson.JsonIOException;

import fr.triedge.minecraft.plugin.v2.MCPlugin19;
import fr.triedge.minecraft.plugin.v2.exceptions.MCLoadingException;
import fr.triedge.minecraft.plugin.v2.utils.Utils;

public class WarpManager implements Listener{

	private MCPlugin19 plugin;
	private WarpList warpList = new WarpList();

	public WarpManager(MCPlugin19 plugin) {
		super();
		this.setPlugin(plugin);
	}

	public void onWarpCommand(Player player, String[] args) {
		if (args.length > 0) {
			switch(args[0]) {
			case "create":
				actionCreateWarp(player,args);
				break;
			case "delete":
				actionDeleteWarp(player,args);
				break;
			case "list":
				actionListWarp(player,args);
				break;
			case "setgroup":
				actionSetGroup(player,args);
				break;
			case "removegroup":
				actionRemoveGroup(player,args);
				break;
			}
		}else {
			// Display help
			player.sendMessage(ChatColor.RED+"Il manque un paramètre!");
		}
	}

	public void onWarpGroupCommand(Player player, String[] args) {
		if (args.length > 0) {
			switch(args[0]) {
			case "create":
				actionCreateGroup(player,args);
				break;
			case "delete":
				actionDeleteGroup(player,args);
				break;
			case "add":
				actionAddToGroup(player,args);
				break;
			case "remove":
				actionRemoveFromGroup(player,args);
				break;
			case "list":
				actionListGroup(player,args);
				break;
			}
		}else {
			// Display help
			player.sendMessage(ChatColor.RED+"Il manque un paramètre!");
		}
	}

	private void actionRemoveFromGroup(Player player, String[] args) {
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED+"Il manque des parametres");
			return;
		}
		String groupName = args[1];
		String playerName = args[2];
		WarpGroup group = getWarpList().getGroup(groupName);
		if (group == null) {
			player.sendMessage(ChatColor.RED+"Ce groupe n'existe pas!");
			return;
		}
		if (!group.isAllowed(playerName)) {
			player.sendMessage(ChatColor.RED+"Ce joueur n'est pas dans ce groupe!");
			return;
		}
		Iterator<String> it = group.getAllowed().iterator();
		while(it.hasNext()) {
			String name = it.next();
			if (name.equals(playerName)) {
				it.remove();
				player.sendMessage(ChatColor.GREEN+"Le joueur "+playerName+" a été supprimé du groupe "+groupName);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event == null)
			return;

		// Right click block
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			// If WALL Sign
			Block block = event.getClickedBlock();
			if (block != null && (block.getType() == Material.ACACIA_WALL_SIGN || block.getType() == Material.BIRCH_WALL_SIGN || block.getType() == Material.DARK_OAK_WALL_SIGN || block.getType() == Material.JUNGLE_WALL_SIGN || block.getType() == Material.OAK_WALL_SIGN || block.getType() == Material.SPRUCE_WALL_SIGN))
			{
				Player player = event.getPlayer();
				String[] lines = readSign(block);
				if (lines != null) {
					parseSign(lines[0], player);
				}
			}

		}
	}

	private String[] readSign(Block block) {
		Sign sign = (Sign) block.getState();
		String[] lines = sign.getLines();
		if (lines.length != 0)
			return lines;
		return null;
	}

	private void parseSign(String line, Player player) {
		String[] sp = line.split(":");
		if (sp[0].equalsIgnoreCase("TP")) {
			actionTP(sp[1],player);
		}
	}

	private void actionTP(String name, Player player) {
		if (name == null || name == "") {
			return;
		}
		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		getPlugin().getLogger().info("### Action TP ###");
		getPlugin().getLogger().info("# "+player.getName());

		if (block.getType().equals(Material.DIAMOND_BLOCK)) {
			warpTo(player, name);
		}else {
			player.sendMessage("Vous devez etre sur un block de diamant pour cette commande.");
		}
	}

	public void warpTo(Player player, String target) {
		if (doesWarpExist(target)) {
			Warp warp = getWarpList().getWarp(target);
			String groupName = warp.getGroup();
			if (!groupName.equals("none")) {
				WarpGroup group = getWarpList().getGroup(groupName);
				if (!group.isAllowed(player.getName())) {
					player.sendMessage(ChatColor.RED+"Vous n'êtes pas authorisé à utiliser ce TP!");
					return;
				}
			}
			// Get teleport target
			String world_str = warp.getWorld();
			//String group = warp.group;

			float pitch = warp.getPitch();
			float yaw = warp.getYaw();

			World world = Bukkit.getWorld(world_str);
			world.playEffect(player.getLocation(), Effect.SMOKE, 10);
			Location target_loc = new Location(world, warp.getLocationX(),warp.getLocationY(),warp.getLocationZ());
			target_loc.setPitch(pitch);
			target_loc.setYaw(yaw);

			Block block = target_loc.getBlock().getRelative(BlockFace.DOWN);
			if (block != null && block.getType() == Material.DIAMOND_BLOCK) {
				player.teleport(target_loc);
				world.playEffect(player.getLocation(), Effect.SMOKE, 10);
				getPlugin().getLogger().info("# "+player.getName()+" warped to "+target);
			}else {
				player.sendMessage(ChatColor.RED+"La destination n'est pas un block de diamant");
			}

		}else {
			player.sendMessage(ChatColor.RED+"La destination n'existe pas!");
		}
	}

	private boolean doesWarpExist(String name) {
		Warp warp = getWarpList().getWarp(name);
		return warp!=null;
	}

	private void actionRemoveGroup(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED+"Il manque des parametres");
			return;
		}
		Warp warp = getWarpList().getWarp(args[1]);
		if (warp == null) {
			player.sendMessage(ChatColor.RED+"Ce TP n'existe pas!");
			return;
		}
		warp.setGroup("none");
		player.sendMessage(ChatColor.GREEN+"Le TP "+warp.getName()+" est maintenant ouvert à tous");
	}

	private void actionSetGroup(Player player, String[] args) {
		if (args.length >= 3) {
			String groupName = args[2];
			String warpName = args[1];
			WarpGroup group = getWarpList().getGroup(groupName);
			if (group == null) {
				player.sendMessage(ChatColor.RED+"Ce groupe n'existe pas!");
				return;
			}
			Warp warp = getWarpList().getWarp(warpName);
			if (warp == null) {
				player.sendMessage(ChatColor.RED+"Ce TP n'existe pas!");
				return;
			}
			warp.setGroup(group.getName());
			player.sendMessage(ChatColor.GREEN+"Le TP "+warpName+" est maintenant lié au groupe "+groupName);
		}else {
			player.sendMessage(ChatColor.RED+"Il manque des parametres");
		}
	}

	private void actionAddToGroup(Player player, String[] args) {
		if (args.length < 3) {
			player.sendMessage(ChatColor.RED+"Il manque des parametres");
			return;
		}
		String groupName = args[1];
		String playerName = args[2];
		WarpGroup group = getWarpList().getGroup(groupName);
		if (group == null) {
			player.sendMessage(ChatColor.RED+"Ce groupe n'existe pas!");
		}
		group.getAllowed().add(playerName);
		player.sendMessage(ChatColor.GREEN+""+playerName+" ajouté au groupe "+groupName);
	}

	private void actionListGroup(Player player, String[] args) {
		if (args.length > 1) {
			String groupeName = args[1];
			WarpGroup group = getWarpList().getGroup(groupeName);
			if (group == null) {
				player.sendMessage(ChatColor.RED+"Ce groupe n'existe pas!");
				return;
			}
			player.sendMessage(ChatColor.GOLD+groupeName+":");
			if (group.getAllowed().isEmpty())
				player.sendMessage("Ce groupe est vide");
			else {
				for (String playerName : group.getAllowed())
					player.sendMessage("-> "+playerName);

			}
		}else {
			if (getWarpList().getWarpGroups().isEmpty()) {
				player.sendMessage("Liste vide!");
				return;
			}
			StringBuilder tmp = new StringBuilder();
			for (WarpGroup warp : getWarpList().getWarpGroups()) {
				String name = warp.getName();
				if (!name.startsWith("h_")) {
					tmp.append(name);
					tmp.append(", ");
				}
			}
			player.sendMessage(tmp.toString());

		}
	}

	private void actionCreateGroup(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED+"Il manque le nom du GROUP!");
			return;
		}
		String name = args[1];
		WarpGroup group = new WarpGroup(name);
		getWarpList().addWarGroup(group);
		player.sendMessage(ChatColor.GREEN+"Group "+name+" créé");

	}

	private void actionDeleteGroup(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED+"Il manque le nom du GROUP!");
			return;
		}
		String name = args[1];
		WarpGroup group = getWarpList().getGroup(name);
		if (group == null) {
			player.sendMessage(ChatColor.RED+"Ce groupe n'existe pas!");
			return;
		}
		getWarpList().getWarpGroups().remove(group);
		player.sendMessage(ChatColor.GREEN+"Group "+name+" supprimé");
	}

	private void actionDeleteWarp(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED+"Il manque le nom du TP!");
			return;
		}
		String name = args[1];
		Warp warp = getWarpList().getWarp(name);
		if (warp == null) {
			player.sendMessage(ChatColor.RED+"TP non trouvé");
			return;
		}

		getWarpList().getWarps().remove(warp);
		player.sendMessage(ChatColor.GREEN+"TP "+name+" supprimé");
	}

	private void actionCreateWarp(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage(ChatColor.RED+"Il manque le nom du TP!");
			return;
		}
		String groupName = "none";
		if (args.length == 3) {
			WarpGroup tmp = getWarpList().getGroup(args[2]);
			if (tmp != null)
				groupName = tmp.getName();
			else
				player.sendMessage(ChatColor.RED+"Le group "+args[2]+" n'existe pas!");
		}
		String name = args[1];
		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

		if (block.getType().equals(Material.DIAMOND_BLOCK)) {
			Vector vector = player.getLocation().toVector();
			String world = player.getWorld().getName();
			float pitch = player.getLocation().getPitch();
			float yaw = player.getLocation().getYaw();
			Warp warp = new Warp(name);
			warp.setWorld(world);
			warp.setLocationX(vector.getBlockX());
			warp.setLocationY(vector.getBlockY());
			warp.setLocationZ(vector.getBlockZ());
			warp.setPitch(pitch);
			warp.setYaw(yaw);
			warp.setGroup(groupName);
			getWarpList().addWarp(warp);

			player.sendMessage(ChatColor.GREEN+"Cible de teleportation sauvegardé: "+name);
			getPlugin().getLogger().info("# REGISTER TP: "+name+"["+vector.getBlockX()+"/"+vector.getBlockY()+"/"+vector.getBlockZ()+"]");
		}else {
			player.sendMessage("Vous devez etre sur un block de diamant pour cette commande.");
		}

	}

	private void actionListWarp(Player player, String[] args) {
		if (getWarpList().getWarps().isEmpty()) {
			player.sendMessage("Liste vide!");
			return;
		}
		StringBuilder tmp = new StringBuilder();
		for (Warp warp : getWarpList().getWarps()) {
			String name = warp.getName();
			if (!name.startsWith("h_")) {
				tmp.append(name);
				tmp.append("[");
				tmp.append(warp.getGroup());
				tmp.append("]");
				tmp.append(", ");
			}
		}
		player.sendMessage(tmp.toString());
	}

	public void save(String path) throws JsonIOException, IOException {
		getPlugin().getLogger().log(Level.INFO,"Storing warps into "+path+"...");
		Utils.storeJson(getWarpList(), new File(path));
		getPlugin().getLogger().log(Level.INFO,"Warps stored");
	}

	public void loadWarps(String path) throws MCLoadingException, JsonIOException, IOException {
		getPlugin().getLogger().log(Level.INFO,"Loading warps from file "+path+"...");
		File file = new File(path);
		if (file.exists()) {
			try {
				WarpList list = Utils.loadJson(WarpList.class, file);
				if (list == null) {
					getPlugin().getLogger().log(Level.SEVERE, "Warp loaded list is null!");
					throw new MCLoadingException("Warp loaded list is null");
				}
				getPlugin().getLogger().log(Level.INFO,"Warps loaded");
				setWarpList(list);
			} catch (Exception e) {
				getPlugin().getLogger().log(Level.SEVERE, "Cannot load config file: "+file.getAbsolutePath(), e);
			}

		}else {
			getPlugin().getLogger().log(Level.WARNING, "Config file doesn't exists, created empty in "+file.getAbsolutePath());
			file.getParentFile().mkdirs();
			save(path);
		}
	}

	public WarpList getWarpList() {
		return warpList;
	}

	public void setWarpList(WarpList warpList) {
		this.warpList = warpList;
	}

	public MCPlugin19 getPlugin() {
		return plugin;
	}

	public void setPlugin(MCPlugin19 plugin) {
		this.plugin = plugin;
	}
}
