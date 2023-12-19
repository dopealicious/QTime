package View;

import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.CsvReader;
import Controller.DatabaseHandler;
import Controller.Question;
import Model.DashboardForm;

public class QuizPanel extends JPanel {
    private JLabel header;
    private JRadioButton option1;
    private JRadioButton option2;
    private JRadioButton option3;
    private JRadioButton option4;
    private JLabel questionlabel;
    private JLabel timelabel;
    private JButton nextbtn;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int correctAnswerIndex;
    private Timer timer;
    private int timeLeft;
    private JPanel timepanel;
    private ButtonGroup g;
    private int score;
    private int totalTimeSpent;
    private String username;

    DatabaseHandler db;

    public QuizPanel(String username) {
        this.username = username;
        initComponents();
        loadQuestions();
        initTimer();
        score = 0;
        totalTimeSpent = 0;
    }

    private void initTimer() {
        timeLeft = 30; // Waktu awal dalam detik
        timelabel.setText(String.format("%02d:%02d", timeLeft / 60, timeLeft % 60));

        ActionListener timerAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int intensity = 0;
                timeLeft--;

                if (timeLeft >= 0) {
                    totalTimeSpent++; // Increment total time spent
                    
                    System.out.println("Total time spent: " + totalTimeSpent);                   
                    if (timeLeft <= 10) {
                        int intensitytotal = intensity + (11 - timeLeft);
                        // Sesuaikan dengan intensitas yang diinginkan
                        int rgbintensity = 255 - intensitytotal * 23; 
                        timepanel.setBackground(new Color(255, rgbintensity, rgbintensity));
                    }                    
                    timelabel.setText(String.format("%02d:%02d", timeLeft / 60, timeLeft % 60));
                } else {
                    // Waktu habis, lanjut ke soal berikutnya
                    currentQuestionIndex++;
                    displayQuestion();
                    resetTimer();
                }
            }
        };

        timer = new Timer(1000, timerAction); // Timer berjalan setiap 1 detik
        timer.start();
    }

    private void resetTimer() {
        timeLeft = 30;
        timelabel.setText(String.format("%02d:%02d", timeLeft / 60, timeLeft % 60));
        timepanel.setBackground(Color.WHITE);
    }

    private void loadQuestions() {
        String filePath = "src\\Resources\\CSV\\question.csv";
        questions = CsvReader.readCsv(filePath);
        currentQuestionIndex = 0;
        
        System.out.println("Total questions: " + questions.size());

        // Menampilkan pertanyaan pertama di UI
        displayQuestion();
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
    
            // Set teks pertanyaan di questionlabel
            updateLabelWithText(questionlabel, currentQuestion.getQuestionText());
    
            clearSelection();
    
            // Set teks opsi jawaban di radio button
            setOptionText(option1, currentQuestion.getOptions().get(0));
            setOptionText(option2, currentQuestion.getOptions().get(1));
            setOptionText(option3, currentQuestion.getOptions().get(2));
            setOptionText(option4, currentQuestion.getOptions().get(3));
    
            // Set jawaban yang benar untuk pemeriksaan nanti
            correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();
            System.out.println("Soal: " + questions.get(currentQuestionIndex).getQuestionText());
            System.out.println("Jawaban Benar: " + questions.get(currentQuestionIndex).getCorrectAnswer());           
        } else {
            System.out.println("finishpanel");
            // Jika ini adalah soal terakhir, reset nilai-nilai dan pindah ke FinishPanel
            timer.stop();  // Stop timer
            checkAnswer();  // Check jawaban untuk soal terakhir
    
            // Pindah ke FinishPanel
            ((DashboardForm) SwingUtilities.getWindowAncestor(this)).switchToFinishPanel(username);
        }
    }    

    private void clearSelection() {
        ButtonModel selection = g.getSelection();
        if (selection != null) {
            g.clearSelection();
        }
    }

    private void checkAnswer() {
        // Check if the selected option matches the correct answer
        int selectedOptionIndex = -1;

        if (option1.isSelected()) {
            selectedOptionIndex = 0;
        } else if (option2.isSelected()) {
            selectedOptionIndex = 1;
        } else if (option3.isSelected()) {
            selectedOptionIndex = 2;
        } else if (option4.isSelected()) {
            selectedOptionIndex = 3;
        }

        if (selectedOptionIndex == correctAnswerIndex) {
            // User's answer is correct
            score += 1;
            System.out.println("Correct! Score: " + score);
        } else {
            System.out.println("Incorrect. Score: " + score);
        }
    }

    private void updateLabelWithText(JLabel label, String text) {
        // Split teks menjadi baris-baris
        String[] lines = text.split("\\r?\\n");
    
        // Mengatur teks label dengan paragraf
        StringBuilder labelText = new StringBuilder("<html>");
        for (String line : lines) {
            labelText.append(line).append("<br>");
        }
        labelText.append("</html>");
    
        // Set teks label
        label.setText(labelText.toString());
    }    

    private void setOptionText(JRadioButton option, String text) {
        // Set teks opsi dan atur visibilitas
        option.setText(text.trim());
        option.setVisible(!text.trim().isEmpty());
    }   
    
    public void adjustLayout(int frameWidth, int frameHeight) {
        setSize(frameWidth, frameHeight);

        // Set header position to center top
        int headerX = (frameWidth - header.getWidth()) / 2;
        int headerY = 40;
        header.setLocation(headerX, headerY);

        // Set questionLabel position and font size
        int questionLabelX = 40;
        int questionLabelY = headerY + header.getHeight() + 25;
        questionlabel.setLocation(questionLabelX, questionLabelY);
        questionlabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Set option positions and font size
        int optionX = 40;
        int optionY = questionLabelY + questionlabel.getHeight() + 25;
        option1.setLocation(optionX, optionY);
        option2.setLocation(optionX, optionY + option1.getHeight() + 10);
        option3.setLocation(optionX, optionY + (option1.getHeight() + 10) * 2);
        option4.setLocation(optionX, optionY + (option1.getHeight() + 10) * 3);

        Font optionFont = new Font("Segoe UI", Font.PLAIN, 14);
        option1.setFont(optionFont);
        option2.setFont(optionFont);
        option3.setFont(optionFont);
        option4.setFont(optionFont);

        // Set timelabel position and font size
        int timeLabelX = frameWidth - 110;
        int timeLabelY = headerY + header.getHeight() + 25;
        timelabel.setLocation(timeLabelX, timeLabelY);
        timelabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        int nextbtnX = 40;
        int nextbtnY = optionY + option4.getHeight() + 35;
        nextbtn.setLocation(nextbtnX,nextbtnY);
    }                            
    private void initComponents() {
        header = new JLabel();
        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();
        nextbtn = new JButton();
        questionlabel = new JLabel();
        timepanel = new JPanel();
        timelabel = new JLabel();
        
        g = new ButtonGroup();
        g.add(option1);
        g.add(option2);
        g.add(option3);
        g.add(option4);

        setBackground(new Color(255, 255, 255));

        header.setFont(new Font("Segoe UI", 1, 40)); // NOI18N
        header.setForeground(new Color(51, 51, 255));
        header.setText("QTime");

        option1.setBackground(new Color(255, 255, 255));
        option1.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        option1.setText("jRadioButton1");

        option2.setBackground(new Color(255, 255, 255));
        option2.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        option2.setText("jRadioButton5");

        option3.setBackground(new Color(255, 255, 255));
        option3.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        option3.setText("jRadioButton3");

        option4.setBackground(new Color(255, 255, 255));
        option4.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        option4.setText("jRadioButton4");

        nextbtn.setBackground(new Color(51, 51, 255));
        nextbtn.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        nextbtn.setForeground(new Color(255, 255, 255));
        nextbtn.setText("Next");
        nextbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timer.stop();
                checkAnswer();
                if (currentQuestionIndex < questions.size() - 1) {
                    // Jika masih ada soal berikutnya, lanjutkan ke soal berikutnya
                    currentQuestionIndex++;
                    displayQuestion();
                    resetTimer();
                    timer.start();                 
                }else{
                    int percentage = (score*100) / questions.size();
                    System.out.println("final score: "+percentage+"%");

                    DatabaseHandler databaseHandler = new DatabaseHandler();
                    int userId = databaseHandler.fetchUserId(username);
                    databaseHandler.insertScore(userId,percentage,totalTimeSpent);
                    System.out.println("User ID: " + userId);

                    score = 0;
                    totalTimeSpent = 0;
                    currentQuestionIndex = 0;
                
                    // Atur ulang tampilan pertanyaan pertama di UI
                    displayQuestion();
                
                    // Atur ulang timer
                    resetTimer();
                    ((DashboardForm) SwingUtilities.getWindowAncestor(QuizPanel.this)).switchToFinishPanel(username);
                }
            }
        });        

        questionlabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
        questionlabel.setText("jLabel1");

        timepanel.setBackground(new Color(255, 255, 255));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        timepanel.setBorder(border);

        timelabel.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        timelabel.setText("jLabel1");

        javax.swing.GroupLayout timepanelLayout = new javax.swing.GroupLayout(timepanel);
        timepanel.setLayout(timepanelLayout);
        timepanelLayout.setHorizontalGroup(
            timepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timepanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(timelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        timepanelLayout.setVerticalGroup(
            timepanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timepanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(timelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(header)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(option1, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)
                            .addComponent(option2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(option3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(option4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nextbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(questionlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(timepanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header)
                .addGap(40, 40, 40)
                .addComponent(timepanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(questionlabel)
                .addGap(25, 25, 25)
                .addComponent(option1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(option2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(option3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(option4)
                .addGap(35, 35, 35)
                .addComponent(nextbtn)
                .addContainerGap(91, Short.MAX_VALUE))
        );
    }                  
}