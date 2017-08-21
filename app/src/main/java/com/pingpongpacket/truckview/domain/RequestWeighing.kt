package com.pingpongpacket.truckview.domain

import com.google.gson.Gson
import com.pingpongpacket.truckview.models.AgentCarrier
import com.pingpongpacket.truckview.models.Weighing
import com.pingpongpacket.truckview.models.interfaces.IServiceExchangePost
import com.pingpongpacket.truckview.models.network.MessageOfService
import com.pingpongpacket.truckview.models.network.ServiceRemotePost
import com.pingpongpacket.truckview.models.persistent.CachingLru
import com.pingpongpacket.truckview.tools.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.SocketTimeoutException
import javax.inject.Inject


class RequestWeighing @Inject constructor(
        val serviceRemotePost: ServiceRemotePost) {
    private val TAG = RequestWeighing::class.java.name!!
    private val KEY_LIST = "listWeighing"
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var servicePost: IServiceExchangePost? = null
    private var listWeighing: ArrayList<Weighing>? = null
    var observableList: Subject<ArrayList<Weighing>> = PublishSubject.create()
    var agentCarrier: AgentCarrier? = null
    private var body: RequestBody? = null


    init {
        observableList
                .subscribe { listWeighing }

    }

    private fun setParams(): Boolean{
        agentCarrier = createAgent()
        if (agentCarrier != null){
            body = createRequestBody(agentCarrier!!)
            return true
        }
        return false
    }

    fun downloadWeighing(flag: Int){
        val dataCache = verifyDataCache()
        if (dataCache != null && flag == 0){
            getDataCache(dataCache)
        }else{
            getDataServer()
        }
    }

    private fun getDataServer(){
        if (setParams() && setServicePost()){
            try {
                disposable.add(servicePost!!.sendPost(agentCarrier!!.license,
                        agentCarrier!!.address + agentCarrier!!.service,
                        body!!)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            response -> getJsonArray(response)
                        })
                )

            }catch (se: SocketTimeoutException){
                println(se.message)
            }

        }
    }
    private fun getDataCache(stringJson: String){
        val jsonArray: JSONArray =  JSONArray(stringJson)
        getListWeighing(jsonArray)
    }
    private fun verifyDataCache(): String?{

        val dataCache = CachingLru.instance.getLru().get(KEY_LIST)
        if (dataCache != null){
            return dataCache as String
        }
        return dataCache
    }

    private fun getJsonArray(messageOfService: MessageOfService){
        if (messageOfService.success){
            val gson: Gson = Gson()
            val gsonResult = gson.toJson(messageOfService.result)

            if (!gsonResult.isEmpty()){
                val jsonArray: JSONArray =  JSONArray(gsonResult)
                CachingLru.instance.getLru().put(KEY_LIST, gsonResult)
                getListWeighing(jsonArray)
            }

        }
    }

    private fun getListWeighing(jsonArray: JSONArray){
        listWeighing = ArrayList()

        for (i in 0 until jsonArray.length()){
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val weigh: Weighing = Weighing(jsonObject.getString("VCCodigo")?: "",
                    jsonObject.getString("ControlFecha")?: "",
                    jsonObject.getString("PuestoControlCID")?: "",
                    jsonObject.getString("VCPlaca1")?: "",
                    jsonObject.getString("VCCondNombre")?: "",
                    jsonObject.getString("VCCondLicencia")?: "",
                    jsonObject.getString("ConfiguracionVehiculoCID")?: "",
                    jsonObject.getString("EmpleadoCodigo")?: "",
                    jsonObject.getInt("ControlPesoTotal")?: 0,
                    jsonObject.getString("provider")?: "",
                    jsonObject.getString("product")?: "",
                    jsonObject.getString("client")?: "")

            listWeighing!!.add(weigh)
        }
        this.observableList.onNext(this.listWeighing!!)
    }

    private fun setServicePost(): Boolean{
        try {
            servicePost = serviceRemotePost.getService()
            return true
        }catch (ie: IllegalArgumentException){
            println(ie.message)
        }
        return false
    }

    private fun createAgent(): AgentCarrier{

        val backPack: Map<String, Any> = hashMapOf("date" to 1, "code" to 2)

        val agent: AgentCarrier = AgentCarrier("weighing",
                Constants.ADDRESS_WEB, Constants.SERVICE_GET_DATA,
                "", backPack)
        return agent
    }

    private fun createRequestBody(agentCarrier: AgentCarrier): RequestBody?{
        try {
            val body: RequestBody = RequestBody.create(
                    okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (JSONObject(agentCarrier.backPack).toString())
            )
            return body

        }catch (je: JSONException){
            println(TAG + ": " + je.message)
        }
        return null
    }


}


