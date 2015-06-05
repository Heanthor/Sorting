CMSC351 Selection and Insertion Sort
Author: Reed Trevelyan

Running time ~1.5 hours on an i5 2500k 3.9GHz.
Progress bar shows completed sorts out of the set, so while it might sit at 0% 
for a while, it is processing.

Example output: 

Hello! Example output can be found in readme.txt.
Program has started. Generating dataset of size 10.
Array generated in time 349 microseconds.

That is pretty fast. Let's generate 10 arrays of size
1,000,000.

Arrays created in total time 670 miliseconds, average time
6 ms per array.

Now testing Insertion Sort. We will test 10 iterations of
each array size.

Generating datasets for use in testing...

Sets generated. Begin sorting.

Note: times printed will be for individual sorts, averaged
over all iterations.

Array Size       Time(ms)       Time(sec)
      100           0.044               0
   10,000          14.318           0.014
  100,000       1,338.591           1.339
1,000,000     134,672.174         134.672

Now testing insertion sort in the worst case, in which all
elements are in reverse order.

Array Size       Time(ms)       Time(sec)
      100           0.006               0
   10,000          27.085           0.027
  100,000       2,678.975           2.679
1,000,000     270,507.039         270.507

Now we will test Selection Sort using the same methods.

Array Size       Time(ms)       Time(sec)
      100           0.071               0
   10,000          36.228           0.036
  100,000         2,974.8           2.975
1,000,000     299,261.454         299.261

And the worst case, despite the fact that selection sort
should run in approximately the same time for any input.

Array Size       Time(ms)       Time(sec)
      100           0.007               0
   10,000          42.795           0.043
  100,000       4,173.115           4.173
1,000,000     419,767.861         419.768

Done!