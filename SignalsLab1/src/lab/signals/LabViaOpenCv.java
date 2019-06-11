package lab.signals;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class LabViaOpenCv {

    private final String srcFilename = "pics/photo1.jpg";
    private final String destFilename = "pics/photo1.png";

    public void start() {
        System.out.println("Start via openCV...");

        Mat image = Imgcodecs.imread(srcFilename);
        Mat gray = new Mat(
                image.rows(),
                image.cols(),
                CvType.CV_8UC1
        );

        System.out.println("Our photo is from " + srcFilename + "(" + image.cols() + "x" + image.rows() + ")");

        Imgproc.cvtColor(
                image,
                gray,
                Imgproc.COLOR_RGB2GRAY
        );

        Imgproc.GaussianBlur(
                gray,
                gray,
                new Size(5,5),
                3
        );
        Imgcodecs.imwrite(destFilename, gray);
        System.out.println("<<<SUCCESS>>>");
        System.out.println("Destination file is in " + destFilename);
    }
}
