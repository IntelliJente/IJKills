package pt.intellijente.kills.mysql;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import pt.intellijente.kills.Main;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnection {

    public static java.sql.Connection con = null;
    public static ConsoleCommandSender sc = Bukkit.getConsoleSender();
    private static Main getmain = Main.main;


    //creates connection
    public static void open() {

        String host = getmain.mysql.getString("Host");
        Integer port = getmain.mysql.getInt("Port");
        String database = getmain.mysql.getString("Database");
        String username = getmain.mysql.getString("Username");
        String password = getmain.mysql.getString("Password");

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database, username, password);
            sc.sendMessage("§aMySQL connected!");
        } catch (SQLException e) {
            sc.sendMessage("§cError setting up MySql check your information");
            getmain.getPluginLoader().disablePlugin(getmain);
        }

    }

    //close connection
    public static void close() {
        if (con != null) {
            try {
                con.close();
                sc.sendMessage("§aMySQL closed with sucess!");
            } catch (SQLException e) {
                e.printStackTrace();
                sc.sendMessage("§cTrying to close... Error");
            }
        }
    }

    //create table
    public static void createTable() {
        if (con != null) {

            PreparedStatement stm = null;

            try {
                stm = con.prepareStatement
                        ("CREATE TABLE IF NOT EXISTS `ijkills`(`player` VARCHAR(50), `nickname` VARCHAR(25), `kills` INT)");
                stm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                sc.sendMessage("§cError creating table");
            }

        }
    }

}
