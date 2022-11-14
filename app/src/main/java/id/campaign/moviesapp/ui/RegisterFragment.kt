package id.campaign.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.FragmentLoginBinding
import id.campaign.moviesapp.databinding.FragmentRegisterBinding
import id.campaign.moviesapp.model.UserModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


// Mengatur tata letak, fungsionalitas dari fragment register
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var dbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        dbref = FirebaseDatabase.getInstance().getReference("Users")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.loginAccount.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.buttonRegister.setOnClickListener {
            saveUserData()
        }
    }

    private fun saveUserData() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()

        if(name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()){
            Toast.makeText(binding.root.context, "Please fill all fields", Toast.LENGTH_LONG).show()
            if(name.isEmpty()){
                binding.editTextName.error = "Please enter name"
            }
            if(email.isEmpty()){
                binding.editTextEmail.error = "Please enter email"
            }
            if(username.isEmpty()){
                binding.editTextUsername.error = "Please enter username"
            }
            if(password.isEmpty()){
                binding.editTextPassword.error = "Please enter password"
            }
        }else{
            val user_Id = dbref.push().key!!
            val user = UserModel(user_Id, name, email, username, password)
            dbref.child(username).setValue(user)
                .addOnCompleteListener{
                    Toast.makeText(binding.root.context, "Register Success", Toast.LENGTH_LONG).show()
                    binding.editTextName.text.clear()
                    binding.editTextEmail.text.clear()
                    binding.editTextUsername.text.clear()
                    binding.editTextPassword.text.clear()
                }.addOnFailureListener { err ->
                    Toast.makeText(binding.root.context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}