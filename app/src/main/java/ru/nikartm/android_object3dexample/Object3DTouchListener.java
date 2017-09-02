package ru.nikartm.android_object3dexample;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;

import min3d.core.Object3dContainer;
import ru.nikartm.android_object3dexample.constant.Constants;
import ru.nikartm.android_object3dexample.util.Utils;

/**
 * @author Ivan Vodyasov on 02.09.2017.
 */

public class Object3DTouchListener implements View.OnTouchListener {

    private static final float MAX_SCALE = 2f;
    private static final float MIN_SCALE = .1f;
    private static final int DRAG   = 1;
    private static final int SCALE  = 2;
    private static final int ROTATE = 3;

    private Object3dContainer object3D;
    private Map<String, Float> displayDpi;

    private float x;
    private float y;
    private float touchedX;
    private float touchedY;
    private float lastPlanePosition;
    private float planePosition;
    private float totalRatio = .7f;

    public Object3DTouchListener(Activity activity, Object3dContainer object3D) {
        displayDpi = Utils.getDisplayDpi(activity);
        this.object3D = object3D;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchedX = event.getX();
                touchedY = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    lastPlanePosition = spacing(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                switch (event.getPointerCount()) {
                    case DRAG:
                        x = event.getX();
                        y = event.getY();
                        float deltaX = (x - touchedX) / (displayDpi.get(Constants.WIDTH));
                        float deltaY = (y - touchedY) / (displayDpi.get(Constants.HEIGHT));

                        object3D.position().x = object3D.position().x + deltaX;
                        object3D.position().y = object3D.position().y - deltaY;
                        touchedX = x;
                        touchedY = y;
                        break;
                    case SCALE:
                        planePosition = spacing(event);
                        float scaledRatio = planePosition / lastPlanePosition;
                        totalRatio = totalRatio * scaledRatio;

                        if(totalRatio > MAX_SCALE) {
                            totalRatio = MAX_SCALE;
                        }
                        if(totalRatio < MIN_SCALE) {
                            totalRatio = MIN_SCALE;
                        }

                        object3D.scale().x = object3D.scale().y = object3D.scale().z = totalRatio;
                        lastPlanePosition = planePosition;
                        break;
                    case ROTATE:
                        x = event.getX();
                        y = event.getY();

                        object3D.rotation().y -= (touchedX - x) / 6f;
                        object3D.rotation().x += (touchedY - y) / 6f;
                        touchedX = x;
                        touchedY = y;
                        break;
                }
                break;
        }
        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
