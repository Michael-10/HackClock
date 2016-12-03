package projecttadhisto.qtma.com.hackclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AlarmActivity extends AppCompatActivity {

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> answer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        TextView textView = (TextView) findViewById(R.id.textView);
        Random random = new Random();
        int ran = random.nextInt(4) + 1;
        textView.setText(questions.get(ran));

    }

    /**
     * Reads each line in a file into a string
     * @param filename File passed into function containing information about the projects
     * @return Text from the file
     */
    private static String readFile(String filename) {
        File file = new File(filename);
        BufferedReader reader;
        String line = "";
        String text = "";
        try {
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                text += line + "\n";
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error opening file");
        } catch (IOException e) {
            System.err.println("Error reading file");
        } // end try-catch
        return text;
    } // end readFile method

}
