package codesquad.net.slipp.web.utils;


import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.exception.SessionNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotMatchException;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String SESSION_KEY = "userSession";

    public static boolean isLogin(HttpSession session) {
        Object sessionUser = session.getAttribute(SESSION_KEY);
        if (sessionUser == null) {

            throw new SessionNotFoundException();
        }
        
        return true;
    }

    public static User getSessionUser(HttpSession session) {
        isLogin(session);

        return (User) session.getAttribute(SESSION_KEY);
    }

    public static boolean isSessionMatch(HttpSession session, User user) {
        User sessiondUser = getSessionUser(session);
        if (!sessiondUser.match(user)) {

            throw new SessionNotMatchException();
        }
        return true;
    }
}
