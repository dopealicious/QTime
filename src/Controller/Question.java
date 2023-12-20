package Controller;

import java.util.List;

public abstract class Question {
    public abstract String getQuestionText();
    public abstract List<String> getOptions();
    public abstract int getCorrectAnswerIndex();
    public abstract String getCorrectAnswer();
}


