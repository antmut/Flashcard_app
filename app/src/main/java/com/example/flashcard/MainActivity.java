package com.example.flashcard;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer1;
    TextView flashcardAnswer2;
    TextView flashcardAnswer3;
    ImageView eyeVisible;
    ImageView eyeInvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer1 = findViewById(R.id.flashcard_answer1);
        flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        flashcardAnswer3 = findViewById(R.id.flashcard_answer3);

        eyeVisible = findViewById(R.id.ic_eye_visible);
        eyeInvisible = findViewById(R.id.ic_eye_invisible);

        //Toast message if clicked the question
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Question was clicked", Toast.LENGTH_SHORT).show();
                Log.i("Toni", "Entered method onClick");
            }
        });

        //When user tap the eye icon answers become visible
        eyeVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.INVISIBLE);
                eyeInvisible.setVisibility(View.VISIBLE);
                flashcardAnswer1.setVisibility(View.INVISIBLE);
                flashcardAnswer2.setVisibility(View.INVISIBLE);
                flashcardAnswer3.setVisibility(View.VISIBLE);
            }
        });

        // When user tap the eye icon answers become invisible
        eyeInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.VISIBLE);
                eyeInvisible.setVisibility(View.INVISIBLE);
                flashcardAnswer1.setVisibility(View.INVISIBLE);
                flashcardAnswer2.setVisibility(View.INVISIBLE);
                flashcardAnswer3.setVisibility(View.INVISIBLE);
            }
        });

        // When User tap on 1st answer colors change accordingly
        findViewById(R.id.flashcard_answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer1)).setBackgroundColor(getResources().getColor(R.color.red));
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // When User tap on 2nd answer colors change accordingly
        findViewById(R.id.flashcard_answer2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer2)).setBackgroundColor(getResources().getColor(R.color.red));
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // When User tap on 3rd answer colors change accordingly
        findViewById(R.id.flashcard_answer3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // User can tap on the background view to reset all views to default settings
        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.flashcard_answer1)).setBackgroundColor(getResources().getColor(R.color.tan));
                ((TextView) findViewById(R.id.flashcard_answer2)).setBackgroundColor(getResources().getColor(R.color.tan));
                ((TextView) findViewById(R.id.flashcard_answer3)).setBackgroundColor(getResources().getColor(R.color.tan));
            }
        });

        // User can press add button and navigate to a new activity
        findViewById(R.id.ic_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        // User can press edit button and navigate to AddCardActivity
        findViewById(R.id.ic_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                String str1 = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String str2 = ((TextView) findViewById(R.id.flashcard_answer3)).getText().toString();
                intent.putExtra("Question", str1);
                intent.putExtra("Answer", str2);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }
        // get data passed from download button AddCardActivity
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100) {            // codes must match
                if(data != null) {
                    String question = data.getExtras().getString("Question_key");
                    String answer = data.getExtras().getString("Answer_key");
                    flashcardQuestion.setText(question);
                    flashcardAnswer3.setText(answer);
                }
            }
        }
}