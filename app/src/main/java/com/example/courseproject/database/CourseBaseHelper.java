package com.example.courseproject.database;

import static com.example.courseproject.database.CourseDbSchema.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.courseproject.Course;

import java.util.ArrayList;

public class CourseBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION=1;
    public static final String DATABASE_NAME="courseBase.db";

    //use a constructor to create a database

    public CourseBaseHelper(Context context){

        super(context,DATABASE_NAME,null,VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ CourseTable.NAME +"("+
               CourseTable.Cols.COURSE_NO + ", " +
               CourseTable.Cols.COURSE_NAME + ", " +
                CourseTable.Cols.MAX_ENRL + ", " +
                CourseTable.Cols.CREDITS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static ContentValues getContentValues(Course course){
        ContentValues values =new ContentValues();
        values.put(CourseTable.Cols.COURSE_NO,course.getCourse_no());
        values.put(CourseTable.Cols.COURSE_NAME,course.getCourse_name());
        values.put(CourseTable.Cols.MAX_ENRL,course.getMax_enrl());
        values.put(CourseTable.Cols.CREDITS,course.credits);
        return values;

    }
    public void addNewCourse(Course course ){
        //writing data into database
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        //creating values from content-values
        ContentValues values=getContentValues(course);
        //inserting values into the table
        sqLiteDatabase.insert(CourseTable.NAME,null,values);//nullColumnHack :to allow empty row
        sqLiteDatabase.close();
        
    }
public ArrayList<Course> readCourses(){
        //read data from database
    SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
    //we need cursor variable inorder to read th data and save them in the array list

    Cursor cursorCourses=sqLiteDatabase.rawQuery("SELECT * FROM "+CourseTable.NAME,null);

    // CREATE AN ARRAY LIST
    ArrayList<Course> courseModalArrayList= new ArrayList<>();
    //moving the cursor to the first position of the row
    if(cursorCourses.moveToFirst())
    {
        do {
            courseModalArrayList.add(new Course(cursorCourses.getString(1),cursorCourses.getString(2),cursorCourses.getInt(3)));
        }while (cursorCourses.moveToNext());
    }
    //close the cursor and return list
    cursorCourses.close();
    return courseModalArrayList;
}

public void updateCourse(Course course){
        String course_noString=course.getCourse_no();
        ContentValues values=getContentValues(course);
    SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
    sqLiteDatabase.update(CourseTable.NAME,values,CourseTable.Cols.COURSE_NO+"=?",
            new String[] {course_noString});//???


}


}
