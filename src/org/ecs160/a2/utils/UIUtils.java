package org.ecs160.a2.utils;

import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;

public class UIUtils implements AppConstants {

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
