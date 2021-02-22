package org.ecs160.a2.ui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;

public class AddNewTask {

    public void get() {
        Dialog addNewTaskDialog = new Dialog("New Task");
        addNewTaskDialog.setLayout(new BorderLayout());
        int displayHeight = Display.getInstance().getDisplayHeight();
        addNewTaskDialog.setDisposeWhenPointerOutOfBounds(true);
        //displayTaskForm(addNewTaskDialog);
        addNewTaskDialog.show(displayHeight/4, 0, 0, 0);
    }
}
