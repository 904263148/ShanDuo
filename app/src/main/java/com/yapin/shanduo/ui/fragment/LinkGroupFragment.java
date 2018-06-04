package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.TIMGroupCacheInfo;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.adapters.ProfileSummaryAdapter;
import com.yapin.shanduo.im.model.GroupInfo;
import com.yapin.shanduo.im.model.GroupProfile;
import com.yapin.shanduo.im.model.ProfileSummary;
import com.yapin.shanduo.im.ui.GroupListActivity;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.widget.LoadingView;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;

public class LinkGroupFragment extends Fragment implements Observer {

    private ProfileSummaryAdapter adapter;
    private ListView listView;
    private String type;
    private List<ProfileSummary> list;

    private View view;

    private Context context;
    private Activity activity;
    private LoadingView loadingView;

    public LinkGroupFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LinkGroupFragment newInstance() {
        LinkGroupFragment fragment = new LinkGroupFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_link_group, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        type = "Public";
        loadingView = view.findViewById(R.id.loading_view);
        listView =(ListView) view.findViewById(R.id.list);
        list = GroupInfo.getInstance().getGroupListByType(type);
        adapter = new ProfileSummaryAdapter(context, R.layout.item_profile_summary, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(position).onClick(activity);
            }
        });
        GroupEvent.getInstance().addObserver(this);
        if(list.size() == 0){
            loadingView.noData(R.string.tips_no_group);
        }else {
            loadingView.setGone();
        }
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
    public void update(Observable observable, Object data) {
        if (observable instanceof GroupEvent){
            if (data instanceof GroupEvent.NotifyCmd){
                GroupEvent.NotifyCmd cmd = (GroupEvent.NotifyCmd) data;
                switch (cmd.type){
                    case DEL:
                        delGroup((String) cmd.data);
                        break;
                    case ADD:
                        addGroup((TIMGroupCacheInfo) cmd.data);
                        break;
                    case UPDATE:
                        updateGroup((TIMGroupCacheInfo) cmd.data);
                        break;
                }
            }
        }
    }

    private void delGroup(String groupId){
        Iterator<ProfileSummary> it = list.iterator();
        while (it.hasNext()){
            ProfileSummary item = it.next();
            if (item.getIdentify().equals(groupId)){
                it.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }


    private void addGroup(TIMGroupCacheInfo info){
        if (info!=null && info.getGroupInfo().getGroupType().equals(type)){
            GroupProfile profile = new GroupProfile(info);
            list.add(profile);
            adapter.notifyDataSetChanged();
        }

    }

    private void updateGroup(TIMGroupCacheInfo info){
        delGroup(info.getGroupInfo().getGroupId());
        addGroup(info);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        GroupEvent.getInstance().deleteObserver(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            loadingView.setVisibility(View.VISIBLE);
            loadingView.noData(R.string.tips_no_login);
        }else {
            list = GroupInfo.getInstance().getGroupListByType(type);
            if(list.size() == 0){
                loadingView.noData(R.string.tips_no_group);
            }else {
                loadingView.setGone();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
