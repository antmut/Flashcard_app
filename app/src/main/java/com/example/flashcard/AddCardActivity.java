package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.BreakIterator;

public class AddCardActivity extends AppCompatActivity {

    EditText questionTextField;
    EditText answerTextField;
    EditText answerTextField1;
    EditText answerTextField2;

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

        // User can tap on download button to save input data
        findViewById(R.id.ic_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();                             //create new intent to put data
                String inputQuestion = ((EditText) findViewById(R.id.questionTextField)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.answerTextField)).getText().toString();
                String inputAnswer1 = ((EditText) findViewById(R.id.answerTextField1)).getText().toString();
                String inputAnswer2 = ((EditText) findViewById(R.id.answerTextField2)).getText().toString();
                data.putExtra("Question_key", inputQuestion);     // puts one string into the intent, with key as "Question_key"
                data.putExtra("Answer_key", inputAnswer);         // puts another string into the intent, with key as "Answer_key
                data.putExtra("Answer_key1", inputAnswer1);
                data.putExtra("Answer_key2", inputAnswer2);
                if (inputQuestion == "") {
                    Toast.makeText(AddCardActivity.this, "Must enter both Question and Answer", Toast.LENGTH_SHORT).show();
                    Log.i("Toni", "Entered method onClick");
                    }
                    else {
                        setResult(RESULT_OK, data);                             // sets result code and bundle data for response
                        finish();                                               // close this activity passing data to main activity
                    }
            }
        });

        // User get passed data from edit button
        findViewById(R.id.questionTextField).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = getIntent().getStringExtra("Question");
                String s1 = getIntent().getStringExtra("Answer1");
                String s2 = getIntent().getStringExtra("Answer2");
                String s3 = getIntent().getStringExtra("Answer3");
                questionTextField.setText(s);
                answerTextField.setText(s1);
                answerTextField1.setText(s2);
                answerTextField2.setText(s3);
            }
        });
    }
}