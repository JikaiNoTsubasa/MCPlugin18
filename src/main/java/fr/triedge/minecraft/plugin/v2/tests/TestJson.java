package fr.triedge.minecraft.plugin.v2.tests;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonIOException;

import fr.triedge.minecraft.plugin.v2.utils.Utils;
import fr.triedge.minecraft.plugin.v2.warp.Warp;
import fr.triedge.minecraft.plugin.v2.warp.WarpList;

public class TestJson {

	public static void main(String[] args) {
		Warp w = new Warp();
		w.setName("Name1");
		w.setWorld("World");
		
		WarpList l = new WarpList();
		l.addWarp(w);
		try {
			Utils.storeJson(l, new File("save.json"));
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}

}
