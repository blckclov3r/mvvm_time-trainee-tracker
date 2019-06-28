package com.example.datetimerecord.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Student;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StudentRecyclerAdapter extends ListAdapter<Student,StudentRecyclerAdapter.ViewHolder> {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "StudentRecyclerAdapter";

    private OnStudentClickListener mListener;

    public StudentRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getT_id() == newItem.getT_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getCourse().equals(newItem.getCourse()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) && oldItem.getContact().equals(newItem.getContact()) &&
                    oldItem.getAddress().equals(newItem.getAddress()) && oldItem.getTimestamp().equals(newItem.getTimestamp()) &&
                    oldItem.getRemaining() == newItem.getRemaining() && oldItem.getTimein_hour() == newItem.getTimein_hour() &&
                    oldItem.getTimein_minute() == newItem.getTimein_minute() && oldItem.getTimeout_hour() == newItem.getTimeout_hour() &&
                    oldItem.getTimeout_minute() == newItem.getTimeout_minute() && oldItem.getElapse_minute() == newItem.getElapse_minute();
        }
    };


    public interface OnStudentClickListener {
        void onClick(Student student);

        void onLongClick(Student student);
    }

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        mListener = listener;
    }


    public Student getNoteAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = getItem(position);
        holder.bind(student);
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
            Student student = getItem(position);
            if(listener!=null && position != RecyclerView.NO_POSITION) {
                listener.onClick(student);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            Student student = getItem(position);
            if(listener != null && position != RecyclerView.NO_POSITION) {
                listener.onLongClick(student);
            }
            return true;
        }
    }
}
