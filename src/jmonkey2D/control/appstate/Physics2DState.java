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
import jmonkey2D.model.physics.RigidBody2D;
import jmonkey2D.model.sprites.AnimatedSprite;
import jmonkey2D.model.sprites.StaticSprite;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class Physics2DState extends AbstractAppState {

    /**
     * The dynamics engine
     */
    private World world;
    /**
     * The entity set containing all physics bodies that need to be updated and
     * synchronized
     */
    private EntitySet rigidBodies;

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

    public Physics2DState(GameObjectPool gameObjectPool) {
        this.gameObjectPool = gameObjectPool;

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.application = (Simple2DApplication) app;
        this.baseNode = new Node("Base Node");
        this.application.getRootNode().attachChild(baseNode);
        this.world = new World();
        setEnabled(true);
        rigidBodies = gameObjectPool.getEntities(RigidBody2D.class);
    }

    @Override
    public void update(float tpf) {
        //step the world
        world.update(tpf);
        //apply changes to the rigidbody set
        rigidBodies.applyChanges();
        //add new rigidbodies
        RegisterNewBodies();
        //remove old rigidbodies
        RemoveOldBodies();
        //sync others
        SynchronizeBodies();
    }

    private void RegisterNewBodies() {
        Set<Entity> addedEntities = rigidBodies.getAddedEntities();
        for (Entity entity : addedEntities) {
            RigidBody2D body = (RigidBody2D) entity.get(RigidBody2D.class);
            this.world.addBody(body);
            //FOR DEBUGGING
            body.applyForce(new Vector2(0f, 10f));
        }
    }

    private void RemoveOldBodies() {
        Set<Entity> removedEntities = rigidBodies.getRemovedEntities();
        for (Entity entity : removedEntities) {
            RigidBody2D body = (RigidBody2D) entity.get(RigidBody2D.class);
            this.world.removeBody(body);
        }
    }

    private void SynchronizeBodies() {
        for (Entity entity : rigidBodies) {

            Entity tmp = gameObjectPool.getEntity(entity.getId(), RigidBody2D.class, AnimatedSprite.class, StaticSprite.class);
            RigidBody2D body = (RigidBody2D) tmp.getComponents()[0];

            StaticSprite sprite = (StaticSprite) (tmp.getComponents()[1] == null ? tmp.getComponents()[2] : tmp.getComponents()[1]);

            float x = (float) body.getWorldCenter().x;
            float y = (float) body.getWorldCenter().y;
            float rot = (float) body.getTransform().getRotation();

            if (sprite != null) {
                sprite.setLocalTranslation(x, y);
                sprite.rotate2D(rot);
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
