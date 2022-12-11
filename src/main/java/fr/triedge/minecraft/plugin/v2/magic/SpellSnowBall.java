package fr.triedge.minecraft.plugin.v2.magic;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import fr.triedge.minecraft.plugin.v2.utils.Utils;

public class SpellSnowBall extends Spell{

	public SpellSnowBall(Player player, SpellDataList config, Plugin plugin) {
		super(player, config,plugin);
	}

	@Override
	public float getDamageFactor() {
		return 1.1f;
	}

	@Override
	public String getName() {
		return "snowball";
	}

	@Override
	public void launch() {
		Snowball snow = player.launchProjectile(Snowball.class);
		snow.setMetadata("player", new FixedMetadataValue(plugin, player.getName()));
		snow.setGravity(false);
		World world = player.getWorld();
		world.playEffect(player.getLocation(), Effect.FIREWORK_SHOOT, 1);
	}

	@Override
	public String getDisplayName() {
		return "Boules de neige";
	}

	@Override
	public int addXP() {
		String playerName = player.getName();
		int xp = 1;
		SpellData sd = null;
		// If no current data available
		if (!config.isPlayerMagical(playerName)) {
			sd = new SpellData();
			sd.setPlayerName(playerName);
			config.getSpellData().add(sd);
		}else {
			sd = config.getDataForPlayer(playerName);
		}
		
		sd.setSnowballXp(sd.getSnowballXp()+xp);
		if (sd.getSnowballXp() >= Utils.getRequiredXp(sd.getSnowballLevel())) {
			// level up every 50 xp
			sd.setSnowballLevel(sd.getSnowballLevel()+1);
			sd.setSnowballXp(0); // Reset xp to 0
			float player_xp_add = sd.getSnowballLevel()*2.5f;
			player.setTotalExperience(player.getTotalExperience() + (int)player_xp_add);
			player.sendMessage(ChatColor.GREEN+"Level UP["+getName()+"]: "+getDisplayName()+" lvl "+sd.getSnowballLevel());
			player.sendMessage(ChatColor.GREEN+"Personnage XP +"+player_xp_add);
		}
		
		return xp;
	}
	
	@Override
	public float getDamage() {
		int level = 1;
		SpellData sd = config.getDataForPlayer(player.getName());
		if (sd != null) {
			level = sd.getSnowballLevel();
		}
		return Utils.getDamage(level);
	}

}
