package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        // User can tap on cancel button to go back to main activity
        findViewById(R.id.ic_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // User can tap on cancel button to go back to main activity
        findViewById(R.id.ic_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();                             //create new intent to put data
                    String inputQuestion = ((EditText) findViewById(R.id.questionTextField)).getText().toString();
                    String inputAnswer = ((EditText) findViewById(R.id.answerTextField)).getText().toString();
                    data.putExtra("Question_key", inputQuestion);    // puts one string into the intent, with key as "string1"
                    data.putExtra("Answer_key", inputAnswer); // puts another string into the intent, with key as "string2"
                    setResult(RESULT_OK, data);                             // sets result code and bundle data for response
                    finish();                                               // close this activity passing data to main activity
            }
        });
    }
}