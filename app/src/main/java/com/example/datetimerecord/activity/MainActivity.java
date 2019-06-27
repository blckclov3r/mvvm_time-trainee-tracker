package com.example.datetimerecord.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.datetimerecord.R;
import com.example.datetimerecord.activity.dialog.DeleteCustomDialog;
import com.example.datetimerecord.fragment.HomeFragment;
import com.example.datetimerecord.fragment.course.CourseAddFragment;
import com.example.datetimerecord.fragment.course.CourseDetailFragment;
import com.example.datetimerecord.fragment.course.CourseListFragment;
import com.example.datetimerecord.fragment.course.CourseUpdateFragment;
import com.example.datetimerecord.fragment.log.LogFragment;
import com.example.datetimerecord.fragment.student.StudentAddFragment;
import com.example.datetimerecord.fragment.student.StudentDetailFragment;
import com.example.datetimerecord.fragment.student.StudentListFragment;
import com.example.datetimerecord.fragment.student.StudentTimeUpdateFragment;
import com.example.datetimerecord.fragment.student.StudentUpdateFragment;
import com.example.datetimerecord.model.Course;
import com.example.datetimerecord.model.Student;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CourseListFragment.OnCourseListFragmentListener,
        StudentListFragment.OnStudentClickListListener, StudentDetailFragment.setOnUpdateListener {

    //vars
    private FragmentManager mFragmentManager;
    private Fragment mFragment = null;

    //component

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

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
        navigationView.setItemIconTintList(null);

        //course list listener
        CourseListFragment courseListFragment = new CourseListFragment();
        StudentListFragment studentListFragment = new StudentListFragment();
        courseListFragment.setOnCourseListFragmentListener(this);
        studentListFragment.setOnStudentClickListListener(this);


        if (savedInstanceState == null) {
            mFragment = new HomeFragment();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.main_frameLayout, mFragment).commit();
            navigationView.setCheckedItem(R.id.nav_home);

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mFragmentManager.getBackStackEntryCount() > 0 && mFragment != null) {
                super.onBackPressed();
            }
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
        int id = item.getItemId();
        if (id == R.id.delete_menu) {
            DeleteCustomDialog dialog = new DeleteCustomDialog();
             dialog.show(getSupportFragmentManager(),null);
            return true;
        }
        if(id == R.id.log_menu){
            if (mFragment != null) {
                for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                    mFragmentManager.popBackStackImmediate();
                }
            }
            mFragment = new LogFragment();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.main_frameLayout, mFragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home: {
                mFragment = new HomeFragment();
            }
            break;
            case R.id.nav_add: {
                mFragment = new StudentAddFragment();
            }
            break;
            case R.id.nav_student_list: {
                mFragment = new StudentListFragment();
            }
            break;
            case R.id.nav_course_list: {
                mFragment = new CourseListFragment();
            }
            break;
            case R.id.nav_add_course: {
                mFragment = new CourseAddFragment();
            }
            break;
            case R.id.nav_share: {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        ApplicationInfo app = getApplicationContext().getApplicationInfo();
                        String filePath = app.sourceDir;
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                        startActivity(Intent.createChooser(intent, "Share app via"));
                    }
                });
            }
            break;
        }
        if (mFragment != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.replace(R.id.main_frameLayout, mFragment);
                    for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                        mFragmentManager.popBackStackImmediate();
                    }
                    ft.commitNow();
                }
            }, 250);
        }
        hideSoftKeyboard();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //COURSE LIST
    @Override
    public void OnCourseListFragment(Course course) {
        if (mFragment != null) {
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                mFragmentManager.popBackStackImmediate();
            }
        }
        mFragment = CourseDetailFragment.newInstance(course);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_frameLayout, mFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void OnLongClickCourseListFragment(Course course) {
        if (mFragment != null) {
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                mFragmentManager.popBackStackImmediate();
            }
        }
        mFragment = CourseUpdateFragment.newInstance(course);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_frameLayout, mFragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    //STUDENT LIST
    @Override
    public void onClicklist(Student student) {
        if (mFragment != null) {
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                mFragmentManager.popBackStackImmediate();
            }
        }
        mFragment = StudentDetailFragment.newInstance(student);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_frameLayout, mFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onLongClick(Student student) {
        if (mFragment != null) {
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                mFragmentManager.popBackStackImmediate();
            }
        }
        mFragment = StudentUpdateFragment.newInstance(student);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_frameLayout, mFragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentManager = null;
        mFragment = null;
    }

    @Override
    public void onStudentDetailFragment(Student student) {
        mFragment = StudentTimeUpdateFragment.newInstance(student);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_frameLayout, mFragment);
        ft.addToBackStack(null);
        if (mFragment != null) {
            for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
                mFragmentManager.popBackStackImmediate();
            }
        }
        ft.commit();
    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
