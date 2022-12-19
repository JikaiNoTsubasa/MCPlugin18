package fr.triedge.minecraft.plugin.v2;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.gson.JsonIOException;

import fr.triedge.minecraft.plugin.v2.archery.ArcheryData;
import fr.triedge.minecraft.plugin.v2.archery.ArcheryManager;
import fr.triedge.minecraft.plugin.v2.custom.Custom;
import fr.triedge.minecraft.plugin.v2.custom.CustomManager;
import fr.triedge.minecraft.plugin.v2.detector.Detector;
import fr.triedge.minecraft.plugin.v2.exceptions.MCLoadingException;
import fr.triedge.minecraft.plugin.v2.inventory.InventoryManager;
import fr.triedge.minecraft.plugin.v2.magic.MagicManager;
import fr.triedge.minecraft.plugin.v2.magic.SpellData;
import fr.triedge.minecraft.plugin.v2.task.SaveTask;
import fr.triedge.minecraft.plugin.v2.utils.Utils;
import fr.triedge.minecraft.plugin.v2.warp.WarpManager;


/**
 * Plugin features:
 * [x] v1.0 Build teleports [TEST OK]
 * [x] v1.1 New gold/diamond pickaxe with more durability [TEST OK]
 * [x] v1.2 Ultimate stick/bottle [TEST OK]
 * [x] v1.2 Display title at login [TEST OK]
 * [x] v1.2 Snow Wand [TEST OK]
 * [x] v1.2 Get more ore with custom pickaxe [TEST OK]
 * [X] v1.3 Update recipe to allow all LOGs for custom pickaxes
 * [x] v1.3 Creepers drop 1 Emerald [TEST OK]
 * [x] v1.3 Store more items [TO TEST]
 * [x] v1.3 Grenade -> no drop [TO TEST]
 * [x] v1.3 Added popo to prevent inventory drop when death occurs [TO TEST]
 * [x] v1.3 Fire Wand -> no xp [TO TEST]
 * [x] v1.3 Command save inventories / when connect check if inv already loaded / save inv when delogg
 * [x] v1.4 Custom Axe and Shovel GOLD [TO TEST]
 * [ ] v1.4 Bigger jump
 * [x] v1.4 Hidden TP with h_ [TO TEST]
 * [x] v1.4 Spawn mob Pack [TO TEST]
 * [x] v1.16 Detector in different directions
 * [x] v1.16 Added warp command
 * [ ] Custom mob drop emerald (BOSS)
 * [ ] Custom mob spawn (obscurity)
 * [ ] Popo to teleport from anywhere - custom craft
 * [ ] Popo REZ
 * [ ] Popo speed with scheduler
 * [ ] Spawn emerald chests
 *
 * [ ] Add glow effects to detector blocks
 *
 * Bug:
 * [x] v1.2 NullPointer - When break block with no item in hand
 * [x] v1.2 NullPointer - Magic config not loading
 * [x] v1.3 Double Snow Ball - Firing 2 snow balls instead of 1
 * [x] v1.3 Solved a bug about xp given to player when level up Magic
 * [x] v1.3 ULT STICK breaks block but no loot from block
 * [x] v1.3 ULT WATER fill up water in bottle and changes it's name with right click
 * [x] v1.3 Teleport could happen event if destination is not on diamond block
 * [x] v1.16 When several stacks of nuke in inventory, it decreases all stacks when using
 * [x] 20200706.0 v1.16.1 When nuke is sent and pickedup, stack of 64 is gained
 * [x] 20200706.1 v1.16.1 When nuke explose, now set in fire and break blocs
 * [x] 20200706.3 v1.16.1 Deported teleport code to dedicated listener
 * [x] 20200706.4 v1.16.2 Detector in different directions and added new blocks
 * [x] 20200706.5 Fixed version numbering
 *
 * Client:
 * [ ] Create laboratory
 * [ ] Terrasse double arches
 * [ ] Bouton fontaine
 * [x] Appart in mountain
 * [ ] Underwater house
 *
 * @author steph
 *
 */
public class MCPlugin19 extends JavaPlugin implements Listener{

