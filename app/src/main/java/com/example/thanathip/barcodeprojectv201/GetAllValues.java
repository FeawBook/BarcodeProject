package com.example.thanathip.barcodeprojectv201;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thanathip on 21/12/2558.
 */

public class GetAllValues {

    //Context mContext;

    //Method

    //getUnitInWork
    public String getUnitInWork() {
        QuerySQL sql = new QuerySQL(input2, input3, input1, input4);
        String showData = sql.getDataForFollower();


        try {
            JSONArray arr = new JSONArray(showData);
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);
                result = obj.getString("UnitInwork");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    //get unitWork group
    public String getUnitWorkGroup() {
        QuerySQL sql = new QuerySQL(input1, input2, input3);
        String showData = sql.getUnitWorkGroup();
        try {
            JSONArray arr = new JSONArray(showData);
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);
                result = obj.getString("SUM(UnitWork)");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //get UnitFail group
    public String getUnitFailGroup() {
        QuerySQL sql = new QuerySQL(input1, input2, input3);
        String showData = sql.getUnitFailGroup();

        try {
            JSONArray arr = new JSONArray(showData);
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);
                result = obj.getString("SUM(UnitFail)");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //get unitInwork group
    public String getUnitInWorkGroup() {
        QuerySQL sql = new QuerySQL(input1, input2, input3);
        String showData = sql.getUnitInWorkGroup();
        try {
            JSONArray arr = new JSONArray(showData);
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);
                result = obj.getString("SUM(UnitInwork)");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //get UnitWork Production board
    public String getUnitWorkBoard() {
        QuerySQL sql = new QuerySQL(input1, input2);
        String showData = sql.getUnitWorkBoard();
        try {
            JSONArray arr = new JSONArray(showData);
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);
                result = obj.getString("countUnitWork");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("UnitWork", result);
        return result;
    }

    public String getUnitFailBoard(){
        QuerySQL sql = new QuerySQL(input1, input2);
        String showData = sql.getUnitFailBoard();
        try {
            JSONArray arr = new JSONArray(showData);
            for (int j = 0; j < arr.length(); j++) {
                JSONObject obj = arr.getJSONObject(j);
                result = obj.getString("countUnitFail");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("UnitFail", result);
        return result;
    }


    //constructor
    GetAllValues(String input1, String input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    GetAllValues(String input1, String input2, String input3) {

        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;

    }

    GetAllValues(String input1, String input2, String input3, String input4) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
    }

    String input1, input2, input3, input4;
    String result;

}
