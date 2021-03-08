package org.ecs160.a2.utils;

import com.codename1.components.SpanLabel;
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
     * Generate a multiline span label for the specified parameters
     * 
     * @param labelText The label's text
     * @param style     The label's font
     * @param color     The label's color
     * @param fontSize  The label's size in CN1 "dips"
     * 
     * @return The newly generated label
     */
    public static SpanLabel createSpanLabel (String labelText, Font style, 
                                             int color, 
                                             float fontSize) {
        SpanLabel label = new SpanLabel(labelText);

        int pixelSize = Display.getInstance().convertToPixels(fontSize);
        label.getTextAllStyles().setFont(style.derive(pixelSize, 
                                                  Font.STYLE_PLAIN));
        label.getTextAllStyles().setFgColor(color);

        return label;
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
