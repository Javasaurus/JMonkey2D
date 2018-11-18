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
package jmonkey2D.control.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.simsilica.es.Entity;
import com.simsilica.es.EntitySet;
import java.util.Set;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.sprites.AnimatedSprite;
import jmonkey2D.model.sprites.StaticSprite;

/**
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class AnimationState extends AbstractAppState {

    /**
     * The entity set containing all animated sprites that need to be updated
     * and synchronized
     */
    private EntitySet animatedSpriteSet;

    /**
     * The game object pool to spawn objects with (and has access to the entity
     * manager)
     */
    private final GameObjectPool gameObjectPool;
    /**
     * The application object
     */
    private Simple2DApplication application;
    /**
     * The local base node (root for the local state)
     */
    private Node baseNode;

    public AnimationState(GameObjectPool gameObjectPool) {
        this.gameObjectPool = gameObjectPool;

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.application = (Simple2DApplication) app;
        this.baseNode = new Node("Base Node");
        this.application.getRootNode().attachChild(baseNode);
        setEnabled(true);
        //update the set of animation sprites
        animatedSpriteSet = gameObjectPool.getEntities(AnimatedSprite.class);
    }

    @Override
    public void update(float tpf) {
        //apply any changes to the set
        if (animatedSpriteSet.applyChanges()) {
            //check for new animated sprites
            AddNewAnimations();
            //check if any animation can be updated
            CheckAnimations();
            //remove any obsolete animations
            RemoveOldAnimations();
        }
    }

    private void AddNewAnimations() {
        Set<Entity> newSprites = animatedSpriteSet.getAddedEntities();
        System.out.println("Adding " + newSprites.size());
        for (Entity entity : newSprites) {
            AnimatedSprite sprite = entity.get(AnimatedSprite.class);
            if (sprite != null) {
                baseNode.attachChild(sprite);
            }
        }
    }

    private void CheckAnimations() {
        Set<Entity> updatedAnimations = animatedSpriteSet.getChangedEntities();
        System.out.println("Updating " + updatedAnimations.size());
        for (Entity entity : updatedAnimations) {
            //this is where the entity's animation can be changed by setting it in the AnimatedSprite object,
            //not yet implemented
        }
    }

    private void RemoveOldAnimations() {
        Set<Entity> oldAnimations = animatedSpriteSet.getChangedEntities();
        System.out.println("Removing " + oldAnimations.size());
        for (Entity entity : oldAnimations) {
            AnimatedSprite sprite = entity.get(AnimatedSprite.class);
            if (sprite != null) {
                baseNode.detachChild(sprite);
            }
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        this.application.getRootNode().detachChild(baseNode);
    }

    @Override
    public void setEnabled(boolean enabled) {

        super.setEnabled(enabled);
        if (enabled) {

            this.application.getRootNode().attachChild(baseNode);
        } else {
            this.application.getRootNode().detachChild(baseNode);
        }

    }

}
