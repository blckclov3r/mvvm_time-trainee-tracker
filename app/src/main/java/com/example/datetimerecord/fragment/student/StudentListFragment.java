package com.example.datetimerecord.fragment.student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.StudentRecyclerAdapter;
import com.example.datetimerecord.model.AppLog;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.example.datetimerecord.viewmodel.LogViewModel;
import com.example.datetimerecord.viewmodel.StudentViewModel;

import java.text.SimpleDateFormat;
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

public class StudentListFragment extends Fragment implements StudentRecyclerAdapter.OnStudentClickListener {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentListFragment";

    private RecyclerView mRecyclerView;
    private StudentRecyclerAdapter mAdapter;
    private StudentViewModel mStudentViewModel;
    private SearchView searchView;
    private LogViewModel mLogViewModel;

    public StudentListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_list_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.student_recyclerView);
        searchView = view.findViewById(R.id.student_searchView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new StudentRecyclerAdapter();
        mAdapter.setOnStudentClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mStudentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

        return view;
    }

    private void Toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStudentViewModel.getmAllStudents().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                if (students.size() > 0) {
                    mAdapter.setmStudentList(students);
                } else {
                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Empty")
                            .setContentText("Student list is empty")
                            .show();
                }
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    new SweetAlertDialog(Objects.requireNonNull(getActivity()), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText(mAdapter.getNoteAt(position).getName())
                            .setContentText("Are you sure, you want to delete this student?")
                            .setConfirmText("Yes")
                            .setCustomImage(R.drawable.user)
                            .setCancelText("No")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    mStudentViewModel.delete(mAdapter.getNoteAt(position));
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                                    String dateFormat = simpleDateFormat.format(new Date());
                                    String name = mAdapter.getNoteAt(position).getName();
                                    mLogViewModel.insert(new AppLog("Student successfully deleted, name: " + name, dateFormat));

                                }
                            })
                            .show();
                }
                mAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(mRecyclerView);

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
                    mStudentViewModel.setSearch(newText).observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
                        @Override
                        public void onChanged(List<Student> students) {
                            if (students.size() > 0) {
                                mAdapter.setmStudentList(students);
                            }
                        }
                    });
                } else {
                    mStudentViewModel.getmAllStudents().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
                        @Override
                        public void onChanged(List<Student> students) {
                            if (students.size() > 0) {
                                mAdapter.setmStudentList(students);
                            }
                        }
                    });
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(Student student) {
        if (listener != null) {
            listener.onClicklist(student);
        }
    }

    @Override
    public void onLongClick(Student student) {
        if (listener != null) {
            listener.onLongClick(student);
        }
    }

    private OnStudentClickListListener listener;

    public interface OnStudentClickListListener {
        void onClicklist(Student student);

        void onLongClick(Student student);
    }


    public void setOnStudentClickListListener(OnStudentClickListListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStudentClickListListener) {
            listener = (OnStudentClickListListener) getActivity();
        } else {
            throw new RuntimeException(context.toString() + " must implement OnStudentClickListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (listener != null) {
            listener = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView = null;
        mAdapter = null;
        mStudentViewModel = null;
        searchView = null;
    }
}
