package fr.triedge.minecraft.plugin.v2.custom;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Custom {

	public static final String ULT_STICK					= ChatColor.GOLD+"ULTIMATE STICK";
	public static final String ULT_WATER					= ChatColor.DARK_AQUA+"ULTIMATE WATER";
	public static final String IMP_GOLD_PICKAXE				= ChatColor.LIGHT_PURPLE+"Improved Gold Pickaxe";
	public static final String IMP_GOLD_AXE					= ChatColor.LIGHT_PURPLE+"Improved Gold Axe";
	public static final String IMP_GOLD_SHOVEL				= ChatColor.LIGHT_PURPLE+"Improved Gold Shovel";
	public static final String IMP_DIAMOND_PICKAXE			= ChatColor.LIGHT_PURPLE+"Improved Diamond Pickaxe";
	public static final String WAND_SNOW					= ChatColor.LIGHT_PURPLE+"Snow Wand";
	public static final String WAND_FIRE					= ChatColor.LIGHT_PURPLE+"Fire Wand";
	public static final String GRENADE						= ChatColor.LIGHT_PURPLE+"Grenade";
	public static final String NUKE							= ChatColor.LIGHT_PURPLE+"NUKE";
	public static final String INVENTORY_POTION				= ChatColor.LIGHT_PURPLE+"Inventory Potion";
	
	public static final String BOSS_SKELETON_LVL				= ChatColor.RED+"BOSS Skeleton lvl";
	
	public static ItemStack createUtlStick() {
		ItemStack stick = new ItemStack(Material.STICK);
		ItemMeta meta = stick.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Legendary stick");
		lore.add("Unlimited Dig");
		meta.setDisplayName(ULT_STICK);
		meta.setLore(lore);
		stick.setItemMeta(meta);
		return stick;
	}
	
	public static ItemStack createUtlBottle() {
		ItemStack water = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta meta = water.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Bouteille l�gendaire de Mc Fisher");
		lore.add("Transforme les blocks en eau");
		meta.setDisplayName(ULT_WATER);
		meta.setLore(lore);
		water.setItemMeta(meta);
		return water;
	}
	
	public static ItemStack createImprovedGoldPickaxe() {
		ItemStack axe = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemMeta meta = axe.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("1600");
		meta.setDisplayName(IMP_GOLD_PICKAXE);
		meta.setLore(lore);
		meta.setUnbreakable(true);
		axe.setItemMeta(meta);
		return axe;
	}
	
	public static ItemStack createImprovedGoldAxe() {
		ItemStack axe = new ItemStack(Material.GOLDEN_AXE);
		ItemMeta meta = axe.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("1600");
		meta.setDisplayName(IMP_GOLD_AXE);
		meta.setLore(lore);
		meta.setUnbreakable(true);
		axe.setItemMeta(meta);
		return axe;
	}
	
	public static ItemStack createImprovedGoldShovel() {
		ItemStack axe = new ItemStack(Material.GOLDEN_SHOVEL);
		ItemMeta meta = axe.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("1600");
		meta.setDisplayName(IMP_GOLD_SHOVEL);
		meta.setLore(lore);
		meta.setUnbreakable(true);
		axe.setItemMeta(meta);
		return axe;
	}
	
	public static ItemStack createImprovedDiamondPickaxe() {
		ItemStack axe = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta meta = axe.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("5000");
		meta.setDisplayName(IMP_DIAMOND_PICKAXE);
		meta.setLore(lore);
		meta.setUnbreakable(true);
		axe.setItemMeta(meta);
		return axe;
	}
	
	public static ItemStack createSnowWand() {
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Baguette de Neige");
		lore.add("Lance des boules de Neige");
		meta.setDisplayName(WAND_SNOW);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createFireWand() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Baguette de Feu");
		lore.add("Lance des boules de Feu");
		meta.setDisplayName(WAND_FIRE);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createGrenade(int number) {
		ItemStack item = new ItemStack(Material.SLIME_BALL, number);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Une grenade qui explose");
		lore.add("au bout de 3 secondes");
		meta.setDisplayName(GRENADE);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createNuke(int number) {
		ItemStack item = new ItemStack(Material.CLAY_BALL, number);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("NUKE NUKE NUKE");
		lore.add("au bout de 5 secondes");
		meta.setDisplayName(NUKE);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack createInventoryPotion() {
		ItemStack item = new ItemStack(Material.POTION);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("Une potion permettant de ne pas perdre");
		lore.add("son inventaire quand on meurt");
		meta.setDisplayName(INVENTORY_POTION);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack decreaseDurability(ItemStack item) {
		int durability = Integer.valueOf(item.getItemMeta().getLore().get(0));
		durability--;
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(String.valueOf(durability));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	// ==== Recipes ===================================================================
	public static ShapedRecipe createImprovedGoldPickaxeRecipe(Plugin plugin, Material log) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_goldaxe_"+log.name());
		ShapedRecipe recipe = new ShapedRecipe(key, createImprovedGoldPickaxe());
		recipe.shape("DDD"," B "," B ");
		recipe.setIngredient('D', Material.GOLD_BLOCK);
		recipe.setIngredient('B', log);
		return recipe;
	}
	
	public static ShapedRecipe createImprovedGoldAxeRecipe(Plugin plugin, Material log) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_goldaxe2_"+log.name());
		ShapedRecipe recipe = new ShapedRecipe(key, createImprovedGoldAxe());
		recipe.shape("DD ","DB "," B ");
		recipe.setIngredient('D', Material.GOLD_BLOCK);
		recipe.setIngredient('B', log);
		return recipe;
	}
	
	public static ShapedRecipe createImprovedGoldShovelRecipe(Plugin plugin, Material log) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_goldshovel_"+log.name());
		ShapedRecipe recipe = new ShapedRecipe(key, createImprovedGoldShovel());
		recipe.shape(" D "," B "," B ");
		recipe.setIngredient('D', Material.GOLD_BLOCK);
		recipe.setIngredient('B', log);
		return recipe;
	}
	
	public static ShapedRecipe createImprovedDiamondPickaxeRecipe(Plugin plugin, Material log) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_diamondaxe_"+log.name());
		ShapedRecipe recipe = new ShapedRecipe(key, createImprovedDiamondPickaxe());
		recipe.shape("DDD"," B "," B ");
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('B', log);
		return recipe;
	}
	
	public static ShapedRecipe createUltimateBottleRecipe(Plugin plugin) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_utl_bottle");
		ShapedRecipe recipe = new ShapedRecipe(key, createUtlBottle());
		recipe.shape(" E ","EBE"," E ");
		recipe.setIngredient('E', Material.EMERALD);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		return recipe;
	}
	
	public static ShapedRecipe createInventoryPotionRecipe(Plugin plugin) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_utl_inv_popo");
		ShapedRecipe recipe = new ShapedRecipe(key, createInventoryPotion());
		recipe.shape("   ","E E"," B ");
		recipe.setIngredient('E', Material.EMERALD);
		recipe.setIngredient('B', Material.GLASS_BOTTLE);
		return recipe;
	}
	
	public static ShapedRecipe createSnowWandRecipe(Plugin plugin) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_snow_wand");
		ShapedRecipe recipe = new ShapedRecipe(key, createSnowWand());
		recipe.shape(" N "," S "," S ");
		recipe.setIngredient('N', Material.SNOWBALL);
		recipe.setIngredient('S', Material.STICK);
		return recipe;
	}
	
	public static ShapedRecipe createFireWandRecipe(Plugin plugin) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_fire_wand");
		ShapedRecipe recipe = new ShapedRecipe(key, createFireWand());
		recipe.shape(" N "," S "," S ");
		recipe.setIngredient('N', Material.BLAZE_POWDER);
		recipe.setIngredient('S', Material.BLAZE_ROD);
		return recipe;
	}
	
	public static ShapedRecipe createGrenadeRecipe(Plugin plugin) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_grenade");
		ShapedRecipe recipe = new ShapedRecipe(key, createGrenade(5));
		recipe.shape("   "," T "," S ");
		recipe.setIngredient('T', Material.STRING);
		recipe.setIngredient('S', Material.SLIME_BALL);
		return recipe;
	}
	
	public static ShapedRecipe createNukeRecipe(Plugin plugin) {
		NamespacedKey key = new NamespacedKey(plugin, "jikai_nuke");
		ShapedRecipe recipe = new ShapedRecipe(key, createNuke(10));
		recipe.shape("   "," T "," S ");
		recipe.setIngredient('T', Material.STRING);
		recipe.setIngredient('S', Material.GUNPOWDER);
		return recipe;
	}
	
	public static void displayTitle(Player p, String title, String subtitle) {
		p.sendTitle(title, subtitle, 10, 70, 20);
	}
	
	// Needs craftbukkit
	/*
	public static boolean setMaxStackSize(int mat, int size) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	    Field field=net.minecraft.server.v1_13_R2.Item.class.getDeclaredField("maxStackSize");
	    net.minecraft.server.v1_13_R2.Item it = net.minecraft.server.v1_13_R2.Item.getById(mat);
	    if (it != null) {
	    	field.setAccessible(true);
	    	field.setInt(it, size);
	    	return true;
	    }
	    return false;
	}
	*/
	
	// ==== MOB =======================================================================
	//https://bukkit.org/threads/tutorial-how-to-customize-the-behaviour-of-a-mob-or-entity.54547/
	//https://bukkit.org/threads/tutorial-custom-entities-meteor.93899/
	/*
	public static void createBossSkeleton(Location loc, int level) {
		Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
		skeleton.setCustomName(BOSS_SKELETON_LVL+" "+level);
		skeleton.setCustomNameVisible(true);
		double health = (8*level)+200;
		//Damageable dmg = (Damageable) skeleton;
		//dmg.setMaxHealth(health);
		
		skeleton.setHealth(health);
		//skeleton.getLootTable().populateLoot(arg0, arg1)
		skeleton.setSkeletonType(SkeletonType.NORMAL);
	}
	*/
}
