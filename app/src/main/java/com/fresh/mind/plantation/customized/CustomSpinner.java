package com.fresh.mind.plantation.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Spinner;

import com.fresh.mind.plantation.R;

/**
 * Created by AND I5 on 17-01-2017.
 */
public class CustomSpinner extends Spinner {


    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(4);
    private final static int PROXIMA_NOVA_LIGHT = 0;
    private final static int PROXIMA_NOVA_BOLD = 1;
    private final static int PROXIMA_NOVA_SEMIBOLD = 2;
    private final static int PROXIMA_REGULAR = 3;


    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        parseAttributes(context, attrs);
    }

    private void parseAttributes(Context context, AttributeSet attrs) {
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        int typefaceValue = values.getInt(R.styleable.CustomFontTextView_typeface, 0);
        values.recycle();


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


}


