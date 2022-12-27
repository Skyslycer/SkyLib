package de.skyslycer.skylib.actions.information;

import org.bukkit.entity.Player;

public class BasicActionInformation implements ActionInformation {

    private Player player;
    private String arguments;

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
