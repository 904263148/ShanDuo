package com.yapin.shanduo.im.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupAddOpt;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberRoleType;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.qcloud.presentation.presenter.GroupInfoPresenter;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.ui.LineControllerView;
import com.tencent.qcloud.ui.ListPickerDialog;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.GroupInfo;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.presenter.CreateGroupPresenter;
import com.yapin.shanduo.presenter.EditGroupPresenter;
import com.yapin.shanduo.presenter.ImageUrlPresenter;
import com.yapin.shanduo.ui.activity.ClipImageActivity;
import com.yapin.shanduo.ui.contract.CreateGroupContract;
import com.yapin.shanduo.ui.contract.EditGroupNameContact;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.ui.fragment.SelectPhotoDialogFragment;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.FileUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupProfileActivity extends FragmentActivity implements GroupInfoView, View.OnClickListener ,CreateGroupContract.View , SelectPhotoDialogFragment.ImageCropListener , UploadContract.View , TIMCallBack , EditGroupNameContact.View{

    private final String TAG = "GroupProfileActivity";

    private String identify,type;
    private GroupInfoPresenter groupInfoPresenter;
    private TIMGroupDetailInfo info;
    private boolean isInGroup;
    private boolean isGroupOwner;
    private final int REQ_CHANGE_NAME = 100, REQ_CHANGE_INTRO = 200;
    private TIMGroupMemberRoleType roleType = TIMGroupMemberRoleType.NotMember;
    private Map<String, TIMGroupAddOpt> allowTypeContent;
    private Map<String, TIMGroupReceiveMessageOpt> messageOptContent;
    private LineControllerView name,intro;
    private CreateGroupPresenter createGroupPresenter;

    private Context context;
    private Activity activity;

    private SelectPhotoDialogFragment selectPhotoDialogFragment;
    private ImageUrlPresenter imageUrlPresenter;
    private ImageView ivHead;
    private EditGroupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_setting);

        createGroupPresenter = new CreateGroupPresenter();
        createGroupPresenter.init(this);

        identify = getIntent().getStringExtra("identify");
        isInGroup = GroupInfo.getInstance().isInGroup(identify);
        groupInfoPresenter = new GroupInfoPresenter(this, Collections.singletonList(identify), isInGroup);
        groupInfoPresenter.getGroupDetailInfo();
        name = (LineControllerView) findViewById(R.id.nameText);
        intro = (LineControllerView) findViewById(R.id.groupIntro);
        LinearLayout controlInGroup = (LinearLayout) findViewById(R.id.controlInGroup);
        controlInGroup.setVisibility(isInGroup? View.VISIBLE:View.GONE);
        TextView controlOutGroup = (TextView) findViewById(R.id.controlOutGroup);
        controlOutGroup.setVisibility(isInGroup ? View.GONE : View.VISIBLE);

        selectPhotoDialogFragment = new SelectPhotoDialogFragment();
        selectPhotoDialogFragment.setImagePathListener(this);
        imageUrlPresenter = new ImageUrlPresenter();
        imageUrlPresenter.init(this);
        presenter = new EditGroupPresenter();
        presenter.init(this);
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {

        context = ShanDuoPartyApplication.getContext();
        activity = this;

        info = groupInfos.get(0);
        isGroupOwner = info.getGroupOwner().equals(UserInfo.getInstance().getId());
        roleType = GroupInfo.getInstance().getRole(identify);
        type = info.getGroupType();

        ivHead = findViewById(R.id.iv_head);
        GlideUtil.loadIMHead(context , activity , info.getFaceUrl() , ivHead);
        ivHead.setOnClickListener(this);

        LineControllerView member = (LineControllerView) findViewById(R.id.member);
        if (isInGroup){
            member.setContent(String.valueOf(info.getMemberNum()));
            member.setOnClickListener(this);
        }else{
            member.setVisibility(View.GONE);
        }
        name.setContent(info.getGroupName());
        LineControllerView id = (LineControllerView) findViewById(R.id.idText);
        id.setContent(info.getGroupId());

        intro.setContent(info.getGroupIntroduction());
        LineControllerView opt = (LineControllerView) findViewById(R.id.addOpt);
        switch (info.getGroupAddOpt()){
            case TIM_GROUP_ADD_AUTH:
                opt.setContent(getString(R.string.chat_setting_group_auth));
                break;
            case TIM_GROUP_ADD_ANY:
                opt.setContent(getString(R.string.chat_setting_group_all_accept));
                break;
            case TIM_GROUP_ADD_FORBID:
                opt.setContent(getString(R.string.chat_setting_group_all_reject));
                break;
        }
        LineControllerView msgNotify = (LineControllerView) findViewById(R.id.messageNotify);
        if (GroupInfo.getInstance().isInGroup(identify)){
            switch (GroupInfo.getInstance().getMessageOpt(identify)){
                case NotReceive:
                    msgNotify.setContent(getString(R.string.chat_setting_no_rev));
                    break;
                case ReceiveAndNotify:
                    msgNotify.setContent(getString(R.string.chat_setting_rev_notify));
                    break;
                case ReceiveNotNotify:
                    msgNotify.setContent(getString(R.string.chat_setting_rev_not_notify));
                    break;
            }
            msgNotify.setOnClickListener(this);
            messageOptContent = new HashMap<>();
            messageOptContent.put(getString(R.string.chat_setting_no_rev), TIMGroupReceiveMessageOpt.NotReceive);
            messageOptContent.put(getString(R.string.chat_setting_rev_not_notify), TIMGroupReceiveMessageOpt.ReceiveNotNotify);
            messageOptContent.put(getString(R.string.chat_setting_rev_notify), TIMGroupReceiveMessageOpt.ReceiveAndNotify);
        }else{
            msgNotify.setVisibility(View.GONE);
        }
        if (isManager()){
            opt.setCanNav(true);
            opt.setOnClickListener(this);
            allowTypeContent = new HashMap<>();
            allowTypeContent.put(getString(R.string.chat_setting_group_auth), TIMGroupAddOpt.TIM_GROUP_ADD_AUTH);
            allowTypeContent.put(getString(R.string.chat_setting_group_all_accept), TIMGroupAddOpt.TIM_GROUP_ADD_ANY);
            allowTypeContent.put(getString(R.string.chat_setting_group_all_reject), TIMGroupAddOpt.TIM_GROUP_ADD_FORBID);
            name.setCanNav(true);
            name.setOnClickListener(this);
            intro.setCanNav(true);
            intro.setOnClickListener(this);
        }
        TextView btnDel = (TextView) findViewById(R.id.btnDel);
        btnDel.setText(isGroupOwner ? getString(R.string.chat_setting_dismiss) : getString(R.string.chat_setting_quit));

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChat:
                ChatActivity.navToChat(this,identify, TIMConversationType.Group);
                break;
            case R.id.btnDel:
                if (isGroupOwner){
//                    GroupManagerPresenter.dismissGroup(identify, new TIMCallBack() {
//                        @Override
//                        public void onError(int i, String s) {
//                            Log.i(TAG, "onError code" + i + " msg " + s);
//                            if (i == 10004 && type.equals(GroupInfo.privateGroup)){
//                                Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_quit_fail_private),Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onSuccess() {
//
//                        }
//                    });
                    createGroupPresenter.createGroup(Constants.TYPE_DELETE , identify  , "" , "");
                }else{
                    GroupManagerPresenter.quitGroup(identify, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.i(TAG, "onError code" + i + " msg " + s);
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_quit_succ),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                break;
            case R.id.controlOutGroup:
                Intent intent = new Intent(this, ApplyGroupActivity.class);
                intent.putExtra("identify", identify);
                startActivity(intent);
                break;
            case R.id.member:
                Intent intentGroupMem = new Intent(this, GroupMemberActivity.class);
                intentGroupMem.putExtra("id", identify);
                intentGroupMem.putExtra("type",type);
                startActivity(intentGroupMem);
                break;
            case R.id.addOpt:
                final String[] stringList = allowTypeContent.keySet().toArray(new String[allowTypeContent.size()]);
                new ListPickerDialog().show(stringList,getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        TIMGroupManager.getInstance().modifyGroupAddOpt(identify, allowTypeContent.get(stringList[which]), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_change_err),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                LineControllerView opt = (LineControllerView) findViewById(R.id.addOpt);
                                opt.setContent(stringList[which]);
                            }
                        });
                    }
                });
                break;
            case R.id.nameText:
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditActivity.navToEdit(GroupProfileActivity.this, getString(R.string.chat_setting_change_group_name), info.getGroupName(), REQ_CHANGE_NAME, new EditActivity.EditInterface() {
                            @Override
                            public void onEdit(final String text, TIMCallBack callBack) {
                                TIMGroupManager.getInstance().modifyGroupName(identify, text, callBack);
                                presenter.editName(text , identify);
                            }
                        },20);

                    }
                });
                break;
            case R.id.groupIntro:
                intro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditActivity.navToEdit(GroupProfileActivity.this, getString(R.string.chat_setting_change_group_intro), intro.getContent(), REQ_CHANGE_INTRO, new EditActivity.EditInterface() {
                            @Override
                            public void onEdit(final String text, TIMCallBack callBack) {
                                TIMGroupManager.getInstance().modifyGroupIntroduction(identify, text, callBack);
                            }
                        },20);

                    }
                });
                break;
            case R.id.messageNotify:
                final String[] messageOptList = messageOptContent.keySet().toArray(new String[messageOptContent.size()]);
                new ListPickerDialog().show(messageOptList,getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        TIMGroupManager.getInstance().modifyReceiveMessageOpt(identify, messageOptContent.get(messageOptList[which]), new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_change_err),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess() {
                                LineControllerView msgNotify = (LineControllerView) findViewById(R.id.messageNotify);
                                msgNotify.setContent(messageOptList[which]);
                            }
                        });
                    }
                });
                break;
            case R.id.iv_head:
                if(isGroupOwner){
                    selectPhotoDialogFragment.show(getSupportFragmentManager() , "");
                }
                break;
        }
    }

    //请求相机
    private static final int REQUEST_CAPTURE = 105;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //调用照相机返回图片文件
    private File tempFile;
    private List<String> path = new ArrayList<>();
    private String FacePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CHANGE_NAME){
            if (resultCode == RESULT_OK){
                name.setContent(data.getStringExtra(EditActivity.RETURN_EXTRA));
            }
        }else if (requestCode == REQ_CHANGE_INTRO){
            if (resultCode == RESULT_OK){
                intro.setContent(data.getStringExtra(EditActivity.RETURN_EXTRA));
            }
        }

        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }
                    FacePath = FileUtil.getRealFilePathFromUri(context, uri);
