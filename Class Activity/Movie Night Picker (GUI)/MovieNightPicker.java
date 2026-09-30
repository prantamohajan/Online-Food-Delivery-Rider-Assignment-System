import javax.swing.*;
import java.awt.*;

public class MovieNightPicker {
          public static void main(String[] args) {
                    SwingUtilities.invokeLater(MovieNightPicker::createAndShowGui);
          }

          private static void createAndShowGui() {
                    JFrame frame = new JFrame("Movie Night Picker");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel panel = new JPanel(new GridBagLayout());
                    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(6, 6, 6, 6);
                    gbc.anchor = GridBagConstraints.WEST;

                    String[] movies = { "Action Hero", "Comedy Night", "Space Quest" };
                    JList<String> movieList = new JList<>(movies);
                    movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    movieList.setVisibleRowCount(3);

                    JRadioButton fiveBtn = new JRadioButton("5:00 PM");
                    JRadioButton eightBtn = new JRadioButton("8:00 PM");
                    fiveBtn.setSelected(true);
                    ButtonGroup showtimeGroup = new ButtonGroup();
                    showtimeGroup.add(fiveBtn);
                    showtimeGroup.add(eightBtn);

                    JCheckBox popcornBox = new JCheckBox("Add Popcorn");
                    JButton confirmBtn = new JButton("Confirm Order");

                    JTextField resultField = new JTextField(28);
                    resultField.setEditable(false);

                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    panel.add(new JLabel("Movie:"), gbc);
                    gbc.gridx = 1;
                    panel.add(new JLabel("Showtime:"), gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridheight = 2;
                    gbc.fill = GridBagConstraints.BOTH;
                    panel.add(new JScrollPane(movieList), gbc);

                    gbc.gridheight = 1;
                    gbc.fill = GridBagConstraints.NONE;
                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    panel.add(fiveBtn, gbc);
                    gbc.gridy = 2;
                    panel.add(eightBtn, gbc);
                    gbc.gridy = 3;
                    panel.add(popcornBox, gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.CENTER;
                    panel.add(confirmBtn, gbc);

                    gbc.gridy = 5;
                    gbc.anchor = GridBagConstraints.WEST;
                    JPanel resultRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    resultRow.add(new JLabel("You picked: "));
                    resultRow.add(resultField);
                    panel.add(resultRow, gbc);

                    confirmBtn.addActionListener(e -> {
                              String movie = movieList.getSelectedValue();
                              if (movie == null) {
                                        resultField.setText("Please choose a movie");
                                        return;
                              }
                              String time = fiveBtn.isSelected() ? fiveBtn.getText() : eightBtn.getText();
                              String popcorn = popcornBox.isSelected() ? "with popcorn" : "without popcorn";
                              resultField.setText(movie + " at " + time + ", " + popcorn);
                    });

                    frame.add(panel);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
          }
}