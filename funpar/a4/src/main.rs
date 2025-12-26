mod base64;
mod charfreq;
mod linsearch;
mod numprimes;
mod pangrindrome;
mod perfect;
mod roman;

fn main() {
    println!("{:?}", charfreq::par_char_freq("banana".as_bytes()));
    println!("{:?}", charfreq::par_char_freq("mississippi".as_bytes()));
}
