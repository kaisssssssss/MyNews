package com.kaichen.mynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        String[] values = getIntent().getExtras().getStringArray("data");
        TextView newsTitle = (TextView) findViewById(R.id.readNewsTitle);
        TextView newsBody = (TextView) findViewById(R.id.newsBody);
        newsBody.setText(values[1]);
        newsTitle.setText(values[0]);
    }
}
