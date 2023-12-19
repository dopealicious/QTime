package Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private static int getCorrectAnswerIndex(List<String> options, String correctAnswer) {
        return options.indexOf(correctAnswer);
    }

    public static List<Question> readCsv(String filePath) {
        List<Question> questions = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
    
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
    
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                List<String> processedValues = new ArrayList<>();
                for (String value : values) {
                    // Menghilangkan tanda petik ganda pada awal dan akhir teks
                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }
                    processedValues.add(value);
                }
    
                String questionText = processedValues.get(0);
                List<String> options = processedValues.subList(1, 5);
                String correctAnswer = processedValues.get(5); // Mengambil jawaban yang benar dari kolom correctoption
                int correctAnswerIndex = getCorrectAnswerIndex(options, correctAnswer);
    
                CsvQuestion question = new CsvQuestion(questionText, options, correctAnswerIndex);
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return questions;
    }
    
}

