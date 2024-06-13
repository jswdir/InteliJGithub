import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.TreeSet;
import javax.imageio.ImageIO;

public class LottoUI extends JFrame {
    private LottoGenerator lottoGenerator;
    private JLabel resultLabel;
    private JButton generateButton;
    private JButton checkButton;
    private JPanel resultPanel;
    private JLabel hyperlinkLabel;
    private JTextField inputField;
    private BufferedImage backgroundImage;

    public LottoUI() {
        lottoGenerator = new LottoGenerator();
        try {
            backgroundImage = ImageIO.read(new File("java.jpg")); // Ensure this path is correct
        } catch (IOException e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {
        setTitle("대나무 숲의 푸바오 복권");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and background
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        setContentPane(backgroundPanel);

        // 타이틀 레이블
        JLabel titleLabel = new JLabel("대나무 숲의 푸바오 복권", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48)); // 글자 크기 48로 조정
        titleLabel.setForeground(new Color(0, 255, 0));
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
                openWebpage("https://docs.google.com/presentation/d/12DkI5TEYtfy4FcSdKBAUA-ytbvhyWsXf/edit#slide=id.p1");
            }
        });
        JPanel linkPanel = new JPanel();
        linkPanel.setOpaque(false);
        linkPanel.add(hyperlinkLabel);
        add(linkPanel, BorderLayout.WEST);

        // 결과 표시 영역
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setOpaque(false);
        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 48));
        resultLabel.setForeground(Color.YELLOW);
        resultPanel.add(resultLabel);
        add(resultPanel, BorderLayout.CENTER);

        // 입력 필드 및 버튼 패널
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setOpaque(false);

        inputField = new JTextField(20);
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 24));
        inputPanel.add(inputField);

        generateButton = new JButton("번호 생성");
        generateButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        generateButton.setBackground(new Color(34, 139, 34));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateLottoNumbers();
            }
        });
        inputPanel.add(generateButton);

        add(inputPanel, BorderLayout.SOUTH);
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

    private void checkLottoNumbers() {
        String inputText = inputField.getText();
        String[] inputNumbers = inputText.split("\\s+");
        Set<Integer> generatedNumbers = lottoGenerator.getGeneratedNumbers();
        StringBuilder result = new StringBuilder("<html><body style='text-align: center;'>");
        int correctCount = 0;

        for (String numStr : inputNumbers) {
            try {
                int num = Integer.parseInt(numStr);
                if (generatedNumbers.contains(num)) {
                    result.append("<span style='color:green;'>").append(num).append("</span> ");
                    correctCount++;
                } else {
                    result.append("<span style='color:red;'>").append(num).append("</span> ");
                }
            } catch (NumberFormatException e) {
                resultLabel.setText("<html><body style='text-align: center; color:red;'>올바른 숫자를 입력하세요.</body></html>");
                return;
            }
        }
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

class LottoApp {
    private Set<Integer> generatedNumbers;

    public Set<Integer> generateNumbers() {
        generatedNumbers = new TreeSet<>();
        while (generatedNumbers.size() < 6) {
            int number = (int) (Math.random() * 45) + 1;
            generatedNumbers.add(number);
        }
        return generatedNumbers;
    }

    public Set<Integer> getGeneratedNumbers() {
        return generatedNumbers;
    }
}

class BackgroundPanel extends JPanel {
    private BufferedImage image;

    public BackgroundPanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}