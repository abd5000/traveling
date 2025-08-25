
import android.util.Log
import com.example.mytestnav.api.Repositry
import com.example.mytestnav.home.refreshtoken.ViewModelToken
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthrazationToken(private val viewModel: ViewModelToken) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = viewModel.token.value?.data?.token
        val repositry=Repositry()
        return if (token != null) {
            Log.i("refresh1", "$token")
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)

        } else {
            runBlocking {
                val refrshToken = viewModel.token.value?.data?.refreshtoken
                Log.i("refresh2", "$refrshToken")
                try {
                    val newToken = repositry.refreshToken(refrshToken!!)
                    viewModel.updateToken(newToken?.data?.refreshtoken!!) // قم بتحديث الرمز المميز في view model
                    val newRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                    chain.proceed(newRequest)
                } catch (e: Exception) {
                    // تعامل مع أي استثناءات عند تحديث الرمز المميز
                }
            }
            chain.proceed(request) // انتظر إكمال التحديث
        }
    }
}
