use crate::cwslice::UnsafeSlice;
#[allow(dead_code)]
use rayon::prelude::*;

// From inclass
pub fn prefix_scan(xs: &[i32]) -> (Vec<i32>, i32) {
    use rayon::iter::*;
    if xs.is_empty() {
        return (Vec::new(), 0);
    }
    if xs.len() == 1 {
        return (vec![0], xs[0]);
    }

    let n = xs.len();
    // contract
    let mut contracted: Vec<i32> = (0..n / 2)
        .into_par_iter()
        .map(|i| xs[2 * i] + xs[2 * i + 1])
        .collect();

    if n % 2 == 1 {
        contracted.push(xs[n - 1]);
    }

    // recurse
    let (contracted_prefixes, mut contracted_sum) = prefix_scan(&contracted);

    // expand
    let mut prefixes: Vec<i32> = (0..n / 2)
        .into_par_iter()
        .flat_map(|i| vec![contracted_prefixes[i], contracted_prefixes[i] + xs[2 * i]])
        .collect();

    if n % 2 == 1 {
        prefixes.push(contracted_prefixes[n / 2]);
    }

    (prefixes, contracted_sum)
}

pub fn par_filter<F>(xs: &[i32], p: F) -> Vec<i32>
where
    F: Fn(i32) -> bool + Send + Sync,
{
    if xs.len() == 0 {
        return Vec::new();
    }

    let flags: Vec<i32> = xs.par_iter().map(|&x| if p(x) { 1 } else { 0 }).collect();

    let (prefixes, total) = prefix_scan(&flags);

    let mut out: Vec<i32> = Vec::with_capacity(total as usize);
    unsafe {
        out.set_len(total as usize);
    }

    let unsafe_out = UnsafeSlice::new(out.as_mut_slice());

    xs.par_iter().enumerate().for_each(|(i, &x)| {
        if flags[i] == 1 {
            let pos = prefixes[i] as usize;
            unsafe {
                unsafe_out.write(pos, x);
            }
        }
    });

    out
}
