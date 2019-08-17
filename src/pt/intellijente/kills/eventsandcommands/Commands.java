package pt.intellijente.kills.eventsandcommands;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.intellijente.kills.Configs;
import pt.intellijente.kills.Main;
import pt.intellijente.kills.mysql.Methods;

import java.util.List;

public class Commands implements CommandExecutor {

    //getting configs
    private static Main getmain = Main.main;
    private Configs commands = (Configs) getmain.commands;


    //get if args[2] is int
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // console
        if (!(sender instanceof Player)) {
            sender.sendMessage(getmain.messages.getString("console-command").replaceAll("&", "§"));
            return true;
        }
        Player player = (Player) sender;

        //commands
        if (cmd.getName().equalsIgnoreCase("ijkills")) {

            if (args.length == 0) {
                List<String> list = commands.getStringList("command-help-user");
                for (String messages : list) {
                    player.sendMessage(messages.replaceAll("&", "§")
                            .replace("%see-your-kills-command%", commands.getString("commands.see-your-kills.name"))
                            .replace("%set-kills-command%", commands.getString("commands.set-kills.name"))
                            .replace("%player-kills-command%", commands.getString("commands.player-kills.name")));
                }
                return true;
            }


            if (args[0].equalsIgnoreCase(commands.getString("commands.see-your-kills.name"))) {
                player.sendMessage(getmain.messages.message("your-kills")
                        .replace("%kills%", "" + Methods.getKills(player)));
                return true;
            }

            if (args[0].equalsIgnoreCase(commands.getString("commands.player-kills.name"))) {
                if (args.length == 1) {
                    player.sendMessage(commands.message("commands.player-kills.message")
                            .replace("%player-kills-command%", commands.getString("commands.player-kills.name")));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    player.sendMessage(getmain.messages.message("see-player-kills")
                            .replace("%kills%", "" + Methods.getKills(target))
                            .replace("%player%", target.getName()));
                    return true;
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase("ijkills-admin")) {
            Player player2 = (Player) sender;
            if (args.length == 0) {
                List<String> list = commands.getStringList("command-help-admin");
                for (String messages : list) {
                    player2.sendMessage(messages.replaceAll("&", "§")
                            .replace("%set-kills-command%", commands.getString("commands.set-kills.name"))
                            .replace("%add-kills-command%", commands.getString("commands.add-kills.name"))
                            .replace("%remove-kills-command%", commands.getString("commands.remove-kills.name"))
                            .replace("%spawn-top-1-command%", commands.getString("commands.spawn-top-1.name"))
                            .replace("%spawn-top-2-command%", commands.getString("commands.spawn-top-2.name"))
                            .replace("%spawn-top-3-command%", commands.getString("commands.spawn-top-3.name"))
                            .replace("%remove-npcs-command%", commands.getString("commands.remove-npcs.name")));
                }
                return true;
            }
            if (args[0].equalsIgnoreCase(commands.getString("commands.set-kills.name"))) {
                if (args.length != 1) {
                    if (args.length != 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (isInt(args[2])) {
                            Methods.setKills(target, Integer.valueOf(args[2]));
                            player.sendMessage(getmain.messages.message("set-kills-admin")
                                    .replace("%int%", args[2])
                                    .replace("%player%", target.getName()));
                            target.sendMessage(getmain.messages.message("set-kills-player")
                                    .replace("%int%", args[2]));
                            return true;
                        } else {
                            player.sendMessage(commands.message("not-int").replace("%int%", args[2]));
                            return true;
                        }
                    } else {
                        player.sendMessage(commands.message("commands.set-kills.message")
                                .replace("%set-kills-command%", commands.getString("commands.set-kills.name")));
                        return true;
                    }
                } else {
                    player.sendMessage(commands.message("commands.set-kills.message")
                            .replace("%set-kills-command%", commands.getString("commands.set-kills.name")));
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase(commands.getString("commands.add-kills.name"))) {
                if (args.length != 1) {
                    if (args.length != 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (isInt(args[2])) {
                            Methods.addKills(target, Integer.valueOf(args[2]));
                            player.sendMessage(getmain.messages.message("add-kills-admin")
                                    .replace("%int%", args[2])
                                    .replace("%player%", target.getName()));
                            target.sendMessage(getmain.messages.message("add-kills-player")
                                    .replace("%int%", args[2]));
                            return true;
                        } else {
                            player.sendMessage(commands.message("not-int").replace("%int%", args[2]));
                            return true;
                        }
                    } else {
                        player.sendMessage(commands.message("commands.add-kills.message")
                                .replace("%add-kills-command%", commands.getString("commands.add-kills.name")));
                        return true;
                    }
                } else {
                    player.sendMessage(commands.message("commands.add-kills.message")
                            .replace("%add-kills-command%", commands.getString("commands.add-kills.name")));
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase(commands.getString("commands.remove-kills.name"))) {
                if (args.length != 1) {
                    if (args.length != 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (isInt(args[2])) {
                            Methods.removeKills(target, Integer.valueOf(args[2]));
                            player.sendMessage(getmain.messages.message("remove-kills-admin")
                                    .replace("%int%", args[2])
                                    .replace("%player%", target.getName()));
                            target.sendMessage(getmain.messages.message("remove-kills-player")
                                    .replace("%int%", args[2]));
                            return true;
                        } else {
                            player.sendMessage(commands.message("not-int").replace("%int%", args[2]));
                            return true;
                        }
                    } else {
                        player.sendMessage(commands.message("commands.remove-kills.message")
                                .replace("%remove-kills-command%", commands.getString("commands.remove-kills.name")));
                        return true;
                    }
                } else {
                    player.sendMessage(commands.message("commands.remove-kills.message")
                            .replace("%remove-kills-command%", commands.getString("commands.remove-kills.name")));
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase(commands.getString("commands.spawn-top-1.name"))) {
                Main.main.npcs.setLocation("top1", player.getLocation());
                Main.main.npcs.saveConfig();
                Main.makeTop();
                return true;
            }
            if (args[0].equalsIgnoreCase(commands.getString("commands.spawn-top-2.name"))) {
                Main.main.npcs.setLocation("top2", player.getLocation());
                Main.main.npcs.saveConfig();
                Main.makeTop2();
                return true;
            }
            if (args[0].equalsIgnoreCase(commands.getString("commands.spawn-top-3.name"))) {
                Main.main.npcs.setLocation("top3", player.getLocation());
                Main.main.npcs.saveConfig();
                Main.makeTop3();
                return true;
            }

            if (args[0].equalsIgnoreCase(commands.getString("commands.remove-npcs.name"))) {
                Main.main.npcs.delete();
                Main.main.holograms.forEach(Hologram::delete);
                Main.main.getnpcs.forEach(NPC::destroy);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                commands.reloadConfig();
                getmain.messages.reloadConfig();
                player.sendMessage(getmain.messages.message("reload-config"));
            }

        }

        return false;
    }
}
