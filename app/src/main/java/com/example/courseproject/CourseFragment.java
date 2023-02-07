package com.example.courseproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.courseproject.database.CourseBaseHelper;

import java.util.ArrayList;

public class CourseFragment extends Fragment {
    // we put all the widgets in this class onstead of having them in main activity since we use id of fragment as reference
    private TextView courseText_view;
    private TextView courseTotalFeesText_view;
    private TextView courseList_text_view;
    private Button courseTotalFeesButton;
    private Button courseNextButton;
    private Button courseDetailButton;
    private Button courseStartServiceButton;
    private Button courseStopServiceButton;
    private Button courseVisitButton;

    private Context context;
    private ArrayList<Course> courseModalArrayList;

    private int currentIndex = 0;
    private static final String TAG = "Course Project";
    private static final String KEY_INDEX = "index";
    //private static final String EXTRA_COURSE_N0="com.example.course.course_no";
    Course[] all_courses;

    @Override
    //to instantiate the object
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment will receive call back from fragmnetmanager to fire oncreateoptionmenu
        setHasOptionsMenu(true);

        Course.credits = 3;

        Course courseRecord1 = new Course("MIS 101", "Intro.to Info. Systems", 140);
        Course courseRecord2 = new Course("MIS 301", "Systems Analysis", 35);
        Course courseRecord3 = new Course("MIS 441", "Database Management", 12);
        Course courseRecord4 = new Course("CS 155", "Programming in C++", 90);
        Course courseRecord5 = new Course("MIS 451", "Web_Based Systems", 30);
        Course courseRecord6 = new Course("MIS 551", "Advanced Web", 30);
        Course courseRecord7 = new Course("MIS 651", "Advanced Java", 30);

        // data structure Array
        all_courses = new Course[]{courseRecord1, courseRecord2, courseRecord3, courseRecord4, courseRecord5, courseRecord6, courseRecord7};
        context = getContext().getApplicationContext();
        CourseBaseHelper courseBaseHelper = new CourseBaseHelper(context);
        courseBaseHelper.addNewCourse(courseRecord1);
        courseBaseHelper.addNewCourse(courseRecord2);
        courseBaseHelper.addNewCourse(courseRecord3);
        courseBaseHelper.addNewCourse(courseRecord4);
        courseBaseHelper.addNewCourse(courseRecord5);
        courseBaseHelper.addNewCourse(courseRecord6);
        courseBaseHelper.addNewCourse(courseRecord7);

        //update any record
        courseRecord4.setCourse_name("maths ");
        courseBaseHelper.updateCourse(courseRecord4);


    }

    @Nullable
    @Override
    //to inflate
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course, container, false);

        // get the view of course toolbar
        Toolbar courseToolbar = (Toolbar) v.findViewById(R.id.coursetoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(courseToolbar);

        // get the courseText_View


        courseText_view = (TextView) v.findViewById(R.id.course_text_view);
        courseText_view.setText("Course :" + all_courses[currentIndex].getCourse_no() + "" + all_courses[currentIndex].getCourse_name());

        courseTotalFeesButton = (Button) v.findViewById(R.id.coursetotalfees_button);
        courseTotalFeesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the view of courseTotalFessText_View
                courseTotalFeesText_view = (TextView) v.findViewById(R.id.coursetotalfees_text_view);
                courseTotalFeesText_view.setText("Total Course Fees is :" + all_courses[currentIndex].calculateCostTotalFees() + "");
                Toast.makeText(getActivity(), "Total Course Fess is " + all_courses[currentIndex].calculateCostTotalFees(), Toast.LENGTH_SHORT).show();
            }
        });
        courseNextButton = (Button) v.findViewById(R.id.coursenext_button);
        courseNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % all_courses.length;
                courseText_view.setText("Course :" + all_courses[currentIndex].getCourse_no() + "" + all_courses[currentIndex].getCourse_name());
                courseTotalFeesText_view = (TextView) v.findViewById(R.id.coursetotalfees_text_view);
                courseTotalFeesText_view.setText("");
            }
        });


        courseDetailButton = (Button) v.findViewById(R.id.course_detail_button);
        courseDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //read table rows calling readCourses()
                courseModalArrayList = new CourseBaseHelper(context).readCourses();

                // get the view list courses

                courseList_text_view = (TextView) v.findViewById(R.id.courseList_text_view);
                String allcourses = "";

                //READ ALL CONTENT OF LIST
                for (Course course : courseModalArrayList) {
                    allcourses = allcourses + course.toString();
                }
                //set the content of allcourses to the courseList_text_view

                courseList_text_view.setText(allcourses);


            }
        });

        //get resource view of courseStrtserviveButtuon

        courseStartServiceButton = (Button) v.findViewById(R.id.course_startService_button);
        courseStartServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting service media palyer service
                getActivity().startService(new Intent(getActivity(), CourseService.class));

            }
        });

        courseStopServiceButton = (Button) v.findViewById(R.id.course_stopService_button);
        courseStopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stoping service media palyer service
                getActivity().stopService(new Intent(getActivity(), CourseService.class));
            }
        });

       //get the view of coursevisit
        courseVisitButton=(Button) v.findViewById(R.id.course_visit_button);
        courseVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //use implicit intent

                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.vaniercollege.qc.ca/"));
                startActivity(intent);
            }
        });
        return v;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //INFLATE THE MENU DEFINED IN MENU COURSE XML
        inflater.inflate(R.menu.menu_course, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.courseoptionitem1:
                Toast.makeText(getActivity(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                // start new activity
                intent=new Intent(getActivity(),CourseMapsActivity.class);
                startActivity(intent);

                return true;
            case R.id.courseoptionitem2:
                intent=new Intent(getActivity(),CourseContentActivity.class);
                startActivity(intent);
                return true;
            case R.id.courseoptionitem3:
                Toast.makeText(getActivity(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.courseoptionitem4:
                Toast.makeText(getActivity(), "Item 4 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.courseoptionitem5:
                Toast.makeText(getActivity(), "Item 5 Selected", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}