public class ArrMath {
    static int[] addArrs(int[] x,int[] y){
        int[] a=y;
        int[] z=x;
        for (int i=0;i<z.length;i++){
            z[i]+=a[i];
        }
        return z;
    }
    static int[] minusArrs(int[] x,int[] y){
      int[] a=y;
      int[] z=x;
      for (int i=0;i<z.length;i++){
          z[i]-=a[i];
      }
      return z;
    }
}
