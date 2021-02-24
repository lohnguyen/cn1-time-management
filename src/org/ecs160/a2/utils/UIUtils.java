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
    
    // create a label based on the specified parameters
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

    // get a list of labels that correspond to amount given
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
                label = createLabel("", NATIVE_LIGHT, 0x000000, 3.0f);
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

    public static int getPixelSize(float size) {
        return Display.getInstance().convertToPixels(size);
    }

    public static Font getTitleFont() {
        return NATIVE_REGULAR.derive(getPixelSize(FONT_SIZE_TITLE),
                Font.STYLE_PLAIN);
    }

    public static FontImage getIcon(char icon, float size) {
        return FontImage.createMaterial(icon, "TitleCommand", size);
    }

    public static FontImage getBackIcon() {
        return getIcon(FontImage.MATERIAL_ARROW_BACK_IOS, ICON_SIZE_TOOLBAR);
    }

}
