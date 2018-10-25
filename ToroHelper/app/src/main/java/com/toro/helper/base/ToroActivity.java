package com.toro.helper.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.toro.helper.R;
import com.toro.helper.modle.BaseResponeData;
import com.toro.helper.modle.DataModleParser;
import com.toro.helper.utils.OnHttpDataUpdateListener;
import com.toro.helper.utils.StringUtils;

/**
 * Create By liujia
 * on 2018/10/23.
 **/
public class ToroActivity extends AppCompatActivity implements OnHttpDataUpdateListener {
    @Override
    public boolean bindData(int tag, Object object) {
        try{
            String result = (String) object;
            BaseResponeData data = DataModleParser.parserBaseResponeData(result);
            if(!data.isStatus()) {
                if(StringUtils.isEmpty(data.getMessage())) {
                    Toast.makeText(this,getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(this,data.getMessage(),Toast.LENGTH_LONG).show();
                }
                return false;
            }
            return true;
        }catch (Exception e) {

        }
        Toast.makeText(this,getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
        return false;
    }
}
