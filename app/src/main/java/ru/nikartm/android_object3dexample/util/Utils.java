package ru.nikartm.android_object3dexample.util;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

import java.util.HashMap;
import java.util.Map;

import ru.nikartm.android_object3dexample.constant.Constants;

/**
 * @author Ivan Vodyasov on 02.09.2017.
 */

public class Utils {

    public static Map<String, Float> getDisplayDpi(Activity activity) {
        Map<String, Float> displayDpi = new HashMap<>();
        Point size = getDisplaySize(activity);
        float density = activity.getResources().getDisplayMetrics().density;

        float width = size.x / density;
        float height = size.y / density;
        displayDpi.put(Constants.WIDTH, width);
        displayDpi.put(Constants.HEIGHT, height);
        return displayDpi;
    }

    private static Point getDisplaySize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
