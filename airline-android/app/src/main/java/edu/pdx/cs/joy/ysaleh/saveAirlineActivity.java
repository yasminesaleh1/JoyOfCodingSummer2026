package edu.pdx.cs.joy.ysaleh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class saveAirlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_airline);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void backToMain(View view) { finish(); }

    public void saveAirline(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Information is missing from the airline name box. Please enter an airline name").setTitle("Error: Empty Fields")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog missingInformation = builder1.create();

        EditText airlineNameWidget = findViewById(R.id.enter_airline_name);
        String airlineName = airlineNameWidget.getText().toString();
        String fileName = airlineName.toLowerCase() + ".txt";
        Boolean exists = false;

        if (airlineName.isEmpty()) {
            missingInformation.show();
        }

        // check there's no airline with that name already (file already exists then)
        String [] files = this.fileList();
        for (String f : files) {
            if (f.equals(fileName)) {
                // file already exists
                exists = true;
            }
        }
        if (!exists) {
            // add new airline and store its contents into a file in internal storage
            Airline newAirline = new Airline(airlineName);
            File airlineFile = new File(getApplicationContext().getFilesDir(), fileName);
            TextDumper td = new TextDumper(airlineFile);
            td.dump(newAirline);
        }

        // inform the user the airline was successfully created
        Snackbar successSnackbar = Snackbar.make(view, "Airline File Successfully Created!", 40);
        successSnackbar.show();
    }

}