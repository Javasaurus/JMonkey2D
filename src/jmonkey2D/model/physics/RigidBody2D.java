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
package jmonkey2D.model.physics;

import com.simsilica.es.EntityComponent;
import jmonkey2D.model.sprites.Sprite;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Capsule;
import org.dyn4j.geometry.MassType;

/**
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class RigidBody2D extends Body implements EntityComponent {

    private double initialGravityScale = 1;

    public RigidBody2D() {
        addFixture(new BodyFixture(new Capsule(0.5, 2)));
        setDynamic();
    }

    public final void setStatic() {
        setMass(MassType.INFINITE);
        initialGravityScale = gravityScale;
        gravityScale = 0;
    }

    public final void setDynamic() {
        setMass(MassType.NORMAL);
        gravityScale = initialGravityScale;
    }

}
