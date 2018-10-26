package com.toro.helper.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.utils.OnHttpDataUpdateListener;
import com.toro.helper.utils.StringUtils;

/**
 * Create By liujia
 * on 2018/10/19.
 **/
public class ToroFragment extends Fragment implements OnHttpDataUpdateListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_base, container, false
        );
    }

    @Override
    public boolean bindData(int tag, Object object) {
        try{
            String result = (String) object;
            BaseResponeData data = DataModleParser.parserBaseResponeData(result);
            if(!data.isStatus()) {
                if(StringUtils.isEmpty(data.getMessage())) {
                    Toast.makeText(getContext(),getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getContext(),data.getMessage(),Toast.LENGTH_LONG).show();
                }
                return false;
            }
            return true;
        }catch (Exception e) {

        }
        Toast.makeText(getContext(),getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
        return false;
    }
}
