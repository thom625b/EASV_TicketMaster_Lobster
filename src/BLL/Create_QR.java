package BLL;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Create_QR extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            String qrCodeData = "Fredagsbar EASV!";
            String charset = "UTF-8";

            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();

            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 500, 500, hintMap);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            Image qrImage = new Image(inputStream);
            ImageView imageView = new ImageView(qrImage);

            StackPane root = new StackPane();
            root.getChildren().add(imageView);

            Scene scene = new Scene(root, 600, 600);

            primaryStage.setTitle("QR Code Viewer");
            primaryStage.setScene(scene);
            primaryStage.show();

            System.out.println("QR Code created successfully!");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
