package io.github.njackwinterofcode.robotalk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

public class textToSpeech extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    EditText mtext;
    Button convertButton;
    SeekBar mpitch ;
    SeekBar mspeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        mtext = findViewById(R.id.editText2);
        mpitch = findViewById(R.id.pitch);
        mspeed = findViewById(R.id.speechRate);
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
                speak();
            }
        });
    }
    private void speak() {
        String text = mtext.getText().toString();
        float pitch = (float) mpitch.getProgress() / 50;
        if (pitch < 0.1) {
            pitch = 0.1f;
        }
        float speed = (float) mspeed.getProgress() / 50;
        if (speed < 0.1) {
            speed = 0.1f;
        }
        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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
