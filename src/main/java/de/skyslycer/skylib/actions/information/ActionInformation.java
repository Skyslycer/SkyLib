package de.skyslycer.skylib.actions.information;

import org.bukkit.entity.Player;

/**
 * This class contains information about an action.
 */
public interface ActionInformation {

    /**
     * Get the player who executed the action.
     * @return The player
     */
    Player getPlayer();

    /**
     * Get the arguments of the action.
     * @return The arguments
     */
    String getArguments();

    /**
     * Set the player who executed the action.
     * @param player The player
     */
    void setPlayer(Player player);

    /**
     * Set the arguments of the action.
     * @param arguments The arguments
     */
    void setArguments(String arguments);

}
