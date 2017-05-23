package gmail820castromarcelo.com.estagiojera;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
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
                countDownTimer = new CountDownTimer(3000, 1000) { // Corrigir Tempo
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        intervalButton.setClickable(false);//Boqueia interval button
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        cont++;
                        if(cont==4) {
                            descanso();
                        }
                        intervalButton.setClickable(true);
                    }
                }.start();
            }
        });

        intervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new CountDownTimer(3000, 1000) { // Corrigir Tempo
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        startButton.setClickable(false);//Bloqueia startButton
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

    private void descanso() {
        AlertDialog builder = new AlertDialog.Builder(this).setTitle("Descanso").setMessage("Você executou 4 timers completos" +
                ", talvez deva descansar 10 minutos!").setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
