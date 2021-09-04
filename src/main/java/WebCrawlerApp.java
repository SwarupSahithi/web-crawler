import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import static javax.swing.JOptionPane.*;

/**
 * Project Name    : web-crawler
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 9/4/2021
 * Time            : 2:12 PM
 * Description     :
 **/

public class WebCrawlerApp {

    private final JFrame frame;
    private final JPanel panel = new JPanel();
    private final Font font = new Font("SansSerif", Font.PLAIN, 18);
    private JTextField txtWebPageUrl, txtCrawlingCount;

    private WebDriver driver;

    public WebCrawlerApp() {
        frame = new JFrame("Web Crawler App");
        frame.setSize(710, 190);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("./src/main/resources/application.png"));
        frame.add(panel);
        panel.setLayout(null);

        setWebPageUrl();
        setCrawlingCount();

        setStartCrawlingButton();
        setResetButton();
        setExitButton();

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new WebCrawlerApp();
    }

    private void setWebPageUrl() {
        JLabel lblProjectName = new JLabel("Web Page URL");
        lblProjectName.setBounds(10, 20, 300, 25);
        lblProjectName.setFont(font);
        panel.add(lblProjectName);

        txtWebPageUrl = new JTextField();
        txtWebPageUrl.setBounds(200, 20, 500, 25);
        txtWebPageUrl.setFont(font);
        panel.add(txtWebPageUrl);
    }

    private void setCrawlingCount() {
        JLabel lblCrawlingCount = new JLabel("Crawling Count");
        lblCrawlingCount.setBounds(10, 60, 300, 25);
        lblCrawlingCount.setFont(font);
        panel.add(lblCrawlingCount);

        txtCrawlingCount = new JTextField();
        txtCrawlingCount.setBounds(200, 60, 500, 25);
        txtCrawlingCount.setFont(font);
        panel.add(txtCrawlingCount);
    }

    private void setStartCrawlingButton() {
        JButton btnStartCrawling = new JButton("Start");
        btnStartCrawling.setBounds(380, 120, 100, 25);
        btnStartCrawling.setFont(font);
        panel.add(btnStartCrawling);

        btnStartCrawling.addActionListener(e -> {
            String submissionStatus;

            try {
                if (isValidUrl()) {

                    if (isValidCrawlingCount()) {
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--headless");
                        chromeOptions.addArguments("start-maximized");
                        driver = new ChromeDriver(chromeOptions);

                        int crawlingCount = Integer.parseInt(txtCrawlingCount.getText());

                        new WebCrawler(driver).crawl(txtWebPageUrl.getText(), crawlingCount);
                        submissionStatus = "Crawling has successfully ran " + crawlingCount + " time(s)!";

                        driver.quit();
                    } else {
                        txtCrawlingCount.setText("");
                        submissionStatus = "Invalid Crawling Count!";
                    }

                } else {
                    txtWebPageUrl.setText("");
                    submissionStatus = "Invalid Web Page URL!";
                }

            } catch (Exception ex) {
                submissionStatus = "Crawling has failed!";
            }

            showDialogBox(submissionStatus);
        });
    }

    private void setResetButton() {
        JButton btnExit = new JButton("Reset");
        btnExit.setBounds(490, 120, 100, 25);
        btnExit.setFont(font);
        btnExit.addActionListener(e -> setDefaultValues());
        panel.add(btnExit);
    }

    private void setExitButton() {
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(600, 120, 100, 25);
        btnExit.setFont(font);
        btnExit.addActionListener(e -> System.exit(0));
        panel.add(btnExit);
    }

    private boolean isValidUrl() {
        try {
            new URL(txtWebPageUrl.getText());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidCrawlingCount() {
        return txtCrawlingCount.getText().matches("[+]?[0-9]");
    }

    private void setDefaultValues() {
        txtWebPageUrl.setText("");
        txtCrawlingCount.setText("");
    }

    private void showDialogBox(String submissionStatus) {
        int messageType;

        if (submissionStatus.contains("success"))
            messageType = INFORMATION_MESSAGE;
        else if (submissionStatus.contains("Invalid"))
            messageType = WARNING_MESSAGE;
        else
            messageType = ERROR_MESSAGE;

        JOptionPane.showMessageDialog(frame, submissionStatus, "Crawling Status", messageType);
    }
}
