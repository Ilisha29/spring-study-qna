package codesquad.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_parent_id"))
    private Question question;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private boolean delete;

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.delete = false;
    }

    public void delete() {
        this.delete = true;
    }

    public boolean isSameWriter(User sessionedUser) {
        return this.writer.isSameUser(sessionedUser.getId());
    }
}
