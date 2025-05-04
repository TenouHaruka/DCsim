package DCsim.ui;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.objdetect.QRCodeDetector;

import java.util.*;

public class Vision {
    private VideoCapture cap;
    private QRCodeDetector qrDetector;
    private Mat frame;

    // Map: targetID -> VisionTarget
    private Map<Integer, VisionTarget> visionTargets;

    public void setup() {
        cap = new VideoCapture(0);
        cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 1920);
        cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1080);

        qrDetector = new QRCodeDetector();
        frame = new Mat();

        visionTargets = new HashMap<>();
    }

    public void loop() {
        if (!cap.read(frame)) return;

        List<String> decodedList = new ArrayList<>();
        Mat points = new Mat();

        boolean found = qrDetector.detectAndDecodeMulti(frame, decodedList, points);

        // Track active IDs this frame
        Set<Integer> activeIDs = new HashSet<>();

        if (found && !decodedList.isEmpty() && points.rows() > 0) {
            for (int i = 0; i < decodedList.size(); i++) {
                Mat pointsRow = points.row(i).reshape(2, 4);
                double xSum = 0, ySum = 0;

                for (int j = 0; j < 4; j++) {
                    double[] pt = pointsRow.get(j, 0);
                    if (pt == null) continue;
                    xSum += pt[0];
                    ySum += pt[1];
                }

                int targetID;
                try {
                    targetID = Integer.parseInt(decodedList.get(i));
                } catch (Exception e) {
                    System.out.println("Invalid QR content: " + decodedList.get(i));
                    continue;
                }

                activeIDs.add(targetID);

                VisionTarget target = visionTargets.get(targetID);
                if (target == null) {
                    target = new VisionTarget(targetID);
                    visionTargets.put(targetID, target);
                    addBlock(targetID);
                }

                target.addVisionMeasurement(xSum / 4, ySum / 4);
                moveBlock(targetID, xSum / 4, ySum / 4);
            }
        }

        // Clean up: remove inactive targets
        Iterator<Map.Entry<Integer, VisionTarget>> iter = visionTargets.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, VisionTarget> entry = iter.next();
            int id = entry.getKey();
            VisionTarget target = entry.getValue();

            if (!activeIDs.contains(id) && target.shouldDisapear()) {
                deleteBlock(id);
                iter.remove();
            }
        }
    }

    private void addBlock(int blockID) {
        System.out.printf("Adding block %d\n", blockID);
    }

    private void deleteBlock(int blockID) {
        System.out.printf("Deleting block %d\n", blockID);
    }

    private void moveBlock(int blockID, double x, double y) {
        System.out.printf("Moving block %d to %.2f, %.2f\n", blockID, x, y);
    }
}
