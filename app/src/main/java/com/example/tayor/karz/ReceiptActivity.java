package com.example.tayor.karz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReceiptActivity extends BaseActivity {
private Button ok_bn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_receipt);

        ok_bn = findViewById(R.id.ok);
        ok_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homepage();
            }
        });
    }

    private void homepage() {
        Intent intent = new Intent(ReceiptActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
