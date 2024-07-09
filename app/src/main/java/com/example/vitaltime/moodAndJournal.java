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
        if (getArguments() != null) {
            DiaryEntry receivedEntry = getArguments().getParcelable("selectedEntry");
            setDiaryEntryValues(receivedEntry);
        }
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
                moodClicked(sadButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.lightning);
                textView.setTypeface(customFont);

            }
        });
        binding.happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(happyButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.comicpillow);
                textView.setTypeface(customFont);
            }
        });
        binding.boredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(boredButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.lemonshake);
                textView.setTypeface(customFont);
            }
        });
        binding.excitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             moodClicked(excitedButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.koass);
                textView.setTypeface(customFont);
            }
        });
        binding.frustratedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(frustratedButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.safetyswitch);
                textView.setTypeface(customFont);
            }
        });
        binding.lovedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(lovedButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.elatox);
                textView.setTypeface(customFont);
            }
        });
        binding.lonelyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(lonelyButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.grunge);
                textView.setTypeface(customFont);
            }
        });
        binding.relaxedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(relaxedButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.benjiro);
                textView.setTypeface(customFont);
            }
        });
        binding.anxiousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodClicked(anxiousButton);
                TextView textView = binding.editTextDiaryContent;
                Typeface customFont = getResources().getFont(R.font.pakuintho);
                textView.setTypeface(customFont);
            }
        });

    }

    private void colorChange(Button button, int color) {

        button.setBackgroundColor(color);
    }

    private void moodClicked(Button clickedButton) {
        for (Button button : buttonMoods){
            colorChange(button, button == clickedButton ? Color.LTGRAY : Color.GRAY);
        }
    }

    private void setDiaryEntryValues(DiaryEntry entry) {

        binding.entryTextView.setText(entry.getTitle()); //Entry title
        binding.editTextDiaryContent.setText(entry.getContent()); //Entry text

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM d, yyyy");
        String formattedDate = sdf.format(entry.getDate());
         binding.entryDateView.setText(formattedDate); //Entry Date

         String moodId = entry.getMood();
         for (Button button : buttonMoods) {
             if (getResources().getResourceEntryName(button.getId()).equals(moodId)) { colorChange(button, Color.LTGRAY); }
             else { colorChange(button, Color.DKGRAY); }
         }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
