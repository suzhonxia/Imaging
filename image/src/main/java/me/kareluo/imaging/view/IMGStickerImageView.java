package me.kareluo.imaging.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.kareluo.imaging.R;
import me.kareluo.imaging.core.IMGImage;
import me.kareluo.imaging.core.IMGStickerImage;
import me.kareluo.imaging.core.IMGText;

/**
 * Created by felix on 2017/12/21 下午10:58.
 */

public class IMGStickerImageView extends IMGStickerView {

    private static final String TAG = "IMGStickerImageView";

    private ImageView mImageView;

    private static final int PADDING = 26;

    public IMGStickerImageView(Context context) {
        super(context);
    }

    public IMGStickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IMGStickerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateContentView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setPadding(PADDING, PADDING, PADDING, PADDING);
        return mImageView;
    }

    public void setImage(IMGStickerImage image) {
        if (image != null && mImageView != null) {
            if (image.getResId() >= 0) {
                mImageView.setImageResource(image.getResId());
            } else if (!TextUtils.isEmpty(image.getPath())) {
                Glide.with(getContext()).load(image.getPath()).into(mImageView);
            }
        }
    }
}
