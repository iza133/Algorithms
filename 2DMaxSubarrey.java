//Izabela Golec
// we look for the maximum subarray in combinations of adjacent rows of the 2d array and sum them,
// storing them in a 1D array and looking for the maximum sum in a 1D array using each algorithm (with field comparison),
// additionally comparing the field of the 2D array
import java.util.Scanner;

public class Source {
    public static Scanner in = new Scanner(System.in);
    public static void main (String[] args)
    {
        int set = in.nextInt();
        for(int z=1; z<=set;z++)
        {
            int ordinal = in.nextInt();
            char sign = in.next().charAt(0);
            int n = in.nextInt(); //rows
            int m = in.nextInt(); //columns

            int [][]matrix = new int[n][m];
            for(int i=0;i<n;i++){ // getting data
                for(int j=0;j<m;j++){
                    matrix[i][j]=in.nextInt();
                }
            }
            System.out.print(ordinal);
            System.out.print(sign);
            maxSubarray(matrix,n,m);
        }
    }
    public static int kadane(int[] p, int m) // searching the largest sum in 1D array
    {
        int max_sum=0, max_temp = 0, start = 0, s = 0;
        int pole=0;
        int temp_pole = 0;
        for (int j=0; j< m; j++)
        {
            for (int i = j; i < m; i++)
            {
                max_temp += p[i];
                temp_pole=temp_pole+1;
                if (max_sum < max_temp)
                {
                    max_sum = max_temp;
                    pole=temp_pole;
                    start = s;
                }
                if (max_sum==max_temp)
                {
                    if (pole>temp_pole)
                    {
                        pole=temp_pole;
                        start = s;
                    }
                }
                if (max_temp <= 0)
                {
                    temp_pole=temp_pole-1;
                    max_temp = 0;
                    s = i + 1;
                }
            }
        }

        return start; //returns first element
    }
    public static void maxSubarray(int[][] a,  int n, int m)
    {
        int h;
        int temp_sum; // temporary sum of the rectangle
        int sum=0; //final sum
        int r1=0; //"rowUP" rectangle's coordinate
        int r2=0; // "rowDOWN" rectangle's coordinate
        int c1=0; // "columnLEFT" rectangle's coordinate
        int c2=0; // "columnsRIGHT" rectangle's coordinate
        int[] p=new int[m]; //supp array
        int tmp_area; //temporary area
        int sum_area = 0; // final sum's area

        for (int g = 0; g < n; g++)
        {
            for (int j = 0; j < m; j++) //we reset supp array because we move one row lower in rectangles that we look for
            {
                p[j] = 0;
            }
            for (int i = g; i < n; i++)//for each row (starting with one equal g) we go throw columns looking for rectangle
            {
                temp_sum = 0;
                for (int j = 0; j < m; j++)
                {
                    p[j] = p[j] + a[i][j];
                    temp_sum = temp_sum + p[j];
                    if ( temp_sum < 0) {
                        temp_sum = 0;
                    }
                    h=kadane(p, m);
                    //counting current rectangle area
                    int x = g-i;
                    if (x<0) x=x*(-1);
                    int y = h-j;
                    if (y<0) y=y*(-1);
                    tmp_area = (x+1)*(y+1);
                    if (temp_sum==sum) //if the sum's are equal we compare the area
                    {
                        if (sum_area>tmp_area)
                        {
                            sum_area = tmp_area;
                            r1 = g;
                            c1 = h;
                            r2 = i;
                            c2 = j;
                        }
                    }
                    if (temp_sum > sum) {
                        sum = temp_sum;
                        sum_area = tmp_area;
                        r1 = g;
                        c1 = h;
                        r2 = i;
                        c2 = j;
                    }
                }
            }
        }
        int counter=0;
        int ry = 1; int w=0; int q=0;
        for (int i=0; i < n; i++) //we check if any value is equal 0 and if at least one is bigger than 0
        {
            for (int j =0; j < m; j++)
            {
                if (a[i][j]<=0) counter=counter+1;
                if (a[i][j]==0 && ry!=0)
                {
                    ry =0;
                    q=i; w=j;
                }
            }
        }
        if (counter==m*n && ry==0)
        {
            r1=q; r2=q; c1=w; c2=w;
        }
        if ((counter!= m*n) || (counter==m*n && ry==0))
            System.out.println(" n = " + n + " m = " + m + ", s = " + sum + ", "+"mst = a[" + r1 + ".." + r2 + "]" + "[" + c1 + ".." + c2 + "]");
        else
            System.out.println( " n = " + n + " m = " + m + ", s = " + sum + ", "+"mst is empty");
    }
}
/*
test.in
20
1 : 3 8
29 16 84 107 93 87 86 -5
60 16 150 121 -10 43 7 26
143 -5 24 37 65 46 50 117
2 : 8 7
68 113 15 49 79 140 -3
103 11 -10 32 16 38 90
96 140 113 63 80 39 44
104 101 74 -4 124 65 65
21 86 39 87 -1 34 47
42 98 46 113 84 104 31
103 69 31 114 58 119 85
34 53 4 4 -4 119 126
3 : 4 3
-6 85 126
84 76 82
57 145 77
141 76 19
4 : 6 6
5 30 67 54 14 98
135 1 49 56 49 51
111 30 1 42 75 85
142 111 110 99 18 133
147 8 71 32 130 49
2 22 115 60 120 82
5 : 5 9
18 20 86 122 -5 116 133 44 146
-4 132 45 121 81 98 150 16 5
67 53 4 111 100 82 5 -6 23
94 112 77 72 94 37 6 72 131
-8 64 76 92 145 9 9 101 121
6 : 9 7
87 57 141 29 70 18 129
-10 122 148 6 125 31 40
106 0 24 92 19 109 24
114 96 130 42 81 91 78
-7 10 145 149 144 72 60
68 111 15 92 49 71 60
122 127 68 1 43 24 54
65 65 46 20 130 149 -8
17 84 35 107 71 49 90
7 : 2 3
-5 9 118
55 64 72
8 : 2 7
73 43 95 20 136 123 98
148 141 5 51 22 19 110
9 : 3 1
17
1
72
10 : 10 6
90 105 101 48 -4 50
9 71 150 96 106 116
110 5 35 101 91 -4
54 101 125 125 19 82
-3 86 113 6 68 35
-1 129 51 29 85 19
149 11 86 -2 127 149
93 100 69 106 108 63
52 -6 66 30 94 60
58 141 107 56 43 100
11 : 1 9
144 21 92 104 16 58 86 -8 74
12 : 7 4
43 71 150 13
-6 78 71 114
58 48 25 61
8 116 10 27
125 58 12 28
14 8 116 123
97 16 4 74
13 : 5 5
15 148 134 144 102
97 52 22 -4 63
30 124 1 138 15
29 81 142 126 46
96 91 45 71 111
14 : 8 1
59
103
15
133
42
111
13
83
15 : 1 10
-10 87 8 58 84 10 117 23 115 113
16 : 9 1
89
-9
119
-6
56
60
132
63
90
17 : 10 7
4 -10 -3 22 -5 40 49
146 58 107 -7 40 37 66
143 92 123 62 131 13 121
129 97 -3 35 115 -2 43
129 5 139 135 50 128 41
31 125 120 7 119 138 107
95 121 99 71 57 24 35
34 46 63 -7 26 62 5
83 4 14 133 120 56 14
89 100 49 14 48 -6 98
18 : 4 9
104 117 41 90 60 86 70 -3 69
71 3 87 119 74 40 39 8 135
141 143 24 98 94 36 24 35 128
-8 64 23 101 6 9 50 9 75
19 : 5 5
37 32 17 30 111
144 128 -3 131 82
65 135 54 66 50
-10 30 39 56 76
97 13 104 114 123
20 : 7 4
22 112 8 102
54 88 10 59
121 92 88 80
120 118 61 77
-6 8 150 34
56 -5 28 21
118 75 22 143
test.out:
1: n = 3 m = 8, s = 1387, mst = a[0..2][0..7]
2: n = 8 m = 7, s = 3591, mst = a[0..7][0..6]
3: n = 4 m = 3, s = 962, mst = a[0..3][0..2]
4: n = 6 m = 6, s = 2404, mst = a[0..5][0..5]
5: n = 5 m = 9, s = 3067, mst = a[0..4][0..8]
6: n = 9 m = 7, s = 4397, mst = a[0..8][0..6]
7: n = 2 m = 3, s = 313, mst = a[0..1][0..2]
8: n = 2 m = 7, s = 1084, mst = a[0..1][0..6]
9: n = 3 m = 1, s = 90, mst = a[0..2][0..0]
10: n = 10 m = 6, s = 4259, mst = a[0..9][0..5]
11: n = 1 m = 9, s = 587, mst = a[0..0][0..8]
12: n = 7 m = 4, s = 1562, mst = a[0..6][0..3]
13: n = 5 m = 5, s = 1919, mst = a[0..4][0..4]
14: n = 8 m = 1, s = 559, mst = a[0..7][0..0]
15: n = 1 m = 10, s = 615, mst = a[0..0][1..9]
16: n = 9 m = 1, s = 594, mst = a[0..8][0..0]
17: n = 10 m = 7, s = 4464, mst = a[0..9][0..6]
18: n = 4 m = 9, s = 2262, mst = a[0..3][0..8]
19: n = 5 m = 5, s = 1721, mst = a[0..4][0..4]
20: n = 7 m = 4, s = 1856, mst = a[0..6][0..3]
 */