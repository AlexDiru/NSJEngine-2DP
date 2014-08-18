import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 18/08/14
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class NSJArray {

    //thx stack overflow http://stackoverflow.com/questions/4697255/combine-two-integer-arrays
    final static
    public int[] merge(final Integer[] ...arrays ) {
        int size = 0;
        for (Integer[] a: arrays )
            size += a.length;

        int[] res = new int[size];

        int destPos = 0;
        for ( int i = 0; i < arrays.length; i++ ) {
            if ( i > 0 ) destPos += arrays[i-1].length;
            int length = arrays[i].length;
            System.arraycopy(arrays[i], 0, res, destPos, length);
        }

        return res;
    }
}
