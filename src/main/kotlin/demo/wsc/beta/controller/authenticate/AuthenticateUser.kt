/*;==========================================
; Title:  End points for authenticate users (login,logout)
; Author: Rupak Kumar
; Date:   23 Sep 2021
;==========================================*/

package demo.wsc.beta.controller.authenticate

import demo.wsc.beta.algorithms.PasswordEncode.Encoder
import demo.wsc.beta.enums.AuthRole
import demo.wsc.beta.exceptions.WSCExceptionInvalidUser
import demo.wsc.beta.model.AuthUserDetails
import demo.wsc.beta.model.transport.AuthStatus
import demo.wsc.beta.service.authentication.ServiceAuthenticationProvider
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


@RestController
@Slf4j
@RequestMapping("api/v1")
class AuthenticateUser {

    @Autowired
    lateinit var serviceAuth: ServiceAuthenticationProvider

    val log = LoggerFactory.getLogger(AuthenticateUser::class.java)

    /**
     * End point for login  customer
     *
     * @param 'Id,Password'
     * @return - Response enitity
     */
    @PostMapping("/login", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun loginUser(
        @RequestBody auth: AuthUserDetails,
        response: HttpServletResponse
    ):ResponseEntity<Any> {
        val status = serviceAuth.applicationAuthentication(auth)
        if (!status.containsKey("NA") && status.containsValue(1)) {
            serviceAuth.addUpdateToken(
                AuthUserDetails(
                    auth.customerId,
                    Encoder.encode(auth.password),
                    status.keys.toList().get(0),
                    Instant.now()
                )
            )
            log.info("User successfully loggedin ${auth.customerId}")
            val key = Jwts.builder()
                .setIssuer(auth.customerId.toString())
                .setExpiration(Date(System.currentTimeMillis() + 1000*60*60*6))
                .signWith(SignatureAlgorithm.HS384, auth.customerId.toString())
                .compact()
            return ResponseEntity(key, HttpStatus.OK)
        } else {
            throw WSCExceptionInvalidUser()
        }
    }



    /**
     * End point for log out
     *
     * @param 'Id,Password'
     * @return - Response enitity
     */

    @PostMapping("/logout/{userId}")
    fun logout(
        response: HttpServletResponse,
        @PathVariable("userId") customerId:Int,
        token:String?
    ): ResponseEntity<Any> {
        val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
        if (body.values.toList()[0].equals(customerId.toString())
        ) {
            log.info("User successfully logged out ${customerId}")
            serviceAuth.deleteToken(customerId)
            return ResponseEntity.ok("logged  out")
        }
        else{
            throw WSCExceptionInvalidUser()
        }
    }

}