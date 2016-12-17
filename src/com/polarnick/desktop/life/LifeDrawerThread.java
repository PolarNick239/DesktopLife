package com.polarnick.desktop.life;

import com.polarnick.desktop.MainFrame;
import com.polarnick.desktop.life.updaters.MultithreadedUpdater;
import com.polarnick.desktop.life.updaters.Updater;
import com.polarnick.desktop.life.updaters.SimpleUpdater;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Polyarniy Nikolay, 17.12.16
 */

public class LifeDrawerThread implements Runnable {

    private static final int DEFAULT_STATES_NUMBER = 15;

    private final MainFrame mainFrame;
    private final List<Updater> updaters;
    private volatile int curUpdater;

    private volatile boolean running = true;

    private final int n;
    private final int[] colorsPalette;

    private BufferedImage img = null;

    public LifeDrawerThread(MainFrame mainFrame) {
        this(mainFrame, DEFAULT_STATES_NUMBER);
    }

    public LifeDrawerThread(MainFrame mainFrame, int n) {
        this.mainFrame = mainFrame;

        this.n = n;
        this.colorsPalette = generateColors(n);
        this.updaters = Arrays.<Updater>asList(new SimpleUpdater());
        this.curUpdater = 0;
    }

    private static int[] generateColors(int n) {
        int[] colors = new int[n];

        Random r = new Random(239);
        for (int i = 0; i < n; ++i) {
            // * TODO генерировать симпатичную палитру :)
            // Например тут можно подглядеть: http://stackoverflow.com/questions/43044/algorithm-to-randomly-generate-an-aesthetically-pleasing-color-palette
            // Или посмотри это видео: https://www.youtube.com/watch?v=FIRT7lf8byw
            colors[i] = color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        }
        return colors;
    }

    @Override
    public void run() {
        while (running) {
            Updater updater = updaters.get(curUpdater);

            if (img == null || img.getWidth() != mainFrame.getWidth() || img.getHeight() != mainFrame.getHeight()) {
                img = new BufferedImage(mainFrame.getWidth(), mainFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
            }
            if (img.getWidth() != updater.getWidth() || img.getHeight() != updater.getHeight()) {
                for (Updater u : updaters) {
                    u.cleanup();
                }
                updater.setup(img.getWidth(), img.getHeight(), n);
            }

            // 1.2 TODO замерять время вычисления функции updater.next() (System.currentTimeMillis())
            int[] state;
            try {
                state = updater.next();
            } catch (InterruptedException e) {
                stop();
                break;
            }
            long passed = ...;

            // Заполняем (BufferedImage this.img) по-пиксельно цветами
            int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
            for(int i = 0; i < state.length; i++) {
                pixels[i] = colorsPalette[state[i]]; // По состоянию берем цвет и кладем его в положение где находится это состояние
            }

            // 1.3 TODO выводить сообщение с временем вычисления updater.next() + количество обрабатываемых клеток в секунду (в миллионах в секунду)
            // Чтобы понять, как число превратить в строку рекомендую призвать на помощь могучий интернет мантрой навроде "java как int в string"
            String message = ...;
            message += " - " + updater.getName();
            mainFrame.paint(img, message);
        }
    }

    public void stop() {
        running = false;
    }

    public void nextUpdater() {
        // 2.3 TODO сделать так, чтобы начал использоваться другой метод вычисления + надо как-то добавить в перечень алгоритмов - MultithreadedUpdater
    }

    private static int color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    private static int color(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

}
