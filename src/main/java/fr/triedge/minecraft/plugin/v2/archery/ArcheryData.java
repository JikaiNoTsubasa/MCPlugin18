package fr.triedge.minecraft.plugin.v2.archery;

public class ArcheryData {

	private String playerName;
	private int copperLevel = 1, copperXp = 0, fireBowLevel = 1, fireBowXp = 0, iceBowLevel = 1, iceBowXp;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getCopperLevel() {
		return copperLevel;
	}

	public void setCopperLevel(int copperLevel) {
		this.copperLevel = copperLevel;
	}

	public int getCopperXp() {
		return copperXp;
	}

	public void setCopperXp(int copperXp) {
		this.copperXp = copperXp;
	}

	public int getFireBowLevel() {
		return fireBowLevel;
	}

	public void setFireBowLevel(int fireBowLevel) {
		this.fireBowLevel = fireBowLevel;
	}

	public int getFireBowXp() {
		return fireBowXp;
	}

	public void setFireBowXp(int fireBowXp) {
		this.fireBowXp = fireBowXp;
	}

	public int getIceBowLevel() {
		return iceBowLevel;
	}

	public void setIceBowLevel(int iceBowLevel) {
		this.iceBowLevel = iceBowLevel;
	}

	public int getIceBowXp() {
		return iceBowXp;
	}

	public void setIceBowXp(int iceBowXp) {
		this.iceBowXp = iceBowXp;
	}
	
	
	
}
