package com.example.thanathip.barcodeprojectv201;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondTran extends Activity {
    String[] numberOfTransfer = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "←", "ลบ"};
    TextView edtNum, edtTran, edtBeforeTran, hintFollowID, txtName, txtTotal;
    TextView HowMuch;
    int check = 0;
    //edtNum is a number in calculator
    //edtTran is a Values to update database
    Button edtNameFollow;
    Button btnBack, btnOK;
    Button btnPalace;
    ImageView followPic, imgEmp;
    ListView listNameFollow;
    TextView unitInworkTemp, txtUnitInWork;
    String unitWork, IDname, numOfUnitFollow, PersonalID, work_detail, BatchNo, orderID, DateMFG, ID, UnitInWorkFollower;
    String makeProduct = "ขึ้นรูปลูกถ้วย";
    String flateMakeProduct = "อัดขึ้นรูปลูกถ้วย";
    String dickProduct = "เจาะแต่งลูกถ้วย";
    String checkProduct = "เช็คสต๊อคดินแท่ง";
    String serviceProduct = "บริการดิน";
    String importToStove = "การนำเข้าห้องอบ";
    String exportFromStove = "ออกจากห้องอบ";

    String polish = "ขัด,ตกแต่ง";
    String tempWorkDetailToTransfer;
    String peopleIDFollower;

    String UnitInWorkCheckForService;

    int checkPressButton = 0;
    int checkDigit = 0;
    int totalTran2;
    int UnitFollowNum;
    String UnitFollow;
    String UnitWorkJson;
    int UnitWorkNum;
    String tempEmp;
    // int checkNull = 0;
    String updateFollowerValues;
    WifiManager wifimanager;
    GridView grid_transfer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Animation Right to left
    Animation animation, animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        grid_transfer = (GridView) findViewById(R.id.transfer_grid);
        animation = AnimationUtils.loadAnimation(SecondTran.this, R.anim.right_to_left);
        animation2 = AnimationUtils.loadAnimation(SecondTran.this, R.anim.up_from_bottom);

        edtNum = (TextView) findViewById(R.id.edtNumber);
        edtTran = (TextView) findViewById(R.id.unitTran);
        edtBeforeTran = (TextView) findViewById(R.id.beforeTran);
        edtNameFollow = (Button) findViewById(R.id.nameFollow);
        followPic = (ImageView) findViewById(R.id.picNameFollow);
        hintFollowID = (TextView) findViewById(R.id.hideFollowerID);
        listNameFollow = (ListView) findViewById(R.id.list_transfer);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnOK = (Button) findViewById(R.id.btnOk);
        imgEmp = (ImageView) findViewById(R.id.imgEmployee);
        txtName = (TextView) findViewById(R.id.txtNameShow);
        btnPalace = (Button) findViewById(R.id.palace);
        txtTotal = (TextView) findViewById(R.id.numTran);
        unitInworkTemp = (TextView) findViewById(R.id.txtUnitInWorkTemp);
        HowMuch = (TextView) findViewById(R.id.how_much);
        txtUnitInWork = (TextView) findViewById(R.id.txtUnitInWork);

        edtTran.setVisibility(View.INVISIBLE);
        Intent i = getIntent();


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

        //WIFI connection
        wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        switch (wifimanager.getWifiState()) {

            case WifiManager.WIFI_STATE_DISABLED:

                AlertDialog.Builder dDialog = new AlertDialog.Builder(SecondTran.this);
                dDialog.setTitle("ไม่ได้ต่ออินเตอร์เน็ต");
                dDialog.setMessage("โปรดทำการเชื่อมต่อ Internet หรือติดต่อแผนกเทคโนโลยีสารสนเทศ");
                dDialog.setPositiveButton("Close", null);
                dDialog.show();
//                Toast.makeText(MainActivity.this, "disconnect", Toast.LENGTH_LONG).show();
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                //Toast.makeText(MainActivity.this, "connect", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        //ID
        ID = i.getStringExtra("mID");

        unitWork = i.getStringExtra("numOfTransfer");
        //int transfernum = Integer.parseInt(unitWork);

        //ID of Follower
        IDname = i.getStringExtra("id");
        //number of UnitFollow
        numOfUnitFollow = i.getStringExtra("unitFollow");
        //int follow = Integer.parseInt(numOfUnitFollow);
        //EmployeeID
        PersonalID = i.getStringExtra("personalID");
        //total to transfer
        //can't use it and get JSON for this
        //WorkDetail
        work_detail = i.getStringExtra("work_detail");

        //Batch number
        BatchNo = i.getStringExtra("batch");


        //OrderID
        orderID = i.getStringExtra("orderID");

        //DateMFG
        DateMFG = i.getStringExtra("DateMFG");

        //transfer 1 palace shortcut
//        if (work_detail.equals("บริการดิน")) {
//           // btnPalace.setVisibility(View.VISIBLE);
//        } else {
//            btnPalace.setVisibility(View.GONE);
//        }


        btnPalace.setVisibility(View.GONE);
        //*****************UnitFollow***********************************//
        QuerySQL getUnitFollow = new QuerySQL(PersonalID, work_detail);
        String jsonUnitFollow = getUnitFollow.getUnitFollowForTran();
        try {
            JSONArray arrUnitFollow = new JSONArray(jsonUnitFollow);
            for (int j = 0; j < arrUnitFollow.length(); j++) {
                JSONObject objUnitFollow = arrUnitFollow.getJSONObject(j);
                UnitFollow = objUnitFollow.getString("UnitFollow");
                UnitFollowNum = Integer.parseInt(UnitFollow);
                UnitWorkJson = objUnitFollow.getString("UnitWork");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //**************************************************************//
        UnitWorkNum = Integer.parseInt(UnitWorkJson);

        //----------------------check case------------------------------//
        final String totalTransfer = Integer.toString(UnitWorkNum - UnitFollowNum);
        //--------------------------------------------------------------//
        QuerySQL getUnitWork = new QuerySQL(ID);
        String showUnitWork = getUnitWork.getUnitWorkShow();
        String show = "";
        String showFollow = "";
        try {
            JSONArray arrUnitWork = new JSONArray(showUnitWork);
            for (int j = 0; j < arrUnitWork.length(); j++) {
                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
                show = objUnitWork.getString("UnitWork");
                showFollow = objUnitWork.getString("UnitFollow");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int totalFollow = Integer.parseInt(show) - Integer.parseInt(showFollow);
        String ShowFollowData = Integer.toString(totalFollow);
        if (work_detail.equals(serviceProduct) || work_detail.equals("เช็คสต๊อคดินแท่ง")) {
            //เครื่องโอนแบบว่าง
            edtNum.setText("");
        } else {
            //เครื่องโอนแบบแสดงยอด
            edtNum.setText(ShowFollowData);
        }
        edtTran.setText(ShowFollowData);
        edtNum.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        edtBeforeTran.setText("ลูกถ้วยที่โอนไปแล้วจำนวน : " + showFollow + " ลูก");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.transfer_grid_row, R.id.number, numberOfTransfer);
        grid_transfer.setAdapter(adapter);
        //*************************************Cal*********************************************************//
        grid_transfer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select;
                select = grid_transfer.getItemAtPosition(position).toString();
                // int calTransfer = Integer.parseInt(unitWork);
                if (select == "ลบ") {
                    edtNum.setText("");
                } else if (select == "←") {
                    String backSpace = edtNum.getText().toString();
                    if (backSpace.length() > 0) {
                        edtNum.setText(backSpace.substring(0, backSpace.length() - 1));
                    } else {
                        edtNum.setText("");
                    }
                } else {
                    edtNum.append(select);
                    String temp = edtNum.getText().toString();
                    edtTran.setText(temp);
                    //String cal = edtTran.getText().toString();
                    //int calTran = Integer.parseInt(cal);
                    //int totalTran = calTransfer - calTran;
                    //String total = Integer.toString(totalTran);
                    String checkNum = edtNum.getText().toString();
                    int caseForOver = Integer.parseInt(checkNum);
                    int normalCase = Integer.parseInt(totalTransfer);
                    int checkState;
                    if (work_detail.equals(serviceProduct) || work_detail.equals("เช็คสต๊อคดินแท่ง")) {
                        checkState = 1;
                    } else {
                        checkState = 0;
                    }
                    if (caseForOver > normalCase && checkState == 0) {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(SecondTran.this);
                        builder.setMessage("ค่าที่กรอกเกินจำนวน?");
                        builder.setPositiveButton("ทำรายการต่อ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                edtNum.setText(totalTransfer);
                                edtTran.setText("0");
                            }
                        });
                        builder.setNegativeButton("ออกจากการโอน", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }
                edtNum.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
                if (edtNum.getText().toString().equals(null)) {
                    checkDigit = 0;
                } else {
                    checkDigit = 1;
                }
            }
        });
        //*************************************End cal*****************************************************//
        //************************************Method for show list to transfer**************************//
        //ขึ้นรูปลูกถ้วย || อัดขึ้นรูปลูกถ้วย
        if (work_detail.equals(makeProduct) || work_detail.equals(flateMakeProduct)) {

            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(dickProduct, orderID);
            String showNext = getNameNext.getEmployeeID();
            tempWorkDetailToTransfer = dickProduct;
            showList(showNext);

        }
        //เช็คสต๊อคดินแท่ง >> บริการดิน
        else if (work_detail.equals(checkProduct)) {
            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(serviceProduct, BatchNo, orderID);
            String showNext = getNameNext.getServiceProduct();
            showList(showNext);
        }
        //บริการดิน >> ขึ้นรูป
        else if (work_detail.equals(serviceProduct)) {
            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(makeProduct, BatchNo, orderID);//modify
            String showNext = getNameNext.getCheckStockProduct();
            tempWorkDetailToTransfer = "ขึ้นรูปลูกถ้วย";
            showList(showNext);
        }
        //เจาะแต่งลูกถ้วย / ขจัดและตกแต่ง
        else if (work_detail.equals(dickProduct) || work_detail.equals(polish)) {
            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(importToStove);
            String showNext = getNameNext.getEmployeeID();
            showList(showNext);
        }
        //นำเข้าเตา
        else if (work_detail.equals(importToStove)) {
            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(exportFromStove, importToStove, BatchNo);
            String showNext = getNameNext.getStove();
            showList(showNext);
        }
        //ออกจากเตา
        else if (work_detail.equals(exportFromStove)) {
            final ArrayList<String> NameList = new ArrayList<>();
            //String showName = "";
            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(exportFromStove);
            String showNext = getNameNext.getExportFormStove();
            //get Name and picture
            String peopleID;
            try {
                JSONArray arrPeopleID = new JSONArray(showNext);
                for (int j = 0; j < arrPeopleID.length(); j++) {
                    JSONObject objPeopleID = arrPeopleID.getJSONObject(j);
                    peopleID = objPeopleID.getString("peopleID");
                    QuerySQL genIDtoName = new QuerySQL(peopleID);
                    String genName = genIDtoName.getEmpName();
                    String NameNext;
                    //  String iNameNext;
                    String sNameNext;

                    try {
                        JSONArray arrNameGen = new JSONArray(genName);
                        for (int z = 0; z < arrNameGen.length(); z++) {
                            JSONObject objNameGen = arrNameGen.getJSONObject(z);
                            NameNext = objNameGen.getString("fName");
                            //iNameNext = objNameGen.getString("iName");
                            sNameNext = objNameGen.getString("sName");
                            NameList.add(NameNext + " " + sNameNext);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ArrayAdapter<String> adapterName = new ArrayAdapter<>(SecondTran.this, R.layout.fail_detail_dialog
                    , NameList);
            listNameFollow.setAdapter(adapterName);
            listNameFollow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object getOBJName = listNameFollow.getItemAtPosition(position);
                    final String getNameToUse = String.valueOf(getOBJName);
                    QuerySQL compareName = new QuerySQL(getNameToUse);
                    String jsonCompareName = compareName.getPeopleID();
                    String getFullPeopleID = "";
                    try {
                        JSONArray arrFullPeopleID = new JSONArray(jsonCompareName);
                        for (int j = 0; j < arrFullPeopleID.length(); j++) {
                            JSONObject objFullPeopleID = arrFullPeopleID.getJSONObject(j);
                            getFullPeopleID = objFullPeopleID.getString("peopleID");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    QuerySQL getEmployeeID = new QuerySQL(getFullPeopleID);
                    String jsonEmployeeID = getEmployeeID.getEmployeeIDForTransfer();
                    String EmployeeID = "";
                    String picture = "";
                    try {
                        JSONArray arrEmployeeID = new JSONArray(jsonEmployeeID);
                        for (int j = 0; j < arrEmployeeID.length(); j++) {
                            JSONObject objEmployeeID = arrEmployeeID.getJSONObject(j);
                            EmployeeID = objEmployeeID.getString("empCode");
                            picture = "http://192.168.0.99/main/hr/" + objEmployeeID.getString("empImg");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Picasso.with(getApplication()).load(picture).error(R.drawable.noimage).placeholder(R.drawable.noimage)
                            .into(imgEmp);
                    txtName.setText(getNameToUse);
                    // String finalEmployeeID = EmployeeID;
                    QuerySQL getDataForFollower = new QuerySQL(EmployeeID, orderID, work_detail, BatchNo);
                    String showDataForFollower = getDataForFollower.getDataForFollower();
                    try {
                        JSONArray arrUnitInWorkFollower = new JSONArray(showDataForFollower);
                        for (int j = 0; j < arrUnitInWorkFollower.length(); j++) {
                            JSONObject objUnitInWork = arrUnitInWorkFollower.getJSONObject(j);
                            UnitInWorkFollower = objUnitInWork.getString("UnitInwork");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(SecondTran.this, "การโอนสำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });


                    Handler present = new Handler();
                    present.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            imgEmp.setVisibility(View.VISIBLE);
                            txtName.setVisibility(View.VISIBLE);
                            // btnPalace.setVisibility(View.VISIBLE);
                            imgEmp.setAnimation(animation);
                            txtName.setAnimation(animation);
                            //btnPalace.setAnimation(animation);
                        }
                    }, 500);
                    present.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            HowMuch.setVisibility(View.VISIBLE);
                            edtNum.setVisibility(View.VISIBLE);
                            grid_transfer.setVisibility(View.VISIBLE);
                            btnOK.setVisibility(View.VISIBLE);
                            HowMuch.setAnimation(animation2);
                            edtNum.setAnimation(animation2);
                            grid_transfer.setAnimation(animation2);
                            btnOK.setAnimation(animation2);
                        }
                    }, 1500);
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //>>>>>>>>>>>>>>>>>>>>>>>>>>> end ออกจากเตา <<<<<<<<<<<<<<<<<<<<<<<<<<<<//
        } else {
            edtNameFollow.setVisibility(View.GONE);
            followPic.setVisibility(View.GONE);
            QuerySQL getNameNext = new QuerySQL(serviceProduct, BatchNo, orderID);
            String showNext = getNameNext.getServiceProduct();
            showList(showNext);
        }


        //************************************end show list to transfer*********************************//
        edtNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtNum.requestFocus();
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(edtNum.getWindowToken(), 0);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void showList(String strToShow) {
        final ArrayList<String> NameList = new ArrayList<>();
        String showName;
        try {
            JSONArray arrShowDickProductList = new JSONArray(strToShow);
            for (int j = 0; j < arrShowDickProductList.length(); j++) {
                JSONObject objShowDickProductList = arrShowDickProductList.getJSONObject(j);
                showName = objShowDickProductList.getString("EmployeeID");
                String NameID;
                QuerySQL genID = new QuerySQL(showName);
                String genNameID = genID.getPicID();
                try {
                    JSONArray arrGenID = new JSONArray(genNameID);
                    for (int k = 0; k < arrGenID.length(); k++) {
                        JSONObject objGenID = arrGenID.getJSONObject(k);
                        NameID = objGenID.getString("peopleID");

                        QuerySQL genIDtoName = new QuerySQL(NameID);
                        String genName = genIDtoName.getEmpName();
                        String NameNext;
                        //String iNameNext = "";
                        String sNameNext;

                        try {
                            JSONArray arrNameGen = new JSONArray(genName);
                            for (int z = 0; z < arrNameGen.length(); z++) {
                                JSONObject objNameGen = arrNameGen.getJSONObject(z);
                                NameNext = objNameGen.getString("fName");
                                //iNameNext = objNameGen.getString("iName");
                                sNameNext = objNameGen.getString("sName");
                                NameList.add(NameNext + " " + sNameNext);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            final ArrayAdapter<String> adapterName = new ArrayAdapter<>(SecondTran.this, R.layout.fail_detail_dialog
                    , NameList);
            listNameFollow.setAdapter(adapterName);
            listNameFollow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object getOBJName = listNameFollow.getItemAtPosition(position);
                    final String getNameToUse = String.valueOf(getOBJName);
                    checkPressButton = 1;


                    Handler present = new Handler();
                    present.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            imgEmp.setVisibility(View.VISIBLE);
                            txtName.setVisibility(View.VISIBLE);


                            if (work_detail.equals("บริการดิน")) {
                                btnPalace.setVisibility(View.VISIBLE);
                                btnPalace.setAnimation(animation);
                            } else {
                                btnPalace.setVisibility(View.GONE);
                            }

                            imgEmp.setAnimation(animation);
                            txtName.setAnimation(animation);


                        }
                    }, 200);
                    present.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            HowMuch.setVisibility(View.VISIBLE);
                            edtNum.setVisibility(View.VISIBLE);
                            grid_transfer.setVisibility(View.VISIBLE);
                            btnOK.setVisibility(View.VISIBLE);
                            HowMuch.setAnimation(animation2);
                            edtNum.setAnimation(animation2);
                            grid_transfer.setAnimation(animation2);
                            btnOK.setAnimation(animation2);
                        }
                    }, 500);

                    QuerySQL compareName = new QuerySQL(getNameToUse);
                    String jsonCompareName = compareName.getPeopleID();
                    String getFullPeopleID = "";
                    try {
                        JSONArray arrFullPeopleID = new JSONArray(jsonCompareName);
                        for (int j = 0; j < arrFullPeopleID.length(); j++) {
                            JSONObject objFullPeopleID = arrFullPeopleID.getJSONObject(j);
                            getFullPeopleID = objFullPeopleID.getString("peopleID");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    QuerySQL getEmployeeID = new QuerySQL(getFullPeopleID);
                    String jsonEmployeeID = getEmployeeID.getEmployeeIDForTransfer();
                    String EmployeeID = "";
                    String picture = "";
                    try {
                        JSONArray arrEmployeeID = new JSONArray(jsonEmployeeID);
                        for (int j = 0; j < arrEmployeeID.length(); j++) {
                            JSONObject objEmployeeID = arrEmployeeID.getJSONObject(j);
                            EmployeeID = objEmployeeID.getString("empCode");
                            tempEmp = EmployeeID;
                            picture = "http://192.168.0.99/main/hr/" + objEmployeeID.getString("empImg");
                            peopleIDFollower = EmployeeID;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Picasso.with(getApplication()).load(picture).error(R.drawable.noimage).placeholder(R.drawable.noimage)
                            .into(imgEmp);
                    txtName.setText(getNameToUse);
                    final String finalEmployeeID = EmployeeID;
                    GetAllValues getUnitInWork = new GetAllValues(tempWorkDetailToTransfer, finalEmployeeID, orderID, BatchNo);
                    UnitInWorkFollower = getUnitInWork.getUnitInWork();

                    txtTotal.setText(UnitInWorkFollower);
                    unitInworkTemp.setText(finalEmployeeID);

                    //get UnitInWork
                    txtUnitInWork.setText("จำนวนUnitInWorkทั้งหมด : " + UnitInWorkFollower + "ลูก");

                    checkPressButton = 1;
                }
            });


            //---------------balance button-------------------------//
            btnPalace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkPressButton == 1) {
                        QuerySQL transferWithPalace = new QuerySQL(unitInworkTemp.getText().toString(), "ขึ้นรูปลูกถ้วย", orderID);
                        transferWithPalace.transferWithPalace();
                        QuerySQL updateUnitWorkService = new QuerySQL(ID, serviceProduct, "10");
                        updateUnitWorkService.updateUnitWorkService();
                        GetAllValues getUnitInWork = new GetAllValues(tempWorkDetailToTransfer
                                , unitInworkTemp.getText().toString(), orderID, BatchNo);
                        String UnitInWorkFollowerPalace = getUnitInWork.getUnitInWork();
                        txtTotal.setText(UnitInWorkFollowerPalace);
                        Toast.makeText(SecondTran.this, "ทำการโอน 1 พาเหรดเรียบร้อย",
                                Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(SecondTran.this, "โปรดเลือกชื่อก่อน", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            //--------------------Ok Button---------------------//
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (checkDigit == 1) {
                        //check over data
                        //check UnitInwork......
                        GetAllValues getAllUnitInWork = new GetAllValues(work_detail, PersonalID, orderID, BatchNo);
                        UnitInWorkCheckForService = getAllUnitInWork.getUnitInWork();
                        int unitInWork_check = 0;

                        try {
                            unitInWork_check = Integer.parseInt(UnitInWorkCheckForService);
                        } catch (NumberFormatException e) {
                            check = 1;
                            Toast.makeText(SecondTran.this, "กรุณากรอกตัวเลข", Toast.LENGTH_SHORT).show();
                        }
                        int unit_to_transfer = 0;
                        try {
                            unit_to_transfer = Integer.parseInt(edtNum.getText().toString());
                        } catch (NumberFormatException e) {
                            check = 1;
                            Toast.makeText(SecondTran.this, "กรุณากรอกตัวเลข", Toast.LENGTH_SHORT).show();
                        }

                        if (unitInWork_check - unit_to_transfer >= 0) {
                            //can transfer......
                            //check values
                            int sum = 0;
                            String total = edtNum.getText().toString();
                            try {
                                sum = Integer.parseInt(total) + Integer.parseInt(UnitFollow);
                            } catch (NumberFormatException e) {

                            }
                            String UnitFollowSum = Integer.toString(sum);
                            QuerySQL updateUnitFollowAndEmIDFollow = new QuerySQL(UnitFollowSum, unitInworkTemp.getText().toString(), PersonalID, orderID, work_detail, BatchNo);
                            updateUnitFollowAndEmIDFollow.updateUnitFollowAndEmIDFollow();


                            //update follower values
                            try {
                                totalTran2 = Integer.parseInt(UnitInWorkFollower) + Integer.parseInt(total);
                            } catch (NumberFormatException e) {

                            }

                            updateFollowerValues = Integer.toString(totalTran2);
                            //send totalTran2,finalEmployeeID1,OrderID,dickProduct,Batch


                            QuerySQL updateUnitInWork = new QuerySQL(edtNum.getText().toString(), unitInworkTemp.getText().toString(),
                                    tempWorkDetailToTransfer, orderID, BatchNo);
                            updateUnitInWork.updateUnitInWork();


                            if (work_detail.equals(serviceProduct)) {
                                //อัพเดต Unit work
                                String totalNum = txtTotal.getText().toString();//that text is a total of transfer
                                int convertTotalNum = Integer.parseInt(totalNum);
                                String totalUnitWork = edtNum.getText().toString();
                                int convertTotalUnitWork = Integer.parseInt(totalUnitWork);
                                int allTotal = convertTotalNum + convertTotalUnitWork;//calculate
                                String stringAllTotal = Integer.toString(allTotal);
                                txtTotal.setText(stringAllTotal);

                                //break case when UnitWork over than UnitInwork


                                QuerySQL updateUnitWorkService = new QuerySQL(ID, serviceProduct, totalUnitWork);
                                updateUnitWorkService.updateUnitWorkService();
                                edtNum.setText("");
                                Toast.makeText(SecondTran.this, "ทำการโอนเรียบร้อย", Toast.LENGTH_SHORT).show();
                                finish();

                            } else if (work_detail.equals("เช็คสต๊อคดินแท่ง")) {
                                String totalUnitToTransfer = edtNum.getText().toString();
                                //update UnitWork for check stock.........
                                QuerySQL updateAndTransfer = new QuerySQL(ID, "เช็คสต๊อคดินแท่ง", totalUnitToTransfer);
                                updateAndTransfer.updateAndTransferForCheckStock();

                                //update unit in work for Service.........
                                QuerySQL updateUnitInWorkService = new QuerySQL(totalUnitToTransfer, "บริการดิน", orderID, unitInworkTemp.getText().toString());
                                updateUnitInWorkService.updateUnitInWorkForService();
                                Toast.makeText(SecondTran.this, orderID, Toast.LENGTH_LONG).show();
                                finish();

                            } else {

                                if (check == 1) {
                                    Log.e("check activity", Integer.toString(check));
                                    check = 0;
                                    //finish();
                                } else if (check == 0) {
                                    Log.e("check activity2", Integer.toString(check));
                                    Toast.makeText(SecondTran.this, "การโอนสำเร็จ", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                // finish();
                            }

                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(SecondTran.this);
                            alert.setTitle("เกินจำนวน");
                            alert.setMessage("ค่าที่กรอกเกินจำนวน");
                            alert.setPositiveButton("ปิด", null);
                            alert.show();
                            edtNum.setText("");
                        }
                    } else if (checkDigit == 0) {
                        Toast.makeText(SecondTran.this, "โปรดกรอกจำนวนที่ต้องการโอน", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //-------------------back button -------------------//
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //finishTask();
            }
        });

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        //WIFI connection
        wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        AlertDialog.Builder dDialog = new AlertDialog.Builder(SecondTran.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SecondTran Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.thanathip.barcodeprojectv201/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SecondTran Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.thanathip.barcodeprojectv201/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
