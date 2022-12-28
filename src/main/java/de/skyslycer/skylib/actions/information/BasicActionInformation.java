package de.skyslycer.skylib.actions.information;

import org.bukkit.entity.Player;

/**
 * This class contains basic information about an action.
 */
public class BasicActionInformation implements ActionInformation {

    private Player player;
    private String arguments;

    /**
     * Create a new basic action information.
     * @param player The player who executed the action
     * @param arguments The arguments of the action
     */
    public BasicActionInformation(Player player, String arguments) {
        this.player = player;
        this.arguments = arguments;
    }

    public Player getPlayer() {
        return player;
    }

    public String getArguments() {
        return arguments;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

}
