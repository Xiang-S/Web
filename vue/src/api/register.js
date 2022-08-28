import axios from "axios";
import request from "@/utils/request";

const baseUrl = "http://localhost:8080/"

export function checkUsername(params) {
    return request({
        url: baseUrl + 'xiaomi/users/checkUsername',
        method: 'post',
        data: params
    })
}