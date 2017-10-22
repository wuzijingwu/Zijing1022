package test.bwie.com.lianxi;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private int i=0;
    public static final int HANDLER_MESSAGE = 2;



    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "scaleX", 1F, 2F, 1F);
                    ObjectAnimator objectAnimators = ObjectAnimator.ofFloat(image, "scaleY", 1F, 2F, 1F);
                    objectAnimator.setDuration(1300).start();
                    objectAnimators.setDuration(1300).start();
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    image.animate().translationY(200).scaleY(1).scaleX(1).setDuration(400);
                    break;
                case 2:
                    cdv.startCountDown();
                    break;



            }
        }
    };
    private CountDownView cdv;
    private Button buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        cdv = (CountDownView) findViewById(R.id.countDownView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                if(i==1){
                    handler.sendEmptyMessage(0);
                    handler.sendEmptyMessage(2);
                }
            }
        },0,100);


    }

}
