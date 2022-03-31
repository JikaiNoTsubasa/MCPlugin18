package fr.triedge.minecraft.plugin.v2.magic;

public class SpellData {

	private String playerName;
	private int snowballLevel = 1, snowballXp, fireballLevel = 1, fireballXp;
	
	public SpellData() {
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getSnowballLevel() {
		return snowballLevel;
	}

	public void setSnowballLevel(int snowballLevel) {
		this.snowballLevel = snowballLevel;
	}

	public int getSnowballXp() {
		return snowballXp;
	}

	public void setSnowballXp(int snowballXp) {
		this.snowballXp = snowballXp;
	}

	public int getFireballLevel() {
		return fireballLevel;
	}

	public void setFireballLevel(int fireballLevel) {
		this.fireballLevel = fireballLevel;
	}


	public int getFireballXp() {
		return fireballXp;
	}
	
	public void setFireballXp(int fireballXp) {
		this.fireballXp = fireballXp;
	}
	
	
}
