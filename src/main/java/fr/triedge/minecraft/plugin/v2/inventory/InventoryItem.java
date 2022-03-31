package fr.triedge.minecraft.plugin.v2.inventory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Item")
public class InventoryItem {

	private String itemType;
	private int count;
	
	public InventoryItem() {
	}
	
	public InventoryItem(String itemType, int count) {
		super();
		this.itemType = itemType;
		this.count = count;
	}

	public String getItemType() {
		return itemType;
	}
	
	@XmlElement(name="Type")
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getCount() {
		return count;
	}
	
	@XmlElement(name="Count")
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
