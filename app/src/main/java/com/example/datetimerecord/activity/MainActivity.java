package com.example.datetimerecord.activity;

import android.os.Bundle;

import com.example.datetimerecord.R;
import com.example.datetimerecord.adapter.CourseRecyclerAdapter;
import com.example.datetimerecord.fragment.course.AddCourseFragment;
import com.example.datetimerecord.fragment.course.CourseDetailFragment;
import com.example.datetimerecord.fragment.course.CourseUpdateFragment;
import com.example.datetimerecord.fragment.student.AddStudentFragment;
import com.example.datetimerecord.fragment.course.CourseListFragment;
import com.example.datetimerecord.fragment.HomeFragment;
import com.example.datetimerecord.fragment.student.StudentListFragment;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.viewmodel.CourseViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CourseListFragment.OnCourseListFragmentListener {

    private FragmentManager mFragmentManager;
    private CourseViewModel mCourseViewModel;
    private Toolbar mToolbar;
    private Fragment mFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mFragmentManager = getSupportFragmentManager();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            mFragment = new HomeFragment();
            mToolbar.setTitle("Trainee Time Tracker");
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.main_frameLayout, mFragment).commit();
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

        switch (id) {
            case R.id.nav_home:
                mFragment = new HomeFragment();
                break;
            case R.id.nav_add:
                mFragment = new AddStudentFragment();
                break;
            case R.id.nav_student_list:
                mFragment = new StudentListFragment();
                break;
            case R.id.nav_course_list:
                mFragment = new CourseListFragment();
                break;
            case R.id.nav_add_course:
                mFragment = new AddCourseFragment();
                break;
            default:
                break;
        }
        if (mFragment != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.main_frameLayout, mFragment);
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void OnCourseListFragment(Course course) {
        mFragment = CourseDetailFragment.newInstance(course);
        if (mFragment != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.main_frameLayout, mFragment);
            ft.addToBackStack(null);
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                mFragmentManager.popBackStackImmediate();
            }
            ft.commit();
        }
    }

    @Override
    public void OnLongClickCourseListFragment(Course course) {
        mFragment = CourseUpdateFragment.newInstance(course);
        if (mFragment != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.main_frameLayout, mFragment);
            ft.addToBackStack(null);
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            ft.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCourseViewModel = null;
    }
}
