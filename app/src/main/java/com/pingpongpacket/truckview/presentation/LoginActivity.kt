package com.pingpongpacket.truckview.presentation

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.models.UserRegisterFirebase
import com.pingpongpacket.truckview.presentation.views.AuthAdapter

class LoginActivity: AppCompatActivity(),
        GoogleApiClient.OnConnectionFailedListener {

    private val RC_SIGN_IN = 30

    private var userRegisterFirebaseActivity: UserRegisterFirebase? = null
    private var googleSignInOptions: GoogleSignInOptions? = null
    private var googleApiClient: GoogleApiClient? = null
    private var loginFacebook: LoginButton? = null
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()
        loginFacebook = ButterKnife.findById(this,R.id.login_button)
        loginFacebook!!.setReadPermissions("email", "public_profile")
        setCallBackManagerFacebook()
        val pager: ViewPager = ButterKnife.findById(this, R.id.pager_login)
        pager.adapter = AuthAdapter(supportFragmentManager, pager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val googleSignInResult: GoogleSignInResult = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data)
            if (googleSignInResult.isSuccess){
                val googleSignInAccount: GoogleSignInAccount =
                        googleSignInResult.signInAccount!!
                firebaseAuthWithGoogle(googleSignInAccount)
            }else{

            }
        }else{
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun configGoogle(){
        googleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* AppCompatActivity */,
                        this /* OnConnectionFailedListener */)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions!!)
                .build()
    }

    fun signInFacebook(userRegisterFirebase: UserRegisterFirebase){
        this.userRegisterFirebaseActivity = userRegisterFirebase
//        loginFacebook!!.callOnClick()
        loginFacebook!!.performClick()
    }
    fun signIn(userRegisterFirebase: UserRegisterFirebase) {
        this.userRegisterFirebaseActivity = userRegisterFirebase
        configGoogle()
        val signInIntent: Intent = Auth.GoogleSignInApi
                .getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    private fun firebaseAuthWithGoogle(googleSignInAccount:
                                       GoogleSignInAccount){
        val authCredential: AuthCredential = GoogleAuthProvider
                .getCredential(googleSignInAccount.idToken, null)
        userRegisterFirebaseActivity!!.signInWithCredential(authCredential)


    }

    private fun handleFacebookAccessToken(token: AccessToken){
        val authCredential: AuthCredential = FacebookAuthProvider
                .getCredential(token.token)
        userRegisterFirebaseActivity!!.signInWithCredential(authCredential)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        if (p0.errorMessage != null){

        }
    }

    private fun setCallBackManagerFacebook(){
        val facebookCallback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException) {

            }
        }

        loginFacebook!!.registerCallback(callbackManager, facebookCallback)
    }
}