package com.base.baselib.net.httploader;

import com.base.baselib.bean.LoginReponseBean;
import com.base.baselib.bean.LoginRequestBean;
import com.base.baselib.constant.Constant;
import com.base.baselib.net.BaseResponse;
import com.base.baselib.net.ObjectLoader;
import com.base.baselib.net.PayLoad;
import com.base.baselib.net.RetrofitServiceManager;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by dhy
 * Date: 2019/5/9
 * Time: 11:18
 * describe:
 */
public class LoginLoader extends ObjectLoader {
    private LoginService mScanService;

    public LoginLoader() {
        mScanService = RetrofitServiceManager.getInstance().create(LoginService.class);
    }

    public Observable<LoginReponseBean> getLoginInFo(LoginRequestBean bean) {
        return observe(mScanService.loginIn(Constant.SIGN_IN, bean)).map(new PayLoad<LoginReponseBean>());
    }

    public interface LoginService {
        @POST
        Observable<BaseResponse<LoginReponseBean>> loginIn(@Url String url, @Body LoginRequestBean bean);
    }
}
