package zwj.kyq.liferay.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:

 *
 * @version 1.0
 * @author: kyq1024
 * @timestamp: 2018-07-03 9:12
 */
public class TestParallel {
    public static void main(String args[]){
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
            System.out.println(c.get(Calendar.DAY_OF_MONTH));

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        final List<Integer> base = new ArrayList();
//
//        for(int i=0 ; i<10000000 ;i++){
//            base.add(i);
//        }
//
//        Date start = new Date();
//        base.parallelStream().forEach(x-> {
//            x = x+1;
//        });
//        Date end = new Date();
//
//        System.out.println(start);
//        System.out.println(end);
//
//        Date start1 = new Date();
//        base.stream().forEach(x-> {x = x+1;});
//        Date end1 = new Date();
//
//        System.out.println(start1);
//        System.out.println(end1);

    }
}
