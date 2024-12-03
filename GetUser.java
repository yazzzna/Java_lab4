import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUser {

    public void User() {
        try {
            URL url = new URL("https://fake-json-api.mock.beeceptor.com/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();
            Parse(response.toString(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Parse(String response, int index) {
        response = response.trim().replaceAll("[{}\\[\\]]", "");

        for (String info : response.split(",")) {
            String[] all_info = info.split(":", 2);
            if (all_info.length == 2) {
                String key = all_info[0].replace("\"", "").trim();
                String value = all_info[1].trim();

                if (value.startsWith("{") || value.startsWith("[")) {
                    System.out.println(key + ":");
                    Parse(value, 0);
                }
                else {
                    System.out.println(key + ": " + value.replace("\"", "").trim());
                }
            }
            else {
                System.out.println(info.replace("\"", "").trim());
            }
        }
    }
}
