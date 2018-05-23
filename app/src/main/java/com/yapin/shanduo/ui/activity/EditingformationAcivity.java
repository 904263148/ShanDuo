package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.ModifyPresenter;
import com.yapin.shanduo.ui.contract.ModifyContract;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/4/19.
 */

public class EditingformationAcivity extends BaseActivity implements ModifyContract.View{
    private ModifyPresenter presenter;
    private Context context;
    private Activity activity;

    String hometown;

    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;


    @BindView(R.id.modify_tv_rg)
    TextView modify_tv_rg;
    @BindView(R.id.modify_tv_flicker)
    TextView modify_tv_flicker;
    @BindView(R.id.modify_et_nickname)
    TextView modify_et_nickname;
    @BindView(R.id.date_display)
    TextView date_display;
    @BindView(R.id.tv_Emotionalstate)
    TextView tv_Emotionalstate;
    @BindView(R.id.tv_Personalitysignature)
    TextView tv_Personalitysignature;
    @BindView(R.id.tv_Hometown)
    TextView tv_Hometown;
    @BindView(R.id.tv_Occupation)
    TextView tv_Occupation;
    @BindView(R.id.tv_School)
    TextView tv_School;

    String gender;
    String emotion;



        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinginformation);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
//            name, birthday,gender,emotion,signature,hometown,occupation,school

        modify_tv_flicker.setText(PrefJsonUtil.getProfile(context).getUserId());
        modify_et_nickname.setText(PrefJsonUtil.getProfile(context).getName());
        modify_tv_rg.setText(PrefJsonUtil.getProfile(context).getGender().equals("1")?"男":"女");
        date_display.setText(PrefJsonUtil.getProfile(context).getBirthday());
        tv_Emotionalstate.setText(PrefJsonUtil.getProfile(context).getEmotion());
        if (tv_Emotionalstate.equals("0")){
            tv_Emotionalstate.setText("保密");
        }else if (tv_Emotionalstate.equals("1")){
            tv_Emotionalstate.setText("已婚");
        }else {
            tv_Emotionalstate.setText("未婚");
        }
        tv_Personalitysignature.setText(PrefJsonUtil.getProfile(context).getSignature());
        tv_Hometown.setText(PrefJsonUtil.getProfile(context).getHometown());
        tv_Occupation.setText(PrefJsonUtil.getProfile(context).getOccupation());
        tv_School.setText(PrefJsonUtil.getProfile(context).getSchool());

        presenter = new ModifyPresenter();
        presenter.init(context,this);

            //日期选择
            final Calendar ca = Calendar.getInstance();
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @OnClick({R.id.fl_rg,R.id.fl_date,R.id.fl_emot,R.id.modify_et_nickname,
            R.id.fl_person,R.id.fl_hom,R.id.fl_scho,R.id.fl_occup,R.id.iv_back})
    public void onClick(View v){
            switch (v.getId()){
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.fl_rg:     //性别
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("请选择性别");
                    final String[] sex = {"男", "女"};
                    //    设置一个单项选择下拉框
                    /**
                     * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                     * 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认'男' 会被勾选上
                     * 第三个参数给每一个单选项绑定一个监听器
                     */
                    builder.setSingleChoiceItems(sex, getIsEvent(),new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // 自动生成的方法存根
                            if (which == 0) {//男
                                gender = "1";
                            }else if(which == 1){//女
                                gender = "0";
                            }
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
//                            Log.i("gender",gender.toString()+"");
                            presenter.modify("",gender,"","","","","","");
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder.show();
                    break;
                case R.id.fl_date:     //出生年月
                    show();
                    break;
                case R.id.fl_emot:    //感情状态
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    final String[] emotions = {"已婚", "未婚","保密"};
                    //    设置一个单项选择下拉框
                    builder1.setSingleChoiceItems(emotions, getIsEvent(), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if (which == 0) {
                                emotion = "1";
                            }else if(which == 1){
                                emotion = "2";
                            }else if (which ==2){
                                emotion = "0";
                            }
                        }
                    });
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            presenter.modify("","","",emotion,"","","","");
                        }
                    });
                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder1.show();
                    break;
                case R.id.modify_et_nickname:   //昵称
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
                            builder2.setTitle("请输入你要修改的昵称");
                            //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                            View view = LayoutInflater.from(activity).inflate(R.layout.nicknamepop_up, null);
                            //    设置我们自己定义的布局文件作为弹出框的Content
                            builder2.setView(view);
                            final EditText et_nickname = (EditText)view.findViewById(R.id.nickname);
                            builder2.setPositiveButton("确定", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    String name = et_nickname.getText().toString().trim();
                                    presenter.modify(name,"","","","","","","");
                                }
                            });
                            builder2.setNegativeButton("取消", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                }
                            });
                            builder2.show();
                            break;
                case R.id.fl_person:
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                    builder3.setTitle("请输入你要修改的个性签名");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view1 = LayoutInflater.from(activity).inflate(R.layout.personalitysignature, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder3.setView(view1);
                    final EditText tv_Personalitysignature = (EditText)view1.findViewById(R.id.Personalitysignature);
                    builder3.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String signature = tv_Personalitysignature.getText().toString().trim();
                            presenter.modify("","","","",signature,"","","");
                        }
                    });
                    builder3.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder3.show();
                    break;
                case R.id.fl_hom:  //家乡
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(activity);
                    builder4.setTitle("请输入你要修改的家乡");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view2 = LayoutInflater.from(activity).inflate(R.layout.hometown, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder4.setView(view2);
                    final EditText tv_Hometown = (EditText)view2.findViewById(R.id.hometown);
                    builder4.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            hometown = tv_Hometown.getText().toString().trim();
                            presenter.modify("","","","","",hometown,"","");
                        }
                    });
                    builder4.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder4.show();
                    break;
                case R.id.fl_scho:    //毕业学校
                    AlertDialog.Builder builder5 = new AlertDialog.Builder(activity);
                    builder5.setTitle("请输入你要修改的昵称");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view3 = LayoutInflater.from(activity).inflate(R.layout.school, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder5.setView(view3);
                    final EditText tv_School = (EditText)view3.findViewById(R.id.school);
                    builder5.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String school = tv_School.getText().toString().trim();
                            presenter.modify("","","","","","","",school);
                        }
                    });
                    builder5.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder5.show();
                    break;
                case R.id.fl_occup:    //职业
                    AlertDialog.Builder builder6 = new AlertDialog.Builder(activity);
                    builder6.setTitle("请输入你要修改的职业");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view4 = LayoutInflater.from(activity).inflate(R.layout.occupation, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder6.setView(view4);
                    final EditText tv_Occupation = (EditText)view4.findViewById(R.id.Occupation);
                    builder6.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String occupation = tv_Occupation.getText().toString().trim();
                            presenter.modify("","","","","","",occupation,"");
                        }
                    });
                    builder6.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder6.show();
                    break;
            }

    }
    private void show() {
        //获取当前年月日
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//当前年
        int month = calendar.get(Calendar.MONTH);//当前月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//当前日
        //new一个日期选择对话框的对象,并设置默认显示时间为当前的年月日时间.
        DatePickerDialog dialog = new DatePickerDialog(this, mdateListener, year, month, day);
        dialog.show();
    }
    /**
     * 日期选择的回调监听
     */
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
            // 绑定选中的日期
            date_display.setText(years+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            String birthday = date_display.getText().toString().trim();
            presenter.modify("","",birthday,"","","","","");
        }

    };

    @Override
    public void initView() {

    }

    @Override
    public void success(String data) {
        if ("1".equals(gender)) {//男
            modify_tv_rg.setText("男");
        }else if("0".equals(gender)){//女
            modify_tv_rg.setText("女");
        }else if ("0".equals(emotion)) {
            tv_Emotionalstate.setText("保密");
        }else if("1".equals(emotion)){
            tv_Emotionalstate.setText("已婚");
        }else if("2".equals(emotion)){
            tv_Emotionalstate.setText("未婚");
        }
        ToastUtil.showShortToast(context,"修改成功");

    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {

    }

}
