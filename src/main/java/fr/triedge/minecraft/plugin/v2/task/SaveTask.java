package fr.triedge.minecraft.plugin.v2.task;

import java.util.logging.Level;

import fr.triedge.minecraft.plugin.v2.MCPlugin18;

public class SaveTask implements Runnable{
	
	protected MCPlugin18 plugin;

	public SaveTask(MCPlugin18 plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Saving Plugin configuration...");
		try {
			this.plugin.getWarpManager().save(MCPlugin18.WARP_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Warp saved");
			this.plugin.getMagicManager().save(MCPlugin18.SPELL_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Magic saved");
			this.plugin.getInventoryManager().save(MCPlugin18.INV_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Inventories saved");
		} catch (Exception e) {
			this.plugin.getLogger().log(Level.SEVERE,"Cannot save configuration",e);
		}
	}

}
