package com.example.vitaltime;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.MoodJournalBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class moodAndJournal extends Fragment {

    private MoodJournalBinding binding;
    private Button happyButton, sadButton, excitedButton, boredButton, frustratedButton, lovedButton,
            relaxedButton, lonelyButton, anxiousButton;
    private final Vector<Button> buttonMoods = new Vector<>();
    private Button selectedButton = null;
    private Date entryDate = new Date();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = MoodJournalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //The buttons
        {
            sadButton = view.findViewById(R.id.sadButton);
            happyButton = view.findViewById(R.id.happyButton);
            excitedButton = view.findViewById(R.id.excitedButton);
            boredButton = view.findViewById(R.id.boredButton);
            frustratedButton = view.findViewById(R.id.frustratedButton);
            lovedButton = view.findViewById(R.id.lovedButton);
            relaxedButton = view.findViewById(R.id.relaxedButton);
            lonelyButton = view.findViewById(R.id.lonelyButton);
            anxiousButton = view.findViewById(R.id.anxiousButton);

            //Adding buttons to a vector for the method setDiaryEntryValues()
            buttonMoods.add(happyButton);
            buttonMoods.add(sadButton);
            buttonMoods.add(excitedButton);
            buttonMoods.add(boredButton);
            buttonMoods.add(frustratedButton);
            buttonMoods.add(lovedButton);
            buttonMoods.add(relaxedButton);
            buttonMoods.add(lonelyButton);
            buttonMoods.add(anxiousButton);
        }

        //Checks to see if there was a diary entry passed into the fragment
        if (getArguments() != null) {
            DiaryEntry receivedEntry = getArguments().getParcelable("selectedEntry");
            setDiaryEntryValues(receivedEntry);
        }

        //Passes the data to the home fragment to save
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaryEntry saveEntry = SaveDiaryEntry();
                Bundle bundle = new Bundle();
                bundle.putParcelable("newEntry", saveEntry);
                NavHostFragment.findNavController(moodAndJournal.this)
                        .navigate(R.id.action_moodAndJournal_to_journalHome, bundle);
            }
        });

        //Shows the date of the entry
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM d, yyyy");
        String formattedDate = sdf.format(entryDate);
        binding.entryDateView.setText(formattedDate);

        //Mood button methods
        {
            binding.sadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(sadButton);
                }
            });
            binding.happyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(happyButton);
                }
            });
            binding.boredButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(boredButton);
                }
            });
            binding.excitedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(excitedButton);
                }
            });
            binding.frustratedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(frustratedButton);
                }
            });
            binding.lovedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(lovedButton);
                }
            });
            binding.lonelyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(lonelyButton);
                }
            });
            binding.relaxedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(relaxedButton);
                }
            });
            binding.anxiousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moodClicked(anxiousButton);
                }
            });
        }

    }

    //Changes the color of a button
    private void colorChange(Button button, int color) {

        button.setBackgroundColor(color);
    }

    //Implementation of when a mood is clicked
    private void moodClicked(Button clickedButton) {
        for (Button button : buttonMoods){
            if (button == clickedButton) {
                TextView textView = binding.editTextDiaryContent;
                Map<Button,Typeface> fonts = Fonts();
                textView.setTypeface(fonts.get(button));
                colorChange(button, Color.LTGRAY);
                selectedButton = button;
            } else {  colorChange(button, Color.DKGRAY); }
        }
    }

    //Opens a Diary Entry data
    private void setDiaryEntryValues(DiaryEntry entry) {

        binding.entryTextView.setText(entry.getTitle()); //Entry title
        binding.editTextDiaryContent.setText(entry.getContent()); //Entry text

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM d, yyyy");
        String formattedDate = sdf.format(entry.getDate());
        binding.entryDateView.setText(formattedDate); //Entry Date
        entryDate = entry.getDate();
        if (entry.getMood() != null) {
            String moodId = entry.getMood();
            for (Button button : buttonMoods) {
                if (getResources().getResourceEntryName(button.getId()).equals(moodId)) {
                    moodClicked(button);
                }
            }
        }
    }

    //Saves the current data
    private DiaryEntry SaveDiaryEntry() {
        String title = String.valueOf(binding.entryTextView.getText());
        if (title.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM d, yyyy");
            String formattedDate = sdf.format(entryDate);
            title = formattedDate;
        }
        String content = String.valueOf(binding.editTextDiaryContent.getText());
        String mood;
        if (selectedButton != null) { mood = getResources().getResourceEntryName(selectedButton.getId()); }
        else { mood = null; }
        DiaryEntry newEntry = new DiaryEntry(entryDate, title, mood, content);
        return newEntry;
    }

    //Makes a map of the fonts
    private Map<Button,Typeface> Fonts (){
        Map<Button,Typeface> fonts = new HashMap<Button,Typeface>();
        fonts.put(sadButton, getResources().getFont(R.font.cinema));
        fonts.put(happyButton, getResources().getFont(R.font.comicpillow));
        fonts.put(boredButton, getResources().getFont(R.font.lemonshake));
        fonts.put(excitedButton, getResources().getFont(R.font.donperry));
        fonts.put(frustratedButton, getResources().getFont(R.font.safetyswitch));
        fonts.put(lovedButton, getResources().getFont(R.font.elatox));
        fonts.put(lonelyButton, getResources().getFont(R.font.grunge));
        fonts.put(relaxedButton, getResources().getFont(R.font.february));
        fonts.put(anxiousButton, getResources().getFont(R.font.pakuintho));
        return fonts;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
