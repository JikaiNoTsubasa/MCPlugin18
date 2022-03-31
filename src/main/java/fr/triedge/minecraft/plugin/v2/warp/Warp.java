package fr.triedge.minecraft.plugin.v2.warp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Warp")
public class Warp {

	private String name, world, group = "none";
	private int locationX, locationY, locationZ;
	private float pitch, yaw;
	
	public Warp() {
	}
	
	public Warp(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	@XmlAttribute(name="Name")
	public void setName(String name) {
		this.name = name;
	}
	public String getWorld() {
		return world;
	}
	@XmlElement(name="World")
	public void setWorld(String world) {
		this.world = world;
	}
	public String getGroup() {
		return group;
	}
	@XmlElement(name="Group")
	public void setGroup(String group) {
		this.group = group;
	}
	public int getLocationX() {
		return locationX;
	}
	@XmlElement(name="LocationX")
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	public int getLocationY() {
		return locationY;
	}
	@XmlElement(name="LocationY")
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	public int getLocationZ() {
		return locationZ;
	}
	@XmlElement(name="LocationZ")
	public void setLocationZ(int locationZ) {
		this.locationZ = locationZ;
	}

	public float getYaw() {
		return yaw;
	}

	@XmlElement(name="Yaw")
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	@XmlElement(name="Pitch")
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
}
