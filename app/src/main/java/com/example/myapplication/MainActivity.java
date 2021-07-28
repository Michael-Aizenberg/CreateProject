package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class MainActivity extends AppCompatActivity {

    //initialize variables
    EditText etInput;
    Button btGenerate;
    ImageView ivOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign Variables
        etInput = findViewById(R.id.et_input);
        btGenerate = findViewById(R.id.bt_generate);
        ivOutput = findViewById(R.id.iv_output);

        btGenerate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Get input value from edit text
                String sText = etInput.getText().toString().trim();



                //initialize multi format writer
                MultiFormatWriter writer = new MultiFormatWriter();


                try {
                    //Initialize bit matrix
                    BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE
                            , 350, 350);

                    int width = matrix.getWidth();
                    int height = matrix.getHeight();

                    //initialize bitmap
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565 );
                    for (int x = 0; x < width; x++){
                        for (int y = 0; y < height; y++){
                            bitmap.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
                        }
                    }

                    ivOutput.setImageBitmap(bitmap);

                    //initialize input manager
                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );

                    //Hide soft keyboard
                    manager.hideSoftInputFromWindow(etInput.getApplicationWindowToken()
                    , 0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });


    }




}