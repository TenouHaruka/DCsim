package DCsim.ui;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import DCsim.components.Module;
import DCsim.handler.ModuleHandler;

import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.highgui.HighGui;

import java.util.ArrayList;
import java.util.List;

public class Vision 
{
    private VideoCapture cap;
    private QRCodeDetector qrDetector;
    private Mat frame;

    public void setup()
    {
        cap = new VideoCapture(0);  
        cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 1920);
        cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1080);

        qrDetector = new QRCodeDetector();
        frame = new Mat();
    }
    public void loop()
    {
        System.out.println("AYUDA");
        if(!cap.read(frame)) 
            return;

        List<String> decodedList = new ArrayList<>();
        Mat points = new Mat();

        boolean found = qrDetector.detectAndDecodeMulti(frame, decodedList, points);

        if (found && !decodedList.isEmpty() && points.rows() > 0) 
        {
            int numQR = points.rows();

            for (int i = 0; i < numQR; i++) 
            {
                Point[] corners = new Point[4];

                System.out.println("Corner : ");
                for (int j = 0; j < 4; j++) 
                {
                    double[] pt = points.get(i, j);
                    
                    corners[j] = new Point(pt[0], pt[1]);
                }
            }
        }

    }
}
