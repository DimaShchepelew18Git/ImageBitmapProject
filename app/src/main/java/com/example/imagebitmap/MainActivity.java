package com.example.imagebitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //объявление глобально, исп. можно в любых местах программы
    ImageView imageView;
    Bitmap bitmap;
    SeekBar seekBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Разработать программу обработки изображения из цветного в черно-белое.
        // Для обработки предоставлять пользователю
        // возможность задать степень преобразования в черно-белое изображение при помощи SeekBar.

        imageView = findViewById(R.id.imageView); //картинка
        seekBar = findViewById(R.id.SB_Value); //ползунок
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat); //битовая карта
        imageView.setImageBitmap(bitmap);
        //textView = findViewById(R.id.tvValue); //значение для SeekBar

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //cлушатель для SeekBar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //textView.setText(String.valueOf(seekBar.getProgress()));

                //узнаем размеры картинки
                //int width = bitmap.getWidth();
                //int height = bitmap.getHeight();

                int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()]; //массив пикселей (ширина*высота)
                bitmap.getPixels(
                        pixels, //эл. массива из пикселей
                        0, //1 эл. массива offset (смещение)
                        bitmap.getWidth(), //ширина stride(шаг)
                        0, //по x
                        0, //по y
                        bitmap.getWidth(), //ширина
                        bitmap.getHeight() //высота
                );
                for (int i = 0; i < pixels.length; i++) {
                    int red = Color.red(pixels[i]);
                    int green = Color.green(pixels[i]);
                    int blue = Color.blue(pixels[i]);

                    int wb = (red + green + blue) / 3;
                    red += (wb - red) * progress / 100;
                    green += (wb - green) * progress / 100;
                    blue += (wb - blue) * progress / 100;

                    pixels[i] = Color.rgb(red, green, blue);
                }
                Bitmap newBitmap = Bitmap.createBitmap(
                        pixels,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        Bitmap.Config.ARGB_8888
                );
                imageView.setImageBitmap(newBitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }
}