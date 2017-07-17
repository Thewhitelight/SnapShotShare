package cn.libery.snapshot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SnapShotListenerManager mManager;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mManager = new SnapShotListenerManager(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            mManager.stopListener();
        }
    }

}
