package com.pingpongpacket.truckview.models

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


class UserRegisterFirebase(context: Context): UserRegisterInterface  {
    private var auth: FirebaseAuth? = null
    private var authListener: FirebaseAuth.AuthStateListener? = null
    var currentUser: FirebaseUser? = null
    var messageResult: String? = String()
    var observableMessage: Subject<String> = PublishSubject.create()
    var caseResult: Observable<String> = observableMessage.map { s -> s }
    private val USER_NOT_REGISTER = "User not register"
    private val USER_REGISTER = "User registered"
    private val OPERATION_PROCESS = "Processing ..."
    private val PROFILE_UPDATED = "User profile updated"
    private val PROFILE_UPDATED_FAIL = "User profile updated fail"
    private val ACCESS_GRANTED = "Access granted"
    private val ACCESS_NOT_ALLOWED = "Access not allowed"
    private var nameUser: String = String()

    init {
        FirebaseApp.initializeApp(context)
        auth = FirebaseAuth.getInstance()


        observableMessage
                .subscribe { messageResult }
        caseResult
                .filter { s -> s == USER_REGISTER }
                .observeOn(Schedulers.newThread())
                .map { _ -> updateProfile(this.nameUser) }
                .subscribe{s -> println(s)}
        initAuthListener()

    }


    override fun isSignedIn(): Boolean {
        currentUser = auth?.currentUser
        return (currentUser != null)

    }

    override fun createAccount(email: String, password: String,
                               name: String): String {
        auth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener({
                    task: Task<AuthResult> ->
                    if (task.isSuccessful){
                        currentUser = auth!!.currentUser
                        this.messageResult = USER_REGISTER
                        this.nameUser = name
                    }else{
                        this.messageResult = USER_NOT_REGISTER
                    }
                    this.observableMessage.onNext(this.messageResult!!)
                })
        return OPERATION_PROCESS

    }

    override fun signIn(email: String, password: String): String {
        auth!!.addAuthStateListener(authListener!!)
        auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener({task: Task<AuthResult> ->
                    if (!task.isSuccessful){
                        this.messageResult = task.exception.toString()
                    }else{
                        this.messageResult = USER_REGISTER
                    }
                    this.observableMessage.onNext(this.messageResult!!)
                })

        return OPERATION_PROCESS

    }

    override fun signOut() {

    }

    override fun finish() {
        if (authListener != null){
            auth!!.removeAuthStateListener(authListener!!)
        }
    }

    private fun initAuthListener(){
        authListener = FirebaseAuth.AuthStateListener { firebaseAuth:
                                                        FirebaseAuth ->
            currentUser = firebaseAuth.currentUser
            if (currentUser != null){
                this.messageResult = ACCESS_GRANTED
            }else{
                this.messageResult = ACCESS_NOT_ALLOWED
            }
            this.observableMessage.onNext(this.messageResult!!)
        }
    }

    private fun updateProfile(name: String): String{
        val profileUpdates: UserProfileChangeRequest =
                UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
        currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener({ task: Task<Void> ->
                    if (task.isSuccessful){
                        this.messageResult = PROFILE_UPDATED
                    }else{
                        this.messageResult = PROFILE_UPDATED_FAIL
                    }
                    this.observableMessage.onNext(this.messageResult!!)

                })

        return  OPERATION_PROCESS
    }

    fun getUid(): String{
        return currentUser!!.uid
    }

}