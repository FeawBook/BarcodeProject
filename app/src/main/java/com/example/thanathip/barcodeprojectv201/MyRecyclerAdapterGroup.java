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
public class MyRecyclerAdapterGroup extends RecyclerView.Adapter<MyRecyclerAdapterGroup.CustomViewHolder> {
    public List<FeedItem> feedItemList;
    public Context mContext;
    String show;

    public MyRecyclerAdapterGroup(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.content_row_group, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {
        final FeedItem feedItem = feedItemList.get(i);
        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getWorkDetail()));
        customViewHolder.textBatch.setText(Html.fromHtml(feedItem.getBatch()));
        customViewHolder.countLike.setText(Html.fromHtml(feedItem.getUnitWork()));
        //customViewHolder.countLike.setText("x");

        //get employee picture
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(0)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        };
        customViewHolder.textView.setOnClickListener(clickListener);
        customViewHolder.btnDislike.setOnClickListener(clickListener);
        final String checkStock = "เช็คสต๊อคดินแท่ง";
        final String service = "บริการดิน";
        String preparePepper = "เตรียมดินป่น";
        if (feedItem.getWorkDetail().equals(service)) {
            customViewHolder.countDislike.setVisibility(View.INVISIBLE);
            customViewHolder.btnDislike.setVisibility(View.INVISIBLE);
        } else if (feedItem.getWorkDetail().equals(checkStock)) {
            customViewHolder.countDislike.setVisibility(View.INVISIBLE);
            customViewHolder.btnDislike.setVisibility(View.INVISIBLE);
        } else if (feedItem.getWorkDetail().equals(preparePepper)) {
            customViewHolder.btnTransfer.setVisibility(View.INVISIBLE);

        }

        //***************************get productID**************************************//
        QuerySQL getProductID = new QuerySQL(feedItem.getOrderID());
        String showProductID = "";
        try {
            JSONArray arrProduct = new JSONArray(getProductID.getProductID());
            for (int j = 0; j < arrProduct.length(); j++) {
                JSONObject objProduct = arrProduct.getJSONObject(j);
                showProductID = objProduct.getString("ProductID");
                QuerySQL getPicProduct = new QuerySQL(showProductID);
                String urlProduct;
                String nameProduct;
                try {
                    JSONArray arrURLproduct = new JSONArray(getPicProduct.getURLproductPic());
                    for (int k = 0; k < arrURLproduct.length(); k++) {
                        JSONObject objURLproduct = arrURLproduct.getJSONObject(k);
                        urlProduct = objURLproduct.getString("product_pic");
                        nameProduct = objURLproduct.getString("product_name");
                        Picasso.with(mContext).load(urlProduct).resize(200, 200)
                                .transform(transformation).error(R.drawable.myrect).placeholder(R.drawable.myrect)
                                .into(customViewHolder.imageView);
                        customViewHolder.nameEmployee.setText(nameProduct);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            customViewHolder.textView.setText(showProductID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //***************************end get productID*********************************//

        // like button on long click
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
                if (feedItem.getWorkDetail().equals("การนำเข้าห้องอบ") || feedItem.getWorkDetail().equals("ออกจากห้องอบ")) {
                    Intent i = new Intent(mContext, UnitDeliver2.class);
                    i.putExtra("EmployeeID", feedItem.getEmployeeID());
                    i.putExtra("OrderID", feedItem.getOrderID());
                    i.putExtra("WorkDetail", feedItem.getWorkDetail());
                    i.putExtra("Batch", feedItem.getBatch());
                    i.putExtra("DepartID", feedItem.getDepartID());
                    i.putExtra("UnitWork", feedItem.getUnitWork());
                    i.putExtra("UnitFollow", feedItem.getUnitFollow());
                    i.putExtra("UnitStock", feedItem.getUnitStock());
                    i.putExtra("DateMFG", feedItem.getDateFail());
                    i.putExtra("ID", feedItem.getId_work());
                    i.putExtra("UnitInWork", feedItem.getUnitInWork());
                    mContext.startActivity(i);
                } else {
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
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    GetAllValues getUnitFailGroup = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
                    String showUnitFailGroup = getUnitFailGroup.getUnitFailGroup();

                    GetAllValues getUnitWorkGroup = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
                    String showUnitWorkGroup = getUnitWorkGroup.getUnitWorkGroup();
                    customViewHolder.totalUnitWork.setText(showUnitWorkGroup);


                    GetAllValues unitInWorkGroupCheck = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
                    //replace msgUnitInWork
                    String unitInWorkGroup = unitInWorkGroupCheck.getUnitInWorkGroup();
                    customViewHolder.hideUnitInWorkGroup.setText(unitInWorkGroup);
                    customViewHolder.countLike.setText(msg);
                    int sumUnit;
                    // int UnitInWorkCheck = Integer.parseInt(msgUnitInWork);
                    int UnitInWorkCheck = Integer.parseInt(unitInWorkGroup);
                    // sumUnit = Integer.parseInt(msg)+Integer.parseInt(msgUnitFail);
                    sumUnit = Integer.parseInt(showUnitWorkGroup) + Integer.parseInt(showUnitFailGroup);
                    final AlertDialog.Builder dDialog = new AlertDialog.Builder(mContext);
                    if (UnitInWorkCheck < sumUnit) {
                        dDialog.setTitle("หยุด");
                        dDialog.setMessage("ค่าเกินจำนวน");
                        dDialog.setPositiveButton("Close", null);
                        dDialog.show();
                        int overUnitWork = Integer.parseInt(msg) - 1;
                        //int overUnitWork = Integer.parseInt(unitInWorkGroup)-1;
                        String strOverUnitWork = Integer.toString(overUnitWork);
                        //customViewHolder.totalUnitWork.setText(strOverUnitWork);
                        customViewHolder.countLike.setText(strOverUnitWork);
                        //update data
                        QuerySQL updateOver = new QuerySQL(strOverUnitWork, update_count_work);
                        updateOver.normalLike();
                        int showUnitInWork = Integer.parseInt(customViewHolder.totalUnitWork.getText().toString()) - 1;
                        String arrShowTotal = Integer.toString(showUnitInWork);
                        customViewHolder.totalUnitWork.setText(arrShowTotal);

                    }
//                    customViewHolder.hideUnitInWorkGroup.setText(unitInWorkGroup);

                }


            }

        });
        //----------------------------End Like Button-----------------------------//
        //update data UnitWork when change Activity
//        String EmployeeID = feedItem.getId_work();
//        QuerySQL getUnitWork = new QuerySQL(EmployeeID);
//        String jsonUnitWork = getUnitWork.getUnitWork();
//        String showUnitWork;
//
//        try{
//            JSONArray arrUnitWork = new JSONArray(jsonUnitWork);
//            for(int j=0;j<arrUnitWork.length();j++){
//                JSONObject objUnitWork = arrUnitWork.getJSONObject(j);
//                showUnitWork = objUnitWork.getString("UnitWork");
//
//
//                //customViewHolder.countLike.setText("X");
//
//                customViewHolder.countLike.setText(showUnitWork);
//            }
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        GetAllValues getUnitWorkGroup = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
        String showUnitWorkGroup = getUnitWorkGroup.getUnitWorkGroup();
        customViewHolder.totalUnitWork.setText(showUnitWorkGroup);
        // customViewHolder.countLike.setText(showUnitWorkGroup);
        //end

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
                //int overDisLike = Integer.parseInt(overFail) + Integer.parseInt(overLikeCheck);


                //******************check**************************//

                GetAllValues getUnitFailGroup = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
                String showUnitFailGroup = getUnitFailGroup.getUnitFailGroup();

                GetAllValues getUnitWorkGroup = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
                String showUnitWorkGroup = getUnitWorkGroup.getUnitWorkGroup();

                //*************************************************//
                int overDisLike = Integer.parseInt(showUnitFailGroup) + Integer.parseInt(showUnitWorkGroup);
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

                GetAllValues unitInWorkGroupCheck = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
                String unitInWorkGroup = unitInWorkGroupCheck.getUnitInWorkGroup();
                //int checkUnitInWorkFail = Integer.parseInt(showUnitInWorkFail);
                int checkUnitInWorkFail = Integer.parseInt(unitInWorkGroup);


                if (checkUnitInWorkFail > overDisLike) {
                    if (feedItem.getWorkDetail().equals("การนำเข้าห้องอบ") || feedItem.getWorkDetail().equals("ออกจากห้องอบ")) {
                        Intent UnitFailStove = new Intent(mContext, UnitFailStove2.class);
                        UnitFailStove.putExtra("idStove", feedItem.getId_work());
                        UnitFailStove.putExtra("EmployeeID", feedItem.getEmployeeID());
                        UnitFailStove.putExtra("WorkDetail", feedItem.getWorkDetail());
                        UnitFailStove.putExtra("orderID", feedItem.getOrderID());
                        mContext.startActivity(UnitFailStove);
                    } else {
                        Intent dislike_activity = new Intent(mContext, UnitFail.class);
                        dislike_activity.putExtra("sendFail", feedItem.getId_work());
                        dislike_activity.putExtra("Batch", feedItem.getBatch());
                        dislike_activity.putExtra("EmployeeID", feedItem.getEmployeeID());
                        dislike_activity.putExtra("DateFail", feedItem.getDateFail());
                        dislike_activity.putExtra("OrderID", feedItem.getOrderID());
                        dislike_activity.putExtra("March", feedItem.getMarchID());
                        dislike_activity.putExtra("DepartID", feedItem.getDepartID());
                        mContext.startActivity(dislike_activity);
                    }
                } else {
                    final AlertDialog.Builder dDialog = new AlertDialog.Builder(mContext);
                    dDialog.setTitle("หยุด");
                    dDialog.setMessage("ค่าเกินจำนวน");
                    dDialog.setPositiveButton("Close", null);
                    dDialog.show();

                }

//                GetAllValues getUnitFailGroup = new GetAllValues(feedItem.getOrderID(),feedItem.getWorkDetail(),feedItem.getBatch());
//                String UnitFailGroup = getUnitFailGroup.getUnitFailGroup();
                customViewHolder.totalUnitFail.setText(showUnitFailGroup);
            }
        });
        //-----------------------------End Dislike--------------------------------//
        //-----------------------------update total UnitFail-----------------------//
        GetAllValues getUnitFailGroup = new GetAllValues(feedItem.getOrderID(), feedItem.getWorkDetail(), feedItem.getBatch());
        String UnitFailGroup = getUnitFailGroup.getUnitFailGroup();
        customViewHolder.totalUnitFail.setText(UnitFailGroup);
        //------------------------------------------------------------------------//

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
        if (Integer.parseInt(showUnitFail) > 0) {
            QuerySQL updateHave = new QuerySQL(have, feedItem.getId_work());
            updateHave.updateHave();

        } else {
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
        customViewHolder.btnLike.setTag(customViewHolder);
        customViewHolder.btnDislike.setTag(customViewHolder);
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
        public ImageButton btnLike, btnDislike, btnTransfer;
        public TextView countLike, countDislike, nameEmployee;
        public TextView totalUnitWork, totalUnitFail;
        public TextView hideUnitInWorkGroup;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.textBatch = (TextView) view.findViewById(R.id.batch);
            this.btnLike = (ImageButton) view.findViewById(R.id.like);
            this.btnDislike = (ImageButton) view.findViewById(R.id.dislike);
            this.btnTransfer = (ImageButton) view.findViewById(R.id.btnTransfer);
            this.countLike = (TextView) view.findViewById(R.id.textLike);
            this.countDislike = (TextView) view.findViewById(R.id.textDislike);
            this.nameEmployee = (TextView) view.findViewById(R.id.nameEmployee);
            this.totalUnitWork = (TextView) view.findViewById(R.id.totalUnitWork);
            this.totalUnitFail = (TextView) view.findViewById(R.id.totalUnitFail);
            this.hideUnitInWorkGroup = (TextView) view.findViewById(R.id.hide_unitInWork);
        }
    }
}
