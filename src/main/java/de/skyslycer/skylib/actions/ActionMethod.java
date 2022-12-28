package de.skyslycer.skylib.actions;

import de.skyslycer.skylib.actions.information.ActionInformation;

/**
 * This class is used to execute an action.
 */
public interface ActionMethod {

    /**
     * This method is called when this action is pushed.
     * @param information The pushed information
     */
    void execute(ActionInformation information);

}
