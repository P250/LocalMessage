package me.johnny.localmessage.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.johnny.localmessage.Main;

public class LocalMessageCommand implements CommandExecutor {
	
	private Main plugin;
	private Player pl;
	
	public LocalMessageCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	private String cc(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "You need to be ingame to use this command!");
			return false;
		}
		
		pl = (Player) sender;
		
		if (args.length == 0 || (args.length == 1)) {
			String usage = plugin.getConfig().getString("localmessage.command.usage");
			pl.sendMessage(cc(usage));
			return false;
		} 
		
		try {
			if (Integer.parseInt(args[0]) > 20) {
				String error = plugin.getConfig().getString("localmessage.command.maxradius_limit");
				String finalError = error.replaceAll("[{][m][a][x][r][a][d][i][u][s][}]", String.valueOf(plugin.getConfig().getInt("localmessage.command.maxradius")));
				pl.sendMessage(cc(finalError));
				return false;
			} else if (Integer.parseInt(args[0].toString()) < 1) {
				String error = plugin.getConfig().getString("localmessage.command.maxradius_min");
				pl.sendMessage(cc(error));
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String usage = plugin.getConfig().getString("localmessage.command.usage");
			pl.sendMessage(cc(usage));
 			return false;
		}
				
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getUniqueId() == pl.getUniqueId()) {
				continue;
			}
			
			if (pl.getLocation().distance(p.getLocation()) <= (double) Integer.parseInt(args[0])) {	
				StringBuilder b = new StringBuilder(256);
				for (int i = 1; i < args.length; i++) {
					b.append(args[i] + " ");
				}
				p.sendMessage(cc(b.toString()));
			}
		}
		pl.sendMessage(cc(plugin.getConfig().getString("localmessage.command.success")));
		return false;
	}

}
