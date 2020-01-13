package com.example.primaryschoolteachers_app.api;




import com.example.primaryschoolteachers_app.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

//    @GET("api/core/categoryList/{str}")
//    Call<List<CategoryResponse>> getCategoryList(@Path("str") String str);
//
//    @GET("api/core/subCategoryList/{c_id}")
//    Call<List<SubCategoryResponse>> getSubCategoryList(@Path("c_id") String cid);
//
//    @GET("api/core/productItem/{sc_id}")
//    Call<List<ItemResponse>> getItemDetails(@Path("sc_id") int sc_id);
//
//    @GET("api/core/dutyCalculationList/{u_id}")
//    Call<DutyHistory> getDutyCalculationHistory(@Path("u_id") int u_id);
//
//    @GET("api/core/dutyCalculationDetails/{dc_id}")
//    Call<DutyHistory> getDutyCalculationDetails(@Path("dc_id") int dc_id);
//
//    @FormUrlEncoded
//    @POST("api/core/addDutyCalculation")
//    Call<ResponseBody> addDutyCalculation(
//            @Field("u_id") String u_id,
//            @Field("created_at") String created_at,
//            @Field("shipping_cost") String shipping_cost,
//            @Field("total_import_duty_and_tax") String total_import_duty_and_tax,
//            @Field("cost_of_product_and_import") String cost_of_product_and_import,
//            @Field("total_landing_cost") String total_landing_cost,
//            @Field("dc_from_country") String dc_from_country,
//            @Field("dc_to_country") String dc_to_country,
//            @Field("product_count") String product_count,
//            @Field("product_id") String product_id,
//            @Field("product_value") String product_value
//    ) ;
//
//    @FormUrlEncoded
//    @POST("api/core/addCurrencyDeclaraton")
//    Call<ResponseBody> addCurrencyDeclaraton(
//            @Field("u_id") String u_id,
//            @Field("created_at") String created_at,
//            @Field("fe_fname") String fe_fname,
//            @Field("fe_nationality") String fe_nationality,
//            @Field("fe_passport_no") String fe_passport_no,
//            @Field("fe_doi") String fe_doi,
//            @Field("fe_poi") String fe_poi,
//            @Field("fe_dntoa") String fe_dntoa,
//            @Field("fe_flight_no") String fe_flight_no,
//            @Field("fe_address") String fe_address,
//            @Field("fe_dia") String fe_dia,
//            @Field("fe_profession") String fe_profession,
//            @Field("fe_contact_no") String fe_contact_no,
//            @Field("dec_count") String dec_count,
//            @Field("d_doc") String d_doc,
//            @Field("d_amount") String d_amount,
//            @Field("d_aiw") String d_aiw
//    ) ;
//
//    @GET("api/core/currencyDeclarationLists/{u_id}")
//    Call<CurrencyDeclarationResponse> getCurrencyDeclarationHistory(@Path("u_id") int u_id);
//
//    @GET("api/core/currencyDeclarationDetails/{fc_id}")
//    Call<CurrencyDeclarationDetailsResponse> getCurrencyDeclarationDetails(@Path("fc_id") int fc_id);
//
//
//    @FormUrlEncoded
//    @POST("api/core/registration")
//    Call<ResponseBody> createUser(
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("password") String password,
//            @Field("passport") String passport,
//            @Field("mobile") String mobile
//    ) ;

    @FormUrlEncoded
    @POST("api/school/login")
    Call<LoginResponse> userLogin(
            @Field("mobile_no") String mobile_no,
            @Field("password") String password
    ) ;

//    @GET("api/core/userDetails/{u_id}")
//    Call<UserData> getUserDetails(@Path("u_id") int id);
//
//    @FormUrlEncoded
//    @POST("api/core/profileUpdate")
//    Call<ResponseBody> updateUser(
//            @Field("id") String id,
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("mobile") String mobile,
//            @Field("passport") String passport,
//            @Field("present_address") String present_address,
//            @Field("permanent_address") String permanent_address
//    ) ;
//
//    @FormUrlEncoded
//    @POST("api/core/profileImage")
//    Call<ResponseBody> uploadProfileImage(
//            @Field("id") String id,
//            @Field("u_image") String imageString
//    ) ;
//
//    @FormUrlEncoded
//    @POST("api/core/passportImage")
//    Call<ResponseBody> uploadPassportImage(
//            @Field("id") String id,
//            @Field("u_passport_scan") String imageString
//    ) ;
//
//    @GET("api/core/countryList/")
//    Call<List<Country>> getCountryList();

}