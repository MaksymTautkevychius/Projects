#include <iostream>
#include <cmath>

const double* aver(const double* arr, size_t size, double& average) {
    if (size == 0)
    {
        average = 0;
        return nullptr;
    }
    double sum = 0;
    for (size_t i = 0; i < size; ++i) sum += arr[i];
    average = sum / size;
    double closest_diff = std::abs(arr[0] - average);
    const double* closest_ptr = &arr[0];

    for (size_t i = 1; i < size; ++i)
    {
        double diff = std::abs(arr[i] - average);
        if (diff < closest_diff)
        {
            closest_diff = diff;
            closest_ptr = &arr[i];
        }
    }
    return closest_ptr;
}

int main() {
    using std::cout; using std::endl;
    double arr[] = {1, 2, -1.5, 3.25, 5.5, 7.75, -0.25, 5.75};
    size_t size = std::size(arr);
    double average = 0;
    const double* p = aver(arr, size, average);
    cout << *p << " " << average << endl;

    return 0;
}