	public static final String WARP_CONFIG_FILE								= "plugins/MCPlugin19/warp.json";
	public static final String SPELL_CONFIG_FILE							= "plugins/MCPlugin19/magic.json";
	public static final String ARCHERY_CONFIG_FILE							= "plugins/MCPlugin19/archery.json";
	public static final String INV_CONFIG_FILE								= "plugins/MCPlugin19/inventory.json";
	public static final String METRIC_INFO									= "metrics.info";
	public static final String VERSION										= "20221219.0";
	public static final String VERSION_SUB									= "Archery Master";

	private WarpManager warpManager;
	private CustomManager customManager;
	private MagicManager magicManager;
	private InventoryManager inventoryManager;
	private ArcheryManager archeryManager;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender != null && sender instanceof Player) {
			Player player = (Player) sender;
			String cmd = command.getName();
			switch (cmd) {
			case "warp":
				getWarpManager().onWarpCommand(player, args);
				return true;
			case "warpgroup":
				getWarpManager().onWarpGroupCommand(player, args);
				return true;
			case "detector":
				Detector.detect(player, this);
				return true;
			case "inv":
				getInventoryManager().onInvCommand(player, args);
				return true;
			case "clearsnowball":
				removeWorldSnowBalls();
				return true;
			case "magic":
				showMagicDetails(player);
				return true;
			case "archery":
				showArcheryDetails(player);
				return true;
			}
		}
		return false;
	}
	
	private void showMagicDetails(Player player) {
		SpellData data = getMagicManager().getSpellDataList().getDataForPlayer(player.getDisplayName());
		if (data == null) {
			player.sendMessage(ChatColor.RED+"Données non trouvées pour le joueur "+player.getDisplayName());
			return;
		}
		player.sendMessage(ChatColor.AQUA+"+= MAGIE ============================+");
		player.sendMessage(ChatColor.AQUA+" Snowball level: "+data.getSnowballLevel());
		player.sendMessage(ChatColor.AQUA+" Snowball dmg: "+Utils.getDamage(data.getSnowballLevel()));
		player.sendMessage(ChatColor.AQUA+" Snowball next lvl: "+(Utils.getRequiredXp(data.getSnowballLevel())-data.getSnowballXp()));
		player.sendMessage(ChatColor.AQUA+"+====================================+");
	}
	
	private void showArcheryDetails(Player player) {
		ArcheryData data = getArcheryManager().getArcheryDataList().getDataForPlayer(player.getDisplayName());
		if (data == null) {
			player.sendMessage(ChatColor.RED+"Données non trouvées pour le joueur "+player.getDisplayName());
			return;
		}
		player.sendMessage(ChatColor.AQUA+"+= ARCHERY ============================+");
		player.sendMessage(ChatColor.AQUA+" Copper level: "+data.getCopperLevel());
		player.sendMessage(ChatColor.AQUA+" Copper dmg: "+Utils.getCopperBowDamage(data.getCopperLevel()));
		player.sendMessage(ChatColor.AQUA+"+======================================+");
	}

	public void removeWorldSnowBalls() {
		List<World> worlds = getServer().getWorlds();
		for (World world : worlds) {
			List<Entity> entList = world.getEntities();//get all entities in the world
			 for(Entity current : entList){//loop through the list
		            if (current instanceof Snowball){//make sure we aren't deleting mobs/players
		            	current.remove();//remove it
		            }
			 }
		}
 
       
	}

	@Override
	public void onDisable() {
		super.onDisable();
		getServer().getScheduler().cancelTasks(this);
	}

	@Override
	public void onEnable() {
		getLogger().log(Level.INFO,"Enable plugin");
		super.onEnable();
		// Init warps
		initWarp();
		
		// Init customs
		initCustoms();
		
		// Init magic
		initMagic();
		
		// Init inventory
		initInventory();
		
		// Init archery
		initArchery();

		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(getWarpManager(), this);
		getServer().getPluginManager().registerEvents(getCustomManager(), this);
		getServer().getPluginManager().registerEvents(getMagicManager(), this);
		getServer().getPluginManager().registerEvents(getInventoryManager(), this);
		getServer().getPluginManager().registerEvents(getArcheryManager(), this);

		// Scheduled tasks
		BukkitScheduler scheduler = getServer().getScheduler();
		int res = scheduler.scheduleSyncRepeatingTask(this, new SaveTask(this), 0L, 6000L);
		if (res == -1)
			getLogger().log(Level.SEVERE, "Cannot schedule SaveTask");

		// Create custom recipes
		getLogger().log(Level.INFO, "Creating custom recipes...");
		getServer().addRecipe(Custom.createImprovedGoldPickaxeRecipe(this, Material.ACACIA_LOG));
		getServer().addRecipe(Custom.createImprovedGoldPickaxeRecipe(this, Material.BIRCH_LOG));
		getServer().addRecipe(Custom.createImprovedGoldPickaxeRecipe(this, Material.DARK_OAK_LOG));
		getServer().addRecipe(Custom.createImprovedGoldPickaxeRecipe(this, Material.JUNGLE_LOG));
		getServer().addRecipe(Custom.createImprovedGoldPickaxeRecipe(this, Material.OAK_LOG));
		getServer().addRecipe(Custom.createImprovedGoldPickaxeRecipe(this, Material.SPRUCE_LOG));

		getServer().addRecipe(Custom.createImprovedGoldAxeRecipe(this, Material.ACACIA_LOG));
		getServer().addRecipe(Custom.createImprovedGoldAxeRecipe(this, Material.BIRCH_LOG));
		getServer().addRecipe(Custom.createImprovedGoldAxeRecipe(this, Material.DARK_OAK_LOG));
		getServer().addRecipe(Custom.createImprovedGoldAxeRecipe(this, Material.JUNGLE_LOG));
		getServer().addRecipe(Custom.createImprovedGoldAxeRecipe(this, Material.OAK_LOG));
		getServer().addRecipe(Custom.createImprovedGoldAxeRecipe(this, Material.SPRUCE_LOG));

		getServer().addRecipe(Custom.createImprovedGoldShovelRecipe(this, Material.ACACIA_LOG));
		getServer().addRecipe(Custom.createImprovedGoldShovelRecipe(this, Material.BIRCH_LOG));
		getServer().addRecipe(Custom.createImprovedGoldShovelRecipe(this, Material.DARK_OAK_LOG));
		getServer().addRecipe(Custom.createImprovedGoldShovelRecipe(this, Material.JUNGLE_LOG));
		getServer().addRecipe(Custom.createImprovedGoldShovelRecipe(this, Material.OAK_LOG));
		getServer().addRecipe(Custom.createImprovedGoldShovelRecipe(this, Material.SPRUCE_LOG));

		getServer().addRecipe(Custom.createImprovedDiamondPickaxeRecipe(this, Material.ACACIA_LOG));
		getServer().addRecipe(Custom.createImprovedDiamondPickaxeRecipe(this, Material.BIRCH_LOG));
		getServer().addRecipe(Custom.createImprovedDiamondPickaxeRecipe(this, Material.DARK_OAK_LOG));
		getServer().addRecipe(Custom.createImprovedDiamondPickaxeRecipe(this, Material.JUNGLE_LOG));
		getServer().addRecipe(Custom.createImprovedDiamondPickaxeRecipe(this, Material.OAK_LOG));
		getServer().addRecipe(Custom.createImprovedDiamondPickaxeRecipe(this, Material.SPRUCE_LOG));
		
		getServer().addRecipe(Custom.createImprovedNetheritePickaxeRecipe(this, Material.ACACIA_LOG));
		getServer().addRecipe(Custom.createImprovedNetheritePickaxeRecipe(this, Material.BIRCH_LOG));
		getServer().addRecipe(Custom.createImprovedNetheritePickaxeRecipe(this, Material.DARK_OAK_LOG));
		getServer().addRecipe(Custom.createImprovedNetheritePickaxeRecipe(this, Material.JUNGLE_LOG));
		getServer().addRecipe(Custom.createImprovedNetheritePickaxeRecipe(this, Material.OAK_LOG));
		getServer().addRecipe(Custom.createImprovedNetheritePickaxeRecipe(this, Material.SPRUCE_LOG));
		
		getServer().addRecipe(Custom.createImprovedCopperBowRecipe(this));

		getServer().addRecipe(Custom.createUltimateBottleRecipe(this));
		getServer().addRecipe(Custom.createSnowWandRecipe(this));
		getServer().addRecipe(Custom.createFireWandRecipe(this));
		getServer().addRecipe(Custom.createGrenadeRecipe(this));
		getServer().addRecipe(Custom.createNukeRecipe(this));
		getServer().addRecipe(Custom.createInventoryPotionRecipe(this));
		getServer().addRecipe(Custom.createImprovedArrowRecipe(this));
		getLogger().log(Level.INFO, "Custom recipes created");
	}

	@Override
	public void onLoad() {
		super.onLoad();
		reloadConfig();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		p.sendMessage(ChatColor.GREEN + "MCPlugin v2 "+VERSION);
		p.sendMessage(ChatColor.GREEN + "Les info sur triedge.ovh");
		Custom.displayTitle(p, ChatColor.AQUA+"Triedge", VERSION_SUB);
	}
	
	private void initArchery() {
		getLogger().log(Level.INFO,"Initializing archery configuration");
		setArcheryManager(new ArcheryManager(this));
		try {
			getArcheryManager().loadArchery(ARCHERY_CONFIG_FILE);
		} catch (JsonIOException | IOException e) {
			getLogger().log(Level.SEVERE, "Cannot load config file: "+ARCHERY_CONFIG_FILE, e);
		}
		getLogger().log(Level.INFO,"Initialization of archery completed");
	}

	private void initWarp() {
		getLogger().log(Level.INFO,"Initializing warps configuration");
		setWarpManager(new WarpManager(this));
		try {
			getWarpManager().loadWarps(WARP_CONFIG_FILE);
		} catch (MCLoadingException | IOException e) {
			getLogger().log(Level.SEVERE, "Cannot load config file: "+WARP_CONFIG_FILE, e);
		}
		getLogger().log(Level.INFO,"Initialization of warps completed");
	}
	
	private void initCustoms() {
		getLogger().log(Level.INFO,"Initializing custom configuration");
		setCustomManager(new CustomManager(this));
		getLogger().log(Level.INFO,"Initialization of custom completed");
	}
	
	private void initMagic() {
		getLogger().log(Level.INFO,"Initializing magic configuration");
		setMagicManager(new MagicManager(this));
		try {
			getMagicManager().loadMagic(SPELL_CONFIG_FILE);
		} catch (MCLoadingException | IOException e) {
			getLogger().log(Level.SEVERE, "Cannot load config file: "+SPELL_CONFIG_FILE, e);
		}
		getLogger().log(Level.INFO,"Initialization of magic completed");
	}
	
	private void initInventory() {
		getLogger().log(Level.INFO,"Initializing inventory configuration");
		setInventoryManager(new InventoryManager(this));
		try {
			getInventoryManager().loadInventories(INV_CONFIG_FILE);
		} catch (MCLoadingException | JsonIOException | IOException e) {
			getLogger().log(Level.SEVERE, "Cannot load config file: "+INV_CONFIG_FILE, e);
		}
		getLogger().log(Level.INFO,"Initialization of inventory completed");
	}

	public WarpManager getWarpManager() {
		return warpManager;
	}

	public void setWarpManager(WarpManager warpManager) {
		this.warpManager = warpManager;
	}

	public CustomManager getCustomManager() {
		return customManager;
	}

	public void setCustomManager(CustomManager customManager) {
		this.customManager = customManager;
	}

	public MagicManager getMagicManager() {
		return magicManager;
	}

	public void setMagicManager(MagicManager magicManager) {
		this.magicManager = magicManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	public ArcheryManager getArcheryManager() {
		return archeryManager;
	}

	public void setArcheryManager(ArcheryManager archeryManager) {
		this.archeryManager = archeryManager;
	}
	
	
}
