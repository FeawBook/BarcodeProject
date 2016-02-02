package com.example.thanathip.barcodeprojectv201;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by thanathip on 18/8/2558.
 */
public class FailList extends BaseAdapter {
    private Context mContext;

    public FailList(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return 24;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView testText;
        if (convertView == null) {
            testText = new TextView(mContext);
            testText.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));

            testText.setPadding(4, 4, 4, 4);
        } else {
            testText = (TextView) convertView;
        }

        testText.setText("ขึ้นรูปลูกถ้วย");

        return testText;
    }
}
