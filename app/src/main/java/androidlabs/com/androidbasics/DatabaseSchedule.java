package androidlabs.com.androidbasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSchedule extends SQLiteOpenHelper implements CourseDao {
    private static final int DATABASE_VERSION = 9;
    private SQLiteDatabase sqLiteDatabase;
    public DatabaseSchedule database;

    private static final String DATABASE_NAME = "student_schedule";
    private static final String TABLE_COURSE = "course";
    private static final String TABLE_INSTRUCTOR = "instructor";
    private static final String TABLE_SCHEDULE = "schedule";

    private static final String COURSE_ID = "id";
    private static final String COURSE_SUBJECT = "subject_name";
    private static final String COURSE_CREDITS = "credits";

    private static final String COURSE_GRADE = "grade";

    private static final String SCHEDULE_ID = "schedule_id";
    private static final String SCHEDULE_DATE = "date";
    private static final String SCHEDULE_DAY_OF_THE_WEAK = "day_of_the_weak";


    private static final String INSTRUCTOR_ID = "instructor_id";
    private static final String INSTRUCTOR_LASTNAME = "instructor_lastname";

    private static final String CREATE_TABLE_INSTRUCTOR = "CREATE TABLE "
            + TABLE_INSTRUCTOR + "(" + INSTRUCTOR_ID + " INTEGER PRIMARY KEY autoincrement," + INSTRUCTOR_LASTNAME
            + " TEXT )";
    private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE " + TABLE_SCHEDULE + "(" + SCHEDULE_ID +
            " INTEGER PRIMARY KEY autoincrement, " + SCHEDULE_DATE + " TEXT , " + SCHEDULE_DAY_OF_THE_WEAK + " TEXT)";

    private static final String CREATE_TABLE_COURSE = "CREATE TABLE course( id integer primary key autoincrement, " +
            "subject_name TEXT ," + INSTRUCTOR_ID + " INTEGER ,credits text ," + SCHEDULE_ID + " INTEGER, grade TEXT )";

    public DatabaseSchedule(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        sqLiteDatabase.execSQL(CREATE_TABLE_INSTRUCTOR);
        sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE);
        sqLiteDatabase.execSQL(CREATE_TABLE_COURSE);

        Log.i("SQLITE", "Database created");


        initializeDB();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTOR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);


        onCreate(sqLiteDatabase);
    }

    /**
     * Initialize {@value DATABASE_NAME) with data.
     */
    private void initializeDB() {
        initializeTableSchedule("Monday", "36:05:2018", sqLiteDatabase);
        initializeTableInstructor("Ivanov", sqLiteDatabase);
        initializeTableCourse(sqLiteDatabase, "English", 1, "34", 1, "5");

    }

    /**
     * Initialize {@value TABLE_INSTRUCTOR} with data.
     *
     * @param instructor_lastname from {@value TABLE_INSTRUCTOR}
     * @param db                  {@link SQLiteDatabase}
     */
    private void initializeTableInstructor(String instructor_lastname, SQLiteDatabase db) {
        ContentValues instructorValues = new ContentValues();
        instructorValues.put(INSTRUCTOR_LASTNAME, instructor_lastname);
        db.insert(TABLE_INSTRUCTOR, null, instructorValues);

    }

    /**
     * Initialize {@value TABLE_SCHEDULE} with data.
     *
     * @param day_of_the_weak {@value SCHEDULE_DAY_OF_THE_WEAK}
     * @param date            {@value SCHEDULE_DATE}
     * @param db              {@link SQLiteDatabase}
     */
    private void initializeTableSchedule(String day_of_the_weak, String date, SQLiteDatabase db) {
        ContentValues scheduleValues = new ContentValues();
        scheduleValues.put(SCHEDULE_DATE, date);
        scheduleValues.put(SCHEDULE_DAY_OF_THE_WEAK, day_of_the_weak);
        db.insert(TABLE_SCHEDULE, null, scheduleValues);
    }

    /**
     * Initialize {@value TABLE_COURSE} with data.
     *
     * @param db             {@link SQLiteDatabase}
     * @param subject        {@value COURSE_SUBJECT}
     * @param instructor_id  {@value INSTRUCTOR_ID}
     * @param course_credits {@value COURSE_CREDITS}
     * @param schedule_id    {@value SCHEDULE_ID}
     * @param course_grade   {@value COURSE_GRADE}
     */
    private void initializeTableCourse(SQLiteDatabase db, String subject, int instructor_id, String course_credits, int schedule_id, String course_grade) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_SUBJECT, subject);
        contentValues.put(INSTRUCTOR_ID, instructor_id);
        contentValues.put(COURSE_CREDITS, course_credits);
        contentValues.put(SCHEDULE_ID, schedule_id);
        contentValues.put(COURSE_GRADE, course_grade);
        db.insert(TABLE_COURSE, null, contentValues);
        db.close();
    }


    /**
     * Get list of courses by day.
     *
     * @param day {@value SCHEDULE_DAY_OF_THE_WEAK}
     * @return list of {@link CourseEntity}
     */
    @Override
    public List<CourseEntity> getCoursesByDay(String day) {
        List<CourseEntity> courses = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectCourse = "SELECT * FROM " + TABLE_COURSE + " WHERE " + SCHEDULE_ID + " IN" + "(SELECT " + SCHEDULE_ID + " FROM " + TABLE_SCHEDULE +
                " WHERE " + SCHEDULE_DAY_OF_THE_WEAK + " = '" + day + "' )";

        Cursor cursor = sqLiteDatabase.rawQuery(selectCourse, null);
        if (cursor.moveToFirst() && cursor != null) {
            do {
                CourseEntity courseEntity = new CourseEntity();
                courseEntity.setCourse_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COURSE_ID))));
                courseEntity.setCourse_subject(cursor.getString(cursor.getColumnIndex(COURSE_SUBJECT)));
                courseEntity.setCredits(cursor.getString(cursor.getColumnIndex(COURSE_CREDITS)));
                courseEntity.setStudent_grade(cursor.getString(cursor.getColumnIndex(COURSE_GRADE)));

                int schedule_id = cursor.getInt(cursor.getColumnIndex(SCHEDULE_ID));
                Log.i("Cursor", String.valueOf(schedule_id));
                int instructor_id = cursor.getInt(cursor.getColumnIndex(INSTRUCTOR_ID));

                String selectSchedule = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE " + SCHEDULE_ID + "= " + schedule_id;
                String selectInstructor = "SELECT * FROM " + TABLE_INSTRUCTOR + " WHERE " +
                        INSTRUCTOR_ID + " = " + instructor_id;
                Cursor instructor_cursor = sqLiteDatabase.rawQuery(selectInstructor, null);
                if (instructor_cursor != null && instructor_cursor.moveToFirst()) {

                    courseEntity.setCourse_instructor(instructor_cursor.getString
                            (instructor_cursor.getColumnIndex(INSTRUCTOR_LASTNAME)));
                    if (instructor_cursor != null) {
                        instructor_cursor.close();
                    }

                }
                Cursor schedule_cursor = sqLiteDatabase.rawQuery(selectSchedule, null);
                if (schedule_cursor.moveToFirst() && schedule_cursor != null) {
                    courseEntity.setSchedule(schedule_cursor.getString(schedule_cursor.getColumnIndex(SCHEDULE_DAY_OF_THE_WEAK)));
                    if (schedule_cursor != null) {
                        schedule_cursor.close();
                    }
                }
                courses.add(courseEntity);

            } while (cursor.moveToNext());


        } else {
            Log.e("Error", "cursor is null");
        }
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();
        return courses;
    }

    /**
     * Get all courses from {@value DATABASE_NAME}
     *
     * @return list of {@link CourseEntity}
     */
    @Override
    public List<CourseEntity> getAllCourses() {

        List<CourseEntity> courses = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String selectCourse = "SELECT * FROM " + TABLE_COURSE;

        Cursor cursor = sqLiteDatabase.rawQuery(selectCourse, null);
        if (cursor.moveToFirst()) {
            do {
                CourseEntity courseEntity = new CourseEntity();
                courseEntity.setCourse_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COURSE_ID))));
                courseEntity.setCourse_subject(cursor.getString(cursor.getColumnIndex(COURSE_SUBJECT)));
                courseEntity.setCredits(cursor.getString(cursor.getColumnIndex(COURSE_CREDITS)));
                courseEntity.setStudent_grade(cursor.getString(cursor.getColumnIndex(COURSE_GRADE)));

                int schedule_id = cursor.getInt(cursor.getColumnIndex(SCHEDULE_ID));
                Log.i("Cursor", String.valueOf(schedule_id));
                int instructor_id = cursor.getInt(cursor.getColumnIndex(INSTRUCTOR_ID));

                String selectSchedule = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE " + SCHEDULE_ID + "= " + schedule_id;
                String selectInstructor = "SELECT * FROM " + TABLE_INSTRUCTOR + " WHERE " +
                        INSTRUCTOR_ID + " = " + instructor_id;
                Cursor instructor_cursor = sqLiteDatabase.rawQuery(selectInstructor, null);
                if (instructor_cursor != null && instructor_cursor.moveToFirst()) {

                    courseEntity.setCourse_instructor(instructor_cursor.getString
                            (instructor_cursor.getColumnIndex(INSTRUCTOR_LASTNAME)));
                    if (instructor_cursor != null) {
                        instructor_cursor.close();
                    }
                }
                Cursor schedule_cursor = sqLiteDatabase.rawQuery(selectSchedule, null);
                if (schedule_cursor.moveToFirst() && schedule_cursor != null) {
                    courseEntity.setSchedule(schedule_cursor.getString(schedule_cursor.getColumnIndex(SCHEDULE_DAY_OF_THE_WEAK)));
                    if (schedule_cursor != null) {
                        schedule_cursor.close();
                    }
                }
                courses.add(courseEntity);

            } while (cursor.moveToNext());


        } else {
            return null;
        }
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();

        return courses;
    }


    /**
     * Update {@link CourseEntity} by course id in database {@value DATABASE_NAME}
     *
     * @param courseEntity
     * @param id
     * @return
     */
    @Override
    public int updateCourse(CourseEntity courseEntity, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_SUBJECT, courseEntity.getCourse_subject());
        contentValues.put(COURSE_CREDITS, courseEntity.getCredits());
        contentValues.put(COURSE_GRADE, courseEntity.getStudent_grade());

        String queryInstructor = "SELECT " + INSTRUCTOR_ID + " FROM " + TABLE_INSTRUCTOR + " WHERE " +
                INSTRUCTOR_LASTNAME + " = '" + courseEntity.getCourse_instructor() + "'";
        String querySchedule = "SELECT " + SCHEDULE_ID + " FROM " + TABLE_SCHEDULE + " WHERE " +
                SCHEDULE_DAY_OF_THE_WEAK + " = '" + courseEntity.getSchedule() + "'";

        int instructor_id = 0;
        int schedule_id = 0;


        Cursor cursor_instructor = db.rawQuery(queryInstructor, null);


        Log.d("ddee", String.valueOf(cursor_instructor.getCount()));
        if (cursor_instructor.moveToFirst()) {
            instructor_id = cursor_instructor.getInt(cursor_instructor.getColumnIndex(INSTRUCTOR_ID));
            contentValues.put(INSTRUCTOR_ID, instructor_id);
            if (cursor_instructor != null) {
                cursor_instructor.close();
            }
        } else {
            ContentValues instructorValues = new ContentValues();
            instructorValues.put(INSTRUCTOR_LASTNAME, courseEntity.getCourse_instructor());
            instructor_id = (int) db.insert(TABLE_INSTRUCTOR, null, instructorValues);
            contentValues.put(INSTRUCTOR_ID, instructor_id);
        }
        Cursor cursor_schedule = db.rawQuery(querySchedule, null);
        if (cursor_schedule != null && cursor_schedule.moveToFirst()) {
            schedule_id = cursor_schedule.getInt(cursor_schedule.getColumnIndex(SCHEDULE_ID));
            contentValues.put(SCHEDULE_ID, schedule_id);
            if (cursor_schedule != null) {
                cursor_schedule.close();
            }
        } else {
            ContentValues scheduleValues = new ContentValues();
            scheduleValues.put(SCHEDULE_DAY_OF_THE_WEAK, courseEntity.getSchedule());
            scheduleValues.put(SCHEDULE_DATE, "");
            schedule_id = (int) db.insert(TABLE_SCHEDULE, null, scheduleValues);
            contentValues.put(SCHEDULE_ID, schedule_id);
        }

        int id_Course = (int) db.update(TABLE_COURSE, contentValues, COURSE_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return id_Course;


    }

    /**
     * Delete all courses from the {@value DATABASE_NAME}
     */
    @Override
    public void deleteAllCourses() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, null, null);

        db.close();
    }

    /**
     * Delete {@link CourseEntity} from {@value DATABASE_NAME}
     *
     * @param courseEntity {@link CourseEntity}
     */
    @Override
    public void deleteCourse(CourseEntity courseEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, COURSE_ID + " = ?", new String[]{String.valueOf(courseEntity.getCourse_id())});

        db.close();

    }

    /**
     * Add {@link CourseEntity} in the {@value DATABASE_NAME}
     *
     * @param courseEntity {@link CourseEntity}
     */
    @Override
    public int addCourse(CourseEntity courseEntity) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_SUBJECT, courseEntity.getCourse_subject());
        contentValues.put(COURSE_CREDITS, courseEntity.getCredits());
        contentValues.put(COURSE_GRADE, courseEntity.getStudent_grade());

        String queryInstructor = "SELECT " + INSTRUCTOR_ID + " FROM " + TABLE_INSTRUCTOR + " WHERE " +
                INSTRUCTOR_LASTNAME + " = '" + courseEntity.getCourse_instructor() + "'";
        String querySchedule = "SELECT " + SCHEDULE_ID + " FROM " + TABLE_SCHEDULE + " WHERE " +
                SCHEDULE_DAY_OF_THE_WEAK + " = '" + courseEntity.getSchedule() + "'";

        int instructor_id = 0;
        int schedule_id = 0;
        Cursor cursor_instructor = sqLiteDatabase.rawQuery(queryInstructor, null);
        if (cursor_instructor != null && cursor_instructor.moveToFirst()) {
            instructor_id = cursor_instructor.getInt(cursor_instructor.getColumnIndex(INSTRUCTOR_ID));
            contentValues.put(INSTRUCTOR_ID, instructor_id);
            if (cursor_instructor != null) {
                cursor_instructor.close();
            }
        } else {
            ContentValues instructorValues = new ContentValues();
            instructorValues.put(INSTRUCTOR_LASTNAME, courseEntity.getCourse_instructor());
            instructor_id = (int) sqLiteDatabase.insert(TABLE_INSTRUCTOR, null, instructorValues);
            contentValues.put(INSTRUCTOR_ID, instructor_id);
        }

        Cursor cursor_schedule = sqLiteDatabase.rawQuery(querySchedule, null);
        if (cursor_schedule != null && cursor_schedule.moveToFirst()) {
            schedule_id = cursor_schedule.getInt(cursor_schedule.getColumnIndex(SCHEDULE_ID));
            contentValues.put(SCHEDULE_ID, schedule_id);
            if (cursor_schedule != null) {
                cursor_schedule.close();
            }
        } else {
            ContentValues scheduleValues = new ContentValues();
            scheduleValues.put(SCHEDULE_DAY_OF_THE_WEAK, courseEntity.getSchedule());
            scheduleValues.put(SCHEDULE_DATE, "");
            schedule_id = (int) sqLiteDatabase.insert(TABLE_SCHEDULE, null, scheduleValues);
            contentValues.put(SCHEDULE_ID, schedule_id);
        }
        long success = sqLiteDatabase.insert(TABLE_COURSE, null, contentValues);
        Log.e("SQL", String.valueOf(success));
        sqLiteDatabase.close();

        return (int) success;
    }

    /**
     * Get {@link CourseEntity} from {@value DATABASE_NAME} by id.
     *
     * @param id {@value COURSE_ID}
     * @return {@link CourseEntity}
     */
    @Override
    public CourseEntity getCourseById(int id) {
        CourseEntity courseEntity = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_COURSE, new String[]{COURSE_ID, COURSE_SUBJECT, INSTRUCTOR_ID, COURSE_CREDITS,
                        SCHEDULE_ID, COURSE_GRADE},
                "id = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            courseEntity = new CourseEntity();
            courseEntity.setCourse_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COURSE_ID))));
            courseEntity.setCourse_subject(cursor.getString(cursor.getColumnIndex(COURSE_SUBJECT)));
            courseEntity.setCredits(cursor.getString(cursor.getColumnIndex(COURSE_CREDITS)));
            courseEntity.setStudent_grade(cursor.getString(cursor.getColumnIndex(COURSE_GRADE)));
            int schedule_id = cursor.getInt(cursor.getColumnIndex(SCHEDULE_ID));
            Log.i("Cursor", String.valueOf(schedule_id));
            int instructor_id = cursor.getInt(cursor.getColumnIndex(INSTRUCTOR_ID));

            String selectSchedule = "SELECT * FROM " + TABLE_SCHEDULE + " WHERE " + SCHEDULE_ID + "= " + schedule_id;
            String selectInstructor = "SELECT * FROM " + TABLE_INSTRUCTOR + " WHERE " +
                    INSTRUCTOR_ID + " = " + instructor_id;
            Cursor instructor_cursor = sqLiteDatabase.rawQuery(selectInstructor, null);
            if (instructor_cursor != null && instructor_cursor.moveToFirst()) {

                do {
                    courseEntity.setCourse_instructor(instructor_cursor.getString
                            (instructor_cursor.getColumnIndex(INSTRUCTOR_LASTNAME)));

                }
                while (instructor_cursor.moveToNext());
            }
            if (instructor_cursor != null) {
                instructor_cursor.close();
            }
            Cursor schedule_cursor = sqLiteDatabase.rawQuery(selectSchedule, null);
            if (schedule_cursor.moveToFirst() && schedule_cursor != null) {

                do {
                    courseEntity.setSchedule(schedule_cursor.getString(schedule_cursor.getColumnIndex(SCHEDULE_DAY_OF_THE_WEAK)));
                } while (schedule_cursor.moveToNext());
            }
            if (schedule_cursor != null) {
                schedule_cursor.close();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();
        return courseEntity;
    }
}
