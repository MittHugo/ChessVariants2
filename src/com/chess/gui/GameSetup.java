package com.chess.gui;

import com.chess.engine.Alliance;
import com.chess.engine.player.Player;
import com.chess.gui.Table.ChessType;
import com.chess.gui.Table.PlayerCount;
import com.chess.gui.Table.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class GameSetup extends JDialog {

    private PlayerType whitePlayerType;
    private PlayerType blackPlayerType;
    private PlayerType redPlayerType;
    private PlayerType bluePlayerType;
    private PlayerCount playerCount;
    private ChessType chessType;

    private JSpinner searchDepthSpinner;

    private static final String HUMAN_TEXT = "Human";
    private static final String COMPUTER_TEXT = "Computer";
    final JRadioButton whiteHumanButton;
    final JRadioButton whiteComputerButton;
    final JRadioButton blackHumanButton;
    final JRadioButton blackComputerButton;
    final JRadioButton blueHumanButton;
    final JRadioButton blueComputerButton;
    final JRadioButton redHumanButton;
    final JRadioButton redComputerButton;

    GameSetup(final JFrame frame, final boolean modal) {
        super(frame, modal);
        setTitle("Game Setup");
        setPreferredSize(new Dimension(500, 800));
        setLayout(new BorderLayout());

        // Main Panel with GridBagLayout
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Board Type Panel
        final JPanel boardTypePanel = new JPanel(new GridLayout(0, 1));
        boardTypePanel.setBorder(BorderFactory.createTitledBorder("Select Board Type"));
        final JRadioButton regularBoardButton = new JRadioButton("Regular Board");
        final JRadioButton fourPlayerTeamsButton = new JRadioButton("4 Player Teams");
        final JRadioButton fourPlayerOpponentsButton = new JRadioButton("4 Player Opponents");
        final ButtonGroup boardGroup = new ButtonGroup();
        boardGroup.add(regularBoardButton);
        boardGroup.add(fourPlayerTeamsButton);
        boardGroup.add(fourPlayerOpponentsButton);
        regularBoardButton.setSelected(true);
        boardTypePanel.add(regularBoardButton);
        boardTypePanel.add(fourPlayerTeamsButton);
        boardTypePanel.add(fourPlayerOpponentsButton);

        // Add Board Type Panel to Main Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(boardTypePanel, gbc);

        // Dimension Panel
        final JPanel dimensionPanel = new JPanel(new GridLayout(0, 1));
        dimensionPanel.setBorder(BorderFactory.createTitledBorder("Select Board Dimension"));
        final JRadioButton twoDButton = new JRadioButton("2D");
        final JRadioButton threeDButton = new JRadioButton("3D");
        final JRadioButton fiveDButton = new JRadioButton("5D");
        final ButtonGroup dimensionGroup = new ButtonGroup();
        dimensionGroup.add(twoDButton);
        dimensionGroup.add(threeDButton);
        dimensionGroup.add(fiveDButton);
        twoDButton.setSelected(true);
        dimensionPanel.add(twoDButton);
        dimensionPanel.add(threeDButton);
        dimensionPanel.add(fiveDButton);

        // Add Dimension Panel to Main Panel
        gbc.gridy = 1;
        mainPanel.add(dimensionPanel, gbc);

        // Player Options Panel
        final JPanel playerPanel = new JPanel(new GridLayout(0, 3, 5, 5));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));

        // Player Buttons
        whiteHumanButton = new JRadioButton(HUMAN_TEXT);
        whiteComputerButton = new JRadioButton(COMPUTER_TEXT);
        blackHumanButton = new JRadioButton(HUMAN_TEXT);
        blackComputerButton = new JRadioButton(COMPUTER_TEXT);
        blueHumanButton = new JRadioButton(HUMAN_TEXT);
        blueComputerButton = new JRadioButton(COMPUTER_TEXT);
        redHumanButton = new JRadioButton(HUMAN_TEXT);
        redComputerButton = new JRadioButton(COMPUTER_TEXT);

        final ButtonGroup whiteGroup = new ButtonGroup();
        final ButtonGroup blackGroup = new ButtonGroup();
        final ButtonGroup blueGroup = new ButtonGroup();
        final ButtonGroup redGroup = new ButtonGroup();

        whiteGroup.add(whiteHumanButton);
        whiteGroup.add(whiteComputerButton);
        blackGroup.add(blackHumanButton);
        blackGroup.add(blackComputerButton);
        blueGroup.add(blueHumanButton);
        blueGroup.add(blueComputerButton);
        redGroup.add(redHumanButton);
        redGroup.add(redComputerButton);

        whiteHumanButton.setSelected(true);
        blackHumanButton.setSelected(true);
        blueHumanButton.setSelected(true);
        redHumanButton.setSelected(true);

        updatePlayerOptions(regularBoardButton.isSelected(), playerPanel);

        // Action Listeners for Board Type
        regularBoardButton.addActionListener(e -> updatePlayerOptions(true, playerPanel));
        fourPlayerTeamsButton.addActionListener(e -> updatePlayerOptions(false, playerPanel));
        fourPlayerOpponentsButton.addActionListener(e -> updatePlayerOptions(false, playerPanel));

        // Add Player Panel to Main Panel
        gbc.gridy = 2;
        mainPanel.add(playerPanel, gbc);

        // Search Depth Spinner
        final JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search Depth"));
        this.searchDepthSpinner = addLabeledSpinner(searchPanel, "Depth", new SpinnerNumberModel(3, 0, Integer.MAX_VALUE, 1));

        gbc.gridy = 3;
        mainPanel.add(searchPanel, gbc);

        // Game Mode Panel
        final JPanel gameModePanel = new JPanel(new GridLayout(0, 1));
        gameModePanel.setBorder(BorderFactory.createTitledBorder("Select Game Mode"));
        final JRadioButton regularChessButton = new JRadioButton("Regular Chess");
        final JRadioButton antiChessButton = new JRadioButton("Anti Chess");
        final JRadioButton conquerAllButton = new JRadioButton("Conquer All");
        final JRadioButton kingPromotionButton = new JRadioButton("King Promotion");
        final JRadioButton tripleCheckButton = new JRadioButton("Triple Check");
        final ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(regularChessButton);
        modeGroup.add(antiChessButton);
        modeGroup.add(conquerAllButton);
        modeGroup.add(kingPromotionButton);
        modeGroup.add(tripleCheckButton);
        regularChessButton.setSelected(true);

        gameModePanel.add(regularChessButton);
        gameModePanel.add(antiChessButton);
        gameModePanel.add(conquerAllButton);
        gameModePanel.add(kingPromotionButton);
        gameModePanel.add(tripleCheckButton);

        // Add Game Mode Panel to Main Panel
        gbc.gridy = 4;
        mainPanel.add(gameModePanel, gbc);

        // Add Action Listeners for Modes
        ActionListener modeListener = e -> {
            if (regularChessButton.isSelected()) chessType = ChessType.Regular;
            else if (antiChessButton.isSelected()) chessType = ChessType.AnitChess;
            else if (conquerAllButton.isSelected()) chessType = ChessType.ConquerAll;
            else if (kingPromotionButton.isSelected()) chessType = ChessType.KingPromotion;
            else if(tripleCheckButton.isSelected()) chessType = ChessType.TripleCheck;
        };

        regularChessButton.addActionListener(modeListener);
        antiChessButton.addActionListener(modeListener);
        conquerAllButton.addActionListener(modeListener);
        kingPromotionButton.addActionListener(modeListener);
        tripleCheckButton.addActionListener(modeListener);

        // Buttons Panel
        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JButton cancelButton = new JButton("Cancel");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            whitePlayerType = whiteComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            blackPlayerType = blackComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            redPlayerType = redComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            bluePlayerType = blueComputerButton.isSelected() ? PlayerType.COMPUTER : PlayerType.HUMAN;
            if (regularBoardButton.isSelected()) {
                playerCount = PlayerCount.Regular;
            } else if (fourPlayerTeamsButton.isSelected()) {
                playerCount = PlayerCount.FourTeams;
            } else {
                playerCount = PlayerCount.FourOpponents;
            }
            setVisible(false);
            Table.get().reset();
        });

        cancelButton.addActionListener(e -> setVisible(false));

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(okButton);

        gbc.gridy = 5;
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(frame);
        setVisible(false);
    }

    // Method to Update Player Options Based on Board Type
    private void updatePlayerOptions(boolean isRegular, JPanel panel) {
        panel.removeAll();
        if (isRegular) {
            panel.add(new JLabel("White"));
            panel.add(this.whiteHumanButton);
            panel.add(whiteComputerButton);
            panel.add(new JLabel("Black"));
            panel.add(blackHumanButton);
            panel.add(blackComputerButton);
        } else {
            panel.add(new JLabel("Blue"));
            panel.add(blueHumanButton);
            panel.add(blueComputerButton);
            panel.add(new JLabel("White"));
            panel.add(whiteHumanButton);
            panel.add(whiteComputerButton);
            panel.add(new JLabel("Black"));
            panel.add(blackHumanButton);
            panel.add(blackComputerButton);
            panel.add(new JLabel("Red"));
            panel.add(redHumanButton);
            panel.add(redComputerButton);
        }
        panel.revalidate();
        panel.repaint();
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }

    boolean isAIPlayer(final Player player) {
        if (player.getAlliance() == Alliance.WHITE) {
            return getWhitePlayerType() == PlayerType.COMPUTER;
        } else if (player.getAlliance().isRed()) {
            return getRedPlayerType() == PlayerType.COMPUTER;
        } else if (player.getAlliance().isBlue()) {
            return getBluePlayerType() == PlayerType.COMPUTER;
        }
        return getBlackPlayerType() == PlayerType.COMPUTER;
    }

    PlayerType getWhitePlayerType() {
        return this.whitePlayerType;
    }

    PlayerType getBlackPlayerType() {
        return this.blackPlayerType;
    }

    PlayerType getBluePlayerType() {
        return this.bluePlayerType;
    }

    PlayerType getRedPlayerType() {
        return this.redPlayerType;
    }

    int getSearchDepth() {
        return (Integer) this.searchDepthSpinner.getValue();
    }

    PlayerCount getPlayerCount() {
        return this.playerCount;
    }
    ChessType getChessType() {
    	return chessType;
    }

    private static JSpinner addLabeledSpinner(final Container c,
                                              final String label,
                                              final SpinnerModel model) {
        final JLabel l = new JLabel(label);
        c.add(l);
        final JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        return spinner;
    }
}
