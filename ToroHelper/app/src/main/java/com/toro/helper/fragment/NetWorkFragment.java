package com.toro.helper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toro.helper.R;
import com.toro.helper.utils.OnHttpDataUpdateListener;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class NetWorkFragment extends Fragment implements OnHttpDataUpdateListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_base, container, false
        );
    }

    @Override
    public boolean bindData(int tag, Object object) {
        return false;
    }
}
