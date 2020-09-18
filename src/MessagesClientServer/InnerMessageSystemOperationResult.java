package MessagesClientServer;

import java.io.Serializable;

public class InnerMessageSystemOperationResult implements InnerMessage, Serializable {

    private boolean answer;
    private String description;

    private static final long serialVersionUID = 6529685098267757692L;


    public InnerMessageSystemOperationResult(boolean answer, String description){
        this.answer = answer;
        this.description = description;
    }


    public boolean getAnswer() {
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
