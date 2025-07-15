package com.rdxstudio.colorirbrincar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class PaintActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawingView = new DrawingView(this);
        setContentView(drawingView);
    }
}
