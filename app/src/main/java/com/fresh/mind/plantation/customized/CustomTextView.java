package com.fresh.mind.plantation.customized;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.WebViewUrl;


/**
 * Created by AND I5 on 14-03-2016.
 */
public class CustomTextView extends TextView {

    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(4);
    private final static int PROXIMA_NOVA_LIGHT = 0;
    private final static int PROXIMA_NOVA_BOLD = 1;
    private final static int PROXIMA_NOVA_SEMIBOLD = 2;
    private final static int PROXIMA_REGULAR = 3;


    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs);
    }

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


}

