package org.ecs160.a2.ui;

import com.codename1.ui.Container;
import com.codename1.ui.layouts.Layout;

public abstract class UpdateableContainer extends Container {

    public UpdateableContainer () {
        super();
    }

    public UpdateableContainer (Layout layout) {
        super(layout);
    }

    public abstract void updateContainer ();
}