package fr.triedge.minecraft.plugin.v2.magic;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SpellData")
public class SpellData {

	private String playerName;
	private int snowballLevel = 1, snowballXp, fireballLevel = 1, fireballXp;
	
	public SpellData() {
	}

	public String getPlayerName() {
		return playerName;
	}

	@XmlAttribute(name="player")
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getSnowballLevel() {
		return snowballLevel;
	}

	@XmlElement(name="snowballLevel")
	public void setSnowballLevel(int snowballLevel) {
		this.snowballLevel = snowballLevel;
	}

	public int getSnowballXp() {
		return snowballXp;
	}

	@XmlElement(name="snowballXP")
	public void setSnowballXp(int snowballXp) {
		this.snowballXp = snowballXp;
	}

	public int getFireballLevel() {
		return fireballLevel;
	}

	@XmlElement(name="fireballLevel")
	public void setFireballLevel(int fireballLevel) {
		this.fireballLevel = fireballLevel;
	}


	public int getFireballXp() {
		return fireballXp;
	}
	
	@XmlElement(name="fireballXP")
	public void setFireballXp(int fireballXp) {
		this.fireballXp = fireballXp;
	}
	
	
}
