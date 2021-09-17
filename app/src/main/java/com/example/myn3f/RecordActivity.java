package com.example.myn3f;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    TextView textView;
    MediaRecorder mediaRecorder;

    public static String fileName = "recorded.3gp";

    String file = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        textView = findViewById(R.id.textV);

        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);


        mediaRecorder.setOutputFile(file);


    }

    public void onClick(View v) throws IOException {

        if (v.getId() == R.id.btnRecord) {
            //Record
            record();

        } else if (v.getId() == R.id.btnStop) {
//stop
            stopAudio();


        } else if (v.getId() == R.id.btnPlay) {
            //play
            play();


        }


    }

    private void stopAudio() throws IOException {

        mediaRecorder.stop();
        textView.setText("Recording Stopped");
        mediaRecorder.release();



    };

    private void play() {
        MediaPlayer mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(file);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText("Playing Audio");


    };

    private void record() {

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        textView.setText("Audio recording . . . ");

    };



}