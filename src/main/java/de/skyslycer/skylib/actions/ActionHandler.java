package de.skyslycer.skylib.actions;

import de.skyslycer.skylib.actions.information.ActionInformation;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;

/**
 * This class handles all actions.
 */
public class ActionHandler {

    private final HashMap<String, ActionMethod> actions = new HashMap<>();

    /**
     * Add an action.
     * @param action The action type
     * @param method The action
     */
    public void subscribe(String action, ActionMethod method) {
        actions.put(action, method);
    }

    /**
     * Push an action.
     * @param action The action type to push
     * @param information The information to push along with it
     */
    public void push(String action, ActionInformation information) {
        if (!actions.containsKey(action)) {
            Bukkit.getLogger().warning("Action " + action + " is not registered! This is a bug! Please report this.");
            return;
        }
        actions.get(action).execute(information);
    }

    /**
     * Push an action from the config.
     * @param actionTypes The actions to read from
     * @param information The action information
     */
    public void pushFromConfig(HashMap<String, List<String>> actionTypes, ActionInformation information) {
        actionTypes.forEach((actionType, actions) -> {
            actions.forEach(action -> {
                information.setArguments(actionType.toUpperCase());
                push(actionType.toUpperCase(), information);
            });
        });
    }

}
