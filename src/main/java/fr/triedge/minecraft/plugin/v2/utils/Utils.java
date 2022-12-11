package fr.triedge.minecraft.plugin.v2.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Utils {
	
	public static final String SYSLOG					= "SYSTEM: ";
	public static final String INFO						= "INFO: ";
	
	public static float getRequiredXp(int level) {
		return (level * 1.1f) + 50;
	}
	
	public static float getDamage(int level) {
		return (level * 0.1f) + 5;
	}
	
	public static boolean createFileIfNotExists(File file) throws IOException {
		file.getParentFile().mkdirs();
		return file.createNewFile(); // if file already exists will do nothing
	}
	
	public static boolean createFileIfNotExists(String fileName) throws IOException {
		return createFileIfNotExists(new File(fileName));
	}
	
	public static void log(JavaPlugin plugin, String message) {
		plugin.getLogger().log(Level.INFO, INFO+message);
	}
	
	public static void syslog(JavaPlugin plugin, Level level, String message) {
		plugin.getLogger().log(level, SYSLOG+message);
	}
	
	public static void syslog(JavaPlugin plugin, String message) {
		syslog(plugin, Level.INFO, message);
	}
	
	public static void playSound(Player player, Sound sound) {
		player.getWorld().playSound(player.getLocation(), sound, 10, 1);
	}
	
	public static int random(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public static boolean percent(int percent) {
		int rnd = random(0,100);
		return percent <= rnd;
	}
	
	public static Player getPlayer(String name) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}
	
	public static void decreaseItemFromInventory(String name, Player player) {
		PlayerInventory inv = player.getInventory();
		for (ItemStack stack : inv.getContents()) {
			if (stack == null)
				continue;
			if (stack.getItemMeta().getDisplayName().equals(name)) {
				int count = stack.getAmount();
				if (count <= 1) {
					inv.remove(stack);
				}else {
					stack.setAmount(count - 1);
				}
				break;
			}
		}
	}
	
	public static void decreaseItemFromInventory(ItemStack stack, Player player) {
		if (stack == null || player == null)
			return;
		PlayerInventory inv = player.getInventory();
		int count = stack.getAmount();
		if (count <= 1) {
			inv.remove(stack);
		}else {
			stack.setAmount(count - 1);
		}
	}
	
	public static long secondsToTicks(int seconds) {
		return (long) seconds * 20L;
	}
	
	public static void storeJson(Object obj, File file) throws JsonIOException, IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter w = new FileWriter(file);
		gson.toJson(obj, w);
		w.flush();
		w.close();
	}
	
	public static <T> T loadJson(Class<T> clazz, File file) throws JsonSyntaxException, JsonIOException, IOException {
		Gson gson = new Gson();
		FileReader r = new FileReader(file);
		T obj = (T) gson.fromJson(r, clazz);
		r.close();
		return obj;
	}

	/**
     * Generic method to store xml files based on classes with XML anotations
	 * @param <T>
     * 
     * @param element - The object to store as xml
     * @param file - The file path where to store the xml
     * @throws JAXBException
     */
	/*
	@Deprecated
    public static  <T> void storeXml(T element, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(element.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(element, file);
    }
    */
     
    /**
     * Generic method to load xml and transform it into an object which was declared with annotations
     * @param <T>
     * @param clazz - The class of the object in which we want the xml to be casted
     * @param file - The XML file located on the file system
     * @return An object converted from XML
     * @throws JAXBException
     */
	/*
    @SuppressWarnings("unchecked")
    @Deprecated
    public static  <T> T loadXml(Class<T> clazz, File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(file);
    }*/
    
    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
