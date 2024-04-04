package ru.nsu.votintsev.view.swing;

import ru.nsu.votintsev.controller.swing.SwingController;
import ru.nsu.votintsev.model.ModelFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinalFrame extends JFrame implements ActionListener {

    private final JLabel loseLabel = new JLabel("YOU LOSE", SwingConstants.CENTER);
    private final JLabel winLabel = new JLabel("YOU WIN", SwingConstants.CENTER);

    private final JButton restartButton = new JButton("RESTART");

    private final GridBagConstraints constraints;

    public FinalFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setResizable(false);

        loseLabel.setFont(new Font("Comic Sans", Font.PLAIN, 60));
        winLabel.setFont(new Font("Comic Sans", Font.PLAIN, 60));

        restartButton.addActionListener(this);
        restartButton.setFocusable(false);
        restartButton.setFont(new Font("Comic Sans", Font.PLAIN, 60));

        constraints = new GridBagConstraints();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.getSize());
    }

    public void run(boolean isWin) {
        constraints.weightx = 0.5;
        constraints.gridy   = 0  ;  // нулевая ячейка таблицы по вертикали
        constraints.gridx = 0;      // нулевая ячейка таблицы по горизонтали

        if (isWin)
            this.add(winLabel, constraints);
        else
            this.add(loseLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(restartButton, constraints);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            ModelFacade modelFacade = new ModelFacade();
            SwingController controller = new SwingController(modelFacade);
            SwingUtilities.invokeLater(() -> new GameFrame(modelFacade, controller));
            this.dispose();
        }
    }
}
