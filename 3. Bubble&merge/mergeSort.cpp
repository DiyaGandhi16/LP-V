#include <iostream>
#include <omp.h>
#include <cstdlib>
#include <ctime>
using namespace std;

void merge(int arr[], int l, int mid, int r) {
    int n1 = mid - l + 1;
    int n2 = r - mid;
    
    int* left = new int[n1];
    int* right = new int[n2];

    for (int i = 0; i < n1; i++) left[i] = arr[l + i];
    for (int i = 0; i < n2; i++) right[i] = arr[mid + 1 + i];

    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2) {
        arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
    }
    while (i < n1) arr[k++] = left[i++];
    while (j < n2) arr[k++] = right[j++];

    delete[] left;
    delete[] right;
}

void sequentialMergeSort(int arr[], int l, int r) {
    if (l < r) {
        int mid = (l + r) / 2;
        sequentialMergeSort(arr, l, mid);
        sequentialMergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }
}

void parallelMergeSort(int arr[], int l, int r) {
    if (l < r) {
        const int THRESHOLD = 1000;
        if (r - l < THRESHOLD) {
            sequentialMergeSort(arr, l, r);
            return;
        }

        int mid = (l + r) / 2;

        #pragma omp task shared(arr)
        parallelMergeSort(arr, l, mid);
        #pragma omp task shared(arr)
        parallelMergeSort(arr, mid + 1, r);

        #pragma omp taskwait
        merge(arr, l, mid, r);
    }
}

int main() {
    const int N = 1000000;
    int arr[N], arr_copy[N];

    srand(time(nullptr)); // Seed random generator
    for (int i = 0; i < N; i++) {
        arr[i] = rand() % N;
        arr_copy[i] = arr[i];
    }

    cout << "Original array size: " << N << endl;

    // Sequential
    double start_seq = omp_get_wtime();
    sequentialMergeSort(arr_copy, 0, N - 1);
    double end_seq = omp_get_wtime();
    cout << "Sequential Merge Sort Time: " << (end_seq - start_seq) << " seconds" << endl;

    // Parallel
    double start_par = omp_get_wtime();
    #pragma omp parallel
    {
        #pragma omp single
        parallelMergeSort(arr, 0, N - 1);
    }
    double end_par = omp_get_wtime();
    cout << "Parallel Merge Sort Time: " << (end_par - start_par) << " seconds" << endl;

    return 0;
}