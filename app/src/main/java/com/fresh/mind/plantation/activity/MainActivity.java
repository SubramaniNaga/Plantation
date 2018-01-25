package com.fresh.mind.plantation.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomEditeText;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.customized.CustomTypefaceSpan;
import com.fresh.mind.plantation.fragment.Inside.Directions;
import com.fresh.mind.plantation.fragment.Inside.LocationSelected;
import com.fresh.mind.plantation.fragment.Inside.SearchedByTree;
import com.fresh.mind.plantation.fragment.Inside.SelectedTreeType;

import com.fresh.mind.plantation.fragment.ModelInter_tabs.VIewAgreoForestryImage;
import com.fresh.mind.plantation.fragment.menu_page.AgroForestry;
import com.fresh.mind.plantation.fragment.menu_page.AppDetails;
import com.fresh.mind.plantation.fragment.menu_page.Calculation;
import com.fresh.mind.plantation.fragment.menu_page.FeedBack;
import com.fresh.mind.plantation.fragment.menu_page.Glossary;
import com.fresh.mind.plantation.fragment.menu_page.Help;
import com.fresh.mind.plantation.fragment.menu_page.Others;
import com.fresh.mind.plantation.fragment.menu_page.PlantationCalculation;
import com.fresh.mind.plantation.fragment.menu_page.Schemes;
import com.fresh.mind.plantation.fragment.menu_page.SettingUpdate;
import com.fresh.mind.plantation.fragment.menu_page.SplashScreen;
import com.fresh.mind.plantation.fragment.menu_page.ToolsSettings;
import com.fresh.mind.plantation.fragment.menu_page.Tutorials;
import com.fresh.mind.plantation.service.UpdateData;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.fresh.mind.plantation.sqlite.sorting.SortOrderAZ;
import com.fresh.mind.plantation.sqlite.sorting.SortOrderSelection;
import com.fresh.mind.plantation.tab_pager.HomeTabView;


import io.fabric.sdk.android.Fabric;

