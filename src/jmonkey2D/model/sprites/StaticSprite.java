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
package jmonkey2D.model.sprites;

import jmonkey2D.model.sprites.data.SpriteData;
import com.jme3.material.Material;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.shader.VarType;
import static com.jme3.shader.VarType.Texture2D;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.simsilica.es.EntityComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmonkey2D.control.sprite.SpriteManager;

/**
 * The base class for 2D sprites
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class StaticSprite extends Geometry implements EntityComponent{

    /**
     * The layer the sprite is on value)
     */
    private int renderingLayer = 0;
    /**
     * The metadata for the sprite
     */
    private final SpriteData data;

    /**
     * Creates a sprite
     *
     * @param spriteSheetPath the path to the spritesheet
     */
    public StaticSprite(String spriteSheetPath) {
        super("Default_Sprite", new Quad(1, 1));
        this.data = new SpriteData(1, 1, 0);
        init(spriteSheetPath);
    }

    /**
     * Creates a sprite
     *
     * @param name the name of this sprite
     * @param spriteSheetPath the path to the spritesheet
     */
    public StaticSprite(String name, String spriteSheetPath) {
        super(name, new Quad(1, 1));
        this.data = new SpriteData(1, 1, 0);
        init(spriteSheetPath);
    }

    /**
     * Creates a sprite
     *
     * @param name the name of this sprite
     * @param spriteSheetPath the path to the spritesheet
     * @param data The metadata for the sprite
     */
    public StaticSprite(String name, String spriteSheetPath, SpriteData data) {
        super(name, new Quad(1, 1));
        this.data = data;
        init(spriteSheetPath);
    }

    private void init(String spriteSheetPath) {
        try {
            Texture2D spriteSheet = SpriteManager.getInstance().getTexture2D(spriteSheetPath, false);
            spriteSheet.setWrap(Texture.WrapMode.Repeat);
            Material mat1;
            mat1 = SpriteManager.getInstance().GetSpriteMaterial();
            mat1.setParam("AniTexMap", Texture2D, spriteSheet);
            mat1.setParam("startingTile", VarType.Int, data.getSpriteIndex());
            mat1.setParam("numTilesU", VarType.Int, data.getColumns());
            mat1.setParam("numTilesV", VarType.Int, data.getRows());
            mat1.setParam("loop", VarType.Int, 0);
            mat1.setParam("animationLength", VarType.Int, 1);
            mat1.setParam("Speed", VarType.Int, 0);
            setMaterial(mat1);

        } catch (IllegalAccessException ex) {
            Logger.getLogger(StaticSprite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 2D-rotation of the object
     *
     * @param angle the angle increment
     * @return the rotating sprite
     */
    public Spatial rotate2D(float angle) {
        super.rotate(0f, 0f, angle);
        return this;
    }

    /**
     * 2D-rotation of the object
     *
     * @param quaternion the angle increment
     * @return the rotating sprite
     */
    public Spatial rotate2D(Quaternion quaternion) {
        float[] angles = quaternion.toAngles(new float[3]);
        return rotate2D(angles[2]);
    }

    /**
     * 2D-translation of the object
     *
     * @param x the x-distance
     * @param y the y-distance
     */
    public void setLocalTranslation(float x, float y) {
        super.setLocalTranslation(x, y, renderingLayer);
    }

    /**
     * 2D-translation of the object4
     *
     * @param translation the translation-distance vector
     */
    public void setLocalTranslation(Vector2f translation) {
        setLocalTranslation(translation.x, translation.y);
    }

    /**
     *
     * @return the rendering layer this sprite currently is on
     */
    public int getRenderingLayer() {
        return renderingLayer;
    }

    /**
     * The renderingLayer is used to determine what sprite should be rendered on
     * top of the other (needs to be further implemented)
     *
     * @param renderingLayer
     */
    public void setRenderingLayer(int renderingLayer) {
        this.renderingLayer = renderingLayer;
        setLocalTranslation(getLocalTranslation().x, getLocalTranslation().y, this.renderingLayer);
    }

}
