package com.example.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNote extends AppCompatActivity {

    private EditText ediTextTitle, ediTextDescription;
    private NumberPicker priorityPicker;

    public static final String EXTRA_TITLE =
            "com.example.mvvm.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.mvvm.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.example.mvvm.EXTRA_PRIORITY";
    public static final String EXTRA_ID =
            "com.example.mvvm.EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ediTextTitle = findViewById(R.id.ediTextTitle);
        ediTextDescription = findViewById(R.id.ediTextDescription);
        priorityPicker = findViewById(R.id.priorityPicker);

        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            ediTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            ediTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            priorityPicker.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else
            setTitle("Add Note");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveNoteItem:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = ediTextTitle.getText().toString();
        String description = ediTextDescription.getText().toString();
        int priority = priorityPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
}