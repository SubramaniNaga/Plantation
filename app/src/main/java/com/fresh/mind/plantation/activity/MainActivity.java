package com.fresh.mind.plantation.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.fresh.mind.plantation.BuildConfig;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomEditeText;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.customized.CustomTypefaceSpan;
import com.fresh.mind.plantation.fragment.Weathers;
import com.fresh.mind.plantation.fragment.menu_page.AppDetails;
import com.fresh.mind.plantation.fragment.menu_page.ContactInformation;
import com.fresh.mind.plantation.fragment.Inside.Directions;
import com.fresh.mind.plantation.fragment.menu_page.FeedBack;
import com.fresh.mind.plantation.fragment.menu_page.Glossary;
import com.fresh.mind.plantation.fragment.menu_page.Help;
import com.fresh.mind.plantation.fragment.Inside.LocationSelected;
import com.fresh.mind.plantation.fragment.Inside.SelectedTreeType;
import com.fresh.mind.plantation.fragment.inside_tabs.LocationType;
import com.fresh.mind.plantation.fragment.menu_page.Others;
import com.fresh.mind.plantation.fragment.menu_page.Schemes;
import com.fresh.mind.plantation.fragment.menu_page.SearchTree;
import com.fresh.mind.plantation.fragment.Inside.SearchedByTree;
import com.fresh.mind.plantation.tab_pager.HomeTabView;
import com.fresh.mind.plantation.fragment.menu_page.SplashScreen;
import com.fresh.mind.plantation.fragment.inside_tabs.ToolsSettings;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeSpecies;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeType;
import com.fresh.mind.plantation.fragment.menu_page.Tutorials;
import com.fresh.mind.plantation.fragment.menu_page.SettingUpdate;
import com.fresh.mind.plantation.service.UpdateData;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import io.fabric.sdk.android.Fabric;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fresh.mind.plantation.R.id.address;
import static com.fresh.mind.plantation.R.id.searchView;
import static com.fresh.mind.plantation.R.id.weatherImage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    public static CustomTextView title, title1;
    private ImageView back;
    private String mCurrentFragment = "Home";
    boolean doubleBackToExitPressedOnce = false;
    public static CustomEditeText mSearchViewEdit;
    public static ConstraintLayout mSearchViewImg;
    public static LinearLayout mSearchViewLayout;
    public static boolean userIsInteracting = false;
    public static CustomTextView tempTxt;
    public static ImageView weatherImage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                //.penaltyDeathOnCleartextNetwork()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                //.penaltyDeath()

                .build());*/
       /* if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }*/
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);

        LanguageChange languageChange = new LanguageChange(getApplicationContext());
        String languages = languageChange.getStatus();
        if (languages != null) {
            if (languages.equals("1")) {
                Utils.setLocalLanguage("ta", MainActivity.this);
            } else {
                Utils.setLocalLanguage("en", MainActivity.this);
            }
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("languages", "2");
            languageChange.onInsert(contentValues);
        }

        setContentView(R.layout.activity_main);
        //getExternalFilesDir("sdcard/Treepedia");
        init();

        //pass(new SplashScreen());
        SplashScreen splashScreen = new SplashScreen();
        Bundle bundle = new Bundle();
        bundle.putString("pass", "splash");
        splashScreen.setArguments(bundle);

