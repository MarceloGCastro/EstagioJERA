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

    private Button iniciar;
    private Button intervalo;
    private TextView timerValue;
    private int cont;
    CountDownTimer countDownTimer;
    MediaPlayer mp;
    private EditText qtdSegundos;
    private EditText qtdMinutos;
    private TextView segundos;
    private TextView minutos;
    private Button set;
    private TextView contDiario;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timerValue);
        iniciar = (Button) findViewById(R.id.iniciar);
        intervalo = (Button) findViewById(R.id.intervalo);
        qtdMinutos = (EditText) findViewById(R.id.qtdMinutos);
        qtdSegundos = (EditText)findViewById(R.id.qtdSegundos);
        segundos = (TextView) findViewById(R.id.segundos);
        minutos = (TextView) findViewById(R.id.minutos);
        set = (Button) findViewById(R.id.set);
        contDiario = (TextView) findViewById(R.id.contDiario);

        mp = MediaPlayer.create(this,R.raw.beep);// Criando o som do final do timer

        Thread t = new Thread() {// Zerando pomodoro a cada 24h.
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(86400000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                contDiario.setText("Hoje, você fez 0 pomodoros!");
                                contador = 0;
                            }
                        });

                    } catch (InterruptedException e) {

                    }
                }
            }
        };

        t.start();

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// Fazendo as alterações do botão iniciar
                countDownTimer = new CountDownTimer(1500000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        intervalo.setClickable(false);//Boqueia interval button
                        set.setClickable(false);//Bloqueia set button
                    }

                    @Override
                    public void onFinish() {
                        mp.start();// Tocando som do timer
                        cont++;
                        if(cont==4) {
                            descanso();// Exibindo sugestão de intervalo
                            cont = 0;
                        }
                        contador++;
                        if(contador == 1) {
                            contDiario.setText("Hoje, você fez 1 pomodoro!");
                        } else {
                            contDiario.setText("Hoje, você fez " + contador + " pomodoros!");// Contando o número de pomodoros
                        }
                        intervalo.setClickable(true);//Desbloqueando o botão de intervalo
                        set.setClickable(true);// Desbloqueando o botão de set
                    }
                }.start();
            }
        });

        intervalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Fazendo as alterações do botão de intervalo
                countDownTimer = new CountDownTimer(300000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        iniciar.setClickable(false);//Bloqueia o botão iniciar
                        set.setClickable(false);//Bloqueia o botão set
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        iniciar.setClickable(true);//Desloqueia o botão iniciar
                        set.setClickable(true);//Desloqueia o botão set
                    }
                }.start();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// Fazendo alterações do botão set

                long minutos = Long.parseLong(qtdMinutos.getText().toString());
                long segundos = Long.parseLong(qtdSegundos.getText().toString());

                minutos *= 60000;
                segundos *= 1000;

                long total = minutos + segundos;

                countDownTimer = new CountDownTimer(total, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int restante = (int) (millisUntilFinished/1000);
                        int mRestante = restante/60;
                        timerValue.setText(Integer.toString(mRestante) + ":" + Integer.toString(restante - (mRestante*60)));
                        intervalo.setClickable(false);//Boqueia  botão de intervalo
                        iniciar.setClickable(false);//Bloqueia botão iniciar
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        intervalo.setClickable(true);// Desbloqueia botão de intervalo
                        iniciar.setClickable(true);// Desbloqueia botão iniciar
                    }
                }.start();
            }
        });
    }

    private void descanso() {//Cria AlertDialog
        AlertDialog builder = new AlertDialog.Builder(this).setTitle("Descanso").setMessage("Você executou 4 timers completos" +
                ", talvez deva descansar 10 minutos!").setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

 }
