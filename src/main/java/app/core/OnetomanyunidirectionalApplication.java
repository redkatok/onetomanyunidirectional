package app.core;

import app.core.model.Student;
import app.core.model.University;
import app.core.repo.StudentRepository;
import app.core.repo.UniversityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class OnetomanyunidirectionalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OnetomanyunidirectionalApplication.class, args);
        UniversityRepository universityRepository = context.getBean(UniversityRepository.class);
        StudentRepository studentRepository = context.getBean(StudentRepository.class);

        Student student1 = new Student("0001");
        Student student2 = new Student("0002");
        Student student3 = new Student("0003");

        List<Student> students = Arrays.asList(student1, student2, student3);

        University university = new University("Oxford", students);
        //пример работы cascade=ALL- при сохранении унивреситета - засеченные в него студенты тоже сохрантся / без cascade.ALl
        // будет TransientObjectException
        universityRepository.save(university);


        //приммер работы orphan
        Optional<University> optionalUniversity = universityRepository.findById(1L);
        universityRepository.delete(optionalUniversity.get());
        System.out.println("==END==");

    }

}
