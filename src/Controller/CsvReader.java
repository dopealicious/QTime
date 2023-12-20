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
            boolean firstLine = true; // Tambahkan variabel ini
    
            while ((line = br.readLine()) != null) {
                // Tambahkan blok berikut untuk mengabaikan baris pertama
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
    
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    
                // Mengatasi koma yang ada di dalam tanda petik ganda
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
                String correctAnswer = processedValues.get(5);
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

