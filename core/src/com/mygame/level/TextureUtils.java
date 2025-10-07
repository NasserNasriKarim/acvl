package com.mygame.level;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextureUtils {
    
    public static Texture resizeTexture(Texture originalTexture, int newWidth, int newHeight) {
        // Get Pixmap from the original texture
        Pixmap originalPixmap = getPixmapFromTexture(originalTexture);
        
        // Create a new Pixmap with the new dimensions
        Pixmap resizedPixmap = new Pixmap(newWidth, newHeight, originalPixmap.getFormat());
        
        // Draw the original Pixmap into the new Pixmap
        resizedPixmap.drawPixmap(originalPixmap,
                0, 0, originalPixmap.getWidth(), originalPixmap.getHeight(),
                0, 0, resizedPixmap.getWidth(), resizedPixmap.getHeight());
        
        // Create a new texture from the resized Pixmap
        Texture resizedTexture = new Texture(resizedPixmap);
        
        // Dispose of Pixmaps to free memory
        originalPixmap.dispose();
        resizedPixmap.dispose();
        
        return resizedTexture;
    }
    
    private static Pixmap getPixmapFromTexture(Texture texture) {
        texture.getTextureData().prepare();
        return texture.getTextureData().consumePixmap();
    }
}
