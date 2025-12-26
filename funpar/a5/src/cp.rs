#[allow(dead_code)]
use rayon::prelude::*;

// working directly with square of distance
pub fn distance(a: (i32, i32), b: (i32, i32)) -> i64 {
    let dx = (b.0 - a.0) as i64;
    let dy = (b.1 - a.1) as i64;
    dx * dx + dy * dy
}

// parallel merge by y (adapted from inclass)
fn merge(xs: &[(i32, i32)], ys: &[(i32, i32)]) -> Vec<(i32, i32)> {
    let (mut xs, mut ys) = (xs, ys);
    let mut combined = vec![];

    use std::cmp::Ordering;
    while !xs.is_empty() && !ys.is_empty() {
        match xs[0].1.cmp(&ys[0].1) {
            Ordering::Less => {
                combined.push(xs[0]);
                xs = &xs[1..];
            }
            _ => {
                combined.push(ys[0]);
                ys = &ys[1..];
            }
        }
    }

    combined.extend(xs.iter());
    combined.extend(ys.iter());

    combined
}

fn cp_helper(xs: &[(i32, i32)]) -> (i64, Vec<(i32, i32)>) {
    // brute force for small slices
    if xs.len() <= 3 {
        let mut d = i64::MAX;
        for i in 0..xs.len() {
            for j in i + 1..xs.len() {
                d = d.min(distance(xs[i], xs[j]));
            }
        }

        let mut ys = xs.to_vec();
        ys.sort_by(|a, b| a.1.cmp(&b.1));

        return (d, ys);
    }

    //(l, L), (r, R) = cp_helper(left), cp_helper(right)

    let mid = xs.len() / 2;
    let mid_x = xs[mid].0;

    let (left, right) = xs.split_at(mid);
    let (lres, rres) = rayon::join(|| cp_helper(left), || cp_helper(right));

    let (l, L, r, R) = (lres.0, lres.1, rres.0, rres.1);

    // new xs = merge(L, R)
    let new_xs = merge(&L, &R);

    // d = min(l, r)
    let d = l.min(r);

    // band of points that are at most d away from the dividing line
    let band: Vec<(i32, i32)> = new_xs
        .par_iter()
        .filter(|p| (p.0 - mid_x).abs() as i64 <= d)
        .cloned()
        .collect();

    // sort by y
    let mut ys = band.to_vec();
    ys.sort_by(|a, b| a.1.cmp(&b.1));

    // lemma: only the next 7 points in y need to be checked
    let mut best = d;
    for i in 0..ys.len() {
        for j in i + 1..ys.len().min(i + 8) {
            let dist = distance(ys[i], ys[j]);
            if dist < best {
                best = dist;
            }
        }
    }

    (best, new_xs)
}

pub fn par_closest_distance(points: &[(i32, i32)]) -> i64 {
    // sort by x
    let mut xs = points.to_vec();
    xs.par_sort_by(|a, b| a.0.cmp(&b.0));

    // return cp_helper by the sorted xs
    let (d, _) = cp_helper(&xs);
    d
}
