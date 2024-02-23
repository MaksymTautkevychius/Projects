#include <iostream>

int gcdRec(int a, int b) {
    if (b == 0) {
        return a;
    }
    return gcdRec(b, a % b);
}

int sumDigits(int a) {
    if (a == 0) return 0;
    return a % 10 + sumDigits(a / 10);
}
int numDigits(int a) {
    if (a == 0) return 1;

    return 1 + numDigits(a / 10);
}
void printOddEvenHelper(int first, int last) {
    if (first > last) return;
    std::cout << first << " ";
    printOddEvenHelper(first + 2, last);
}
void printOddEven(int a) {
    if (a % 2 == 0) printOddEvenHelper(2, a);
    else printOddEvenHelper(1, a);
}
void hailstone(int a) {
    if (a == 1) {
        std::cout << "1 ";
        return;
    }
    std::cout << a << " ";
    if (a % 2 == 0) {
        hailstone(a / 2);
    } else {
        hailstone(3 * a + 1);
    }
}


int main() {
    std::cout << "gcdRec(12, 42) = " << gcdRec(12, 42) << std::endl
              << "gcdRec(12, 25) = " << gcdRec(12, 25) << std::endl;

    std::cout << "sumDigits(123) = " << sumDigits(123) << std::endl
              << "sumDigits(971) = " << sumDigits(971) << std::endl;

    std::cout << "numDigits(12345) = " << numDigits(12345) << std::endl
              << "numDigits(971) = " << numDigits(971) << std::endl;

    std::cout << "printOddEven(15): ";
    printOddEven(15);
    std::cout << std::endl;

    std::cout << "printOddEven(14): ";
    printOddEven(14);
    std::cout << std::endl;

    std::cout << "hailstone(13): ";
    hailstone(13);
    std::cout << std::endl;

    return 0;
}
