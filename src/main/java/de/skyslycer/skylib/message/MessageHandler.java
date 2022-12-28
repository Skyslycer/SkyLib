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

package de.skyslycer.skylib.message;

import de.skyslycer.skylib.SkyPlugin;
import de.skyslycer.skylib.util.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.PropertyResourceBundle;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver.Single;
import org.bukkit.command.CommandSender;

/**
 * A message handler for loading messages.
 */
public class MessageHandler {

    private final SkyPlugin plugin;
    private final Path path;
    private PropertyResourceBundle bundle;
    private PropertyResourceBundle fallback;

    /**
     * Create a new message handler.
     * @param plugin The plugin
     * @param path The path to the messages file
     */
    public MessageHandler(SkyPlugin plugin, Path path) {
        this.plugin = plugin;
        this.path = path;
    }

    /**
     * Load the messages.
     * @return If they could be loaded
     */
    public boolean load() {
        if (!Files.exists(path)) {
            try {
                Files.copy(SkyPlugin.class.getClassLoader().getResourceAsStream(path.getFileName().toString()), path);
            } catch (IOException exception) {
                plugin.logSevere("Could not copy the messages file!", exception);
            }
        }
        update();
        try {
            fallback = new PropertyResourceBundle(SkyPlugin.class.getClassLoader().getResource(path.getFileName().toString()).openStream());
        } catch (IOException | NullPointerException exception) {
            plugin.logSevere("An error occurred while trying to load the fallback messages (please report this to the developers):", exception);
        }

        try {
            bundle = new PropertyResourceBundle(Files.newInputStream(path));
        } catch (IOException exception) {
            plugin.logSevere("An error occurred while trying to load the messages (please report this to the developers):", exception);
        }

        if (bundle == null && fallback == null) {
            plugin.logSevere("Could not load any messages (please report this to the developers)! The plugin will shut down now.");
            return false;
        }
        return true;
    }

    /**
     * Get a message from the file or fallback.
     * @param key The message key
     * @return The message string
     */
    public String get(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        } else if (fallback.containsKey(key)) {
            return fallback.getString(key);
        } else {
            return "Invalid key: " + key;
        }
    }

    /**
     * Update the message file according to the fallback.
     */
    public void update() {
        try {
            var stream = SkyPlugin.class.getClassLoader().getResource(path.getFileName().toString()).openStream();
            var lines = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines();
            var checkLines = Files.readAllLines(path);
            lines.forEach(line -> {
                var split = line.split("=");
                if (split.length > 1 && checkLines.stream().filter(it -> it.startsWith(split[0])).findFirst().isEmpty()) {
                    try {
                        Files.writeString(path, line, StandardOpenOption.APPEND);
                    } catch (IOException exception) {
                        plugin.logSevere("Could not append the following line: \n" + line, exception);
                    }
                }
            });
        } catch (Exception exception) {
            plugin.logSevere("Could not load the message files to update them!");
            exception.printStackTrace();
        }
    }

    /**
     * Send a message using a message key.
     * @param sender The sender
     * @param key The message key
     * @param placeholders The placeholders
     */
    public void send(CommandSender sender, String key, Single... placeholders) {
        StringUtil.send(sender, get(key), placeholders);
    }

}
