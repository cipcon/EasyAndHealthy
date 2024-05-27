package org.steep.StockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.steep.Stock.CurrentStock;

public class CurrentStockTest {
    @Test
    void ingredients() {
        CurrentStock currentStock =  new CurrentStock();
        currentStock.addIngredientAndQuantity("Papaya", 1);

        assertTrue(currentStock.getIngredientAndQuantity().containsKey("Papaya"));
        assertEquals(1, currentStock.getIngredientAndQuantity().get("Papaya"));
    }
}
