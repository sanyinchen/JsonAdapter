import com.sanyinchen.json.JsonAdapter

/**
 * Created by sanyinchen on 19-11-13.
 *
 * @author sanyinchen
 * @version v0.1
 * @since 19-11-13
 */
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