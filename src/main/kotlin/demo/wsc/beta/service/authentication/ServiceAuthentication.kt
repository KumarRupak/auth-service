/*;==========================================
; Title:  Service class for Authenticate Users
; Author: Rupak Kumar
; Date:   22 Sep 2021
;==========================================*/

package demo.wsc.beta.service.authentication

import demo.wsc.beta.model.AuthUserDetails

 interface ServiceAuthentication {

    fun applicationAuthentication(details: AuthUserDetails): MutableMap<String,Int>

    fun addUpdateToken(details: AuthUserDetails): Boolean

    fun getUserLevel(customerId: Int): String?

    fun getUserPassword(customerId: Int):String?

    fun deleteToken(customerId: Int): Boolean
}
