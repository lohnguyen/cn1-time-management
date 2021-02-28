package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.ui.Container;
import com.codename1.ui.layouts.Layout;

import org.ecs160.a2.models.Task;

public abstract class UpdateableContainer extends Container {

    public UpdateableContainer () {
        super();
    }

    public UpdateableContainer (Layout layout) {
        super(layout);
    }

    public abstract void updateContainer (List<Task> taskList);
}