package tr.edu.istiklal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MayinTarlasi extends JFrame {
    public int n = 10;
    public JButton[][] butons = new JButton[n][n];
    public boolean mayinlar[][] = new boolean[n][n];
    public int mayinCount = 0;
    public int maxMayinCount =10;

    public MayinTarlasi() {
        Random r = new Random();
        for (int i = 0; i < maxMayinCount; i++) {
            int satir = r.nextInt(n);
            int sutun = r.nextInt(n);
            if (!mayinlar[satir][sutun]) {
                mayinlar[satir][sutun] = true;
                mayinCount++;
            }
        }
        setTitle("Mayın Tarlası");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(n, n));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(50, 50));
                butons[i][j] = b;
                add(b);
                final int s = i;
                final int c = j;
                b.addActionListener(e -> {
                    if (!b.getText().equals("🚩")) {
                        if (mayinlar[s][c]) {
                            oyunBitir();
                        } else {
                            alanAc(s, c);
                            kazandiMi();
                        }
                    }
                });
                b.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (b.isEnabled()) {
                                if (b.getText().equals("🚩")) {
                                    b.setText("");
                                } else {
                                    b.setText("🚩");
                                }
                            }
                        }
                    }
                });
            }
        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public int mayinSayac(int r, int c) {
        int sayac = 0;
        for (int i = r - 1; i < r + 2; i++) {
            for (int j = c - 1; j < c + 2; j++) {
                if (i >= 0 && j >= 0 && i < n && j < n) {
                    if (mayinlar[i][j]) {
                        sayac++;
                    }
                }
            }
        }
        return sayac;
    }

    public void alanAc(int r, int c) {
        if (r < 0 || r >= n || c < 0 || c >= n) return;

        if (!butons[r][c].isEnabled() || butons[r][c].getText().equals("🚩")) return;

        butons[r][c].setEnabled(false);
        int adet = mayinSayac(r, c);
        if (adet > 0) {
            butons[r][c].setText(String.valueOf(adet));
        } else {
            butons[r][c].setText("");
            butons[r][c].setBackground(Color.lightGray);
            for (int i = r - 1; i <= r + 1; i++) {
                for (int j = c - 1; j <= c + 1; j++) {
                    alanAc(i, j);
                }
            }
        }
    }

    public void oyunBitir() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mayinlar[i][j]) {
                    butons[i][j].setText("💣");
                    butons[i][j].setBackground(Color.RED);
                }
                butons[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(this, "BUM! Mayına bastın.");
    }

    public void kazandiMi() {
        int kapaliAlan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (butons[i][j].isEnabled()) {
                    kapaliAlan++;
                }
            }
        }
        if (kapaliAlan == mayinCount) {
            JOptionPane.showMessageDialog(this, "TEBRİKLER! Tüm mayınları buldun.");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) butons[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        new MayinTarlasi();
    }

}
