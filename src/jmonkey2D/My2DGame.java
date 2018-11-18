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

import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.sprites.AnimatedSprite;
import jmonkey2D.model.data.AnimationData;
import jmonkey2D.model.data.SpriteData;
import jmonkey2D.model.sprites.StaticSprite;

public class My2DGame extends Simple2DApplication {

    public static void main(String[] args) {
        My2DGame app = new My2DGame();
        app.start();
    }

    private AnimatedSprite[] animatedSprites;

    @Override
    public void simpleInit2DApp() {
        int movingSprites = 5000;
        animatedSprites = new AnimatedSprite[movingSprites];
        for (int i = 0; i < movingSprites; i++) {
            animatedSprites[i] = new AnimatedSprite("Textures/Sprites.png");

            AnimationData data = new AnimationData("Idle", 5, 5, 0, 25, 2 * i, true);
            //can set an animation or add multiple to the animated sprite object to pick one later
            animatedSprites[i].setAnimation(data);
            animatedSprites[i].setLocalTranslation((float) (-0.5 + Math.random()) * 5f, (float) (-0.5 + Math.random()) * 5f);
            rootNode.attachChild(animatedSprites[i]);
        }

        StaticSprite staticSprite = new StaticSprite("TestingSprite", "Textures/Sprites.png", new SpriteData(5, 5, 0));
        staticSprite.setLocalTranslation(1f, 0f);
    //    staticSprite.setRenderingLayer(1);
        rootNode.attachChild(staticSprite);

        staticSprite = new StaticSprite("TestingSprite", "Textures/Sprites.png", new SpriteData(5, 5, 15));
        staticSprite.setLocalTranslation(-1f, 0f);
    //    staticSprite.setRenderingLayer(-1);
        rootNode.attachChild(staticSprite);
    }

    @Override
    public void simpleUpdate(float tpf) {
        for (AnimatedSprite animatedSprite : animatedSprites) {
            animatedSprite.rotate2D(tpf * animatedSprite.getCurrentAnimation().getPlaybackSpeed());
            float x = (float) (Math.cos(getTimer().getTimeInSeconds())) * 2f;
            float y = (float) (Math.sin(getTimer().getTimeInSeconds())) * 2f;
            animatedSprite.setLocalTranslation(x, y);
        }

    }

}
