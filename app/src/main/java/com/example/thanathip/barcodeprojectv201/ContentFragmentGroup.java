package com.example.thanathip.barcodeprojectv201;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by thanathip on 15/10/2558.
 */
public class ContentFragmentGroup extends Fragment {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapterGroup adapter;
    SwipeRefreshLayout mSwipeRefresh;
    int MYACTIVITY_REQUEST_CODE = 101;
    TextView titleProduct,titleBatch,titleGood,titleBad,titleTransfer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.content_fragment,container,false);

        Fragment fragment = getFragmentManager().findFragmentById(R.layout.home_page_fragment);
        if(fragment != null){
            getFragmentManager().beginTransaction().remove(fragment).commit();
            Toast.makeText(getActivity(), "show clear fragment", Toast.LENGTH_SHORT).show();
        }

        mSwipeRefresh = (SwipeRefreshLayout)content.findViewById(R.id.mSwipeFragment);
        mRecyclerView = (RecyclerView)content.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        titleProduct = (TextView)content.findViewById(R.id.product_show);
        titleBatch = (TextView)content.findViewById(R.id.batch_show);
        titleGood = (TextView)content.findViewById(R.id.good_show);
        titleBad = (TextView)content.findViewById(R.id.bad_show);
        titleTransfer = (TextView)content.findViewById(R.id.transfer);
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(),"font/NotoSerifThai-Bold.ttf");
        Typeface EngFont = Typeface.createFromAsset(getActivity().getAssets(),"font/NotoSans-Bold.ttf");
        titleProduct.setTypeface(myCustomFont);
        titleBatch.setTypeface(EngFont);
        titleGood.setTypeface(myCustomFont);
        titleBad.setTypeface(myCustomFont);
        titleTransfer.setTypeface(myCustomFont);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        mRecyclerView.setScrollContainer(true);
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        mRecyclerView.getLayoutManager().onScrollStateChanged(50);

        parseJSON(getdata);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                parseJSON(getdata);
                mSwipeRefresh.setColorSchemeColors(Color.parseColor("#4183D7"),
                        Color.parseColor("#F62459"),
                        Color.parseColor("#03C9A9"),
                        Color.parseColor("#F4D03F"));
                mSwipeRefresh.setRefreshing(false);
            }
        });
        return content;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == MYACTIVITY_REQUEST_CODE) && (resultCode == Activity.RESULT_OK))
            adapter.notifyDataSetChanged();
    }


    private void parseJSON(String result){
        try{
            JSONArray posts = new JSONArray(result);
            List<FeedItem> feedsList = new ArrayList<>();
            for(int i=0;i<posts.length();i++){
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setId_work(post.optString("ID"));
                item.setWorkDetail(post.optString("WorkDetail"));
                // item.setThumbnail(post.optString("test_pic"));
                item.setBatch(post.optString("Batch"));
                item.setUnitWork(post.optString("UnitWork"));
                item.setUnitFail(post.optString("UnitFail"));
                item.setCause(post.optString("Cause"));
                //pic employee
                item.setPicID(post.optString("EmployeeID"));
                //end pic employee
                item.setFollow(post.optString("EmIDfollow"));
                item.setOrderID(post.optString("OrderID"));
                item.setUnitFollow(post.optString("UnitFollow"));
                item.setDateFail(post.optString("DateMFG"));
                item.setEmployeeID(post.optString("EmployeeID"));
                item.setMarchID(post.optString("MachID"));
                item.setDepartID(post.optString("DepartID"));
                item.setUnitStock(post.optString("UnitStock"));
                item.setUnitInWork(post.optString("UnitInwork"));
                feedsList.add(item);
                adapter = new MyRecyclerAdapterGroup(getActivity(), feedsList);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setItemViewCacheSize(200);
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
                mRecyclerView.startAnimation(animation);
                mRecyclerView.smoothScrollToPosition(0);




                //Toast.makeText(getActivity(),feedsList.get(i).getOrderID().toString(), Toast.LENGTH_SHORT).show();


            }
        }catch(JSONException e){
            e.printStackTrace();
        }


    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getdata = savedInstanceState.getString("getData");
        // bundle = savedInstanceState.getBundle("Bundle");
        Log.i("Check", "onActivityCreated");
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("Check", "onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Check", "onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("Check", "onDestroy");
        android.os.Debug.stopMethodTracing();

    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Check", "onDestroyView");
    }

    public void onDetach() {
        super.onDetach();
        Log.i("Check", "onDetach");
    }

    public void onPause() {
        super.onPause();
        Log.i("Check", "onPause");
    }

    public void onResume() {
        super.onResume();
        Log.i("Check", "onResume");
        //WIFI connection
        WifiManager wifimanager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        AlertDialog.Builder dDialog = new AlertDialog.Builder(getActivity());
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

    public void onStart() {
        super.onStart();
        //mRecyclerView.setAdapter(adapter);
        //adapter.notifyItemChanged(0);
        adapter.notifyDataSetChanged();
        adapter.notifyItemInserted(0);
        //Toast.makeText(getActivity(), "On start", Toast.LENGTH_SHORT).show();
        Log.i("Check", "onStart");
    }

    public void onStop() {
        super.onStop();


        Log.i("Check", "onStop");
    }
    ContentFragmentGroup(String getdata){
        this.getdata = getdata;
    }
    String getdata;

}
