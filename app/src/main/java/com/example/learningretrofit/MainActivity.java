package com.example.learningretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        searchRecipes();
        wireWidgets();
    }

    private void wireWidgets() {
        dish = findViewById(R.id.textView_dish_main);
    }

    private void searchRecipes() {
        //need GSON & converter-gson libraries for this step
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://recipepuppy.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipePuppyService service = retrofit.create(RecipePuppyService.class);
        //temporarily hardcode our food query
        //TODO made edittexts to enter search terms
        Call<RecipeResponse> recipeResponseCall = service.searchByIngredient("bacon", "eggs");

        recipeResponseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                List<Recipe> recipes =  response.body().getResults();
                dish.setText(recipes.toString());
                Log.d("ENQUEUE", "onResponse: " + recipes.toString());
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.d("ENQUEUE", "onFailure: " + t.getMessage());
            }
        });
    }
}
