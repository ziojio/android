package com.zhuj.android.http.data.result;

import com.zhuj.android.http.response.ApiResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 可以直接使用ObjectResult<List<T>>，
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class ArrayResult<S, F> {
    List<S> array;

}
