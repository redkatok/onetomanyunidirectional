package app.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "university")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public University(String name) {
        this.name = name;
    }


    //TODO -  в отличии от политики генерации колонки с внешними ключами в связи one-to-one
    // ,в one-to-many использование аннотации  JoinColumn в owner сущности
    //  приводит к генерации колонки с внешними ключами в противоположной сущности - Student
    //orphan removal запрещает хранить в student таблице записи без ссылки на  запись в таблице unversity и
    // если записи без ссылки существовали до введния политики orphan removal то они будут  удалены , а также удаление записи из таблицы
    //университета приведет к удалению всех связанных с ней записей из таблицы  student
    //cascade = CascadeType.ALL - помимо удаления он также позволяет сохранять в бд transient(связанные несохраненные временные экземпляры) student
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "university_id")
    private List<Student> students = new ArrayList<>();

    public University(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }
}
