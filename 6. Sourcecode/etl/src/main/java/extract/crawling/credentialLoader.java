package extract.crawling;

import java.io.*;

public class credentialLoader {
    private String clientId;
    private String clientSecret;

    public credentialLoader(String filePath) {
        loadCredentials(filePath);
    }

    private void loadCredentials(String filePath) {

        try(InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                if(inputStream == null) {
                    throw new IllegalArgumentException("There is no file in " + filePath);
                }

                // credential format
                // cliendId="blahblahblah"
                // clientSecret="blahblahblah"
                clientId = reader.readLine().split("=")[1].trim();
                clientSecret = reader.readLine().split("=")[1].trim();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}