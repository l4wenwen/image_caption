import type {ResponseData} from "@/api/basic";

export interface ImageUploadData {
    data: string
}

export interface ImageCaptionData {
    mode: number,
    id: number,
}

export interface ImageUploadResponse extends ResponseData {
    id: number
}

export interface ImageCaptionResponse extends ResponseData {
    label: string
}
