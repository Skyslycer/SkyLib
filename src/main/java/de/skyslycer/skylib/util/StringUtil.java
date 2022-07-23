/*
 * Basic library providing basic functionality for Bukkit plugins of mine.
 * Copyright (C) 2022 David (Skyslycer)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.skyslycer.skylib.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StringUtil {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .tags(StandardTags.defaults()).build();

    /**
     * Get a component from a string combined with tag resolvers.
     * @param message The string
     * @param placeholders The placeholders
     * @return The parsed component
     */
    public static Component parseComponent(String message, TagResolver... placeholders) {
        String string = ChatColor.translateAlternateColorCodes('&', message);
        return Component.text().decoration(TextDecoration.ITALIC, false).append(MINI_MESSAGE.deserialize(string, TagResolver.resolver(placeholders))).build();
    }

    /**
     * Get a component from a string combined with tag resolvers and PlaceholderAPI placeholders.
     * @param sender The sender
     * @param message The string
     * @param placeholders The placeholders
     * @return The parsed component
     */
    public static Component parseComponent(CommandSender sender, String message, TagResolver... placeholders) {
        String string = ChatColor.translateAlternateColorCodes('&', message);
        return Component.text().decoration(TextDecoration.ITALIC, false)
                .append(MINI_MESSAGE.deserialize(replacePlaceholders(sender, string), placeholders)).build();
    }

    /**
     * Get a BaseComponent array from a string combined with tag resolvers and PlaceholderAPI placeholders.
     * @param sender The sender
     * @param message The string
     * @param placeholders The placeholders
     * @return The parsed BaseComponent array
     */
    public static BaseComponent[] parse(CommandSender sender, String message, TagResolver... placeholders) {
        return BungeeComponentSerializer.get().serialize(parseComponent(sender, message, placeholders));
    }

    /**
     * Send a message to a sender from a string combined with tag resolvers and PlaceholderAPI placeholders.
     * @param sender The sender
     * @param message The string
     * @param placeholders The placeholders
     */
    public static void send(CommandSender sender, String message, TagResolver... placeholders) {
        sender.spigot().sendMessage(parse(sender, message, placeholders));
    }

    private static String replacePlaceholders(CommandSender sender, String string) {
        if (sender instanceof Player player && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, string);
        }
        return string;
    }

}
