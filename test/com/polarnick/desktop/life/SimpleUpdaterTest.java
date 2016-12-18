package com.polarnick.desktop.life;

import com.polarnick.desktop.life.updaters.SimpleUpdater;
import com.polarnick.desktop.life.updaters.Updater;

/**
 * Polyarniy Nikolay, 18.12.16
 */
public class SimpleUpdaterTest extends UpdaterTest {

    @Override
    public Updater newUpdater() {
        return new SimpleUpdater();
    }

}