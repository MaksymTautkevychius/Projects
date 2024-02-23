
#include <iostream>
//#define POL
#define ENG

#ifdef POL
#define START_MASSAGE "podaj liczbe naturalna (0 jesli zakonczyc): "
#define FINAL_MASSAGE "Maksymalna suma cyfr wyniosla "
#elif defined ENG
#define START_MASSAGE "enter a natural number (0 if done): "
#define FINAL_MASSAGE "Max sum of digits was %d for %d\n"
#else
#error "You need to choose one language"
#endif

int InSum(int num) {
    int sum = 0;
    while (num != 0) {
        sum += num % 10;
        num /= 10;
    }
    return sum;
}
int main() {
    int number, maxSum = 0, Sum_Final = 0;
    do {
        std::printf(START_MASSAGE);
        std::scanf("%d", &number);

        if (number != 0) {
            int SUM = InSum(number);
            if (SUM > maxSum) {
                maxSum = SUM;
                Sum_Final = number;
            }
        }
    } while (number != 0);
    std::printf(FINAL_MASSAGE, maxSum, Sum_Final);
    return 0;
}
