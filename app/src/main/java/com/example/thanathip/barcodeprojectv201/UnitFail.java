package com.example.thanathip.barcodeprojectv201;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UnitFail extends Activity {

    //private List<FeedItem> feedsList;
    GridView failContent, failContent2;
    //private MyRecyclerAdapter adapter;
    Button btnAnother, btnOK;
    Context mContext;
    ImageView pictureFail;
    TextView textDetail;
    AutoCompleteTextView autoCompleteTextView;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_fail);


        failContent = (GridView) findViewById(R.id.gridview);
        failContent2 = (GridView) findViewById(R.id.gridview2);
        pictureFail = (ImageView) findViewById(R.id.pic_for_fail);
        textDetail = (TextView) findViewById(R.id.textFailDetail);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnBack = (Button) findViewById(R.id.btnBack);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        // failContent.setAdapter(new FailList(this));


        //fullscreen
        int currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        final String fail, DateFail, OrderID, batch, EmployeeID, MarchID, DepartID,ID;
        Intent i = getIntent();
        fail = i.getStringExtra("sendFail");
        batch = i.getStringExtra("Batch");
        DateFail = i.getStringExtra("DateFail");
        OrderID = i.getStringExtra("OrderID");
        EmployeeID = i.getStringExtra("EmployeeID");
        MarchID = i.getStringExtra("March");
        DepartID = i.getStringExtra("DepartID");


        // Toast.makeText(UnitFail.this,batch+" "+DateFail+" "+OrderID+" "+EmployeeID+" "+MarchID+" "+DepartID+" ", Toast.LENGTH_SHORT).show();
        failContent2.setVisibility(View.GONE);
        QuerySQL getDB = new QuerySQL(fail);
        final String fetchFail = getDB.getFailDetail();
        // Toast.makeText(this,fetchFail,Toast.LENGTH_LONG).show();
        String msg;
        try {
            JSONArray unitFail = new JSONArray(fetchFail);


            final ArrayList<HashMap<String, String>> titleList = new ArrayList<>();

            HashMap<String, String> map;

            for (int j = 0; j < unitFail.length(); j++) {
                JSONObject objFail = unitFail.getJSONObject(j);
                msg = objFail.getString("FailureID");


                final QuerySQL FailureDetail = new QuerySQL(msg);
                String testFetch = FailureDetail.getFailDetailDesc();
                JSONArray thaiFail = new JSONArray(testFetch);
                for (int k = 0; k < thaiFail.length(); k++) {
                    JSONObject objThai = thaiFail.getJSONObject(k);
                    map = new HashMap<>();
                    map.put("FailDetail", objThai.getString("FailDetail"));


                    titleList.add(map);

                }
                final SimpleAdapter sAdap;


                String[] from = {"FailDetail"};
                int[] to = {R.id.text1};


                sAdap = new SimpleAdapter(UnitFail.this, titleList, R.layout.fail_detail_list, from, to);
                failContent.setAdapter(sAdap);


                //on click
                failContent.setOnItemClickListener(new AdapterView.OnItemClickListener()

                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {


                        final String updateFail = titleList.get(position).get("FailDetail");


                        QuerySQL getFailureID = new QuerySQL(updateFail);
                        String failure_id = getFailureID.getFailureID();
                        String msg = "";
                        try {
                            JSONArray arrFailureID = new JSONArray(failure_id);
                            for (int j = 0; j < arrFailureID.length(); j++) {
                                JSONObject objFailureID = arrFailureID.getJSONObject(j);
                                msg = objFailureID.getString("FailureID");
                            }

                            QuerySQL pictureForShow = new QuerySQL(msg);
                            String PictureUnitFail = pictureForShow.getPictureUnitFail();
                            String link_picture;
                            JSONArray arrPictureFail = new JSONArray(PictureUnitFail);
                            for (int k = 0; k < arrPictureFail.length(); k++) {
                                JSONObject objPictureFail = arrPictureFail.getJSONObject(k);
                                link_picture = "http://192.168.0.99/main/systemqc/Detaildamage/" + objPictureFail.getString("CPDPic");
                                //Toast.makeText(UnitFail.this,link_picture, Toast.LENGTH_SHORT).show();
                                Picasso.with(mContext).load(link_picture).error(R.drawable.noimage).placeholder(R.drawable.noimage)
                                        .into(pictureFail);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textDetail.setText("คุณได้ทำการเลือก: " + updateFail);
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //AlertDialog.Builder builder = new AlertDialog.Builder(UnitFail.this);
                                QuerySQL getFailDetail = new QuerySQL(updateFail);
                                String jsonFailDetail = getFailDetail.getFailureID();
                                String FailureID = "";
                                try {
                                    JSONArray arrFailureID = new JSONArray(jsonFailDetail);
                                    for (int k = 0; k < arrFailureID.length(); k++) {
                                        JSONObject objFailureID = arrFailureID.getJSONObject(k);
                                        FailureID = objFailureID.getString("FailureID");
                                    }
                                    //Toast.makeText(UnitFail.this, FailureID, Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                               // Log.e("FailureID is >> ",FailureID);

                                QuerySQL sendFailureID = new QuerySQL(FailureID);
                                sendFailureID.sendFailureID();

                                QuerySQL updateUnitFail = new QuerySQL(FailureID, batch, DateFail,
                                        OrderID, EmployeeID, MarchID, DepartID,fail);
                                updateUnitFail.getUpdateFailDetail();
                                QuerySQL UpdateFail = new QuerySQL("fuck", fail);
                                UpdateFail.getDislike();
                                finish();
                                Toast.makeText(UnitFail.this, "อัพเดตของเสียสำเร็จ", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


//------------------------------------------------อื่นๆ--------------------------------------------------------------//
        btnAnother = (Button) findViewById(R.id.Fail_etc);
        btnAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // autoCompleteTextView.requestFocus();
                btnAnother.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);

//                failContent.setAdapter();

                QuerySQL listInAutoComplete = new QuerySQL("FakeData");
                //listInAutoComplete.getShowDialogFail();
                //final ArrayList<HashMap<String, String>> listDetail = new ArrayList<HashMap<String, String>>();

                final ArrayList<String> listDetailTest = new ArrayList<>();
                String failDetailAuto;
                try {


                    HashMap<String, String> map;

                    JSONArray arrFailDetail = new JSONArray(listInAutoComplete.getShowDialogFail());
                    for (int j = 0; j < arrFailDetail.length(); j++) {
                        map = new HashMap<>();
                        JSONObject objFailAuto = arrFailDetail.getJSONObject(j);
                        failDetailAuto = objFailAuto.getString("FailDetail");
                        map.put("FailDetail", objFailAuto.getString("FailDetail"));
                        //listDetail.add(map);
                        //Object value = map.get("FailDetail");
//                        String testOb = value.toString();
                        listDetailTest.add(failDetailAuto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(UnitFail.this, R.layout.autocomplete_layout
                        , listDetailTest);
                autoCompleteTextView.setAdapter(adapter);
                failContent.setVisibility(View.INVISIBLE);
                failContent2.setVisibility(View.VISIBLE);
                failContent2.setAdapter(adapter);
                failContent2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Object selectListFailDetail = autoCompleteTextView.getAdapter().getItem(position);
                        final String strSelectListFailDetailr = String.valueOf(selectListFailDetail);
                        // Toast.makeText(UnitFail.this,strSelectListFailDetailr, Toast.LENGTH_SHORT).show();
                        textDetail.setText("คุณได้ทำการเลือก: " + strSelectListFailDetailr);
                        QuerySQL reverseToID = new QuerySQL(strSelectListFailDetailr);
                        String resultID = reverseToID.getFailureID();
                        String msg = "";
                        try {
                            JSONArray arrGetFailureID = new JSONArray(resultID);
                            for (int j = 0; j < arrGetFailureID.length(); j++) {
                                JSONObject objGetFailureID = arrGetFailureID.getJSONObject(j);
                                msg = objGetFailureID.getString("FailureID");
                            }
                            QuerySQL pictureForShow = new QuerySQL(msg);
                            String PictureUnitFail = pictureForShow.getPictureUnitFail();
                            String link_picture;
                            JSONArray arrPictureFail = new JSONArray(PictureUnitFail);
                            for (int k = 0; k < arrPictureFail.length(); k++) {
                                JSONObject objPictureFail = arrPictureFail.getJSONObject(k);
                                link_picture = "http://192.168.0.99/main/systemqc/Detaildamage/" + objPictureFail.getString("CPDPic");
                                //Toast.makeText(UnitFail.this,link_picture, Toast.LENGTH_SHORT).show();
                                Picasso.with(mContext).load(link_picture).error(R.drawable.noimage).placeholder(R.drawable.noimage)
                                        .into(pictureFail);
//                                Glide.with(mContext).load(link_picture).error(R.drawable.noimage).placeholder(R.drawable.noimage);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        final String finalMsg = msg;
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                QuerySQL updateFailDetail = new QuerySQL(finalMsg, batch, DateFail,
                                        OrderID, EmployeeID, MarchID, DepartID);
                                updateFailDetail.getUpdateFailDetail();
                                QuerySQL UpdateFail = new QuerySQL("fuck", fail);
                                UpdateFail.getDislike();
                                finish();
                                Toast.makeText(getApplicationContext(), "อัพเดตของเสียสำเร็จ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(UnitFail.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
                autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        final int count = autoCompleteTextView.length();
                        autoCompleteTextView.setDropDownHeight(0);
                        if (count == 0) {
                            //setAdapter
                            btnAnother.setVisibility(View.VISIBLE);
                            final String fail;
                            Intent i = getIntent();
                            fail = i.getStringExtra("sendFail");
                            QuerySQL getDB = new QuerySQL(fail);
                            String fetchFail = getDB.getFailDetail();
                            String msg;

                            try {
                                JSONArray unitFail = new JSONArray(fetchFail);


                                final ArrayList<HashMap<String, String>> titleList = new ArrayList<>();

                                HashMap<String, String> map;

                                for (int j = 0; j < unitFail.length(); j++) {
                                    JSONObject objFail = unitFail.getJSONObject(j);
                                    msg = objFail.getString("FailureID");


                                    QuerySQL FailureDetail = new QuerySQL(msg);
                                    String testFetch = FailureDetail.getFailDetailDesc();
                                    JSONArray thaiFail = new JSONArray(testFetch);
                                    for (int k = 0; k < thaiFail.length(); k++) {
                                        JSONObject objThai = thaiFail.getJSONObject(k);
                                        map = new HashMap<>();
                                        map.put("FailDetail", objThai.getString("FailDetail"));


                                        titleList.add(map);

                                    }
                                    final SimpleAdapter sAdap;


                                    String[] from = {"FailDetail"};
                                    int[] to = {R.id.text1};


                                    sAdap = new SimpleAdapter(UnitFail.this, titleList, R.layout.fail_detail_list, from, to);
                                    failContent.setAdapter(sAdap);

                                    //end set adapter
                                    failContent.setAdapter(sAdap);
                                    failContent2.setVisibility(View.GONE);
                                    failContent.setVisibility(View.VISIBLE);
                                    failContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            final String updateFail = titleList.get(position).get("FailDetail");
                                            //textDetail.setText("คุณได้ทำการเลือก: " + updateFail);
                                            QuerySQL getFailureID = new QuerySQL(updateFail);
                                            String failure_id = getFailureID.getFailureID();
                                            String msg = "";
                                            try {
                                                JSONArray arrFailureID = new JSONArray(failure_id);
                                                for (int j = 0; j < arrFailureID.length(); j++) {
                                                    JSONObject objFailureID = arrFailureID.getJSONObject(j);
                                                    msg = objFailureID.getString("FailureID");
                                                }

                                                QuerySQL pictureForShow = new QuerySQL(msg);
                                                String PictureUnitFail = pictureForShow.getPictureUnitFail();
                                                String link_picture;
                                                JSONArray arrPictureFail = new JSONArray(PictureUnitFail);
                                                for (int k = 0; k < arrPictureFail.length(); k++) {
                                                    JSONObject objPictureFail = arrPictureFail.getJSONObject(k);
                                                    link_picture = "http://192.168.0.99/main/systemqc/Detaildamage/" + objPictureFail.getString("CPDPic");
                                                    //Toast.makeText(UnitFail.this,link_picture, Toast.LENGTH_SHORT).show();
                                                    Picasso.with(mContext).load(link_picture).error(R.drawable.noimage).placeholder(R.drawable.noimage)
                                                            .into(pictureFail);
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            textDetail.setText("คุณได้ทำการเลือก: " + updateFail);
                                            btnOK.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    QuerySQL getFailDetail = new QuerySQL(updateFail);
                                                    String jsonFailDetail = getFailDetail.getFailureID();
                                                    String FailureID = "";
                                                    try {
                                                        JSONArray arrFailureID = new JSONArray(jsonFailDetail);
                                                        for (int k = 0; k < arrFailureID.length(); k++) {
                                                            JSONObject objFailureID = arrFailureID.getJSONObject(k);
                                                            FailureID = objFailureID.getString("FailureID");
                                                        }
                                                        //  Toast.makeText(UnitFail.this, FailureID, Toast.LENGTH_LONG).show();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                    final String finalFailureID = FailureID;
                                                    QuerySQL updateUnitFail = new QuerySQL(finalFailureID, batch, DateFail,
                                                            OrderID, EmployeeID, MarchID, DepartID);
                                                    updateUnitFail.getUpdateFailDetail();
                                                    QuerySQL UpdateFail = new QuerySQL("fuck", fail);
                                                    UpdateFail.getDislike();
                                                    finish();
                                                    Toast.makeText(getApplicationContext(), "อัพเดตของเสียสำเร็จ", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            btnAnother.setVisibility(View.INVISIBLE);
                            failContent2.setAdapter(adapter);
                            failContent.setVisibility(View.GONE);
                            failContent2.setVisibility(View.VISIBLE);

                            failContent2.setAdapter(autoCompleteTextView.getAdapter());
                            failContent2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Object selectListFailDetail = autoCompleteTextView.getAdapter().getItem(position);
                                    final String strSelectListFailDetailr = String.valueOf(selectListFailDetail);
                                    // Toast.makeText(UnitFail.this,strSelectListFailDetailr, Toast.LENGTH_SHORT).show();
                                    textDetail.setText("คุณได้ทำการเลือก: " + strSelectListFailDetailr);
                                    QuerySQL reverseToID = new QuerySQL(strSelectListFailDetailr);
                                    String resultID = reverseToID.getFailureID();
                                    String msg = "";
                                    try {
                                        JSONArray arrGetFailureID = new JSONArray(resultID);
                                        for (int j = 0; j < arrGetFailureID.length(); j++) {
                                            JSONObject objGetFailureID = arrGetFailureID.getJSONObject(j);
                                            msg = objGetFailureID.getString("FailureID");
                                        }
                                        QuerySQL pictureForShow = new QuerySQL(msg);
                                        String PictureUnitFail = pictureForShow.getPictureUnitFail();
                                        String link_picture;
                                        JSONArray arrPictureFail = new JSONArray(PictureUnitFail);
                                        for (int k = 0; k < arrPictureFail.length(); k++) {
                                            JSONObject objPictureFail = arrPictureFail.getJSONObject(k);
                                            link_picture = "http://192.168.0.99/main/systemqc/Detaildamage/" + objPictureFail.getString("CPDPic");
                                            //Toast.makeText(UnitFail.this,link_picture, Toast.LENGTH_SHORT).show();
                                            Picasso.with(mContext).load(link_picture).error(R.drawable.noimage).placeholder(R.drawable.noimage)
                                                    .into(pictureFail);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    final String finalMsg = msg;
                                    btnOK.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            QuerySQL updateFailDetail = new QuerySQL(finalMsg, batch, DateFail,
                                                    OrderID, EmployeeID, MarchID, DepartID);
                                            updateFailDetail.getUpdateFailDetail();
                                            QuerySQL UpdateFail = new QuerySQL("fuck", fail);
                                            UpdateFail.getDislike();
                                            finish();
                                            Toast.makeText(getApplicationContext(), "อัพเดตของเสียสำเร็จ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    //  Toast.makeText(UnitFail.this,msg,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


            }
        });

        failContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // If it loses focus...
                if (!hasFocus) {
                    // Hide soft keyboard.
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(failContent.getWindowToken(), 0);
                    // Make it non-editable again.
                    autoCompleteTextView.setKeyListener(null);
                }
            }
        });

//-----------------------------------------end อื่นๆ --------------------------------------------//

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unit_fail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //WIFI connection
        WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        AlertDialog.Builder dDialog = new AlertDialog.Builder(UnitFail.this);
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
    }
}
