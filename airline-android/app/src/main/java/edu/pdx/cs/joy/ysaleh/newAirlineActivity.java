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

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class newAirlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_airline);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void backToMain(View view) {
        finish();
    }

    // Reference: https://developer.android.com/develop/ui/views/notifications/snackbar/showing
    // https://www.geeksforgeeks.org/android/how-to-create-an-alert-dialog-box-in-android/
    // https://developer.android.com/training/data-storage/app-specific#internal-access-files
    public void addNewAirline(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Information is missing from the airline name box. Please enter an airline name").setTitle("Error: Empty Fields")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); finish();
                    }
                });
        AlertDialog missingInformation = builder1.create();

        EditText airlineNameWidget = findViewById(R.id.enter_airline_name);
        String airlineName = airlineNameWidget.getText().toString();
        String fileName = airlineName.toLowerCase() + ".txt";

        if (airlineName.isEmpty()) {
            missingInformation.show();
            return;
        }

        // check there's no airline with that name already
        String [] files = this.fileList();
        for (String f : files) {
            if (f.equals(fileName)) {
                //AlertDialog.Builder builder = new AlertDialog.Builder(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This airline already exists. Please enter a new airline.").setTitle("Error: Airline already exists")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
        }

        // add new airline and store its contents into a file in internal storage
        Airline newAirline = new Airline(airlineName);
        File airlineFile = new File(getApplicationContext().getFilesDir(), fileName);
        TextDumper td = new TextDumper(airlineFile);
        td.dump(newAirline);

        // inform the user the airline was successfully created
        Snackbar successSnackbar = Snackbar.make(view, "Airline Successfully Added!", BaseTransientBottomBar.LENGTH_SHORT);
        successSnackbar.show();

    }
}