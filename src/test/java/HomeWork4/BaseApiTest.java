package HomeWork4;

import HomeWork3.PropertyScanner;

import java.io.IOException;

abstract class BaseApiTest {

    public final String token;
    private final String baseUri;
    private final String userName;
    private final String imageHash;
    private final PropertyScanner scanner;

    public BaseApiTest() throws IOException {
        scanner =new PropertyScanner();
        token = scanner.getProperty("imgur.auth.token");
        baseUri = scanner.getProperty("imgur.api.url");
        userName = scanner.getProperty("imgur.username");
        imageHash = scanner.getProperty("imgur.imageHash");
    }

    public String getToken() {
        return token;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getUserName() {
        return userName;
    }
    public String getimageHash() {
        return imageHash;
    }

    public PropertyScanner getScanner() {
        return scanner;
    }
}