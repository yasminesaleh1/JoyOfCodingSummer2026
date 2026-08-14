package edu.pdx.cs.joy.ysaleh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import edu.pdx.cs.joy.ParserException;

public class prettyAirlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pretty_airline);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backToMain(View view) {
        finish();
    }

    public void printAirline(View view) throws ParserException {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This airline does not exist. Please enter an existing airline.").setTitle("Error: Airline doesn't exist")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); finish();
                    }
                });
        AlertDialog invalidAirlineDialog = builder.create();

        EditText airlineNameWidget = findViewById(R.id.enter_airline_name);
        String airlineName = airlineNameWidget.getText().toString();
        String fileName = airlineName.toLowerCase() + ".txt";
        boolean airlineExists = false;
        String prettyPrintedAirline = null;

        String[] files = this.fileList();
        for (String f : files) {
            if (f.equals(fileName)) {
                airlineExists = true;
                File file = new File(this.getFilesDir(), fileName);

                TextParser tp = new TextParser(file);
                Airline retrievedAirline = tp.parse();
                PrettyPrinter p = new PrettyPrinter();
                prettyPrintedAirline = p.dump(retrievedAirline, null, null);

                EditText printingBox = findViewById(R.id.displayedAirline);
                printingBox.setText(prettyPrintedAirline);
            }
        }
        if (!airlineExists) {
            invalidAirlineDialog.show(); // no airline matched
        }

    }
}