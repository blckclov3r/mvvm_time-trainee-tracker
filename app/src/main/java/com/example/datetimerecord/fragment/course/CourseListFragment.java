package com.example.datetimerecord.fragment.course;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.CourseRecyclerAdapter;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.persistence.CourseRepository;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CourseListFragment extends Fragment implements CourseRecyclerAdapter.OnCourseClickListener {

    private static final String TAG = "CourseListFragment";
    public static final String COMMON_TAG = "mAppLog";

    private RecyclerView mRecyclerView;
    private CourseRecyclerAdapter mCourseAdapter;
    private CourseViewModel mCourseViewModel;
    private OnCourseListFragmentListener mListFragmentListener;
    private StudentViewModel mStudentViewModel;

    private boolean mCheck = false;
    private List<Student> mStudents;
    private CourseRepository mCourseRepository;
    private SearchView searchView;
    public CourseListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment, container, false);
        Log.d(COMMON_TAG, TAG + " onCreateView invoked");

        mRecyclerView = view.findViewById(R.id.course_recyclerView);
        searchView = view.findViewById(R.id.course_searchView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCourseAdapter = new CourseRecyclerAdapter();
        mCourseAdapter.setOnCourseClickListener(this);
        mRecyclerView.setAdapter(mCourseAdapter);

        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        mCourseRepository = new CourseRepository(Objects.requireNonNull(getActivity()).getApplication());

        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if(courses.size() > 0) {
                    mCourseAdapter.setCourseList(courses);
                }

            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(COMMON_TAG, TAG + " onViewCreated invoked");

        mStudents = new ArrayList<>(mStudentViewModel.getStudent());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()) {
                    mCourseRepository.setSearch(newText).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                        @Override
                        public void onChanged(List<Course> courses) {
                            mCourseAdapter.setCourseList(courses);
                        }
                    });
                }else{
                    mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                        @Override
                        public void onChanged(List<Course> courses) {
                            if(courses.size() > 0) {
                                mCourseAdapter.setCourseList(courses);
                            }
                        }
                    });
                }
                return false;
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mCheck = false;
                int position = viewHolder.getAdapterPosition();
                if (position != -1) {
                    for (int i = 0; i < mStudents.size(); i++) {
                        Student student = mStudents.get(i);
                        if (student.getCourse().equals(mCourseAdapter.getCourseAt(position).getCourse())) {
                            mCheck = true;
                            break;
                        }
                    }

                    if (!mCheck) {
                        mCourseViewModel.delete(mCourseAdapter.getCourseAt(position));
                        Toast.makeText(getActivity(), "Course successfully deleted", Toast.LENGTH_SHORT).show();
                        Log.d(COMMON_TAG, TAG + " mCheck invoked: " + mCheck);
                    } else {
                        Toast.makeText(getActivity(), "Please delete all student who enrolled this course", Toast.LENGTH_SHORT).show();
                        Log.d(COMMON_TAG, TAG + " mCheck invoked: " + mCheck);
                        mCourseAdapter.notifyCourse();
                    }
                    mCheck = false;
                }
            }
        }).attachToRecyclerView(mRecyclerView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView = null;
        mCourseAdapter = null;
        mCourseViewModel = null;
        mListFragmentListener = null;
        mStudentViewModel = null;
    }


    @Override
    public void onCourseClick(Course course) {
        mListFragmentListener.OnCourseListFragment(course);
    }

    @Override
    public void onCourseLongClick(Course course) {
        mListFragmentListener.OnLongClickCourseListFragment(course);
    }

    public interface OnCourseListFragmentListener {
        void OnCourseListFragment(Course course);

        void OnLongClickCourseListFragment(Course course);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCourseListFragmentListener) {
            mListFragmentListener = (OnCourseListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnCourseListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(COMMON_TAG, TAG + " onDetach");
        if (mListFragmentListener != null) {
            mListFragmentListener = null;
        }
    }

    public void setOnCourseListFragmentListener(OnCourseListFragmentListener listener) {
        mListFragmentListener = listener;
    }
}
