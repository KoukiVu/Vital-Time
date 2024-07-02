package com.example.vitaltime;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.FragmentJournalHomeBinding;

import java.util.Date;

public class JournalHome extends Fragment {

    private FragmentJournalHomeBinding binding;
    DiaryBook diaryBook = new DiaryBook();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = com.example.vitaltime.databinding.FragmentJournalHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView calView = binding.calendarView;
        final Date[] dateDate = {new Date(calView.getDate())};
        CardView entryCard = binding.CardView;

        ///Adding to the Diary
        DiaryEntry dayOne = new DiaryEntry(new Date(1719638573715L), "Day One Testing", "happyButton", "This is a test");
        DiaryEntry dayTwo = new DiaryEntry(new Date(1719033962142L), "Day Two Testing", "sadButton", "This is a test");
        diaryBook.addDiaryEntry(dayOne);
        diaryBook.addDiaryEntry(dayTwo);



        binding.buttonNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(JournalHome.this)
                        .navigate(R.id.action_journalHome_to_moodAndJournal);
            }
        });

        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                TextView titleText = binding.titleView;
                TextView dateText = binding.dateView;

                dateDate[0] = new Date(selectedDate.getTimeInMillis());
                DiaryEntry selectedEntry = diaryBook.getEntryKey(dateDate[0]);

                if (selectedEntry != null){
                    titleText.setText(selectedEntry.getTitle());
                    dateText.setText(selectedEntry.getDate().toString());
                    entryCard.setClickable(true);
                }
                else {
                    titleText.setText("No entry found");
                    dateText.setText(dateDate[0].toString());
                    entryCard.setClickable(false);
                }

            }
        });

        entryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NavHostFragment.findNavController(JournalHome.this)
                       .navigate(R.id.action_journalHome_to_moodAndJournal);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}