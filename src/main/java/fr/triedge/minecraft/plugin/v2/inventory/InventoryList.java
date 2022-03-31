package fr.triedge.minecraft.plugin.v2.inventory;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="InventoryList")
public class InventoryList {
	
	private ArrayList<InventoryData> inventories = new ArrayList<>();

	public InventoryList() {
	}

	public ArrayList<InventoryData> getInventories() {
		return inventories;
	}

	@XmlElementWrapper(name="InventoryList")
    @XmlElement(name="Inventory")
	public void setInventories(ArrayList<InventoryData> inventories) {
		this.inventories = inventories;
	}
}
