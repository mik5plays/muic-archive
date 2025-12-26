use std::{cmp::Ordering, ops::Range};

#[allow(dead_code)]
#[derive(Debug, Eq, PartialEq)] // make this enum type support equality test (i.e., ==)
pub enum Classification {
    Perfect,
    Deficient,
    Excessive,
}

#[allow(dead_code)]
pub fn classify_perfect(n: u64) -> Classification {
    let mut aliquot: u64 = 0;
    for x in 1..n {
        if n % x == 0 {
            aliquot += x;
        }
    }

    let classify: i64 = aliquot as i64 - n as i64;
    if classify > 0 {
        Classification::Excessive
    } else if classify < 0 {
        Classification::Deficient
    } else {
        Classification::Perfect
    }
}

#[allow(dead_code)]
pub fn select_perfect(range: Range<u64>, kind: Classification) -> Vec<u64> {
    range.filter(|&n| classify_perfect(n) == kind).collect()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn basic_classify() {
        use Classification::*;
        assert_eq!(classify_perfect(1), Deficient);
        assert_eq!(classify_perfect(6), Perfect);
        assert_eq!(classify_perfect(12), Excessive);
        assert_eq!(classify_perfect(28), Perfect);
        // Some extra tests
        assert_eq!(classify_perfect(18), Excessive);
        assert_eq!(classify_perfect(496), Perfect);
        assert_eq!(classify_perfect(8), Deficient);
    }

    #[test]
    fn basic_select() {
        use Classification::*;
        assert_eq!(select_perfect(1..10_000, Perfect), vec![6, 28, 496, 8128]);
        assert_eq!(
            select_perfect(1..50, Excessive),
            vec![12, 18, 20, 24, 30, 36, 40, 42, 48]
        );
        assert_eq!(
            select_perfect(1..11, Deficient),
            vec![1, 2, 3, 4, 5, 7, 8, 9, 10]
        );
        // not sure if extra tests are of use for this one
    }
}
