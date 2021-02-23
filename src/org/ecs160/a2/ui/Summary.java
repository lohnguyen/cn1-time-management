package org.ecs160.a2.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

import org.ecs160.a2.models.Task;

public class Summary extends UpdateableContainer {

    // might move these to some other class if they need to be shared
    protected static final Font nativeLight = Font.createTrueTypeFont("native:MainLight");
    protected static final Font nativeRegular = Font.createTrueTypeFont("native:MainRegular");
    protected static final Font nativeBold = Font.createTrueTypeFont("native:MainBold");

    // NOTE: this is temporary and will be changed to work with the
    // database later on!
    protected static List<Task> taskList = new ArrayList<Task>();

    // some conversion factors
    protected static long MILIS_TO_HOURS = 3600000L;
    
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
        this.add(createLabel("Summary", nativeBold, 0x000000, 8.0f));

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

    // create a label based on the specified parameters
    protected static Label createLabel (String labelText, Font style, 
                                                          int color, 
                                                          float fontSize) {
        Label label = new Label(labelText);

        int pixelSize = Display.getInstance().convertToPixels(fontSize);
        label.getAllStyles().setFont(style.derive(pixelSize, 
                                                  Font.STYLE_PLAIN));
        label.getAllStyles().setFgColor(color);

        return label;
    }


    // get a list of labels that correspond to amount given
    protected static List<Label> getLabelsToUpdate (Container container, 
                                                    int labelCount) {
        int i;
        List<Label> returnLabels = new ArrayList<Label>();

        // loop through the task list, adding new labels if necessary
        for (i = 0; i < labelCount; i++) {
            Label label;

            if (i < container.getComponentCount()) {
                label = (Label) container.getComponentAt(i);
            } else {
                label = createLabel("", nativeLight, 0x000000, 3.0f);
                container.add(label);
            }

            returnLabels.add(label);
        }

        // remove the extra labels from the component
        while (i < container.getComponentCount()) {
            Component extraLabel = container.getComponentAt(i);
            container.removeComponent(extraLabel);
        }

        return returnLabels;
    }

    // called whenever the labels need updating
    // TODO: onload? on refresh?
    @Override
    public void updateContainer () {
        if (taskList.size() > 0) {
            this.page1.updateContainer();
        }
    }

    // definitely gonna change this, unneeded since this class is a container
    public Container get () {
        return this;
    }
}