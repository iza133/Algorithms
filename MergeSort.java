//Izabela Golec
import java.util.Scanner;

/* a program that shuffles songs from two CDs. In addition, we want to know what is the longest prefix
among the names of all songs from both selected albums.*/

/* in my merge sort solution, the first step is to divide the array into 4 equal
  parts and "replacement" of the two middle ones (if array_length%2 == 1, then the last song from the first album is placed at the end of the array,
  and if array_length%4 == 2, then the last elements of the two slabs are moved to the end of the array
  and new_end_array = old_end - 2, we break the array into two "smaller problems" and do recursion
  The program has O(Nlog(N)) complexity because it recursively calls itself on smaller and smaller halves of the array. */

public class Source {
    public static String prefix = "";
    static void swap(String[] A, int i, int j)
    {
        String tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }
    public static void mergeSort(String[] arr, int beg, int end){
        int len = end - beg + 1; //we calculate the length of the current fragment of the array
        //prefix check
        char[] arr1 = arr[beg].toCharArray();
        char[] arr2 = arr[end].toCharArray();
        char[] prefix_tab = prefix.toCharArray();
        prefix = "";
        for (int k = 0; k < prefix_tab.length && k < arr1.length && k < arr2.length && prefix_tab[k] == arr1[k] && prefix_tab[k]==arr2[k]; k++)
        {
            prefix = prefix + prefix_tab[k];
        }
        if (len <= 2) //end (array cannot be split into smaller problems or into 4)
        {
            return;
        }
        if (beg>=end) return;//end
        //for length 3 we swap the last two elements
        if (len == 3)
        {
            swap(arr,beg+1, end);
            return;
        }
        //for odd length, the last song of the first disc is moved to the end of the table
        if (len%2 == 1)
        {
            String tmp = arr[len/2];
            for (int i = len/2 + 1; i < len; i++)
            {
                arr[i-1]=arr[i];
            }
            arr[end] = tmp;
            len = len - 1;
            end = end - 1;
        }
        //for array_length%4 == 2 we shift
        //the last song from the first album to the penultimate position of the table and new_end_tab = old_end-2
        if ((len%4) == 2)
        {
            int end1 = ((end + beg) / 2) - 1;
            int beg2 = ((end + beg) / 2) + 1;
            int beg1 = ((end1 + beg) / 2) + 1;
            int j = beg2;
            for (int i = beg1; i <= end1 ; i ++)
            {
                swap (arr, i, j);
                j++;
            }
            String tmp = arr[end1+1];
            for (int i = beg2; i < end; i++)
            {
                arr[i-1]=arr[i];
            }
            arr[end - 1] = tmp;
            beg2 = beg2 - 1;
            mergeSort(arr, beg, end1);
            mergeSort(arr, beg2, end-2);
        }
        // for length 4 we divide the array into 4 as in the description of the solution
        if ((len) % 4 == 0)
        {
            int end1 = ((end + beg) / 2);
            int beg2 = ((end + beg) / 2) + 1;
            int beg1 = ((end1 + beg) / 2) + 1;
            if (len == 4)
            {
                beg1=beg+1;
                end1=beg1;
            }
            int j = beg2;
            for (int i = beg1; i <= end1 ; i ++)
            {
                    swap (arr, i, j);
                    j++;
            }
            int mid = ((end+beg)/2)+1;
            mergeSort(arr, beg, mid-1);
            mergeSort(arr, mid, end);
        }
    }
    public static Scanner in = new Scanner(System.in);
    public static void main (String[] args)
    {
        int z = in.nextInt();
        for (int i = 0; i < z; i++)
        {
            int n = in.nextInt();
            String[] array = new String[n];
            array[0] = in.nextLine();
            for (int j = 0; j < n; ++j)
            {
                array[j] = in.next();
            }
            //set the prefix to the first name of the song
            prefix = array[0];
            mergeSort(array, 0, array.length-1);
            for (int k = 0; k < array.length; k ++)
            {
                System.out.print(array[k] + " ");
            }
            System.out.println();
            System.out.println(prefix);
        }
    }
}
/*
test.in
6
24
a1 a2 a3 a4 a5 a6 a7 a8 a9 a10 a11 a12 b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12
4
raz_raz, raz_dwa, dwa_raz, dwa_dwa
5
pio11 piose12 piosenk13 piosea21 piose22
15
p1 p2 p3 p4 p5 p6 p7 p8 p9 p10 p11 p12 p13 p14 p15
6
aaaa aaa aa aaaa aaa aa
9
a1 a2 a3 a4 a5 b1 b2 b3 b4

test.out
1 b1 a2 b2 a3 b3 a4 b4 a5 b5 a6 b6 a7 b7 a8 b8 a9 b9 a10 b10 a11 b11 a12 b12

raz_raz, dwa_raz, raz_dwa, dwa_dwa

pio11 piosea21 piose12 piose22 piosenk13
pio
p1 p9 p2 p10 p3 p11 p4 p12 p5 p13 p6 p14 p7 p15 p8
p
aaaa aaaa aaa aaa aa aa
aa
 */