package ru.nikartm.android_object3dexample;

import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import ru.nikartm.android_object3dexample.constant.Constants;

/**
 * @author Ivan Vodyasov on 02.09.2017.
 */

public class Image3DView extends RendererActivity {

    private Object3dContainer object3D;

    @Override
    public void initScene() {
        scene.lights().add(new Light());

        IParser myParser = Parser.createParser(Parser.Type.OBJ, getResources(),
                Constants.RAW_PATH + "penguin_obj", true);
        if (myParser != null) {
            myParser.parse();
            object3D = myParser.getParsedObject();
            object3D.position().x = object3D.position().y = object3D.position().z = 0;
            object3D.scale().x = object3D.scale().y = object3D.scale().z = 1.5f;
            scene.addChild(object3D);
        }

        _glSurfaceView.setOnTouchListener(new Object3DTouchListener(this, object3D));
    }

    @Override
    public void updateScene() {
//        faceObject3D.rotation().y += 0.5;
//        faceObject3D.rotation().z += 1;
    }
}
