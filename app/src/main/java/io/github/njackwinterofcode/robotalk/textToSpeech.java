package io.github.njackwinterofcode.robotalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class textToSpeech extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    EditText text;
    Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        text = findViewById(R.id.editText2);
        convertButton = findViewById(R.id.button);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.US);

                    if(lang == TextToSpeech.LANG_MISSING_DATA || lang == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.i("Language Support", "Not Supported");
                    }
                    else{
                        Log.i("Language Support", "Supported");
                    }
                    Log.i("TTS", "Initialization Success");
                }
                else{
                    Toast.makeText(textToSpeech.this, "TTS Initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = text.getText().toString();
                Log.i("TTS", "Button Clicked" + data);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH,null);
                if(speechStatus == TextToSpeech.ERROR){
                    Log.e("TTS ", "Error in conversion");
                }
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
