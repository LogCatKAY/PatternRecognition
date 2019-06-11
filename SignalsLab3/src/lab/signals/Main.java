package lab.signals;

import lab.signals.common.LabViaOpenCv;
import org.opencv.core.Core;

//Лаба 3
// Реализовать через openCV эрозию, дилатацию, замыкание, размыкание, условную дилатацию, скелетирование.

public class Main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        new LabViaOpenCv().start();
    }
}
