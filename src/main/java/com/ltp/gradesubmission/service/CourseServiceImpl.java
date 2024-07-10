package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    StudentRepository studentRepository;

    @Override
    public Course getCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            return course.get();
        } else {
            throw new CourseNotFoundException(id);
        }

    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course addStudentToCourse(Long studentId, Long courseId) {

        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            Optional<Student> student = studentRepository.findById(studentId);
            if (student.isPresent()) {
                course.get().getStudents().add(student.get());
                return courseRepository.save(course.get());

            } else {
                throw new StudentNotFoundException(studentId);
            }

        } else {
            throw new CourseNotFoundException(courseId);
        }

    }

    @Override
    public Set<Student> getEnrolledStudents(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            return course.get().getStudents();

        } else {
            throw new CourseNotFoundException(id);
        }

    }

}
