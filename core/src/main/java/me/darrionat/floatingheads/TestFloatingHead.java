package me.darrionat.floatingheads;

import me.darrionat.floatingheads.skulls.SkullBuilder;
import me.darrionat.pluginlib.ErrorHandler;
import me.darrionat.pluginlib.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TestFloatingHead extends Plugin implements Listener {
    public static final String TIGER = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDZkZjM3OGJlZTY5ODBhNzg0NTkzYTFhZTk4OTI4MGE2ZTk2MjFhOTE5MTcyZGE0OWQzMTgxNWYzYzQ5Mjg2ZSJ9fX0=";
    private FloatingHead floatingHead;

    @Override
    public void initPlugin() {
        floatingHead = new FloatingHead(this, SkullBuilder.skullFromBase64(TIGER).getSkull());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        floatingHead.setSmall(true);
        floatingHead.create(e.getPlayer().getLocation());
        // FloatingHead floatingHead, Entity entity, double staticRadius, double moveSpeed, double hoverRadius, double hoverSpeed
        // FollowEntityAnimation followEntityAnimation = new FollowEntityAnimation(
        //   floatingHead, e.getPlayer(), 3, 2, 2, 2);
        // floatingHead.animate(followEntityAnimation, null);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
//        Player p = e.getPlayer();
//        Location pLoc = p.getLocation();
//        Location headLocation = floatingHead.getLocation();
//        double dist = pLoc.distance(headLocation);
//        if (dist >= 3 && !floatingHead.isAnimated()) {
//            MoveAnimation moveAnimation = new MoveAnimation(floatingHead, pLoc, 3);
//            floatingHead.animate(moveAnimation, null);
//        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }
}