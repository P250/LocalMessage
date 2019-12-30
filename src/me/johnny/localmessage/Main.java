package me.johnny.localmessage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.johnny.localmessage.commands.LocalMessageCommand;

public class Main extends JavaPlugin {
	
	private File configFile;
	private YamlConfiguration config;
	private HashMap<String, Object> defaults;
	
	private void loadConfig() {
		configFile = new File(getDataFolder(), "config.yml");
		
		if (!(configFile.exists())) {
			defaults = new HashMap<String, Object>();
			
			getConfig().options().header("Use placeholder {maxradius} to replace it with the commands maximum range.");
			defaults.put("localmessage.command.usage", "&4Usage: /lm <radius> <message>");
			defaults.put("localmessage.command.maxradius_limit", "&4Error, you can't send a message more than {maxradius} blocks away");
			defaults.put("localmessage.command.maxradius_min", "&4Error, you can't send a message less than 0 blocks away");
			defaults.put("localmessage.command.maxradius", 20);
			defaults.put("localmessage.command.success", "&5Successfully sent message");
			
			getConfig().addDefaults(defaults);
			getConfig().options().copyHeader(true);
			getConfig().options().copyDefaults(true);
			
			saveConfig();
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	@Override
	public void onEnable() {
		loadConfig();
		
		getCommand("lm").setExecutor(new LocalMessageCommand(this));
		getCommand("lr").setExecutor(new LocalMessageCommand(this));
		getCommand("localmessage").setExecutor(new LocalMessageCommand(this));
		getCommand("localraw").setExecutor(new LocalMessageCommand(this));

	}
	
	@Override
	public void onDisable() {
		saveConfig();
	}

}
