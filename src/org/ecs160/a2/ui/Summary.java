package org.ecs160.a2.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.RoundRectBorder;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.ui.containers.UpdateableContainer;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.UIUtils;

/**
 * The container that houses the Summary containers for all Tasks
 */
public class Summary extends UpdateableContainer implements AppConstants {

    // the current state of TaskList for this page
    private static List<Task> taskList;

    private Container pageButtonContainer;
    private Map<String, UpdateableContainer> pages;

    /**
     * Default constructor that assembles the children of this container
     */
    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        setScrollableY(true); // can scroll vertically
        
        // setup pages list
        pages = new HashMap<>();

        // page button container setup
        // set the background styling
        int marginSize = UIUtils.getPixelSize(FONT_SIZE_REGULAR / 2.0f);
        pageButtonContainer = new Container();
        pageButtonContainer.getAllStyles().setBgColor(0xD3D3D3);
        pageButtonContainer.getAllStyles().setBgTransparency(255);
        pageButtonContainer.getAllStyles().setBorder(RoundRectBorder.create());
        pageButtonContainer.getAllStyles().setMargin(Component.TOP, marginSize);
        pageButtonContainer.getAllStyles().setMargin(Component.BOTTOM, marginSize);
        add(pageButtonContainer);

        // setup pages
        addPage("Everything", new SummaryAll());
        addPage("By Size", new SummarySize());
        addPage("By Tag", new SummaryTags());

        // revalidate the button container
        pageButtonContainer.setLayout(new GridLayout(1, pages.size()));
        pageButtonContainer.revalidate();

        // Setup pull to refresh for this container
        addPullToRefresh(() -> updateSubContainers());
        updateSubContainers();
        selectPage("Everything");
    }

    // method to construct page buttons
    // set the selection styles (default to background transparent)
    private void addPageButton (String text) {
        Button pageButton = new Button (text);
        pageButton.getAllStyles().setFgColor(0x000000); // black font
        pageButton.getAllStyles().setBgColor(0xffffff); // white background
        pageButton.getAllStyles().setBorder(RoundRectBorder.create());
        pageButton.addActionListener((e) -> {
            selectPage(((Button) e.getComponent()).getText());
        });
        pageButtonContainer.add(pageButton);
    }

    // update selected button styles by updating the transparencies
    private void setSelectedButtonStyle (String text) {
        for (int i = 0; i < pageButtonContainer.getComponentCount(); i++) {
            Button button = (Button) pageButtonContainer.getComponentAt(i);
            if (button.getText().equals(text)) { // if equal, make bg visible
                button.getAllStyles().setBgTransparency(255);
            } else {
                button.getAllStyles().setBgTransparency(0);
            }
        }
    }

    // method to add a new page
    private void addPage (String text, UpdateableContainer page) {
        addPageButton(text);
        page.setHidden(true);
        pages.put(text, page);
        add(page);
    }

    // select a page for the given button text
    private void selectPage (String text) {
        setSelectedButtonStyle(text);
        pages.forEach((key, value) -> value.setHidden(true));
        pages.get(text).updateContainer(taskList);
        pages.get(text).setHidden(false);
    }

    // reload the internal task list by reading in from the static Database
    private void reloadTaskList () {
        taskList = (List) Database.readAll(Task.OBJECT_ID);
    }

    // for a given page, check if not hidden and update
    private void checkForUpdate (UpdateableContainer page) {
        if (!page.isHidden()) page.updateContainer(taskList);
    }

    /**
     * Update the visible subpages of this Container
     */
    public void updateSubContainers () {
        reloadTaskList(); // refresh the tasks first
        if (taskList.size() > 0) {
            pages.forEach((key, value) -> checkForUpdate(value));
        }
    }

    /**
     * Update the source child with the internal Task List
     */
    @Override
    protected void childAsksForUpdate (UpdateableContainer source) {
        source.updateContainer(taskList);
    }

    /**
     * Update the sub containers when this containers need updating
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        updateSubContainers();
    }
}