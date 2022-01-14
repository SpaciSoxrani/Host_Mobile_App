package com.example.hostapp.preSale;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hostapp.BackPressedForFragments;
import com.example.hostapp.R;
import com.example.hostapp.mainMenu.MainActivity;
import com.example.hostapp.mainMenu.MainMenuFragment;
import com.example.hostapp.mainMenu.MainMenuViewModel;
import com.example.hostapp.mainMenu.MenuItem;
import com.example.hostapp.serverapi.DemoServerApi;
import com.example.hostapp.utils.UiUtils;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class PreSaleFragment extends Fragment {
    Fragment mainMenuFragment;
    private PreSaleViewModel preSaleViewModel;
    private Context context;
    Fragment preSaleFragment;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pre_sale_fragment, container, false);
        final TableLayout tableContainer = root.findViewById(R.id.tableContainer);
        MaterialButton newMailingButton = root.findViewById(R.id.newMailing);
        preSaleFragment = new PreSaleFragment();
        preSaleViewModel = new ViewModelProvider(this).get(PreSaleViewModel.class);
        context = root.getContext();
        UiUtils utils = new UiUtils();

        newMailingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.CreateNewMailingDialog("Новая рассылка", context, preSaleFragment);
            }
        });

        preSaleViewModel.getNewMailings().observe(getViewLifecycleOwner(), new Observer<List<NewMailing>>() {
            @Override
            public void onChanged(List<NewMailing> newMailings) {
                addTableRow(newMailings, tableContainer);
            }
        });

        preSaleViewModel.setNewMailings(DemoServerApi.NEW_MAILINGS);
        return root;
    }

    private void addTableRow(List<NewMailing> newMailings, TableLayout tableContainer) {
        tableContainer.removeAllViews();

        tableContainer.addView(createTableRow("Название", "Статус", "Департамент", R.drawable.ic_baseline_psychology_24));
        TableRow row = new TableRow(context);

        for (int i = 0; i < newMailings.size(); i++) {
            final NewMailing newMailing = newMailings.get(i);
            row = createTableRow(newMailing.name, newMailing.status, newMailing.depart, R.drawable.ic_baseline_edit_24);
            tableContainer.addView(row);
        }
    }

    private TableRow createTableRow(String name, String status, String depart, int icon1){
        TableRow tr1 = new TableRow(context);
        TextView twName = new TextView(context);
        TextView twStatus = new TextView(context);
        TextView twDepart = new TextView(context);
        ImageView icon = new ImageView(context);

        twName.setText(name);
        twName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.25f));
        tr1.addView(twName);

        twStatus.setText(status);
        twStatus.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.25f));
        tr1.addView(twStatus);

        twDepart.setText(depart);
        twDepart.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.25f));
        tr1.addView(twDepart);

        icon.setImageResource(icon1);
        icon.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 0.25f));
        tr1.addView(icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //new dialogue
            }
        });
        return  tr1;
    }

}
