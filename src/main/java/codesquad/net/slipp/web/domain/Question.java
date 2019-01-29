package codesquad.net.slipp.web.domain;

public class Question {

    private long id;

    private String writer;
    private String title;
    private String contents;


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String toString() {
        return "Question{" +
                "writer : " + writer +
                ", title : " + title +
                ", contents :" + contents +
                " }";
    }
}
