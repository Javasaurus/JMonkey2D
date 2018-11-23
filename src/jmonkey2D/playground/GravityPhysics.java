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
package jmonkey2D.playground;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.control.appstate.AnimationState;
import jmonkey2D.control.appstate.Physics2DState;
import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.GameObject;
import jmonkey2D.model.physics.RigidBody2D;
import jmonkey2D.model.sprites.data.AnimationData;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

public class GravityPhysics extends Simple2DApplication {

    public static void main(String[] args) {
        GravityPhysics app = new GravityPhysics();
        app.start();

    }

    /**
     * The shared instance of the game object pool
     */
    private static final GameObjectPool GAME_OBJECT_POOL = new GameObjectPool();
    private GameObject gameObject;
    private RigidBody2D gameRigidBody;

    public GravityPhysics() {
        super(new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
                new AnimationState(GAME_OBJECT_POOL), new Physics2DState(GAME_OBJECT_POOL));
    }

    @Override
    public void simpleInit2DApp() {

    }

    float time = 0;
    boolean ready = false;

    @Override
    public void simpleUpdate(float tpf) {
        WaitForReady(tpf);

    }

    /**
     * For some reason, when new objects are made in the initializer they are
     * not picked up. Therefor I added a small waiting function to only spawn
     * the objects once everything is properly loaded (look into this later
     * !!!!)
     *
     * @param tpf the time per frame
     */
    public void WaitForReady(float tpf) {
        if (ready) {
            return;
        }
        time += tpf;
        if (time > 0.3f) {
            time = 0;

            //TODO NEED TO CLEAN THIS UP AND FIX WITH TILED!!!!
            GameObject floor = GAME_OBJECT_POOL.CreateGameObject();
            RigidBody2D floorBody = floor.addRigidBody2D(true);
            floorBody.removeAllFixtures();
            floorBody.addFixture(new Rectangle(100f, 1f));
            floorBody.translate(0, -4f);

            gameObject = GAME_OBJECT_POOL.CreateGameObject();
            //add a sprite
            gameObject.addSprite(
                    "Sprite",
                    "Textures/Sprites.png",
                    new AnimationData("Idle", 5, 5, 0, 25, 10, true));
            //add a rigidbody
            gameRigidBody = gameObject.addRigidBody2D(false);
            gameRigidBody.applyImpulse(new Vector2(0,3f));
            ready = true;
        }
    }

}
