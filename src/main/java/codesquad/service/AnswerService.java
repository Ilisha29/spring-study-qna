package codesquad.service;

import codesquad.exception.AnswerNotFoundException;
import codesquad.exception.QuestionNotFoundException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import codesquad.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public void saveAnswer(HttpSession session, Long questionId, String contents) {
        Answer answer = new Answer(HttpSessionUtils.getSessionedUser(session), findQuestion(questionId), contents);
        answer.setTime(TimeUtils.getCurrentTime());

        answerRepository.save(answer);
    }

    public boolean isSameWriter(Long id, HttpSession session) {
        return findQuestion(id).isSameWriter(HttpSessionUtils.getSessionedUser(session));
    }

    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
        answer.delete();
        answerRepository.save(answer);
    }

    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }

}
