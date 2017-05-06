package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TimotiusEk on 5/6/2017.
 */

public class ShowSkillAdapter extends BaseAdapter {
    private ArrayList<Skill> skills;
    private Context mContext;
    private LayoutInflater inflater;
    @BindView(R.id.instrument)
    TextView instrument;
    @BindView(R.id.instrument_detail)
    TextView instrumentDetail;

    public ShowSkillAdapter(ArrayList<Skill> skills, Context c) {
        this.skills = skills;
        mContext = c;
    }

    @Override
    public int getCount() {
        return skills.size();
    }

    @Override
    public Object getItem(int position) {
        return skills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Skill skill = (Skill) getItem(position);
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout_show_skill, parent, false);
            ButterKnife.bind(this, convertView);

            instrument.setText(skill.getInstrument());
            instrumentDetail.setText(skill.getDetail());
        }
        return convertView;
    }
}
