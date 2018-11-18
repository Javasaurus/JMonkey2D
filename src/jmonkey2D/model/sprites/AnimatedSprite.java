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

import jmonkey2D.model.data.AnimationData;
import com.jme3.material.Material;
import com.jme3.shader.VarType;
import java.util.HashMap;

/**
 * This class represents an animated sprite
 *
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class AnimatedSprite extends StaticSprite {

    /**
     * A map of animations that can be played (these can then be set at a later
     * point by an animation controller)
     */
    private HashMap<String, AnimationData> animationMap = new HashMap<>();
    /**
     * The name of the currently playing animation (this might move to the
     * animation data later)
     */
    private String currentAnimationName = "";

    /**
     * Creates an animated sprite
     *
     * @param spriteSheetPath the path to the spritesheet
     */
    public AnimatedSprite(String spriteSheetPath) {
        super(spriteSheetPath);
    }

    /**
     * Creates a sprite
     *
     * @param name the name of this sprite
     * @param spriteSheetPath the path to the spritesheet
     */
    public AnimatedSprite(String name, String spriteSheetPath) {
        super(name, spriteSheetPath);
    }

    /**
     * Creates a sprite
     *
     * @param name the name of this sprite
     * @param spriteSheetPath the path to the spritesheet
     * @param data The metadata for the sprite
     */
    public AnimatedSprite(String name, String spriteSheetPath, AnimationData data) {
        super(name, spriteSheetPath, data);

    }

    /**
     * Adds an animation to the animation pool for this sprite
     *
     * @param data the animation data
     * @return a boolean indicating if the animation was succesfully added
     */
    public boolean addAnimation(AnimationData data) {
        return animationMap.put(data.getAnimationName(), data) != null;
    }

    /**
     * Sets the current animation to the one described in the animation data
     * object. This method simultanuously adds the data to the animation pool
     *
     * @param data the animation data
     */
    public void setAnimation(AnimationData data) {
        //get the material
        Material mat1 = getMaterial();
        //set where the sprite can be found on the spritesheet
        mat1.setParam("startingTile", VarType.Int, data.getSpriteIndex());
        mat1.setParam("numTilesU", VarType.Int, data.getColumns());
        mat1.setParam("numTilesV", VarType.Int, data.getRows());
        mat1.setParam("loop", VarType.Int, data.isLooping() ? 1 : 0);
        mat1.setParam("animationLength", VarType.Int, data.getAnimationLength());
        mat1.setParam("Speed", VarType.Int, data.getPlaybackSpeed());
        //set the geometry back 
        setMaterial(mat1);
        animationMap.put(data.getAnimationName(), data);
        currentAnimationName = data.getAnimationName();
    }

    /**
     * Sets the current animation to the one described in the animation data
     * object.
     *
     * @param animationName the animation name as stored in the animation pool
     */
    public void SetAnimation(String animationName) {
        setAnimation(animationMap.get(animationName));
    }

    public HashMap<String, AnimationData> getAnimationMap() {
        return animationMap;
    }

    public void setAnimationMap(HashMap<String, AnimationData> animationMap) {
        this.animationMap = animationMap;
    }

    public AnimationData getAnimation(String animationName) {
        return this.animationMap.get(animationName);
    }

    public String getCurrentAnimationName() {
        return currentAnimationName;
    }
    
    public AnimationData getCurrentAnimation(){
        return getAnimation(currentAnimationName);
    }

}
