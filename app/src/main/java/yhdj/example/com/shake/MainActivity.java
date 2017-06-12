package yhdj.example.com.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ShakeSensor.OnShakeListener {
    private ShakeSensor mShakeSensor;
private ImageView imgHandle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initViews();
        mShakeSensor = new ShakeSensor(MainActivity.this);
        mShakeSensor.setOnShakeListener(MainActivity.this);

        mShakeSensor.init();

    }

    private void initViews() {
        imgHandle = (ImageView) findViewById(R.id.img_handle);
     Animation animation  = AnimationUtils.loadAnimation(MainActivity.this,R.anim.handle_roate);
        imgHandle.startAnimation(animation);
    }

    @Override
    public void onShake() {
        Toast.makeText(this, "摇一摇成功！！！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.shake, 0);
    }
}
