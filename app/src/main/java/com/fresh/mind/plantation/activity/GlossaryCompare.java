package com.fresh.mind.plantation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.adapter.base_adapter.AdapterGlossery;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.GlossaryTable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 02-01-2018.
 */

public class GlossaryCompare extends AppCompatActivity {
    private ListView listView;
    private GlossaryTable glossaryTable;
    public static String itemPoistion;
    public static int mSelectedPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark));
            //window.setNavigationBarColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
        }
        LanguageChange languageChange = new LanguageChange(GlossaryCompare.this);
        glossaryTable = new GlossaryTable(GlossaryCompare.this);
        String languages = languageChange.getStatus();
        Intent bundle = getIntent();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", GlossaryCompare.this);
        } else {
            Utils.setLocalLanguage("en", GlossaryCompare.this);
        }

        setContentView(R.layout.activity_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        /**
         * Set Toolbar's title
         */
        TextView txtToolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txtToolbarTitle.setText("Glossary Info");

        /**
         * To set Back arrow
         */

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        Config.SELECTED_TAB_VIEW_POSITION = 0;
        itemPoistion = bundle.getStringExtra("itemPoistion");
        Log.d("itemPoistion", "" + itemPoistion);
        ArrayList<HashMap<String, String>> hashMaps = glossaryTable.getGlossaryDetails(languages);
        listView = (ListView) findViewById(R.id.listView);
        AdapterGlossery adapterGlossery = new AdapterGlossery(GlossaryCompare.this, hashMaps);
        listView.setAdapter(adapterGlossery);

        if (!itemPoistion.isEmpty()) {
            mSelectedPosition = Integer.parseInt(itemPoistion);
            listView.setSelection(mSelectedPosition);
            adapterGlossery.notifyDataSetChanged();
        } else {
            mSelectedPosition = -1;
        }

    }
}
