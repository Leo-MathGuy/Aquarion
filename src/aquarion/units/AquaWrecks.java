package aquarion.units;

import aquarion.type.DerelictUnit;

public class AquaWrecks {
    public static DerelictUnit zoarcidWreck, messengerWreck, gossWreck;
    public static void loadContent() {
        zoarcidWreck = new DerelictUnit("zoarcid-wreck");
        gossWreck = new DerelictUnit("goss-wreck");
        messengerWreck = new DerelictUnit("messenger-wreck");
    }
}
