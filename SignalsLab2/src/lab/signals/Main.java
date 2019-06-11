package lab.signals;

import lab.signals.commons.BinarizeHalftone;
import lab.signals.commons.BradleyBinarizeHalftone;
import lab.signals.commons.OtsuBinarizeHalftone;
import lab.signals.commons.OtsuBinarizeHalftoneOpenCV;
import org.opencv.core.Core;
import lab.signals.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * Лабораторная работа №2.
 * <p>
 * Реализовать бинаризацию полутонового изображения:
 * <p>
 * 1) с ручным выбором порога(threshold).
 * 2) с выбором порога методом Оцу.
 * а) с помощью OpenCV.
 * б) вручную.
 * 3) методом Брэдли - вручную.
 * При демонстрации работы - использовать фотографию текста с сильно неравномерным
 * освещением.
 */
public class Main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        System.out.println("Введите 1-5:");
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        BufferedImage destination = null;
        BufferedImage image = Utils.readImageFromResources();
        switch (value) {
            case 1:
                destination = new BinarizeHalftone().binarize(image, 90);
                break;
            case 2:
                destination = new BinarizeHalftone().binarize2(image, 40);
                break;
            case 3:
                destination = new OtsuBinarizeHalftone().otsuBinarize(image);
                break;
            case 4:
                new OtsuBinarizeHalftoneOpenCV().thresholdOpenCV();
                break;
            case 5:
                destination = new BradleyBinarizeHalftone().bradleyBinarize(image, image.getWidth() / 8, 1);
                break;
        }
        if (destination != null) {
            Utils.writeImageToResources(destination);
        }
    }

}
