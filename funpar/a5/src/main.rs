mod cp;
mod cwslice;
mod fib;
mod filter;
mod ontime;
mod sudoku;

fn main() {
    use crate::fib::*;
    use crate::filter::*;
    use crate::ontime::*;

    let a = (1, 1, 1, 0);
    let b = (1, 1, 1, 0);

    println!("Hello, world!");
    println!("{:?}", par_fib_seq(5));

    let a = vec![3, 1, 4, 5, 10, 7, 9];
    let evens = par_filter(&a, |x| x % 2 == 0);
    let odds = par_filter(&a, |x| x % 2 == 1);

    println!("{:?}", evens);
    println!("{:?}", odds);

    let demo = "a,hhh,ab,hello,world,,meh";
    println!("{:?}", par_split(demo, ','));

    let demo2 = "a,b,c,";
    println!("{:?}", par_split(demo2, ','));

    /*
    The original 2008.csv is too big and causes memory issues so I tested using
    two smaller versions:
    - 2008_short.csv (10k lines) <1 second to run
    - 2008_medium.csv (100k lines) ~10 seconds to run
     */
    println!("{:?}", ontime_rank("2008_short.csv").unwrap());
    println!("{:?}", ontime_rank("2008_medium.csv").unwrap());

    let points = [(2, 3), (12, 30), (40, 50), (5, 1), (12, 10), (3, 4)];
    
    println!("{:?}", cp::par_closest_distance(&points));
    println!("{:?}", cp::distance((2, 5), (4, 10)));
}
