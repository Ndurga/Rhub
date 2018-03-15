package in.rhub.rhub;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by durga on 11-02-2018.
 */

public interface RhubAPIS {

    @POST("usersdata.php?fn=authenticateuser")
    Call<Response> authenticateUser(@Query("phone") String phone, @Query("password") String password);

    @POST("usersdata.php?fn=registeruser")
    Call<Response> registerUser(@Query("name") String name, @Query("email") String email,
                                @Query("phone") String phone, @Query("password") String passwd);

    @POST("usersdata.php?fn=resetuserpassword")
    Call<Response> resetPassword(@Query("phone") String phone, @Query("password") String password);

    @POST("customerdata.php?fn=registercustomer")
    Call<Response> registerCustomer(@Query("name") String name, @Query("email") String email,
                                    @Query("phone") String phone, @Query("user_phone") String userPhone,
                                    @Query("city_interest") String cityInterst, @Query("area_interest") String areaInterest);

    @POST("customerdata.php?fn=getcustomerstatus")
    Call<Response> getCustomerStatus(@Query("phone") String phone, @Query("user_phone") String userPhone);

    @POST("customerdata.php?fn=updatecustomerinfo")
    Call<Response> updateCustomerInformation(@Query("name") String name, @Query("email") String email,
                                             @Query("phone") String phone, @Query("user_phone") String user_phone, @Query("city_interest") String cityOfInterest,
                                             @Query("area_interest") String areaOfInterest);

    @POST("customerdata.php?fn=getcustomerinfo")
    Call<CustomerInformationResponse> getCustomerInformation(@Query("phone") String phone, @Query("user_phone") String user_phone);

    @POST("customerdata.php?fn=getallcustomersbasicinfo")
    Call<CustomersBasicInformation> getAllCustomersBasicInfo(@Query("user_phone") String phone);
}
