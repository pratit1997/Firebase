package com.codewithpratit.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Person;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {
 Button btn_add,btn_update,btn_delete,btn_find,btn_findall;
 EditText et_id,et_name,et_age;
 DatabaseReference persondatabase,personchild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initilize();
    }
    public void Initilize(){
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_update=(Button)findViewById(R.id.btn_update);
        btn_delete=(Button)findViewById(R.id.btn_delete);
        btn_find=(Button)findViewById(R.id.btn_find);
        btn_findall=(Button)findViewById(R.id.btn_findall);
        et_id=(EditText)findViewById(R.id.et_id);
        et_name=(EditText)findViewById(R.id.et_name);
        et_age=(EditText)findViewById(R.id.et_age);
        //btn on click
        btn_add.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btn_findall.setOnClickListener(this);

        persondatabase= FirebaseDatabase.getInstance().getReference("person");

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_find:
                find();
                break;
            case R.id.btn_findall:
                //findAll();
                break;


        }

    }
    void add(){
        try {
            String name = et_name.getText().toString();
            int id = Integer.valueOf(et_id.getText().toString());
            int age = Integer.valueOf(et_age.getText().toString());
            Person person = new Person(id, name, age);
            persondatabase.child(et_id.getText().toString()).setValue(person);
            Toast.makeText(this, "the value" + id + "is Added to the database", Toast.LENGTH_LONG).show();
            et_name.setText(null);
            et_age.setText(null);
            et_id.setText(null);
            et_id.requestFocus();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    void update(){
        try {
            String name = et_name.getText().toString();
            int id = Integer.valueOf(et_id.getText().toString());
            int age = Integer.valueOf(et_age.getText().toString());
            Person person = new Person(id, name, age);
            persondatabase.child(et_id.getText().toString()).setValue(person);
            Toast.makeText(this, "the value" + id + "is updated to the database", Toast.LENGTH_LONG).show();
            et_name.setText(null);
            et_age.setText(null);
            et_id.setText(null);
            et_id.requestFocus();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    void delete(){
        try {
            String name = et_name.getText().toString();
            String id = et_id.getText().toString();
            personchild=FirebaseDatabase.getInstance().getReference().child("person").child(id);
            personchild.removeValue();
            Toast.makeText(this, "the value" + id + "is Deleted From database", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    void find(){
        try {
            String id = et_id.getText().toString();
            personchild=FirebaseDatabase.getInstance().getReference().child("person").child(id);
            personchild.addValueEventListener(this);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            String name=snapshot.child("name").getValue().toString();
            String age=snapshot.child("age").getValue().toString();
            String id=snapshot.child("id").getValue().toString();
            String send="ID: "+id+" name: "+name+" age: "+age;
            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
            intent.putExtra("send",send);
            startActivity(intent);

        }else{
            Toast.makeText(this,"The data do not exist",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}