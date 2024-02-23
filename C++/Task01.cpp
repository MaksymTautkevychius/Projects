#include <iostream>
using namespace std;

int main() {
    int min = INT_MAX,max = INT_MIN,min_times = 0,max_times = 0,num;
    while (true) {
        cin >> num;
        if (num == 0) {
            break;
        }
        if (num < min) {
            min = num;
            min_times = 1;
        }
        else if (num == min) {
            min_times++;
        }
        if (num > max) {
            max = num;
            max_times = 1;
        }
        else if (num == max) {
            max_times++;
        }
    }
    cout << "min:" << min << " " << min_times << " times" << "\n" << "max:" << max << " " << max_times << " times" << endl;
    return 0;
}
