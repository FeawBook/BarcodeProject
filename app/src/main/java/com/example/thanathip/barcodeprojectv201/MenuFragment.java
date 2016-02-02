package com.example.thanathip.barcodeprojectv201;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thanathip on 11/1/2559.
 */
public class MenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View menu = inflater.inflate(R.layout.menu_fragment,container,false);
        return menu;
    }
}
