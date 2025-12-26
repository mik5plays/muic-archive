use rayon::iter::IntoParallelIterator;
use rayon::iter::ParallelIterator;

#[allow(dead_code)]
pub fn par_is_prime(n: u64) -> bool {
    if n < 2 {
        return false;
    }
    let sqrt_n = (n as f64).sqrt() as u64;

    !(2..=sqrt_n).into_par_iter().any(|i| n % i == 0)
}

#[allow(dead_code)]
pub fn par_count_primes(n: u32) -> usize {
    (1..=n)
        .into_par_iter()
        .filter(|&x| par_is_prime(x as u64))
        .count()
}

#[cfg(test)]
mod tests {
    use crate::numprimes::{par_count_primes, par_is_prime};

    #[test]
    fn basic_is_prime() {
        assert_eq!(false, par_is_prime(0));
        assert_eq!(false, par_is_prime(1));
        assert_eq!(true, par_is_prime(2));
        assert_eq!(true, par_is_prime(3));
        assert_eq!(false, par_is_prime(6));
        assert_eq!(false, par_is_prime(25));
        assert_eq!(true, par_is_prime(41));
        // additional tests
        assert_eq!(true, par_is_prime(13));
        assert_eq!(true, par_is_prime(97));
        assert_eq!(false, par_is_prime(100));
        assert_eq!(true, par_is_prime(2147483647));
    }
    #[test]
    fn basic_count_primes() {
        assert_eq!(25, par_count_primes(100));
        assert_eq!(78498, par_count_primes(1_000_000));
        // additional tests
        assert_eq!(4, par_count_primes(10));
        assert_eq!(15, par_count_primes(50));
        assert_eq!(168, par_count_primes(1000));
        assert_eq!(1229, par_count_primes(10_000));
    }
}
