package com.example.thanathip.barcodeprojectv201;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    ListView list;
    SwipeRefreshLayout mSwipeRefresh;
    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    WifiManager wifimanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initinstances();


    }

    private void initinstances() {
        //fullscreen
        int currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                //               | View.SYSTEM_UI_FLAG_FULLSCREEN;
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }


        //WIFI connection
        wifimanager=(WifiManager)getSystemService(Context.WIFI_SERVICE);
        AlertDialog.Builder dDialog = new AlertDialog.Builder(MainActivity.this);
        switch (wifimanager.getWifiState()) {

            case WifiManager.WIFI_STATE_DISABLED:


                dDialog.setTitle("ไม่ได้ต่ออินเตอร์เน็ต");
                dDialog.setMessage("โปรดทำการเชื่อมต่อ Internet หรือติดต่อแผนกเทคโนโลยีสารสนเทศ");
                dDialog.setPositiveButton("Close", null);
                dDialog.show();
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                break;
            case WifiManager.WIFI_STATE_DISABLING:

                dDialog.setTitle("ไม่ได้ต่ออินเตอร์เน็ต");
                dDialog.setMessage("โปรดทำการเชื่อมต่อ Internet หรือติดต่อแผนกเทคโนโลยีสารสนเทศ");
                dDialog.setPositiveButton("Close", null);
                dDialog.show();
                break;
            default:
                break;
        }


        // Permission StrictMode
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //Typeface myCustomFont = Typeface.createFromAsset(MainActivity.this.getAssets(), "font/NotoSerifThai-Bold.ttf");
        //**********************Home page****************************//
        HomePageFragment homePic = new HomePageFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.rootLayout, homePic);
        transaction.commit();
        //*********************End Home page*************************//
        list = (ListView)findViewById(R.id.listView1);
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);

        GetDB();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigation = (NavigationView) findViewById(R.id.navigation);
        drawerLayout.setDrawerShadow(R.drawable.abc_btn_borderless_material, GravityCompat.START);


        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetDB();
                mSwipeRefresh.setColorSchemeColors(Color.parseColor("#4183D7"),
                        Color.parseColor("#F62459"),
                        Color.parseColor("#03C9A9"),
                        Color.parseColor("#F4D03F"));
                mSwipeRefresh.setRefreshing(false);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this
                , drawerLayout, R.string.hello_world, R.string.hello_world);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDB();
            }
        });
        //Hamberger
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(titleName);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DIALOG_DOWNLOAD_JSON_PROGRESS :
                ProgressDialog mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("โปรดรอสักครู่...........");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }else if(id == R.id.home){
//            HomePageFragment homePic = new HomePageFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.add(R.id.rootLayout, homePic);
//            transaction.commit();

            ContentFragment contentView = new ContentFragment("xxx");
            FragmentTransaction ContentTransaction = getFragmentManager().beginTransaction().remove(contentView);

            ContentTransaction.commit();

            HomePageFragment homePic = new HomePageFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.rootLayout, homePic);
            transaction.commit();

            getSupportActionBar().setTitle("โปรแกรมการลงบันทึกข้อมูลการผลิตประจำวัน");
            getSupportActionBar().setElevation(20);
            return true;
        }else if(id == R.id.list){
            MenuFragment menu = new MenuFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.rootLayout,menu);
            transaction.commit();
            return true;
        }

        return true;
    }


    public void GetDB() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            }

            @Override
            protected String doInBackground(Void... voids) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://192.168.0.99/main/tablet/forming/menu_drawer.php").build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        return response.body().string();
                    } else {
                        return "Not Success - code : " + response.code();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error - " + e.getMessage();
                }
            }


            @Override
            protected void onPostExecute(String string) {
                try{
                    JSONArray json = new JSONArray(string);
                    final ArrayList<String> titleShow = new ArrayList<>();
                    titleShow.add("หน้าแรก");
                    final ArrayList<HashMap<String, String>> titleList = new ArrayList<>();
                    HashMap<String, String> map;
                    for(int i=0;i<json.length();i++){
                        JSONObject title = json.getJSONObject(i);

                        map = new HashMap<>();
                        map.put("WorkDetail", title.getString("WorkDetail"));
                        titleShow.add(title.getString("WorkDetail"));
                        titleList.add(map);
                    }

                    //SimpleAdapter sAdap;
                    //sAdap = new SimpleAdapter(MainActivity.this,titleList,R.layout.list_row,new String[]{"WorkDetail"},new int[]{R.id.title});
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_row, R.id.title, titleShow);
                    //list.setAdapter(sAdap);
                    list.setAdapter(adapter);
//                QuerySQL sortTitles = new QuerySQL(makeProduct,DickProduct,CheckProduct,ServiceProduct,addToStove,removeFormStove,
//                        flateProduct,prepareProduct);
                    // sortTitles.SortTitle();


                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {


                            final ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
                            if (list.getAdapter().getItem(position).toString().equals("หน้าแรก")) {

                                Handler mHandler = new Handler();
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //waiting Dailog
                                        mProgressDialog.setMessage("โปรดรอสักครู่...........");
                                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();



                                        ContentFragment contentView = new ContentFragment("xxx");
                                        FragmentTransaction ContentTransaction = getFragmentManager().beginTransaction().remove(contentView);

                                        ContentTransaction.commit();

                                        HomePageFragment homePic = new HomePageFragment();
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        transaction.replace(R.id.rootLayout, homePic);
                                        transaction.commit();

                                        getSupportActionBar().setTitle("โปรแกรมการลงบันทึกข้อมูลการผลิตประจำวัน");
                                        getSupportActionBar().setElevation(20);
                                        Handler subHandler = new Handler();
                                        subHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressDialog.cancel();
                                            }
                                        }, 1000);
                                    }
                                },500);
                                drawerLayout.closeDrawer(GravityCompat.START);

                            } else if (list.getAdapter().getItem(position).toString().equals("ขึ้นรูปลูกถ้วย") ||
                                    list.getAdapter().getItem(position).toString().equals("เช็คสต๊อคดินแท่ง") ||
                                    list.getAdapter().getItem(position).toString().equals("บริการดิน")) {

                                // String titleName = titleList.get(position).get("WorkDetail").toString();
                                String titleName = list.getAdapter().getItem(position).toString();
                                QuerySQL sql = new QuerySQL(titleName);
                                //sendToDB(titleName);
                                final String get_data = sql.getDatabase();
                                Handler mHandler = new Handler();
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        //waiting Dailog
                                        mProgressDialog.setMessage("โปรดรอสักครู่...........");
                                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        //remove HomeFragment
                                        HomePageFragment homePic = new HomePageFragment();
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction().remove(homePic);
                                        transaction.commit();


                                        //remove groupFragment
                                        ContentFragmentGroup contentViewGroup = new ContentFragmentGroup("xxx");
                                        FragmentTransaction ContentTransactionClear = getFragmentManager().beginTransaction().remove(contentViewGroup);
                                        ContentTransactionClear.commit();


                                        //********************Content Fragment*********************************//
                                        ContentFragment contentView = new ContentFragment(get_data);
                                        FragmentTransaction ContentTransaction = getFragmentManager().beginTransaction();
                                        ContentTransaction.replace(R.id.rootLayout, contentView);
                                        ContentTransaction.commit();
                                        //*******************End content Fragment*****************************//
//

                                        Handler subHandler = new Handler();
                                        subHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressDialog.cancel();
                                            }
                                        },1000);
                                    }
                                },500);

                                drawerLayout.closeDrawer(GravityCompat.START);
                                titleName = list.getItemAtPosition(position).toString();

                                getSupportActionBar().setTitle(titleName);
                                //drawerLayout.closeDrawer(GravityCompat.START);
                                //////////////////////////////////////////////////////////////


                            } else {
                                String titleName = list.getAdapter().getItem(position).toString();
                                QuerySQL sql = new QuerySQL(titleName);
                                final String getData = sql.getDatabaseGroup();
                                Handler mHandler = new Handler();
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        //waiting Dailog
                                        mProgressDialog.setMessage("โปรดรอสักครู่...........");
                                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        mProgressDialog.setCancelable(true);
                                        mProgressDialog.show();
                                        //remove HomeFragment
                                        HomePageFragment homePic = new HomePageFragment();
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction().remove(homePic);
                                        transaction.commit();

                                        //remove before fragment
                                        ContentFragment contentView = new ContentFragment("xxx");
                                        FragmentTransaction ContentTransactionClear = getFragmentManager().beginTransaction().remove(contentView);
                                        ContentTransactionClear.commit();

                                        //Create new fragment

                                        ContentFragmentGroup contentGroupView = new ContentFragmentGroup(getData);
                                        FragmentTransaction ContentTransaction = getFragmentManager().beginTransaction();
                                        ContentTransaction.replace(R.id.rootLayout, contentGroupView);
                                        ContentTransaction.commit();

                                        Handler subHandler = new Handler();
                                        subHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mProgressDialog.cancel();
                                            }
                                        }, 1000);

                                    }
                                },500);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                titleName = list.getItemAtPosition(position).toString();
                                getSupportActionBar().setTitle(titleName);
                            }
                            getFragmentManager().beginTransaction().remove(new HomePageFragment()).commit();



                        }
                    });

                    list.getAdapter();
                }catch(JSONException e){
                    e.printStackTrace();
                }

                dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
                removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);

            }
        }.execute();




    }


}

















