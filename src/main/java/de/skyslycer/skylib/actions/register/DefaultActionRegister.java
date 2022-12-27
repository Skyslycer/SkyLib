package de.skyslycer.skylib.actions.register;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetTitleSubtitle;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetTitleText;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetTitleTimes;
import com.owen1212055.particlehelper.api.particle.MultiParticle;
import com.owen1212055.particlehelper.api.particle.Particle;
import com.owen1212055.particlehelper.api.particle.types.BlockDataParticle;
import com.owen1212055.particlehelper.api.particle.types.ColorableParticle;
import com.owen1212055.particlehelper.api.particle.types.DelayableParticle;
import com.owen1212055.particlehelper.api.particle.types.DestinationParticle;
import com.owen1212055.particlehelper.api.particle.types.MaterialParticle;
import com.owen1212055.particlehelper.api.particle.types.RollableParticle;
import com.owen1212055.particlehelper.api.particle.types.SizeableParticle;
import com.owen1212055.particlehelper.api.particle.types.SpeedModifiableParticle;
import com.owen1212055.particlehelper.api.particle.types.dust.transition.TransitionDustParticle;
import com.owen1212055.particlehelper.api.particle.types.note.MultiNoteParticle;
import com.owen1212055.particlehelper.api.particle.types.velocity.VelocityParticle;
import com.owen1212055.particlehelper.api.particle.types.vibration.VibrationParticle;
import com.owen1212055.particlehelper.api.type.Particles;
import de.skyslycer.skylib.util.StringUtil;
import de.skyslycer.skylib.SkyPlugin;
import de.skyslycer.skylib.actions.information.ActionInformation;
import java.math.BigInteger;
import java.util.Arrays;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DefaultActionRegister {

    private final SkyPlugin plugin;

    public DefaultActionRegister(SkyPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        registerParticle();
        registerParticleMulti();
        registerSound();
        registerTitle();
        registerSubtitle();
        registerActionBar();
        registerMessage();
        registerCommand();
        registerConsoleCommand();
    }

    private void registerParticle() {
        plugin.actionHandler().subscribe("PARTICLE", (information) -> doParticle(information, false));
    }

    private void registerParticleMulti() {
        plugin.actionHandler().subscribe("PARTICLE_MULTI", (information) -> doParticle(information, true));
    }

    private void doParticle(ActionInformation information, boolean multi) {
        var split = information.getArguments().split(" ");
        if (checkSplit(split, 1, multi ? "particle multi" : "particle", multi ? "heart 10 0.1 0.1 0.1" : "heart")) {
            return;
        }
        var particleType = Particles.fromKey(NamespacedKey.minecraft(split[0].toLowerCase()));
        if (particleType == null) {
            plugin.getLogger().warning("The particle " + split[0] + " does not exist!");
            return;
        }
        var particle = multi ? particleType.multi() : particleType.single();
        if (particle instanceof DestinationParticle || particle instanceof BlockDataParticle
                || particle instanceof VibrationParticle || particle instanceof VelocityParticle) {
            plugin.getLogger().warning("The particle " + split[0] + " is not supported by this action!");
            return;
        }
        particle = addParticleValues(particle, split);
        particle.compile().send(information.getPlayer(), information.getPlayer().getLocation());
    }

    private BigInteger getBigInteger(String string) {
        try {
            return new BigInteger(string);
        } catch (Exception e) {
            return BigInteger.valueOf(1);
        }
    }

    private Particle addParticleValues(Particle particle, String[] split) {
        var counter = 1;
        if (particle instanceof MultiParticle multiParticle) {
            multiParticle.setCount(getBigInteger(split[counter]).intValue());
            counter++;
            multiParticle.setXOffset(getBigInteger(split[counter]).floatValue());
            counter++;
            multiParticle.setYOffset(getBigInteger(split[counter]).floatValue());
            counter++;
            multiParticle.setZOffset(getBigInteger(split[counter]).floatValue());
            counter++;
            if (multiParticle instanceof MultiNoteParticle multiNoteParticle) {
                multiNoteParticle.setColorMultplier(getBigInteger(split[counter]).intValue());
                counter++;
            }
        }
        if (particle instanceof ColorableParticle colorableParticle && StringUtil.colorFromString(split[counter]) != null) {
            colorableParticle.setColor(StringUtil.colorFromString(split[counter]));
            counter++;
        }
        if (particle instanceof TransitionDustParticle transitionDustParticle && StringUtil.colorFromString(split[counter]) != null) {
            transitionDustParticle.setFadeColor(StringUtil.colorFromString(split[counter]));
            counter++;
        }
        if (particle instanceof MaterialParticle materialParticle && Material.getMaterial(split[counter]) != null) {
            materialParticle.setMaterial(Material.getMaterial(split[counter]));
            counter++;
        }
        if (particle instanceof SpeedModifiableParticle speedModifiableParticle) {
            speedModifiableParticle.setSpeed(getBigInteger(split[counter]).floatValue());
            counter++;
        }
        if (particle instanceof DelayableParticle delayableParticle) {
            delayableParticle.setDelay(getBigInteger(split[counter]).intValue());
            counter++;
        }
        if (particle instanceof SizeableParticle sizeableParticle) {
            sizeableParticle.setSize(getBigInteger(split[counter]).floatValue());
            counter++;
        }
        if (particle instanceof RollableParticle rollableParticle) {
            rollableParticle.setRoll(getBigInteger(split[counter]).floatValue());
        }
        return particle;
    }

    private void registerSound() {
        plugin.actionHandler().subscribe("SOUND", (information) -> {
            var player = information.getPlayer();
            var split = information.getArguments().split(" ");
            if (checkSplit(split, 1, "sound", "ENTITY_VILLAGER_HURT")) return;
            var sound = Sound.ENTITY_VILLAGER_HURT;
            try {
                sound = Sound.valueOf(split[0]);
            } catch (IllegalArgumentException ignored) {
                plugin.getLogger().warning("The sound " + split[0] + " is not a valid sound! Example: ENTITY_VILLAGER_HURT (action configuration)");
            }
            if (split.length == 3) {
                try {
                    player.playSound(player.getLocation(), sound, Float.parseFloat(split[1]), Float.parseFloat(split[2]));
                    return;
                } catch (NumberFormatException exception) {
                    plugin.getLogger().warning("The sound " + split[1] + " or " + split[2] + " is not a valid float number! Example: 1.0 (action configuration)");
                }
            }
            player.playSound(player.getLocation(), sound, 1, 1);
        });
    }

    private void registerTitle() {
        plugin.actionHandler().subscribe("TITLE", (information -> {
            var player = information.getPlayer();
            var split = information.getArguments().split(" ");
            if (checkSplit(split, 4, "title", "0.5 4.0 1.0 message")) return;
            var message = String.join(" ", Arrays.copyOfRange(split, 3, split.length));
            setTitleTimes(player, split);
            if (plugin.data().packets()) {
                PacketEvents.getAPI().getPlayerManager()
                        .sendPacket(player, new WrapperPlayServerSetTitleText(StringUtil.parseComponent(player, parseMessage(information, message))));
            }
        }));
    }

    private void registerSubtitle() {
        plugin.actionHandler().subscribe("SUBTITLE", (information -> {
            var player = information.getPlayer();
            var split = information.getArguments().split(" ");
            if (checkSplit(split, 4, "title", "0.5 4.0 1.0")) return;
            var message = String.join(" ", Arrays.copyOfRange(split, 3, split.length));
            setTitleTimes(player, split);
            if (plugin.data().packets()) {
                PacketEvents.getAPI().getPlayerManager().sendPacket(player,
                        new WrapperPlayServerSetTitleSubtitle(StringUtil.parseComponent(player, parseMessage(information, message))));
            }
        }));
    }

    private void setTitleTimes(Player player, String[] split) {
        var fadeIn = 5;
        var hold = 40;
        var fadeOut = 10;
        try {
            fadeIn = Math.round(Float.parseFloat(split[0]) * 20);
            hold = Math.round(Float.parseFloat(split[1]) * 20);
            fadeOut = Math.round(Float.parseFloat(split[2]) * 20);
        } catch (NumberFormatException exception) {
            plugin.getLogger().warning("The title duration " + split[0] + " or " + split[1] + " or " + split[2] + " is not a valid float number! Example: 1.0 (action configuration)");
        }
        if (plugin.data().packets()) {
            PacketEvents.getAPI().getPlayerManager().sendPacket(player, new WrapperPlayServerSetTitleTimes(fadeIn, hold, fadeOut));
        }
    }

    private void registerActionBar() {
        plugin.actionHandler().subscribe("ACTIONBAR", (information -> {
            if (checkSplit(information.getArguments().split(" "), 1, "actionbar", "message")) return;
            information.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, StringUtil.parse(information.getPlayer(), parseMessage(information)));
        }));
    }

    private void registerMessage() {
        plugin.actionHandler().subscribe("MESSAGE", (information) -> {
            if (checkSplit(information.getArguments().split(" "), 1, "message", "message")) return;
            StringUtil.send(information.getPlayer(), parseMessage(information));
        });
    }

    private void registerCommand() {
        plugin.actionHandler().subscribe("COMMAND", (information) -> {
            if (checkSplit(information.getArguments().split(" "), 1, "command", "say I love this plugin!")) return;
            var player = information.getPlayer();
            Bukkit.dispatchCommand(player, parseCommand(information));
        });
    }

    private void registerConsoleCommand() {
        plugin.actionHandler().subscribe("CONSOLE_COMMAND", (information) -> {
            if (checkSplit(information.getArguments().split(" "), 1, "console command", "kill <player>")) return;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parseCommand(information));
        });
    }

    private boolean checkSplit(String[] split, int length, String action, String example) {
        if (split.length < length) {
            plugin.getLogger().warning("The " + action + " action needs at least " + length + " arguments! Example: " + example + " (action configuration)");
            return true;
        }
        return false;
    }

    private String parseCommand(ActionInformation information) {
        var string = parseMessage(information);
        if (string.startsWith("/")) {
            string = string.substring(1);
        }
        return string;
    }

    private String parseMessage(ActionInformation information, String message) {
        var player = information.getPlayer();
        return StringUtil.replacePlaceholders(player, message.replace("<player>", player.getName()));
    }

    private String parseMessage(ActionInformation information) {
        return parseMessage(information, information.getArguments());
    }

}
