//Izabela Golec
import java.util.Scanner;

/*looking for the number of possible triangles in the array, we sort the array and for each
possible pair we look for the value up to which the triangle condition is met*/
/* the program has a pessimistic complexity of O(n^2log2n) because we consider cases for every
possible pair (n^2) and for each such pair we perform a bin search with a complexity of log2n.*/

public class Source
{
    public static Scanner in = new Scanner(System.in);
    public static void main (String[] args)
    {
        int set = in.nextInt();
        for(int z=1; z<=set;++z)
        {
            int n = in.nextInt(); //rows
            int []matrix = new int[n];
            for(int i=0;i<n;i++)
            {
                matrix[i]=in.nextInt();
            }
            sort (matrix, n);
            System.out.println(z+": n= "+n);
            for (int i=0; i < n; i++) //print a sorted array
            {
                System.out.print(matrix[i]+" ");
                if ((i+1)%25==0 && i+1<n && i > 0)
                    System.out.print("\n");
            }

            triangles(matrix,n);
        }
    }
    public static void sort (int[] a, int n) //sorting function
    {
        {
            int i,j;
            for(i=0; i<n-1; i++) {
                int min = i;
                for(j=i+1; j<n; j++)
                    if(a[min]>a[j])
                        min = j;
                if(min!=i)
                {
                    int temp=a[min];
                    a[min]=a[i];
                    a[i]=temp;
                }
            }
        }

    }
    public static int binSearch (int[] array, int x, int y, int begin, int last)
    {
        int sum = array[x]+array[y];
        int n = last;
        while (begin<=last)
        {
            int mid = (begin+last)/2;
            if (array[mid] < sum)
            {
                if (mid < n)
                {
                    if (array[mid+1] >= sum)
                        return mid;
                    else
                        begin = mid +1;
                }
                else
                    return mid;
            }
            else if (array[mid]>=sum)
            {
                last = mid-1;
            }
        }
        return -1;
    }
    public static void triangles(int[] a,  int n)
    {
        int number_of_triangles=0;
        int how_many=0;
        System.out.print("\n");
        for (int i=0; i+2 < n; i++) //selection of the first element of the pair
        {
            for (int k=i+1; (k+1)<n; ++k) //selection of the second element of the pair
            {
                int c = binSearch(a, i, k,  k+1, n-1); //find the value up to which the triangle condition is satisfied
                if (c!=-1)
                    number_of_triangles += c - k;
                if (how_many < 10 && c != -1)
                {
                    for (int j=k+1; j<=c && how_many < 10 ;++j) //loop that will execute only 10 times - we don't take it into account in complexity
                    {
                        System.out.print("("+ i +"," + (k) + "," +j + ") ");
                        how_many++;
                    }
                }
            }
        }
        //print the coordinates as soon as I count them
        if (number_of_triangles!=0)
        {
            System.out.print("\n");
            System.out.println("Number of triangles: "+number_of_triangles);
        }
        else
            System.out.println("Triangles cannot be built");
    }
}

