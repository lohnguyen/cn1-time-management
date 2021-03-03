package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.ui.Container;
import com.codename1.ui.layouts.Layout;

import org.ecs160.a2.models.Task;

/**
 * Class that adds custom update functionality to Codename One containers
 */
public abstract class UpdateableContainer extends Container {

    /**
     * Default constructor that constructs as a Codename One container
     * without parameters
     */
    public UpdateableContainer () {
        super();
    }

    /**
     * Constructor that constructs as a Codename One container
     * with the specified layout
     * 
     * @param layout The layout of this new container
     */
    public UpdateableContainer (Layout layout) {
        super(layout);
    }

    /**
     * Method that is called when an UpdateableContainer child asks for
     * to be updated
     * 
     * @param source The child that asks for an update
     */
    protected void childAsksForUpdate (UpdateableContainer source) {
    }

    /**
     * Asks the parent container for an update if it is an instance of
     * an UpdateableContainer
     */
    protected void askParentForUpdate () {
        Container parent = this.getParent();
        if (parent instanceof UpdateableContainer) {
            UpdateableContainer parentCast = (UpdateableContainer) parent;
            parentCast.childAsksForUpdate(this);
        }
    }

    /**
     * Method that updates the container with the specified TaskList
     * 
     * @param taskList The task list that will provide data for the update
     */
    public abstract void updateContainer (List<Task> taskList);
}