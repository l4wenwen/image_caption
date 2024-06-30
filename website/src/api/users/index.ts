import request from '@/utils/request'
import type {LoginData, RegisterData, UserLoginResponseData, UserRegisterResponseData} from "@/api/users/type";
import type {ResponseData} from "@/api/basic";

enum API {
    LOGIN_URL = '/users/login',
    REGISTER_URL = '/users/register',
    LOGOUT_URL = '/users/logout'
}

export const reqUserLogin = (data: LoginData) => request.post<any, UserLoginResponseData>(API.LOGIN_URL, data)

export const reqUserRegister = (data: RegisterData) => request.post<any, UserRegisterResponseData>(API.REGISTER_URL, data)

export const reqUserLogout = () => request.post<any, ResponseData>(API.LOGOUT_URL)