/*test.in:
        10
        50
        7 7 3 9 9 5 9 8 1 9 9 4 6 7 6 3 1 8 5 9 6 9 7 3 7 5 3 7 4 4 1 9 6 2 2 5 9 6 4 3 2 7 6 9 1 5 2 6 8 6
        50
        9 9 5 6 3 4 7 2 6 4 6 6 4 1 8 5 1 2 2 3 5 9 1 3 3 6 4 6 8 9 6 6 4 8 6 2 2 2 4 2 8 1 5 7 3 5 7 4 8 7
        50
        5 9 2 2 1 7 3 5 5 3 7 5 1 7 2 3 2 4 8 7 8 9 7 4 7 3 4 7 9 1 8 6 7 7 2 7 2 4 3 3 6 4 8 8 6 3 3 4 2 5
        50
        4 8 9 8 7 4 8 8 6 4 5 7 7 6 3 7 8 3 2 8 6 7 6 4 7 8 3 5 6 9 1 8 3 2 2 9 2 3 4 2 1 5 4 7 2 3 3 1 4 7
        50
        5 9 3 5 4 1 4 1 7 7 5 8 3 9 3 2 3 8 4 4 7 8 3 4 8 9 6 8 4 4 4 6 7 7 5 7 8 6 8 7 2 7 5 3 3 8 3 5 2 9
        50
        2 9 1 5 8 8 6 4 7 3 3 4 1 7 1 6 3 2 3 8 8 4 5 3 3 8 1 2 7 3 1 2 9 8 4 4 3 5 8 1 3 1 7 2 6 6 9 1 9 1
        50
        8 4 5 6 9 4 4 5 8 6 5 8 4 6 6 5 9 4 3 1 3 7 6 9 4 6 2 7 3 2 2 9 6 9 7 5 3 5 1 6 7 9 3 6 8 6 8 3 7 7
        50
        5 3 7 5 9 2 7 3 6 6 9 6 2 2 4 8 4 9 3 4 6 1 9 9 5 1 5 8 8 4 3 8 1 6 3 1 7 6 4 9 7 1 4 2 3 6 6 4 7 4
        50
        2 9 4 3 7 8 5 2 5 1 1 8 5 2 2 5 1 9 4 7 3 6 1 2 4 4 7 6 3 8 1 4 9 8 2 1 5 6 3 7 4 8 4 9 4 6 9 6 9 4
        50
        6 6 7 5 2 3 2 9 8 7 7 6 1 2 3 7 8 3 9 1 3 6 3 3 9 6 2 1 5 1 7 5 6 7 3 9 2 4 6 6 8 5 3 5 8 4 6 4 2 3
test.out:
1: n= 50
1 1 1 1 2 2 2 2 3 3 3 3 3 4 4 4 4 5 5 5 5 5 6 6 6
6 6 6 6 6 7 7 7 7 7 7 7 8 8 8 9 9 9 9 9 9 9 9 9 9
(0,1,2) (0,1,3) (0,2,3) (0,4,5) (0,4,6) (0,4,7) (0,5,6) (0,5,7) (0,6,7) (0,8,9)
Number of triangles: 11591
2: n= 50
1 1 1 1 2 2 2 2 2 2 2 3 3 3 3 3 4 4 4 4 4 4 4 5 5
5 5 5 6 6 6 6 6 6 6 6 6 7 7 7 7 8 8 8 8 8 9 9 9 9
(0,1,2) (0,1,3) (0,2,3) (0,4,5) (0,4,6) (0,4,7) (0,4,8) (0,4,9) (0,4,10) (0,5,6)
Number of triangles: 10384
3: n= 50
1 1 1 2 2 2 2 2 2 2 3 3 3 3 3 3 3 3 4 4 4 4 4 4 5
5 5 5 5 6 6 6 7 7 7 7 7 7 7 7 7 7 8 8 8 8 8 9 9 9
(0,1,2) (0,3,4) (0,3,5) (0,3,6) (0,3,7) (0,3,8) (0,3,9) (0,4,5) (0,4,6) (0,4,7)
Number of triangles: 10549
4: n= 50
1 1 1 2 2 2 2 2 2 3 3 3 3 3 3 3 4 4 4 4 4 4 4 5 5
5 6 6 6 6 6 7 7 7 7 7 7 7 7 8 8 8 8 8 8 8 8 9 9 9
(0,1,2) (0,3,4) (0,3,5) (0,3,6) (0,3,7) (0,3,8) (0,4,5) (0,4,6) (0,4,7) (0,4,8)
Number of triangles: 11070
5: n= 50
1 1 2 2 2 3 3 3 3 3 3 3 3 4 4 4 4 4 4 4 4 5 5 5 5
5 5 6 6 6 7 7 7 7 7 7 7 7 8 8 8 8 8 8 8 8 9 9 9 9
(0,2,3) (0,2,4) (0,3,4) (0,5,6) (0,5,7) (0,5,8) (0,5,9) (0,5,10) (0,5,11) (0,5,12)
Number of triangles: 12527
6: n= 50
1 1 1 1 1 1 1 1 1 2 2 2 2 2 3 3 3 3 3 3 3 3 3 4 4
4 4 4 5 5 5 6 6 6 6 7 7 7 7 8 8 8 8 8 8 8 9 9 9 9
(0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,1,6) (0,1,7) (0,1,8) (0,2,3) (0,2,4) (0,2,5)
Number of triangles: 7396
7: n= 50
1 1 2 2 2 3 3 3 3 3 3 4 4 4 4 4 4 5 5 5 5 5 5 6 6
6 6 6 6 6 6 6 6 7 7 7 7 7 7 8 8 8 8 8 9 9 9 9 9 9
(0,2,3) (0,2,4) (0,3,4) (0,5,6) (0,5,7) (0,5,8) (0,5,9) (0,5,10) (0,6,7) (0,6,8)
Number of triangles: 13467
8: n= 50
1 1 1 1 1 2 2 2 2 3 3 3 3 3 3 4 4 4 4 4 4 4 4 5 5
5 5 6 6 6 6 6 6 6 6 7 7 7 7 7 8 8 8 8 9 9 9 9 9 9
(0,1,2) (0,1,3) (0,1,4) (0,2,3) (0,2,4) (0,3,4) (0,5,6) (0,5,7) (0,5,8) (0,6,7)
Number of triangles: 10431
9: n= 50
1 1 1 1 1 1 2 2 2 2 2 2 3 3 3 3 4 4 4 4 4 4 4 4 4
5 5 5 5 5 6 6 6 6 6 7 7 7 7 8 8 8 8 8 9 9 9 9 9 9
(0,1,2) (0,1,3) (0,1,4) (0,1,5) (0,2,3) (0,2,4) (0,2,5) (0,3,4) (0,3,5) (0,4,5)
Number of triangles: 9132
10: n= 50
1 1 1 1 2 2 2 2 2 2 3 3 3 3 3 3 3 3 3 4 4 4 5 5 5
5 5 6 6 6 6 6 6 6 6 6 7 7 7 7 7 7 8 8 8 8 9 9 9 9
(0,1,2) (0,1,3) (0,2,3) (0,4,5) (0,4,6) (0,4,7) (0,4,8) (0,4,9) (0,5,6) (0,5,7)
Number of triangles: 10331
 */