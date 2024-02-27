package Canvas;
public class ArrMath {
    /**
     * add two arrays together
     * @param x
     * @param y
     * @return
     */
    static int[] addArrs(int[] x,int[] y){
        int[] a=y;
        int[] z=x;
        for (int i=0;i<z.length;i++){
            z[i]+=a[i];
        }
        return z;
    }
    /**
     * subtract one array from another
     * @param x
     * @param y
     * @return
     */
    static int[] minusArrs(int[] x,int[] y){
      int[] a=y;
      int[] z=x;
      for (int i=0;i<z.length;i++){
          z[i]-=a[i];
      }
      return z;
    }
}
