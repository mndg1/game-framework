package me.kap.gfw.events.timing;

import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TimerComponentTest {
    private final Plugin pluginFake;
    private final EventTimer timerFake;
    private final TimerComponent timerComponent;

    TimerComponentTest() {
        pluginFake = mock(Plugin.class);
        timerFake = mock(EventTimer.class);
        timerComponent = new TimerComponent(pluginFake, timerFake);
    }

    @Test
    void whenStart_thenContainedTimerIsStarted() {
        // act
        timerComponent.start();

        // assert
        verify(timerFake).startTimer(pluginFake);
    }

    @Test
    void whenEnd_thenContainedTimerIsStopped() {
        // act
        timerComponent.end();

        // assert
        verify(timerFake).stopTimer();
    }
}