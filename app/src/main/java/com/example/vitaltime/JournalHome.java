package com.example.vitaltime;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.*;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.FragmentJournalHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class JournalHome extends Fragment
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    private FragmentJournalHomeBinding binding;
    DiaryBook diaryBook = new DiaryBook();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = com.example.vitaltime.databinding.FragmentJournalHomeBinding.inflate(inflater, container, false);
        bottomNavigationView = binding.bottomNavigationView;
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView calView = binding.calendarView;
        final Date[] dateDate = {new Date(calView.getDate())};
        CardView entryCard = binding.CardView;

        bottomNavigationView.setSelectedItemId(R.id.diary);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        ///Adding to the Diary
        DiaryEntry dayOne = new DiaryEntry(new Date(1719638573715L), "Day One Testing", "happy", "This is a test");
        DiaryEntry dayTwo = new DiaryEntry(new Date(1719033962142L), "Day Two Testing", "sad", "This is a test");
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

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.tasks) {
            NavHostFragment.findNavController(JournalHome.this).navigate(R.id.toDoFragment);
            return true;
        } else if (menuItem.getItemId() == R.id.home) {
            NavHostFragment.findNavController(JournalHome.this).navigate(R.id.homeFragment);
            return true;
        } else
            return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            NavHostFragment.findNavController(JournalHome.this).navigate(R.id.loginFragment);
            return true;
        } else if (item.getItemId() == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}