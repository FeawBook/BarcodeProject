package com.example.thanathip.barcodeprojectv201;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by thanathip on 27/7/2558.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder>{
    public List<FeedItem> feedItemList;
    public Context mContext;

    String show;
    String unitInWorkGro = "";
    public MyRecyclerAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_row, null);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder,final int i) {
       final FeedItem feedItem = feedItemList.get(i);
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getWorkDetail()));
        customViewHolder.textBatch.setText(Html.fromHtml(feedItem.getBatch()));
        customViewHolder.countLike.setText(Html.fromHtml(feedItem.getUnitWork()));
        //get employee picture
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(0)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        String getEmpID = feedItem.getPicID();
        QuerySQL getpic = new QuerySQL(getEmpID);
        String tmpPicID = getpic.getPicID();
        String msg;
        String peopleID = "";
        try {
            JSONArray picID = new JSONArray(tmpPicID);

            for (int j = 0; j < picID.length(); j++) {
                JSONObject objPicID = picID.getJSONObject(j);
                msg = "http://192.168.0.99/main/hr/" +objPicID.getString("empImg");
                peopleID = objPicID.getString("peopleID");
                Picasso.with(mContext).load(msg).resize(150,150)
                        .transform(transformation).error(R.drawable.myrect).placeholder(R.drawable.myrect).noFade()
                        .into(customViewHolder.imageView);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final QuerySQL arrName = new QuerySQL(peopleID);
        String tmpName = arrName.getEmpName();
        String name;
        String iName;
        String lName;
        try {
            JSONArray getName = new JSONArray(tmpName);

            for (int j = 0; j < getName.length(); j++) {
                JSONObject objName = getName.getJSONObject(j);
                name = objName.getString("fName");
                iName = objName.getString("iName");
                lName = objName.getString("sName");
                customViewHolder.nameEmployee.setText(iName+name+" "+lName);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        };

        customViewHolder.textView.setOnClickListener(clickListener);
        customViewHolder.imageView.setOnClickListener(clickListener);
        customViewHolder.btnDislike.setOnClickListener(clickListener);
        final String checkStock = "เช็คสต๊อคดินแท่ง";
        final String service = "บริการดิน";
        String preparePepper = "เตรียมดินป่น";

        if(feedItem.getWorkDetail().equals(service)){
            customViewHolder.countDislike.setVisibility(View.INVISIBLE);
            customViewHolder.btnDislike.setVisibility(View.INVISIBLE);


        }else if(feedItem.getWorkDetail().equals(checkStock)){
            customViewHolder.countDislike.setVisibility(View.INVISIBLE);
            customViewHolder.btnDislike.setVisibility(View.INVISIBLE);

        }else if(feedItem.getWorkDetail().equals(preparePepper)){
            customViewHolder.btnTransfer.setVisibility(View.INVISIBLE);

        }

        //***************************get productID**************************************//
        QuerySQL getProductID = new QuerySQL(feedItem.getOrderID());
        String showProductID = "";
        try{
            JSONArray arrProduct = new JSONArray(getProductID.getProductID());
            for(int j=0;j<arrProduct.length();j++){
                JSONObject objProduct = arrProduct.getJSONObject(j);
                showProductID = objProduct.getString("ProductID");
            }
            customViewHolder.textView.setText(showProductID);
        }catch(JSONException e){
            e.printStackTrace();
        }
        //***************************end get productID*********************************//

        //like button on long click
        customViewHolder.btnLike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder detail = new AlertDialog.Builder(mContext);
                detail.setTitle("คำอธิบาย");
                detail.setMessage("จำนวนของดีที่พบเจอ");
                detail.show();

                return false;
            }
        });

        //----------------------------Like Button-----------------------------------//




        customViewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //update database +1 when user click

                if(feedItem.getWorkDetail().equals("การนำเข้าห้องอบ") || feedItem.getWorkDetail().equals("ออกจากห้องอบ")){
                    Intent i = new Intent(mContext,UnitDeliver.class);
                    i.putExtra("EmployeeID", feedItem.getEmployeeID());
                    i.putExtra("OrderID",feedItem.getOrderID());
                    i.putExtra("WorkDetail",feedItem.getWorkDetail());
                    i.putExtra("Batch",feedItem.getBatch());
                    i.putExtra("DepartID",feedItem.getDepartID());
                    i.putExtra("UnitWork",feedItem.getUnitWork());
                    i.putExtra("UnitFollow",feedItem.getUnitFollow());
                    i.putExtra("UnitStock",feedItem.getUnitStock());
                    i.putExtra("DateMFG",feedItem.getDateFail());
                    i.putExtra("ID",feedItem.getId_work());
                    i.putExtra("UnitInWork",feedItem.getUnitInWork());
                    mContext.startActivity(i);
                }else if(feedItem.getWorkDetail().equals("บริการดิน")){
                    Toast.makeText(mContext, "โปรดเลือกปุ่มโอน", Toast.LENGTH_SHORT).show();
                }else{
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    final int position = holder.getPosition();
                    final FeedItem feedItem = feedItemList.get(position);
                    String update_count_work = feedItem.getId_work();
                    final String tmp;
                    tmp = customViewHolder.countLike.getText().toString();
                    int countFromTextView = Integer.parseInt(tmp);
                    countFromTextView++;
                    final String sendToDB = Integer.toString(countFromTextView);
                    customViewHolder.countLike.setText(sendToDB);
                    QuerySQL good = new QuerySQL(sendToDB, update_count_work);
                    String Json = good.getLike();

                    //-----refesh view-----//
                    String msg = "";
                    String msgUnitInWork = "";
                    String msgUnitFail = "";

                    try {
                        JSONArray unitWork = new JSONArray(Json);
                        for (int i = 0; i < unitWork.length(); i++) {
                            JSONObject objUnitWork = unitWork.getJSONObject(i);
                            msg = objUnitWork.getString("UnitWork");
                            msgUnitInWork = objUnitWork.getString("UnitInwork");
                            msgUnitFail = objUnitWork.getString("UnitFail");
                            unitInWorkGro = objUnitWork.getString("UnitInwork");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    unitInWorkGro = msgUnitInWork;
                    customViewHolder.countLike.setText(msg);
                    int sumUnit;
                    int UnitInWorkCheck = Integer.parseInt(msgUnitInWork);
                    sumUnit = Integer.parseInt(msg)+Integer.parseInt(msgUnitFail);
                    final AlertDialog.Builder dDialog = new AlertDialog.Builder(mContext);

                    if(UnitInWorkCheck < sumUnit){
                        dDialog.setTitle("หยุด");
                        dDialog.setMessage("ค่าเกินจำนวน");
                        dDialog.setPositiveButton("Close", null);
                        dDialog.show();
                        int overUnitWork = Integer.parseInt(msg)-1;
                        String strOverUnitWork = Integer.toString(overUnitWork);
                        customViewHolder.countLike.setText(strOverUnitWork);
                       //update data
                       // String overGood = customViewHolder.countLike.getText().toString();
                        QuerySQL updateOver = new QuerySQL(strOverUnitWork,update_count_work);
                        updateOver.normalLike();


                    }
                }
            }

        });
        //----------------------------End Like Button-----------------------------//
        //update data UnitWork when change Activity
        String EmployeeID = feedItem.getId_work();
        QuerySQL getUnitWork = new QuerySQL(EmployeeID);
        String jsonUnitWork = getUnitWork.getUnitWork();
        String showUnitWork;

        try{
            JSONArray arrUnitWork = new JSONArray(jsonUnitWork);
            for(int j=0;j<arrUnitWork.length();j++){
                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
                showUnitWork = objUnitWork.getString("UnitWork");
                customViewHolder.countLike.setText(showUnitWork);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        //dislike long click.....
        customViewHolder.btnDislike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder failDetail = new AlertDialog.Builder(mContext);
                failDetail.setTitle("คำอธิบาย");
                failDetail.setMessage("จำนวนของเสียที่พบเจอ");
                failDetail.show();
                return false;
            }
        });

        //end
        //----------------------------Dislike Button------------------------------//
        customViewHolder.btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //--------getTag---------------//
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                int position = holder.getPosition();
                FeedItem feedItem = feedItemList.get(position);


                String overFail = customViewHolder.countDislike.getText().toString();
                String overLikeCheck = customViewHolder.countLike.getText().toString();
                int overDisLike = Integer.parseInt(overFail)+Integer.parseInt(overLikeCheck);

                //check Over
                String update_count_work = feedItem.getId_work();
                QuerySQL getShowUnitFail = new QuerySQL(update_count_work);
                String show = getShowUnitFail.showUnitFail();
                String showUnitInWorkFail = "";

                try {
                    JSONArray unitFail = new JSONArray(show);
                    for (int j = 0; j < unitFail.length(); j++) {
                        JSONObject objUnitFail = unitFail.getJSONObject(j);
                        showUnitInWorkFail = objUnitFail.getString("UnitInwork");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int checkUnitInWorkFail = Integer.parseInt(showUnitInWorkFail);
                if(checkUnitInWorkFail>overDisLike){
                    if (feedItem.getWorkDetail().equals("การนำเข้าห้องอบ") || feedItem.getWorkDetail().equals("ออกจากห้องอบ")) {
                        Intent UnitFailStove = new Intent(mContext,UnitFailStove
                                .class);
                        UnitFailStove.putExtra("idStove", feedItem.getId_work());
                        UnitFailStove.putExtra("EmployeeID", feedItem.getEmployeeID());
                        UnitFailStove.putExtra("WorkDetail", feedItem.getWorkDetail());
                        UnitFailStove.putExtra("orderID", feedItem.getOrderID());
                        mContext.startActivity(UnitFailStove);
                    } else {
                        Intent dislike_activity = new Intent(mContext,UnitFail.class);
                        dislike_activity.putExtra("sendFail", feedItem.getId_work());
                        dislike_activity.putExtra("Batch", feedItem.getBatch());
                        dislike_activity.putExtra("EmployeeID", feedItem.getEmployeeID());
                        dislike_activity.putExtra("DateFail", feedItem.getDateFail());
                        dislike_activity.putExtra("OrderID", feedItem.getOrderID());
                        dislike_activity.putExtra("March", feedItem.getMarchID());
                        dislike_activity.putExtra("DepartID", feedItem.getDepartID());
                        mContext.startActivity(dislike_activity);
                    }
                }else{
                        final AlertDialog.Builder dDialog = new AlertDialog.Builder(mContext);
                        dDialog.setTitle("หยุด");
                        dDialog.setMessage("ค่าเกินจำนวน");
                        dDialog.setPositiveButton("Close", null);
                        dDialog.show();

//แก้

                }
            }
        });
        //-----------------------------End Dislike--------------------------------//

        String update_count_work = feedItem.getId_work();
        QuerySQL getShowUnitFail = new QuerySQL(update_count_work);
        String show = getShowUnitFail.showUnitFail();
        String showUnitFail = "";

        try {
            JSONArray unitFail = new JSONArray(show);
            for (int j = 0; j < unitFail.length(); j++) {
                JSONObject objUnitFail = unitFail.getJSONObject(j);
                showUnitFail = objUnitFail.getString("UnitFail");
                customViewHolder.countDislike.setText(showUnitFail);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String have = "มี";
        String none = "ไม่มี";
        if(Integer.parseInt(showUnitFail) > 0)
        {
            QuerySQL updateHave = new QuerySQL(have, feedItem.getId_work());
            updateHave.updateHave();

        }else{
            QuerySQL updateHave = new QuerySQL(none, feedItem.getId_work());
            updateHave.updateHave();
        }
        //-----------------------------Transfer Button-----------------------------//

        customViewHolder.btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent transfer = new Intent(mContext, SecondTran.class);
                transfer.putExtra("work_detail", feedItem.getWorkDetail());
                transfer.putExtra("numOfTransfer", feedItem.getUnitWork());
                transfer.putExtra("id", feedItem.getFollow());
                transfer.putExtra("personalID", feedItem.getPicID());
                transfer.putExtra("unitFollow", feedItem.getUnitFollow());
                transfer.putExtra("batch", feedItem.getBatch());
                transfer.putExtra("orderID", feedItem.getOrderID());
                transfer.putExtra("DateMFG", feedItem.getDateFail());
                transfer.putExtra("mID", feedItem.getId_work());
                mContext.startActivity(transfer);
            }


        });
        //-----------------------------End Transfer Button-------------------------//
        customViewHolder.textView.setTag(customViewHolder);
        customViewHolder.imageView.setTag(customViewHolder);
        customViewHolder.btnLike.setTag(customViewHolder);
        customViewHolder.btnDislike.setTag(customViewHolder);
        customViewHolder.countLike.setTag(customViewHolder);
        customViewHolder.countDislike.setTag(customViewHolder);
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onViewRecycled(CustomViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView textBatch;
        public ImageButton btnLike,btnDislike,btnTransfer;
        public TextView countLike,countDislike,nameEmployee;
        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.textBatch = (TextView) view.findViewById(R.id.batch);
            this.btnLike = (ImageButton)view.findViewById(R.id.like);
            this.btnDislike = (ImageButton)view.findViewById(R.id.dislike);
            this.btnTransfer = (ImageButton)view.findViewById(R.id.btnTransfer);
            this.countLike = (TextView)view.findViewById(R.id.textLike);
            this.countDislike = (TextView)view.findViewById(R.id.textDislike);
            this.nameEmployee = (TextView)view.findViewById(R.id.nameEmployee);
        }

    }






}
