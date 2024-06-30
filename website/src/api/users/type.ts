// 用户登录接口需要携带参数
import type {ResponseData} from "@/api/basic";

export interface LoginData {
    username: string,
    password: string,
}

export interface RegisterData {
    username: string,
    password: string,
    role: number,
}

export interface UserInfo {
    id: number,
    password: string,
    role: string
}

export interface UserLoginResponseData extends ResponseData {
    data: UserInfo
}

export interface UserRegisterResponseData extends ResponseData {
    data: UserInfo
}
