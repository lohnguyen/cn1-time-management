package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;

public class UIEditTask extends Form {

    Form prev;
    Form current;

    UIEditTask() {
        prev = Display.getInstance().getCurrent();

        current = new Form("Edit");
        current.add(new Label("Summary " +
                "and/or options will show up here."));

        Button backButton = new Button("Back");
        backButton.addActionListener(e->goBack());
        current.add(backButton);

        Button nextButton = new Button("Next");
        nextButton.addActionListener(e->goNext());
        current.add(nextButton);

        current.show();
    }

    private void goBack() {
        prev.showBack();
    }

    private void goNext() {
        new UITest();
    }
}
