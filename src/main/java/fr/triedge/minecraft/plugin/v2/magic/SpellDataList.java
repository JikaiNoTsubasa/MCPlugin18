package fr.triedge.minecraft.plugin.v2.magic;

import java.util.ArrayList;

public class SpellDataList {

	private ArrayList<SpellData> spellData = new ArrayList<>();
	
	public SpellDataList() {
	}

	public ArrayList<SpellData> getSpellData() {
		return spellData;
	}

	public void setSpellData(ArrayList<SpellData> spellData) {
		this.spellData = spellData;
	}
	
	public boolean isPlayerMagical(String playerName) {
		for (SpellData sd : getSpellData()) {
			if (sd.getPlayerName().equals(playerName))
				return true;
		}
		return false;
	}
	
	public SpellData getDataForPlayer(String playerName) {
		for (SpellData sd : getSpellData()) {
			if (sd.getPlayerName().equals(playerName))
				return sd;
		}
		return null;
	}
}
