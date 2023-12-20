package Controller;

import java.util.List;

public class CsvQuestion extends Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public CsvQuestion(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public List<String> getOptions() {
        return options;
    }

    @Override
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    @Override
    public String getCorrectAnswer() {
        return options.get(correctAnswerIndex);
    }
}
