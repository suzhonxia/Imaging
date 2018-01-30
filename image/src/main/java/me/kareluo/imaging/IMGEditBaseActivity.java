package me.kareluo.imaging;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;

import me.kareluo.imaging.core.IMGMode;
import me.kareluo.imaging.core.IMGStickerImage;
import me.kareluo.imaging.core.IMGText;
import me.kareluo.imaging.view.IMGColorGroup;
import me.kareluo.imaging.view.IMGView;

/**
 * Created by felix on 2017/12/5 下午3:08.
 */

abstract class IMGEditBaseActivity extends Activity implements View.OnClickListener,
        IMGTextEditDialog.Callback, RadioGroup.OnCheckedChangeListener,
        DialogInterface.OnShowListener, DialogInterface.OnDismissListener {

    protected IMGView mImgView;

    private RadioGroup mModeGroup;

    private IMGColorGroup mColorGroup;

    private IMGTextEditDialog mTextDialog;

    private View mLayoutOpSub;

    private ViewSwitcher mOpSwitcher, mOpSubSwitcher;

    public static final int OP_HIDE = -1;

    public static final int OP_NORMAL = 0;

    public static final int OP_CLIP = 1;

    public static final int OP_SUB_DOODLE = 0;

    public static final int OP_SUB_MOSAIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            setContentView(R.layout.image_edit_activity);
            initViews();
            mImgView.setImageBitmap(bitmap);
            onCreated();
        } else
            finish();
    }

    public void onCreated() {

    }

    private void initViews() {
        mImgView = findViewById(R.id.image_canvas);
        mModeGroup = findViewById(R.id.rg_modes);

        mOpSwitcher = findViewById(R.id.vs_op);
        mOpSubSwitcher = findViewById(R.id.vs_op_sub);

        mColorGroup = findViewById(R.id.cg_colors);
        mColorGroup.setOnCheckedChangeListener(this);

        mLayoutOpSub = findViewById(R.id.layout_op_sub);
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.rb_doodle) {// 涂鸦
            onModeClick(IMGMode.DOODLE);
        } else if (vid == R.id.btn_text) {// 添加文字
            onTextModeClick();
        } else if (vid == R.id.btn_image) {// 添加图片
            onImageModeClick();
        } else if (vid == R.id.rb_mosaic) {// 打马赛克
            onModeClick(IMGMode.MOSAIC);
        } else if (vid == R.id.btn_clip) {// 裁剪
            onModeClick(IMGMode.CLIP);
        } else if (vid == R.id.btn_undo) {// 撤销
            onUndoClick();
        } else if (vid == R.id.tv_done) {// 完成
            onDoneClick();
        } else if (vid == R.id.tv_cancel) {// 取消
            onCancelClick();
        } else if (vid == R.id.ib_clip_cancel) {// 取消裁剪
            onCancelClipClick();
        } else if (vid == R.id.ib_clip_done) {// 完成裁剪
            onDoneClipClick();
        } else if (vid == R.id.tv_clip_reset) {// 还原裁剪
            onResetClipClick();
        } else if (vid == R.id.ib_clip_rotate) {// 裁剪页面旋转
            onRotateClipClick();
        }
    }

    public void updateModeUI() {
        IMGMode mode = mImgView.getMode();
        switch (mode) {
            case DOODLE:
                mModeGroup.check(R.id.rb_doodle);
                setOpSubDisplay(OP_SUB_DOODLE);
                break;
            case MOSAIC:
                mModeGroup.check(R.id.rb_mosaic);
                setOpSubDisplay(OP_SUB_MOSAIC);
                break;
            case NONE:
                mModeGroup.clearCheck();
                setOpSubDisplay(OP_HIDE);
                break;
        }
    }

    /**
     * 点击了添加文字的按钮：弹出一个Dialog进行对文字编辑
     */
    public void onTextModeClick() {
        if (mTextDialog == null) {
            mTextDialog = new IMGTextEditDialog(this, this);
            mTextDialog.setOnShowListener(this);
            mTextDialog.setOnDismissListener(this);
        }
        mTextDialog.show();
    }

    /**
     * 点击了添加图片的按钮：构造一个对象做测试
     */
    public void onImageModeClick() {
        IMGStickerImage image = new IMGStickerImage();
        image.setResId(R.mipmap.comment);

        onImage(image);
    }

    @Override
    public final void onCheckedChanged(RadioGroup group, int checkedId) {
        onColorChanged(mColorGroup.getCheckColor());
    }

    /**
     * 根据标识切换页面(默认编辑页面和裁剪页面)
     *
     * @param op 0:默认编辑; 1:裁剪页面
     */
    public void setOpDisplay(int op) {
        if (op >= 0) {
            mOpSwitcher.setDisplayedChild(op);
        }
    }

    /**
     * 根据标识切换页面(涂鸦颜色选择和马赛克提示语)
     *
     * @param opSub 0:涂鸦颜色选择; 1:马赛克提示语
     */
    public void setOpSubDisplay(int opSub) {
        if (opSub < 0) {
            mLayoutOpSub.setVisibility(View.GONE);
        } else {
            mOpSubSwitcher.setDisplayedChild(opSub);
            mLayoutOpSub.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        mOpSwitcher.setVisibility(View.GONE);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mOpSwitcher.setVisibility(View.VISIBLE);
    }

    public abstract Bitmap getBitmap();

    /**
     * 点击了功能按钮
     *
     * @param mode 点击的那个功能标识
     */
    public abstract void onModeClick(IMGMode mode);

    /**
     * 点击了撤销按钮
     */
    public abstract void onUndoClick();

    /**
     * 点击了取消按钮
     */
    public abstract void onCancelClick();

    /**
     * 点击了完成按钮
     */
    public abstract void onDoneClick();

    /**
     * 点击了取消裁剪按钮
     */
    public abstract void onCancelClipClick();

    /**
     * 点击了完成裁剪按钮
     */
    public abstract void onDoneClipClick();

    /**
     * 点击了还原裁剪按钮
     */
    public abstract void onResetClipClick();

    /**
     * 点击了旋转裁剪按钮
     */
    public abstract void onRotateClipClick();

    /**
     * 涂鸦画笔颜色变化监听
     *
     * @param checkedColor 选中的颜色值
     */
    public abstract void onColorChanged(int checkedColor);

    @Override
    public abstract void onText(IMGText text);

    public abstract void onImage(IMGStickerImage image);
}
