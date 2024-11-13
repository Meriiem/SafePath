package com.example.safepath

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addContactButton: Button
    private lateinit var emergencyAlertButton: Button
    private lateinit var contactsAdapter: ContactsAdapter
    private var contactsList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        // Initialize views
        recyclerView = findViewById(R.id.rv_contacts)
        addContactButton = findViewById(R.id.btn_add_contact)
        emergencyAlertButton = findViewById(R.id.btn_emergency_alert)

        // Initialize adapter
        contactsAdapter = ContactsAdapter(contactsList)

        // Set up RecyclerView with layout manager and adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactsAdapter

        // Set button listeners
        addContactButton.setOnClickListener {
            openAddContactActivity()
        }

        emergencyAlertButton.setOnClickListener {
            sendEmergencyAlert()
        }

        // Load contacts (for now, dummy data)
        loadContacts()
    }

    private fun loadContacts() {
        // Example dummy contacts
        contactsList.add(Contact("John Doe", "+1234567890"))
        contactsList.add(Contact("Jane Smith", "+0987654321"))
        contactsAdapter.notifyDataSetChanged()
    }

    private fun openAddContactActivity() {
        // Logic to open another activity for adding a contact
    }

    private fun sendEmergencyAlert() {
        // Send emergency alert to all contacts
        contactsList.forEach { contact ->
            // Logic to send alert (e.g., SMS, Firebase push notification)
            println("Alert sent to: ${contact.name}")
        }
    }
}

// Contact data class
data class Contact(val name: String, val phone: String)

// Adapter for displaying the contacts in RecyclerView
class ContactsAdapter(private val contacts: List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name
        holder.phoneTextView.text = contact.phone

        holder.shareLocationButton.setOnClickListener {
            // Logic for sharing location
        }

        holder.removeButton.setOnClickListener {
            // Logic for removing contact
        }
    }

    override fun getItemCount(): Int = contacts.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_contact_name)
        val phoneTextView: TextView = itemView.findViewById(R.id.tv_contact_phone)
        val shareLocationButton: Button = itemView.findViewById(R.id.btn_share_location)
        val removeButton: Button = itemView.findViewById(R.id.btn_remove_contact)
    }
}
