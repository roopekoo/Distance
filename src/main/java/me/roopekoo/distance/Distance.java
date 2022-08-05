package me.roopekoo.distance;

import com.sun.tools.sjavac.Log;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class Distance extends JavaPlugin {

	private static double FastSqrt(double number) {
		double x = number;
		double xhalf = 0.5d*x;
		long i = Double.doubleToLongBits(x);
		i = 0x5fe6ec85e7de30daL-(i >> 1);
		x = Double.longBitsToDouble(i);
		for(int it = 0; it<4; it++) {
			x = x*(1.5d-xhalf*x*x);
		}
		x *= number;
		return x;
	}

	@Override public void onEnable() {
		Bukkit.getLogger().log(Level.INFO, "The Distance plugin started");

	}

	@Override public void onDisable() {
		Bukkit.getLogger().log(Level.INFO, "The Distance plugin has been shut down.");
	}

	@Override public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
	{
		if(command.getName().equalsIgnoreCase("distance"))
		{
			if(args.length>2)
			{
				return null;
			}
			List<String> list = new ArrayList<>();
			for(Player p: Bukkit.getOnlinePlayers()) {
				list.add(p.getName());
			}
			return list;
		}
		return null;
	}

	@Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equals("distance")) {
			if(sender instanceof Player) {
				if(args.length == 1) {
					Player target1 = (Player) sender;
					Player target2 = Bukkit.getPlayerExact(args[0]);
					if(isTargetPlayer(target2, sender)) {
						assert target2 != null;
						calcDistance(target1, target2, sender);
					}
				} else if(args.length == 2) {
					Player target1 = Bukkit.getPlayerExact(args[0]);
					Player target2 = Bukkit.getPlayerExact(args[1]);
					if(isTargetPlayer(target1, sender) && isTargetPlayer(target2, sender)) {
						assert target1 != null;
						assert target2 != null;
						calcDistance(target1, target2, sender);
					}
				} else {
					sender.sendMessage("Incorrect amount of arguments! Expected 1 or 2, got "+args.length);
				}
			} else {
				if(args.length == 2) {
					Player target1 = Bukkit.getPlayerExact(args[0]);
					Player target2 = Bukkit.getPlayerExact(args[1]);
					if(isTargetPlayer(target1, sender) && isTargetPlayer(target2, sender)) {
						assert target1 != null;
						assert target2 != null;
						calcDistance(target1, target2, sender);
					}
				} else {
					Bukkit.getLogger()
					      .log(Level.WARNING, "Incorrect amount of arguments! Expected 2, got "+args.length);
				}
			}
		}
		return true;
	}

	private boolean isTargetPlayer(Player target, CommandSender sender) {
		if(target != null) {
			return true;
		} else {
			String message = "That player doesn't exist!";
			sender.sendMessage(message);
			return false;
		}
	}

	private void calcDistance(Player target1, Player target2, CommandSender sender) {
		Location loc1 = target1.getLocation();
		Location loc2 = target2.getLocation();
		if(loc1.getWorld() != loc2.getWorld()) {
			String message = "Players are not in the same world!";
			sender.sendMessage(message);
		} else {
			double x = loc1.getX()-loc2.getX();
			double y = loc1.getY()-loc2.getY();
			double z = loc1.getZ()-loc2.getZ();
			double distSqrt = x*x+y*y+z*z;
			double distance = FastSqrt(distSqrt);
			String message = "The distance between "+target1.getName()+" and "+target2.getName()+" is "+
			                 String.format("%.2f", distance)+" meters.";
			sender.sendMessage(message);
		}
	}
}