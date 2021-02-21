package org.ecs160.a2.ui;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

public class Summary extends Form {
    public Summary() {
    }

    public Container get() {
        return BoxLayout.encloseXCenter(new Label("Summary " +
                "and/or options will show up here."));
    }
}