import static com.fresh.mind.plantation.Constant.Config.SPLASH_LANGUAGE;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    private Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    public static CustomTextView title, title1;
    private ImageView back;
    private String mCurrentFragment = "Home";
    boolean doubleBackToExitPressedOnce = false;
    public static CustomEditeText mSearchViewEdit;
    public static LinearLayout mSearchViewImg;
    public static LinearLayout mSearchViewLayout;
    public static boolean userIsInteracting = false;
    public static CustomTextView tempTxt, mAddressTxt;
    public static ImageView weatherImage;
    double lat, lng;
    private SortOrder sortOrder;
    public static MenuItem menuItem, menuItem1;
    public static String previouseTxt;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MultiDex.install(getBaseContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fabric.with(this, new Crashlytics());
        }
        LanguageChange languageChange = new LanguageChange(MainActivity.this);
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
        sortOrder = new SortOrder(getApplicationContext());
        //getExternalFilesDir("sdcard/Treepedia");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
        StrictMode.setThreadPolicy(policy);

        init();

        SplashScreen splashScreen = new SplashScreen();
        Bundle bundle = new Bundle();
        bundle.putString("pass", "splash");
        splashScreen.setArguments(bundle);
        pass(splashScreen);

        //checkDrawOverlayPermission(getApplicationContext());

        mSearchViewImg.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (Config.checkInternetConenction(MainActivity.this)) {
                                                      /*String address = mAddressTxt.getText().toString();
                                                      getLocationFromAddress(address);
                                                      Log.d("address", "" + address + "  " + lat + lng);
                                                      Dialog dialog = new Dialog(MainActivity.this);
                                                      dialog.setContentView(R.layout.dialog_location);
                                                      CustomTextView mLocationAddress = (CustomTextView) dialog.findViewById(R.id.mLocationAddress);
                                                      mLocationAddress.setText("" + address);
                                                      GoogleMap googleMap;
                                                      MapView mMapView = (MapView) dialog.findViewById(R.id.mapView);
                                                      MapsInitializer.initialize(MainActivity.this);
                                                      mMapView = (MapView) dialog.findViewById(R.id.mapView);
                                                      mMapView.onCreate(dialog.onSaveInstanceState());
                                                      mMapView.onResume();// needed to get the map to display immediately
                                                      googleMap = mMapView.getMap();
                                                      googleMap.setMyLocationEnabled(true);
                                                      CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(12).build();
                                                      googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                                      dialog.show();*/
                                                  } else {
                                                      Toast.makeText(MainActivity.this, getResources().getString(R.string.netConnectionLocation), Toast.LENGTH_LONG).show();
                                                  }
                                              }
                                          }
        );

    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 547);
            return false;
        } else {
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menuItem = menu.findItem(R.id.sortBy);
        menuItem1 = menu.findItem(R.id.sortByDSC);
        return true;
    }

    public void showBackArrow(String titleStr) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title1.setText("" + titleStr);
        title1.setVisibility(View.VISIBLE);
        title.setVisibility(View.GONE);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.d("chooseByLocation", "" + mCurrentFragment);s
        if (mCurrentFragment.equals(getString(R.string.treeSpecies))) {
            switch (item.getItemId()) {
                case R.id.sortBy:
                    checkSort("A-Z");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    return true;
                case R.id.sortByDSC:
                    checkSort("Z-A");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else if (mCurrentFragment.equals(getString(R.string.SelectedTreeType))) {
            switch (item.getItemId()) {
                case R.id.sortBy:
                    checkSortSelected("A-Z");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).commit();
                    return true;
                case R.id.sortByDSC:
                    checkSortSelected("Z-A");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).commit();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        } /*else if (mCurrentFragment.equals(getString(R.string.searchResult))) {
            switch (item.getItemId()) {
                case R.id.sortBy:
                    checkSort("A-Z");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();
                    return true;
                case R.id.sortByDSC:
                    checkSort("Z-A");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } */ else if (mCurrentFragment.equals(getString(R.string.treeType))) {
            //   Log.d("dsfdf", "sdsdkjh");
            switch (item.getItemId()) {
                case R.id.sortBy:
                    checkSortAZ("A-Z");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    return true;
                case R.id.sortByDSC:
                    checkSortAZ("Z-A");
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkSortAZ(String sort) {
        SortOrderAZ sortOrderSelection = new SortOrderAZ(getApplicationContext());
        int count = sortOrderSelection.getCount();
        //  Log.d("count", "" + count);
        if (count >= 1) {
            sortOrderSelection.update(sort, 1);
        } else {
            if (sort.equals("A-Z")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("byType", "A-Z");
                sortOrderSelection.onInsert(contentValues);
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("byType", "Z-A");
                sortOrderSelection.onInsert(contentValues);
            }
        }
    }

    private void checkSortSelected(String sort) {
        SortOrderSelection sortOrderSelection = new SortOrderSelection(getApplicationContext());
        int count = sortOrderSelection.getCount();
        if (count >= 1) {
            sortOrderSelection.update(sort, 1);
        } else {
            if (sort.equals("A-Z")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("byType", "A-Z");
                sortOrderSelection.onInsert(contentValues);
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("byType", "Z-A");
                sortOrderSelection.onInsert(contentValues);
            }
        }
    }

    public void checkSort(String sort) {
        int count = sortOrder.getCount();
        if (count >= 1) {
            sortOrder.update(sort, 1);
        } else {
            if (sort.equals("A-Z")) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("byType", "A-Z");
                sortOrder.onInsert(contentValues);
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("byType", "Z-A");
                sortOrder.onInsert(contentValues);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission(Context context) {
        // check if we already  have permission to draw over other apps
        if (Settings.canDrawOverlays(context)) {
            // code
        } else {
            // if not construct intent to request permission
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            // request permission via start activity for result
            startActivityForResult(intent, 02);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 02) {
            // if so check once again if we have permission
            if (Settings.canDrawOverlays(this)) {
                SplashScreen splashScreen = new SplashScreen();
                Bundle bundle = new Bundle();
                bundle.putString("pass", "splash");
                splashScreen.setArguments(bundle);
                pass(splashScreen);
            } else {
                //  Log.d("sdskjdhsh", " 1222dsdsd");
            }
        }
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
        mSearchViewImg = (LinearLayout) findViewById(R.id.searchView);
        mSearchViewEdit = (CustomEditeText) findViewById(R.id.mSearchViewEdit);
        mSearchViewLayout = (LinearLayout) findViewById(R.id.mSearchViewLayout);
        tempTxt = (CustomTextView) findViewById(R.id.tempDetails);
        weatherImage = (ImageView) findViewById(R.id.weatherImage);
        title1 = (CustomTextView) findViewById(R.id.title1);
        mAddressTxt = (CustomTextView) findViewById(R.id.mAddressTxt);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("drawerClosed", "yes");
                title.setText(previouseTxt);
                ///invalidateOptionsMenu();

            }

            @SuppressLint("NewApi")
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.d("drawerClosed", "no");
                title.setText(getString(R.string.app_name));

                //  invalidateOptionsMenu();
            }
        };
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
       /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.up_from_bottom);
        navigationView.setAnimation(animation);*/

        /*Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_right_1);
        navigationView.startAnimation(animation);*/
        title = (CustomTextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);

        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // Log.d("currentapiVersion", "" + currentapiVersion + "   " + android.os.Build.VERSION_CODES.LOLLIPOP);
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
        Fragment ff = getSupportFragmentManager().findFragmentById(R.id.container_body);
        Log.d("mCurrentFragment", "" + mCurrentFragment + "  " + ff);

       /*
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (ff instanceof HomeTabView || ff instanceof SplashScreen) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finish();
                return;
            }
            doubleBackToExitPressedOnce = true;
            SPLASH_LANGUAGE = -1;
            Toast.makeText(this, getResources().getString(R.string.pressAgain), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else if (ff instanceof TreeSpecies || ff instanceof Others || ff instanceof TreeType || ff instanceof SearchTree || ff instanceof Directions || ff instanceof FeedBack || ff instanceof PlantationCalculation ||
                ff instanceof SettingUpdate || ff instanceof Schemes || ff instanceof Tutorials || ff instanceof AgroForestry || ff instanceof Models || ff instanceof Intercropstab) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
        } else {
            super.onBackPressed();
        }*/

        Log.d("mCurrentFragment", "" + mCurrentFragment);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mCurrentFragment.equals(getResources().getString(R.string.ContactUs)) || mCurrentFragment.equals(getString(R.string.help)) || mCurrentFragment.equals(getString(R.string.locator)) ||
                mCurrentFragment.equals(getResources().getString(R.string.appDetails)) || mCurrentFragment.equals(getString(R.string.plantationCalculation)) || mCurrentFragment.equals(getString(R.string.agroForestry))
                || mCurrentFragment.equals(getString(R.string.tutorials)) || mCurrentFragment.equals("Tree Type11") || mCurrentFragment.equals(getString(R.string.Schemes))
                || mCurrentFragment.equals(getString(R.string.Feedback)) || mCurrentFragment.equals("Tree Species1") || mCurrentFragment.equals(getResources().getString(R.string.UpdateData)) ||
                mCurrentFragment.equals(getResources().getString(R.string.treeType) + "1") || mCurrentFragment.equals(getResources().getString(R.string.SelectedTreeType))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();

        } else if (mCurrentFragment.equals(getResources().getString(R.string.treeSpecies) + "1") || mCurrentFragment.equals("Location Selection")
                || mCurrentFragment.equals(getResources().getString(R.string.searchResult)) || mCurrentFragment.equals(getResources().getString(R.string.glossary))) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();

        } else if (mCurrentFragment.equals(getResources().getString(R.string.treeSpecies) + "11")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();

        } else if (mCurrentFragment.equals("Choose By Location11") || mCurrentFragment.equals("Location Details")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).commit();

        } else if (mCurrentFragment.equals("Tree Type111")) {
            pass(new SelectedTreeType());

        } else if (mCurrentFragment.equals("Tree Species11")) {
            pass(new SearchedByTree());

        } else if (mCurrentFragment.equals("View Details")) {
            pass(new AgroForestry());
        } else if (mCurrentFragment.equals("Calculation")) {
            pass(new PlantationCalculation());
        } else {
            if (mCurrentFragment.equals(getResources().getString(R.string.treeSpecies)) || mCurrentFragment.equals(getResources().getString(R.string.treeType)) ||
                    mCurrentFragment.equals(getResources().getString(R.string.search)) || mCurrentFragment.equals(getResources().getString(R.string.chooseByLocation))) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    finish();
                    return;
                }
                doubleBackToExitPressedOnce = true;
                SPLASH_LANGUAGE = -1;
                Toast.makeText(this, getResources().getString(R.string.pressAgain), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment ff = getSupportFragmentManager().findFragmentById(R.id.container_body);
        Config.SELECTED_DIS = 0;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Config.LISTVIEW_SMOOTH_VIEW_POSITION = 0;
        Config.SELECTED_TAB_VIEW_POSITION = 0;
        if (id == R.id.nav_home) {
            if (ff instanceof HomeTabView) {
            } else {
                pass(new HomeTabView());
            }
        } else if (id == R.id.nav_choose_by_tree) {
            if (ff instanceof AgroForestry) {
            } else {
                pass(new AgroForestry());
            }
        } else if (id == R.id.nav_plantation_calculation) {
            if (ff instanceof PlantationCalculation) {
            } else {
                pass(new PlantationCalculation());
            }
        } else if (id == R.id.nav_search) {
            if (ff instanceof ToolsSettings) {
            } else {
                pass(new ToolsSettings());
            }
        } else if (id == R.id.nav_glossery) {
            if (ff instanceof ToolsSettings) {
            } else {
                Fragment fragment = new Glossary();
                Bundle bundle = new Bundle();
                bundle.putString("itemPoistion", "");
                bundle.putString("itemName", "");
                fragment.setArguments(bundle);
                pass(fragment);
            }
        } else if (id == R.id.nav_tools) {
            if (ff instanceof ToolsSettings) {
            } else {
                pass(new ToolsSettings());
            }
        } else if (id == R.id.nav_contact) {
            if (ff instanceof Directions) {
            } else {
                pass(new Directions());
            }
        } else if (id == R.id.nav_app_details) {
            if (ff instanceof AppDetails) {
            } else {
                pass(new AppDetails());
            }
        } else if (id == R.id.nav_help) {
            if (ff instanceof Help) {
            } else {
                pass(new Help());
            }
        } else if (id == R.id.nav_others) {
            if (ff instanceof SettingUpdate) {
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("task", "check");
                Fragment fragment = new SettingUpdate();
                fragment.setArguments(bundle);
                pass(fragment);
            }
        } else if (id == R.id.nav_tutorial) {
            if (ff instanceof Tutorials) {
            } else {
                pass(new Tutorials());
            }
        } else if (id == R.id.nav_direction) {
            if (ff instanceof Directions) {
            } else {
                pass(new Directions());
            }
        } else if (id == R.id.nav_Others) {
            if (ff instanceof Schemes) {
            } else {
                pass(new Schemes());
            }
        } else if (id == R.id.nav_feedback) {
            if (ff instanceof FeedBack) {
            } else {
                pass(new FeedBack());
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void pass(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }


    public void species(String titleStr) {
        previouseTxt = titleStr;
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

                //getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                onBackPressed();

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

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.d("ArrayIndextOutOfBound", String.valueOf(e));
            return dispatchTouchEvent(ev);
        }
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


    public void viewTreeDetails(String titleStr, final String fargment) {
        previouseTxt = titleStr;
        toolBarVisiables();
        mSearchViewImg.setVisibility(View.VISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        back.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle(null);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);

        title.setVisibility(View.GONE);
        title1.setVisibility(View.VISIBLE);
        title1.setText("" + titleStr);
        mCurrentFragment = fargment;
        title1.setSelected(true);
        title1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //Log.d("dsadlk;", "" + titleStr + "  " + fargment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
/*
                Log.d("fargment", "" + fargment);
                if (fargment.equals("Tree Species1")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                } else if (fargment.equals("Tree Type111")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SelectedTreeType()).commit();
                } else if (fargment.equals("Choose By Location11")) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).commit();
                } else if (fargment.equals(getResources().getString(R.string.SelectedTreeType))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                } else if (fargment.equals("Location Selection") || fargment.equals(getResources().getString(R.string.searchResult))) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();
                }*/

            }
        });
    }

    public void setSearch(String titleStr) {
        previouseTxt = titleStr;
        toolBarVisiables();
        mSearchViewImg.setVisibility(View.VISIBLE);
        navigationView.getMenu().getItem(0).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title1.setVisibility(View.GONE);
        // title.setText("" + titleStr);
        mCurrentFragment = titleStr;
        title.setVisibility(View.VISIBLE);

    }

    public void setGlossary(String titleStr) {
        previouseTxt = titleStr;
        mCurrentFragment = titleStr;
        toolBarVisiables();
        navigationView.getMenu().getItem(10).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + titleStr);
        title.setVisibility(View.VISIBLE);
        title1.setVisibility(View.GONE);
    }

    public void setTools(String titleStr) {
        previouseTxt = titleStr;
        toolBarVisiables();
        navigationView.getMenu().getItem(13).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        title.setText(titleStr);
        // title.setText("" + titleStr);
        //title.setText("Tree Plantation");
        mCurrentFragment = titleStr;
        title1.setVisibility(View.GONE);

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
        title1.setVisibility(View.GONE);
    }


    public void setAppDetails(String titleStr) {
        toolBarVisiables();
        previouseTxt = titleStr;
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
        title1.setVisibility(View.GONE);

    }

    public void setHelp(String titleStr) {
        toolBarVisiables();
        previouseTxt = titleStr;
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
        title1.setVisibility(View.GONE);

    }


    public void setUpdate(String titleStr) {
        toolBarVisiables();
        previouseTxt = titleStr;
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
        title1.setVisibility(View.GONE);
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
        title1.setVisibility(View.GONE);
    }


    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.fresh.mind.plantation.service".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!isServiceRunning()) {
            stopService(new Intent(getApplicationContext(), UpdateData.class));
        }
      /*  NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();*/
    }

    public void setUpdatePage(String string) {
        toolBarVisiables();
        previouseTxt = string;
        navigationView.getMenu().getItem(13).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        title1.setVisibility(View.GONE);
        mCurrentFragment = string;

    }

    public void setTutorials(String string) {
        toolBarVisiables();
        previouseTxt = string;
        navigationView.getMenu().getItem(3).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        title1.setVisibility(View.GONE);
        mCurrentFragment = string;

    }

    public void setDirection(String string) {
        toolBarVisiables();
        previouseTxt = string;
        navigationView.getMenu().getItem(11).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        title1.setVisibility(View.GONE);
        mCurrentFragment = string;

    }

    public void setOtherPage(String string) {
        toolBarVisiables();
        previouseTxt = string;
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
        title1.setVisibility(View.GONE);
    }

    public void setFeedback(String string) {
        toolBarVisiables();
        previouseTxt = string;
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
        title1.setVisibility(View.GONE);
    }


    public void setAgro(String string) {
        toolBarVisiables();
        previouseTxt = string;
        navigationView.getMenu().getItem(1).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;
        title1.setVisibility(View.GONE);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);

    }


    public void setPlantationCalc(String string) {
        toolBarVisiables();
        previouseTxt = string;
        navigationView.getMenu().getItem(2).setChecked(true);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().show();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        back.setVisibility(View.GONE);
        title.setText("" + string);
        title.setVisibility(View.VISIBLE);
        mCurrentFragment = string;
        title1.setVisibility(View.GONE);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);

    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void sortOrderFragment(String string) {
        mCurrentFragment = string;
    }
}
