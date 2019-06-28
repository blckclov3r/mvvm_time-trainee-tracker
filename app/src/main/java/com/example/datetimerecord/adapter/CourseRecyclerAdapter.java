package com.example.datetimerecord.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.datetimerecord.R;
import com.example.datetimerecord.model.Course;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CourseRecyclerAdapter extends ListAdapter<Course,CourseRecyclerAdapter.ViewHolder> {

    private static final String COMMON_TAG = "mAppLog";
    private static final String TAG = "CourseRecyclerAdapter";
    private OnCourseClickListener listener;

    public CourseRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<Course> DIFF_CALLBACK = new DiffUtil.ItemCallback<Course>() {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getC_id() == newItem.getC_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getCourse().equals(newItem.getCourse()) && oldItem.getCourse_time() == newItem.getCourse_time() &&
                    oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getTimein_hour() == newItem.getTimein_hour() &&
                    oldItem.getTimein_minute() == newItem.getTimein_minute() && oldItem.getTimeout_hour() == newItem.getTimeout_hour() &&
                    oldItem.getTimeout_minute() == newItem.getTimeout_minute();
        }
    };

    public Course getCourseAt(int pos){
        return getItem(pos);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = getItem(position);
        int id = course.getC_id();
        String name = course.getCourse();
        int time = course.getCourse_time();
        String description = course.getDescription();
        holder.course_tv.setText(name);
        holder.time_tv.setText(String.valueOf(time));
        holder.description_tv.setText(description);
        holder.id_tv.setText(String.valueOf(id));
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
            if(listener!=null && position != RecyclerView.NO_POSITION) {
                Course course = getItem(position);
                listener.onCourseClick(course);
            }else{
                Log.d(COMMON_TAG,TAG+" something went wrong");
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(COMMON_TAG,TAG+" onLongClick");
            int position = getAdapterPosition();
            if(listener!=null && position != RecyclerView.NO_POSITION){
                Course course = getItem(position);
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
