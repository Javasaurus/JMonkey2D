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
package platformer.model.data;

/**
 * This class is a simple wrapper for sprite parameters with the intent to serialize to json/xml later
 * @author DrJavaSaurus <javasaurusdev@gmail.com>
 */
public class SpriteData {

    /**
     * The amount of columns in the spritesheet
     */
    protected int columns;
    /**
     * The amount of rows in the spritesheet
     */
    protected int rows;
    /**
     * The starting index for the sprite (corresponds to the sprite index for static sprites)
     */
    protected int spriteIndex;

    /**
     * 
     * @param columns The amount of columns in the spritesheet
     * @param rows The amount of rows in the spritesheet
     * @param spriteIndex The starting index for the sprite (corresponds to the sprite index for static sprites)
     */
    public SpriteData(int columns, int rows, int spriteIndex) {
        this.columns = columns;
        this.rows = rows;
        this.spriteIndex = spriteIndex;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }  
    
}
