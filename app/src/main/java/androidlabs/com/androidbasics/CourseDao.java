package androidlabs.com.androidbasics;

import java.util.List;

public interface CourseDao {
    List<CourseEntity> getCoursesByDay(String day);

    CourseEntity getCourseById(int id);

    List<CourseEntity> getAllCourses();

    int updateCourse(CourseEntity courseEntity, int id );

    void deleteCourse(CourseEntity courseEntity);

    int addCourse(CourseEntity courseEntity);

    void deleteAllCourses();
}
