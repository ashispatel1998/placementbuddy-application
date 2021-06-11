package com.example.prism.dev.ashis.patel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var phoneNumber : EditText
    private lateinit var otpNumber : PinView
    private lateinit var textViewOtp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        supportActionBar?.hide()

        auth = Firebase.auth

        val buttonLogin : Button = findViewById(R.id.btn_Login)
        val btn_verify : Button= findViewById(R.id.btn_verify)
        val btn_Resend : Button = findViewById(R.id.btn_resend_otp)
        textViewOtp= findViewById(R.id.textViewOtp)
        otpNumber = findViewById(R.id.otpText)

        btn_verify.visibility=View.GONE
        btn_Resend.visibility=View.GONE
        otpNumber.visibility=View.GONE
        textViewOtp.visibility=View.GONE

        phoneNumber = findViewById(R.id.editTextPhone);

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                val code: String = credential.getSmsCode().toString()
                if (code != null) {
                    otpNumber.setText(code)
                    verifyPhoneNumberWithCode(storedVerificationId,code)
                }
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent:$verificationId")
                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token
            }
        }

        buttonLogin.setOnClickListener(View.OnClickListener {

            buttonLogin.visibility = View.GONE
            btn_verify.visibility = View.VISIBLE
            btn_Resend.visibility = View.VISIBLE
            otpNumber.visibility = View.VISIBLE
            textViewOtp.visibility = View.VISIBLE

            var phone = phoneNumber.text.toString().trim()
            if (phone.isEmpty()) {
                phoneNumber.requestFocus()
                phoneNumber.error = "Required"
            } else if (phone.length != 10) {
                phoneNumber.requestFocus()
                phoneNumber.error = "Invalid Format"
            }
            phone = "+91${phone}"
            startPhoneNumberVerification(phone)

        })

        btn_Resend.setOnClickListener(){
            var phone = phoneNumber.text.toString().trim();
            resendVerificationCode("+91${phone}", resendToken)
        }
    }

    private fun startPhoneNumberVerification(phoneNo: String){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNo)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        Toast.makeText(applicationContext,"Verification Completed!",Toast.LENGTH_SHORT).show()
        signInWithPhoneAuthCredential(credential)
    }

    private fun resendVerificationCode(
            phoneNumber: String,
            token: PhoneAuthProvider.ForceResendingToken?) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
        if (token != null) {
            optionsBuilder.setForceResendingToken(token)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                //   Log.d(TAG, "signInWithCredential:success")
                //   var intent : Intent = Intent(applicationContext,UserRegistration::class.java)
                //   intent.putExtra("mobileno","+91"+phoneNumber.text.toString().trim())
                //   startActivity(intent)
                //   finish()
                //   val user = task.result?.user
                //   Toast.makeText(applicationContext, "${user}", Toast.LENGTH_SHORT).show()

                      updateUI(auth.currentUser)
                }
                else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(applicationContext, "Invalid Code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun updateUI(user: FirebaseUser? = auth.currentUser) {
        if(user!=null){
            var intent : Intent = Intent(applicationContext,HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }

}
