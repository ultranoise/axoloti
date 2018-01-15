package axoloti.swingui.patch.object.display;

import axoloti.patch.object.display.DisplayInstanceController;
import axoloti.patch.object.display.DisplayInstanceFrac32;

abstract class DisplayInstanceViewFrac32 extends DisplayInstanceView1 {

    DisplayInstanceViewFrac32(DisplayInstanceController controller) {
        super(controller);
    }

    @Override
    DisplayInstanceFrac32 getModel() {
        return (DisplayInstanceFrac32) super.getModel();
    }
}