package pt.intellijente.kills.mysql;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pt.intellijente.kills.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Methods {

    public static ConsoleCommandSender sc = Bukkit.getConsoleSender();


    //verify if contais player
    public static boolean contais(Player player) {
        PreparedStatement stm = null;

        try {
            stm = MySQLConnection.con.prepareStatement("SELECT * FROM `ijkills` WHERE `player` = ?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    // creates player
    public static void setPlayer(Player player) {
        PreparedStatement stm = null;
        try {
            stm = MySQLConnection.con.prepareStatement("INSERT INTO `ijkills`(`player`, `nickname`, `kills`) VALUES (?,?,?)");
            stm.setString(1, player.getUniqueId().toString());
            stm.setString(2, player.getName());
            if (Main.main.mysql.getBoolean("start-with-0-kills")) {
                stm.setInt(3, 0);
            } else {
                stm.setInt(3, player.getStatistic(Statistic.PLAYER_KILLS));
            }
            stm.executeUpdate();
            sc.sendMessage("§aPlayer §7" + player.getName() + " §acreated");
        } catch (SQLException e) {
            sc.sendMessage("§cError creating §7" + player.getName());
        }
    }

    // set player kills
    public static void setKills(Player player, Integer i) {
        try {
            PreparedStatement stm = MySQLConnection.con.prepareStatement("UPDATE `ijkills` SET `kills` = ? WHERE `player` = ?");
            stm.setInt(1, i);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get player kills
    public static int getKills(Player player) {
        try {
            PreparedStatement stm = MySQLConnection.con.prepareStatement("SELECT * FROM `ijkills` WHERE `player` = ?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("kills");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //addkills player
    public static void addKills(Player player, Integer i) {
        if (contais(player)) {
            setKills(player, getKills(player) + i);
        } else {
            setPlayer(player);
        }
    }


    //remove playerkills
    public static void removeKills(Player player, Integer i) {
        if (contais(player)) {
            setKills(player, getKills(player) - i);
        } else {
            setPlayer(player);
        }
    }


    //get top 3
    public static List<String> getTops() {

        List<String> tops = new ArrayList<String>();

        try {
            PreparedStatement stm = MySQLConnection.con.prepareStatement("SELECT * FROM `ijkills` ORDER BY `kills` DESC");
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (i <= 2) {
                    i++;
                    tops.add(rs.getString("nickname")+ ": §e" + rs.getInt("kills"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tops;
    }

    //get top 3 nicks
    public static List<String> getTopsNick() {

        List<String> tops = new ArrayList<String>();

        try {
            PreparedStatement stm = MySQLConnection.con.prepareStatement("SELECT * FROM `ijkills` ORDER BY `kills` DESC");
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (i <= 2) {
                    i++;
                    tops.add(rs.getString("nickname"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tops;
    }


}
