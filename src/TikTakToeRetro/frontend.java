/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TikTakToeRetro;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author andygl
 */
public class frontend extends javax.swing.JFrame {

    private MoveStrategy strategyLogic;
    private Board board = new Board();

    public void resetGame() {
        board = new Board();
        JButton[] buttons = {
            _11, _12, _13,
            _21, _22, _23,
            _31, _32, _33
        };
        for (JButton b : buttons) {
            b.setEnabled(true);
            b.setIcon(null);
            b.setDisabledIcon(null);
        }

    }

    public static void set_motif_light() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

            ColorUIResource lightGray = new ColorUIResource(new Color(232, 232, 232)); // #C0C0C0
            ColorUIResource white = new ColorUIResource(Color.WHITE);

            // Set backgrounds to light gray
            UIManager.put("control", lightGray);
            UIManager.put("info", lightGray);
            UIManager.put("nimbusBase", lightGray);
            UIManager.put("nimbusBlueGrey", lightGray);
            UIManager.put("controlHighlight", new ColorUIResource(224, 224, 224));
            UIManager.put("controlShadow", new ColorUIResource(128, 128, 128));
            UIManager.put("controlDkShadow", new ColorUIResource(64, 64, 64));
            UIManager.put("window", lightGray);
            UIManager.put("background", lightGray);
            UIManager.put("Button.background", lightGray);
            UIManager.put("Panel.background", lightGray);
            UIManager.put("Menu.background", lightGray);
            UIManager.put("MenuBar.background", lightGray);
            UIManager.put("ToolTip.background", lightGray);
            UIManager.put("ScrollPane.background", lightGray);
            UIManager.put("Viewport.background", lightGray);
            UIManager.put("TabbedPane.background", lightGray);
            UIManager.put("OptionPane.background", lightGray);
            UIManager.put("ComboBox.background", lightGray);
            UIManager.put("CheckBox.background", lightGray);
            UIManager.put("RadioButton.background", lightGray);
            UIManager.put("ToggleButton.background", lightGray);
            UIManager.put("Label.background", lightGray);
            UIManager.put("List.background", lightGray);
            UIManager.put("Table.background", lightGray);
            UIManager.put("Tree.background", lightGray);
            UIManager.put("Separator.background", lightGray);
            // Set text component backgrounds to white
            UIManager.put("TextField.background", white);
            UIManager.put("TextArea.background", white);
            UIManager.put("EditorPane.background", white);
            UIManager.put("FormattedTextField.background", white);
            UIManager.put("PasswordField.background", white);
            UIManager.put("ComboBox.selectionBackground", new Color(178, 77, 122));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to set Motif Look and Feel.");
        }

    }

    class HiddenTabsUI extends BasicTabbedPaneUI {

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets = new Insets(0, 0, 0, 0);
            contentBorderInsets = new Insets(0, 0, 0, 0);
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            // ΜΗΝ ζωγραφίζεις τίποτα
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            // ΜΗΝ ζωγραφίζεις τίποτα (κρύβει και τα tabs)
        }

        @Override
        protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
            return 0; // no tab area
        }

        @Override
        protected int calculateTabAreaWidth(int tabPlacement, int vertRunCount, int maxTabWidth) {
            return 0; // no tab area
        }
    }

    private boolean checkWin(char symbol) {
        char[][] g = board.getGrid();

        for (int i = 0; i < 3; i++) {
            if (g[i][0] == symbol && g[i][1] == symbol && g[i][2] == symbol) {
                return true;
            }
            if (g[0][i] == symbol && g[1][i] == symbol && g[2][i] == symbol) {
                return true;
            }
        }
        if (g[0][0] == symbol && g[1][1] == symbol && g[2][2] == symbol) {
            return true;
        }
        if (g[0][2] == symbol && g[1][1] == symbol && g[2][0] == symbol) {
            return true;
        }

        return false;
    }

    public static void Beep(int fr, int dr) {
        try {
            float sampleRate = 44100;
            int durationMs = dr;      // very short beep
            int freq = fr;           // middle pitch

            byte[] buf = new byte[(int) (sampleRate * durationMs / 1000)];
            for (int i = 0; i < buf.length; i++) {
                double angle = 2.0 * Math.PI * i * freq / sampleRate;
                buf[i] = (byte) (Math.sin(angle) * 127);
            }

            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(af);
            line.open(af);
            line.start();
            line.write(buf, 0, buf.length);
            line.drain();
            line.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gameOver(String msg) {
        jLabel4.setText(msg);
        jDialog1.setLocationRelativeTo(this);
        jDialog1.setIconImage(new ImageIcon(frontend.class.getResource("icon.png")).getImage());
        Toolkit.getDefaultToolkit().beep();
        if (jLabel4.getText().contains("You lost the game! Better luck next time")) {
            flag.setIcon(new ImageIcon(frontend.class.getResource("loose.png")));
        } else if (jLabel4.getText().contains("You won the game! Good job!")) {
            flag.setIcon(new ImageIcon(frontend.class.getResource("win.png")));
        } else {
            flag.setIcon(new ImageIcon(frontend.class.getResource("draw.png")));
        }
        jDialog1.setVisible(true);
    }

    public frontend() throws IOException {
        new Thread(() -> {
            Beep(1, 1); // σχεδόν μηδενικός ήχος, απλά ανοίγει το audio system
        }).start();
        set_motif_light();
        initComponents();
        ImageIcon X = new ImageIcon(frontend.class.getResource("X.png"));
        ImageIcon O = new ImageIcon(frontend.class.getResource("O.png"));
        jDialog1.getContentPane().setBackground(Color.white);
        this.setIconImage(new ImageIcon(frontend.class.getResource("icon.png")).getImage());
        scenes.setUI(new HiddenTabsUI());
        Component editor = strategy.getEditor().getEditorComponent();
        editor.setBackground(new Color(232, 232, 232));
        editor.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        System.out.println(this.getSize().toString());
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (strategy.isPopupVisible()) {
                    strategy.hidePopup();
                } else {
                    strategy.showPopup();
                }
            }
        });
        this.getContentPane().setBackground(new Color(250, 250, 250));
        this.setLocationRelativeTo(null);
        JButton[] buttons = {
            _11, _12, _13,
            _21, _22, _23,
            _31, _32, _33
        };

        for (int i = 0; i < buttons.length; i++) {
            int r = i / 3;
            int c = i % 3;
            JButton bnt = buttons[i];
            bnt.addActionListener(e -> {
                new Thread(() -> {
                    Beep(480, 80);
                }).start();

                //USER MOVE
                if (!board.move(r, c, 'X')) {
                    return;
                }
                bnt.setDisabledIcon(X);
                bnt.setIcon(X);
                bnt.setEnabled(false);

                if (checkWin('X')) {
                    gameOver("<html>You won the game! Good job! <br>Difficulty: " + strategy.getSelectedItem() + "</html>");
                    return;
                }
                if (board.isFull()) {
                    gameOver("<html>Draw! No winner. <br>Difficulty: " + strategy.getSelectedItem() + "</html>");
                    return;
                }
                //COMPUTER MOVE
                int[] move = strategyLogic.move(board, 'O', 'X');
                if (board.move(move[0], move[1], 'O')) {
                    int pcIndex = move[0] * 3 + move[1];
                    buttons[pcIndex].setDisabledIcon(O);
                    buttons[pcIndex].setIcon(O);
                    buttons[pcIndex].setEnabled(false);
                }

                if (checkWin('O')) {
                    gameOver("<html>You lost the game! Better luck next time :) <br>Difficulty: " + strategy.getSelectedItem() + "</html>");
                    return;
                }

                if (board.isFull()) {
                    gameOver("<html>Draw! No winner. <br>Difficulty: " + strategy.getSelectedItem() + "</html>");
                    return;
                }
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialog1 = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        ok = new javax.swing.JButton();
        flag = new javax.swing.JLabel();
        again = new javax.swing.JButton();
        menu = new javax.swing.JButton();
        scenes = new javax.swing.JTabbedPane();
        jPanel1 = new BackgroundPanel("bg.png");
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        forcombobox = new javax.swing.JPanel();
        blackarrow = new javax.swing.JLabel();
        strategy = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new BackgroundPanel("bg2.png");
        xogrid = new javax.swing.JPanel();
        _11 = new javax.swing.JButton();
        _12 = new javax.swing.JButton();
        _13 = new javax.swing.JButton();
        _21 = new javax.swing.JButton();
        _22 = new javax.swing.JButton();
        _23 = new javax.swing.JButton();
        _31 = new javax.swing.JButton();
        _32 = new javax.swing.JButton();
        _33 = new javax.swing.JButton();
        turn = new javax.swing.JLabel();

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog1.setTitle("Game over");
        jDialog1.setBackground(new java.awt.Color(255, 255, 255));
        jDialog1.setMinimumSize(new java.awt.Dimension(324, 159));
        jDialog1.setModal(true);
        jDialog1.setResizable(false);
        jDialog1.setSize(new java.awt.Dimension(0, 0));
        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jDialog1WindowClosing(evt);
            }
        });
        jDialog1.getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("<html>You won the game!<br>\nYour final score is 0. Good job!</html>");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 3, 12);
        jDialog1.getContentPane().add(jLabel4, gridBagConstraints);

        ok.setText("Quit");
        ok.setFocusable(false);
        ok.setMinimumSize(new java.awt.Dimension(12, 26));
        ok.setPreferredSize(new java.awt.Dimension(12, 26));
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 7, 6);
        jDialog1.getContentPane().add(ok, gridBagConstraints);

        flag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TikTakToeRetro/loose.png"))); // NOI18N
        flag.setText("  ");
        flag.setToolTipText("");
        flag.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jDialog1.getContentPane().add(flag, gridBagConstraints);

        again.setText("Play Again");
        again.setFocusable(false);
        again.setPreferredSize(new java.awt.Dimension(80, 26));
        again.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                againActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 28;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 7, 0);
        jDialog1.getContentPane().add(again, gridBagConstraints);

        menu.setText("Menu");
        menu.setFocusPainted(false);
        menu.setFocusable(false);
        menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 7, 0);
        jDialog1.getContentPane().add(menu, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tik-Tak-Toe!");
        setBackground(new java.awt.Color(249, 249, 249));
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(462, 362));
        setResizable(false);

        scenes.setEnabled(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TikTakToeRetro/logo.png"))); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 90, 330, 60));

        jButton2.setText("Quit game");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 114, 45));

        jButton1.setText("Start Game");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 114, 44));

        jLabel3.setFont(new java.awt.Font("SansSerif.plain", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ανδρόνικος Κωνσταντίνος Γλαρός - 220075");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 307, -1));

        forcombobox.setLayout(null);

        blackarrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TikTakToeRetro/down.png"))); // NOI18N
        forcombobox.add(blackarrow);
        blackarrow.setBounds(104, 0, 15, 30);

        strategy.setEditable(true);
        strategy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rule-based", "Minimax", "Heuristic" }));
        strategy.setFocusable(false);
        forcombobox.add(strategy);
        strategy.setBounds(0, 0, 120, 30);

        jPanel1.add(forcombobox, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 120, 30));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Selected strategy:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, -1, -1));

        scenes.addTab("tab1", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        xogrid.setBackground(new java.awt.Color(255, 255, 255));
        xogrid.setOpaque(false);
        xogrid.setLayout(new java.awt.GridLayout(3, 3));

        _11.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _11.setFocusable(false);
        _11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _11ActionPerformed(evt);
            }
        });
        xogrid.add(_11);

        _12.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _12.setFocusable(false);
        xogrid.add(_12);

        _13.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _13.setFocusable(false);
        xogrid.add(_13);

        _21.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _21.setFocusable(false);
        xogrid.add(_21);

        _22.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _22.setFocusable(false);
        xogrid.add(_22);

        _23.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _23.setFocusable(false);
        xogrid.add(_23);

        _31.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _31.setFocusable(false);
        xogrid.add(_31);

        _32.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _32.setFocusable(false);
        xogrid.add(_32);

        _33.setFont(new java.awt.Font("SansSerif.plain", 0, 48)); // NOI18N
        _33.setFocusable(false);
        xogrid.add(_33);

        turn.setFont(new java.awt.Font("SansSerif.plain", 1, 18)); // NOI18N
        turn.setForeground(new java.awt.Color(255, 255, 255));
        turn.setText("Playing against computer");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(xogrid, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(turn, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(turn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(xogrid, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        scenes.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scenes)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scenes)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        scenes.setSelectedIndex(1);
        strategy.setSelectedItem(strategy.getEditor().getItem());
        switch (strategy.getSelectedIndex()) {
            case 0:
                strategyLogic = StrategyFactory.create(StrategyFactory.stype.RULEBASED);
                break;
            case 1:
                strategyLogic = StrategyFactory.create(StrategyFactory.stype.MINIMAX);
                break;
            case 2:
                strategyLogic = StrategyFactory.create(StrategyFactory.stype.HEURISTIC);
                break;
            default:
                strategyLogic = StrategyFactory.create(StrategyFactory.stype.RULEBASED);
                break;
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jDialog1WindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowClosing
        System.exit(0);
    }//GEN-LAST:event_jDialog1WindowClosing

    private void _11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event__11ActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        System.exit(0);
    }//GEN-LAST:event_okActionPerformed

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
        resetGame();
        scenes.setSelectedIndex(0);
        jDialog1.dispose();
    }//GEN-LAST:event_menuActionPerformed

    private void againActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_againActionPerformed
        resetGame();
        jDialog1.dispose();
    }//GEN-LAST:event_againActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frontend().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(frontend.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _11;
    private javax.swing.JButton _12;
    private javax.swing.JButton _13;
    private javax.swing.JButton _21;
    private javax.swing.JButton _22;
    private javax.swing.JButton _23;
    private javax.swing.JButton _31;
    private javax.swing.JButton _32;
    private javax.swing.JButton _33;
    private javax.swing.JButton again;
    private javax.swing.JLabel blackarrow;
    private javax.swing.JLabel flag;
    private javax.swing.JPanel forcombobox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton menu;
    private javax.swing.JButton ok;
    private javax.swing.JTabbedPane scenes;
    private javax.swing.JComboBox<String> strategy;
    private javax.swing.JLabel turn;
    private javax.swing.JPanel xogrid;
    // End of variables declaration//GEN-END:variables
}
