package com.example.vitaltime;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.*;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;
import com.example.vitaltime.databinding.FragmentJournalHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.firebase.database.DatabaseReference;
import java.util.Map;

import android.graphics.Color;
import android.util.Log;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import org.jetbrains.annotations.NotNull;



public class JournalHome extends BaseFragment
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    private DatabaseReference rootDataBase;
    private FragmentJournalHomeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        binding = com.example.vitaltime.databinding.FragmentJournalHomeBinding.inflate(inflater, container, false);
        bottomNavigationView = binding.bottomNavigationView;
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Adding a new saved DiaryEntry
        if (getArguments() != null) {
            DiaryEntry receivedEntry = getArguments().getParcelable("newEntry");
            ((ApplicationData) requireActivity().getApplication()).addDiaryEntry(receivedEntry);
            DiaryBook diaryBook = ((ApplicationData) requireActivity().getApplication()).getDiaryBook();
            rootDataBase = FirebaseDatabase.getInstance().getReference().child("Journal");
            if (diaryBook != null) {
                Map<String, Object> diaryBookMap = diaryBook.toMap();
                rootDataBase.child("DiaryBook").setValue(diaryBookMap); }
        }

        DiaryBook diaryBook = ((ApplicationData) requireActivity().getApplication()).getDiaryBook();

        CalendarView calView = binding.calendarView;
        final Date[] dateDate = {new Date()};
        final DiaryEntry[] selectedEntry = {null};
        CardView entryCard = binding.CardView;

        bottomNavigationView.setSelectedItemId(R.id.diary);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        selectedEntry[0] = diaryBook.getEntryKey(dateDate[0]);
        setEntryCard(entryCard, selectedEntry[0], dateDate[0]);

        //Creates a new DiaryEntry or opens an already existing Entry
        binding.buttonNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date todayDate = new Date();
                if (dateDate[0].before(todayDate)) { //if the date is before the current date
                    if (diaryBook.getEntryKey(dateDate[0]) == null) {
                        DiaryEntry newEntry = new DiaryEntry(dateDate[0], "", null, "");
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("selectedEntry", newEntry);
                        NavHostFragment.findNavController(JournalHome.this)
                                .navigate(R.id.action_journalHome_to_moodAndJournal, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("selectedEntry", selectedEntry[0]);
                        NavHostFragment.findNavController(JournalHome.this)
                                .navigate(R.id.action_journalHome_to_moodAndJournal, bundle);
                    }
                }
            }
        });

        //Selects the date on the calendar
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                dateDate[0] = new Date(selectedDate.getTimeInMillis());
                selectedEntry[0] = diaryBook.getEntryKey(dateDate[0]);

                setEntryCard(entryCard, selectedEntry[0], dateDate[0]);

            }
        });

        //Opens the DiaryEntry for that date
        entryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedEntry[0] != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("selectedEntry", selectedEntry[0]);
                    NavHostFragment.findNavController(JournalHome.this)
                            .navigate(R.id.action_journalHome_to_moodAndJournal, bundle);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setEntryCard(CardView entryCard, DiaryEntry selectedEntry, Date dateDate) {

        TextView titleText = binding.titleView;
        TextView dateText = binding.dateView;

        if (selectedEntry != null){
            titleText.setText(selectedEntry.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
            String formattedDate = sdf.format(selectedEntry.getDate());
            dateText.setText(formattedDate);
            entryCard.setClickable(true);
        }
        else {
            titleText.setText("No entry found");
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");
            String formattedDate = sdf.format(dateDate);
            dateText.setText(formattedDate);
            entryCard.setClickable(false);
        }
    }

}