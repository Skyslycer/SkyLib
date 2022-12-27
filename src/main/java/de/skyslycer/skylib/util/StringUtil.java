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

import java.util.Arrays;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.ParsingException;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class StringUtil {

    public static final MiniMessage MINI_MESSAGE = MiniMessage.builder().tags(StandardTags.defaults()).build();
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors()
            .useUnusualXRepeatedCharacterHexFormat().build();

    /**
     * Parse MiniMessage from a string and replace placeholders.
     * @param message The string to parse
     * @param placeholders The placeholders
     * @return The parsed component
     */
    public static Component parseComponent(String message, TagResolver... placeholders) {
        String string = legacyToMiniMessage(message);
        return Component.text().decoration(TextDecoration.ITALIC, false).append(MINI_MESSAGE.deserialize(string, TagResolver.resolver(placeholders)))
                .build();
    }

    /**
     * Parse MiniMessage from a string, replace placeholders and replace and PlaceholderAPI placeholders.
     * @param sender The sender
     * @param message The string to parse
     * @param placeholders The placeholders
     * @return The parsed component
     */
    public static Component parseComponent(CommandSender sender, String message, TagResolver... placeholders) {
        String string = legacyToMiniMessage(message);
        return Component.text().decoration(TextDecoration.ITALIC, false)
                .append(MINI_MESSAGE.deserialize(replacePlaceholders(sender, string), placeholders)).build();
    }

    /**
     * Parse MiniMessage from a string, replace placeholders and replace and PlaceholderAPI placeholders and return a Spigot friendly component.
     * @param sender The sender
     * @param message The string to parse
     * @param placeholders The placeholders
     * @return The BaseComponent array used in Spigot
     */
    public static BaseComponent[] parse(CommandSender sender, String message, TagResolver... placeholders) {
        return BungeeComponentSerializer.get().serialize(parseComponent(sender, message, placeholders));
    }

    /**
     * Parse MiniMessage from a string, replace placeholders and replace and PlaceholderAPI placeholders and send it to the sender.
     * @param sender The sender
     * @param message The string to parse
     * @param placeholders The placeholders
     */
    public static void send(CommandSender sender, String message, TagResolver... placeholders) {
        sender.spigot().sendMessage(parse(sender, message, placeholders));
    }

    /**
     * Replace all PlaceholderAPI placeholders in a string.
     * @param sender The sender
     * @param string The string
     * @return A replaced string
     */
    public static String replacePlaceholders(CommandSender sender, String string) {
        if (sender instanceof Player player && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, string);
        }
        return string;
    }

    /**
     * Parse a color from a string.
     * Formats: #RRGGBB; R,G,B
     * @param color The string
     * @return The color, if the string can't be parsed, null is returned
     */
    @Nullable
    public static Color colorFromString(@Nullable String color) {
        if (color == null) {
            return null;
        }
        try {
            var decodedColor = java.awt.Color.decode(color.startsWith("#") ? color : "#" + color);
            return Color.fromRGB(decodedColor.getRed(), decodedColor.getGreen(), decodedColor.getBlue());
        } catch (NumberFormatException invalidHex) {
            try {
                var rgbValues = Arrays.stream(color.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                return Color.fromRGB(rgbValues[0], rgbValues[1], rgbValues[2]);
            } catch (Exception invalidRgb) {
                return null;
            }
        }
    }

    /**
     * Convert legacy color codes (&c) to MiniMessage (<red>).
     * If the string contains legacy color codes, other MiniMessage tags won't work.
     * @param legacy The string containing legacy color codes
     * @return The string with MiniMessage color codes
     */
    public static String legacyToMiniMessage(String legacy) {
        try {
            MINI_MESSAGE.deserialize(ChatColor.translateAlternateColorCodes('&', legacy));
            return legacy;
        } catch (ParsingException exception) {
            return MINI_MESSAGE.serialize(LEGACY_SERIALIZER.deserialize(legacy));
        }
    }

}
