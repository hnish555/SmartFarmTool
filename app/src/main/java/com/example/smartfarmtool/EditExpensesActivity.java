package com.example.smartfarmtool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditExpensesActivity extends AppCompatActivity {
    static TextView irr_cost, lab_cost, chem_cost, total_expense;
    FloatingActionButton add_irr_cost, add_lab_cost, add_chem_cost, add_other_cost;
    EditText irr_input, lab_input, chem_input;
    Button save_updates;
    static CropDetail current;
    static List<OtherCropCost> otherCosts;
    LinearLayout other_cost_list;
    int totalothercost = 0;
    CropRepo cropRepo;
    static ListView listView;
    private boolean update_irr_cost = false, update_lab_cost = false, update_chem_cost = false;
    private int tog = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenses);

        irr_cost = findViewById(R.id.irr_cost);
        lab_cost = findViewById(R.id.lab_cost);
        chem_cost = findViewById(R.id.chem_cost);

        total_expense = findViewById(R.id.total_expense);

        add_irr_cost = findViewById(R.id.add_irr_cost);
        add_lab_cost = findViewById(R.id.add_lab_cost);
        add_chem_cost = findViewById(R.id.add_chem_cost);
        add_other_cost = findViewById(R.id.add_other_cost);

        irr_input = findViewById(R.id.irr_input);
        lab_input = findViewById(R.id.lab_input);
        chem_input = findViewById(R.id.chem_input);

        other_cost_list = findViewById(R.id.other_cost_list);

        save_updates = findViewById(R.id.save_updates);

        irr_input.setVisibility(View.GONE);
        lab_input.setVisibility(View.GONE);
        chem_input.setVisibility(View.GONE);
        save_updates.setVisibility(View.GONE);

        listView = new ListView(EditExpensesActivity.this);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("id",-1);

        cropRepo = new CropRepo(this);
        GetAsyncTask task = new GetAsyncTask(this,cropRepo,id);
        task.execute();

        OtherCostRepo costRepo = new OtherCostRepo(EditExpensesActivity.this);
        GetCostsAsyncTask costsAsyncTask = new GetCostsAsyncTask(EditExpensesActivity.this,costRepo,id);
        costsAsyncTask.execute();

        add_irr_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!update_lab_cost && !update_chem_cost) {
                    if (tog == 0) {
                        irr_input.setVisibility(View.VISIBLE);
                        irr_input.requestFocus();
                        save_updates.setVisibility(View.VISIBLE);
                        update_irr_cost = true;
                        tog = 1;
                        add_irr_cost.animate().rotation(45f).setDuration(300);
                    } else if (tog == 1) {
                        irr_input.setText("");
                        irr_input.setVisibility(View.GONE);
                        update_irr_cost = false;
                        save_updates.setVisibility(View.GONE);
                        tog = 0;
                        add_irr_cost.animate().rotation(0f).setDuration(300);
                    }
                }
                else {
                    Toast.makeText(EditExpensesActivity.this, "Add one cost at a time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_lab_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!update_irr_cost && !update_chem_cost) {
                    if (tog == 0) {
                        lab_input.setVisibility(View.VISIBLE);
                        lab_input.requestFocus();
                        save_updates.setVisibility(View.VISIBLE);
                        update_lab_cost = true;
                        tog = 1;
                        add_lab_cost.animate().rotation(45f).setDuration(300);
                    } else if (tog == 1) {
                        lab_input.setText("");
                        lab_input.setVisibility(View.GONE);
                        update_lab_cost = false;
                        save_updates.setVisibility(View.GONE);
                        tog = 0;
                        add_lab_cost.animate().rotation(0f).setDuration(300);
                    }
                }
                else {
                    Toast.makeText(EditExpensesActivity.this, "Add one cost at a time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_chem_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!update_lab_cost && !update_irr_cost) {
                    if (tog == 0) {
                        chem_input.setVisibility(View.VISIBLE);
                        chem_input.requestFocus();
                        save_updates.setVisibility(View.VISIBLE);
                        update_chem_cost = true;
                        tog = 1;
                        add_chem_cost.animate().rotation(45f).setDuration(300);
                    } else if (tog == 1) {
                        chem_input.setText("");
                        chem_input.setVisibility(View.GONE);
                        update_chem_cost = false;
                        save_updates.setVisibility(View.GONE);
                        tog = 0;
                        add_chem_cost.animate().rotation(0f).setDuration(300);
                    }
                }
                else {
                    Toast.makeText(EditExpensesActivity.this, "Add one cost at a time", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add_other_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(EditExpensesActivity.this);
                dialog.setContentView(R.layout.ask_other_cost_dialog);
                dialog.show();
                final EditText ask_desc = dialog.findViewById(R.id.ask_desc);
                final EditText ask_cost = dialog.findViewById(R.id.ask_cost);
                Button confirm_other_cost = dialog.findViewById(R.id.confirm_other_cost);
                Button cancel_other_cost = dialog.findViewById(R.id.cancel_other_cost);

                confirm_other_cost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date today = new Date();
                        String date = dateFormat.format(today);
                        if(!ask_desc.getText().toString().equals("") && !ask_cost.getText().toString().equals("")){
                            OtherCropCost cropCost = new OtherCropCost(id,ask_desc.getText().toString(),Integer.parseInt(ask_cost.getText().toString()),date);
                            OtherCostRepo costRepo = new OtherCostRepo(EditExpensesActivity.this);
                            costRepo.insertCost(cropCost);
                            other_cost_list.removeAllViews();
                            GetCostsAsyncTask costsAsyncTask = new GetCostsAsyncTask(EditExpensesActivity.this,costRepo,id);
                            costsAsyncTask.execute();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(EditExpensesActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancel_other_cost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


            }
        });


        save_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update_irr_cost){
                    update_irr_cost = false;
                    if(!irr_input.getText().toString().equals("")){
                        if(!irr_input.getText().toString().equals("0")){
                            int newCost = current.getIrrigationCost() + Integer.parseInt(irr_input.getText().toString());
                            current.setIrrigationCost(newCost);
                            int total = current.getIrrigationCost() + current.getLaborCost() + current.getChemicalCost()+totalothercost;
                            current.setTotalCost(total);
                            cropRepo.updateCrop(current);
                            save_updates.setVisibility(View.GONE);
                            GetAsyncTask refetch = new GetAsyncTask(EditExpensesActivity.this,cropRepo,id);
                            refetch.execute();
                            irr_input.setText("");
                            irr_input.setVisibility(View.GONE);
                            add_irr_cost.animate().rotation(0f).setDuration(300);
                            tog = 0;
                        }
                        else {
                            irr_input.setError("Invalid amount");
                        }
                    }
                    else {
                        Toast.makeText(EditExpensesActivity.this, "Enter some amount", Toast.LENGTH_SHORT).show();
                    }
                }

                else if(update_lab_cost){
                    update_lab_cost = false;
                    if(!lab_input.getText().toString().equals("")){
                        if(!lab_input.getText().toString().equals("0")){
                            int newCost = current.getLaborCost() + Integer.parseInt(lab_input.getText().toString());
                            current.setLaborCost(newCost);
                            int total = current.getIrrigationCost() + current.getLaborCost() + current.getChemicalCost()+totalothercost;
                            current.setTotalCost(total);
                            cropRepo.updateCrop(current);
                            save_updates.setVisibility(View.GONE);
                            GetAsyncTask refetch = new GetAsyncTask(EditExpensesActivity.this,cropRepo,id);
                            refetch.execute();
                            lab_input.setText("");
                            lab_input.setVisibility(View.GONE);
                            add_lab_cost.animate().rotation(0f).setDuration(300);
                            tog = 0;
                        }
                        else {
                            lab_input.setError("Invalid amount");
                        }
                    }
                    else {
                        Toast.makeText(EditExpensesActivity.this, "Enter some amount", Toast.LENGTH_SHORT).show();
                    }
                }


                else if(update_chem_cost){
                    update_chem_cost = false;
                    if(!chem_input.getText().toString().equals("")){
                        if(!chem_input.getText().toString().equals("0")){
                            int newCost = current.getChemicalCost() + Integer.parseInt(chem_input.getText().toString());
                            current.setChemicalCost(newCost);
                            int total = current.getIrrigationCost() + current.getLaborCost() + current.getChemicalCost()+totalothercost;
                            current.setTotalCost(total);
                            cropRepo.updateCrop(current);
                            save_updates.setVisibility(View.GONE);
                            GetAsyncTask refetch = new GetAsyncTask(EditExpensesActivity.this,cropRepo,id);
                            refetch.execute();
                            chem_input.setText("");
                            chem_input.setVisibility(View.GONE);
                            add_chem_cost.animate().rotation(0f).setDuration(300);
                            tog = 0;
                        }
                        else {
                            chem_input.setError("Invalid amount");
                        }
                    }
                    else {
                        Toast.makeText(EditExpensesActivity.this, "Enter some amount", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




    }


    private static class GetAsyncTask extends AsyncTask<Void, Void, Void> {

        CropRepo repo;
        int id;
        Context mContext;
        CropDetail detail;

        GetAsyncTask(Context context, CropRepo croprepo, int id) {
            mContext = context.getApplicationContext();
            repo = croprepo;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            detail = repo.getCrop(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (detail != null) {
                current = detail;
                irr_cost.setText("₹ "+ String.valueOf(detail.getIrrigationCost()));
                lab_cost.setText("₹ "+ String.valueOf(detail.getLaborCost()));
                chem_cost.setText("₹ "+ String.valueOf(detail.getChemicalCost()));
                total_expense.setText("₹ "+ String.valueOf(detail.getTotalCost()));

            } else{
                irr_cost.setText("₹ 0");
                lab_cost.setText("₹ 0");
                chem_cost.setText("₹ 0");
                total_expense.setText("₹ 0");
            }
        }
    }

    private class GetCostsAsyncTask extends AsyncTask<Void, Void, Void> {

        OtherCostRepo repo;
        Context mContext;
        int cropId;

        GetCostsAsyncTask(Context context, OtherCostRepo costRepo,int id) {
            mContext = context.getApplicationContext();
            repo = costRepo;
            cropId = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            otherCosts = repo.fetchAllCosts(cropId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(otherCosts!=null){
                if(otherCosts.size()>0){

                    for (OtherCropCost item : otherCosts){
                        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                        View view = layoutInflater.inflate(R.layout.other_cost_view,other_cost_list, false);
                        TextView description = view.findViewById(R.id.description);
                        TextView crop_cost = view.findViewById(R.id.crop_cost);
                        TextView date_today = view.findViewById(R.id.date_today);
                        totalothercost += item.getCost();
                        description.setText(item.getDescription());
                        crop_cost.setText("₹ "+String.valueOf(item.getCost()));
                        date_today.setText(item.getDate());
                        other_cost_list.addView(view);
                    }
                    current.setTotalCost(current.getIrrigationCost()+current.getLaborCost()+current.getChemicalCost()+totalothercost);
                    cropRepo.updateCrop(current);
                    GetAsyncTask refetch = new GetAsyncTask(EditExpensesActivity.this,cropRepo,cropId);
                    refetch.execute();
                }
            }

        }

    }
}
