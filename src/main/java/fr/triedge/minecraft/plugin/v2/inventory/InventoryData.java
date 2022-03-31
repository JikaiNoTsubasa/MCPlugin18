package fr.triedge.minecraft.plugin.v2.inventory;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Inventory")
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

	@XmlAttribute(name="Player")
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getId() {
		return id;
	}

	@XmlAttribute(name="ID")
	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<InventoryItem> getItems() {
		return items;
	}

	@XmlElementWrapper(name="ItemList")
    @XmlElement(name="Item")
	public void setItems(ArrayList<InventoryItem> items) {
		this.items = items;
	}
}
