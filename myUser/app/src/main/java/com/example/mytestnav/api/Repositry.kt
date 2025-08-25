package com.example.mytestnav.api


import android.util.Log
import com.example.mytestnav.home.cites.ResponCity
import com.example.mytestnav.home.refreshtoken.InfoRefreshToken
import com.example.mytestnav.home.refreshtoken.RefreshTokenResponse
import com.example.mytestnav.login.confirmtheemail.confirmsignup.Code
import com.example.mytestnav.login.confirmtheemail.confirmsignup.ConfirmResult
import com.example.mytestnav.login.forgetpassword.codeconfirm.CodeForgetPassword
import com.example.mytestnav.login.forgetpassword.codeconfirm.ConfirmPasswordResylt
import com.example.mytestnav.login.forgetpassword.newpass.NewPasswordResult
import com.example.mytestnav.login.forgetpassword.newpass.PasswordRequst
import com.example.mytestnav.login.forgetpassword.sendEmail.Email
import com.example.mytestnav.login.forgetpassword.sendEmail.ForgetEmailResult
import com.example.mytestnav.login.loginInformation.LoginInfo
import com.example.mytestnav.login.loginInformation.ResoultLogin
import com.example.mytestnav.login.signupinfomation.SignupResult
import com.example.mytestnav.login.signupinfomation.Signupinfo
import com.example.mytestnav.mybook.notpaid.details.CancelResarevResponse
import com.example.mytestnav.mybook.paid.PaidResponse
import com.example.mytestnav.mybook.paid.details.Rating
import com.example.mytestnav.mybook.paid.details.RatingResponse
import com.example.mytestnav.reserve.ReserveRequst
import com.example.mytestnav.reserve.ReserveResponse
import com.example.mytestnav.trips.SearchRequst
import com.example.mytestnav.trips.SearchResponse
import com.example.mytestnav.trips.companys.CompanysResponse
import com.example.mytestnav.trips.filter.typeTrips.RequstTypeTrips
import com.example.mytestnav.trips.filter.typeTrips.TypeTrips
import com.google.gson.Gson

class Repositry {
    suspend fun login(email: String, password: String): ResoultLogin? {
        val result=Instance.apiServes.login(LoginInfo(email, password))
        //
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ResoultLogin::class.java)
            errorResponse


        }


    }

    suspend fun signup(fullName:String,email: String?,password: String?, confirmpassword: String?,fcmToken:String?): SignupResult? {
        val result = Instance.apiServes.signup(Signupinfo(fullName,email, password, confirmpassword,fcmToken))
        Log.i("reSend", "signup:${email} ")
        return try {
            result.body()!!

        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), SignupResult::class.java)
            errorResponse
        }


    }


    suspend fun confirmEmail(id:Int,code: String): ConfirmResult?{
        val result= Instance.apiServes.verifyCode(id, Code(code))
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ConfirmResult::class.java)
            errorResponse
        }

    }


    suspend fun sendEmail(email: String): ForgetEmailResult? {

        val result= Instance.apiServes.sendEmail(Email(email))
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ForgetEmailResult::class.java)
            errorResponse
        }

    }

    suspend fun confirmEmailForget(id:Int,code: String): ConfirmPasswordResylt {

        val result= Instance.apiServes.verifyPassword(id,CodeForgetPassword(code))
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ConfirmPasswordResylt::class.java)
            errorResponse
        }
    }


    suspend fun newPassword(id:Int,password: String,confirmpassword: String):NewPasswordResult?{
        val result=    Instance.apiServes.newPassword(id, PasswordRequst(password,confirmpassword))
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), NewPasswordResult::class.java)
            errorResponse
        }
    }
    suspend fun refreshToken(refreshToken:String):RefreshTokenResponse?{
        val result=    Instance.apiServes.refreshToken(InfoRefreshToken(refreshToken))

        return try {
            result.body()!!
        } catch (error: Throwable) {
           val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), RefreshTokenResponse::class.java)
            Log.i("refreshtoken", "${errorResponse.message}", )
            errorResponse
        }
    }
    suspend fun cites():ResponCity?{
        val result=Instance.apiServes.cities()
        return try {
            result.body()
        }catch (error:Throwable){
             null
        }
    }
    suspend fun getCompanies(token:String):CompanysResponse?{
        val result=Instance.apiServes.getCompanies("Bearer $token")
        return try {
            result.body()
        }catch (error:Throwable){
             null
        }
    }
    suspend fun typeTrips():TypeTrips?{
        val result=Instance.apiServes.typeTrips()
        return try{
            result.body()
        }catch (error:Throwable){
             null
        }
    }
    suspend fun getTrips(idFrom:Int,idTo:Int,date:String,token:String):SearchResponse?{
        val result=Instance.apiServes.getTrips("Bearer $token",SearchRequst(date,idTo,idFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
             null
        }
    }
    suspend fun FilterCompany(idCompany:String,idCityFrom:Int,idCityTo:Int,date:String,token:String):SearchResponse?{
        val result=Instance.apiServes.company("Bearer $token",com.example.mytestnav.trips.filter.FilterCompany(idCompany,date,idCityTo,idCityFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), SearchResponse::class.java)
            errorResponse
        }
    }
    suspend fun FilterTypeTrips(idType:String,idCityFrom: Int,idCityTo: Int,date: String,token:String):SearchResponse?{
        val result=Instance.apiServes.filterTypeTrips("Bearer $token",RequstTypeTrips(date,idCityTo,idCityFrom,idType))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), SearchResponse::class.java)
            errorResponse
        }
    }
    suspend fun reserve(token:String,tripId:Int,steats:List<Int>):ReserveResponse?{
        val result=Instance.apiServes.reserve("Bearer $token",tripId, ReserveRequst(steats))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ReserveResponse::class.java)
            errorResponse
        }
    }
    suspend fun paid(token:String):PaidResponse?{
        val result=Instance.apiServes.paid("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), PaidResponse::class.java)
            errorResponse
        }
    }
    suspend fun notPaid(token:String):PaidResponse?{
        val result=Instance.apiServes.notPaid("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), PaidResponse::class.java)
            errorResponse
        }
    }
    suspend fun cancelReserve(token:String,tripId:Int):CancelResarevResponse?{
        val result=Instance.apiServes.cancelReserve("Bearer $token",tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), CancelResarevResponse::class.java)
            errorResponse
        }
    }
    suspend fun rating(token:String,tripId:Int,rate:Int): RatingResponse?{
        val result=Instance.apiServes.rating("Bearer $token",tripId, Rating(rate))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), RatingResponse::class.java)
            errorResponse
        }
    }
}