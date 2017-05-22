package gmail820castromarcelo.com.estagiojera;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button intervalButton;
    private TextView timerValue;
    private int cont;
    CountDownTimer countDownTimer;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        intervalButton = (Button) findViewById(R.id.intervalButton);

        mp = MediaPlayer.create(this,R.raw.beep);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new CountDownTimer(10000, 1000) {//Corrigir minutagem
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        intervalButton.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        cont++;
                        intervalButton.setClickable(true);
                    }
                }.start();
            }
        });

        intervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new CountDownTimer(5000, 1000) {//Corrigir minutagem
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        startButton.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        startButton.setClickable(true);
                    }
                }.start();
            }
        });
    }
}
