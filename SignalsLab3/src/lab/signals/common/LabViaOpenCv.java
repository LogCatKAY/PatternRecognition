package lab.signals.common;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class LabViaOpenCv {

    private final String srcFilename = "images/img.png";
    private final String srcFilenameForCondDilate = "images/img_test.png";
    private final String destErodeFilename = "images/img_erode.png";
    private final String destDilateFilename = "images/img_dilate.png";
    private final String destClosingFilename = "images/img_closing.png";
    private final String destOpeningFilename = "images/img_opening.png";
    private final String destSkeletonFilename = "images/img_skeleton.png";
    private final String destCondDilateFilename = "images/img_cond_dilate.png";

    public void start() {
        System.out.println("Start via openCV...");

        Mat image = Imgcodecs.imread(srcFilename);
        Mat imageForCondDilate = Imgcodecs.imread(srcFilenameForCondDilate);

        erode(image);
        dilate(image);
        closing(image);
        opening(image);
        conditionalDilate(imageForCondDilate);
        skeletonization(image);

        System.out.println("<<<SUCCESS>>>");
    }

    private Mat erode(Mat srcImage) {
        Mat destImage = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        Imgproc.cvtColor(
                srcImage,
                destImage,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.threshold(destImage, destImage, 0, 255, Imgproc.THRESH_OTSU);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.erode(destImage,destImage, kernel);
        Imgcodecs.imwrite(destErodeFilename, destImage);
        return destImage;
    }

    private Mat dilate(Mat srcImage) {
        Mat destImage = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        Imgproc.cvtColor(
                srcImage,
                destImage,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.threshold(destImage, destImage, 0, 255, Imgproc.THRESH_OTSU);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.dilate(destImage,destImage, kernel);
        Imgcodecs.imwrite(destDilateFilename, destImage);
        return destImage;
    }

    private Mat closing(Mat srcImage) {
        Mat destImage = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        Imgproc.cvtColor(
                srcImage,
                destImage,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.threshold(destImage, destImage, 0, 255, Imgproc.THRESH_OTSU);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.dilate(destImage,destImage, kernel);
        Imgproc.erode(destImage,destImage, kernel);
        Imgcodecs.imwrite(destClosingFilename, destImage);
        return destImage;
    }

    private Mat opening(Mat srcImage) {
        Mat destImage = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        Imgproc.cvtColor(
                srcImage,
                destImage,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.threshold(destImage, destImage, 0, 255, Imgproc.THRESH_OTSU);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.erode(destImage,destImage, kernel);
        Imgproc.dilate(destImage,destImage, kernel);
        Imgcodecs.imwrite(destOpeningFilename, destImage);
        return destImage;
    }

    private Mat conditionalDilate(Mat srcImage) {
        Mat img = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );

        Mat destImage = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        Imgproc.cvtColor(
                srcImage,
                destImage,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.cvtColor(
                srcImage,
                img,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.threshold(destImage, destImage, 0, 255, Imgproc.THRESH_OTSU);
        Imgproc.threshold(img, img, 0, 255, Imgproc.THRESH_OTSU);
        Mat kernelErode = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,3));
        Mat kernelDilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
        Imgproc.erode(destImage,destImage, kernelErode);

        int tempZeros = -1;
        int size = srcImage.cols() * srcImage.rows();
        boolean done = false;
        while (!done) {
            Imgproc.dilate(destImage,destImage, kernelDilate);
            Core.bitwise_and(destImage, img, destImage);
            int zeros = size - Core.countNonZero(destImage);
            if (zeros == tempZeros) {
                done = true;
            } else {
                tempZeros = zeros;
            }
        }

        Imgcodecs.imwrite(destCondDilateFilename, destImage);
        return destImage;
    }

    private Mat skeletonization(Mat srcImage) {
        Mat skel = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        int size = srcImage.cols() * srcImage.rows();
        Mat destImage = new Mat(
                srcImage.rows(),
                srcImage.cols(),
                CvType.CV_8UC1
        );
        Imgproc.cvtColor(
                srcImage,
                destImage,
                Imgproc.COLOR_RGB2GRAY
        );
        Imgproc.threshold(destImage, destImage, 127, 255, Imgproc.THRESH_BINARY);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3,3));

        boolean done = false;
        while (!done) {
            Mat eroded = new Mat();
            Mat temp = new Mat();
            Imgproc.erode(destImage, eroded, kernel);
            Imgproc.dilate(eroded, temp, kernel);
            Core.subtract(destImage,temp,temp);
            Core.bitwise_or(skel,temp,skel);
            destImage = eroded.clone();

            int zeros = size - Core.countNonZero(destImage);
            if (zeros == size) {
                done = true;
            }

        }
        Imgcodecs.imwrite(destSkeletonFilename, skel);
        return skel;
    }
}
