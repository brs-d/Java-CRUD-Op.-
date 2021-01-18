package com.barisdeneme.contactbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private ArrayList<Model> arrayList;

    DatabaseHelper databaseHelper;

    public Adapter(Context context, ArrayList<Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Model model = arrayList.get(position);

        //görünümler alınıyor
        String id = model.getId();
        String name = model.getName();
        String surname = model.getSurname();
        String birthday = model.getBirthday();
        String email = model.getEmail();
        String phone = model.getPhone();
        String note = model.getNote();

        //görünümler ayarlanıyor
        holder.personName.setText(name);
        holder.personSurname.setText(surname);
        holder.personBirthday.setText("Doğum Tarihi: " + birthday);
        holder.personEmail.setText("Eposta: " + email);
        holder.personPhone.setText("Telefon Numarası: " + phone);
        holder.personNote.setText("Not: " + note);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPerson(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+surname,
                        ""+birthday,
                        ""+email,
                        ""+phone,
                        ""+note
                );
            }
        });
        
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    deletePerson(
                            "" + id
                    );
            }
        });


    }

    private void deletePerson(final String id) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Kişi Silme");
            builder.setMessage("Silmek istediğinize emin misiniz?");
            builder.setCancelable(false);
            builder.setIcon(R.drawable.ic_action_delete);

            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseHelper.deletePerson(id);
                    ((KisiListesi) context).onResume();
                    Toast.makeText(context, "Başarılı", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.create().show();
    }

    public void editPerson(String position, String id, String name, String surname, String birthday, String email, String phone, String note){
        Intent intent = new Intent(context,KisiEkle.class);
        intent.putExtra("ID",id);
        intent.putExtra("NAME",name);
        intent.putExtra("SURNAME",surname);
        intent.putExtra("BİRTHDAY",birthday);
        intent.putExtra("EMAIL",email);
        intent.putExtra("PHONE",phone);
        intent.putExtra("NOTE",note);
        intent.putExtra("editMode",true);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView personName,personSurname,personBirthday,personEmail,personPhone,personNote;
        ImageButton editButton,deleteButton;

        public Holder(@NonNull View itemView) {
            super(itemView);

            personName = itemView.findViewById(R.id.personName);
            personSurname = itemView.findViewById(R.id.personSurname);
            personBirthday = itemView.findViewById(R.id.personBirthday);
            personEmail = itemView.findViewById(R.id.personEmail);
            personPhone = itemView.findViewById(R.id.personPhone);
            personNote = itemView.findViewById(R.id.personNote);
            editButton = itemView.findViewById(R.id.editBtn);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
