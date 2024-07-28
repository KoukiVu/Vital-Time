package com.example.vitaltime;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

// Imports for AI
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import yuku.ambilwarna.AmbilWarnaDialog;

import java.util.concurrent.Executor;


public class moodAndJournal extends Fragment {

    private MenuItem item;
    int defaultColor = Color.WHITE;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_color) {
            openColorPicker();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(requireContext(), defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // Do nothing on cancel
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                binding.editTextDiaryContent.setTextColor(defaultColor);
            }
        });
        colorPicker.show();
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
                SaveDiaryEntry();
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
                    TextView textView = binding.editTextDiaryContent;
                    Typeface customFont = getResources().getFont(R.font.cinema);
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
                    Typeface customFont = getResources().getFont(R.font.donperry);
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
                    Typeface customFont = getResources().getFont(R.font.february);
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

    }

    //Changes the color of a button
    private void colorChange(Button button, boolean clicked) {


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        int themeId = prefs.getInt("theme", R.style.Theme_VitalTime);

        if (themeId == R.style.VitalTime_Light) {
            button.setTextColor(getResources().getColor(R.color.lightText));
            if (clicked)
                button.setBackgroundColor(getResources().getColor(R.color.lightMid));
            else
                button.setBackgroundColor(getResources().getColor(R.color.lightSecondary));
        }
        else if (themeId == R.style.VitalTime_Coffee) {
            button.setTextColor(getResources().getColor(R.color.coffeeText));
            if (clicked)
                button.setBackgroundColor(getResources().getColor(R.color.coffeeDark));
            else
                button.setBackgroundColor(getResources().getColor(R.color.coffeeSecondary));
        }
        else if (themeId == R.style.VitalTime_Dark) {
            button.setTextColor(getResources().getColor(R.color.darkText));
            if (clicked)
                button.setBackgroundColor(getResources().getColor(R.color.darkMid));
            else
                button.setBackgroundColor(getResources().getColor(R.color.darkSecondary));
        }
        else if (themeId == R.style.VitalTime_Pastel) {
            button.setTextColor(getResources().getColor(R.color.pastelText));
            if (clicked)
                button.setBackgroundColor(getResources().getColor(R.color.pastelSecondary));
            else
                button.setBackgroundColor(getResources().getColor(R.color.pastelPink));
        }
        else if (themeId == R.style.VitalTime_Midnight) {
            button.setTextColor(getResources().getColor(R.color.midnightText));
            if (clicked)
                button.setBackgroundColor(getResources().getColor(R.color.black));
            else
                button.setBackgroundColor(getResources().getColor(R.color.midnightSecondary));
        }
    }

    //Implementation of when a mood is clicked
    private void moodClicked(Button clickedButton) {
        for (Button button : buttonMoods){
            if (button == clickedButton) {
                TextView textView = binding.editTextDiaryContent;
                Map<Button,Typeface> fonts = Fonts();
                textView.setTypeface(fonts.get(button));
                colorChange(button, true);
                selectedButton = button;
            } else {  colorChange(button, false); }
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
    private void SaveDiaryEntry() {
        CreateDiaryEntryTitle(new TitleGenerationCallback() {
            public void onTitleGenerated(String title) {
                String content = String.valueOf(binding.editTextDiaryContent.getText());
                String mood;
                if (selectedButton != null) { mood = getResources().getResourceEntryName(selectedButton.getId()); }
                else { mood = null; }
                DiaryEntry newEntry = new DiaryEntry(entryDate, title, mood, content);

                // Pass the data to the home fragment to save
                Bundle bundle = new Bundle();
                bundle.putParcelable("newEntry", newEntry);
                NavHostFragment.findNavController(moodAndJournal.this)
                        .navigate(R.id.action_moodAndJournal_to_journalHome, bundle);
            }
        });
    }

    public interface TitleGenerationCallback {
        void onTitleGenerated(String title);
    }

    private void CreateDiaryEntryTitle(TitleGenerationCallback callback) {
        String content = String.valueOf(binding.editTextDiaryContent.getText());
        TextView titleView = binding.entryTextView;
        String title = titleView.getText().toString();

        if (title.isEmpty()) {
            // For text-only input, use the gemini-pro model
            GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-pro", /* apiKey */ "AIzaSyBlslMky_9x02o6qtUxlbD0slpLVOSQX0Q");
            GenerativeModelFutures model = GenerativeModelFutures.from(gm);
            Content textContent = new Content.Builder().addText("Create a title with 20 characters or less for the text: " + content).build();

            ListenableFuture<GenerateContentResponse> response = model.generateContent(textContent);
            Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    String resultText = result.getText();
                    titleView.setText(resultText);
                    callback.onTitleGenerated(resultText);
                }

                @Override
                public void onFailure(Throwable t) {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMMM d, yyyy");
                    String formattedDate = sdf.format(entryDate);
                    titleView.setText(formattedDate);
                    callback.onTitleGenerated(formattedDate);
                }
            }, new Handler(Looper.getMainLooper())::post);
        } else {
            callback.onTitleGenerated(title);
        }
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
