package com.example.moviedbjm.ui.contacts

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moviedbjm.R
import com.example.moviedbjm.databinding.ContactsFragmentBinding
import com.example.moviedbjm.domain.Contact
import com.example.moviedbjm.viewBinding

class ContactsFragment : Fragment(R.layout.contacts_fragment) {

    private val viewBinding: ContactsFragmentBinding by viewBinding(
        ContactsFragmentBinding::bind
    )

    //private val adapter = ContactsAdapter()
    private val adapter = ContactsCursorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentResolver = requireContext().contentResolver
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " DESC"
        )

        val safeCursor = cursor ?: return

        adapter.setCursor(safeCursor)

        /*val contacts = mutableListOf<Contact>()

        while (safeCursor.moveToNext()) {
            val displayName =
                safeCursor.getString(safeCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

            contacts.add(Contact(displayName))
        }

        safeCursor.close()

        adapter.submitList(contacts)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.contactsList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()

        adapter.close()
    }
}