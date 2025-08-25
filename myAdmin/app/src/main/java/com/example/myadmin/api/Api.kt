package com.example.myadmin.api

import com.example.myadmin.home.addtrip.AddTripRequest
import com.example.myadmin.home.addtrip.duration.DurationRequset
import com.example.myadmin.home.addtrip.duration.DurationResponse
import com.example.myadmin.home.addtrip.buss.RequstGetBus
import com.example.myadmin.home.addtrip.buss.ResponseGetBus
import com.example.myadmin.home.addtrip.ResponCity
import com.example.myadmin.home.addtrip.ResponseAddTrip
import com.example.myadmin.home.edittrips.editeordelettrips.AvailableRequste
import com.example.myadmin.home.edittrips.editeordelettrips.deletetrip.DeleteResponse
import com.example.myadmin.home.edittrips.editeordelettrips.filterbybus.FilterBusRequste
import com.example.myadmin.home.edittrips.editeordelettrips.savechenge.RequestSaveChanges
import com.example.myadmin.home.edittrips.editeordelettrips.savechenge.SaveChengesResponse
import com.example.myadmin.home.showtrips.availabletrips.AllTripsAvailbleResponse
import com.example.myadmin.home.showtrips.availabletrips.flterbybusandcities.FilterByBusAndCitiesRequste
import com.example.myadmin.home.showtrips.availabletrips.flterbybusandcities.filteronlybus.FilterByBusOnlyRequste
import com.example.myadmin.home.showtrips.detailstrips.ConfermResponse
import com.example.myadmin.home.showtrips.detailstrips.CustomerTripAvailable
import com.example.myadmin.home.showtrips.detailstrips.filtretcustomers.FilterCustmerBySeatRequset
import com.example.myadmin.home.showtrips.finishedtrips.FilterFinishedRequste
import com.example.myadmin.home.showtrips.finishedtrips.FinishedfFilterResponse
import com.example.myadmin.home.showtrips.reserve.completereserve.ResponseOneTrip
import com.example.myadmin.home.showtrips.reserve.reservapi.ReserveRequste
import com.example.myadmin.home.showtrips.reserve.reservapi.ReserveResponse
import com.example.myadmin.login.LoginInfo
import com.example.myadmin.login.LoginResponse
import com.example.myadmin.usersandbuss.addDriver.AddDriverRequest
import com.example.myadmin.usersandbuss.addDriver.AddDriverResponse
import com.example.myadmin.usersandbuss.addbuss.AddBusRequest
import com.example.myadmin.usersandbuss.addbuss.AddBusResponse
import com.example.myadmin.usersandbuss.addbuss.availableDrivers.DraiversAvailableResponse
import com.example.myadmin.usersandbuss.addbuss.typBus.TypeBusResponse
import com.example.myadmin.usersandbuss.deleteDriver.AllDriversResponse
import com.example.myadmin.usersandbuss.deleteDriver.deleteapi.DeleteDrivereResponse
import com.example.myadmin.usersandbuss.deletebus.DeleteBusResponse
import com.example.myadmin.usersandbuss.deletebus.allbuss.AllBussResponse
import com.example.myadmin.usersandbuss.deletebus.editeBus.EditeBusResponse
import com.example.myadmin.usersandbuss.deletebus.editeBus.EditeBusRequest
import com.example.myadmin.usersandbuss.users.banned.BannedResponse
import com.example.myadmin.usersandbuss.users.banned.custoersBlocked.AllCustomersBlockedResponse
import com.example.myadmin.usersandbuss.users.toReview.AllUsersToReviewResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {
    @POST("login")
    suspend fun login(@Body admin: LoginInfo):Response<LoginResponse>
    @POST("getbusbyorg")
    suspend fun getBus(@Header("Authorization") token: String,@Body info: RequstGetBus):Response<ResponseGetBus>
    @POST("getduration")
    suspend fun getDuration(@Body info: DurationRequset):Response<DurationResponse>
    @GET("getcities")
    suspend fun cities():Response<ResponCity>
    @POST("addtrip")
    suspend fun addTrip(@Body info:AddTripRequest):Response<ResponseAddTrip>
    @POST("gettripisavailable")
    suspend fun getTripsAvailable(@Header("Authorization") token: String,@Body info:AvailableRequste):Response<AllTripsAvailbleResponse>
    @DELETE("deletetrip/{id}")
    suspend fun deleteTrip(@Path("id") tripId:Int):Response<DeleteResponse>
    @POST("filtertripbynumberbus")
    suspend fun filterBus(@Header("Authorization") token: String,@Body info:FilterBusRequste):Response<AllTripsAvailbleResponse>
    @PUT("updatetrip/{id}")
    suspend fun updateTrip(@Path("id") tripId:Int,@Body info:RequestSaveChanges):Response<SaveChengesResponse>
    @GET("getcustumers/{id}")
    suspend fun getCustomerTrip(@Header("Authorization") token: String,@Path("id") tripId:Int):Response<CustomerTripAvailable>
    @GET("getcustumersipaid/{id}")
    suspend fun getCustomerPaid(@Path("id") tripId:Int):Response<CustomerTripAvailable>
    @GET("gettripsavailable")
    suspend fun getAllTripsAvailable(@Header("Authorization") token: String):Response<AllTripsAvailbleResponse>
    @POST("filtertripbynumberbus")
    suspend fun filterByBusAndCities(@Header("Authorization") token: String,@Body info:FilterByBusAndCitiesRequste):Response<AllTripsAvailbleResponse>
    @POST("gettripisavailable")
    suspend fun filterByCites(@Header("Authorization") token: String,@Body info:AvailableRequste):Response<AllTripsAvailbleResponse>
    @POST("filtertripbynumberbusonly")
    suspend fun filterTripsBus(@Header("Authorization") token: String,@Body info:FilterByBusOnlyRequste):Response<AllTripsAvailbleResponse>
    @GET("getalltripfinished")
    suspend fun getAllTripsFinished(@Header("Authorization") token: String):Response<FinishedfFilterResponse>
    @POST("gettripisfinished")
    suspend fun getFinishedByCites(@Header("Authorization") token: String,@Body info:FilterFinishedRequste):Response<FinishedfFilterResponse>
    @POST("getcustumersbynumberdisk/{id}")
    suspend fun filterCustomerBySeat(@Path("id") tripId:Int,@Body info:FilterCustmerBySeatRequset):Response<CustomerTripAvailable>
    @PUT("reservation/{idCustomer}/{idTrip}")
    suspend fun confirmReserve(@Path("idCustomer") customerId:Int,@Path("idTrip") tripId:Int):Response<ConfermResponse>
    @PUT("reservebyadmin/{id}")
    suspend fun reserveSeats(@Header("Authorization") token: String,@Path("id") tripId:Int,@Body info: ReserveRequste):Response<ReserveResponse>
    @PUT("canclereservation/{idCustomer}/{idTrip}")
    suspend fun cancelCustomerReserve(@Path("idCustomer") customerId:Int,@Path("idTrip") tripId:Int):Response<ConfermResponse>
    @GET("getonetrip/{id}")
    suspend fun getOneTrip(@Path("id") tripId:Int):Response<ResponseOneTrip>
    @POST("addbus")
    suspend fun addBus(@Header("Authorization") token: String,@Body info:AddBusRequest):Response<AddBusResponse>
    @GET("gettypebus")
    suspend fun getTypeBus():Response<TypeBusResponse>
    @DELETE("deletebus/{id}")
    suspend fun deleteBus(@Path("id") busId:Int):Response<DeleteBusResponse>
    @GET("getbuses")
    suspend fun getAllBuss(@Header("Authorization") token: String):Response<AllBussResponse>
    @GET("getdriverisavailable")
    suspend fun getAvailableDrivers(@Header("Authorization") token: String):Response<DraiversAvailableResponse>
    @GET("getalldrivers")
    suspend fun getAllDrivers(@Header("Authorization") token: String):Response<AllDriversResponse>
    @POST("block/{id}")
    suspend fun blockCustomer(@Header("Authorization") token: String,@Path("id") customerId:Int):Response<BannedResponse>
    @PUT("cancleblock/{id}")
    suspend fun cancelBlockCustomer(@Header("Authorization") token: String,@Path("id") customerId:Int):Response<BannedResponse>
    @POST("adddrivers")
    suspend fun addDriver(@Header("Authorization") token: String,@Body info:AddDriverRequest):Response<AddDriverResponse>
    @DELETE("deletedriver/{id}")
    suspend fun deleteDriver(@Path("id") driverId:Int):Response<DeleteDrivereResponse>
    @GET("getcustumersisblock")
    suspend fun getAllCustomersBlocked(@Header("Authorization") token: String):Response<AllCustomersBlockedResponse>
    @GET("getcustumersisnotpaid")
    suspend fun getAllUsersToReview(@Header("Authorization") token: String):Response<AllUsersToReviewResponse>
    @PUT("updatebus/{id}")
    suspend fun upDateBus(@Path("id") busId:Int,@Body info:EditeBusRequest):Response<EditeBusResponse>
    @GET("gettripscurrent")
    suspend fun getTrackTrips(@Header("Authorization") token: String):Response<AllTripsAvailbleResponse>
}