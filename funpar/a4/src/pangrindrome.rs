#[allow(dead_code)]
pub fn is_palindrome(s: &str) -> bool {
    s.chars().eq(s.chars().rev())
}

#[allow(dead_code)]
pub fn is_pangram(s: &str) -> bool {
    let lower = s.to_lowercase(); // making it case insensitive

    for letter in 'a'..='z' {
        if !lower.chars().any(|c| c == letter) {
            return false;
        }
    }

    true
}

#[cfg(test)]
mod tests {
    use crate::pangrindrome::{is_palindrome, is_pangram};

    #[test]
    fn basic_is_palindrome() {
        assert_eq!(true, is_palindrome("r"));
        assert_eq!(true, is_palindrome("abba"));
        assert_eq!(true, is_palindrome("abcba"));
        assert_eq!(false, is_palindrome("abc"));
        // some more tests
        assert_eq!(true, is_palindrome("17071"));
        assert_eq!(true, is_palindrome("racecar"));
        assert_eq!(false, is_palindrome("taxi"));
        assert_eq!(false, is_palindrome("preepr"));
    }

    #[test]
    fn basic_pangram() {
        let quick_brown_fox = "The quick brown fox jumps over the lazy Dog";
        assert_eq!(true, is_pangram(quick_brown_fox));
        let quick_prairie_dog = "The quick prairie dog jumps over the lazy fox";
        assert_eq!(false, is_pangram(quick_prairie_dog));
        // some more tests
        assert_eq!(true, is_pangram("abcdefghijklmnOPQRstuVWXYZ"));
        assert_eq!(false, is_pangram("You'd think thiz sentence has all the letters of the alphabet but xt actually doesn't."));
    }
}
