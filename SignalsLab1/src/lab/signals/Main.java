package lab.signals;

import org.opencv.core.Core;

import java.io.IOException;

//Лаба 1
// Методом свертки пропустить через фильтр изображение: с помощью openCV и вручную.

public class Main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {

        new LabViaOpenCv().start();

        System.out.println();

        try {
            new LabViaOurFilter().start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
