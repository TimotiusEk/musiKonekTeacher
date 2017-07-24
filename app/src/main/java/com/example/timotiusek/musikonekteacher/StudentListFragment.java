package com.example.timotiusek.musikonekteacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timotiusek.musikonekteacher.CustomClass.Student;
import com.example.timotiusek.musikonekteacher.CustomClass.StudentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends Fragment {
    String status = "";
    ArrayList<Student> notFilteredStudents;
    ArrayList<Student> filteredStudents;
    @BindView(R.id.student_list_list_view)
    ListView listView;
    MainActivity ma;

    public StudentListFragment() {
        // Required empty public constructor
    }

    public StudentListFragment(String status){
        this.status = status;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);
        ButterKnife.bind(this, v);
        ma = (MainActivity) getActivity();

        notFilteredStudents = new ArrayList<>();
        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Piano Pemula", "Paket 5 kali pertemuan","Joe Biden", "EXISTING"));
        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Gitar Pemula", "Paket 2 kali pertemuan","Joe Allen", "EXISTING"));
        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Biola Pemula", "Paket 1 kali pertemuan","Joe Cole", "EXISTING"));
        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Vokal Pemula", "Paket 4 kali pertemuan","Joe shua Suherman", "GRADUATED"));
        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Piano Intermediate", "Paket 12 kali pertemuan","Joe unochi", "GRADUATED"));
        notFilteredStudents.add(new Student(R.drawable.avatar, "Kursus Angkung Pemula", "Paket 3 kali pertemuan","Joealan Kacang", "GRADUATED"));
        filteredStudents = new ArrayList<>();

        if(status.equals("EXISTING")){
            for(Student notFilteredStudent : notFilteredStudents){
                if(notFilteredStudent.getStatus().equals("EXISTING")){
                    filteredStudents.add(notFilteredStudent);
                }
            }

        } else if(status.equals("GRADUATED")){
            for(Student notFilteredStudent : notFilteredStudents){
                if(notFilteredStudent.getStatus().equals("GRADUATED")){
                    filteredStudents.add(notFilteredStudent);
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * todo : send data
                 */
                ma.changeFragment(new StudentDetailFragment());
            }
        });

        StudentAdapter studentAdapter = new StudentAdapter(filteredStudents, getActivity());
        listView.setAdapter(studentAdapter);
        // Inflate the layout for this fragment
        return v;
    }

}
