package fr.triedge.minecraft.plugin.v2.warp;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="WarpGroup")
public class WarpGroup {

	private String name;
	private ArrayList<String> allowed = new ArrayList<>();
	
	public WarpGroup() {
	}
	
	public WarpGroup(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}

	@XmlAttribute(name="Name")
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getAllowed() {
		return allowed;
	}

	@XmlElementWrapper(name="AllowedList")
    @XmlElement(name="Allowed")
	public void setAllowed(ArrayList<String> allowed) {
		this.allowed = allowed;
	}
	
	public boolean isAllowed(String name) {
		for (String playerName : getAllowed()) {
			if (playerName.equals(name))
				return true;
		}
		return false;
	}
}
