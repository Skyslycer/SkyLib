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
     * Give the player an item and drop if necessary.
     * @param player The player to give the item to
     * @param item The item to give
     */
    public static void give(Player player, ItemStack item) {
        var drops = player.getInventory().addItem(item);
        drops.values().forEach(left -> player.getLocation().getWorld().dropItemNaturally(player.getLocation(), left));
    }

    /**
     * Get the block the player is looking at.
     * @param player The player
     * @return The location of the block the player is looking at
     */
    public static Location getLookBlock(Player player) {
        var blocks = player.getLineOfSight(null, 2);
        if (blocks.size() < 2 || blocks.get(1).getType() != Material.AIR) {
            return null;
        }
        return blocks.get(1).getLocation().clone();
    }

    /**
     * Get the opposite of the facing block of a player.
     * @param player The player
     * @return The block behind the player
     */
    public static Location getOpposite(Player player) {
        var facing = player.getFacing().getOppositeFace();
        var location = player.getLocation().clone();
        location.add(facing.getModX(), facing.getModY(), facing.getModZ());
        var block = player.getLocation().getWorld().getBlockAt(location);
        return block.getLocation();
    }

}
