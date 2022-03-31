package fr.triedge.minecraft.plugin.v2.detector;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class Detector {

	public static void detect(Player player) {
		ArrayList<Block> blocks = new ArrayList<>();
		int maxDist = 50;
		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Location loc = block.getLocation();
		player.sendMessage(ChatColor.GOLD+"Recherche avec le detecteur:");
		int startX = block.getX() -(maxDist/2);
		int startY = block.getY() -(maxDist/2);
		int startZ = block.getZ() -(maxDist/2);

		int endX = block.getX() +(maxDist/2);
		int endY = block.getY() +(maxDist/2);
		int endZ = block.getZ() +(maxDist/2);

		// 20200706.4
		for (int x = startX; x <= endX; ++x) {
			for (int y = startY; y <= endY; ++y) {
				for (int z = startZ; z <= endZ; ++z) {
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					Material type = loc.getBlock().getType();
					if (
							type == Material.DIAMOND_ORE ||
							type == Material.IRON_ORE ||
							type == Material.GOLD_ORE ||
							type == Material.EMERALD_ORE ||
							type == Material.ANCIENT_DEBRIS ||
							type == Material.NETHERITE_BLOCK ||
							type == Material.LAPIS_ORE) {
						blocks.add(loc.getBlock());
					}
				}
			}
		}
		if (blocks.isEmpty())
			player.sendMessage(ChatColor.DARK_PURPLE+"Rien trouv�");
		else {
			for (Block b : blocks) {
				String name = b.getType().toString();
				if (b.getType() == Material.DIAMOND_ORE)
					player.sendMessage(ChatColor.AQUA+name+" -> X:"+b.getX()+" Y:"+b.getY()+" Z:"+b.getZ());
				else
					player.sendMessage(ChatColor.GREEN+name+" -> X:"+b.getX()+" Y:"+b.getY()+" Z:"+b.getZ());
			}
		}
	}
}