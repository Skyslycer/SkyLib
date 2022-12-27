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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {

    /**
     * Give a player an item and drop what doesn't fit.
     * @param player The player
     * @param item The item to give
     */
    public static void give(Player player, ItemStack item) {
        var drops = player.getInventory().addItem(item);
        drops.values().forEach(left -> player.getLocation().getWorld().dropItemNaturally(player.getLocation(), left));
    }

    /**
     * Get the block a player is looking at. Max distance is 2 blocks and min distance is 0 blocks.
     * @param player The player
     * @return The block the player is looking at
     */
    public static Location getLookBlock(Player player) {
        var twoBlocks = fixLocation(player.getEyeLocation().add(player.getLocation().getDirection().clone().multiply(2)).subtract(0, 0.5, 0), player);
        var oneBlock = fixLocation(player.getEyeLocation().add(player.getLocation().getDirection().clone()).subtract(0, 0.5, 0), player);
        if (oneBlock.getWorld().getBlockAt(oneBlock).getType() == Material.AIR) {
            if (twoBlocks.getWorld().getBlockAt(twoBlocks).getType() == Material.AIR) {
                return twoBlocks;
            }
            return oneBlock;
        }
        return fixLocation(player.getLocation(), player);
    }

    /**
     * Get the block location on the opposite side of the player
     * @param player The player
     * @return The opposite location
     */
    public static Location getOpposite(Player player) {
        var facing = player.getFacing().getOppositeFace();
        var location = player.getLocation().clone();
        location.add(facing.getModX(), facing.getModY(), facing.getModZ());
        var block = player.getLocation().getWorld().getBlockAt(location);
        return block.getLocation();
    }

    /**
     * Sets the Y-coordinate of the location to 1 higher than the players Y-level
     * @param location The location to edit
     * @param player The player
     * @return The changed location
     */
    private static Location fixLocation(Location location, Player player) {
        location.setY(player.getLocation().getY() + 1);
        return location;
    }

}
