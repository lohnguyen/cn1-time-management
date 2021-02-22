package org.ecs160.a2.ui;

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class Summary extends Container {

    // might move these to some other class if they need to be shared
    private static final Font nativeLight = Font.createTrueTypeFont("native:MainLight");
    private static final Font nativeRegular = Font.createTrueTypeFont("native:MainRegular");
    private static final Font nativeBold = Font.createTrueTypeFont("native:MainBold");

    private void addLabel (String labelText, Font style, int color, 
                                                         float fontSize) {
        Label label = new Label(labelText);
        int pixelSize = Display.getInstance().convertToPixels(fontSize);
        label.getAllStyles().setFont(style.derive(pixelSize, 
                                                  Font.STYLE_PLAIN));
        label.getAllStyles().setFgColor(color);
        this.add(label);
    }

    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true);
        this.addLabel("Summary", nativeBold, 0x000000, 8.0f);

        // TODO add other pages

        this.addLabel("Tasks", nativeBold, 0x000000, 5.5f);
        this.addLabel("Sizes", nativeBold, 0x000000, 5.5f);
        this.addLabel("Statistics", nativeBold, 0x000000, 5.5f);
    }

    // definitely gonna change this, unneeded since this class is a container
    public Container get () {
        return this;
    }
}
