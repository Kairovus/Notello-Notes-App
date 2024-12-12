package com.example.notesapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesapp.Dao.NotesDao;
import com.example.notesapp.Database.NotesDatabase;
import com.example.notesapp.Model.Notes;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotesRepository {

    public LiveData<List<Notes>> getallNotes;
    private NotesDao notesDao;
    private ExecutorService executor;

    public NotesRepository(Application application) {
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getallNotes = notesDao.getallNotes();
        executor = Executors.newSingleThreadExecutor(); // Initialize the Executor
    }

    public void insertNotes(Notes notes) {
        executor.execute(() -> notesDao.insertNotes(notes)); // Perform insert on background thread
    }

    public void deleteNotes(int id) {
        executor.execute(() -> notesDao.deleteNotes(id)); // Perform delete on background thread
    }

    public void updateNotes(Notes notes) {
        executor.execute(() -> notesDao.updateNotes(notes)); // Perform update on background thread
    }
}
