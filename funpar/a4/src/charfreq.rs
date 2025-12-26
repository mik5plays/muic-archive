use chashmap::CHashMap;
use rayon::iter::{IntoParallelRefIterator, ParallelIterator};
use std::collections::HashMap;

pub fn par_char_freq(chars: &[u8]) -> HashMap<u8, u32> {
    chars
        .par_iter()
        .fold(
            || HashMap::new(),
            |mut map, &c| {
                *map.entry(c).or_insert(0) += 1;
                map
            },
        )
        .reduce(
            || HashMap::new(),
            |mut map1, map2| {
                for (c, count) in map2 {
                    *map1.entry(c).or_insert(0) += count;
                }
                map1
            },
        )
}

#[cfg(test)]
mod tests {
    use super::par_char_freq;
    use std::collections::HashMap;

    #[test]
    fn basic_tests() {
        // basic tests from the assignment instructions
        assert_eq!(
            par_char_freq("banana".as_bytes()),
            HashMap::from([(b'b', 1), (b'a', 3), (b'n', 2)])
        );
        assert_eq!(
            par_char_freq("mississippi".as_bytes()),
            HashMap::from([(b'm', 1), (b'i', 4), (b's', 4), (b'p', 2)])
        );
        // additional tests
        assert_eq!(
            par_char_freq("funpar".as_bytes()),
            HashMap::from([
                (b'f', 1),
                (b'u', 1),
                (b'n', 1),
                (b'p', 1),
                (b'a', 1),
                (b'r', 1)
            ])
        );
        assert_eq!(
            par_char_freq("xxxxxxxxxx".as_bytes()),
            HashMap::from([(b'x', 10)])
        );
        assert_eq!(par_char_freq("".as_bytes()), HashMap::new()); // empty input so empty hashmap
    }
}
