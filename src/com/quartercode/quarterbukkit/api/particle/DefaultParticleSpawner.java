
package com.quartercode.quarterbukkit.api.particle;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.protocol.Packet;
import com.quartercode.quarterbukkit.api.protocol.PacketType;
import com.quartercode.quarterbukkit.api.reflect.BukkitServer;
import com.quartercode.quarterbukkit.api.reflect.MethodAccessor;
import com.quartercode.quarterbukkit.api.reflect.SafeMethod;
import com.quartercode.quarterbukkit.api.util.PlayerUtil;

/**
 * This is a default {@link ParticleSpawner} which executes the particle effects using fireworks.
 * The method spawns a new {@link Firework}, let it explode directly after that using reflection and then removes the {@link Entity}.
 */
public class DefaultParticleSpawner implements ParticleSpawner {

    @Override
    public void spawn(final Plugin plugin, final List<ParticleDescription> descriptions, final Location location, List<Player> players) {

        final Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

        MethodAccessor<Object> fw = new SafeMethod<Object>(firework.getClass(), "getHandle");
        MethodAccessor<Void> set = new SafeMethod<Void>(BukkitServer.getNMSClass("Entity"), "setInvisible", boolean.class);
        set.invoke(fw.invoke(firework), true);

        final FireworkMeta meta = firework.getFireworkMeta();

        meta.clearEffects();

        for (final ParticleDescription description : descriptions) {
            final Builder builder = FireworkEffect.builder();
            builder.with(description.getShape().getFireworkType());
            builder.withColor(description.getColors().toArray(new Color[description.getColors().size()]));
            builder.withFade(description.getFadeColors().toArray(new Color[description.getFadeColors().size()]));
            meta.addEffect(builder.build());
        }

        meta.setPower(1);
        firework.setFireworkMeta(meta);

        Packet packet = new Packet(PacketType.ENTITY_STATUS);
        packet.write("a", firework.getEntityId());
        packet.write("b", (byte) 17);

        if (players.isEmpty()) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerUtil.sendPacket(player, packet);
            }

        } else {

            for (Player player : players) {
                PlayerUtil.sendPacket(player, packet);
            }
        }

        firework.remove();
    }
}
