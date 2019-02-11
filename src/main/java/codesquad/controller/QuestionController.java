package codesquad.controller;

import codesquad.model.Question;
import codesquad.utils.OptionalProcessor;
import codesquad.utils.SessionChecker;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SessionChecker sessionChecker;

    @Autowired
    private OptionalProcessor optionalProcessor;

    @GetMapping("/form")
    public String questionForm(HttpSession session, Model model) {

        if (!sessionChecker.isThisSessionedWasLoggedin(session)) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("user", sessionChecker.loggedinUser(session));
        return "qna/form";
    }

    @PostMapping("/questions")
    public String quest(Question question, HttpSession session) {
        question.setWriter(sessionChecker.loggedinUser(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String bringQuestionsList(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "qna/list";
    }

    @GetMapping("/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/show";
    }

    @GetMapping("/{id}/updateForm")
    public String questionUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!sessionChecker.isThisSessionedWasLoggedin(session)) {
            return "redirect:/users/loginForm";
        }
        if (!sessionChecker.loggedinUser(session)
                .isWriterIsSame(optionalProcessor
                        .optionalToQuestion(id))) {
            return "/utils/authenticationError";
        }
        model.addAttribute("question", questionRepository.findById(id).get());
        return "qna/updateForm";
    }


    @PutMapping("/{id}")
    public String updateQuestion(@PathVariable Long id, Question question, HttpSession session) {
        if (!sessionChecker.isThisSessionedWasLoggedin(session)) {
            return "redirect:/users/login";
        }
        if (!sessionChecker.loggedinUser(session).isWriterIsSame(optionalProcessor.optionalToQuestion(id))) {
            return "redirect:/users/login";
        }
        questionRepository.save(optionalProcessor.optionalToQuestion(id));
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, Question question, HttpSession session) {
        if (!sessionChecker.isThisSessionedWasLoggedin(session)) {
            return "redirect:/users/login";
        }
        if (!sessionChecker.loggedinUser(session).isWriterIsSame(optionalProcessor.optionalToQuestion(id))) {
            return "redirect:/users/login";
        }
        questionRepository.delete(optionalProcessor.optionalToQuestion(id));
        return "redirect:/";
    }
}
