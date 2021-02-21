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

        Database.init();
    }

    private void setToolbar() {
        Toolbar toolbar = new Toolbar();
        current.setToolbar(toolbar);
        toolbar.setTitle("Tasks");

        Button addTaskButton = new Button();

        try {
            addTaskButton.setIcon(Image.createImage("/addbutton.png").scaled(80,80));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addTaskButton.addActionListener(e->showNewTaskForm());
        toolbar.addComponent(BorderLayout.EAST, addTaskButton);
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }

        current = new Form("Task Management App", new BorderLayout());
        setToolbar();
        setTabs();

        current.show();
    }

    private void setTabs() {
        Tabs tabs = new Tabs();
        FontImage taskIcon = FontImage.createMaterial(
                FontImage.MATERIAL_ALARM_ON, "Label", 6);

        Container taskList = new InfiniteContainer() {
            @Override
            public Component[] fetchComponents(int index, int amount) {
                Component[] allTasks = new Component[20];

                for (int i = 0; i < allTasks.length; i++) {
                    final int taskNum = i;
                    MultiButton buttons = new MultiButton("Task " + taskNum);
                    buttons.setTextLine2("details");
                    FontImage.setMaterialIcon(buttons,
                            FontImage.MATERIAL_ALARM_ON);
                    buttons.addActionListener(ee ->
                            ToastBar.showMessage("Clicked: " + taskNum,
                                    FontImage.MATERIAL_ALARM_ON));
                    allTasks[i] = buttons;
                }

                return allTasks;
            }
        };

        tabs.addTab("Tasks", taskIcon, taskList);
        tabs.addTab("Summary", UISummary.instance());
        current.add(BorderLayout.CENTER, tabs);
    }

    private void showNewTaskForm() {
        UIEditTask edit = new UIEditTask();
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