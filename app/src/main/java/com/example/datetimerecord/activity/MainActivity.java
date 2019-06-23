package com.example.datetimerecord.activity;

import android.os.Bundle;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.CourseRecyclerAdapter;
import com.example.datetimerecord.fragment.AddCourseFragment;
import com.example.datetimerecord.fragment.AddStudentFragment;
import com.example.datetimerecord.fragment.CourseInfoFragment;
import com.example.datetimerecord.fragment.CourseListFragment;
import com.example.datetimerecord.fragment.HomeFragment;
import com.example.datetimerecord.fragment.StudentListFragment;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , CourseListFragment.OnCourseListFragmentListener {

    private FragmentManager mFragmentManager;
    private CourseViewModel mCourseViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mFragmentManager = getSupportFragmentManager();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_frameLayout,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        mCourseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mCourseViewModel.getAllCourse().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                new CourseRecyclerAdapter().setCourseList(courses);
            }
        });

        //course list listener
        CourseListFragment courseListFragment = new CourseListFragment();
        courseListFragment.setOnCourseListFragmentListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                mFragmentManager.beginTransaction().replace(R.id.main_frameLayout,new HomeFragment()).commit();
                break;
            case R.id.nav_add:
                mFragmentManager.beginTransaction().replace(R.id.main_frameLayout,new AddStudentFragment()).commit();
                break;
            case R.id.nav_student_list:
                mFragmentManager.beginTransaction().replace(R.id.main_frameLayout,new StudentListFragment()).commit();
                break;
            case R.id.nav_course_list:
                mFragmentManager.beginTransaction().replace(R.id.main_frameLayout,new CourseListFragment()).commit();
                break;
            case R.id.nav_add_course:
                mFragmentManager.beginTransaction().replace(R.id.main_frameLayout,new AddCourseFragment()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void OnCourseListFragment(Course course) {
        CourseInfoFragment fragment = CourseInfoFragment.newInstance(course);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frameLayout,fragment)
                .commit();
    }
}
