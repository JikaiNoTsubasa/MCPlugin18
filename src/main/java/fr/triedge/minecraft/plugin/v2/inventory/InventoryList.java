package fr.triedge.minecraft.plugin.v2.inventory;

import java.util.ArrayList;

public class InventoryList {
	
	private ArrayList<InventoryData> inventories = new ArrayList<>();

	public InventoryList() {
	}

	public ArrayList<InventoryData> getInventories() {
		return inventories;
	}

	public void setInventories(ArrayList<InventoryData> inventories) {
		this.inventories = inventories;
	}
}
