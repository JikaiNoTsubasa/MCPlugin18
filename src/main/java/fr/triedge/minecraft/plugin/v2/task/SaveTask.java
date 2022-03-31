package fr.triedge.minecraft.plugin.v2.task;

import java.util.logging.Level;

import javax.xml.bind.JAXBException;

import fr.triedge.minecraft.plugin.v2.MCPluginV2;

public class SaveTask implements Runnable{
	
	protected MCPluginV2 plugin;

	public SaveTask(MCPluginV2 plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Saving Plugin configuration...");
		try {
			this.plugin.getWarpManager().save(MCPluginV2.WARP_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Warp saved");
			this.plugin.getMagicManager().save(MCPluginV2.SPELL_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Magic saved");
			this.plugin.getInventoryManager().save(MCPluginV2.INV_CONFIG_FILE);
			this.plugin.getLogger().log(Level.INFO,"[SCHEDULED TASK] Configuration Inventories saved");
		} catch (JAXBException e) {
			this.plugin.getLogger().log(Level.SEVERE,"Cannot save configuration",e);
		}
	}

}
