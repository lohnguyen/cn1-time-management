package org.ecs160.a2;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class UISummary {
    static public Container instance() {
        return BoxLayout.encloseXCenter(new Label("Summary " +
                "and/or options will show up here."));
    }
}
