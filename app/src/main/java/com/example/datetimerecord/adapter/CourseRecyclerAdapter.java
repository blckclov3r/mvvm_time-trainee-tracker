package com.example.datetimerecord.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {
    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "CourseRecyclerAdapter";
    private List<Course> courseList = new ArrayList<>();
    private OnCourseClickListener listener;

    public void setCourseList(List<Course> courseList){
        this.courseList = courseList;
        notifyDataSetChanged();
    }


    public Course getCourseAt(int pos){
        return courseList.get(pos);
    }

    public void notifyCourse(){
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courseList.get(position);
        int id = course.getC_id();
        String name = course.getCourse();
        int time = course.getCourse_time();
        String description = course.getDescription();
        holder.course_tv.setText(name);
        holder.time_tv.setText(String.valueOf(time));
        holder.description_tv.setText(description);
        holder.id_tv.setText(String.valueOf(id));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView course_tv,time_tv,description_tv,id_tv;
        private OnCourseClickListener listener;
        public ViewHolder(@NonNull View itemView,OnCourseClickListener listener) {
            super(itemView);
            this.listener = listener;
            id_tv = itemView.findViewById(R.id.course_id_textView);
            course_tv = itemView.findViewById(R.id.course_textView);
            time_tv = itemView.findViewById(R.id.course_time_textView);
            description_tv = itemView.findViewById(R.id.description_textView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(COMMON_TAG,TAG+" onClick");
            int position = getAdapterPosition();
            if(listener!=null ) {
                Course course = courseList.get(position);
                listener.onCourseClick(course);
            }else{
                Log.d(COMMON_TAG,TAG+" something went wrong");
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(COMMON_TAG,TAG+" onLongClick");
            int position = getAdapterPosition();
            if(listener!=null ){
                Course course = courseList.get(position);
                listener.onCourseLongClick(course);
                return true;
            }else{
                Log.d(COMMON_TAG,TAG+" something went wrong");
            }
           return false;
        }
    }

    public interface OnCourseClickListener{
        void onCourseClick(Course course);
        void onCourseLongClick(Course course);
    }
    public void setOnCourseClickListener(OnCourseClickListener listener){
        this.listener = listener;
    }

}
