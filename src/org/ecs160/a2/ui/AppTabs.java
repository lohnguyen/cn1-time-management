package org.ecs160.a2.ui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

public class AppTabs extends Tabs {
    public static AppTabs instance;
    private Form current;

    public AppTabs(Form current) {
        super();
        this.current = current;
        AppTabs.instance = this;

        configTabs();
        createAllTabs();

        current.add(BorderLayout.CENTER, this);
    }

    /**
     * Configures anything to do with the Tabs object
     */
    private void configTabs() {
        this.setSwipeActivated(false); // Disable the swipe to prevent
                                       // competition with the cards
        addTabSelectionListeners();
    }

    /**
     * Creates all the tabs in the below order
     */
    private void createAllTabs() {
        createTaskListTab();
        createSummaryTab();
    }

    /**
     * Creates the taskList tab with its icon and adds it to the tabs
     */
    private void createTaskListTab() {
        FontImage taskIcon = FontImage.createMaterial(FontImage.MATERIAL_ALARM,
                "Label", 6);
        this.addTab("Tasks", taskIcon, new TaskList());
    }

    /**
     * Creates the summary tab with its icon and adds it to the tabs
     */
    private void createSummaryTab() {
        FontImage summaryIcon =
                FontImage.createMaterial(FontImage.MATERIAL_ASSESSMENT,
                        "Label", 6);
        this.addTab("Summary", summaryIcon, new Summary());
    }

    /**
     * Configures the action listeners for when a tab is selected
     * Adds and removes the search bar based on what tab we're on
     */
    private void addTabSelectionListeners() {
        this.addSelectionListener((oldTabIndex, newTabIndex) -> {
            if (newTabIndex == 0) {
                selectTaskListTab();
            } else {
                selectSummaryTab();
            }
        });
    }

    /**
     * What happens when the taskList tab is selected
     */
    private void selectTaskListTab() {
        AppToolbars.resetTaskListToolbar();
        current.setToolbar(AppToolbars.getTaskListToolbar());
        TaskList.clearSearch();
        TaskList.refresh();
    }

    /**
     * What happens when the summary tab is selected
     */
    private void selectSummaryTab() {
        current.setToolbar(AppToolbars.getSummaryToolbar());
    }
}
