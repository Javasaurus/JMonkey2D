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
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.es.EntitySet;
import jmonkey2D.control.GameObjectPool;
import jmonkey2D.control.system.Simple2DApplication;
import jmonkey2D.model.physics.RigidBody2D;
import org.dyn4j.dynamics.World;

/**
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class TileMapState extends AbstractAppState {

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

    public TileMapState(GameObjectPool gameObjectPool) {
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
        this.application.getCamera().setLocation(new Vector3f(8, 3.5f, this.application.getCamera().getLocation().z));
    }



    @Override
    public void update(float tpf) {

    }

    private void RegisterNewBodies() {

    }

    private void RemoveOldBodies() {

    }

    private void SynchronizeBodies() {

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

    public Node getBaseNode() {
        return baseNode;
    }

}
