package com.example.bob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import android.Manifest;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.bob.Functions.wishMe;

public class HomeActivity extends AppCompatActivity {
    private SpeechRecognizer recognizer;
    private TextView tvResult;
    private TextToSpeech tts;

    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);
        imageView2 = findViewById(R.id.imageView2);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        System.exit(0);
                    }

                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        findById();
        initializeTextToSpeech();
        initializeResult();

    }
    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size()==0) {
                    Toast.makeText(HomeActivity.this, "Engine is not available", Toast.LENGTH_SHORT).show();
                }
                else {
                    String s = wishMe();
                    speak("Hii this is Bob...!"+s);
                }
            }
        });
    }

    private void speak(String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null);
        }

    }

    private void findById() {
        tvResult = (TextView)findViewById(R.id.tv_result);
    }

    private void initializeResult() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(HomeActivity.this, ""+result.get(0), Toast.LENGTH_SHORT).show();
                    tvResult.setText(result.get(0));
                    response(result.get(0));

                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void response(String msg) {
        String msgs = msg.toLowerCase();
        if (msgs.indexOf("hey") != -1 || msgs.indexOf("hello")!=-1 || msgs.indexOf("hii")!=-1) {
            if (msgs.indexOf("bob")!=-1) {
                speak("Hello buddy, how may i help you!");
            }
        }
        if (msgs.indexOf("bob")!=-1){
            if (msgs.indexOf("who are you") != -1) {
                speak("I am your personal voice assistant bob..!");
            }
            if (msgs.indexOf("what can you do for me") != -1) {
                speak("I can do a lot for you, like I can open some of your applications, I can say you the time and date, I can do web search for you and lot more my dear buddy..!");

            }
            if (msgs.indexOf("when is your birthday") != -1) {
                speak("my birthday is on june second");
            }
            if (msgs.indexOf("tell about yourself") != -1 || msgs.indexOf("tell about you") != -1) {
                speak("My self bob, I am your virtual assistant. I was made build to assist you.");
            }
            if (msgs.indexOf("where do you live") != -1) {
                speak("In fact, i do live in your phone");
            }
            if (msgs.indexOf("what do you eat") != -1) {
                speak("Heyy funny query buddy, i eat your memory, charging and internet");
            }
            if (msgs.indexOf("who is your boss") != -1) {
                speak("my boss is Mrs. T. Rajani mam, head of the department of electronics and communication engineering from nalla malla reddy engineering college ");
            }
            if (msgs.indexOf("under whose guidance you have been built") != -1) {
                speak("i was built under the guidance of Dr. M. Senthil kumar, associate professor in the department of Electronics and communication engineering from nalla malla reddy engineering college");
            }
            if (msgs.indexOf("who made you") != -1 || msgs.indexOf("who build you") != -1 || msgs.indexOf("who built you")!=-1) {
                speak("I was built by Students of Electronics and communication engineering department from nalla malla reddy engineering college hyderabad, and the name of the students is ... sai pranith..., ...pranay..., ...raju");
            }
            if (msgs.indexOf("what is your native place") != -1 || msgs.indexOf("where did you born") != -1) {
                speak("I have born in NMREC college");
            }
            if (msgs.indexOf("what's time now") != -1 || msgs.indexOf("what's the current time") != -1 || msgs.indexOf("time please") != -1 || msgs.indexOf("time")!=-1) {
                Date date = new Date();
                String time = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
                speak("Now the current time is" + time);
            }
            if (msgs.indexOf("what is today date") != -1 || msgs.indexOf("date") != -1) {
                SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
                Calendar cal = Calendar.getInstance();
                String todays_date = df.format(cal.getTime());
                speak("Today's date is" + todays_date);
            }
            if (msgs.indexOf("open") != -1) {
                if (msgs.indexOf("google") != -1) {
                    speak("Opening google...");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                    startActivity(intent);
                }
                if (msgs.indexOf("browser") != -1) {
                    speak("Opening browser...");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                    startActivity(intent);
                }
                if (msgs.indexOf("chrome") != -1) {
                    speak("Opening chrome...");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                    startActivity(intent);
                }
                if (msgs.indexOf("youtube") != -1) {
                    speak("Opening youtube...");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
                    startActivity(intent);
                }
                if (msgs.indexOf("instagram") != -1) {
                    speak("Opening instagram...");
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                    ctx.startActivity(i);
                }
                if (msgs.indexOf("amazon") != -1) {
                    speak("Opening amazon...");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/"));
                    startActivity(intent);
                }
                if (msgs.indexOf("whatsapp") != -1) {
                    speak("Opening what'sapp...");
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    ctx.startActivity(i);
                }
            }
            if (msgs.indexOf("search for") != -1) {
                String a = msgs;
                int len = a.length();
                String websearch = a.substring(14, len);
                try {
                    speak("Searching for..." + websearch);
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    intent.putExtra(SearchManager.QUERY, websearch);
                    startActivity(intent);

                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (msgs.indexOf("play")!=-1){
                String x = msgs;
                int len = x.length();
                String play = x.substring(14, len);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" +  play));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            speak("Sorry......, I am unable to recognize");
        }
    }

    public void startRecording(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

        recognizer.startListening(intent);
    }

}