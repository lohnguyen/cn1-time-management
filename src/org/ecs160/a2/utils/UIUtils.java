package org.ecs160.a2.utils;

import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Style;

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
     * Get material icon for TaskDetail
     */
    public static FontImage getIcon(char icon, Style style, float size) {
        if (style != null) return FontImage.createMaterial(icon, style, size);
        else return FontImage.createMaterial(icon, "TitleCommand", size);

    }

    /**
     * Get material chevron left icon for TaskDetail's toolbar
     */
    public static FontImage getBackIcon() {
        return getIcon(FontImage.MATERIAL_ARROW_BACK_IOS, null,
                ICON_SIZE_TOOLBAR);
    }

    /**
     * Get material chevron right icon for TaskDetail's toolbar
     */
    public static FontImage getNextIcon() {
        return getIcon(FontImage.MATERIAL_ARROW_RIGHT_ALT, null,
                ICON_SIZE_TOOLBAR);
    }

    /**
     * Get material tag icon for task's tags
     */
    public static FontImage getTagIcon() {
        Style s = new Style();
        s.setBgColor(0xff884b);
        s.setFgColor(0xffffff);
        return getIcon(FontImage.MATERIAL_LOCAL_OFFER, s, ICON_SIZE_REGULAR);
    }

}
