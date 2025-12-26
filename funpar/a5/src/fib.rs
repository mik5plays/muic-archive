use num_bigint::BigUint;
use num_traits::{One, Zero};
use rayon::prelude::*;

#[allow(dead_code)]

/*
say if A = (a, b, c, d) - (row, column)
then A = [ A B
           C D ]
*/
pub fn mult_2x2(
    a: &(BigUint, BigUint, BigUint, BigUint),
    b: &(BigUint, BigUint, BigUint, BigUint),
) -> (BigUint, BigUint, BigUint, BigUint) {
    let (a11, a12, a21, a22) = a;
    let (b11, b12, b21, b22) = b;

    let c11 = a11 * b11 + a12 * b21;
    let c12 = a11 * b12 + a12 * b22;
    let c21 = a21 * b11 + a22 * b21;
    let c22 = a21 * b12 + a22 * b22;

    (c11, c12, c21, c22)
}

/*
Since f1 = f2 = 1, this function just returns
(a + b,
 c + d)
*/
pub fn mult_f2f1(a: (BigUint, BigUint, BigUint, BigUint)) -> (BigUint, BigUint) {
    let (a11, a12, a21, a22) = (&a.0, &a.1, &a.2, &a.3);

    (a11 + a12, a21 + a22)
}

/*
Parallel prefix scan
- Work = W(n/2) + O(n) = O(n)
- Span = S(n/2) + O(1) = O(log n)
*/
fn prefix_scan(
    m: &[(BigUint, BigUint, BigUint, BigUint)],
) -> Vec<(BigUint, BigUint, BigUint, BigUint)> {
    let n = m.len();
    if n == 0 {
        return vec![];
    }
    if n == 1 {
        return vec![(
            BigUint::one(),
            BigUint::zero(),
            BigUint::zero(),
            BigUint::one(),
        )];
    }

    // pairwise multiplication, so m has length n/2 ["contracted"]
    // Work here simplifies to O(n) - assumption that BigInt operations are O(1)
    // Parallel map -- constant operation per chunk --> O(1) to span
    let foo: Vec<(BigUint, BigUint, BigUint, BigUint)> = m
        .par_chunks(2)
        .map(|chunk| {
            if chunk.len() == 1 {
                chunk[0].clone()
            } else {
                mult_2x2(&chunk[0], &chunk[1])
            }
        })
        .collect();

    // recursion -- W(n/2)
    // contributes S(n/2) to span
    let prefix_foo = prefix_scan(&foo);

    let mut out = vec![
        (
            BigUint::one(),
            BigUint::zero(),
            BigUint::zero(),
            BigUint::one()
        );
        n
    ];

    // combining, forming final array
    // n/2 chunks, O(1) per chunk --> O(n)
    // parallel chunk assigning --> O(1) to span
    out.par_chunks_mut(2).enumerate().for_each(|(i, chunk)| {
        let left_prefix = prefix_foo[i].clone();
        chunk[0] = left_prefix.clone();
        if chunk.len() == 2 {
            chunk[1] = mult_2x2(&left_prefix, &m[i * 2]);
        }
    });
    out
}

/*
Work = O(n) + O(n) --> O(n)
Span = O(log n) + O(1) --> O(log n)

meets assignment specifications
*/

pub fn par_fib_seq(n: u32) -> Vec<num_bigint::BigUint> {
    if n == 0 {
        return vec![];
    }

    let A = (
        BigUint::one(),
        BigUint::one(),
        BigUint::one(),
        BigUint::zero(),
    );
    let I = (
        BigUint::one(),
        BigUint::zero(),
        BigUint::zero(),
        BigUint::one(),
    );

    let n_A = vec![A.clone(); n as usize];

    // analyzed already -- O(n) work, O(log n) span
    let prefix_powers = prefix_scan(&n_A);

    // Do A^k * (f2, f1) = (f_(k+2), f_(k+1)) for each, but we keep f_(k+1)
    // Map --> Work = O(n), Span = O(1)
    prefix_powers
        .into_par_iter()
        .map(|mat| {
            let (a, b) = mult_f2f1(mat);
            b
        })
        .collect()
}
