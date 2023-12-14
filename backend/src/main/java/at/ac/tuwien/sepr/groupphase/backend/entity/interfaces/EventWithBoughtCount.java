package at.ac.tuwien.sepr.groupphase.backend.entity.interfaces;

import at.ac.tuwien.sepr.groupphase.backend.entity.Event;

public interface EventWithBoughtCount {

    Event getEvent();

    int getBoughtCount();
}
