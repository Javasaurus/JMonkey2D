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

import com.jme3.math.Vector3f;
import com.simsilica.es.EntitySet;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.control.sprite.TileManager;
import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.sprites.data.MapData;

public class TileRendering extends Simple2DApplication {

    public static void main(String[] args) {
        TileRendering app = new TileRendering();
        app.start();

    }

    private EntitySet spriteEntities;

    @Override
    public void simpleInit2DApp() {
        cam.setLocation(new Vector3f(8, 3.5f, cam.getLocation().z));
        TileManager tileManager = TileManager.getInstance(assetManager);
        tileManager.LoadTileSet("Textures/tilesheet.png", 32, 32);
        MapData data = getDebugMapData();
        tileManager.LoadMap(data, new GameObjectPool(), rootNode);

    }

    @Override
    public void simpleUpdate(float tpf) {

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
