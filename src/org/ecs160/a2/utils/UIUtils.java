package org.ecs160.a2.utils;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.FontImage;

import java.util.ArrayList;
import java.util.List;

public class UIUtils implements AppConstants {
    
    /**
     * Generate a label for the specified parameters
     * 
     * @param labelText The label's text
     * @param style     The label's font
     * @param color     The label's color
     * @param fontSize  The label's size in CN1 "dips"
     * 
     * @return The newly generated label
     */
    public static Label createLabel (String labelText, Font style, 
                                                       int color, 
                                                       float fontSize) {
        Label label = new Label(labelText);

        int pixelSize = Display.getInstance().convertToPixels(fontSize);
        label.getAllStyles().setFont(style.derive(pixelSize, 
                                                  Font.STYLE_PLAIN));
        label.getAllStyles().setFgColor(color);

        return label;
    }

    /**
     * Get a list of labels to update in a container for the specified number
     * 
     * @param container  The container that houses the labels
     * @param labelCount The number of labels that must be visible
     * 
     * @return List of visible labels from the specified container
     */
    public static List<Label> getLabelsToUpdate (Container container, 
                                                 int labelCount) {
        int i;
        List<Label> returnLabels = new ArrayList<Label>();

        // loop through the task list, adding new labels if necessary
        for (i = 0; i < labelCount; i++) {
            Label label;

            if (i < container.getComponentCount()) {
                label = (Label) container.getComponentAt(i);
                if (label.isHidden()) label.setHidden(false);
            } else {
                label = createLabel("", NATIVE_LIGHT, COLOR_REGULAR, 
                                    FONT_SIZE_REGULAR);
                container.add(label);
            }

            returnLabels.add(label);
        }

        // hide the extra labels from the component
        for (int j = i; j < container.getComponentCount(); j++) {
            Component extraLabel = container.getComponentAt(i);
            extraLabel.setHidden(true);
        }

        return returnLabels;
    }

    /**
     * @param size The CN1 "dips" to convert to pixels
     */
    public static int getPixelSize(float size) {
        return Display.getInstance().convertToPixels(size);
    }

    /**
     * Get font for displaying task's title in TaskDetail
     */
    public static Font getTitleFont() {
        return NATIVE_REGULAR.derive(getPixelSize(FONT_SIZE_TITLE),
                Font.STYLE_PLAIN);
    }

    /**
     * Get material icon for TaskDetail
     */
    public static FontImage getIcon(char icon, float size) {
        return FontImage.createMaterial(icon, "TitleCommand", size);
    }

    /**
     * Get material chevron left icon for TaskDetail's toolbar
     */
    public static FontImage getBackIcon() {
        return getIcon(FontImage.MATERIAL_ARROW_BACK_IOS, ICON_SIZE_TOOLBAR);
    }

}
