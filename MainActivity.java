package stalwart.team.yuhuu.easymusic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_AUDIO = 1;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String audio = "audioKey";
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = new MediaPlayer();
        sharedpreferences = getSharedPreferences(mypreference,Context.MODE_PRIVATE);
        if (sharedpreferences.contains(audio)) {
            try {
                Uri uri= Uri.parse(sharedpreferences.getString(audio, ""));
                mp.setDataSource(this, uri);
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.setOnPreparedListener(
                    new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                        }
                    }
            );
        }


        Button playMe1 = (Button) findViewById(R.id.playMe1);
        playMe1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_AUDIO);
                return true;
            }
        });


    }//onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO) {

            if (resultCode == RESULT_OK) {
                if (requestCode == PICK_AUDIO && resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();
                    String uriS=uri.toString();
                    mp.reset();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(audio, uriS);
                    editor.commit();

                    try {
                        mp.setDataSource(this, uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.prepareAsync();
                    final View playMe1 = findViewById(R.id.playMe1);
                    playMe1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mp.isPlaying()){
                                mp.pause();
                            }else{
                                mp.start();
                            }
                        }

                    });
                }
            }
        }
    }
}//class




