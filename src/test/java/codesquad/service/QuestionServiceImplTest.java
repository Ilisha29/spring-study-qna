package codesquad.service;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.QuestionRepository;
import codesquad.util.HttpSessionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceImplTest {

    private MockHttpSession session;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @Before
    public void setUptQuestions() {
        session = new MockHttpSession();
        session.setAttribute("sessionedUser", userService.findUser(Integer.toUnsignedLong(1)));
        System.out.println(HttpSessionUtils.getUserFromSession(session).toString());
        Question question = new Question().builder()
                .title("tmpTilte")
                .contents("asdadasdasdasdasdas")
                .user(HttpSessionUtils.getUserFromSession(session))
                .build();
        questionRepository.save(question);
        System.out.println(question.toString());
    }

    @Test
    public void findQuestions() {
        System.out.println(questionService.findQuestion(Integer.toUnsignedLong(1)).toString());
        assertNotNull(questionService.findQuestion(Integer.toUnsignedLong(1)));
    }

    @Test
    public void create() {
        List<Question> beforeQuestionList = (List<Question>) questionRepository.findAll();
        questionService.create("titititititi", "conconocnocnocon", session);
        List<Question> afterQuestionList = (List<Question>) questionRepository.findAll();
        assertEquals(afterQuestionList.size() - beforeQuestionList.size(), 1);
    }

    @Test
    public void findQuestion() {
        assertNotNull(questionService.findQuestion(Integer.toUnsignedLong(1)));
    }

    @Test
    public void checkUserId() {
        assertTrue(questionService.checkUserId(Integer.toUnsignedLong(1), session));
    }

    @Test
    public void updateQuestionInfo() {
        Long id = Integer.toUnsignedLong(1);
        String changeTitle = "changedTitle";
        String changeContents = "changedConCONConCOn";
        questionService.updateQuestionInfo(id, changeTitle, changeContents);
        Question changedQuestion = questionService.findQuestion(id);
        assertEquals(changedQuestion.getTitle(), changeTitle);
        assertEquals(changedQuestion.getContents(), changeContents);
    }

    @Test
    public void deleteQuestion() {
        questionService.create("titititititi", "conconocnocnocon", session);
        List<Question> beforeQuestionList = (List<Question>) questionRepository.findAll();
        questionService.deleteQuestion(Integer.toUnsignedLong(2));
        List<Question> afterQuestionList = (List<Question>) questionRepository.findAll();
        assertEquals(beforeQuestionList.size() - afterQuestionList.size(), 1);
    }
}