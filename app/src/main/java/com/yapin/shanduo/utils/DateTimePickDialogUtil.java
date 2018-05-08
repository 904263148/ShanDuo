package com.yapin.shanduo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.yapin.shanduo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间选择控件
 */
public class DateTimePickDialogUtil implements OnTimeChangedListener {
    private DatePicker datePicker;
    private String dateTime;
    private String initDateTime;
    private Activity activity;
    public Long d = null;
    private int day = -1;
    private TextView dateTitle;
    int hours; //时
    int minutes; //分

    public DateTimePickDialogUtil(Activity activity, String initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;
    }

    public void init(DatePicker datePicker) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (day > 0) {
            calendar.setTime(new Date(System.currentTimeMillis() + (day * 60 * 60 * 1000 * 24)));
            dateTime = sdf.format(calendar.getTime());
        } else {
            dateTime = sdf.format(calendar.getTime());
        }

        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "- ";
        }

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (day > 0) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.setTime(new Date(System.currentTimeMillis() + (day * 60 * 60 * 1000 * 24)));
                    // 获得日历实例
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(view.getYear(), view.getMonth(),
                            view.getDayOfMonth());
                    if (System.currentTimeMillis() + (day * 60 * 60 * 1000 * 24) > calendar.getTimeInMillis()) {
                        view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
                        dateTime = sdf.format(mCalendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    } else {
                        // 获得日历实例
                        dateTime = sdf.format(calendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    }
                } else if (day == -9999) {
                    // 获得日历实例
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(view.getYear(), view.getMonth(),
                            view.getDayOfMonth());
                    dateTime = sdf.format(calendar.getTime());
                    dateTitle.setText(dateTime + getWeek(dateTime));
                } else {
                    if (isDateAfter(view)) {
                        Calendar mCalendar = Calendar.getInstance();
                        view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), this);
                        dateTime = sdf.format(mCalendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    } else {
                        // 获得日历实例
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(view.getYear(), view.getMonth(),
                                view.getDayOfMonth());
                        dateTime = sdf.format(calendar.getTime());
                        dateTitle.setText(dateTime + getWeek(dateTime));
                    }
                }
            }

            private boolean isDateAfter(DatePicker tempView) {
                Calendar mCalendar = Calendar.getInstance();
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.set(tempView.getYear(), tempView.getMonth(), tempView.getDayOfMonth(), 0, 0, 0);
                if (tempCalendar.after(mCalendar))
                    return false;
                else
                    return true;

            }
        });
    }

    /**
     * 弹出日期时间选择框方法
     * 当前时间的下一天
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public Dialog dateTimePicKDialog(final TextView inputDate, int day, final boolean isShowTime) {
        this.day = day;
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_datetime, null);
        final Dialog dialog = new android.app.AlertDialog.Builder(activity).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keylistener);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setContentView(dateTimeLayout);


        dateTitle = (TextView) dateTimeLayout.findViewById(R.id.date_title);
        TimePicker timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        if (isShowTime) {
            timePicker.setVisibility(View.VISIBLE);
            timePicker.setIs24HourView(true);
        }
        hours = timePicker.getCurrentHour();
        minutes = timePicker.getCurrentMinute();
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;
            }
        });
        dateTitle.setText(initDateTime);
        ImageView dateCancle = (ImageView) dateTimeLayout.findViewById(R.id.date_cancle);
        TextView dateSubmit = (TextView) dateTimeLayout.findViewById(R.id.date_submit);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);

        dateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Long diff = null;
                try {
                    Date d1 = df.parse(initDateTime);
                    Date d2 = df.parse(dateTime); //前的时间
                    diff = d1.getTime() - d2.getTime();   //两时间差
                    d = d1.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (diff >= 0) {
                    Toast.makeText(activity.getApplicationContext(), "请选择大于现在的日期", Toast.LENGTH_SHORT).show();

                } else {
                    if (isShowTime) {
                        if (minutes < 10) {
                            inputDate.setText(dateTime + " " + hours + ":0" + minutes);
                        } else {
                            inputDate.setText(dateTime + " " + hours + ":" + minutes);
                        }
                    } else {
                        inputDate.setText(dateTime);
                    }

                    if (cd != null) {
                        cd.toShow(d);
                    }
                }
                dialog.dismiss();
            }
        });
        dateCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        init(datePicker);
        dateTitle.setText(dateTime + getWeek(dateTime));
        return dialog;
    }


    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }

    countDay cd;

    public interface countDay {
        void toShow(Long l);
    }

    /**
     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime 初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();
        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
        String date = spliteString(initDateTime, "-", "index", "front"); // 日期
        String time = spliteString(initDateTime, "-", "index", "back"); // 时间

        String yearStr = spliteString(date, "-", "index", "front"); // 年份
        String monthAndDay = spliteString(date, "-", "index", "back"); // 月日

        String monthStr = spliteString(monthAndDay, "-", "index", "front"); // 月
        String dayStr = spliteString(monthAndDay, "-", "index", "back"); // 日

        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();

        calendar.set(currentYear, currentMonth, currentDay);
        return calendar;
    }

    /**
     * 截取子串
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }

    //获取时间为周几
    private String getWeek(String time) {
        String Week = " 周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//也可将此值当参数传进来
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                Week += "日";
                break;
            case 2:
                Week += "一";
                break;
            case 3:
                Week += "二";
                break;
            case 4:
                Week += "三";
                break;
            case 5:
                Week += "四";
                break;
            case 6:
                Week += "五";
                break;
            case 7:
                Week += "六";
                break;
            default:
                break;
        }
        return Week;
    }


}