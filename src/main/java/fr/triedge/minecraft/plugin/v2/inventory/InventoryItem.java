package fr.triedge.minecraft.plugin.v2.inventory;

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
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
