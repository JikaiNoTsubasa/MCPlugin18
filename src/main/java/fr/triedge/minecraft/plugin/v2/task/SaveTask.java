package fr.triedge.minecraft.plugin.v2.task;

import java.util.logging.Level;

import fr.triedge.minecraft.plugin.v2.MCPlugin20;

public class SaveTask implements Runnable{
	
	protected MCPlugin20 plugin;

	public SaveTask(MCPlugin20 plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Saving Plugin configuration...");
		try {
			this.plugin.getWarpManager().save(MCPlugin20.WARP_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Warp saved");
			this.plugin.getMagicManager().save(MCPlugin20.SPELL_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Magic saved");
			this.plugin.getInventoryManager().save(MCPlugin20.INV_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Inventories saved");
			this.plugin.getArcheryManager().save(MCPlugin20.ARCHERY_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Archery saved");
		} catch (Exception e) {
			this.plugin.getLogger().log(Level.SEVERE,"Cannot save configuration",e);
		}
	}

}