//                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    setImFace();
                }
                break;
        }


    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        StartActivityUtil.start(activity ,ClipImageActivity.class , uri , REQUEST_CROP_PHOTO);
    }

    public void setImFace(){
        path.clear();
        path.add(FacePath);
        imageUrlPresenter.upload(path);
    }

    private boolean isManager(){
        return roleType == TIMGroupMemberRoleType.Owner || roleType == TIMGroupMemberRoleType.Admin;
    }

    @Override
    public void success(String data) {
        Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_dismiss_succ),Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void uploadSuccess(String imgIds) {
        TIMGroupManager.getInstance().modifyGroupFaceUrl(identify, ApiUtil.IMG_URL + imgIds, this);
    }

    @Override
    public void show(String data) {
        ToastUtil.showShortToast(context,data);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(this,"网络异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(this,msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(this,msg);
    }

    @Override
    public void initView() {

    }

    @Override
    public void setImagePath(File path) {
        this.tempFile = path;
    }

    @Override
    public void onError(int i, String s) {
        ToastUtil.showShortToast(context ,s);
    }

    @Override
    public void onSuccess() {
        ToastUtil.showShortToast(context , "修改群头像成功");
        Glide.with(context).load(FacePath).transform(GlideUtil.transform(context)).into(ivHead);
    }
}
