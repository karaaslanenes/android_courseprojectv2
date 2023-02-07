package com.example.courseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class CourseContentActivity extends AppCompatActivity {
     private TextView contentTextView;
     private Button callWebServiceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        // get the view of textviw
        contentTextView=(TextView) findViewById(R.id.coursecontent_textView);
        // get the callwebservice button
        callWebServiceButton=(Button) findViewById(R.id.callcoursewebservicesbutton);
        callWebServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // consuming android web services
                String uri="https://jsonplaceholder.typicode.com/todos/198";
               new AsyncHttpClient().get(uri, new AsyncHttpResponseHandler() {
                   @Override
                   public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                       String strResponse=new String (responseBody);
                       contentTextView.setText(strResponse);
                   }

                   @Override
                   public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    contentTextView.setText("error in calling web services");
                   }
               });
            }
        });
    }
}