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
import java.lang.reflect.Array;
import java.util.Objects;

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
    public void addNewAirline(View view) {
        EditText airlineNameWidget = findViewById(R.id.airline_name_text_box);
        String airlineName = airlineNameWidget.getText().toString();
        String fileName = airlineName.toLowerCase() + ".txt";

        // check there's no airline with that name already
        String [] files = this.fileList();
        for (String f : files) {
            if (f.equals(fileName)) {
                //AlertDialog.Builder builder = new AlertDialog.Builder(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This airline already exists. Please enter a new airline.").setTitle("Error: Airline already exists")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        // add new airline and store its contents into a file in internal storage
        Airline newAirline = new Airline(airlineName);
        File airlineFile = new File(getApplicationContext().getFilesDir(), fileName);
        TextDumper td = new TextDumper(airlineFile);
        td.dump(newAirline);

        // inform the user the airline was successfully created
        Snackbar successSnackbar = Snackbar.make(view, "Airline Successfully Added!", 40);
        successSnackbar.show();

    }
}