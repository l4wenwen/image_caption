import request from "@/utils/request";
import type {ImageCaptionData, ImageCaptionResponse, ImageUploadData, ImageUploadResponse} from "@/api/images/type";

enum API {
    UPLOAD_URL = '/images/upload',
    CAPTION_URL = '/images/caption'
}

export const reqImageUpload = (data: ImageUploadData) => request.post<any, ImageUploadResponse>(API.UPLOAD_URL, data)

export const reqImageCaption = (data: ImageCaptionData) => request.post<any, ImageCaptionResponse>(API.CAPTION_URL, data)
