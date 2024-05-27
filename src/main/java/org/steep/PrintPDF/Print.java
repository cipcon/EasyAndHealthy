/* package org.steep.PrintPDF;

import java.io.FileOutputStream;
import java.util.HashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.steep.Ingredients.Ingredients;
import com.steep.Recipe.CrudRecipe;
import com.steep.Recipe.SearchRecipe;

 - Alle Listen sollen druckbar sein (Einkaufsliste, Kochliste, Vorschlagsliste beim KeineAhnungModus)
    - Ein Rezept ist druckbar
    - Vorratsübersicht ist druckbar
 
public class Print {

    public static void shoppingListPDF(Ingredients ingredients, int userId) {
        CrudRecipe crudRecipe = new CrudRecipe();
        String recipe = "Ofenkartoffeln";
        int recipeId = crudRecipe.recipeId(recipe);
        
        SearchRecipe searchRecipe = new SearchRecipe();
        HashMap<String, Double> shoppingList = searchRecipe.shoppingList(4, recipeId, userId);
        
        try {
            String filename = "newPDF.pdf";
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();         
            

            for (String i : shoppingList.keySet()) {
                Paragraph paragraph = new Paragraph(i + " " + shoppingList.get(i) + " " + ingredients.ingredientUnit(i));
                document.add(paragraph);
                document.add(new Paragraph(" "));

            }
            

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

*/