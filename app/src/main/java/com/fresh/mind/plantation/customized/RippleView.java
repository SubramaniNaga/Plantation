package com.fresh.mind.plantation.customized;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.WebViewUrl;

/**
 * Created by AND I5 on 12-05-2017.
 */

@SuppressLint("ClickableViewAccessibility")
public class RippleView extends TextView {
    private float mDownX;
    private float mDownY;
    private float mAlphaFactor;
    private float mDensity;
    private float mRadius;
    private float mMaxRadius;

    private int mRippleColor;
    private boolean mIsAnimating = false;
    private boolean mHover = true;

    private RadialGradient mRadialGradient;
    private Paint mPaint;
    private ObjectAnimator mRadiusAnimator;

    private int dp(int dp) {
        return (int) (dp * mDensity + 0.5f);
    }

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(4);
    private final static int PROXIMA_NOVA_LIGHT = 0;
    private final static int PROXIMA_NOVA_BOLD = 1;
    private final static int PROXIMA_NOVA_SEMIBOLD = 2;
    private final static int PROXIMA_REGULAR = 3;


    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        int typefaceValue = values.getInt(R.styleable.CustomFontTextView_typeface, 0);
        values.recycle();

        setTypeface(obtaintTypeface(context, typefaceValue));
    }

    private Typeface obtaintTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        Typeface typeface = mTypefaces.get(typefaceValue);
        if (typeface == null) {
            typeface = createTypeface(context, typefaceValue);
            mTypefaces.put(typefaceValue, typeface);
        }
        return typeface;
    }

    private Typeface createTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        //fightingspiritital  blkchcry.ttf  RobotoCondensed-Bold.ttf  regular.ttf fragmentcore.otf avantiserif.ttf

        Typeface typeface;
        switch (typefaceValue) {
            case PROXIMA_NOVA_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "proximanovalight.otf");
                break;
            case PROXIMA_NOVA_BOLD:
                //BoldItalic
                typeface = Typeface.createFromAsset(context.getAssets(), "proximanovabold.otf");
                break;
            case PROXIMA_NOVA_SEMIBOLD:
                // Italic
                typeface = Typeface.createFromAsset(context.getAssets(), "proximanovasemibold.otf");
                break;
            case PROXIMA_REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "proximanovaregular.otf");
                break;

            default:
                throw new IllegalArgumentException("Unknown `typeface attribute value " + typefaceValue);
        }
        return typeface;
    }


    public void onClick(final FragmentActivity appDetails, final CustomTextView ref3) {
        ref3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ref3.getText().toString();
                Intent intent = new Intent(appDetails, WebViewUrl.class);
                intent.putExtra("url", "" + url);
                appDetails.startActivity(intent);
            }
        });
    }


    public RippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RippleView);
        mRippleColor = a.getColor(R.styleable.RippleView_rippleColor,
                mRippleColor);
        mAlphaFactor = a.getFloat(R.styleable.RippleView_alphaFactor,
                mAlphaFactor);
        mHover = a.getBoolean(R.styleable.RippleView_hover, mHover);
        a.recycle();

        parseAttributes(context, attrs);
    }

    public void init() {
        mDensity = getContext().getResources().getDisplayMetrics().density;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAlpha(100);
        setRippleColor(getResources().getColor(R.color.colorPrimary), 0.2f);

    }

    public void setRippleColor(int rippleColor, float alphaFactor) {
        mRippleColor = rippleColor;
        mAlphaFactor = alphaFactor;
    }

    public void setHover(boolean enabled) {
        mHover = enabled;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = (float) Math.sqrt(w * w + h * h);
    }

    private boolean mAnimationIsCancel;
    private Rect mRect;

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        Log.d("TouchEvent", String.valueOf(event.getActionMasked()));
        Log.d("mIsAnimating", String.valueOf(mIsAnimating));
        Log.d("mAnimationIsCancel", String.valueOf(mAnimationIsCancel));
        boolean superResult = super.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN
                && this.isEnabled() && mHover) {
            mRect = new Rect(getLeft(), getTop(), getRight(), getBottom());
            mAnimationIsCancel = false;
            mDownX = event.getX();
            mDownY = event.getY();

            mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", 0, dp(50))
                    .setDuration(400);
            mRadiusAnimator
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mIsAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    setRadius(0);
                    //ViewHelper.setAlpha(RippleView.this, 1);
                    mIsAnimating = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            mRadiusAnimator.start();
            if (!superResult) {
                return true;
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE
                && this.isEnabled() && mHover) {
            mDownX = event.getX();
            mDownY = event.getY();

            // Cancel the ripple animation when moved outside
            if (mAnimationIsCancel = !mRect.contains(
                    getLeft() + (int) event.getX(),
                    getTop() + (int) event.getY())) {
                setRadius(0);
            } else {
                setRadius(dp(50));
            }
            if (!superResult) {
                return true;
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP
                && !mAnimationIsCancel && this.isEnabled()) {
            mDownX = event.getX();
            mDownY = event.getY();

            final float tempRadius = (float) Math.sqrt(mDownX * mDownX + mDownY
                    * mDownY);
            float targetRadius = Math.max(tempRadius, mMaxRadius);

            if (mIsAnimating) {
                mRadiusAnimator.cancel();
            }
            mRadiusAnimator = ObjectAnimator.ofFloat(this, "radius", dp(50),
                    targetRadius);
            mRadiusAnimator.setDuration(500);
            mRadiusAnimator
                    .setInterpolator(new AccelerateDecelerateInterpolator());
            mRadiusAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    mIsAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    setRadius(0);
                    //ViewHelper.setAlpha(RippleView.this, 1);
                    mIsAnimating = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            mRadiusAnimator.start();
            if (!superResult) {
                return true;
            }
        }
        return superResult;
    }

    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public void setRadius(final float radius) {
        mRadius = radius;
        if (mRadius > 0) {
            mRadialGradient = new RadialGradient(mDownX, mDownY, mRadius,
                    adjustAlpha(mRippleColor, mAlphaFactor), mRippleColor,
                    Shader.TileMode.MIRROR);
            mPaint.setShader(mRadialGradient);
        }
        invalidate();
    }

    private Path mPath = new Path();

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode()) {
            return;
        }

        canvas.save(Canvas.CLIP_SAVE_FLAG);

        mPath.reset();
        mPath.addCircle(mDownX, mDownY, mRadius, Path.Direction.CW);

        canvas.clipPath(mPath);
        canvas.restore();

        canvas.drawCircle(mDownX, mDownY, mRadius, mPaint);
    }
}
