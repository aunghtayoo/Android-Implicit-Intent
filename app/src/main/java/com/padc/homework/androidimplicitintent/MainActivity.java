package com.padc.homework.androidimplicitintent;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static int ACTION_TAKE_VIDEO = 330;

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSetTimer = findViewById(R.id.btn_set_timer);
        btnSetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmClock.ACTION_SHOW_TIMERS);
                startActivity(intent);
            }
        });

        final EditText textEventName = findViewById(R.id.text_event_name);
        Button btnAddEvent = findViewById(R.id.btn_add_cal_event);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", calendar.getTimeInMillis());
                intent.putExtra("endTime", calendar.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", ""+textEventName.getText().toString());
                startActivity(intent);
            }
        });


        //record and save a video
        videoView = findViewById(R.id.video_view);
        Button btnRecordVideo = findViewById(R.id.btn_record_video);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath()+"hello_video.mp4");
                startActivityForResult(intent,ACTION_TAKE_VIDEO);
            }
        });

        //select phone contact info.
        Button btnSelectContact = findViewById(R.id.btn_select_contact);
        btnSelectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //web-search with query String
        final EditText textQueryString = findViewById(R.id.text_search);
        Button btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String queryString = textQueryString.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(queryString));
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Query String Format.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==ACTION_TAKE_VIDEO && data!=null){
            String videoPath = data.getData().toString();
            videoView.setVideoPath(videoPath);
            videoView.start();
        }
    }
}
