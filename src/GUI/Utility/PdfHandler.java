package GUI.Utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.layout.element.Image;
import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PdfHandler implements Initializable {

    @FXML
    private ImageView eventImage, qrCode;
    @FXML
    private Label lblDatePdf;
    @FXML
    private Label lblEventCity;
    @FXML
    private Label addressEvent;
    @FXML
    private Label lblEventName;
    @FXML
    private Label lblEventZip;
    @FXML
    private Pane panePdfTicket;
    @FXML
    private Pane paneLabel;
    @FXML
    private Label lblHeadName, lblHeadAddress, lblHeadDate;
    @FXML
    private Label lblTicketHeader;
    @FXML
    private Label lblTicketType;
    @FXML
    private Label lblHeadTicketType;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        panePdfTicket.setStyle("-fx-background-color: #108CDC;");
        paneLabel.setStyle("-fx-background-color: #FBBB2C;");
        applyLabelStyles();

    }

    public void applyLabelStyles() {
        Platform.runLater(() -> {
            lblTicketHeader.setFont(Font.font("System", FontWeight.BOLD ,40));

            lblEventName.setFont(Font.font("System", FontWeight.BOLD, 12));
            lblDatePdf.setFont(Font.font("System", FontWeight.BOLD, 12));
            addressEvent.setFont(Font.font("System", FontWeight.BOLD, 12));
            lblEventZip.setFont(Font.font("System", FontWeight.BOLD, 12));
            lblEventCity.setFont(Font.font("System", FontWeight.BOLD, 12));
            lblTicketType.setFont(Font.font("System", FontWeight.BOLD, 12));

            lblHeadName.setFont(Font.font("System", FontWeight.BOLD ,16));
            lblHeadAddress.setFont(Font.font("System", FontWeight.BOLD ,16));
            lblHeadDate.setFont(Font.font("System", FontWeight.BOLD ,16));
            lblHeadTicketType.setFont(Font.font("System", FontWeight.BOLD ,16));

        });
    }

    // Method to set the data on the ticket layout
    public void setTicketData(String eventName, String eventDate, String eventAddress, String eventZIP, String eventCity, String eventType, BufferedImage eventImage, BufferedImage qrCodeImage) {
        lblEventName.setText(eventName);
        lblDatePdf.setText(eventDate);
        addressEvent.setText(eventAddress);
        lblEventZip.setText(eventZIP);
        lblEventCity.setText(eventCity);
        lblTicketType.setText(eventType);

        if (eventImage != null) {
            this.eventImage.setImage(SwingFXUtils.toFXImage(eventImage, null));
        } else {
            this.eventImage.setImage(null); // Set to null or a default image
        }

        if (qrCodeImage != null) {
            this.qrCode.setImage(SwingFXUtils.toFXImage(qrCodeImage, null));
        } else {
            this.qrCode.setImage(null); // Set to null or a default image
        }
    }

    public BufferedImage generateQRCodeImage(String data, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? java.awt.Color.BLACK.getRGB() : java.awt.Color.WHITE.getRGB());
            }
        }
        return image;
    }

    public CompletableFuture<String> generatePDFAsync(String destinationPath) {
        CompletableFuture<WritableImage> futureImage = new CompletableFuture<>();

        Platform.runLater(() -> {
            try {
                WritableImage writableImage = panePdfTicket.snapshot(new SnapshotParameters(), null);
                futureImage.complete(writableImage);
            } catch (Exception e) {
                futureImage.completeExceptionally(e);
            }
        });

        return futureImage.thenApplyAsync(writableImage -> {
            try {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                try (PdfWriter writer = new PdfWriter(destinationPath);
                     PdfDocument pdfDocument = new PdfDocument(writer);
                     Document document = new Document(pdfDocument)) {
                    ImageData imageData = ImageDataFactory.create(imageBytes);
                    Image pdfImage = new Image(imageData);
                    document.add(pdfImage);

                    document.close();
                }
                return destinationPath;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }




    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }



}
