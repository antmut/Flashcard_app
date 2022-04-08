package com.example.flashcard;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

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
    //int minNumber = 0;
    //int maxNumber = 0;
    CountDownTimer countDownTimer;

    int getRandomNumber(int minNumber, int maxNumber){
        Random rand = new Random();
        return rand.nextInt(((maxNumber - minNumber) + 1) + minNumber);
    }

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

        // When user tap the eye icon answers become invisible
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardAnswer1.setVisibility(View.VISIBLE);
                flashcardAnswer2.setVisibility(View.VISIBLE);
                flashcardAnswer3.setVisibility(View.VISIBLE);

                View answerSideView1 = findViewById(R.id.flashcard_answer1);
                View answerSideView2 = findViewById(R.id.flashcard_answer2);
                View answerSideView3 = findViewById(R.id.flashcard_answer3);

                // get the center for the clipping circle
                int cx = answerSideView2.getWidth() / 2;
                int cy = answerSideView2.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim1 = ViewAnimationUtils.createCircularReveal(answerSideView1, cx, cy, 0f, finalRadius);
                Animator anim2 = ViewAnimationUtils.createCircularReveal(answerSideView2, cx, cy, 0f, finalRadius);
                Animator anim3 = ViewAnimationUtils.createCircularReveal(answerSideView3, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer1.setVisibility(View.VISIBLE);
                flashcardAnswer2.setVisibility(View.VISIBLE);
                flashcardAnswer3.setVisibility(View.VISIBLE);

                anim1.setDuration(3000);
                anim1.start();
                anim2.setDuration(3000);
                anim2.start();
                anim3.setDuration(3000);
                anim3.start();
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
                overridePendingTransition(R.anim.right_in, R.anim.left_in);
            }
        });

        // User can press edit button and navigate to AddCardActivity with passing data
        findViewById(R.id.ic_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, AddCardActivity.class);
                myIntent.putExtra("Question_key",((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                myIntent.putExtra("Answer_key", ((TextView) findViewById(R.id.flashcard_answer1)).getText().toString());
                myIntent.putExtra("Answer_key1", ((TextView) findViewById(R.id.flashcard_answer2)).getText().toString());
                myIntent.putExtra("Answer_key2", ((TextView) findViewById(R.id.flashcard_answer3)).getText().toString());
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
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());   //what about answers??
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
                //getRandomNumber(0,5);
                    currentCardIndex++;

                if (currentCardIndex >= allFlashcards.size()) {
                    Snackbar.make(view,
                            " LAST card is displayed. Starting from the beginning",
                            Snackbar.LENGTH_SHORT)
                            .setTextColor(getColor(R.color.blue_dark))
                            .setBackgroundTint(getColor(R.color.tan))
                            .show();
                    currentCardIndex = 0;
                }

                    //loading animations
                    final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_in);
                    final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                    leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            flashcardQuestion.startAnimation(rightInAnim);

                            Flashcard currentCard = allFlashcards.get(currentCardIndex);
                            flashcardQuestion.setText(currentCard.getQuestion());
                            flashcardAnswer1.setText(currentCard.getAnswer());
                            flashcardAnswer2.setText(currentCard.getWrongAnswer1());
                            flashcardAnswer3.setText(currentCard.getWrongAnswer2());

                            flashcardQuestion.setVisibility((View.VISIBLE));
                            flashcardAnswer1.setVisibility(View.INVISIBLE);
                            flashcardAnswer2.setVisibility(View.INVISIBLE);
                            flashcardAnswer3.setVisibility((View.INVISIBLE));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    flashcardQuestion.startAnimation(leftOutAnim);
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
                            "   New flashcard has been added successfully ",
                            Snackbar.LENGTH_SHORT)
                            .setTextColor(getColor(R.color.blue_dark))
                            .setBackgroundTint(getColor(R.color.tan))
                            .show();

                    flashcardDatabase.insertCard(new Flashcard(question, answer, answer1, answer2));          // save a new flashcard
                    allFlashcards = flashcardDatabase.getAllCards();                                          // update the list
                }
            }

            else if(requestCode == 101) {
                Log.d("MainActivity", String.valueOf(requestCode));
                if(data != null) {

                    String question = data.getExtras().getString("Question_key");
                    String answer = data.getExtras().getString("Answer_key");
                    String answer1 = data.getExtras().getString("Answer_key1");
                    String answer2 = data.getExtras().getString("Answer_key2");

                    flashcardQuestion.setText(question);
                    flashcardAnswer1.setText(answer);
                    flashcardAnswer2.setText(answer1);
                    flashcardAnswer3.setText(answer2);
                    //editCard.setQuestion(question);
                    //editCard.setAnswer(answer);
                    //editCard.setWrongAnswer1(answer1);
                    //editCard.setWrongAnswer2(answer2);

                    flashcardDatabase.updateCard(editCard);
                }
            }

        }
}