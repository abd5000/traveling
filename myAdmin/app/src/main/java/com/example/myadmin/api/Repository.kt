package com.example.myadmin.api

import android.util.Log
import com.example.myadmin.home.addtrip.AddTripRequest
import com.example.myadmin.home.addtrip.duration.DurationRequset
import com.example.myadmin.home.addtrip.duration.DurationResponse
import com.example.myadmin.home.addtrip.buss.RequstGetBus
import com.example.myadmin.home.addtrip.ResponCity
import com.example.myadmin.home.addtrip.ResponseAddTrip
import com.example.myadmin.home.addtrip.buss.ResponseGetBus
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
import com.google.gson.Gson

class Repository {
    suspend fun login(email:String,password:String):LoginResponse?{
        val result=Instance.api.login(LoginInfo(email, password))
        //
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), LoginResponse::class.java)
            errorResponse


        }
    }
    suspend fun getBus(token:String,arrCity:String,depCity:String,date:String,time:String): ResponseGetBus?{
        val result=Instance.api.getBus("Bearer $token", RequstGetBus(arrCity,depCity,date,time))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ResponseGetBus::class.java)
            errorResponse
        }
    }
    suspend fun getDuration(arrCity: String,depCity:String): DurationResponse?{
        val result=Instance.api.getDuration(DurationRequset(arrCity,depCity))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), DurationResponse::class.java)
            errorResponse
        }
    }
    suspend fun cites(): ResponCity?{
        val result=Instance.api.cities()
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ResponCity::class.java)
            errorResponse
        }
    }
    suspend fun addTrip( destination: Int, starting: Int, tripDate: String, tripTime: String, busid: Int,isPeriodic:Boolean ):ResponseAddTrip?{
        val result=Instance.api.addTrip(AddTripRequest( busid,destination,starting,tripDate,tripTime,isPeriodic))
        Log.i("responceaddtrip", "${result}")
        return try {
            result.body()!!
        }catch (error:Throwable){

            Log.i("responceaddtriperror", "${result.errorBody()}")
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ResponseAddTrip::class.java)
            errorResponse
        }
    }
    suspend fun getAvailableTrips(token:String,idFrom:Int,idTo:Int):AllTripsAvailbleResponse?{
        val result=Instance.api.getTripsAvailable("Bearer $token", AvailableRequste(idTo,idFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
            errorResponse
        }

    }
    suspend fun deleteTrip(tripId:Int):DeleteResponse?{
        val result=Instance.api.deleteTrip(tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), DeleteResponse::class.java)
            errorResponse
        }
    }
    suspend fun filterBus(token:String,idFrom:Int,idTo:Int,numberBus:Int):AllTripsAvailbleResponse?{
        val result=Instance.api.filterBus("Bearer $token", FilterBusRequste(idTo,numberBus,idFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
            errorResponse
        }
    }
    suspend fun upDateTrip(date:String,time: String,tripId:Int):SaveChengesResponse?{
        val result=Instance.api.updateTrip(tripId,RequestSaveChanges(date,time))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), SaveChengesResponse::class.java)
            errorResponse
        }
    }
    suspend fun getCustomerTrip(token:String,tripId:Int): CustomerTripAvailable? {
        val result=Instance.api.getCustomerTrip("Bearer $token",tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), CustomerTripAvailable::class.java)
            errorResponse
        }
    }
  suspend fun getAllTripsAvailable(token:String):AllTripsAvailbleResponse?{
      val result=Instance.api.getAllTripsAvailable("Bearer $token")
      return try {
          result.body()!!
      }catch (error:Throwable){
          val responseBody = result.errorBody()
          val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
          errorResponse
      }
  }
    suspend fun filterByBusAndCites(token:String,idFrom:Int,idTo:Int,idBus:Int):AllTripsAvailbleResponse?{
        val result=Instance.api.filterByBusAndCities("Bearer $token", FilterByBusAndCitiesRequste(idTo,idBus,idFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
            errorResponse
        }
    }
    suspend fun filterByCites(token:String,idFrom:Int,idTo:Int):AllTripsAvailbleResponse?{
        val result=Instance.api.filterByCites("Bearer $token", AvailableRequste(idTo,idFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
            errorResponse
        }

    }
    suspend fun filterTripsBus(token:String,numberBus:Int):AllTripsAvailbleResponse?{
        val result=Instance.api.filterTripsBus("Bearer $token", FilterByBusOnlyRequste(numberBus))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
            errorResponse
        }
    }
    suspend fun getAllTripsFinished(token:String):FinishedfFilterResponse?{
        val result=Instance.api.getAllTripsFinished("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), FinishedfFilterResponse::class.java)
            errorResponse
        }
    }
    suspend fun getFinishedTripsByCites(token:String,idFrom: Int,idTo: Int):FinishedfFilterResponse?{
        val result=Instance.api.getFinishedByCites("Bearer $token",FilterFinishedRequste(idTo,idFrom))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), FinishedfFilterResponse::class.java)
            errorResponse
        }
    }
    suspend fun filterCustomerBuySeat(tripId: Int,seat:Int):CustomerTripAvailable?{
        val result=Instance.api.filterCustomerBySeat(tripId, FilterCustmerBySeatRequset(seat))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), CustomerTripAvailable::class.java)
            errorResponse
        }
    }
    suspend fun confirmReserve(customerId:Int,tripId: Int):ConfermResponse?{
        val result=Instance.api.confirmReserve(customerId, tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ConfermResponse::class.java)
            errorResponse
        }
    }
    suspend fun reserveSeats(token: String, tripId: Int, seats: List<Int>, name:String):ReserveResponse?{
        val result=Instance.api.reserveSeats("Bearer $token", tripId,ReserveRequste(seats,name))
        Log.i("infoseats", "${result.body()}")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ReserveResponse::class.java)
            errorResponse
        }
    }
    suspend fun cancelCustomerReserve(customerId:Int,tripId: Int):ConfermResponse?{
        val result=Instance.api.cancelCustomerReserve(customerId,tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ConfermResponse::class.java)
            errorResponse
        }
    }
    suspend fun getCustomerPaid(tripId: Int):CustomerTripAvailable?{
        val result=Instance.api.getCustomerPaid(tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), CustomerTripAvailable::class.java)
            errorResponse
        }
    }
    suspend fun getOneTrip(tripId: Int): ResponseOneTrip?{
        val result=Instance.api.getOneTrip(tripId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), ResponseOneTrip::class.java)
            errorResponse
        }
    }
    suspend fun addBus(token: String,busNumber: Int,type:String,place:String,driverId:Int):AddBusResponse?{
        val result=Instance.api.addBus("Bearer $token", AddBusRequest(busNumber,place,type,driverId))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AddBusResponse::class.java)
            errorResponse
        }
    }
    suspend fun getTypeBus():TypeBusResponse?{
        val result=Instance.api.getTypeBus()
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), TypeBusResponse::class.java)
            errorResponse
        }
    }
    suspend fun deleteBus(busId: Int):DeleteBusResponse?{
        val result=Instance.api.deleteBus(busId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), DeleteBusResponse::class.java)
            errorResponse
        }
    }
    suspend fun getAllBuss(token: String):AllBussResponse?{
        val result=Instance.api.getAllBuss("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllBussResponse::class.java)
            errorResponse
        }
    }
    suspend fun getAvailableDrivers(token: String):DraiversAvailableResponse?{
        val result=Instance.api.getAvailableDrivers("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), DraiversAvailableResponse::class.java)
            errorResponse
        }

    }

    suspend fun blockCustomer(token: String,customerId: Int):BannedResponse?{
        val result=Instance.api.blockCustomer("Bearer $token",customerId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), BannedResponse::class.java)
            errorResponse
        }
    }
    suspend fun cancelBlockCustomer(token: String,customerId: Int):BannedResponse?{
        val result=Instance.api.cancelBlockCustomer("Bearer $token",customerId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), BannedResponse::class.java)
            errorResponse
        }
    }
    suspend fun addDriver(token: String, fullname: String, email: String, password: String,confirmpassword: String, phone: String):AddDriverResponse? {
        val result = Instance.api.addDriver(
            "Bearer $token",
            AddDriverRequest(confirmpassword, email, fullname, password, phone)
        )
       return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse =
                Gson().fromJson(responseBody?.string(), AddDriverResponse::class.java)
            errorResponse
        }

}

    suspend fun getAllDrivers(token: String):AllDriversResponse?{
        val result = Instance.api.getAllDrivers("Bearer $token")
        return try {
            result.body()!!
        } catch (error: Throwable) {
            val responseBody = result.errorBody()
            val errorResponse =
                Gson().fromJson(responseBody?.string(), AllDriversResponse::class.java)
            errorResponse
        }
    }
    suspend fun deleteDriver(driverId: Int):DeleteDrivereResponse?{
        val result=Instance.api.deleteDriver(driverId)
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), DeleteDrivereResponse::class.java)
            errorResponse
        }
    }
    suspend fun getAllCustomersBlocked(token: String):AllCustomersBlockedResponse?{
        val result=Instance.api.getAllCustomersBlocked("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllCustomersBlockedResponse::class.java)
            errorResponse
        }
    }
    suspend fun getAllUsersToReview(token: String):AllUsersToReviewResponse?{
        val result=Instance.api.getAllUsersToReview("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllUsersToReviewResponse::class.java)
            errorResponse
        }
    }
    suspend fun upDateBus(busId: Int,driverId: Int): EditeBusResponse?{
        val result=Instance.api.upDateBus(busId, EditeBusRequest(driverId))
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), EditeBusResponse::class.java)
            errorResponse
        }
    }
    suspend fun getTrackTrips(token: String):AllTripsAvailbleResponse?{
        val result=Instance.api.getTrackTrips("Bearer $token")
        return try {
            result.body()!!
        }catch (error:Throwable){
            val responseBody = result.errorBody()
            val errorResponse = Gson().fromJson(responseBody?.string(), AllTripsAvailbleResponse::class.java)
            errorResponse
        }
    }

}