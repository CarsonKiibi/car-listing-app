package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEvent {
    private Event e;

    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Added car listing to list of listings");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("Added car listing to list of listings", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Added car listing to list of listings", e.toString());
    }
}
