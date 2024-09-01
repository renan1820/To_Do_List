package com.renan.portifolio.to_dolist.ui.login.viewmodel.estudy

enum class Sexology{
    MAN, GIRL
}

data class User(
    val name: String,
    val age: Int,
    val sexology: Sexology
)

object ConfigIdentify{

    fun checkIdentify(user: User, operation: (user: User) -> Boolean): String{
        if(operation(user)){
            return "GOOD"
        }
        return "BAD: ${user.name}"
    }

}

interface MyCallback{
    fun onCallback(result: String)
}

class IdentifyClass : MyCallback{
    private var callback: MyCallback? = null

    init {
        main()
    }

    private fun main(){
        val identify: (user: User) -> Boolean = {
                user ->
            user.name == "Renan" && user.age > 20 && user.sexology == Sexology.MAN
        }

        val resultIdentify =
            ConfigIdentify
                .checkIdentify(
                    User(
                        name = "Renan",
                        age = 30,
                        sexology = Sexology.MAN
                    ), identify)

        callback?.onCallback(resultIdentify)
    }

    override fun onCallback(result: String) {
        println("result: $result")
    }
}