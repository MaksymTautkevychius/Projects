#include <iostream>
#include <string>
#include <cctype>
#include <algorithm>

int countUniqueCharacters(const std::string& pass) {
    int count = 0;
    bool charSet[256] = {false};
    for (char ch : pass) {
        if (!charSet[static_cast<unsigned char>(ch)]) {
            charSet[static_cast<unsigned char>(ch)] = true;
            count++;
        }
    }

    return count;
}
bool hasDigit(const std::string& pass) {
    for (char ch : pass) {
        if (std::isdigit(ch)) {
            return true;
        }
    }
    return false;
}

bool hasUppercase(const std::string& pass) {
    for (char ch : pass) {
        if (std::isupper(ch)) {
            return true;
        }
    }
    return false;
}

bool hasLowercase(const std::string& pass) {
    for (char ch : pass) {
        if (std::islower(ch)) {
            return true;
        }
    }
    return false;
}

bool hasNonAlphanumeric(const std::string& pass) {
    for (char ch : pass) {
        if (!std::isalnum(ch)) {
            return true;
        }
    }
    return false;
}

bool checkingPass(const std::string& pass) {
    int flag =0;
    if (pass.length() < 8) {
        std::cout << "Too short" << std::endl;
        flag++;
    }
     if (countUniqueCharacters(pass) < 6) {
        std::cout << "Too few different characters" << std::endl;
         flag++;
    }

    if (!hasDigit(pass)) {
        std::cout << "No digit" << std::endl;
        flag++;
    }

    if (!hasUppercase(pass)) {
        std::cout << "No uppercase letter" << std::endl;
        flag++;
    }

    if (!hasLowercase(pass)) {
        std::cout << "No lowercase letter" << std::endl;
        flag++;
    }

    if (!hasNonAlphanumeric(pass)) {
        std::cout << "No non-alphanumeric character" << std::endl;
        flag++;
    }
    if (flag>0) return false;

    return true;
}

int main() {
    using std::cout;
    using std::endl;
    const std::string passes[] = {
        "AbcDe93", "A1b:A1b>", "Ab:Acb<", "abc123><", "Zorro@123"
    };

    for (size_t i = 0; i < std::size(passes); ++i) {
        cout << "checking " << passes[i] << endl;
        if (checkingPass(passes[i])) cout << "OK" << endl;
        cout << endl;
    }

    return 0;
}
