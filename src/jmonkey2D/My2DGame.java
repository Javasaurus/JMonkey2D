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
package jmonkey2D;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.control.appstate.AnimationState;
import jmonkey2D.control.appstate.Physics2DState;
import jmonkey2D.control.appstate.TileMapState;
import jmonkey2D.control.sprite.TileManager;
import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.GameObject;
import jmonkey2D.model.physics.RigidBody2D;
import jmonkey2D.model.sprites.data.AnimationData;
import jmonkey2D.model.sprites.data.MapData;
import org.dyn4j.geometry.Rectangle;

public class My2DGame extends Simple2DApplication {

    public static void main(String[] args) {
        My2DGame app = new My2DGame();
        app.start();

    }

    private static final GameObjectPool GAME_OBJECT_POOL = new GameObjectPool();
    private GameObject gameObject;
    private RigidBody2D gameRigidBody;

    public My2DGame() {
        super(new StatsAppState(), new FlyCamAppState(), new AudioListenerState(), new DebugKeysAppState(),
                new AnimationState(GAME_OBJECT_POOL),
                new Physics2DState(GAME_OBJECT_POOL),
                new TileMapState(GAME_OBJECT_POOL));
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

    public void WaitForReady(float tpf) {
        if (ready) {
            return;
        }
        time += tpf;
        if (time > 0.3f) {
            time = 0;

            //LOAD A MAP
            //loading tiles
            TileManager tileManager = TileManager.getInstance(getAssetManager());
            tileManager.LoadTileSet("Textures/tilesheet.png", 32, 32);
            MapData data = getDebugMapData();
            tileManager.LoadMap(data, GAME_OBJECT_POOL,getStateManager().getState(TileMapState.class).getBaseNode());

            //ADD A PLAYER
            gameObject = GAME_OBJECT_POOL.CreateGameObject();
            //add a sprite
            gameObject.addSprite(
                    "Sprite",
                    "Textures/Sprites.png",
                    new AnimationData("Idle", 5, 5, 0, 25, 10, true));
            //add a rigidbody
            gameRigidBody = gameObject.addRigidBody2D(false);
            gameRigidBody.translate(7, 5);
            ready = true;
        }
    }

    public MapData getDebugMapData() {
        MapData mapData = new MapData(16, 16);

        //fill the bottom row with tile 2 
        mapData.getMapArray()[0][0] = 1;
        mapData.getMapArray()[0][mapData.getMapArray()[0].length - 1] = 33;

        for (int i = 1; i < mapData.getMapArray()[0].length - 1; i++) {
            mapData.getMapArray()[i][0] = 3;
        }
        //add some other stuff on second row
        //fill the bottom row with tile 2 
        for (int i = 5; i < mapData.getMapArray()[0].length - 5; i++) {
            mapData.getMapArray()[i][1] = 1;
        }

        return mapData;
    }

}
