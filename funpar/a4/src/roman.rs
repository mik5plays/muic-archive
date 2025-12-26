#[allow(dead_code)]

const ROMAN: &[(u16, &str)] = &[
    (1000, "M"),
    (900, "CM"),
    (500, "D"),
    (400, "CD"),
    (100, "C"),
    (90, "XC"),
    (50, "L"),
    (40, "XL"),
    (10, "X"),
    (9, "IX"),
    (5, "V"),
    (4, "IV"),
    (1, "I"),
];

pub fn to_roman(n: u16) -> String {
    let mut num = n;
    let mut out = String::new();

    for (val, sym) in ROMAN.iter() {
        while num >= *val {
            out.push_str(sym);
            num -= *val;
        }
        if num == 0 {
            break;
        }
    }

    out
}

#[allow(dead_code)]
pub fn parse_roman(roman_number: &str) -> u16 {
    let mut out: u16 = 0;
    let mut idx: u16 = 0;

    while idx < roman_number.len() as u16 {
        if let Some((value, symbol)) = ROMAN
            .iter()
            .find(|(_, sym)| roman_number[idx as usize..].starts_with(*sym))
        {
            out += *value;
            idx += symbol.len() as u16;
        } else {
            break;
        }
    }

    out
}

#[cfg(test)]
mod tests {
    use super::{parse_roman, to_roman};

    #[test]
    fn basic_digits() {
        assert_eq!("I", to_roman(1));
        assert_eq!("V", to_roman(5));
        assert_eq!("X", to_roman(10));
        assert_eq!("L", to_roman(50));
        assert_eq!("C", to_roman(100));
        // no need for more tests here i feel
    }

    #[test]
    fn basic_mixture() {
        assert_eq!("II", to_roman(2));
        assert_eq!("IV", to_roman(4));
        assert_eq!("IX", to_roman(9));
        assert_eq!("XII", to_roman(12));
        assert_eq!("XIV", to_roman(14));
        // some more tests
        assert_eq!("XLIX", to_roman(49));
        assert_eq!("XCIII", to_roman(93));
        assert_eq!("CDXLIV", to_roman(444));
        assert_eq!("MCMLIV", to_roman(1954));
    }

    #[test]
    fn basic_parsing() {
        assert_eq!(3, parse_roman("III"));
        assert_eq!(4, parse_roman("IV"));
        assert_eq!(8, parse_roman("VIII"));
        assert_eq!(19, parse_roman("XIX"));
        // some more tests
        assert_eq!(38, parse_roman("XXXVIII"));
        assert_eq!(49, parse_roman("XLIX"));
        assert_eq!(99, parse_roman("XCIX"));
        assert_eq!(444, parse_roman("CDXLIV"));
    }
}
