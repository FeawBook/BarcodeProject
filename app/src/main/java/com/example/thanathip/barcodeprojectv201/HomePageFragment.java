package com.example.thanathip.barcodeprojectv201;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * Created by thanathip on 31/7/2558.
 */
public class HomePageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View picHome = inflater.inflate(R.layout.home_page_fragment,container,false);
        ImageView image = (ImageView)picHome.findViewById(R.id.testPic);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.blink);
        image.startAnimation(animation);
        return picHome;
    }
}
