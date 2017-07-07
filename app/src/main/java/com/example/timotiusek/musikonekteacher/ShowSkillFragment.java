package com.example.timotiusek.musikonekteacher;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Skill;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowSkillFragment extends Fragment {
    @BindView(R.id.show_skill_list_view)
    ListView showSkillListView;
    ArrayList<Skill> skills;
    ShowSkillAdapter showSkillAdapter;
    MainActivity ma;

    public ShowSkillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_skill, container, false);
        ButterKnife.bind(this,view);
        ma = (MainActivity) getActivity();
        ma.setTitle("Keahlian");

        skills = new ArrayList<>();
        skills.add(new Skill("Gitar", "Gitar Akustik"));
        skills.add(new Skill("Piano", "Piano Pop"));

        showSkillAdapter = new ShowSkillAdapter(skills, getActivity());
        showSkillListView.setAdapter(showSkillAdapter);
        // Inflate the layout for this fragment
        return view;
    }
    @OnClick(R.id.add_skill_fab)
    void addSkill(){
        startActivity(new Intent(getActivity(), AddSkillActivity.class));
        /**
         * Todo : add add_skill_fab behavior
         */
    }

}
