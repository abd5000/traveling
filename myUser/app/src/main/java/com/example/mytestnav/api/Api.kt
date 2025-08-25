package com.example.mytestnav.api


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
import com.example.mytestnav.trips.filter.FilterCompany
import com.example.mytestnav.trips.filter.typeTrips.RequstTypeTrips
import com.example.mytestnav.trips.filter.typeTrips.TypeTrips
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @POST("logincus")
    suspend fun login(@Body user:LoginInfo):Response<ResoultLogin>
    @POST("createcus")
    suspend fun signup(@Body user:Signupinfo):Response<SignupResult>
    //لا تغير شي ما اشتغل الا لطالع روحي
    @POST("verifycodecustumer/{id}")
    suspend fun verifyCode(@Path("id") userid:Int, @Body ver: Code): Response<ConfirmResult>
    @POST("coderesetpassword")
    suspend fun sendEmail(@Body email: Email):Response<ForgetEmailResult>
    @POST("verifycodepassword/{id}")
    suspend fun verifyPassword(@Path("id") userid:Int ,@Body code: CodeForgetPassword):Response<ConfirmPasswordResylt>
    @POST("resetpassword/{id}")
    suspend fun newPassword(@Path("id") userid:Int,@Body password:PasswordRequst):Response<NewPasswordResult>
    @POST("refreshtoken")
    suspend fun refreshToken(@Body refeshToken:InfoRefreshToken):Response<RefreshTokenResponse>
    //
    @GET("getcities")
    suspend fun cities():Response<ResponCity>
    @GET("getcompanies")
    suspend fun getCompanies(@Header("Authorization") token: String):Response<CompanysResponse>
    @GET("gettypebus")
    suspend fun typeTrips():Response<TypeTrips>
    @POST("search")
    suspend fun getTrips(@Header("Authorization") token: String,@Body info:SearchRequst):Response<SearchResponse>
    @POST("searchbycompany")
    suspend fun company(@Header("Authorization") token: String,@Body filter:FilterCompany):Response<SearchResponse>
    @POST("searchbytypebus")
    suspend fun filterTypeTrips(@Header("Authorization") token: String,@Body filter:RequstTypeTrips):Response<SearchResponse>
    @POST("reservation/{id}")
    suspend fun reserve(@Header("Authorization") token: String,@Path("id") tripId:Int,@Body seats:ReserveRequst):Response<ReserveResponse>
    //
    @GET("getreserveispaid")
    suspend fun paid(@Header("Authorization") token: String):Response<PaidResponse>
    @GET("getreserveisnotpaid")
    suspend fun notPaid(@Header("Authorization") token: String):Response<PaidResponse>
    @POST("canclereserve/{id}")
    suspend fun cancelReserve(@Header("Authorization") token: String,@Path("id") tripId:Int):Response<CancelResarevResponse>
    @POST("rating/{id}")
    suspend fun rating(@Header("Authorization") token: String,@Path("id") tripId:Int,@Body rate: Rating):Response<RatingResponse>

}