package org.steep.Ingredients;

import java.util.ArrayList;

public enum UnitEnum {
    Bund,
    EL,
    g,
    Handvoll,
    kg,
    l,
    ml,
    Prise,
    Scheibe,
    Stück,
    TL,
    Zehe,
    Zweige;

    public static ArrayList<String> allUnits() {
        ArrayList<String> units = new ArrayList<>();
        for (UnitEnum unit : UnitEnum.values()) {
            units.add(unit.name());
        }
        return units;
    }
}
