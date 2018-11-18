/*
 * Copyright 2018 DrJavaSaurus <javasaurusdev@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jmonkey2D.control.system;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.math.Vector3f;
import jmonkey2D.control.sprite.SpriteManager;

/**
 * A special implementation for the SimpleApplication tailored for 2D
 * applications
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 *
 */
public abstract class Simple2DApplication extends SimpleApplication {

    /**
     * The spritemanager instance
     */
    private SpriteManager spriteManager;
    private float aspect;

    
    public Simple2DApplication() {
        super(new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState());
    }

    public Simple2DApplication(AppState... initialStates) {
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {
        InitCamera();
        spriteManager = SpriteManager.getInstance(assetManager);
        simpleInit2DApp();
    }

    /**
     * Initializes a camera for 2D applications
     */
    private void InitCamera() {
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        renderManager.setCamera(cam, true);

        cam.setParallelProjection(true);

        cam.setLocation(new Vector3f(0f, 0f, 10f));

        SetCameraSize(3.5f);
    }

    /**
     * Changes the size of the camera
     * @param size the new camera size
     */
    public void SetCameraSize(float size) {
        aspect = (float) cam.getWidth() / cam.getHeight();
        cam.setFrustum(-1000, 1000, -aspect * size, aspect * size, size, -size);
    }

    /**
     * Initializes the 2D application
     */
    public abstract void simpleInit2DApp();

}
