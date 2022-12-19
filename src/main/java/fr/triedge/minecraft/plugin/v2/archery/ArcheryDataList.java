package fr.triedge.minecraft.plugin.v2.archery;

import java.util.ArrayList;

public class ArcheryDataList {

	private ArrayList<ArcheryData> archeryData = new ArrayList<>();

	public ArrayList<ArcheryData> getArcheryData() {
		return archeryData;
	}

	public void setArcheryData(ArrayList<ArcheryData> archeryData) {
		this.archeryData = archeryData;
	}
	
	public boolean isPlayerArcher(String playerName) {
		for (ArcheryData sd : getArcheryData()) {
			if (sd.getPlayerName().equals(playerName))
				return true;
		}
		return false;
	}
	
	public ArcheryData getDataForPlayer(String playerName) {
		for (ArcheryData sd : getArcheryData()) {
			if (sd.getPlayerName().equals(playerName))
				return sd;
		}
		ArcheryData d = new ArcheryData();
		d.setPlayerName(playerName);
		getArcheryData().add(d);
		return d;
	}
}
