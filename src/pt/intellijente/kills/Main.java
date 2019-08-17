package pt.intellijente.kills;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import net.citizensnpcs.util.NMS;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pt.intellijente.kills.eventsandcommands.Commands;
import pt.intellijente.kills.eventsandcommands.Events;
import pt.intellijente.kills.mysql.Methods;
import pt.intellijente.kills.mysql.MySQLConnection;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    //Get main class
    public static Main main;

    //Configs
    public Configs mysql;
    public Configs npcs;
    public Configs messages;
    public Configs commands;

    //Arrays to destroy npcs and holograms
    public ArrayList<NPC> getnpcs = new ArrayList();
    public ArrayList<Hologram> holograms = new ArrayList();

    @Override
    public void onEnable() {

        //Get main class
        main = this;

        //Save Configs
        npcs = new Configs("npcs.yml");
        npcs.saveDefaultConfig();
        commands = new Configs("commands.yml");
        commands.saveDefaultConfig();
        messages = new Configs("messages.yml");
        messages.saveDefaultConfig();
        mysql = new Configs("mysql.yml");
        mysql.saveDefaultConfig();


        //Connects to MYSQL
        MySQLConnection.open();
        MySQLConnection.createTable();

        //Register Commands and Events
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        getCommand("ijkills").setExecutor(new Commands());
        getCommand("ijkills-admin").setExecutor(new Commands());

        //Try to spawn NPCS
        try {
            makeTop();
            makeTop2();
            makeTop3();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("No NPCS found");
        }

    }

    @Override
    public void onDisable() {
        //Deletes NPCs and Holograms (onEnable they spawn again)
        getnpcs.forEach(NPC::destroy);
        holograms.forEach(Hologram::delete);

        //Close mysql
        MySQLConnection.close();

    }

    //Spawn top1 npc
    public static void makeTop() {
        //get location and load chunks
        Location location = main.npcs.getLocation("top1");
        Chunk c = location.getChunk();
        c.load();

        //Create Npc and Spawn
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC top = registry.createNPC(EntityType.PLAYER, "");
        main.getnpcs.add(top);
        top.spawn(location);

        //Create Hologram
        location.add(0.0D, 0.5D, 0.0D);
        com.gmail.filoghost.holographicdisplays.api.Hologram tophd = HologramsAPI.createHologram(Main.main,
                location.add(0, 1.37, 0));
        tophd.insertTextLine(0, main.messages.message("npcs.top1.hd1"));
        tophd.insertTextLine(1, main.messages.message("npcs.top1.hd2"));
        tophd.insertTextLine(2, Methods.getTops().get(0));
        main.holograms.add(tophd);

        //Verify new top
        (new BukkitRunnable() {
            public void run() {
                if (top.isSpawned()) {
                    SkinnableEntity skinnable = NMS.getSkinnable(top.getEntity());
                    if (skinnable != null) {
                        skinnable.setSkinName(Methods.getTopsNick().get(0));
                    }
                }
                if (tophd.isDeleted()) {
                    return;
                }
                tophd.getLine(2).removeLine();
                tophd.insertTextLine(2, Methods.getTops().get(0));
            }
        }).runTaskTimer(Main.main, 20 * main.messages.getInt("npcs.verify-time"), 20 * main.messages.getInt("npcs.verify-time"));
    }

    //spawn top2
    public static void makeTop2() {
        Location location = main.npcs.getLocation("top2");
        Chunk c = location.getChunk();
        c.load();
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC top2 = registry.createNPC(EntityType.PLAYER, "");
        main.getnpcs.add(top2);
        top2.spawn(location);
        location.add(0.0D, 0.5D, 0.0D);
        com.gmail.filoghost.holographicdisplays.api.Hologram top2hd = HologramsAPI.createHologram(Main.main,
                location.add(0, 1.1, 0));
        top2hd.insertTextLine(0, main.messages.message("npcs.top2.hd"));
        top2hd.insertTextLine(1, Methods.getTops().get(1));

        main.holograms.add(top2hd);
        (new BukkitRunnable() {
            public void run() {
                if (top2.isSpawned()) {
                    SkinnableEntity skinnable = NMS.getSkinnable(top2.getEntity());
                    if (skinnable != null) {
                        skinnable.setSkinName(Methods.getTopsNick().get(1));
                    }
                }
                if (top2hd.isDeleted()) {
                    return;
                }
                top2hd.getLine(1).removeLine();
                top2hd.insertTextLine(1, Methods.getTops().get(1));
            }
        }).runTaskTimer(Main.main, 20 * main.messages.getInt("npcs.verify-time"), 20 * main.messages.getInt("npcs.verify-time"));
    }

    //spawn top3
    public static void makeTop3() {
        Location location = main.npcs.getLocation("top3");
        Chunk c = location.getChunk();
        c.load();
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        NPC top3 = registry.createNPC(EntityType.PLAYER, "");
        main.getnpcs.add(top3);
        top3.spawn(location);
        location.add(0.0D, 0.5D, 0.0D);
        com.gmail.filoghost.holographicdisplays.api.Hologram top3hd = HologramsAPI.createHologram(Main.main,
                location.add(0, 1.1, 0));
        top3hd.insertTextLine(0, main.messages.message("npcs.top3.hd"));
        top3hd.insertTextLine(1, Methods.getTops().get(2));

        main.holograms.add(top3hd);
        (new BukkitRunnable() {
            public void run() {
                if (top3.isSpawned()) {
                    SkinnableEntity skinnable = NMS.getSkinnable(top3.getEntity());
                    if (skinnable != null) {
                        skinnable.setSkinName(Methods.getTopsNick().get(2));
                    }
                }
                if (top3hd.isDeleted()) {
                    return;
                }
                top3hd.getLine(1).removeLine();
                top3hd.insertTextLine(1, Methods.getTops().get(2));
            }
        }).runTaskTimer(Main.main, 20 * main.messages.getInt("npcs.verify-time"), 20 * main.messages.getInt("npcs.verify-time"));
    }



    //Get main class
    public static Main getPlugin() {
        return (Main) getPlugin(Main.class);
    }

}
