package cn.bingoogolapple.bgamenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.nineoldandroids.view.ViewHelper;

public class BGAMenu extends HorizontalScrollView {
    private ViewGroup mWrapper;
    private View mMenu;
    private View mContent;
    private int mMenuWidth = 250;
    private boolean mIsMeasured = false;
    private int mWindowWidth;
    private boolean mIsOpened = false;
    private ShowMode mShowMode = ShowMode.Scale;

    public BGAMenu(Context context) {
        this(context, null);
    }

    public BGAMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGAMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mWindowWidth = displayMetrics.widthPixels;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGAMenu);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            if (R.styleable.BGAMenu_bgamenu_showMode == attr) {
                int ordinal = typedArray.getInt(attr, ShowMode.PullOut.ordinal());
                mShowMode = ShowMode.values()[ordinal];
            } else if (R.styleable.BGAMenu_bgamenu_menuWidth == attr) {
                /**
                 * getDimension和getDimensionPixelOffset的功能差不多,都是获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘;两个函数的区别是一个返回float,一个返回int. getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.
                 */
                mMenuWidth = typedArray.getDimensionPixelSize(attr, mMenuWidth);
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mIsMeasured) {
            mIsMeasured = true;
            mWrapper = (ViewGroup) this.getChildAt(0);
            mMenu = mWrapper.getChildAt(0);
            mContent = mWrapper.getChildAt(1);

            mMenu.getLayoutParams().width = mMenuWidth;
            mContent.getLayoutParams().width = mWindowWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (this.getScrollX() >= mMenuWidth / 2) {
                this.smoothScrollTo(mMenuWidth, 0);
                mIsOpened = false;
            } else {
                this.smoothScrollTo(0, 0);
                mIsOpened = true;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public void toggle() {
        if (mIsOpened) {
            this.close();
        } else {
            this.open();
        }
    }

    public void close() {
        if (mIsOpened) {
            this.smoothScrollTo(mMenuWidth, 0);
            mIsOpened = false;
        }
    }

    public void open() {
        if (!mIsOpened) {
            this.smoothScrollTo(0, 0);
            mIsOpened = true;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 此处的l就是this.getScrollX()的值
        float scale = 1.0f * l / mMenuWidth;

        switch (mShowMode) {
            case LayDown:
                /**
                 * 1.抽屉效果
                 * 滑动过程                             关闭 -> 打开
                 * l                                   mMenuWidth -> 0
                 * scale = 1.0f * l / mMenuWidth       1.0 -> 0
                 * translationX = mMenuWidth * scale   mMenuWidth -> 0
                 */
                ViewHelper.setTranslationX(mMenu, mMenuWidth * scale);
                break;
            case Scale:
                /**
                 * 2.QQ菜单效果
                 * 滑动过程                                       关闭 -> 打开
                 * l                                             mMenuWidth -> 0
                 * scale = 1.0f * l / mMenuWidth                 1.0 -> 0
                 *
                 * translationX = mMenuWidth * scale * 0.7f      mMenuWidth -> 0
                 * menuScale =  1.0f - 0.4f * scale              0.6 -> 1.0
                 *
                 * contentScale = 0.7f + scale * 0.3f            1.0 -> 0.7
                 */
                // 菜单部分
                float menuScale = 1.0f - 0.4f * scale;
                ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);
                ViewHelper.setScaleX(mMenu, menuScale);
                ViewHelper.setScaleY(mMenu, menuScale);
                ViewHelper.setAlpha(mMenu, menuScale);
                // 内容部分
                float contentScale = 0.7f + 0.3f * scale;
                ViewHelper.setPivotX(mContent, 0);
                ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
                ViewHelper.setScaleX(mContent, contentScale);
                ViewHelper.setScaleY(mContent, contentScale);
                break;
            default:
                break;
        }
    }

    public static enum ShowMode {
        LayDown, PullOut, Scale
    }
}