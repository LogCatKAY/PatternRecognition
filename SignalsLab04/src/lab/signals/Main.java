package lab.signals;

import lab.signals.common.TestSift;
import org.opencv.core.Core;

// Лаба 4
// С помощью SIFT из openCV сопоставить по ключевым точкам изображения.

public class Main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {
        new TestSift().workSift();
    }
}
