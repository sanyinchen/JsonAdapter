package com.sanyinchen.json.curry

/**
 * Created by sanyinchen on 19-11-13.
 *
 * @author sanyinchen
 * @version v0.1
 * @since 19-11-13
 */

typealias Function<T> = (String) -> T?

typealias FunctionWrap<T> = (Function<T>) -> Function<T>