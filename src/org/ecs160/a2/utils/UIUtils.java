package org.ecs160.a2.utils;

import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Style;

import java.time.LocalDateTime;

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
    public static Label createLabel(String labelText, Font style,
                                     int color, float fontSize) {
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
    public static SpanLabel createSpanLabel(String labelText, Font style,
                                            int color, float fontSize) {
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
     * Create material icon for TaskDetail
     */
    public static FontImage createIcon(char icon, Style style, float size) {
        if (style != null) return FontImage.createMaterial(icon, style, size);
        else return FontImage.createMaterial(icon, "TitleCommand", size);

    }

    /**
     * Create material chevron left icon for TaskDetail's toolbar
     */
    public static FontImage createBackIcon() {
        return createIcon(FontImage.MATERIAL_ARROW_BACK_IOS, null,
                ICON_SIZE_TOOLBAR);
    }

    /**
     * Create material chevron right icon for TaskDetail's toolbar
     */
    public static FontImage createNextIcon() {
        return createIcon(FontImage.MATERIAL_ARROW_RIGHT_ALT, null,
                ICON_SIZE_TOOLBAR);
    }

    /**
     * Create material tag icon for task's tags
     */
    public static FontImage createTagIcon() {
        Style s = new Style();
        s.setBgColor(0xff884b);
        s.setFgColor(0xffffff);
        return createIcon(FontImage.MATERIAL_LOCAL_OFFER, s, ICON_SIZE_REGULAR);
    }

    /**
     * Create an icon for TaskCard's Swipeable
     *
     * @param color The color for the card icon
     * @return
     */
    public static Style createCardIconStyle(int color) {
        Font font = NATIVE_LIGHT.derive(Display.getInstance()
                .convertToPixels(5, true), Font.STYLE_PLAIN);
        return new Style(color, 0, font, (byte) 0);
    }

    /**
     * Create a TimeLabel for UI
     */
    public static SpanLabel createTimeLabel(LocalDateTime time) {
        return new SpanLabel(TimeUtils.timeAsUIString(time));
    }

}
