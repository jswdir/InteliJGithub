
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.Set;
import java.util.TreeSet;

public class LottoUI extends JFrame {
    private LottoGenerator lottoGenerator;
    private JLabel resultLabel;
    private JButton generateButton;
    private JPanel resultPanel;
    private JLabel imageLabel;
    private JLabel hyperlinkLabel;

    public LottoUI() {
        lottoGenerator = new LottoGenerator();
        initUI();
    }

    private void initUI() {
        setTitle("대나무 숲의 푸바오 복권");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 이미지 추가
        imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon("background.png"); // 이미지 파일 경로
        imageLabel.setIcon(icon);
        imageLabel.setLayout(new BorderLayout());
        setContentPane(imageLabel);

        // 타이틀 레이블
        JLabel titleLabel = new JLabel("대나무 숲의 푸바오 복권", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.GREEN);
        add(titleLabel, BorderLayout.NORTH);

        // 하이퍼링크 레이블
        hyperlinkLabel = new JLabel("<html><a href=''>사용 가이드</a></html>");
        hyperlinkLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        hyperlinkLabel.setForeground(Color.BLUE);
        hyperlinkLabel.setHorizontalAlignment(JLabel.CENTER);
        hyperlinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hyperlinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebpage("https://docs.google.com/presentation/d/12DkI5TEYtfy4FcSdKBAUA-ytbvhyWsXf/edit#slide=id.p1"); // 원하는 URL로 변경
            }
        });
        add(hyperlinkLabel, BorderLayout.EAST);

        // 결과 표시 영역
        resultPanel = new JPanel();
        resultPanel.setOpaque(false);
        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        resultLabel.setForeground(Color.YELLOW);
        resultPanel.add(resultLabel);
        add(resultPanel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        generateButton = new JButton("번호 생성");
        generateButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        generateButton.setBackground(Color.GREEN);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateLottoNumbers();
            }
        });
        buttonPanel.add(generateButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generateLottoNumbers() {
        Set<Integer> numbers = lottoGenerator.generateNumbers();
        StringBuilder result = new StringBuilder("<html><body style='text-align: center;'>");
        for (Integer number : numbers) {
            result.append(number).append(" ");
        }
        result.append("</body></html>");
        resultLabel.setText(result.toString().trim());
    }

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LottoUI ex = new LottoUI();
                ex.setVisible(true);
            }
        });
    }
}
