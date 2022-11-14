package id.campaign.moviesapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.database.*
import id.campaign.moviesapp.R
import id.campaign.moviesapp.databinding.FragmentLoginBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


// Mengatur tata letak, fungsionalitas dari fragment login
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLoginBinding
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
        binding =FragmentLoginBinding.inflate(inflater, container, false)
        dbref = FirebaseDatabase.getInstance().getReference("Users")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.facebookLogo.setOnClickListener{
            Toast.makeText(binding.root.context, "Just Logo for now", Toast.LENGTH_SHORT)
        }
        binding.googleLogo.setOnClickListener{
            Toast.makeText(binding.root.context, "Just Logo for now", Toast.LENGTH_SHORT)
        }
        binding.twitterLogo.setOnClickListener{
            Toast.makeText(binding.root.context, "Just Logo for now", Toast.LENGTH_SHORT)
        }
        binding.registerAccount.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.buttonLogin.setOnClickListener{
            checkUserData()
        }

    }

    private fun checkUserData() {
        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(binding.root.context, "Please fill all fields", Toast.LENGTH_LONG).show()
            if(username.isEmpty()){
                binding.editTextUsername.error = "Please enter username"
            }
            if(password.isEmpty()){
                binding.editTextPassword.error = "Please enter password"
            }
        }else{
            dbref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.hasChild(username)){
                        val getPasswordFirebase = snapshot.child(username).child("password").value
                        if(getPasswordFirebase!!.equals(password)){
                            Toast.makeText(binding.root.context, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(binding.root.context, MainActivity::class.java))
                            activity!!.finish()
                        } else{
                            Toast.makeText(binding.root.context, "Wrong Password", Toast.LENGTH_SHORT).show()
                        }
                    } else{
                        Toast.makeText(binding.root.context, "Wrong Username", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
    }
}