package com.toro.helper.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toro.helper.R;
import com.toro.helper.base.BaseFragment;
import com.toro.helper.base.ToroListAdapter;
import com.toro.helper.base.ToroListFragment;
import com.toro.helper.modle.LoginUserData;
import com.toro.helper.modle.ToroUserManager;
import com.toro.helper.utils.ImageLoad;
import com.toro.helper.utils.StringUtils;
import com.toro.helper.view.RoundnessImageView;

import java.util.List;

/**
 * Create By liujia
 * on 2018/10/27.
 **/
public class MineFragment extends BaseFragment {
    private RoundnessImageView headImageView;
    private ImageView headEditImage;
    private TextView nameText,phoneText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =   inflater.inflate(
                R.layout.mine_fragment, container, false
        );
        initView(view);
        return view;
    }

    private void initView(View rootView) {
        headImageView = rootView.findViewById(R.id.head_img);
        headEditImage = rootView.findViewById(R.id.edit_image);
        nameText = rootView.findViewById(R.id.name_text);
        phoneText = rootView.findViewById(R.id.phone_text);

        LoginUserData loginUserData = ToroUserManager.getInstance(getContext()).getLoginUserData();
        if(loginUserData != null) {
            if(loginUserData.getHeadPhoto() != null) {
                ImageLoad.GlidLoad(headImageView,loginUserData.getHeadPhoto(),R.mipmap.default_head);
            } else {
                headImageView.setImageResource(R.mipmap.default_head);
            }
            if(StringUtils.isNotEmpty(loginUserData.getNickName())) {
                nameText.setText(loginUserData.getNickName());
            } else {
                nameText.setText(R.string.have_no_nick_name);
            }
            phoneText.setText(loginUserData.getPhone());
        }

        View setting1 = rootView.findViewById(R.id.setting_item1);
        View setting2 = rootView.findViewById(R.id.setting_item2);
        View setting3 = rootView.findViewById(R.id.setting_item3);
        View setting4 = rootView.findViewById(R.id.setting_item4);

        setItemView(setting1,R.string.family_member_manager,R.mipmap.family_member_manager_icon,null);
        setItemView(setting2,R.string.family_photo,R.mipmap.my_photo_icon,null);
        setItemView(setting3,R.string.sms_mode,R.mipmap.sms_mode_icon,null);
        setItemView(setting4,R.string.setting,R.mipmap.setting_icon,null);
    }

    private void setItemView(View itemView, int stringID, int imgID, View.OnClickListener listener) {
        ((ImageView)itemView.findViewById(R.id.title_icon)).setImageResource(imgID);
        ((TextView)itemView.findViewById(R.id.title_text)).setText(stringID);
        itemView.setOnClickListener(listener);
    }
}
