package com.rtsoftbd.siddiqui.hellocar.customAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rtsoftbd.siddiqui.hellocar.R;
import com.rtsoftbd.siddiqui.hellocar.models.History;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-30.
 */

public class HistoryAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<History> historyList;

    public HistoryAdapter(Activity activity, List<History> historyList) {
        this.activity = activity;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = layoutInflater.inflate(R.layout.list_row, null);

        ViewHolder holder = new ViewHolder(convertView);
        History history = historyList.get(position);
        holder.ms_FromTextView.setText(history.getFrom_Area());
        holder.ms_ToTextView.setText(history.getTo_Area());
        holder.ms_DateTextView.setText(history.getPickup_Date());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.fromTextView)
        TextView ms_FromTextView;
        @BindView(R.id.toTextView)
        TextView ms_ToTextView;
        @BindView(R.id.dateTextView)
        TextView ms_DateTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
