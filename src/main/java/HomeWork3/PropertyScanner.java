package HomeWork3;

import java.io.IOException;
import java.util.Properties;

public class PropertyScanner {

    private final Properties properties;

    public PropertyScanner() { //конструктор
        properties = new Properties();
        Properties properties = new Properties();
    }

    public String getProperty(String key) throws IOException {

        properties.load(getClass().getResourceAsStream("application.properties"));
        return properties.getProperty(key);
    }

}

