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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UnitFailStove2 extends Activity {
    GridView number;
    String[] numberInGrid = new String[]{"1","2","3","4","5","6","7","8","9","0","←","ลบ"};
    TextView numberShow;
    ListView listFail;
    String total;
    String EmployeeID,NameID;
    String UnitFail,WorkDetail,cause;
    String workID,orderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_fail_stove);

        Button btnBack;
        number = (GridView)findViewById(R.id.deliver_grid);
        numberShow = (TextView)findViewById(R.id.txtShow);
        listFail = (ListView)findViewById(R.id.list_deliver);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Values
        Intent i = getIntent();
        EmployeeID = i.getStringExtra("EmployeeID");
        WorkDetail = i.getStringExtra("WorkDetail");
        workID = i.getStringExtra("idStove");
        orderID = i.getStringExtra("orderID");

        //fullscreen
        int currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
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


        final ArrayList<String> NameList = new ArrayList<>();
        QuerySQL genID = new QuerySQL(EmployeeID);
        String genNameID = genID.getPicID();
        try{
            JSONArray arrGenID = new JSONArray(genNameID);
            for(int k=0;k<arrGenID.length();k++){
                JSONObject objGenID = arrGenID.getJSONObject(k);
                NameID = objGenID.getString("peopleID");

                QuerySQL genIDtoName = new QuerySQL(NameID);
                String genName = genIDtoName.getEmpName();
                String NameNext;
                //String iNameNext = "";
                String sNameNext;

                try{
                    JSONArray arrNameGen = new JSONArray(genName);
                    for(int z=0;z<arrNameGen.length();z++){
                        JSONObject objNameGen = arrNameGen.getJSONObject(z);
                        NameNext = objNameGen.getString("fName");
                       // iNameNext = objNameGen.getString("iName");
                        sNameNext = objNameGen.getString("sName");
                        NameList.add(NameNext + " " + sNameNext);
                        //Toast.makeText(UnitDeliver.this,NameNext+" "+sNameNext, Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
            //Toast.makeText(SecondActivity.this,NameID,Toast.LENGTH_SHORT).show();
        }catch (JSONException e){
            e.printStackTrace();
        }
        final ArrayAdapter<String> adapterName = new ArrayAdapter<>(UnitFailStove2.this,R.layout.deliver_name
                ,NameList);
        listFail.setAdapter(adapterName);

        //NUMBER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.transfer_grid_row, R.id.numberDeliver,numberInGrid);
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

                } else {
                    numberShow.append(select);
                    String temp = numberShow.getText().toString();
                    numberShow.setText(temp);
                }
            }
        });

        listFail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UnitFail = getUnitFail(EmployeeID, "xx", WorkDetail,workID,orderID);
                total = numberShow.getText().toString();
                int totalSum = Integer.parseInt(UnitFail)+Integer.parseInt(total);
                cause = getFailCause(EmployeeID,"yy",WorkDetail);
                String causes;
                if(UnitFail.equals("0")){
                    causes = "ไม่มี";
                    //Toast.makeText(UnitFailStove.this,causes, Toast.LENGTH_SHORT).show();
                }else{
                    causes = "มี";
                    // Toast.makeText(UnitFailStove.this,causes, Toast.LENGTH_SHORT).show();
                }
                QuerySQL updateFailStove = new QuerySQL(workID,EmployeeID,causes,Integer.toString(totalSum));
                updateFailStove.updateFailStove();
                finish();
                // Toast.makeText(UnitFailStove.this, UnitFail, Toast.LENGTH_SHORT).show();
            }
        });


    }


    public String getUnitFail(String EmployeeID,String DateMFG,String WorkDetail,String workID,String orderID){
        QuerySQL genUnitFail = new QuerySQL(EmployeeID,DateMFG,WorkDetail,workID,orderID);
        String jsonUnitWork = genUnitFail.getUnitWorkRight();
        String UnitFail = "";
        try{
            JSONArray arrUnitWork = new JSONArray(jsonUnitWork);
            for(int j=0;j<arrUnitWork.length();j++){
                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
                UnitFail = objUnitWork.getString("UnitFail");
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return UnitFail;
    }

    public String getFailCause(String EmployeeID,String DateMFG,String WorkDetail){
        QuerySQL genUnitFail = new QuerySQL(EmployeeID,DateMFG,WorkDetail);
        String jsonUnitWork = genUnitFail.getUnitWorkFull();
        String cause = "";
        try{
            JSONArray arrUnitWork = new JSONArray(jsonUnitWork);
            for(int j=0;j<arrUnitWork.length();j++){
                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
                cause = objUnitWork.getString("Cause");
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return cause;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_unit_fail_stove, menu);
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
        WifiManager wifimanager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        AlertDialog.Builder dDialog = new AlertDialog.Builder(UnitFailStove2.this);
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
