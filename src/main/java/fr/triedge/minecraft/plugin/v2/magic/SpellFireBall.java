package fr.triedge.minecraft.plugin.v2.magic;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;

public class SpellFireBall extends Spell{

	public SpellFireBall(Player player, SpellDataList config, Plugin plugin) {
		super(player, config,plugin);
	}

	@Override
	public String getName() {
		return "fireball";
	}

	@Override
	public void launch() {
		SmallFireball fire = player.launchProjectile(SmallFireball.class);
		fire.setMetadata("player", new FixedMetadataValue(plugin, player.getName()));
		fire.setIsIncendiary(true);
		fire.setYield(0);
		World world = player.getWorld();
		world.playEffect(player.getLocation(), Effect.FIREWORK_SHOOT, 1);
		// Double handed
		/*
		if (player.getInventory().getItemInOffHand().getType() == Material.BLAZE_ROD) {
			for (int i = 0 ; i < 10 ; ++i) {
				SmallFireball f = player.launchProjectile(SmallFireball.class);
				f.setIsIncendiary(true);
				f.setYield(10);
			}
			world.playEffect(player.getLocation(), Effect.FIREWORK_SHOOT, 1);
		}
		*/
		BlockIterator it = new BlockIterator(player.getEyeLocation(),1,20);
		while(it.hasNext()) {
			Location loc = it.next().getLocation();
			world.spawnParticle(Particle.FLAME, loc, 10);
			if (loc.getBlock().getType().isSolid()) {
				break;
			}
		}
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
		
		sd.setFireballXp(sd.getFireballXp()+xp);
		if (xp % 50 == 0) {
			// level up every 50 xp
			sd.setFireballLevel(sd.getFireballLevel()+1);
			float player_xp_add = sd.getFireballLevel()*2.5f;
			player.setTotalExperience(player.getTotalExperience() + (int)player_xp_add);
			player.sendMessage(ChatColor.GREEN+"Level UP["+getName()+"]: "+getDisplayName()+" lvl "+sd.getFireballLevel());
			player.sendMessage(ChatColor.GREEN+"Personnage XP +"+player_xp_add);
		}
		
		return xp;
	}
	
	@Override
	public float getDamage() {
		int level = 1;
		SpellData sd = config.getDataForPlayer(player.getName());
		if (sd != null) {
			level = sd.getFireballLevel();
		}
		return getDamageFactor()*level;
	}

	@Override
	public float getDamageFactor() {
		return 1.1f;
	}

	@Override
	public String getDisplayName() {
		return "Boules de feu";
	}

}