/*
        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(intent);*/

        pass(splashScreen);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    private void init() {
        Window window = getWindow();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark));
            //window.setNavigationBarColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
        } else {
            // do something for phones running an SDK before lollipop
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mSearchViewImg = (ConstraintLayout) findViewById(R.id.searchView);
        mSearchViewEdit = (CustomEditeText) findViewById(R.id.mSearchViewEdit);
        mSearchViewLayout = (LinearLayout) findViewById(R.id.mSearchViewLayout);
        tempTxt = (CustomTextView) findViewById(R.id.tempDetails);
        weatherImage = (ImageView) findViewById(R.id.weatherImage);
        title1 = (CustomTextView) findViewById(R.id.title1);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle(null);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up_from_bottom);
        navigationView.setAnimation(animation);

        /*Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_right_1);
        navigationView.startAnimation(animation);*/
        title = (CustomTextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);

        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Log.d("currentapiVersion", "" + currentapiVersion + "   " + android.os.Build.VERSION_CODES.LOLLIPOP);
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            back.setBackground(getDrawable(R.drawable.ripple_back));
            //  mSearchViewImg.setBackground(getDrawable(R.drawable.ripple_back));
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "proximanovasemibold.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    @Override
    public void onBackPressed() {
        Log.d("fargment mCurrentFragment", "" + mCurrentFragment);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

       /* Fragment ff = getSupportFragmentManager().findFragmentById(R.id.container_body);
        if (ff instanceof HomeTabView) {
            if (doubleBackToExitPressedOnce) {
                FragmentManager fm = getSupportFragmentManager();
                if (fm != null) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                }
                final Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                super.onBackPressed();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 10000);
        } else if (ff instanceof TreeSpecies || ff instanceof LocationType) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();

        } else {
            super.onBackPressed();
        }*/
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mCurrentFragment.equals(getResources().getString(R.string.chooseByLocation)) || mCurrentFragment.equals(getResources().getString(R.string.ContactUs))
                || mCurrentFragment.equals(getResources().getString(R.string.glossary)) ||
                mCurrentFragment.equals(getResources().getString(R.string.appDetails))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else if (mCurrentFragment.equals(getResources().getString(R.string.searchTree))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else if (mCurrentFragment.equals(getResources().getString(R.string.searchResult))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchTree()).commit();
        } else if (mCurrentFragment.equals(getResources().getString(R.string.treeSpecies) + "11")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();
        } else if (mCurrentFragment.equals(getResources().getString(R.string.treeSpecies) + "1")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else if (mCurrentFragment.equals("Location Selection")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else if (mCurrentFragment.equals("Location Details")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).commit();
        } else if (mCurrentFragment.equals("Choose By Location11")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).commit();
        } else if (mCurrentFragment.equals(getResources().getString(R.string.SelectedTreeType))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else if (mCurrentFragment.equals(getResources().getString(R.string.treeType) + "1")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else if (mCurrentFragment.equals("Tree Species1") || mCurrentFragment.equals(getResources().getString(R.string.UpdateData))) {
            pass(new HomeTabView());
        } else if (mCurrentFragment.equals(getString(R.string.tutorials)) || mCurrentFragment.equals("Tree Type11") || mCurrentFragment.equals(getString(R.string.Schemes)) || mCurrentFragment.equals(getString(R.string.Feedback))) {
            pass(new HomeTabView());
        } else if (mCurrentFragment.equals(getString(R.string.directions))) {
            pass(new ContactInformation());
        } else if (mCurrentFragment.equals("Tree Type111")) {
            pass(new SelectedTreeType());
        } else if (mCurrentFragment.equals("Tree Species11")) {
            pass(new SearchedByTree());
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        boolean userIsInteracting = true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            pass(new HomeTabView());
        } else if (id == R.id.nav_choose_by_tree) {
            pass(new TreeType());
        } else if (id == R.id.nav_choose_by_location) {
            pass(new LocationType());
        } else if (id == R.id.nav_search) {
            pass(new SearchTree());
        } else if (id == R.id.nav_glossery) {
            pass(new Glossary());
        } else if (id == R.id.nav_tools) {
            pass(new ToolsSettings());
        } else if (id == R.id.nav_contact) {
            pass(new ContactInformation());
        } else if (id == R.id.nav_app_details) {
            pass(new AppDetails());
        } else if (id == R.id.nav_help) {
            pass(new Help());
        } else if (id == R.id.nav_others) {
            Bundle bundle = new Bundle();
            bundle.putString("task", "check");
            Fragment fragment = new SettingUpdate();
            fragment.setArguments(bundle);
            pass(fragment);

/*
            Intent intent = new Intent(MainActivity.this, UpdateActiity.class);
            intent.putExtra("task", "check");
            startActivity(intent);*/


        } else if (id == R.id.nav_tutorial) {
            pass(new Tutorials());
        } else if (id == R.id.nav_direction) {
            pass(new Directions());
        } else if (id == R.id.nav_Others) {
            pass(new Schemes());
        } else if (id == R.id.nav_feedback) {
            pass(new FeedBack());
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void pass(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).commit();
    }


    public void species(String titleStr) {
        Log.d("titltitleStreStr", "" + titleStr);
        navigationView.getMenu().getItem(0).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        // title.setText("" + titleStr);
        mCurrentFragment = titleStr;
        title1.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        if (Config.SEARCH_STR_VALUES.length() == 0) {
            mSearchViewEdit.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            mSearchViewImg.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
            // mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
        } else if (Config.SEARCH_STR_VALUES != "") {
            mSearchViewLayout.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            mSearchViewImg.setVisibility(View.VISIBLE);
            // mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
            mSearchViewEdit.setText("" + Config.SEARCH_STR_VALUES);
            mSearchViewEdit.setVisibility(View.VISIBLE);


            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setHomeButtonEnabled(false);
            toggle.setDrawerIndicatorEnabled(false);

        }
       /* mSearchViewEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                TreeSpecies.adapter_ttreeSpecies.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchViewLayout.setVisibility(View.GONE);
                Config.SEARCH_STR_VALUES = "";
                mSearchViewEdit.setText("");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
            }
        });
      /*  mSearchViewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchViewLayout.getVisibility() == View.VISIBLE) {
                    mSearchViewLayout.setVisibility(View.GONE);
                    // mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
                    title.setVisibility(View.VISIBLE);
                    mSearchViewEdit.setHint("Search...");
                    mSearchViewEdit.setText("");
                    mSearchViewEdit.setVisibility(View.GONE);
                    Config.SEARCH_STR_VALUES = "";
                    back.setVisibility(View.GONE);
                    navigationView.getMenu().getItem(1).setChecked(true);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getSupportActionBar().show();
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    getSupportActionBar().setHomeButtonEnabled(true);
                    toggle.setDrawerIndicatorEnabled(true);
                } else {

                    mSearchViewEdit.setVisibility(View.VISIBLE);
                    mSearchViewEdit.requestFocus();
                    mSearchViewEdit.setFocusableInTouchMode(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mSearchViewEdit, InputMethodManager.SHOW_FORCED);
                    mSearchViewLayout.setVisibility(View.VISIBLE);
                    title.setVisibility(View.GONE);
                    //  mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    getSupportActionBar().setHomeButtonEnabled(false);
                    toggle.setDrawerIndicatorEnabled(false);
                    back.setVisibility(View.VISIBLE);


                }
            }
        });*/
     /*   mSearchViewEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // If it loses focus...
                if (!hasFocus) {
                    // Hide soft keyboard.
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSearchViewEdit.getWindowToken(), 0);
                    // Make it non-editable again.
                    mSearchViewEdit.setKeyListener(null);
                }
            }
        });*/
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void toolBarVisiables() {

        if (mSearchViewLayout.getVisibility() == View.VISIBLE) {
            mSearchViewLayout.setVisibility(View.GONE);
            //  mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
            title.setVisibility(View.VISIBLE);
            mSearchViewEdit.setHint("Search...");
            mSearchViewEdit.setText("");
        } else {
            /*mSearchViewLayout.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));*/
        }

    }

    public void setTreeType(String titleStr, String view) {
        toolBarVisiables();
        mSearchViewImg.setVisibility(View.VISIBLE);
        //  navigationView.getMenu().getItem(1).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        //title.setText("Tree Plantation");
        title.setVisibility(View.VISIBLE);
        //mCurrentFragment = titleStr;
        mCurrentFragment = "Home";
        title1.setVisibility(View.GONE);
        Log.d("viewkj", "" + view + "  " + titleStr);
        if (view.equals("1")) {
            back.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            back.setVisibility(View.VISIBLE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setHomeButtonEnabled(true);
            toggle.setDrawerIndicatorEnabled(false);
            title.setText("" + titleStr);
            //title1.setVisibility(View.VISIBLE);
            //title1.setText("" + titleStr);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pass(new HomeTabView());
                }
            });
        }
       /* mSearchViewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSearchViewLayout.getVisibility() == View.VISIBLE) {
                    mSearchViewLayout.setVisibility(View.GONE);
                    //  mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
                    title.setVisibility(View.VISIBLE);
                    // title1.setVisibility(View.VISIBLE);
                    mSearchViewEdit.setHint("Search...");
                    mSearchViewEdit.setText("");
                    //  TreeSpecies.mTreeSpecies.setAdapter(new Adapter_Tree_Species(MainActivity.this, TreeSpecies.mLocation));

                } else {
                    mSearchViewLayout.setVisibility(View.VISIBLE);
                    title.setVisibility(View.GONE);
                    //   mSearchViewImg.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
                }
            }
        });*/

       /* mSearchViewEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TreeType.adapter_tree_type.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    public void setLocation(String titleStr, String fargment) {
        Log.d("jdsafgykh212564", "" + titleStr + "   " + fargment);
        toolBarVisiables();
        mSearchViewImg.setVisibility(View.VISIBLE);

        if (fargment.equals(getResources().getString(R.string.chooseByLocation))) {
            //navigationView.getMenu().getItem(2).setChecked(true);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setHomeButtonEnabled(true);
            toggle.setDrawerIndicatorEnabled(true);
            back.setVisibility(View.GONE);

        } /*else if (titleStr.equals("Tree Species")) {
            //navigationView.getMenu().getItem(0).setChecked(true);
        } else if (titleStr.equals("Tree Type")) {
            //navigationView.getMenu().getItem(1).setChecked(true);

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setHomeButtonEnabled(false);
            toggle.setDrawerIndicatorEnabled(false);
            back.setVisibility(View.VISIBLE);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new Sesss()).commit();
                }
            });
        }*/ else if (fargment.equals("Location Selection")) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setHomeButtonEnabled(false);
            toggle.setDrawerIndicatorEnabled(false);
            back.setVisibility(View.VISIBLE);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                }
            });

        }
        //title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = fargment;

    }

    public void viewTreeDetails(String titleStr, final String fargment) {
        toolBarVisiables();
        mSearchViewImg.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        back.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(null);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        title.setText("" + titleStr);
        title.setVisibility(View.GONE);
        title1.setVisibility(View.VISIBLE);
        title1.setText("" + titleStr);
        mCurrentFragment = fargment;

        //  title1.setText("" + titleStr);
        // title1.setVisibility(View.VISIBLE);
        Log.d("dsadlk;", "" + titleStr + "  " + fargment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fargment", "" + fargment);
                if (fargment.equals("Tree Species1")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                } else if (fargment.equals("Tree Type111")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).commit();
                } else if (fargment.equals("Choose By Location11")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).commit();
                } else if (fargment.equals(getResources().getString(R.string.SelectedTreeType))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                } else if (fargment.equals("Location Selection")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                } else if (fargment.equals(getResources().getString(R.string.searchResult))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchTree()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();
                }
            }
        });
    }

    public void setSearch(String titleStr) {
        toolBarVisiables();
        mSearchViewImg.setVisibility(View.VISIBLE);
        navigationView.getMenu().getItem(3).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title1.setVisibility(View.GONE);
        title.setText("" + titleStr);
        mCurrentFragment = titleStr;
        title.setVisibility(View.VISIBLE);

    }

    public void setGlossary(String titleStr) {
        toolBarVisiables();

        navigationView.getMenu().getItem(4).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = titleStr;
    }

    public void setTools(String titleStr) {
        toolBarVisiables();

        navigationView.getMenu().getItem(5).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        // title.setText("" + titleStr);
        //title.setText("Tree Plantation");
        mCurrentFragment = titleStr;
    }

    public void setContact(String titleStr) {
        toolBarVisiables();

        navigationView.getMenu().getItem(6).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + titleStr);
        mCurrentFragment = titleStr;
        title.setVisibility(View.VISIBLE);

    }


    public void setAppDetails(String titleStr) {
        toolBarVisiables();

        navigationView.getMenu().getItem(7).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = titleStr;

    }

    public void setHelp(String titleStr) {
        toolBarVisiables();

        navigationView.getMenu().getItem(8).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = titleStr;

    }


    public void setUpdate(String titleStr) {
        toolBarVisiables();

        navigationView.getMenu().getItem(9).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = titleStr;

    }

    public void setSearchedByTree(String titleStr) {
        toolBarVisiables();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        back.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(null);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setHomeButtonEnabled(false);
        toggle.setDrawerIndicatorEnabled(false);
        title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = titleStr;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchTree()).commit();
            }
        });
    }

    public void setSplashScreen() {
        getSupportActionBar().hide();
        title.setVisibility(View.GONE);
        //fab.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void setHomePage() {
        toolBarVisiables();
        navigationView.getMenu().getItem(0).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        mCurrentFragment = "Home";
        title.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("servicedsa3w4", "May bestart");
        stopService(new Intent(getApplicationContext(), UpdateData.class));

      /*  NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();*/
    }

    public void setUpdatePage(String string) {
        toolBarVisiables();
        navigationView.getMenu().getItem(13).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;

    }

    public void setTutorials(String string) {
        toolBarVisiables();

        navigationView.getMenu().getItem(9).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;

    }

    public void setDirection(String string) {
        toolBarVisiables();

        navigationView.getMenu().getItem(10).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;

    }

    public void setOtherPage(String string) {
        toolBarVisiables();

        navigationView.getMenu().getItem(11).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;
    }

    public void setFeedback(String string) {
        toolBarVisiables();

        navigationView.getMenu().getItem(12).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;
    }
