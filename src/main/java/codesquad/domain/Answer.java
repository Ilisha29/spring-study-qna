package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
  private User writer;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
  private Question question;

  private String contents;

  @CreationTimestamp
  private LocalDateTime createdDate;
  @UpdateTimestamp
  private LocalDateTime updatedDate;

  public String getTime() {
    if (updatedDate == null) {
      return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    return updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

  public void update(Answer updatedAnswer) {
    this.contents = updatedAnswer.contents;
  }

  public void checkWriter(User user) {
    if (user == null || !writer.isSame(user)) {
      throw new IllegalStateException("다른 사용자 입니다");
    }
  }
}
