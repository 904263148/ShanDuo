package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.User;
import com.yapin.shanduo.ui.adapter.LinkFriendAdapter;
import com.yapin.shanduo.widget.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkFriendFragment extends Fragment {


    @BindView(R.id.quick_index_bar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.listview)
    ListView listview;

    private Context context;
    private Activity activity;

    private ArrayList<User> list;

    public LinkFriendFragment() {
        // Required empty public constructor
    }

    public static LinkFriendFragment newInstance() {
        LinkFriendFragment fragment = new LinkFriendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_friend, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    public void initView() {

        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        //给自定义view设置自定义监听，当点击到某个字母的时候弹出吐司显示这个字母；
        //当点击字母的时候遍历集合，找到首字母为点击字母的HaoHan的下标，让listview跳转到响应位置
        quickIndexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListen() {

            @Override
            public void onLetterChange(String letter) {

                quickIndexBar.setBackgroundColor(getResources().getColor(R.color.bg_color));

                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(letter);

                if(letter.equals("↑")){
                    listview.setSelection(0);
                    return;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (letter.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                        listview.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }

            @Override
            //当手指弹起的时候此方法执行
            public void onResert() {
                tvHint.setVisibility(View.GONE);
                quickIndexBar.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new User("亳州")); // 亳[bó]属于不常见的二级汉字
        list.add(new User("大娃"));
        list.add(new User("二娃"));
        list.add(new User("三娃"));
        list.add(new User("四娃"));
        list.add(new User("五娃"));
        list.add(new User("六娃"));
        list.add(new User("七娃"));
        list.add(new User("喜羊羊"));
        list.add(new User("美羊羊"));
        list.add(new User("懒羊羊"));
        list.add(new User("沸羊羊"));
        list.add(new User("暖羊羊"));
        list.add(new User("慢羊羊"));
        list.add(new User("灰太狼"));
        list.add(new User("红太狼"));
        list.add(new User("孙悟空"));
        list.add(new User("黑猫警长"));
        list.add(new User("舒克"));
        list.add(new User("贝塔"));
        list.add(new User("海尔"));
        list.add(new User("阿凡提"));
        list.add(new User("邋遢大王"));
        list.add(new User("哪吒"));
        list.add(new User("没头脑"));
        list.add(new User("不高兴"));
        list.add(new User("蓝皮鼠"));
        list.add(new User("大脸猫"));
        list.add(new User("大头儿子"));
        list.add(new User("小头爸爸"));
        list.add(new User("蓝猫"));
        list.add(new User("淘气"));
        list.add(new User("叶峰"));
        list.add(new User("楚天歌"));
        list.add(new User("江流儿"));
        list.add(new User("Tom"));
        list.add(new User("Jerry"));
        list.add(new User("12345"));
        list.add(new User("54321"));
        list.add(new User("_(:з」∠)_"));
        list.add(new User("……%￥#￥%#"));
        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        LinkFriendAdapter adapter = new LinkFriendAdapter(context, list);
        listview.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
