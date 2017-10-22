package test.bwie.com.lianxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by dell on 2017/10/21.
 */

public class ThreeActivity extends Activity {

    private WebView webView;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threeactivity);
        webView = findViewById(R.id.webview);
        button = findViewById(R.id.buttok);
        webView.loadUrl("https://www.baidu.com/");
        Intent intent = getIntent();
        final String name1 = intent.getStringExtra("name1");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThreeActivity.this);
                builder.setMessage(name1);
                builder.create().show();
            }
        });

    }


}
