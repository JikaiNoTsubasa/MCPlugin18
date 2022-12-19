package fr.triedge.minecraft.plugin.v2.custom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import fr.triedge.minecraft.plugin.v2.MCPlugin19;
import fr.triedge.minecraft.plugin.v2.utils.Utils;

public class CustomManager implements Listener{
	
	private MCPlugin19 plugin;

	public CustomManager(MCPlugin19 plugin) {
		setPlugin(plugin);
	}
	
	@EventHandler
	public void onBlockDamageEvent(BlockDamageEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand() != null &&
				player.getInventory().getItemInMainHand().getType() == Material.STICK &&
				player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(Custom.ULT_STICK)) {
			Block block = event.getBlock();
			if (block != null) {
				block.breakNaturally();
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item == null)
			return;
		if (item.getItemMeta() == null)
			return;
		String name = item.getItemMeta().getDisplayName();
		if (name.equals(Custom.IMP_GOLD_PICKAXE) ||
				name.equals(Custom.IMP_DIAMOND_PICKAXE) ||
				name.equals(Custom.IMP_GOLD_SHOVEL) ||
				name.equals(Custom.IMP_GOLD_AXE) ||
				name.equals(Custom.IMP_NETHERITE_PICKAXE)) {
			// Manage durability
			int durability = Integer.valueOf(item.getItemMeta().getLore().get(0));
			durability--;
			if (durability <= 0) {
				player.getInventory().remove(item);
				player.sendMessage(ChatColor.RED+item.getItemMeta().getDisplayName()+" est cassé");
			}else {
				Custom.decreaseDurability(item);
			}

			// Manage custom drop for custom pickaxe
			Block block = event.getBlock();
			if (block == null)
				return;
			Material type = block.getType();
			if (type == Material.DIAMOND_ORE ||
					type == Material.DEEPSLATE_DIAMOND_ORE ||
					type == Material.DEEPSLATE_GOLD_ORE ||
					type == Material.DEEPSLATE_EMERALD_ORE ||
					type == Material.DEEPSLATE_IRON_ORE ||
					type == Material.DEEPSLATE_COAL_ORE ||
					type == Material.GOLD_ORE ||
					type == Material.EMERALD_ORE ||
					type == Material.IRON_ORE ||
					type == Material.NETHER_GOLD_ORE ||
					type == Material.NETHER_QUARTZ_ORE ||
					type == Material.NETHERITE_BLOCK ||
					type == Material.COAL_ORE ||
					type == Material.ANCIENT_DEBRIS ||
					type == Material.SNOW) {
				if (!block.getDrops().isEmpty()) {
					if (name.equals(Custom.IMP_NETHERITE_PICKAXE)) {
						ItemStack stack = new ItemStack(block.getDrops().iterator().next().getType(), 5);
						block.getLocation().getWorld().dropItemNaturally(block.getLocation(), stack);
					}else if (name.equals(Custom.IMP_DIAMOND_PICKAXE)){
						ItemStack stack = new ItemStack(block.getDrops().iterator().next().getType(), 3);
						block.getLocation().getWorld().dropItemNaturally(block.getLocation(), stack);
					}else {
						ItemStack stack = new ItemStack(block.getDrops().iterator().next().getType(), 2);
						block.getLocation().getWorld().dropItemNaturally(block.getLocation(), stack);
					}

				}
			}
		}
	}
	
	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
		// Slime appearance
		if (event.getEntityType() == EntityType.SLIME) {
			Slime slime = (Slime) event.getEntity();
			Bukkit.broadcastMessage(ChatColor.ITALIC+""+ChatColor.DARK_PURPLE+"Un Slime est apparu: x:"+slime.getLocation().getX()+" y:"+slime.getLocation().getY()+" z:"+slime.getLocation().getZ());
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event == null)
			return;
		// Custom loot on creepers
		if (event.getEntityType() == EntityType.CREEPER) {
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.EMERALD));
		}

		// Check player dead
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player)event.getEntity();
			ItemStack inventoryPotion = checkInventoryPotion(player);
			if (inventoryPotion != null) {
				player.sendMessage(ChatColor.GOLD+"Votre potion d'inventaire fait effet!");
				Utils.decreaseItemFromInventory(Custom.INVENTORY_POTION, player);
				event.getDrops().clear();
			}
		}
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
	
	private void onPlayerUseCustomItem(PlayerInteractEvent event, String name) {
		// Grenade
		if (name.equals(Custom.GRENADE) && event.getAction() == Action.RIGHT_CLICK_AIR) {
			launchGrenade(event.getPlayer(), 2.0F, Utils.secondsToTicks(3)); // 3s
			event.setCancelled(true);
		// Nuke
		}else if (name.equals(Custom.NUKE) && event.getAction() == Action.RIGHT_CLICK_AIR) {
			launchGrenade(event.getPlayer(), 10.0F, Utils.secondsToTicks(5)); // 5s
			event.setCancelled(true);
		// Ultimate Water
		}else if (name.equals(Custom.ULT_WATER) && event.getAction() == Action.LEFT_CLICK_BLOCK) {
			event.getClickedBlock().setType(Material.WATER);
			event.getPlayer().getWorld().spawnParticle(Particle.WATER_BUBBLE, event.getClickedBlock().getLocation(), 10);
		// Ultimate Water Cancel
		}else if (name.equals(Custom.ULT_WATER) && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			event.setCancelled(true);
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
		if (sp[0].equals("UTL")) {
			actionULT(sp[1],player);
		}
	}
	
	private void actionULT(String name, Player player) {
		switch (name) {
		case "STICK":
			player.getInventory().addItem(Custom.createUtlStick());
			player.sendMessage(ChatColor.GOLD+"Added STICK");
			break;
		case "WATER":
			player.getInventory().addItem(Custom.createUtlBottle());
			player.sendMessage(ChatColor.GOLD+"Added WATER");
			break;
		case "G.PAXE":
			player.getInventory().addItem(Custom.createImprovedGoldPickaxe());
			player.sendMessage(ChatColor.GOLD+"Added Gold Pickaxe");
			break;
		case "G.AXE":
			player.getInventory().addItem(Custom.createImprovedGoldAxe());
			player.sendMessage(ChatColor.GOLD+"Added Gold Axe");
			break;
		case "G.SHO":
			player.getInventory().addItem(Custom.createImprovedGoldShovel());
			player.sendMessage(ChatColor.GOLD+"Added Gold Shovel");
			break;
		case "D.PAXE":
			player.getInventory().addItem(Custom.createImprovedDiamondPickaxe());
			player.sendMessage(ChatColor.GOLD+"Added Diamond Pickaxe");
			break;
		case "S.WAND":
			player.getInventory().addItem(Custom.createSnowWand());
			player.sendMessage(ChatColor.GOLD+"Added Sonw Wand");
			break;
		case "F.WAND":
			player.getInventory().addItem(Custom.createFireWand());
			player.sendMessage(ChatColor.GOLD+"Added Fire Wand");
			break;
		case "POP.INV":
			player.getInventory().addItem(Custom.createInventoryPotion());
			player.sendMessage(ChatColor.GOLD+"Added Inventory Potion");
			break;
		case "GRE":
			player.getInventory().addItem(Custom.createGrenade(10));
			player.sendMessage(ChatColor.GOLD+"Added 10 Grenades");
			break;
		default:
			break;
		}
	}
	
	private void launchGrenade(Player player, float power, long ticks ) {
		// must be player's inventory item
		ItemStack stack = player.getInventory().getItemInMainHand();
		ItemStack stack2 = new ItemStack(stack);
		stack2.setAmount(1); // 20200706.0
		final Item item = player.getWorld().dropItem(player.getEyeLocation(), stack2);
		item.setVelocity(player.getEyeLocation().getDirection());
		//Utils.decreaseItemFromInventory(item.getItemStack().getItemMeta().getDisplayName(), player);
		Utils.decreaseItemFromInventory(stack, player);
		//player.getInventory().removeItem(item.getItemStack());
		final float pow = power;
		// After 3sec
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
			@Override
			public void run() {
				item.getWorld().playEffect(item.getLocation(), Effect.SMOKE, 5);
				item.getWorld().createExplosion(item.getLocation(), pow, false, true); // 20200706.1
				item.remove();
				item.playEffect(EntityEffect.WOLF_SMOKE);
			}
		}, ticks);
	}
	
	private ItemStack checkInventoryPotion(Player player) {
		ItemStack[] items = player.getInventory().getContents();
		for (int i = 0; i < items.length; ++i) {
			ItemStack item = items[i];
			if (item == null)
				continue;
			if (item.getItemMeta().getDisplayName()!=null && item.getItemMeta().getDisplayName().equals(Custom.INVENTORY_POTION)) {
				return item;
			}
		}
		return null;
	}

	public MCPlugin19 getPlugin() {
		return plugin;
	}

	public void setPlugin(MCPlugin19 plugin) {
		this.plugin = plugin;
	}

	
}
