package session

data class AuthenticatedSession(
    private val user_id : Int,
    private val email : String
) {

    fun getUserId() : Int = user_id
    fun getEmail() : String = email

    companion object {
        const val NAME = "AUTHENTICATED_SESSION"
    }
}
