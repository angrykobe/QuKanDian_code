package com.zhangku.qukandian.widght.datepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.observer.ExitActivityObserver;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuzuoning on 16/8/12.
 * 生日日期控件组合
 */
public class MyDatePicker extends LinearLayout implements NumberPicker.OnValueChangeListener, ExitActivityObserver.ExitActivityObserverAction {

    private NumberPicker mYearPicker;//年
    private NumberPicker mMonthPicker;//月
    private NumberPicker mDayOfMonthPicker;//日

    private Calendar mCalendar;//日期类

    private OnDateChangedListener mOnDateChangedListener;

    private LayoutInflater mLayoutInflater;

    public MyDatePicker(Context context) {
        this(context, null);
    }

    public MyDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
    }

    private void init() {
        mLayoutInflater.inflate(R.layout.date_picker_layout, this, true);
        mYearPicker = (NumberPicker) findViewById(R.id.year_picker);
        mMonthPicker = (NumberPicker) findViewById(R.id.month_picker);
        mDayOfMonthPicker = (NumberPicker) findViewById(R.id.day_picker);

        mYearPicker.setOnValueChangeListener(this);
        mMonthPicker.setOnValueChangeListener(this);
        mDayOfMonthPicker.setOnValueChangeListener(this);

        if (!getResources().getConfiguration().locale.getCountry().equals("CN")
                && !getResources().getConfiguration().locale.getCountry().equals("TW")) {

            String[] monthNames = getResources().getStringArray(R.array.month_name);
            mMonthPicker.setCustomTextArray(monthNames);

        }
        mCalendar = Calendar.getInstance();
        setDate(mCalendar.getTime());
        ExitActivityObserver.getInst().addExitActivityObserverAction(getContext(),this);
    }

    public MyDatePicker setDate(Date date) {
        mCalendar.setTime(date);
        mDayOfMonthPicker.setEndNumber(mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        mYearPicker.setCurrentNumber(mCalendar.get(Calendar.YEAR));
        mMonthPicker.setCurrentNumber(mCalendar.get(Calendar.MONTH) + 1);
        mDayOfMonthPicker.setCurrentNumber(mCalendar.get(Calendar.DAY_OF_MONTH));

        return this;
    }

    @Override
    public void onValueChange(final NumberPicker picker, final int oldVal, final int newVal) {

        if (picker == mYearPicker) {
            int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
            mCalendar.set(newVal, mCalendar.get(Calendar.MONTH), 1);
            int lastDayOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (dayOfMonth > lastDayOfMonth) {
                dayOfMonth = lastDayOfMonth;
            }
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mDayOfMonthPicker.setEndNumber(lastDayOfMonth);
        } else if (picker == mMonthPicker) {
            int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
            mCalendar.set(mCalendar.get(Calendar.YEAR), newVal - 1, 1);
            int lastDayOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (dayOfMonth > lastDayOfMonth) {
                dayOfMonth = lastDayOfMonth;
            }
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mDayOfMonthPicker.setEndNumber(lastDayOfMonth);
        } else if (picker == mDayOfMonthPicker) {
            mCalendar.set(Calendar.DAY_OF_MONTH, newVal);
        }

        notifyDateChanged();
    }

    /**
     * The callback used to indicate the user changes\d the date.
     */
    public interface OnDateChangedListener {

        /**
         * Called upon a date change.
         *
         * @param view        The view associated with this listener.
         * @param year        The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *                    with {@link Calendar}.
         * @param dayOfMonth  The day of the month that was set.
         */
        void onDateChanged(MyDatePicker view, int year, int monthOfYear, int dayOfMonth);
    }

    public MyDatePicker setOnDateChangedListener(OnDateChangedListener l) {
        mOnDateChangedListener = l;
        return this;
    }

    private void notifyDateChanged() {
        if (mOnDateChangedListener != null) {
            mOnDateChangedListener.onDateChanged(this, getYear(), getMonth(), getDayOfMonth());
        }
    }

    public int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    public int getDayOfMonth() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public MyDatePicker setSoundEffect(Sound sound) {
        mYearPicker.setSoundEffect(sound);
        mMonthPicker.setSoundEffect(sound);
        mDayOfMonthPicker.setSoundEffect(sound);
        return this;
    }

    @Override
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        super.setSoundEffectsEnabled(soundEffectsEnabled);
        mYearPicker.setSoundEffectsEnabled(soundEffectsEnabled);
        mMonthPicker.setSoundEffectsEnabled(soundEffectsEnabled);
        mDayOfMonthPicker.setSoundEffectsEnabled(soundEffectsEnabled);
    }

    public MyDatePicker setRowNumber(int rowNumber) {
        mYearPicker.setRowNumber(rowNumber);
        mMonthPicker.setRowNumber(rowNumber);
        mDayOfMonthPicker.setRowNumber(rowNumber);
        return this;
    }

    public MyDatePicker setCurrentDate(int year, int month, int day) {
        mYearPicker.setCurrentNumber(year);
        mMonthPicker.setCurrentNumber(month);
        mDayOfMonthPicker.setCurrentNumber(day);
        setCurrentMaxNumber(year,month,day);
        mCalendar.set(year,month-1,day);
        return this;
    }

    private void setCurrentMaxNumber(int year, int month, int day){
        String date = CommonHelper.formatTime(System.currentTimeMillis()/1000,true);
        int mYear = Integer.valueOf(date.substring(0,4));
        int mMonth = Integer.valueOf(date.substring(5,7));
        int mDay = Integer.valueOf(date.substring(8,10));
        mYearPicker.setEndNumber(mYear);
        if (year == mYear) {
            mMonthPicker.setEndNumber(mMonth);
            if(month == mMonth){
                mDayOfMonthPicker.setEndNumber(mDay);
            }
        }else {
            mMonthPicker.setEndNumber(12);
        }
    }

    public MyDatePicker setMaxNumber(int year, int month, int day) {
        mYearPicker.setEndNumber(year);
        mMonthPicker.setEndNumber(month);
        mDayOfMonthPicker.setEndNumber(day);
        return this;
    }

    public MyDatePicker setMaxYear(int year) {
        mYearPicker.setEndNumber(year);
        return this;
    }

    public MyDatePicker setMaxMonth(int month) {
        mMonthPicker.setEndNumber(month);
        return this;
    }

    public MyDatePicker setMaxDay(int day) {
        mDayOfMonthPicker.setEndNumber(day);
        return this;
    }

    public MyDatePicker setTextSize(float textSize) {
        mYearPicker.setTextSize(textSize);
        mMonthPicker.setTextSize(textSize);
        mDayOfMonthPicker.setTextSize(textSize);
        return this;
    }

    public MyDatePicker setFlagTextSize(float textSize) {
        mYearPicker.setFlagTextSize(textSize);
        mMonthPicker.setFlagTextSize(textSize);
        mDayOfMonthPicker.setFlagTextSize(textSize);
        return this;
    }

    public MyDatePicker setTextColor(int color) {
        mYearPicker.setTextColor(color);
        mMonthPicker.setTextColor(color);
        mDayOfMonthPicker.setTextColor(color);
        return this;
    }

    public MyDatePicker setFlagTextColor(int color) {
        mYearPicker.setFlagTextColor(color);
        mMonthPicker.setFlagTextColor(color);
        mDayOfMonthPicker.setFlagTextColor(color);
        return this;
    }

    public MyDatePicker setBackground(int color) {
        super.setBackgroundColor(color);
        mYearPicker.setBackground(color);
        mMonthPicker.setBackground(color);
        mDayOfMonthPicker.setBackground(color);
        return this;
    }

    public MyDatePicker setDividerLineColor(int color) {
        mYearPicker.setDividerLineColor(color);
        mMonthPicker.setDividerLineColor(color);
        mDayOfMonthPicker.setDividerLineColor(color);
        return this;
    }

    @Override
    public void onActivityDestory() {
        mYearPicker = null;
        mMonthPicker = null;
        mDayOfMonthPicker = null;
        mCalendar = null;
        mOnDateChangedListener = null;
    }
}