/*
    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        String address = Weathers.getAddress(MainActivity.this, location.getLatitude(), location.getLongitude());
        Log.d("addre123324ss ", "" + address);
        setAddress(address);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }*/

    private void setAddress(String address) {

        try {
            String response = getWeatherJSON(address);
            JSONObject jsonObject = new JSONObject(response);
            Log.d("jsonObject", "" + jsonObject);
            JSONObject coord = jsonObject.getJSONObject("coord");
            double lon = coord.getDouble("lon");
            double lat = coord.getDouble("lat");

            JSONArray weather = jsonObject.getJSONArray("weather");

            JSONObject jsonObject1 = weather.getJSONObject(0);
            String description = jsonObject1.getString("description");
            String main = jsonObject1.getString("main");

            String base = jsonObject.getString("base");

            JSONObject weatherMain = jsonObject.getJSONObject("main");
            double temp = weatherMain.getDouble("temp");
            int celsiu = (int) (temp - 273.15);


            double pressure = weatherMain.getDouble("pressure");
            double humidity = weatherMain.getDouble("humidity");
            double temp_min = weatherMain.getDouble("temp_min");
            double temp_max = weatherMain.getDouble("temp_max");
            JSONObject wind = jsonObject.getJSONObject("wind");
            double speed = wind.getDouble("speed");
            double deg = wind.getDouble("deg");
            JSONObject clouds = jsonObject.getJSONObject("clouds");
            String all = clouds.getString("all");
            long millis = jsonObject.getLong("dt");
            millis = millis * 1000;
            Date date = new Date(millis);
            DateFormat formatter = new SimpleDateFormat("EEE MMM YYYY hh:mm:ss ");
            String dateFormatted = formatter.format(date);
            String name = jsonObject.getString("name");
            /*JSONObject sys = jsonObject.getJSONObject("sys");
            Log.d("sys",""+sys);
            String type = sys.getString("type");
            String id = sys.getString("id");
            String message = sys.getString("message");
            String country = sys.getString("country");
            String sunrise = sys.getString("sunrise");
            String sunset = sys.getString("sunset");*/

            Log.d("allViewsFormServer ", celsiu + "  " + " dateFormatted " + dateFormatted + " all " + all + " deg " + deg + " speed " + speed + " pressure " + pressure + " humidity " + humidity + " temp_min " + temp_min + " temp_max " + temp_max + " description " + description + " main " + main + "  " + base + " lon " + lon + "  lat " + lat);

            tempTxt.setText("" + celsiu + " ℃ ");

            //mSample.setText("Temperacture " + celsiu + " ℃ ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getWeatherJSON(String address) throws IOException {


//test  ea574594b9d36ab688642d5fbeab847e;
        URL url = null;
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + address + "&APPID=ea574594b9d36ab688642d5fbeab847e");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("asasaqw", "" + url);
        String response = null;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = Weathers.convertStreamToString(in);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        // read the response
        Log.d("asdsad", "" + response);
        return response;

    }
}
