import GUI.Utility.PdfHandler;
import com.google.zxing.WriterException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import java.util.UUID;

public class PdfGeneratorMock extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, WriterException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PdfTicket.fxml"));
            Parent root = loader.load();


            PdfHandler pdfHandler = loader.getController();
            if (pdfHandler == null) {
                throw new IllegalStateException("Controller not initialized.");
            }

            String eventName = "Mock Event";
            String eventDate = "2024-01-01";
            String eventAddress = "123 Mock Street";
            String eventZIP = "12345";
            String eventCity = "MockCity";
            String eventType = "VIP";
            BufferedImage eventImage = generateMockImage();
            String uuid = UUID.randomUUID().toString();
            BufferedImage qrCodeImage = pdfHandler.generateQRCodeImage(uuid, 200, 200);

            // Set mock data
            pdfHandler.setTicketData(eventName, eventDate, eventAddress, eventZIP, eventCity, eventType, eventImage, qrCodeImage);

            // Generate PDF
            String destinationPath = "resources/Data/Pdf/mockTicket.pdf";
            pdfHandler.generatePDFAsync(destinationPath);

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static BufferedImage generateMockImage() throws IOException {
        InputStream is = PdfGeneratorMock.class.getResourceAsStream("/pictures/party.jpg");
        if (is == null) {
            throw new IOException("Image file not found");
        }
        BufferedImage image = ImageIO.read(is);
        return image;
    }
}
