package MessagesClientServer;

import java.io.Serializable;

public class InnerMessageSystemYN implements InnerMessage, Serializable {

    private boolean answer;
    private String description;


    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptionr(String answer) {
        this.description = description;
    }
}
