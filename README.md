# JsonAdapter

A simple lib fo enhance moshi json parse .

When you parse fail by some unknown exception , it will auto down to parse by Gson .

### FOO DEMO

```

data class Person(var username: String, var number: String)

data class Persons(var person: Person)


fun main() {
    val json = "{\n" +
            "    \"person\":{\n" +
            "        \"username\": \"jesse\",\n" +
            "        \"number\": 32\n" +
            "    }\n" +
            "}"

    var test: Persons? = JsonAdapter.fromJson(json, Persons::class.java)
    println(test?.person ?: "null")
}

```

### CORE CODE SCOPE

```
fun <T> fromJson(
        content: String,
        classz: Class<T>,
        parserAdapter: Any? = null,
        ignoreException: Boolean = true
    ): T? {

        val moshiBuild = Moshi.Builder()
        moshiBuild.add(KotlinJsonAdapterFactory())
        if (parserAdapter != null) {
            moshiBuild.add(parserAdapter)
        }

        val parserChain = mutableListOf<FunctionWrap<T>>()

        parserChain.add { next ->
            {
                try {
                    val jsonAdapter = moshiBuild.build().adapter<T>(classz)
                    jsonAdapter.fromJson(content)
                } catch (ex: Exception) {
                    if (!ignoreException) {
                        ex.printStackTrace()
                    }
                    null
                } ?: next(it)
            }

        }

        return parserChain.asReversed().fold(
            { str: String ->
                try {
                    Gson().fromJson<T>(str, classz)
                } catch (ex: Exception) {
                    if (!ignoreException) {
                        ex.printStackTrace()
                    }
                    null
                }
            }, { next, functionWrap ->
                functionWrap(next)
            })(content)
    }
    
```
