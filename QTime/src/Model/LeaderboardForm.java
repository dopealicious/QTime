package Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import Controller.DatabaseHandler;
import java.awt.*;


public class LeaderboardForm extends JFrame {
    private JLabel leaderboardframe;
    private JList<String> leaderboardlist;
    private JPanel leaderboardpanel;
    private JScrollPane scrollpane;
    private DatabaseHandler databaseHandler;
    private JTable leaderboardTable;   

    public LeaderboardForm() {
        initComponents();
        leaderboardTable = new JTable();
        databaseHandler = new DatabaseHandler();
        initLeaderboardTable();

        setIconImage();
        setResizable(false);         
    }

    private void setIconImage(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resources/Assets/qtime-logo.png")));
    }
    
    private void initLeaderboardTable(){
        DefaultTableModel tablemodel = new DefaultTableModel();
        tablemodel.addColumn("Rank");
        tablemodel.addColumn("Name");
        tablemodel.addColumn("Score");
        tablemodel.addColumn("Time");
    
        List<String[]> leaderboardData = databaseHandler.getTotalScore();
        int rank = 1;
        for (String[] entry : leaderboardData) {
            String[] datawithRank = {String.valueOf(rank), entry[0], entry[1], entry[2]};
            tablemodel.addRow(datawithRank);
            rank++;
        }
    
        leaderboardTable.setModel(tablemodel); // Menetapkan model untuk leaderboardTable
        scrollpane.setViewportView(leaderboardTable);
    }

    private void initComponents() {

        leaderboardpanel = new JPanel();
        leaderboardframe = new JLabel();
        scrollpane = new JScrollPane();
        leaderboardlist = new JList<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        leaderboardpanel.setBackground(new Color(51, 51, 255));

        leaderboardframe.setFont(new Font("Segoe UI", 1, 24)); // NOI18N
        leaderboardframe.setForeground(new Color(255, 255, 255));
        leaderboardframe.setText("Leaderboard");

        GroupLayout leaderboardpanelLayout = new GroupLayout(leaderboardpanel);
        leaderboardpanel.setLayout(leaderboardpanelLayout);
        leaderboardpanelLayout.setHorizontalGroup(
            leaderboardpanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(leaderboardpanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(leaderboardpanelLayout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addComponent(leaderboardframe, GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                    .addGap(53, 53, 53)))
        );
        leaderboardpanelLayout.setVerticalGroup(
            leaderboardpanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
            .addGroup(leaderboardpanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(leaderboardpanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(leaderboardframe)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        scrollpane.setBackground(new Color(255, 255, 255));

        leaderboardlist.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        leaderboardlist.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        scrollpane.setViewportView(leaderboardlist);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(leaderboardpanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leaderboardpanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE))
        );

        pack();
    }
    public static void main(String[] args) {
        new LeaderboardForm().setVisible(true);
    }                                                    
}
