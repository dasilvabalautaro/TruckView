package com.pingpongpacket.truckview.presentation.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.pingpongpacket.truckview.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Cancellable
import io.reactivex.schedulers.Schedulers


class SignUpFragment: AuthFragment() {
    val TAG = SignUpFragment::class.java.name!!

    @BindView(R.id.et_email_sign_up)
    @JvmField var etEmailSignUp: TextInputEditText? = null
    @BindView(R.id.et_password_sign_up)
    @JvmField var etPasswordSignUp: TextInputEditText? = null
    @BindView(R.id.et_user_name)
    @JvmField var etUserName: TextInputEditText? = null
    @BindView(R.id.parent_sign)
    @JvmField var parent_sign: ViewGroup? = null
    @BindView(R.id.bt_send_sign_up)
    @JvmField var btSend: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater!!.inflate(R.layout.sign_up_fragment,
                container,false)
        ButterKnife.bind(this, root)
        controller!!.text = mergeColoredText(getString(R.string.or),
                getString(R.string.log_in_or_label),
                ContextCompat.getColor(context, R.color.white_trans),
                ContextCompat.getColor(context, R.color.color_text))
        return root
    }

    override fun onStart() {
        super.onStart()
        disposable.add( actionSingUpButtonClickObservable()
                .observeOn(Schedulers.newThread())
                .map { validate ->
                    run{
                        if (validate){
                            return@map this.userRegisterFirebase
                                    .createAccount(etEmailSignUp!!.text.toString(),
                                            etPasswordSignUp!!.text.toString(),
                                            etUserName!!.text.toString())

                        }else{
                            val alert: String = resources
                                    .getString(R.string.error_input)
                            return@map alert
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> context.toast(result)})
    }

    override fun fireAnimation() {
        val offsetX: Float = parent_sign!!.width -
                (parent_sign!!.width - first!!.left + resources.getDimension(R.dimen.option_size))
        val firstAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(first, View.TRANSLATION_X, 0f)
        val secondAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(second, View.TRANSLATION_X, 0f)
        val lastAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(last, View.TRANSLATION_X, 0f)
        val buttonAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(controller, View.TRANSLATION_X,
                        controller!!.translationX)
        first!!.translationX = - offsetX
        second!!.translationX = - offsetX
        last!!.translationX = - offsetX
        controller!!.translationX = - controller!!.width.toFloat()
        buttonAnimator.interpolator = BounceOvershootInterpolator(4f)
        val buttonSet: AnimatorSet = AnimatorSet()
        buttonSet.playTogether(firstAnimator, secondAnimator, lastAnimator)
        buttonSet.interpolator = BounceOvershootInterpolator(2f)
        val animatorSet: AnimatorSet = AnimatorSet()
        animatorSet.startDelay = 500
        animatorSet.playTogether(buttonSet, buttonAnimator)
        animatorSet.start()
    }
    private fun actionSingUpButtonClickObservable(): Observable<Boolean> {
        return Observable.create({
            e: ObservableEmitter<Boolean>? ->
            btSend!!.setOnClickListener({
                e!!.onNext(inputCheck.validateInputLogin(
                        etEmailSignUp!!.text.toString(),
                        etPasswordSignUp!!.text.toString(),
                        etUserName!!.text.toString()))
            })
            e!!.setCancellable { Cancellable{
                btSend!!.setOnClickListener(null)
            } }
        })
    }
}