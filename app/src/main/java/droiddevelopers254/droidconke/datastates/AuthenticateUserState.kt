package droiddevelopers254.droidconke.datastates

import droiddevelopers254.droidconke.models.UserModel

data class AuthenticateUserState (
        val isUserExists : Boolean = false,
        val error : String?= null,
        val userModel: UserModel?=null
)
