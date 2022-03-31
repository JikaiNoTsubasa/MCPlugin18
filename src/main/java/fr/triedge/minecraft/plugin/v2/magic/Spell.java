package fr.triedge.minecraft.plugin.v2.magic;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class Spell {

	protected Player player;
	protected SpellDataList config;
	protected Plugin plugin;
	public abstract float getDamageFactor();
	
	public Spell(Player player, SpellDataList config, Plugin plugin) {
		super();
		this.player = player;
		this.config = config;
		this.plugin = plugin;
	}
	public abstract String getName();
	public abstract String getDisplayName();
	public abstract void launch();
	public abstract int addXP();
	public abstract float getDamage();
}
