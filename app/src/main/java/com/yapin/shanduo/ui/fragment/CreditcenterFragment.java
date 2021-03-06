package com.yapin.shanduo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yapin.shanduo.R;

/**
 * Created by dell on 2018/5/29.
 */

public class CreditcenterFragment extends Fragment {

    private int mTitle;
    private int mColor;
//    private TextView mTextView;
    private LinearLayout mLinear;

    public static CreditcenterFragment newInstance(int title , int color){
        CreditcenterFragment fragment = new CreditcenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("title",title);
        bundle.putInt("color",color);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getInt("title");
        mColor = getArguments().getInt("color");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_credit_center, container, false);
//        mTextView = (TextView) view.findViewById(R.id.fragment_textView);
//        mTextView.setText("Page"+(mTitle + 1));
        mLinear = (LinearLayout) view.findViewById(R.id.fragment_ll);
        /**这里注意是setBackgroundResource不是setBackgroundColor；setBackgroundResource(int resId)方法的参数是一个组件的id值。该方法也是用于加载组件的背景图片的；setBackgroundColor(Color.XXX)方法参数为一个Color类的静态常量.顾名思义,它是用来设置背景颜色的方法.*/
        mLinear.setBackgroundResource(mColor);
        return view;
    }


}
