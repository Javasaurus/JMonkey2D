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
package jmonkey2D.model.sprites.data;

/**
 * This class is a simple wrapper for animation parameters with the intent to serialize to json/xml later
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class AnimationData extends SpriteData {

    /**
     * The name of the animation
     */
    private final String animationName;
    /**
     * The framelength of the animation
     */
    private final int animationLength;
    /**
     * The playback speed for the animation
     */
    private final int playbackSpeed;
    /**
     * Boolean to indicate if the animation should be looping
     */
    private final boolean looping;

    /**
     * 
     * @param animationName The name of the animation
     * @param columns The amount of columns in the spritesheet
     * @param rows The amount of rows in the spritesheet
     * @param spriteIndex The starting index for the sprite (corresponds to the sprite index for static sprites)
     * @param animationLength The framelength of the animation
     * @param playbackSpeed The playback speed for the animation
     * @param looping Boolean to indicate if the animation should be looping
     */
    public AnimationData(String animationName, int columns, int rows, int spriteIndex, int animationLength, int playbackSpeed, boolean looping) {
        super(columns,rows,spriteIndex);
        this.animationName = animationName;
        this.animationLength = animationLength;
        this.playbackSpeed = playbackSpeed;
        this.looping = looping;
    }

    public String getAnimationName() {
        return animationName;
    }

    public int getAnimationLength() {
        return animationLength;
    }

    public int getPlaybackSpeed() {
        return playbackSpeed;
    }

    public boolean isLooping() {
        return looping;
    }

}
