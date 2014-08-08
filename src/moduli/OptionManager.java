/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moduli;

import java.io.Serializable;

/**
 *
 * @author simone
 */
public class OptionManager implements Serializable {
    private boolean fullscreen;
    private int x, y;
    private String optifineVersion;
    private String memory;

    public OptionManager(boolean fullscreen, int x, int y, String optifineVersion, String memory) {
        this.fullscreen = fullscreen;
        this.x = x;
        this.y = y;
        this.optifineVersion = optifineVersion;
        this.memory = memory;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getOptifineVersion() {
        return optifineVersion;
    }

    public void setOptifineVersion(String optifineVersion) {
        this.optifineVersion = optifineVersion;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
    
    
}
