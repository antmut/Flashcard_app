package com.example.flashcard;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer1;
    TextView flashcardAnswer2;
    TextView flashcardAnswer3;
    ImageView eyeVisible;
    ImageView eyeInvisible;

    Flashcard editCard;
    FlashcardDatabase flashcardDatabase;                                            //declaring as global variable flashcard
    List<Flashcard> allFlashcards;                                                  //declaring the whole list of flashcards
    int currentCardIndex = 0;

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

        flashcardDatabase = new FlashcardDatabase(this);                     // initializing flashcardDatabase
        allFlashcards = flashcardDatabase.getAllCards();

        //Toast message shown if question is clicked instead of background to reset answers
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Click the background to reset to default", Toast.LENGTH_SHORT).show();
                Log.i("Toni", "Entered method onClick");
            }
        });

        //When user tap the eye icon answers become visible
        eyeVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.INVISIBLE);
                eyeInvisible.setVisibility(View.VISIBLE);
                flashcardAnswer1.setVisibility(View.VISIBLE);
                flashcardAnswer2.setVisibility(View.VISIBLE);
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

        // User can press edit button and navigate to AddCardActivity with passing data
        findViewById(R.id.ic_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, AddCardActivity.class);
                myIntent.putExtra("Question",((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                myIntent.putExtra("Answer1", ((TextView) findViewById(R.id.flashcard_answer1)).getText().toString());
                myIntent.putExtra("Answer2", ((TextView) findViewById(R.id.flashcard_answer2)).getText().toString());
                myIntent.putExtra("Answer3", ((TextView) findViewById(R.id.flashcard_answer3)).getText().toString());
                MainActivity.this.startActivityForResult(myIntent, 101);
            }
        });

        // User can press trash button to delete current card
        findViewById(R.id.ic_trash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {       //check if empty
                    Snackbar.make(view,
                            "No more cards left. Please enter a flashcard!",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardIndex = 0;
                    return;
                }
                flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());   //what about answers??
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardIndex--;
            }
        });

        // User can press next button to go through the cards
        findViewById(R.id.ic_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards == null || allFlashcards.size() == 0) {       //always check edges
                    return;
                }
                    currentCardIndex++;

                if (currentCardIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            "End of cards reached. Starting from the beginning",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentCardIndex = 0;
                }
                    Flashcard currentCard = allFlashcards.get(currentCardIndex);
                    flashcardQuestion.setText(currentCard.getQuestion());
                    flashcardAnswer1.setText(currentCard.getAnswer());
                    flashcardAnswer2.setText(currentCard.getWrongAnswer1());
                    flashcardAnswer3.setText(currentCard.getWrongAnswer2());
            }
        });

        //Accessing flashcards
        if (allFlashcards != null && allFlashcards.size() > 0) {                     // check if list is empty
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer1.setText(allFlashcards.get(0).getAnswer());
            flashcardAnswer2.setText(allFlashcards.get(0).getWrongAnswer1());
            flashcardAnswer3.setText(allFlashcards.get(0).getWrongAnswer2());
        }
    }

        // get data passed from download button AddCardActivity
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && resultCode == RESULT_OK) {            // codes must match
                if(data != null) {
                    String question = data.getExtras().getString("Question_key");
                    String answer = data.getExtras().getString("Answer_key");
                    String answer1 = data.getExtras().getString("Answer_key1");
                    String answer2 = data.getExtras().getString("Answer_key2");
                    flashcardQuestion.setText(question);
                    flashcardAnswer1.setText(answer);
                    flashcardAnswer2.setText(answer1);
                    flashcardAnswer3.setText(answer2);

                    Snackbar.make(findViewById(R.id.flashcard_question),
                            "New flashcard added successfully",
                            Snackbar.LENGTH_SHORT)
                            .show();

                    flashcardDatabase.insertCard(new Flashcard(question, answer, answer1, answer2));          // save a new flashcard
                    allFlashcards = flashcardDatabase.getAllCards();                                          // update the list
                }
            }

            else if(requestCode == 101 && requestCode == RESULT_OK) {
                if(data != null) {

                    String question = getIntent().getStringExtra("Question");
                    String answer1 = getIntent().getStringExtra("Answer1");
                    String answer2 = getIntent().getStringExtra("Answer2");
                    String answer3 = getIntent().getStringExtra("Answer3");

                    ((EditText) findViewById(R.id.questionTextField)).setText(question);
                    ((EditText) findViewById(R.id.answerTextField)).setText(answer1);
                    ((EditText) findViewById(R.id.answerTextField1)).setText(answer2);
                    ((EditText) findViewById(R.id.answerTextField2)).setText(answer2);

                    if (((EditText) findViewById(R.id.questionTextField)) == null || ((EditText) findViewById(R.id.answerTextField)) == null
                            || ((EditText) findViewById(R.id.answerTextField1)) == null || ((EditText) findViewById(R.id.answerTextField2)) == null) {
                        Toast toast = Toast.makeText(MainActivity.this, "Must enter all fields", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 20, 0);
                        toast.show();
                    }

                    editCard.setQuestion(question);
                    editCard.setAnswer(answer1);
                    editCard.setWrongAnswer1(answer2);
                    editCard.setWrongAnswer2(answer3);
                    flashcardDatabase.updateCard(editCard);
                }
            }

        }
}