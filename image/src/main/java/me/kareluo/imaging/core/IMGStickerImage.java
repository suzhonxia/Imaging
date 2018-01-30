package me.kareluo.imaging.core;

/**
 * @author Sun
 * @data 2018/1/30
 * @desc
 */
public class IMGStickerImage {
    private int resId;
    private String path;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public IMGStickerImage() {
    }

    public IMGStickerImage(int resId, String path) {
        this.resId = resId;
        this.path = path;
    }

    @Override
    public String toString() {
        return "IMGStickerImage{" +
                "resId=" + resId +
                ", path='" + path + '\'' +
                '}';
    }
}
