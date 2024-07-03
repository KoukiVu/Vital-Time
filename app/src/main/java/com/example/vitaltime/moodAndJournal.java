package com.example.vitaltime;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.MoodJournalBinding;

import java.util.Vector;


public class moodAndJournal extends Fragment {

    private MoodJournalBinding binding;
    private Button happyButton, sadButton, excitedButton, boredButton, frustratedButton, lovedButton,
            relaxedButton, lonelyButton, anxiousButton;
    private final Vector<Button> buttonMoods = new Vector<>();
    private DiaryEntry receivedEntry = null;

    public static moodAndJournal newInstance(DiaryEntry entry) {
        moodAndJournal fragment = new moodAndJournal();
        fragment.receivedEntry = entry;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = MoodJournalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        if (receivedEntry != null) { setDiaryEntryValues(receivedEntry); } //Sets the values if an entry was passed in

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(moodAndJournal.this)
                        .navigate(R.id.action_moodAndJournal_to_journalHome);
            }
        });

        binding.sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(lovedButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(sadButton, Color.LTGRAY);
            }
        });
        binding.happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(happyButton, Color.LTGRAY);
            }
        });
        binding.boredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(boredButton, Color.LTGRAY);
            }
        });
        binding.excitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(excitedButton, Color.LTGRAY);
            }
        });
        binding.frustratedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(frustratedButton, Color.LTGRAY);
            }
        });
        binding.lovedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(lovedButton, Color.LTGRAY);
            }
        });
        binding.lonelyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(lonelyButton, Color.LTGRAY);
            }
        });
        binding.relaxedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(anxiousButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(relaxedButton, Color.LTGRAY);
            }
        });
        binding.anxiousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorChange(sadButton, Color.GRAY);
                colorChange(happyButton, Color.GRAY);
                colorChange(boredButton, Color.GRAY);
                colorChange(excitedButton, Color.GRAY);
                colorChange(frustratedButton, Color.GRAY);
                colorChange(lovedButton, Color.GRAY);
                colorChange(lonelyButton, Color.GRAY);
                colorChange(relaxedButton, Color.GRAY);
                colorChange(anxiousButton, Color.LTGRAY);
            }
        });


    }

    private void colorChange(Button button, int color) {

        button.setBackgroundColor(color);
    }

    private void setDiaryEntryValues(DiaryEntry entry) {
         binding.entryTextView.setText(entry.getContent());
         binding.entryNameView.setText(entry.getTitle());
         binding.entryDateView.setText(entry.getDate().toString());
         String moodId =entry.getMood();
         for (Button button : buttonMoods) {
             if (getResources().getResourceEntryName(button.getId()).equals(moodId)) { colorChange(button, Color.DKGRAY); }
             else { colorChange(button, Color.GRAY); }
         }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
