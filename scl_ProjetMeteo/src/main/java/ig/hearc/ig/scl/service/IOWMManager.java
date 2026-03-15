package ig.hearc.ig.scl.service;

import ig.hearc.ig.scl.business.Meteo;
import ig.hearc.ig.scl.business.Pays;
import ig.hearc.ig.scl.business.StationMeteo;

public interface IOWMManager {
    void insertAll(Meteo meteo, Pays pays, StationMeteo stationMeteo);
}
