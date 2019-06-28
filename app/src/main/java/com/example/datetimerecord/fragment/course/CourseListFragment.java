package com.example.datetimerecord.fragment.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.CourseRecyclerAdapter;
import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.persistence.repository.CourseRepository;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.LogViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CourseListFragment extends Fragment implements CourseRecyclerAdapter.OnCourseClickListener {

    private static final String TAG = "CourseListFragment";
    public static final String COMMON_TAG = "mAppLog";

    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private CourseRecyclerAdapter mCourseAdapter;
    private CourseViewModel mCourseViewModel;
    private OnCourseListFragmentListener mListFragmentListener;
    private StudentViewModel mStudentViewModel;
    private LogViewModel mLogViewModel;
    private boolean mCheck = false;
    private List<Student> mStudents;
    private CourseRepository mCourseRepository;


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
        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

        mCourseRepository = new CourseRepository(Objects.requireNonNull(getActivity()).getApplication());

        mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if (courses.size() > 0) {
                    mCourseAdapter.setCourseList(courses);
                } else {
                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("Empty")
                            .setCustomImage(R.drawable.books_xml)
                            .setContentText("Course list is empty")
                            .show();
                }
                Log.d(COMMON_TAG, TAG + " onChanged");

            }
        });

        return view;
    }

    private void Toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(COMMON_TAG, TAG + " onViewCreated invoked");

        mStudents = new ArrayList<>(mStudentViewModel.getStudent());
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    mCourseRepository.setSearch(newText).observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                        @Override
                        public void onChanged(List<Course> courses) {
                            mCourseAdapter.setCourseList(courses);
                        }
                    });
                    Log.d(COMMON_TAG, TAG + " onChanged: if, onQueryTextChange");
                } else {
                    mCourseViewModel.getAllCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
                        @Override
                        public void onChanged(List<Course> courses) {
                            if (courses.size() > 0) {
                                mCourseAdapter.setCourseList(courses);
                            }
                        }
                    });
                    Log.d(COMMON_TAG, TAG + " onChanged: else, onQueryTextChange");
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
                final int position = viewHolder.getAdapterPosition();


                new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText(mCourseAdapter.getCourseAt(position).getCourse())
                        .setContentText("Are you sure, you want to delete this course?")
                        .setConfirmText("Yes")
                        .setCustomImage(R.drawable.books_xml)
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
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
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                                        String dateFormat = simpleDateFormat.format(new Date());
                                        mLogViewModel.insert(new AppLog("Course successfully deleted, name: "+mCourseAdapter.getCourseAt(position).getCourse(),dateFormat));
                                    } else {
                                        new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Error")
                                                .setContentText("Please delete all student whose enrolled this course")
                                                .show();
                                        Log.d(COMMON_TAG, TAG + " mCheck invoked: " + mCheck);
                                    }
                                    mCheck = false;
                                }


                            }
                        })
                        .show();
                    mCourseAdapter.notifyDataSetChanged();

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
        searchView = null;
        mCourseRepository = null;
        mStudents = null;
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
