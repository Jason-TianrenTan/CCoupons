package com.example.administrator.ccoupons.User.UserCoupons.SingleCouponList.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.example.administrator.ccoupons.Main.Coupon;
import com.example.administrator.ccoupons.User.CouponDetail.CouponDetailActivity;
import com.example.administrator.ccoupons.User.CouponDetail.DisplayCouponDetailActivity;
import com.example.administrator.ccoupons.User.CouponDetail.MyCouponDetailActivity;
import com.example.administrator.ccoupons.User.UserCouponInfoAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class UserBoughtAdapter extends UserCouponInfoAdapter {


    private Context mContext;
    public UserBoughtAdapter(ArrayList<Coupon> clist) {
        super(clist);
        /*setCouponClickListener(new CouponClickedListener() {
            @Override

            public void onCouponClicked(Coupon coupon) {
                Intent intent = new Intent(mContext, MyCouponDetailActivity.class);
                intent.putExtra("Coupon", coupon);
                mContext.startActivity(intent);
            }
        });*/
    }


    @Override
    public UserCouponInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        return super.onCreateViewHolder(parent, viewType);
    }


}
