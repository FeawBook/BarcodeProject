package com.example.thanathip.barcodeprojectv201;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UnitDeliver2 extends Activity {
    GridView number;
    String[] numberInGrid = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "←", "ลบ"};
    TextView numberShow;
    ListView listDeliver;
    String employeeID, NameID, OrderID, WorkDetail, Batch, DepartID, UnitStock, DateMFG, UnitWork, workID, ProductID;
    String UnitInWork, UnitFail;
    String checkStock = "เช็คสต๊อคดินแท่ง";
    String serviceProduct = "บริการดิน";
    String total;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_deliver);
        number = (GridView) findViewById(R.id.deliver_grid);
        numberShow = (TextView) findViewById(R.id.txtShow);
        listDeliver = (ListView) findViewById(R.id.list_deliver);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent i = getIntent();
        employeeID = i.getStringExtra("EmployeeID");
        OrderID = i.getStringExtra("OrderID");
        WorkDetail = i.getStringExtra("WorkDetail");
        Batch = i.getStringExtra("Batch");
        DepartID = i.getStringExtra("DepartID");
        UnitStock = i.getStringExtra("UnitStock");
        DateMFG = i.getStringExtra("DateMFG");
        workID = i.getStringExtra("ID");
        UnitInWork = i.getStringExtra("UnitInWork");

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


        //GET NAME
        final ArrayList<String> NameList = new ArrayList<>();
        QuerySQL genID = new QuerySQL(employeeID);
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
                        // iNameNext = objNameGen.getString("iName");
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
        final ArrayAdapter<String> adapterName = new ArrayAdapter<>(UnitDeliver2.this, R.layout.deliver_name
                , NameList);
        listDeliver.setAdapter(adapterName);
        //NUMBER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.transfer_grid_row, R.id.numberDeliver, numberInGrid);
        number.setAdapter(adapter);

        number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String select;
                select = number.getItemAtPosition(position).toString();
                if (select == "ลบ") {
                    numberShow.setText("");

                } else if (select == "←") {
                    String backSpace = numberShow.getText().toString();
                    if (backSpace.length() > 0) {
                        numberShow.setText(backSpace.substring(0, backSpace.length() - 1));
                    } else {
                        numberShow.setText("");
                    }
                    /*
                        str = str.substring(0, str.length()-1);
                    * */
                } else {
                    numberShow.append(select);
                    String temp = numberShow.getText().toString();
                    numberShow.setText(temp);
                }
            }
        });
        //Check data before Button Click
        listDeliver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String employeeName = listDeliver.getItemAtPosition(position).toString();
                //employeeID OrderID  WorkDetail

                int unitInWorkNum = Integer.parseInt(UnitInWork);

                ProductID = getOrderID(OrderID);
                UnitWork = getUnitWork(employeeID, DateMFG, WorkDetail, workID, OrderID);
                int unitWorkNum = Integer.parseInt(UnitWork);
                total = numberShow.getText().toString();
                UnitFail = getUnitFail(employeeID, DateMFG, WorkDetail);
                int unitFailNum = Integer.parseInt(UnitFail);
                int sum = Integer.parseInt(total) + Integer.parseInt(UnitWork);
                if (unitFailNum == 0) {
                    UnitFail = "ไม่มี";
                } else {
                    UnitFail = "มี";
                }

                if (ProductID.equals("SP20")) {
                    unitInWorkNum = unitWorkNum + unitFailNum + Integer.parseInt(total);
                    UnitInWork = Integer.toString(unitInWorkNum);
                } else {
                    unitInWorkNum = Integer.parseInt(UnitInWork);
                }
                //add values to database
                //update UnitWork
                //update UnitInwork , FailDetail
                if (WorkDetail.equals("การนำเข้าห้องอบ") || WorkDetail.equals("ออกจากห้องอบ")) {
                    String UnitWorks = getUnitWork(employeeID, "xxx", WorkDetail, workID, OrderID);
                    int sumGood = Integer.parseInt(UnitWorks) + Integer.parseInt(total);
                    QuerySQL updateStove = new QuerySQL(Integer.toString(sumGood), WorkDetail, employeeID, UnitInWork, OrderID, workID);
                    updateStove.updateGoodStove();

                } else {
                    QuerySQL updateUnitWork = new QuerySQL(Integer.toString(sum), serviceProduct, checkStock, OrderID);
                    updateUnitWork.getUpdateUnitWork();
                    Toast.makeText(UnitDeliver2.this, "การโอนสำเร็จ", Toast.LENGTH_SHORT).show();
                    QuerySQL updateUnitInWorkAndUnitFail = new QuerySQL(UnitInWork, UnitFail, serviceProduct, checkStock, OrderID);
                    updateUnitInWorkAndUnitFail.getUpdateUnitInWorkAndUnitFail();
                }
                finish();
            }
        });

    }

    // get UnitWork,UnitFail Values
    public String getUnitWork(String EmployeeID, String DateMFG, String WorkDetail, String workID, String OrderID) {
        QuerySQL genUnitWork = new QuerySQL(EmployeeID, DateMFG, WorkDetail, workID, OrderID);
        String jsonUnitWork = genUnitWork.getUnitWorkRight();
        String UnitWork = "";
        try {
            JSONArray arrUnitWork = new JSONArray(jsonUnitWork);
            for (int j = 0; j < arrUnitWork.length(); j++) {
                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
                UnitWork = objUnitWork.getString("UnitWork");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return UnitWork;
    }

    public String getUnitFail(String EmployeeID, String DateMFG, String WorkDetail) {
        QuerySQL genUnitFail = new QuerySQL(EmployeeID, DateMFG, WorkDetail);
        String jsonUnitWork = genUnitFail.getUnitWorkFull();
        String UnitFail = "";
        try {
            JSONArray arrUnitWork = new JSONArray(jsonUnitWork);
            for (int j = 0; j < arrUnitWork.length(); j++) {
                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
                UnitFail = objUnitWork.getString("UnitFail");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return UnitFail;
    }

    public String getOrderID(String OrderID) {
        QuerySQL getOrderID = new QuerySQL(OrderID);
        String orderCheck = getOrderID.getOrderIDInProductionnew();

        String ProductID = "";
        try {
            JSONArray arrProductID = new JSONArray(orderCheck);
            for (int j = 0; j < arrProductID.length(); j++) {
                JSONObject objProductID = arrProductID.getJSONObject(j);
                ProductID = objProductID.getString("ProductID");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ProductID;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unit_deliver, menu);
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
        AlertDialog.Builder dDialog = new AlertDialog.Builder(UnitDeliver2.this);
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
