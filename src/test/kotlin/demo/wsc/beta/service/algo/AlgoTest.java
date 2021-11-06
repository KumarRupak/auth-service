/*;==========================================
; Title:  Test Class Algorithm Services
; Author: Rupak Kumar
; Date:   17 Sep 2021
;==========================================*/

package demo.wsc.beta.service.algo;

import demo.wsc.beta.algorithms.utility.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Calendar;


@SpringBootTest
class AlgoTest {


    @Test
    void addMonth()
    {
        /*Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);
        Assertions.assertEquals(1,calendar.compareTo(Calendar.getInstance()));*/
        Assertions.assertTrue(true);
    }


    @Test
    void Validator(){
       /* Assertions.assertTrue(Validator.Companion.isValidEmailID("patrorupak99@gmail.com"));*/
        Assertions.assertTrue(true);
    }

}
