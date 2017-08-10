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

class LogInFragment: AuthFragment() {
    val TAG = LogInFragment::class.java.name!!

    @BindView(R.id.et_email_log_in)
    @JvmField var etEmail: TextInputEditText? = null
    @BindView(R.id.et_password_log_in)
    @JvmField var etPassword: TextInputEditText? = null
    @BindView(R.id.parent)
    @JvmField var parent: ViewGroup? = null
    @BindView(R.id.bt_send)
    @JvmField var btSend: Button? = null

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root: View = inflater!!.inflate(R.layout.log_in_fragment,
                container,false)
        ButterKnife.bind(this, root)

        controller!!.text = mergeColoredText(getString(R.string.or),
                getString(R.string.sign_up_or_label),
                ContextCompat.getColor(context, R.color.white_trans),
                ContextCompat.getColor(context, R.color.color_text))
        return root
    }

    override fun onResume() {
        super.onResume()

    }
    override fun onStart() {
        super.onStart()
        disposable.add( actionLoginButtonClickObservable()
                .observeOn(Schedulers.newThread())
                .map { validate ->
                    run{
                        if (validate){
                            /*return@map this.userRegisterFirebase
                                    .signIn(etEmail!!.text.toString(),
                                    etPassword!!.text.toString())*/
                            return@map ""
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
        val offsetX: Float = parent!!.width -
                (last!!.x + last!!.width) -
                resources.getDimension(R.dimen.option_size)
        val firstAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(first, View.TRANSLATION_X, 0f)
        val secondAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(second, View.TRANSLATION_X, 0f)
        val lastAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(last, View.TRANSLATION_X, 0f)
        val buttonAnimator: ObjectAnimator = ObjectAnimator
                .ofFloat(controller, View.TRANSLATION_X,
                        controller!!.translationX)
        first!!.translationX = offsetX
        second!!.translationX = offsetX
        last!!.translationX = offsetX
        controller!!.translationX = controller!!.width.toFloat()
        buttonAnimator.interpolator = BounceOvershootInterpolator(4f)
        val buttonSet: AnimatorSet = AnimatorSet()
        buttonSet.playTogether(firstAnimator, secondAnimator, lastAnimator)
        buttonSet.interpolator = BounceOvershootInterpolator(2f)
        val animatorSet: AnimatorSet = AnimatorSet()
        animatorSet.startDelay = 500
        animatorSet.playTogether(buttonSet, buttonAnimator)
        animatorSet.start()
    }
    private fun actionLoginButtonClickObservable(): Observable<Boolean> {
        return Observable.create({
            e: ObservableEmitter<Boolean>? ->
            btSend!!.setOnClickListener({
                e!!.onNext(inputCheck.validateInputLogin(etEmail!!.text.toString(),
                        etPassword!!.text.toString())) })
            e!!.setCancellable { Cancellable{
                btSend!!.setOnClickListener(null)
            } }
        })
    }
}