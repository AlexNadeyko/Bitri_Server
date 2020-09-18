package MessagesClientServer;

import java.io.Serializable;

public class BasicMessage implements Serializable {

    private TypeOfInnerMessage typeOfInnerMessage;
    private InnerMessage innerMessage;

    private static final long serialVersionUID = 6529685098267757690L;


    public BasicMessage(TypeOfInnerMessage typeOfInnerMessage, InnerMessage innerMessage){
        this.typeOfInnerMessage = typeOfInnerMessage;
        this.innerMessage = innerMessage;
    }


    public TypeOfInnerMessage getTypeOfInnerMessage(){
        return typeOfInnerMessage;
    }

    public InnerMessage getInnerMessage(){
        return innerMessage;
    }



}
