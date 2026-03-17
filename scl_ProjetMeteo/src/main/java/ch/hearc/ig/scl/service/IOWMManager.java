package ch.hearc.ig.scl.service;

import ch.hearc.ig.scl.business.Meteo;
import ch.hearc.ig.scl.business.Pays;
import ch.hearc.ig.scl.business.StationMeteo;

public interface IOWMManager {
    boolean insertAll(Meteo meteo, Pays pays, StationMeteo stationMeteo);
}
