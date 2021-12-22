package com.example.myscheduler;

import java.util.Date;

public class DatabaseStructure {

    public static class Category{
        int CATEGORY_ID;
        String CATEGORY_NAME;
        int CATEGORY_PRIORITY;
        boolean CATEGORY_ACTIVE;
    }

    public static class Note{
        int NOTE_ID;
        String NOTE_TITLE;
        String NOTE_DESCRIPTION;
        Date NOTE_DATE;
    }

    public static class Task{
        int TASK_ID;
        String TASK_NAME;
        int TASK_CATEGORY;
        Date TASK_DATE;
        boolean TASK_ACTIVE;
    }
}
