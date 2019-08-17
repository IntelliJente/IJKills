package pt.intellijente.kills.eventsandcommands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pt.intellijente.kills.mysql.Methods;

public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Methods.contais(event.getPlayer())) {
            Methods.setPlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Methods.addKills(Bukkit.getPlayer(event.getEntity().getKiller().getUniqueId()), 1);

    }

}
