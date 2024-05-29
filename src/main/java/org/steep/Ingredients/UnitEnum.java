package org.steep.Ingredients;

import java.util.ArrayList;

public enum UnitEnum {
    BUND,
    EL,
    G,
    HANDVOLL,
    KG,
    L,
    ML,
    PRISE,
    SCHEIBE,
    STÜCK,
    TL,
    ZEHE,
    ZWEIGE;

    public static ArrayList<String> allUnits() {
        ArrayList<String> units = new ArrayList<>();
        for (UnitEnum unit : UnitEnum.values()) {
            units.add(unit.name());
        }
        return units;
    }
}
