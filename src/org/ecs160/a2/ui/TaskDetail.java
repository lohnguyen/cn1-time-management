package org.ecs160.a2.ui;

import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import org.ecs160.a2.models.Task;

public class TaskDetail extends Form {

    private Form prev;
    private Task task;

    TaskDetail(Task task) {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        prev = Display.getInstance().getCurrent();

        this.task = task;

        Label title = new Label(task.getTitle());
        this.add(title);

        Button back = new Button("Back");
        back.addActionListener(e -> {
            goBack();
        });
        this.add(back);
    }

    private void goBack() {
        prev.showBack();
    }

}
