package com.example.datetimerecord.adapter;

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

    private List<Student> mStudentList = new ArrayList<>();

    public void setmStudentList(List<Student> mStudentList){
        this.mStudentList = mStudentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item,parent,false);
        return new ViewHolder(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView studentId_tv,name_tv,course_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentId_tv = itemView.findViewById(R.id.student_id_textView);
            name_tv = itemView.findViewById(R.id.studentName_textView);
            course_tv = itemView.findViewById(R.id.course_textView);
        }

        public void bind(Student student){
            studentId_tv.setText(String.valueOf(student.getT_id()));
            name_tv.setText(student.getName());
            course_tv.setText(student.getCourse());
        }
    }
}
