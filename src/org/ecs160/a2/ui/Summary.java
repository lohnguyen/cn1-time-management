package org.ecs160.a2.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class Summary extends UpdateableContainer implements AppConstants {

    // NOTE: this is temporary and will be changed to work with the
    // database later on!
    protected static List<Task> taskList = new ArrayList<Task>();
    
    private UpdateableContainer page1, page2;

    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true);

        // temporary Tasks for the summary
        taskList.add(new Task("Task 1"));
        taskList.add(new Task("Task 2"));
        taskList.get(0).setSize("M");
        taskList.get(0).start(LocalDateTime.of(2021, 2, 21, 5, 0));
        taskList.get(0).stop(LocalDateTime.of(2021, 2, 21, 7, 0));
        taskList.get(1).setSize("L");
        taskList.get(1).start(LocalDateTime.of(2021, 2, 22, 6, 0));
        taskList.get(1).stop(LocalDateTime.of(2021, 2, 23, 7, 0));

        // title
        this.add(UIUtils.createLabel("Summary", NATIVE_BOLD, 0x000000, 8.0f));

        // Selection
        Container buttonContainer = new Container(new GridLayout(1, 2));
        Button page1Button = new Button ("Everything");
        page1Button.addActionListener((e) -> selectPageButtonAction(e));
        buttonContainer.add(page1Button);
        Button page2Button = new Button ("Tasks");
        page2Button.addActionListener((e) -> selectPageButtonAction(e));
        buttonContainer.add(page2Button);
        this.add(buttonContainer);

        // setup the different summary pages
        this.page1 = new SummaryAll();
        this.page1.setVisible(true);
        this.add(page1);
        this.page2 = new SummarySize();
        this.page2.setVisible(false);
        this.add(page2);

        // call function on refresh (temporary, can have a better solution)
        this.addPullToRefresh(() -> updateContainer());
        this.updateContainer();
    }

    private void selectPageButtonAction (ActionEvent e) {
        Button button = (Button) e.getComponent();
        switch (button.getText()) {
            case "Everything":
                this.page1.setVisible(true);
                this.page2.setVisible(false);
                break;
            case "Tasks":
                this.page1.setVisible(false);
                this.page2.setVisible(true);
        }
    }

    // called whenever the labels need updating
    // TODO: onload? on refresh?
    @Override
    public void updateContainer () {
        if (taskList.size() > 0) {
            this.page1.updateContainer();
        }
    }
}
