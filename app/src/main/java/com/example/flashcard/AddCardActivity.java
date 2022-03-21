package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        //Get data passed from edit button in MainActivity
        ((EditText) findViewById(R.id.questionTextField)).setText(getIntent().getStringExtra("Question"));
        ((EditText) findViewById(R.id.answerTextField)).setText(getIntent().getStringExtra("Answer1"));
        ((EditText) findViewById(R.id.answerTextField1)).setText(getIntent().getStringExtra("Answer2"));
        ((EditText) findViewById(R.id.answerTextField2)).setText(getIntent().getStringExtra("Answer3"));

        // User can tap on cancel button to go back to main activity
        findViewById(R.id.ic_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // User can tap on download button to save input data
        findViewById(R.id.ic_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((EditText) findViewById(R.id.questionTextField)) == null || ((EditText) findViewById(R.id.answerTextField)) == null
                        || ((EditText) findViewById(R.id.answerTextField1)) == null || ((EditText) findViewById(R.id.answerTextField2)) == null) {

                    Toast toast = Toast.makeText(AddCardActivity.this, "Must enter all fields", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 20, 0);
                    toast.show();
                }
                else {
                    Intent data = new Intent();                             //create new intent to put data
                    data.putExtra("Question_key", ((EditText) findViewById(R.id.questionTextField)).getText().toString());     // puts one string into the intent, with key as "Question_key"
                    data.putExtra("Answer_key", ((EditText) findViewById(R.id.answerTextField)).getText().toString());         // puts another string into the intent, with key as "Answer_key
                    data.putExtra("Answer_key1", ((EditText) findViewById(R.id.answerTextField1)).getText().toString());
                    data.putExtra("Answer_key2", ((EditText) findViewById(R.id.answerTextField2)).getText().toString());
                    setResult(RESULT_OK, data);                             // sets result code and bundle data for response
                    finish();                                               // close this activity passing data to main activity
                }
            }
        });
    }
}