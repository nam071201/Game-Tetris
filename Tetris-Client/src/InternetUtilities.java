import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class InternetUtilities {
    TetrisWindow window;

    public InternetUtilities(TetrisWindow window) {
        this.window = window;
    }

    public String postRequest(String endpoint, HashMap<String, String> data) {
        try {
            String url = "http://127.0.0.1:8888" + endpoint;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(ParameterBuilder.build(data));
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            error("Please check your internet connection and try again later.");
            window.gameState = "menu";
            return null;
        }
    }

    public void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
