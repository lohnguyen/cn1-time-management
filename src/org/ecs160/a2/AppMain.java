package org.ecs160.a2;


import static com.codename1.ui.CN.*;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;

import com.codename1.ui.layouts.BoxLayout;

import java.io.IOException;
import java.lang.Object;
import java.time.LocalDateTime;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class AppMain {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if (err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error",
                    "There was a networking error in the connection to " +
                            err.getConnectionRequest().getUrl(), "OK",
                    null);
        });
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

        // TODO: Test, take out when done
        Database.init();
        Database.test();

        Form hi = new Form("Task Management App", new BorderLayout());

        Toolbar toolbar = new Toolbar();
        hi.setToolbar(toolbar);
        toolbar.setTitle("Tasks");
        Button addTaskButton = new Button();

        try {
            addTaskButton.setIcon(Image.createImage("/addbutton.png").scaled(80,80));
        } catch (IOException e) {
            e.printStackTrace();
        }

        toolbar.addComponent(BorderLayout.EAST, addTaskButton);

        addTaskButton.addActionListener(e->showNewTaskForm());

        final FontImage taskOn =
                FontImage.createMaterial(FontImage.MATERIAL_ALARM_ON, "Label"
                        , 6);

        Container taskList = new InfiniteContainer() {
            @Override
            public Component[] fetchComponents(int index, int amount) {
                Component[] allTasks = new Component[20];

                for (int i = 0; i < allTasks.length; i++) {
                    final Task testTask = new Task("Task " + i);
                    testTask.getTimeSpans().add(new TimeSpan(LocalDateTime.now(), null));
                    final Component card = TaskCard.createTaskCard(testTask);
                    allTasks[i] = card;
                }

                return allTasks;
            }
        };

        Tabs tabs = new Tabs();
        tabs.setSwipeActivated(false); // This will prevent the tabs compete
        // with TaskCard on swipe
        hi.add(BorderLayout.CENTER, tabs);
        tabs.addTab("Tasks", taskOn, taskList);
        tabs.addTab("Summary", BoxLayout.encloseXCenter(new Label("Summary " +
                "and/or options will show up here.")));
        hi.show();
    }

    private void showNewTaskForm() {
        log("New Task Form.");
    }

    public void stop() {
        current = getCurrentForm();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}