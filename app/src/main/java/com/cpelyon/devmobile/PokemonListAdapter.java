package com.cpelyon.devmobile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cpelyon.devmobile.databinding.PokemonItemBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.ViewHolder> {
    List<Pokemon> pokemonList;
    public PokemonListAdapter(List<Pokemon> pokemonList) {
        assert pokemonList != null;
        this.pokemonList = pokemonList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokemonItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.pokemon_item, parent, false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.binding.front.setImageResource(pokemon.getFrontResource());
        holder.binding.name.setText(pokemon.getName());
        holder.binding.type1Text.setText(pokemon.getType1String());
        holder.binding.number.setText("#"+pokemon.getOrder());
        if (pokemon.getType2() != null) {
            holder.binding.type2Text.setText(pokemon.getType2String());
        } else {
            holder.binding.type2Text.setText("");
        }
    }
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public com.cpelyon.devmobile.PokemonViewModel PokemonViewModel;
        private PokemonItemBinding binding;
        private PokemonViewModel viewModel = new PokemonViewModel();
        ViewHolder(PokemonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setPokemonViewModel(viewModel);
        }
    }

}
