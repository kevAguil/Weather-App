import org.json.simple.JSONObject;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class weatherAppGUI extends JFrame {
    private JSONObject weatherData;

    public weatherAppGUI(){
        // Set up our gui and add a title
        super("Weather App");

        // Configure gui to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Set the size of our gui (in pixels)
        setSize(450, 650);

        // Load our gui at the center of the screen
        setLocationRelativeTo(null);

        // Make our layout manager null to manually position our components within the gui
        setLayout(null);

        // Prevent any resize of our gui
        setResizable(false);

        addGuiComponents();

    }

    private void addGuiComponents(){

        // Search field 
        JTextField searchTextField = new JTextField();

        // Set the location and size of our component
        searchTextField.setBounds(15, 15, 351, 45);

        // Change the font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        // Weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);

        add(weatherConditionImage);

        // Temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));

        // Center the text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);

        add(temperatureText);

        // Weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);

        add(weatherConditionDesc);

        // Humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);

        add(humidityImage);

        // Humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 15));

        add(humidityText);

        // Windspeed image
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220,500,74, 66);

        add(windSpeedImage);

        // Windspeed text
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310, 500, 85, 55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 15));

        add(windSpeedText);

        // Search button
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        // Change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get location from user
                String userInput = searchTextField.getText();

                // Validate input - remove whitespace to ensure non-empty text
                if (userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // Retrieve weather data
                weatherData = weatherApp.getWeatherData(userInput);

                //Update GUI

                // Update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // Depending on the weather condition, we will update the weather image that corresponds with the condition
                switch (weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                // Update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + "C");

                // Update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // Update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // Update windspeed text
                double windSpeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windSpeed + "km/h</html>");


            }
        });

        add(searchButton);

    }

    // Used to create images in our gui components
    private ImageIcon loadImage(String resourcePath){

        try{
            // Read the image file from the path given 
            BufferedImage image = ImageIO.read(new File(resourcePath));

            // Returns an image icon so that our component can render it
            return new ImageIcon(image);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;

    }
}
