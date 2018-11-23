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
package jmonkey2D.model;

import com.jme3.texture.Texture2D;
import com.simsilica.es.EntityId;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.model.sprites.data.AnimationData;
import jmonkey2D.model.sprites.data.SpriteData;
import jmonkey2D.model.physics.RigidBody2D;
import jmonkey2D.model.sprites.AnimatedSprite;
import jmonkey2D.model.sprites.Sprite;

/**
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class GameObject {

    /**
     * The game object pool (entitydata)
     */
    private final GameObjectPool gameObjectPool;
    /**
     * The entity ID this object has in the game object pool
     */
    private final EntityId entityID;

    /**
     * Creates a game object
     *
     * @param gameObjectPool game object pool
     */
    public GameObject(GameObjectPool gameObjectPool) {
        this.entityID = gameObjectPool.createEntity();
        this.gameObjectPool = gameObjectPool;
    }

    public EntityId getEntityID() {
        return entityID;
    }

    /**
     * Adds a sprite to this object
     *
     * @param spriteSheetPath the path to the spritesheet
     * @return the attached sprite component
     */
    public Sprite addSprite(String spriteSheetPath) {
        Sprite sprite = new Sprite(spriteSheetPath);
        gameObjectPool.setComponent(entityID, sprite);
        return sprite;
    }

    /**
     * Adds a sprite to this object
     *
     * @param name the name of this sprite
     * @param spriteSheetPath the path to the spritesheet
     * @return the attached sprite component
     */
    public Sprite addSprite(String name, String spriteSheetPath) {
        Sprite sprite = new Sprite(name, spriteSheetPath);
        gameObjectPool.setComponent(entityID, sprite);
        return sprite;
    }

    /**
     * Adds a sprite to this object
     *
     * @param name the name of this sprite
     * @param spriteSheetPath the path to the spritesheet
     * @param data the sprite data parameters (determines if this should be
     * animated)
     * @return the attached sprite component
     */
    public Sprite addSprite(String name, String spriteSheetPath, SpriteData data) {

        Sprite sprite;
        if (data instanceof AnimationData) {
            sprite = new AnimatedSprite(name, spriteSheetPath, (AnimationData) data);
        } else {
            sprite = new Sprite(name, spriteSheetPath, data);
        }

        gameObjectPool.setComponent(entityID, sprite);
        return sprite;
    }

    public Sprite addSprite(String name, Texture2D tileTexture, SpriteData spriteData) {
        Sprite sprite;
        if (spriteData instanceof AnimationData) {
            sprite = new AnimatedSprite(name, tileTexture, (AnimationData) spriteData);
        } else {
            sprite = new Sprite(name, tileTexture, spriteData);
        }

        gameObjectPool.setComponent(entityID, sprite);
        return sprite;
    }

    /**
     * Adds a rigidbody to the game object if needed
     *
     * @param isStatic boolean indicating if the object will be static (for
     * example a wall, an obstacle, ...)
     * @return the attached rigidbody
     */
    public final RigidBody2D addRigidBody2D(boolean isStatic) {
        RigidBody2D body = new RigidBody2D();
        if (isStatic) {
            body.setStatic();
        }
        gameObjectPool.setComponent(entityID, body);
        return body;
    }

}
