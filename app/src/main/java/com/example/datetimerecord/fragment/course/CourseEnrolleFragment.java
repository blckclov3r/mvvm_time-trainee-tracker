package com.example.datetimerecord.fragment.course;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.StudentRecyclerAdapter;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseEnrolleFragment extends Fragment  {

    private static final String COURSENAME = "course";


    private StudentViewModel mStudentViewModel;
    private RecyclerView mRecyclerView;
    private StudentRecyclerAdapter mAdapter;
    private SearchView mSearchView;

    public CourseEnrolleFragment(){}

    public static CourseEnrolleFragment newInstance(String course){
        CourseEnrolleFragment fragment = new CourseEnrolleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(COURSENAME,course);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_list_fragment,container,false);
        mRecyclerView = view.findViewById(R.id.student_recyclerView);
        mSearchView = view.findViewById(R.id.student_searchView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new StudentRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            String course = getArguments().getString(COURSENAME);
            List<Student> studentList = mStudentViewModel.getStudentCourse(course);
            mAdapter.setmStudentList(studentList);
            Toast.makeText(getActivity(), course+" students", Toast.LENGTH_SHORT).show();
        }


        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setIconified(false);
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    mStudentViewModel.setSearch(newText).observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
                        @Override
                        public void onChanged(List<Student> students) {
                            if (students.size() > 0) {
                                mAdapter.setmStudentList(students);
                            }
                        }
                    });
                } else {
                    String course = Objects.requireNonNull(getArguments()).getString(COURSENAME);
                    List<Student> studentList = mStudentViewModel.getStudentCourse(course);
                    mAdapter.setmStudentList(studentList);
                }
                return false;
            }
        });
    }
}
