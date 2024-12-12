package com.example.notesapp.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;
import com.example.notesapp.ViewModel.NotesViewModel;
import com.example.notesapp.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    ActivityUpdateNotesBinding binding;
    String priority = "1";
    String stitle, ssubtitle, snotes, spriority;
    int iid;
    NotesViewModel notesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        iid = getIntent().getIntExtra("id", 0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        spriority = getIntent().getStringExtra("priority");
        snotes = getIntent().getStringExtra("notes");

        binding.upTitle.setText(stitle);
        binding.upSubtitle.setText(ssubtitle);
        binding.upNotes.setText(snotes);

        if (spriority.equals("1")) {
            binding.greenpriority.setImageResource(R.drawable.check);
        } else if (spriority.equals("2")) {
            binding.yellowpriority.setImageResource(R.drawable.check);
        } else if (spriority.equals("3")) {
            binding.redpriority.setImageResource(R.drawable.check);
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

        binding.updateNotesBtn.setOnClickListener(v -> {
            String title = binding.upTitle.getText().toString();
            String subtitle = binding.upSubtitle.getText().toString(); // Fixed this
            String notes = binding.upNotes.getText().toString();

            // Create and save the note
            UpdateNotes(title, subtitle, notes);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void UpdateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes updateNotes = new Notes();
        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubTitle = subtitle;
        updateNotes.notes = notes;
        updateNotes.notesDate = sequence.toString();
        updateNotes.notesPriority = priority;

        // Ensure toast is displayed before any operations
        Toast.makeText(this, "Notes Updated Successfully", Toast.LENGTH_LONG).show();

        // Save the note (assuming this is handled asynchronously)
        notesViewModel.updateNote(updateNotes);

        // Optionally delay finish to ensure toast visibility
        new Handler(Looper.getMainLooper()).postDelayed(this::finish, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.ic_delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this);

            View view = LayoutInflater.from(UpdateNotesActivity.this).inflate(R.layout.delete_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes, no;

            yes=  view.findViewById(R.id.delete_yes);
            no=  view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v->{
                notesViewModel.deleteNote(iid);
                finish();
            });

            no.setOnClickListener(v->{
                sheetDialog.dismiss();
            });


            sheetDialog.show();
        }
        return true;
    }
}