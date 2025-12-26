#[allow(dead_code)]

/*

This function has two base cases:
- If the input slice is empty, it returns None.
- If the input slice has one element, it returns Some(0) if that element is equal to k, and returns None if not.

Afterwards, the recursion takes place where:
- Array slicing (split_at) is used to slice the array into two halves (half is marked by index mid, which is just half of the fed array xs).
- rayon::join is used to search both halves in parallel.

When the results from the two halves are obtained, they are matched:
- Since the assigment wants left-preferred, if both left and right return something, the index from the left half is returned.
- If only the right half returns something, what is returned is added to mid for an accurate index in the original array.
- Otherwise, return None. (as this implies both halves returned None).

Work:
W(n) = W(n/2) + W(n/2) + O(1) = 2W(n/2) + O(1) --> O(n)
--> The two recursive calls each take W(n/2) since they each work on half the array.
--> O(1) is the slicing and matching work, whcih in theory should all be constant.
--> As such, work is O(n).

Span:
S(n) = S(n/2) + O(1) --> O(log n)
--> Only one of the two recursive calls contributes to the span, since they are done in parallel.
--> O(1) is the slicing and matching work, which in theory should all be constant.
--> As such, n halves a maximum of log_2(n) times until it reaches 1. Therefore this resolves to O(log n).

*/

pub fn par_lin_search<T: Eq + Sync>(xs: &[T], k: &T) -> Option<usize> {
    let x = xs.len();
    if x == 0 {
        return None;
    }
    if x == 1 {
        if &xs[0] == k {
            return Some(0);
        } else {
            return None;
        }
    }

    let mid = x / 2;
    let (left, right) = xs.split_at(mid);

    let left_result = rayon::join(|| par_lin_search(left, k), || par_lin_search(right, k));

    match left_result {
        (Some(idx), _) => Some(idx),
        (None, Some(idx)) => Some(mid + idx),
        (None, None) => None,
    }
}

#[cfg(test)]
mod tests {
    use crate::linsearch::par_lin_search;

    #[test]
    fn basic_lin_search() {
        let xs = vec![3, 1, 4, 2, 7, 3, 1, 9];
        assert_eq!(par_lin_search(&xs, &3), Some(0));
        assert_eq!(par_lin_search(&xs, &5), None);
        assert_eq!(par_lin_search(&xs, &1), Some(1)); // first occurence in list, staple for linear search (as left preferred)
        assert_eq!(par_lin_search(&xs, &2), Some(3));
        // additional tests
        assert_eq!(par_lin_search(&xs, &9), Some(7));
        assert_eq!(par_lin_search(&xs, &4), Some(2));
        assert_eq!(par_lin_search(&xs, &15), None);

        let ys = vec!['a', 'b', 'c', 'd', 'e', 'f', 'h', 'k'];
        assert_eq!(par_lin_search(&ys, &'e'), Some(4));
        assert_eq!(par_lin_search(&ys, &'z'), None);
        assert_eq!(par_lin_search(&ys, &'a'), Some(0));
        assert_eq!(par_lin_search(&ys, &'k'), Some(7));
    }
}
