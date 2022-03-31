package fr.triedge.minecraft.plugin.v2.inventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonIOException;

import fr.triedge.minecraft.plugin.v2.MCPlugin18;
import fr.triedge.minecraft.plugin.v2.exceptions.MCLoadingException;
import fr.triedge.minecraft.plugin.v2.utils.Utils;

public class InventoryManager implements Listener{

	private MCPlugin18 plugin;
	private HashMap<String, Inventory> internalInv = new HashMap<>();
	
	public InventoryManager(MCPlugin18 plugin) {
		setPlugin(plugin);
	}
	
	public void onInvCommand(Player player, String[] args) {
		if (args.length > 0) {
			if (Utils.isInteger(args[0])) {
				int id = Integer.parseInt(args[0]);
				openInventory(player, id);
			}else {
				switch(args[0]) {
				case "list": 
					actionListInventories(player);
					break;
				case "share":
					actionSharedInventory(player);
					break;
				}
			}
		}else {
			openInventory(player, 0);
		}
		
	}

	private void actionSharedInventory(Player player) {
		Inventory inv = getInventory("share", 0);
		if (inv == null) {
			// Create Custom Inventory if not exist
			inv = createEmptyInventory(player, 0);
			getInternalInv().put("share-0", inv);
			player.sendMessage(ChatColor.RED+"Inventaire partagé créé");
			Utils.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL);
		}
		player.openInventory(inv);
		Utils.playSound(player, Sound.BLOCK_CHEST_OPEN);
	}

	private void actionListInventories(Player player) {
		for (Entry<String, Inventory> e : getInternalInv().entrySet()) {
			String name = e.getKey().split("-")[0];
			String id = e.getKey().split("-")[1];
			if (name.equals(player.getName())) {
				player.sendMessage("-> "+id);
			}
		}
	}

	private void openInventory(Player player, int id) {
		Inventory inv = getInventory(player, id);
		if (inv == null) {
			player.sendMessage(ChatColor.RED+"Aucun inventaire disponible pour l'id: "+id);
			// Create Custom Inventory if not exist
			inv = createEmptyInventory(player, id);
			getInternalInv().put(player.getName()+"-"+id, inv);
			player.sendMessage(ChatColor.RED+"Inventaire créé pour l'id: "+id);
			Utils.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL);
		}
		player.openInventory(inv);
		Utils.playSound(player, Sound.BLOCK_CHEST_OPEN);
		
	}

	private Inventory getInventory(Player player, int id) {
		return getInventory(player.getName(), id);
	}
	
	private Inventory getInventory(String name, int id) {
		return getInternalInv().get(name+"-"+id);
	}
	
	private Inventory createEmptyInventory(Player player, int id) {
		return Bukkit.createInventory(player, InventoryType.CHEST);
	}
	
	public void save(String path) throws JsonIOException, IOException {
		getPlugin().getLogger().log(Level.INFO,"Storing inventories into "+path+"...");
		InventoryList list = new InventoryList();
		for (Entry<String, Inventory> e : getInternalInv().entrySet()) {
			String key = e.getKey();
			String playerName = key.split("-")[0];
			int id = Integer.parseInt(key.split("-")[1]);
			Inventory inv = e.getValue();
			InventoryData i = new InventoryData(playerName);
			i.setId(id);
			for (ItemStack stack : inv.getContents()) {
				InventoryItem item = new InventoryItem(stack.getType().toString(), stack.getAmount());
				i.getItems().add(item);
			}
			list.getInventories().add(i);
			getPlugin().getLogger().info("Stored data for: "+playerName+"-"+id);
		}
		Utils.storeJson(list, new File(path));
		getPlugin().getLogger().log(Level.INFO,"Inventories stored");
	}
	
	public void loadInventories(String path) throws MCLoadingException {
		getPlugin().getLogger().log(Level.INFO,"Loading inventories from file "+path+"...");
		InventoryList list = null;
		File file = new File(path);
		if (file.exists()) {
			try {
				list = Utils.loadJson(InventoryList.class, file);
				if (list == null) {
					getPlugin().getLogger().log(Level.SEVERE, "Inventory loaded list is null!");
					throw new MCLoadingException("Inventory loaded list is null");
				}
				getPlugin().getLogger().log(Level.INFO,"Inventories loaded");
				// loading into internal inv
				for (InventoryData i : list.getInventories()) {
					String key = i.getPlayerName()+"-"+i.getId();
					Inventory inv = Bukkit.createInventory(Utils.getPlayer(i.getPlayerName()), InventoryType.CHEST);
					ItemStack[] stacks = new ItemStack[i.getItems().size()];
					int idx = 0;
					for (InventoryItem item : i.getItems()) {
						stacks[idx] = new ItemStack(Material.getMaterial(item.getItemType()), item.getCount());
					}
					inv.setContents(stacks);
					getInternalInv().put(key, inv);
					getPlugin().getLogger().info("Loaded inventory "+key);
				}
			} catch (Exception e) {
				getPlugin().getLogger().log(Level.SEVERE, "Cannot load config file: "+file.getAbsolutePath(), e);
			}
		}else {
			getPlugin().getLogger().log(Level.WARNING, "Config file doesn't exists: "+file.getAbsolutePath());
		}
	}

	public MCPlugin18 getPlugin() {
		return plugin;
	}

	public void setPlugin(MCPlugin18 plugin) {
		this.plugin = plugin;
	}

	public HashMap<String, Inventory> getInternalInv() {
		return internalInv;
	}

	public void setInternalInv(HashMap<String, Inventory> internalInv) {
		this.internalInv = internalInv;
	}
}
