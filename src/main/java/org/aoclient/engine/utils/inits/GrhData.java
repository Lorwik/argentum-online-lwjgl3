package org.aoclient.engine.utils.inits;

/**
 * Simula el "Type" o la estructura de {@code GrhData}, almacenando informacion detallada sobre los recursos graficos.
 * <p>
 * Esta clase mantiene los datos necesarios para representar un grafico (Grh), incluyendo su ubicacion en archivos de recursos,
 * dimensiones, y propiedades de animacion si corresponde.
 *
 * @see GrhInfo
 * @see org.aoclient.engine.utils.GameData
 * @see org.aoclient.engine.renderer.Drawn
 */

public final class GrhData {

    private short sX;
    private short sY;

    private int fileNum;

    private short pixelWidth;
    private short pixelHeight;

    private float tileWidth;
    private float tileHeight;

    private short numFrames;
    private int[] frames;

    private float speed;

    public GrhData() {

    }

    public short getsX() {
        return sX;
    }

    public void setsX(short sX) {
        this.sX = sX;
    }

    public short getsY() {
        return sY;
    }

    public void setsY(short sY) {
        this.sY = sY;
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }

    public short getPixelWidth() {
        return pixelWidth;
    }

    public void setPixelWidth(short pixelWidth) {
        this.pixelWidth = pixelWidth;
    }

    public short getPixelHeight() {
        return pixelHeight;
    }

    public void setPixelHeight(short pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    public float getTileWidth() {
        return tileWidth;
    }

    public void setTileWidth(float tileWidth) {
        this.tileWidth = tileWidth;
    }

    public float getTileHeight() {
        return tileHeight;
    }

    public void setTileHeight(float tileHeight) {
        this.tileHeight = tileHeight;
    }

    public short getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(short numFrames) {
        this.numFrames = numFrames;
    }

    public int getFrame(int index) {
        return frames[index];
    }

    public void setFrames(int[] frames) {
        this.frames = frames;
    }

    public void setFrame(int index, int frame) {
        this.frames[index] = frame;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
