package com.zhuj.android.http.data.result;

import com.zhuj.android.http.response.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class ObjectResult<T> extends ApiResponse {
    T obj;

}
