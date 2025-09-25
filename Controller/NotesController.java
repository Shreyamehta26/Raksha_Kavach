package com.raksha_kavach.Controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.raksha_kavach.dao.NotesDao;
import com.raksha_kavach.model.Notes;

public class NotesController {
    private NotesDao c2w_ai_NotesDao=new NotesDao();
    public void addNote(Notes c2w_ai_Notes){
        try{
            c2w_ai_NotesDao.addData("notes",
            String.valueOf(System.currentTimeMillis()),c2w_ai_Notes);
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
    }
    public List<Notes> getAllNotesForUser(String c2w_ai_userName){
        try{
            return c2w_ai_NotesDao.getDataList("notes", c2w_ai_userName);
        }catch(ExecutionException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return List.of();
    }
}

