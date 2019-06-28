package com.example.datetimerecord.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Student;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.ViewHolder> {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentRecyclerAdapter";

    private List<Student> mStudentList = new ArrayList<>();
    private OnStudentClickListener mListener;

    public interface OnStudentClickListener {
        void onClick(Student student);

        void onLongClick(Student student);
    }

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        mListener = listener;
    }

    public void setmStudentList(List<Student> mStudentList) {
        this.mStudentList = mStudentList;
        notifyDataSetChanged();
    }

    public Student getNoteAt(int position) {
        return mStudentList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView studentId_tv, name_tv, course_tv;
        private OnStudentClickListener listener;

        public ViewHolder(@NonNull View itemView, OnStudentClickListener listener) {
            super(itemView);
            this.listener = listener;
            studentId_tv = itemView.findViewById(R.id.student_id_textView);
            name_tv = itemView.findViewById(R.id.studentName_textView);
            course_tv = itemView.findViewById(R.id.course_textView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Student student) {
            studentId_tv.setText(String.valueOf(student.getT_id()));
            name_tv.setText(student.getName());
            course_tv.setText(student.getCourse());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Student student = mStudentList.get(position);
            if(listener!=null && position != RecyclerView.NO_POSITION) {
                listener.onClick(student);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            Student student = mStudentList.get(position);
            if(listener != null && position != RecyclerView.NO_POSITION) {
                listener.onLongClick(student);
            }
            return true;
        }
    }
}
