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

import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.GameObject;
import jmonkey2D.model.sprites.data.AnimationData;
import jmonkey2D.model.sprites.AnimatedSprite;

public class SpriteRendering extends Simple2DApplication {

    public static void main(String[] args) {
        SpriteRendering app = new SpriteRendering();
        app.start();

    }

    private EntitySet spriteEntities;

    @Override
    public void simpleInit2DApp() {
        GameObjectPool gameObjectPool = new GameObjectPool();
        SetCameraSize(50);
        int movingSprites = 6500;
        for (int i = 0; i < movingSprites; i++) {
            GameObject gameObject = gameObjectPool.CreateGameObject();
            AnimatedSprite sprite
                    = (AnimatedSprite) gameObject.addSprite(
                            "Sprite",
                            "Textures/Sprites.png",
                            new AnimationData("Idle", 5, 5, 0, 25, 2 * i, true)
                    );

            float x = (i / 200)-10;
            float y = (i % 50)-25;
            sprite.setLocalTranslation(x, y);
            rootNode.attachChild(sprite);
        }
        spriteEntities = gameObjectPool.getEntities(AnimatedSprite.class);
    }

    @Override
    public void simpleUpdate(float tpf) {

        spriteEntities.applyChanges();
        for (Entity e : spriteEntities) {
            AnimatedSprite sprite = e.get(AnimatedSprite.class);
            sprite.rotate2D(tpf * 10);
        }

    }

}
