package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.timotiusek.musikonekteacher.CustomClass.Skill;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wilbe on 15/08/2017.
 */

public class SkillPickerAdapter extends BaseAdapter {

    private ArrayList<Skill> skills;
    private Context mContext;
    private LayoutInflater inflater;

    @BindView(R.id.skill_text_view)
    TextView skillText;

    public SkillPickerAdapter(ArrayList<Skill> skills, Context c) {
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
            convertView = inflater.inflate(R.layout.one_text_layout, parent, false);
            ButterKnife.bind(this, convertView);

            skillText.setText(skill.getInstrument());
        }
        return convertView;
    }
}
