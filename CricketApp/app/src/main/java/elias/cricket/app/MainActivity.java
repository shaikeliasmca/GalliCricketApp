package elias.cricket.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import android.view.View;
import java.util.Locale;
import android.content.ActivityNotFoundException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
        private ImageButton btnSpeak;
        private final int REQ_CODE_SPEECH_INPUT = 100;
        private int balls=0;
        private int score=0;
        private int overs=0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
            btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

            // hide the action bar
            getSupportActionBar().hide();
            txtSpeechInput.setText("overs:"+overs+"."+balls+" "+"score:"+score);

            btnSpeak.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    promptSpeechInput();
                }
            });
        }

        /**
         * Showing google speech input dialog
         * */
        private void promptSpeechInput() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.speech_not_supported),
                        Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Receiving speech input
         * */
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            System.out.println("Did You get Somthing!!! "+requestCode);
            switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
            System.out.println("RESULT_OK " +RESULT_OK+"data "+data);
                if (resultCode == RESULT_OK && null != data) {
                String strTxt=null;
                    try{
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            strTxt=result.get(0);
                            System.out.println(strTxt);
                            if(strTxt.equalsIgnoreCase("tu")
                            ||strTxt.equalsIgnoreCase("two")
                            ||strTxt.equalsIgnoreCase("2 runs")
                            ){
                                strTxt="2";
                            }else if(strTxt.equalsIgnoreCase("sex")){
                                 strTxt="6";
                            }else if(strTxt.contains("dot")
                            ||strTxt.contains("Dodgeball")||strTxt.contains("dog")
                            ){
                                 strTxt="0";
                            }else if(strTxt.contains("single")){
                                strTxt="1";
                            }else if(strTxt.contains("3 runs")){
                                strTxt="3";
                            }else if(strTxt.contains("4 runs")){
                                strTxt="4";
                            }else if(strTxt.contains("6 runs")){
                                strTxt="6";
                            }

                            if(strTxt.equalsIgnoreCase("white")||strTxt.equalsIgnoreCase("wide")
                            ||strTxt.equalsIgnoreCase("Haider")
                            ||strTxt.equalsIgnoreCase("wide")
                            ||strTxt.equalsIgnoreCase("nope")
                            ||strTxt.equalsIgnoreCase("no ball")
                            ||strTxt.equalsIgnoreCase("wall")
                            ||strTxt.equalsIgnoreCase("no")
                            ||strTxt.equalsIgnoreCase("dog")
                            ||strTxt.equalsIgnoreCase("why")
                            ||strTxt.equalsIgnoreCase("wild")
                            ||strTxt.equalsIgnoreCase("wife")
                            ||strTxt.equalsIgnoreCase("fight")
                            ||strTxt.equalsIgnoreCase("wide ball")
                            ||strTxt.equalsIgnoreCase("white ball")
                            ){
                            strTxt="1";
                            balls--;
                            }else if(strTxt.equalsIgnoreCase("no + 1")){
                                 strTxt="2";
                                 balls--;
                            }else if(strTxt.equalsIgnoreCase("no + 2")){
                                strTxt="3";
                                balls--;
                            }
                            else if(strTxt.equalsIgnoreCase("no + 3")){
                                strTxt="4";
                                balls--;
                            }else if(strTxt.equalsIgnoreCase("no + 4")){
                                strTxt="5";
                                balls--;
                            }else if(strTxt.equalsIgnoreCase("no + 5")){
                                strTxt="6";
                                balls--;
                            }else if(strTxt.equalsIgnoreCase("no + 6")){
                                strTxt="7";
                                balls--;
                            }
                            if(Integer.parseInt(strTxt)>7){
                            Toast.makeText(getApplicationContext(),
                                            strTxt+" Can not be more than 7!!!",
                                            Toast.LENGTH_SHORT).show();
                                            break;
                            }
                            score=score+Integer.parseInt(strTxt);

                            balls=balls+1;
                            if(balls==6){
                                overs=overs+1;
                                balls=0;
                             }
                            txtSpeechInput.setText("overs:"+overs+"."+balls+" "+"score:"+score);
                }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                strTxt+" Not a right word to use!!!",
                Toast.LENGTH_SHORT).show();
                }
                break;
                }
            }
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
         }
}
