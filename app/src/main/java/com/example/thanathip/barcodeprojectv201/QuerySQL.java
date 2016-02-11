package com.example.thanathip.barcodeprojectv201;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thanathip on 30/7/2558.
 */
public class QuerySQL extends Activity {


    public String getDatabaseGroup() {
        //String url = "http://192.168.0.99/main/tablet/forming/select_list.php";
        GetURL urls = new GetURL();
        String url = urls.SelectList();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mTitleNameGroup", value1));
        return getHttpPost(url, params);
    }

    public String getDatabase() {
        //String url = "http://192.168.0.99/main/tablet/forming/select_list.php";
        GetURL urls = new GetURL();
        String url = urls.SelectList();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mTitleName", value1));
        return getHttpPost(url, params);
    }

    public String getLike() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mUnitWork", value1));
        params.add(new BasicNameValuePair("Work_ID", value2));
        return getHttpPost(url, params);
    }

    public String getUnitWorkGroup() {
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("orderID_group", value1));
        params.add(new BasicNameValuePair("work_detail_group", value2));
        params.add(new BasicNameValuePair("batch_group", value3));
        return getHttpPost(url, params);
    }

    public String normalLike() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("nUnitWork", value1));
        params.add(new BasicNameValuePair("nWork_ID", value2));
        return getHttpPost(url, params);
    }


    public String getDislike() {
        //String url = "http://192.168.0.69/update_database.php";
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mUnitFail", value1));
        params.add(new BasicNameValuePair("Work_ID", value2));
        return getHttpPost(url, params);
    }

    public String getFailDetail() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mFailDetail", value1));
        return getHttpPost(url, params);
    }

    public String getFailDetailDesc() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mFailDetailDesc", value1));
        return getHttpPost(url, params);
    }

    public String getFailureID() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mFailureID", value1));
        return getHttpPost(url, params);
    }

    public String getUpdateFailDetail() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mUpdateFailDetail", value1));
        params.add(new BasicNameValuePair("mBatch", value2));
        params.add(new BasicNameValuePair("mDateFail", value3));
        params.add(new BasicNameValuePair("mOrderID", value4));
        params.add(new BasicNameValuePair("mEmployeeID", value5));
        params.add(new BasicNameValuePair("mMarch", value6));
        params.add(new BasicNameValuePair("mDepartID", value7));
        params.add(new BasicNameValuePair("mFailureID_ID_work", value8));
        return getHttpPost(url, params);
    }

    public String getShowDialogFail() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mShowAutoCompleteFail", value1));
        return getHttpPost(url, params);
    }

    public String getPicID() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mPicID", value1));
        return getHttpPost(url, params);
    }


    public String getEmpName() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mName", value1));
        return getHttpPost(url, params);
    }

    public String getUnitWorkShow() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("Work_ID", value1));
        params.add(new BasicNameValuePair("Work_ID", value1));
        return getHttpPost(url, params);
    }

    public String getUpdateUnitWork() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mTotal", value1));
        params.add(new BasicNameValuePair("mServiceProduct", value2));
        params.add(new BasicNameValuePair("mCheckStock", value3));
        params.add(new BasicNameValuePair("mOrderID", value4));
        return getHttpPost(url, params);
    }

    public String getProductID() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mProduct", value1));
        return getHttpPost(url, params);
    }

    public String getPictureUnitFail() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mPictureFail", value1));
        return getHttpPost(url, params);
    }

    public String getUnitWork() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mUnitWorkShow", value1));
        return getHttpPost(url, params);
    }

    public String showUnitFail() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mShowUnitFail", value1));
        return getHttpPost(url, params);
    }

    public String getEmployeeID() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mEmployeeNext", value1));
        params.add(new BasicNameValuePair("mOrderIDNext", value2));
        return getHttpPost(url, params);
    }

    public String getPeopleID() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mFullName", value1));
        return getHttpPost(url, params);
    }

    public String getEmployeeIDForTransfer() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mEmployeeIDforShow", value1));
        return getHttpPost(url, params);
    }

    public String getStove() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mExportFormStove", value1));
        params.add(new BasicNameValuePair("mWorkDetail", value2));
        params.add(new BasicNameValuePair("mBatch", value3));
        return getHttpPost(url, params);
    }

    public String getExportFormStove() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mGetExportFormStove", value1));
        return getHttpPost(url, params);
    }

    public String getCheckStockProduct() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mMakeProduct", value1));
        params.add(new BasicNameValuePair("mBatch", value2));
        params.add(new BasicNameValuePair("mOrderIDMake", value3));
        return getHttpPost(url, params);
    }

    public String getServiceProduct() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mFlateMakeProduct", value1));
        params.add(new BasicNameValuePair("mBatch", value2));
        params.add(new BasicNameValuePair("mOrderFlate", value3));
        return getHttpPost(url, params);
    }

    //get UnitWork ver.full
    public String getUnitWorkFull() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("mEmployeeID", value1));
        params.add(new BasicNameValuePair("mDateMFG", value2));
        params.add(new BasicNameValuePair("mWorkDetail", value3));
        return getHttpPost(url, params);
    }

    //get OrderID in productionnew
    public String getOrderIDInProductionnew() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("OrderIDForProductionnew", value1));
        return getHttpPost(url, params);
    }

    public String getUpdateUnitInWorkAndUnitFail() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("updateUnitInWork", value1));
        params.add(new BasicNameValuePair("updateUnitFailDetail", value2));
        params.add(new BasicNameValuePair("updateServiceProduct", value3));
        params.add(new BasicNameValuePair("updateCheckStock", value4));
        params.add(new BasicNameValuePair("updateOrderID", value5));
        return getHttpPost(url, params);
    }

    public String updateUnitFollowAndEmIDFollow() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("updateUnitFollowTran", value1));
        params.add(new BasicNameValuePair("updateEmployeeIDFollowTran", value2));
        params.add(new BasicNameValuePair("updateEmployeeIDTran", value3));
        params.add(new BasicNameValuePair("updateOrderIDTran", value4));
        params.add(new BasicNameValuePair("updateWorkDetailTran", value5));
        params.add(new BasicNameValuePair("updateBatchTran", value6));
        return getHttpPost(url, params);
    }

    public String getUnitFollowForTran() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("getUnitFollowTran", value1));
        params.add(new BasicNameValuePair("getWorkDetailTran", value2));
        return getHttpPost(url, params);
    }

    public String getDataForFollower() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("getFollowIDForFollower", value1));//follower ID
        params.add(new BasicNameValuePair("getOrderIDForFollower", value2));//OrderID
        params.add(new BasicNameValuePair("getWorkDetailForFollower", value3));//WorkDetail(เจาะแต่งลูกถ้วย)
        params.add(new BasicNameValuePair("getBatchForFollower", value4));//Batch
        return getHttpPost(url, params);
    }

    public String updateUnitInWork() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("totalToTransfer", value1));
        params.add(new BasicNameValuePair("EmpIDForFollower", value2));
        params.add(new BasicNameValuePair("tempWorkDetailToTransfer", value3));
        params.add(new BasicNameValuePair("orderIDUnitInWork", value4));
        params.add(new BasicNameValuePair("BatchNoUnitInWork", value5));
        return getHttpPost(url, params);
    }

    public String updateHave() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("have", value1));
        params.add(new BasicNameValuePair("idHave", value2));
        return getHttpPost(url, params);
    }

    public String getUnitWorkRight() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("employeeID_show_unitWork", value1));
        params.add(new BasicNameValuePair("another_show_unitWork", value2));
        params.add(new BasicNameValuePair("workDetail_show_unitWork", value3));
        params.add(new BasicNameValuePair("ID_show_unitWork", value4));
        params.add(new BasicNameValuePair("orderID_show_unitWork", value5));
        return getHttpPost(url, params);
    }

    public String updateFailStove() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("stove_fail_id", value1));
        params.add(new BasicNameValuePair("stove_fail_employeeID", value2));
        params.add(new BasicNameValuePair("stove_fail_cause", value3));
        params.add(new BasicNameValuePair("stove_fail_unitFail", value4));
        return getHttpPost(url, params);
    }

    public String updateGoodStove() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("update_stove_unitwork", value1));
        params.add(new BasicNameValuePair("update_stove_workDetail", value2));
        params.add(new BasicNameValuePair("update_stove_employeeID", value3));
        params.add(new BasicNameValuePair("update_stove_unitInWork", value4));
        params.add(new BasicNameValuePair("update_stove_orderID", value5));
        params.add(new BasicNameValuePair("update_stove_workID", value6));
        return getHttpPost(url, params);
    }


    public String getURLproductPic() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("getURLproductPic", value1));
        return getHttpPost(url, params);
    }

    public String updateUnitWorkService() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("ID_Work_Service", value1));
        params.add(new BasicNameValuePair("Service_product", value2));
        params.add(new BasicNameValuePair("total_unitWork_service", value3));
        return getHttpPost(url, params);
    }

    public String updateAndTransferForCheckStock() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("ID_Work_CheckStock", value1));
        params.add(new BasicNameValuePair("check_stock_product", value2));
        params.add(new BasicNameValuePair("check_stock_total", value3));
        return getHttpPost(url, params);
    }

    public String updateUnitInWorkForService() {
        // String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("total_unitInWork_service", value1));
        params.add(new BasicNameValuePair("workDetail_service", value2));
        params.add(new BasicNameValuePair("orderID_service", value3));
        params.add(new BasicNameValuePair("employeeID_service", value4));
        return getHttpPost(url, params);
    }

    public String transferWithPalace() {
        //String url = "http://192.168.0.99/main/tablet/forming/update_database.php";
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("palace_employee_next", value1));
        params.add(new BasicNameValuePair("palace_workDetail_next", value2));
        params.add(new BasicNameValuePair("palace_orderID_next", value3));
        return getHttpPost(url, params);
    }

    public String getUnitFailGroup() {
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("unitFailGroup_orderID", value1));
        params.add(new BasicNameValuePair("unitFailGroup_workDetail", value2));
        params.add(new BasicNameValuePair("unitFailGroup_batch", value3));
        return getHttpPost(url, params);
    }

    public String getUnitInWorkGroup() {
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("unitInWork_orderID_group", value1));
        params.add(new BasicNameValuePair("unitInWork_workDetail_group", value2));
        params.add(new BasicNameValuePair("unitInWork_batch_group", value3));
        return getHttpPost(url, params);
    }

    public String sendFailureID() {
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("failureID_borad", value1));
        return getHttpPost(url, params);
    }

    public String getUnitWorkBoard() {
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("unit_work_board_employeeID", value1));
        params.add(new BasicNameValuePair("unit_work_board_dateMFG", value2));
        return getHttpPost(url, params);
    }

    public String getUnitFailBoard() {
        GetURL urls = new GetURL();
        String url = urls.UpdateLink();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("unit_fail_board_employeeID", value1));
        params.add(new BasicNameValuePair("unit_fail_board_dateMFG", value2));
        return getHttpPost(url, params);
    }

    //constructor
    //don't sort the article

    //c1
    QuerySQL(String value1) {
        this.value1 = value1;
    }

    //c2
    QuerySQL(String value1, String value2) {

        this.value1 = value1;
        this.value2 = value2;
    }

//test push

    //c7
    QuerySQL(String value1, String value2, String value3, String value4, String value5, String value6, String value7) {

        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
        this.value6 = value6;
        this.value7 = value7;

    }

    //c3
    QuerySQL(String value1, String value2, String value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;

    }

    //c4
    QuerySQL(String value1, String value2, String value3, String value4) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }

    //c5
    QuerySQL(String value1, String value2, String value3, String value4, String value5) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }

    //c6
    QuerySQL(String value1, String value2, String value3, String value4, String value5, String value6) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
        this.value6 = value6;
    }
    //c8

    QuerySQL(String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
        this.value6 = value6;
        this.value7 = value7;
        this.value8 = value8;

    }


    String value1;
    String value2;
    String value3;
    String value4;
    String value5;
    String value6;
    String value7;
    String value8;


    public String getHttpPost(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
                Toast.makeText(QuerySQL.this, "Error To fatch SQL", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}


