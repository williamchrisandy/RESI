package com.resi.remindassistant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

public class ContactFragment extends Fragment implements ContactAdapter.ClickListener
{
    private RecyclerView recyclerViewContact;
    private ContactAdapter contactAdapter;

    public ContactFragment()
    {
        // Required empty public constructor
    }

    public static ContactFragment newInstance()
    {
        return new ContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerViewContact = rootView.findViewById(R.id.recycler_view_contact);

        contactAdapter = new ContactAdapter(getActivity(), this, true, false);
        recyclerViewContact.setAdapter(contactAdapter);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadContactData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.add_item) intentToContactActivity(-1);

        return super.onOptionsItemSelected(item);
    }

    private void loadContactData()
    {
        ContactDatabase contactDatabase = new ContactDatabase(getActivity());
        Vector<Contact> contactVector = contactDatabase.getContact();
        contactAdapter.setContactVector(contactVector);
    }

    private void intentToContactActivity(int contactId)
    {
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        intent.putExtra(ContactActivity.KEY_CONTACT_ID, contactId);
        startActivity(intent);
    }

    @Override
    public void onContactItemClick(int contactId)
    {
        intentToContactActivity(contactId);
    }

    @Override
    public void onContactItemEditedClick(int contactId)
    {
    }

    @Override
    public void onContactItemDeletedClick(int contactId)
    {
        ContactDatabase contactDatabase = new ContactDatabase(getActivity());
        contactDatabase.deleteContact(contactId);
    }
}