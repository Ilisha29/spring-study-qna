package codesquad.service;

import codesquad.domain.Question;
import codesquad.repository.QuestionRepository;
import codesquad.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Iterable<Question> findQuestions() {
        return questionRepository.findAll();
    }

    public void create(String title, String contents, HttpSession session) {
        questionRepository.save(new Question().builder().user(HttpSessionUtils.getUserFromSession(session)).title(title).contents(contents).build());
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id).get();
    }

    public boolean checkUserId(Long id, HttpSession session) {
        return findQuestion(id).isSameUserId(HttpSessionUtils.getUserFromSession(session));
    }

    public void updateQuestionInfo(Long id, String title, String contents) {
        Question question = findQuestion(id);
        question.changeQuestionInfo(title, contents);
        questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
