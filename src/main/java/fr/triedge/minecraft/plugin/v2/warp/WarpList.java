package fr.triedge.minecraft.plugin.v2.warp;

import java.util.ArrayList;

public class WarpList {

	private ArrayList<Warp> warps = new ArrayList<>();
	private ArrayList<WarpGroup> warpGroups = new ArrayList<>();
	
	public WarpList() {
		WarpGroup none = new WarpGroup("none");
		getWarpGroups().add(none);
	}

	public ArrayList<Warp> getWarps() {
		return warps;
	}

	public void setWarps(ArrayList<Warp> warps) {
		this.warps = warps;
	}
	
	public Warp getWarp(String name) {
		for (Warp w : getWarps())
			if (w.getName().equals(name))
				return w;
		return null;
	}
	
	public void addWarp(Warp warp) {
		// Verify if warp exist, remove it
		Warp tmp = getWarp(warp.getName());
		if (tmp != null) {
			getWarps().remove(tmp);
		}
		getWarps().add(warp);
	}
	
	public WarpGroup getGroup(String name) {
		for (WarpGroup w : getWarpGroups())
			if (w.getName().equals(name))
				return w;
		return null;
	}
	
	public void addWarGroup(WarpGroup warp) {
		// Verify if warpGroup exist, remove it
		WarpGroup tmp = getGroup(warp.getName());
		if (tmp != null) {
			getWarpGroups().remove(tmp);
		}
		getWarpGroups().add(warp);
	}

	public ArrayList<WarpGroup> getWarpGroups() {
		return warpGroups;
	}

	public void setWarpGroups(ArrayList<WarpGroup> warpGroups) {
		this.warpGroups = warpGroups;
	}
}
