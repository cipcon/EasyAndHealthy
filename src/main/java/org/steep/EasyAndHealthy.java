/* package com.steep;

import java.util.Scanner;
import com.steep.Database.DatabaseManagement;
import com.steep.Ingredients.Ingredients;
import com.steep.Recipe.CrudRecipe;
import com.steep.Recipe.SearchRecipe;
import com.steep.Stock.ManageStock;
import com.steep.User.*;

public class EasyAndHealthy {
    public static void main(String[] args) {
        DatabaseManagement.ConnectToDB();
        Scanner scan = new Scanner(System.in);

        // choose between login and register
        Boolean conditionLOrR = true;
        Boolean conditionCRUD = true;

        User user = null;

        try {
            while (conditionLOrR) {
                System.out.println("Please select to Login or to Register");
                String loginRegister = scan.nextLine().toLowerCase();

                if (loginRegister.equals("login")) {
                    user = Login.loginMethod();
                    if (user != null) {
                        System.out.println(
                                "\nHello " + user.getCurrentUsername() + ", you have the following ingredients: ");
                        for (String ing : ManageStock.readUserStock(user).keySet()) {
                            System.out.println(ing + " " + ManageStock.readUserStock(user).get(ing));
                        }
                    } else {
                        System.out.println("User not found");
                    }
                    conditionLOrR = false;
                } else if (loginRegister.equals("register")) {
                    Register.registerMethod();
                    conditionLOrR = false;
                } else {
                    continue;
                }
            }

            while (conditionCRUD) {
                System.out.println("What do you want to do next? (add, read, update, delete, close with exit)");
                String whatNext = scan.nextLine().toLowerCase();
                if (whatNext.equals("add")) {
                    ManageStock.addStock(user.getId(), "Tomaten", 4);

                } else if (whatNext.equals("read")) {
                    for (String ing : ManageStock.readUserStock(user).keySet()) {
                        System.out.println(ing + " " + ManageStock.readUserStock(user).get(ing));
                    }

                } else if (whatNext.equals("update")) {
                    ManageStock.updateStock(user.getId(), "Tomaten", 8);

                } else if (whatNext.equals("delete")) {
                    ManageStock.deleteStock(user.getId(), "Tomaten");
                }

                ///////////////// Recipes

                else if (whatNext.equals("create recipe")) {
                    CrudRecipe.createRecipe(user, "Pasta bolognesee", 2);

                } else if (whatNext.equals("add recipe to list")) {
                    CrudRecipe.addRecipeToUser(user, "Pasta bolognesee");

                } else if (whatNext.equals("add ingredient to recipe")) {
                    CrudRecipe.addIngredientToCreatedRecipe("Pasta Bolognesee", "Nudeln", 200);

                } else if (whatNext.equals("read all recipes")) {
                    CrudRecipe.readAllRecipes();

                } else if (whatNext.equals("recipes from user")) {
                    CrudRecipe.recipesFromUser(user);

                } else if (whatNext.equals("update ingredient quantity")) {
                    CrudRecipe.updateRecipeIngredientTable(user, "Pasta Bolognesee", "Nudeln", 250);

                } else if (whatNext.equals("update recipe")) {
                    CrudRecipe.updateGlobalRecipe(user, "Pasta Bolognesee", "Pasta Bolognese", 4);

                } else if (whatNext.equals("delete recipe global")) {
                    CrudRecipe.deleteRecipeGlobally("Pasta Bolognese", user);

                } else if (whatNext.equals("delete recipe from user list")) {
                    CrudRecipe.deleteFromRecipeUserTable("Pasta Bolognese", user);

                }

                // Ingredients

                else if (whatNext.equals("create ingredient")) {
                    //Ingredients.createIngredientGlobal("Papaya", "stück");

                } /* else if (whatNext.equals("read all ingredients")) {
                    for (String ingredient : Ingredients.readAllIngredients()) {
                        System.out.println(ingredient + " " + Ingredients.ingredientUnit(ingredient));
                    }

                }  else if (whatNext.equals("read ingredients")) {
                    for (String ingredient : Ingredients.readIngredients("Mehl")) {
                        System.out.println(ingredient + " " + Ingredients.ingredientUnit(ingredient));
                    }

                } else if (whatNext.equals("read recipe ingredients")) {
                    for (String ingredient : Ingredients.readRecipeIngredients("Ofenkartoffeln").keySet()) {
                        System.out.println(
                                ingredient + " " + Ingredients.readRecipeIngredients("Ofenkartoffeln").get(ingredient));
                    }

                } else if (whatNext.equals("update ingredient")) {
                    Ingredients.updateGlobalIngredient("Pfaumen", "Pflaumen", "g");

                } else if (whatNext.equals("delete ingredient")) {
                    Ingredients.deleteGlobalIngredient("Pflaumen");

                    // SearchRecipe Class

                } else if (whatNext.equals("search recipe")) {
                    SearchRecipe.recipeSearch("Nudeln");

                } else if (whatNext.equals("cooking plan")) {
                    for (String ingredient : SearchRecipe.cookingPlan("Ofenkartoffeln", 4).keySet()) {
                        System.out.println(
                                ingredient + " " + SearchRecipe.cookingPlan("Ofenkartoffeln", 4).get(ingredient) + " "
                                        + Ingredients.ingredientUnit(ingredient));
                    }

                } else if (whatNext.equals("shopping list")) {
                    for (String ingredient : SearchRecipe.shoppingList("Ofenkartoffeln", 2, user).keySet()) {
                        System.out.println(ingredient + " " + SearchRecipe.shoppingList(null, 4, user).get(ingredient) + " " + Ingredients.ingredientUnit(ingredient));
                    } 

                } 
                else if (whatNext.equals("no idea mode")) {
                    for (String recipe : SearchRecipe.noIdeaMode()) {
                        System.out.println(recipe);
                    }

                }
                else if (whatNext.equals("exit") || whatNext.equals("close")) {
                    break;
                }else {
                    continue;
                }
            }
            scan.close();

        } finally {
            DatabaseManagement.CloseResources();
        }
    }
}
 */