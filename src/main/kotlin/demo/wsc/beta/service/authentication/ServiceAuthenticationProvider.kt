/*;==========================================
; Title:  Service class for Authenticate Users
; Author: Rupak Kumar
; Date:   22 Sep 2021
;==========================================*/

package demo.wsc.beta.service.authentication


import org.springframework.beans.factory.annotation.Autowired
import demo.wsc.beta.repository.AuthCustomerRepository
import demo.wsc.beta.repository.WSCOwnerRepository
import demo.wsc.beta.repository.AuthUserDetailsRepository
import demo.wsc.beta.model.AuthUserDetails
import demo.wsc.beta.algorithms.authconfig.AuthUser
import org.springframework.stereotype.Service

@Service
 class ServiceAuthenticationProvider : ServiceAuthentication {
    @Autowired
    lateinit var repoAusCus: AuthCustomerRepository

    @Autowired
    lateinit var repoOwner: WSCOwnerRepository

    @Autowired
    lateinit var repoAuthUser: AuthUserDetailsRepository




    /**
     * Authenticate the users based on the provided details by user
     *
     * @param 'AuthUserDetails'
     * @return - boolen
     */
    override fun applicationAuthentication(details: AuthUserDetails): MutableMap<String, Int> {
        val auth = AuthUser()
        auth.setCustomerId(details.customerId)
        auth.setPasswd(details.password)
        return auth.authenticate(repoAusCus, repoOwner)
    }

    /**
     * Adding and updating the JWT token in repository
     *
     * @param 'AuthUserDetails'
     * @return - boolen
     */
    override fun addUpdateToken(details: AuthUserDetails): Boolean {
        if (!repoAuthUser.save(details).equals(null)) {
            return true
        }
        return false
    }

    /**
     * Get the user level and his role
     *
     * @param 'customerId'
     * @return - role
     */
    override fun getUserLevel(customerId: Int): String? {
        return if (repoAuthUser.findById(customerId).isPresent) {
            repoAuthUser.findById(customerId).get().level
        } else
            return null
    }

    /**
     * Deleting the JWT token form repository
     *
     * @param 'customerId'
     * @return - boolen
     */
    override fun deleteToken(customerId: Int): Boolean {
        if (repoAuthUser.findById(customerId).isPresent) {
            repoAuthUser.deleteById(customerId)
            return true
        }
        return false
    }


    /**
     * Getting the password based on the customerId provided by the user
     *
     * @param 'customerID'
     * @return - password
     */
    override fun getUserPassword(customerId: Int): String? {
        if (repoAuthUser.findById(customerId).isPresent) {
            return repoAuthUser.findById(customerId).get().password
        } else
            return null
    }
}
