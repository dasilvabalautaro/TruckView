package com.pingpongpacket.truckview.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pingpongpacket.truckview.App
import com.pingpongpacket.truckview.R
import com.pingpongpacket.truckview.dagger.AuthModule
import com.pingpongpacket.truckview.models.UserRegisterFirebase
import com.pingpongpacket.truckview.tools.InputCheck
import com.pingpongpacket.truckview.tools.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import javax.inject.Inject

abstract class AuthFragment: Fragment() {
    protected var disposable: CompositeDisposable = CompositeDisposable()
    val USER_REGISTER = "User registered"


    val Fragment.app: App
        get() = activity.application as App

    val component by lazy { app.component.plus(AuthModule(context)) }


    interface Callback{
        fun remove(fragment: AuthFragment)
    }

    @Inject
    lateinit var inputCheck: InputCheck
    @Inject
    lateinit var preferences: Preferences
    @Inject
    lateinit var userRegisterFirebase: UserRegisterFirebase

    var callback: Callback? = null


    @BindView(R.id.tv_controller)
    @JvmField var controller: TextView? = null

    @BindView(R.id.ib_facebook)
    @JvmField var first: View? = null

    @BindView(R.id.ib_google)
    @JvmField var second: View? = null

    @BindView(R.id.ib_twitter)
    @JvmField var last: View? = null

    @BindView(R.id.iv_logo)
    @JvmField var logo: View? = null

    @OnClick(R.id.tv_controller)
    fun makeTransition(){
        if (callback != null){
            callback!!.remove(this)
        }
    }

    @SuppressLint("SupportAnnotationUsage")
    @LayoutRes
    abstract fun fireAnimation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(activity)
        KeyboardVisibilityEvent.setEventListener(activity) { isOpen ->
            val scale:Float = if (isOpen) 0.75f else 1f
            ViewCompat.animate(logo)
                    .scaleX(scale)
                    .scaleY(scale)
                    .setDuration(300)
                    .start()

        }

    }

    override fun onStart() {
        super.onStart()
        val message = this.userRegisterFirebase.observableMessage.map { s -> s }
        disposable.add(message.observeOn(Schedulers.newThread())
                .map { s ->
                    if (s == USER_REGISTER){
                        preferences.username =
                                userRegisterFirebase
                                        .currentUser!!.displayName.toString()
                    }
                    s
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s -> context.toast(s) })
    }

    fun mergeColoredText(leftPart: String,
                         rightPart: String,
                         leftColor: Int, rightColor: Int):
            SpannableStringBuilder{
        val builder: SpannableStringBuilder = SpannableStringBuilder()
        val leftPartSpannable: SpannableString =
                SpannableString(leftPart.toUpperCase())
        val rightPartSpannable : SpannableString =
                SpannableString(rightPart.toUpperCase())
        leftPartSpannable.setSpan(ForegroundColorSpan(leftColor),
                0, leftPart.length, 0)
        rightPartSpannable.setSpan(ForegroundColorSpan(rightColor),
                0, rightPart.length, 0)
        return builder.append(leftPartSpannable).append("   ")
                .append(rightPartSpannable)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        component.inject(this)
    }

    fun Context.toast(message: CharSequence,
                      duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()

    }
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}