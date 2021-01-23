package me.roopekoo.distance;

import org.bukkit. * ;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity. * ;
import org.bukkit.plugin.java.JavaPlugin;

public final class Distance extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("The Distance plugin started");

    }

    @Override
    public void onDisable() {
        System.out.println("The Distance plugin has been shut down.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("distance")) {
            if (sender instanceof Player) {
                if (args.length == 1) {
                    Player target1 = (Player) sender;
                    Player target2 = Bukkit.getPlayerExact(args[0]);
                    if (isTargetPlayer(target2, sender)) {
                        calcDistance(target1, target2, sender);
                    }
                }
                else if (args.length == 2) {
                    Player target1 = Bukkit.getPlayerExact(args[0]);
                    Player target2 = Bukkit.getPlayerExact(args[1]);
                    if (isTargetPlayer(target1, sender) && isTargetPlayer(target2, sender)) {
                        calcDistance(target1, target2, sender);
                    }
                }
                else {
                    sender.sendMessage("Incorrect amount of arguments! Expected 1 or 2, got " + args.length);
                }
            }
            else {
                if (args.length == 2) {
                    Player target1 = Bukkit.getPlayerExact(args[0]);
                    Player target2 = Bukkit.getPlayerExact(args[1]);
                    if (isTargetPlayer(target1, sender) && isTargetPlayer(target2, sender)) {
                        calcDistance(target1, target2, sender);
                    }
                }
                else {
                    System.out.println("Incorrect amount of arguments! Expected 2, got " + args.length);
                }
            }
        }
        return true;
    }

    private boolean isTargetPlayer(Player target, CommandSender sender) {
        if (target instanceof Player) {
            return true;
        }
        else {
            String message = "That player doesn't exist!";
            if (sender instanceof Player) {
                sender.sendMessage(message);
            }
            else {
                System.out.println(message);
            }
            return false;
        }
    }

    private void calcDistance(Player target1, Player target2, CommandSender sender) {
        Location loc1 = target1.getLocation();
        Location loc2 = target2.getLocation();
        if (loc1.getWorld() != loc2.getWorld()) {
            String message = "Players are not in the same world!";
            if (sender instanceof Player) {
                sender.sendMessage(message);
            }
            else {
                System.out.println(message);
            }
        }
        else {
            int distance = (int) loc1.distance(loc2);
            String message = "The distance between " + target1.getName() + " and " + target2.getName() + " is " + distance + " meters.";
            if (sender instanceof Player) {
                sender.sendMessage(message);
            }
            else {
                System.out.println(message);
            }
        }
    }
}