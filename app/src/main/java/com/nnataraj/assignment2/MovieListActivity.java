package com.nnataraj.assignment2;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MovieListActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, GestureDetector.OnGestureListener {

    private ImageView imageView;
    private SeekBar seekBar;
    private MovieData movieData = new MovieData();
    final static private int imageHeight = 317 * 3;
    final static private int imageWidth = 214 * 3;
    private int current = 0;
    private boolean clickStart = false;
    private float xPrevious;
    private boolean longPress = false;
    final static float swipeSensitivity = 50; //Just specific how much variation in x should trigger the swipe gesture

    private GestureDetectorCompat mDetector;

    private void updateMovie(int id) {
        HashMap hashMap = movieData.getItem(id);
        imageView.setImageResource((int) hashMap.get("image"));
        TextView textView = (TextView) findViewById(R.id.name);
        textView.setText((String) hashMap.get("name") + " ");
        textView = (TextView) findViewById(R.id.year);
        textView.setText("(" + hashMap.get("year") + ")");
        textView = (TextView) findViewById(R.id.rating);
        textView.setText((double) hashMap.get("rating") + "/10");
        textView = (TextView) findViewById(R.id.length);
        textView.setText((String) hashMap.get("length"));
        textView = (TextView) findViewById(R.id.director);
        textView.setText("Directed by "+hashMap.get("director"));
        textView = (TextView) findViewById(R.id.stars);
        textView.setText("Stars: "+hashMap.get("stars"));
        textView = (TextView) findViewById(R.id.description);
        textView.setText((String) hashMap.get("description"));
        textView = (TextView) findViewById(R.id.url);
        textView.setText((String) hashMap.get("url"));
    }

    private void resizeImage(int factor) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = imageHeight * (factor) / 100;
        layoutParams.width = imageWidth * (factor) / 100;
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        imageView = (ImageView) findViewById(R.id.imageView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        updateMovie(current);
        resizeImage(50);

        seekBar.setOnSeekBarChangeListener(this);
        mDetector = new GestureDetectorCompat(imageView.getContext(), this);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        xPrevious = event.getX();
                    case (MotionEvent.ACTION_UP):
                        float xNow = event.getX();
                        if (xNow > xPrevious + swipeSensitivity) {
                            if (!longPress) {
                                //Log.d("MovieList-Swipe", "left to right; " + x2 + "--> " + x1);
                                if (current - 1 > -1)
                                    updateMovie(--current);
                            } else {
                                longPress = false;
                                return true;
                            }
                        } else if (xNow + swipeSensitivity < xPrevious) {
                            if (!longPress) {
                                //Log.d("MovieList-Swipe", "right to left; " + x2 + "--> " + x1);
                                if (current + 1 < movieData.getSize())
                                    updateMovie(++current);
                            } else {
                                longPress = false;
                                return true;
                            }
                        }

                }
                return mDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //do nothing
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //do nothing
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        resizeImage(progress);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        clickStart = true;
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        longPress = true;
        clickStart = false;
        resizeImage(50);
        seekBar.setProgress(50);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        //do nothing
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        if (clickStart) {
            Snackbar.make(findViewById(android.R.id.content), "This is a snack bar message", Snackbar.LENGTH_SHORT)
                    .show();

            Toast.makeText(getApplicationContext(), "This is a toast message", Toast.LENGTH_SHORT).show();
        }
        longPress = false;
        return true;
    }

}
