package com.polarnick.desktop.life;

import com.polarnick.desktop.life.updaters.MultithreadedUpdater;
import com.polarnick.desktop.life.updaters.Updater;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public class MultithreadedUpdaterTest extends UpdaterTest {

    @Override
    public Updater newUpdater() {
        return new MultithreadedUpdater();
    }

}