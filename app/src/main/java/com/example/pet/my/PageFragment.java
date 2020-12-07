package com.example.pet.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pet.R;
import com.example.pet.other.Cache;

public class PageFragment extends Fragment {

    private ImageView petPhoto;
    private EditText petName;
    private EditText petType;
    private EditText petAge;
    private EditText petAutograph;
private Button btnUpdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pet, container, false);
        Bundle bundle = getArguments();
        int index = bundle.getInt("index");
        //设置
        petPhoto = view.findViewById(R.id.pet_photo);
        petName = view.findViewById(R.id.pet_name);
        petType = view.findViewById(R.id.pet_type);
        petAge = view.findViewById(R.id.pet_age);
        petAutograph = view.findViewById(R.id.pet_autograph);
        btnUpdate = view.findViewById(R.id.pet_update);



        petAutograph.setText(Cache.myPetList.get(index).getPetAutograph(), TextView.BufferType.EDITABLE);
        petPhoto.setImageBitmap(Cache.myPetList.get(index).getPicture());
        petName.setText(Cache.myPetList.get(index).getPetName(), TextView.BufferType.EDITABLE);
        petType.setText(Cache.myPetList.get(index).getPetType(), TextView.BufferType.EDITABLE);
        petAge.setText(Cache.myPetList.get(index).getPetAge()+"",TextView.BufferType.EDITABLE);
        return view;
    }
}
