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
