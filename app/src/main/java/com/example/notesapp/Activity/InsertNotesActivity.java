package com.example.notesapp.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;
import com.example.notesapp.ViewModel.NotesViewModel;
import com.example.notesapp.databinding.ActivityInsertNotesBinding;

import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title, subtitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.greenpriority.setOnClickListener(v -> {
            binding.greenpriority.setImageResource(R.drawable.check);
            binding.yellowpriority.setImageResource(0);
            binding.redpriority.setImageResource(0);
            priority = "1";
        });

        binding.yellowpriority.setOnClickListener(v -> {
            binding.greenpriority.setImageResource(0);
            binding.yellowpriority.setImageResource(R.drawable.check);
            binding.redpriority.setImageResource(0);
            priority = "2";
        });

        binding.redpriority.setOnClickListener(v -> {
            binding.greenpriority.setImageResource(0);
            binding.yellowpriority.setImageResource(0);
            binding.redpriority.setImageResource(R.drawable.check);
            priority = "3";
        });


        binding.doneNotesBtn.setOnClickListener(v -> {
            String title = binding.notesTitle.getText().toString();
            String subtitle = binding.notesSubtitle.getText().toString(); // Fixed this
            String notes = binding.notesData.getText().toString();

            // Create and save the note
            CreateNotes(title, subtitle, notes);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void CreateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubTitle = subtitle;
        notes1.notes = notes;
        notes1.notesDate = sequence.toString();
        notes1.notesPriority = priority;

        // Ensure toast is displayed before any operations
        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_LONG).show();

        // Save the note (assuming this is handled asynchronously)
        notesViewModel.insertNote(notes1);

        // Optionally delay finish to ensure toast visibility
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 2000);
    }
}