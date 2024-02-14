package com.cpelyon.devmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cpelyon.devmobile.databinding.PokedexFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PokedexFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PokedexFragmentBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.pokedex_fragment,container,false);

        List<Pokemon> pokemonList = new ArrayList<>();
        InputStreamReader isr = new InputStreamReader(getResources().openRawResource(R.raw.pokemons));
        // Ouverture du fichier dans assets
        // InputStreamReader isr =
        // new InputStreamReader(getResources().getAssets().open("poke.json"));
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String data = "";
        //lecture du fichier. data == null => EOF
        while(data != null) {
            try {
                data = reader.readLine();
                builder.append(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Traitement du fichier
        Integer x = 0;
        try {
            x++;
            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String image = object.getString("image");
                POKEMON_TYPE type1 = POKEMON_TYPE.valueOf(object.getString("type1"));
                POKEMON_TYPE type2 = null;
                if (object.has("type2")) {
                    type2 = POKEMON_TYPE.valueOf(object.getString("type2"));
                }

                String numericPart = image.substring(1);

                int id = getResources().getIdentifier(image,"drawable",
                        binding.getRoot().getContext().getPackageName());
                Pokemon pokemon = new Pokemon(x, name, id, type1, type2);
                pokemonList.add(pokemon);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PokemonListAdapter adapter = new PokemonListAdapter(pokemonList);
        binding.pokemonList.setAdapter(adapter);

        binding.pokemonList.setLayoutManager(new LinearLayoutManager(
                binding.getRoot().getContext()));
        return binding.getRoot();
    }
}