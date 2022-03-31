package fr.triedge.minecraft.plugin.v2.inventory;

import java.util.ArrayList;


public class InventoryData {

	private String playerName;
	private int id;
	private ArrayList<InventoryItem> items = new ArrayList<>();
	
	public InventoryData() {
	}
	
	public InventoryData(String playerName) {
		setPlayerName(playerName);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<InventoryItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<InventoryItem> items) {
		this.items = items;
	}
}
