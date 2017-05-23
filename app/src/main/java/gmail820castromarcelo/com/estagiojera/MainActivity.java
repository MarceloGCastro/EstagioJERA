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
    private EditText qtdSegundos;
    private EditText qtdMinutos;
    private TextView segundos;
    private TextView minutos;
    private Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        intervalButton = (Button) findViewById(R.id.intervalButton);
        qtdMinutos = (EditText) findViewById(R.id.qtdMinutos);
        qtdSegundos = (EditText)findViewById(R.id.qtdSegundos);
        segundos = (TextView) findViewById(R.id.segundos);
        minutos = (TextView) findViewById(R.id.minutos);
        set = (Button) findViewById(R.id.set);


        mp = MediaPlayer.create(this,R.raw.beep);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new CountDownTimer(1500000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        intervalButton.setClickable(false);//Boqueia interval button
                        set.setClickable(false);//Bloqueia set button
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        cont++;
                        if(cont==4) {
                            descanso();
                            cont = 0;
                        }
                        intervalButton.setClickable(true);
                        set.setClickable(true);
                    }
                }.start();
            }
        });

        intervalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer = new CountDownTimer(300000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        startButton.setClickable(false);//Bloqueia startButton
                        set.setClickable(false);//Bloqueia set button
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        startButton.setClickable(true);
                        set.setClickable(true);
                    }
                }.start();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int minutos = Integer.parseInt(qtdMinutos.getText().toString());
                int segundos = Integer.parseInt(qtdSegundos.getText().toString());
                segundos *= 1000;
                minutos *= 60000;
                final long total = minutos + segundos;

                countDownTimer = new CountDownTimer(total, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        intervalButton.setClickable(false);//Boqueia interval button
                        startButton.setClickable(false);//Bloqueia start button
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        intervalButton.setClickable(true);
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
